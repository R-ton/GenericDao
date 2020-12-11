package classes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;

import string.StringFunction;

public class BasicReflect {
	public Method getMethodByField(Class objectClass,String name,Class fieldClass) throws Exception{
		Method method = objectClass.getMethod(name,fieldClass);
		return method;
	}
	
	public void setMethod(Object object,JSONObject data) throws Exception{
		Class objectClass = object.getClass();
		System.out.println(objectClass);
		Field[] fields = objectClass.getDeclaredFields();
		StringFunction stringFunction = new StringFunction();
		String methodBeginName = "set";
		for(int i=0;i<fields.length;i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String fieldNameModified = stringFunction.upperCaseString(fieldName, 0, 1);
			Class fieldClass = field.getType();
			String methodName = methodBeginName + fieldNameModified;
			Method method = this.getMethodByField(objectClass, methodName, fieldClass);
			Object value = data.get(fieldName);
			method.invoke(object,value);
		}
	}
}
