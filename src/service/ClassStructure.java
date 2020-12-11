package service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dao.BaseDao;
import string.StringFunction;

public class ClassStructure {
	public Vector simpleSelectSql(Connection connection,Object object,String sql) throws Exception{
		Class objectClass = object.getClass();
		String tableName = objectClass.getSimpleName();
		boolean create = false;
		if(connection == null) {
			BaseDao baseDao = new BaseDao();
			connection = baseDao.createConnectionToSqlite();
			create = true;
		}
	
		Statement statement = connection.createStatement();
		System.out.println(sql);
		ResultSet result = statement.executeQuery(sql);
		Vector data = new Vector();
		try {
			 data =  this.settingObjectFieldValueByResultSetInVector(object,result);
		}catch(Exception e) {
			throw e;
		}
		finally {
			result.close();
			statement.close();
			if(create) {
				connection.close();
			}
		}
		return data;
	}
	
	public Vector settingObjectFieldValueByResultSetInVector(Object object,ResultSet result) throws Exception{
		Vector list = new Vector();
		Class objectClass = object.getClass();
		String[] columnName = this.gettingColumnNameFromBase(result);
		Class[] fieldsClass = this.gettingFieldsClass(objectClass, columnName);
		
		while(result.next()) {
			Object newInstance = this.settersMethodInvoke(objectClass, columnName, fieldsClass, result);
			list.add(newInstance);
		}
		System.out.println(list.size());
		return list;
	}
	
	public String[] gettingColumnNameFromBase(ResultSet result) throws Exception{
		 ResultSetMetaData resultMeta = result.getMetaData();
		 int columnNumber = resultMeta.getColumnCount();
		 String[] columnName = new String[columnNumber];
		 System.out.println(columnName.length);
		 for(int i=0;i<columnNumber;i++) {
			 columnName[i] = resultMeta.getColumnName(i+1);
		 }
		 return columnName;
	}
	
	public Class[] gettingFieldsClass(Class objectClass,String[] columnName)throws Exception {
		int fieldCount = columnName.length;
		Class[] fieldsClass = new Class[fieldCount];
		for(int i=0;i<fieldCount;i++) {
			String column = columnName[i];
			Field fieldReference = this.gettingField(objectClass,column); 
			fieldsClass[i] = fieldReference.getType();
			System.out.println(fieldsClass[i]);
		}
		return fieldsClass;
	}
	
	public Field gettingField(Class objectClass,String name) throws Exception{
		Field field = objectClass.getDeclaredField(name);
		return field;
	}
	
	public List<Field> gettingStaticField(Object object) {
		Class objectClass = object.getClass();
		Field[] declaredFields = objectClass.getDeclaredFields();
		List<Field> staticFields = new ArrayList<Field>();
		for (Field field : declaredFields) {
		    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
		        staticFields.add(field);
		        System.out.println(field.getName());
		    }
		}
		return staticFields;
	}
	
	public Object settersMethodInvoke(Class objectClass,String[] columnName,Class[] fieldsClass,ResultSet result)throws Exception {
		Object object = objectClass.newInstance();
		StringFunction stringFunction = new StringFunction();
		String methodBeginName = "set";
		int fieldCount = columnName.length;
		for(int i=0;i<fieldCount;i++) {
			String fieldName = stringFunction.upperCaseString(columnName[i], 0, 1);
			String methodName = methodBeginName + fieldName;
			Class fieldClass = fieldsClass[i];
			Method method = this.gettingMethodByFieldClass(objectClass, methodName, fieldClass);
			Object value = result.getObject(i+1);
			
			System.out.println(method);
			System.out.println(value);
			System.out.println(value.getClass());
			method.invoke(object,value);
		}
		return object;
	}	
	
	public Method gettingMethodByFieldClass(Class objectClass,String name,Class fieldClass) throws Exception{
		Method method = objectClass.getMethod(name,fieldClass);
		return method;
	}

}
