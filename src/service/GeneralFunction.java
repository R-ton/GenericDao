package service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dao.BaseDao;
import string.StringFunction;

public class GeneralFunction {
	//select with sql 
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

	//simple select with TableName
		public Vector simpleSelectWithTableName(Connection connection,Object object,String tableName) throws Exception{
			Class objectClass = object.getClass();
			boolean create = false;
			if(connection == null) {
				BaseDao baseDao = new BaseDao();
				connection = baseDao.createConnectionToSqlite();
				create = true;
			}
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM "+tableName;
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
		
	//simple select
		public Vector simpleSelect(Connection connection,Object object) throws Exception{
			Class objectClass = object.getClass();
			String tableName = objectClass.getSimpleName();
			boolean create = false;
			if(connection == null) {
				BaseDao baseDao = new BaseDao();
				connection = baseDao.createConnectionToSqlite();
				create = true;
			}
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM "+tableName;
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
		
	//select with where
		public Vector selectWhere(Connection connection,Object object,Vector<String> reference,Vector<String> value) throws Exception{
			Class objectClass = object.getClass();
			String tableName = objectClass.getSimpleName();
			boolean create = false;
			if(connection == null) {
				BaseDao baseDao = new BaseDao();
				connection = baseDao.createConnectionToSqlite();
				create = true;
			}
			int referenceSize = reference.size();
			String sql = "SELECT * FROM "+tableName;
			sql += " where ";
			for(int i=0;i<referenceSize;i++) {
				String referenceValue = reference.get(i);
				if(i<referenceSize-1) {
					sql+= referenceValue+" = ? and ";
				}
				else {
					sql+= referenceValue+" = ?";
				}
			}
			System.out.println(sql);
			PreparedStatement statement = connection.prepareStatement(sql);
			for(int i=0;i<referenceSize;i++) {
				int set = i+1;
				String referenceValue = value.get(i);
				statement.setString(set, referenceValue);
			}
			ResultSet result = statement.executeQuery();
			Vector data = new Vector();
			try {
				data = this.settingObjectFieldValueByResultSetInVector(object,result);
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
		
		public List find(Object object,String attribute,List list)throws Exception {
			List result = null;
			StringFunction stringFunction = new StringFunction();
			Class attributeClass = attribute.getClass();
			Class objectClass = object.getClass();
			
			Field field = null;
			try {
					field = this.gettingField(objectClass, attribute);
					Class fieldClass = field.getClass();
					
					String fieldName = stringFunction.upperCaseString(field.getName(), 0, 1);
					String methodName = "get"+ fieldName;
					Method objectMethod = this.gettingMethodByFieldClass(objectClass, methodName, fieldClass);
					String value = objectMethod.invoke(object).toString();
					for(int i=0;i<list.size();i++) {
						Object objectData = list.get(i);
						String dataValue = objectMethod.invoke(objectData).toString();
						if(value.equals(dataValue)) {
							result.add(objectData);
						}
					}
					
			}catch(Exception e) {
				throw e;
			}
			
			return result;
		}
		
		public List findWithAttributeName(Object object,String attribute,Object[] list,String listAttribute)throws Exception {
			List result = new ArrayList();
			StringFunction stringFunction = new StringFunction();
			Class attributeClass = attribute.getClass();
			Class objectClass = object.getClass();
			
			Field field = null;
			try {
					field = this.gettingField(objectClass, attribute);
					Class fieldClass = field.getType();
					
					String fieldName = stringFunction.upperCaseString(field.getName(), 0, 1);
					String methodName = "get"+ fieldName;
					Method objectMethod = this.gettingMethodByFieldClassGetMethod(objectClass, methodName);
					String value = objectMethod.invoke(object).toString();
					String fieldNameList = stringFunction.upperCaseString(listAttribute, 0, 1);
					String methodNameList = "get"+ fieldNameList;
					for(int i=0;i<list.length;i++) {
						
						Object objectData = list[i];
						Class objectDataClass = list[i].getClass();
						System.out.println(objectData.getClass());
						System.out.println(objectClass);
						Method objectMethodList = this.gettingMethodByFieldClassGetMethod(objectDataClass, methodNameList);
						String dataValue = objectMethodList.invoke(objectData).toString();
						
						System.out.println(dataValue+"---"+value);
						if(value.equals(dataValue)) {				
							result.add(objectData);
						}
					}
					
			}catch(Exception e) {
				throw e;
			}
			
			return result;
		}
		
	//getting function
		public Vector gettersMethodInvoke(Object object,String[] columnName)throws Exception {
			Vector data = new Vector();
			Class objectClass = object.getClass();
			StringFunction stringFunction = new StringFunction();
			String methodBeginName = "get";
			int fieldCount = columnName.length;
			for(int i=0;i<fieldCount;i++) {
				String fieldName = stringFunction.upperCaseString(columnName[i], 0, 1);
				String methodName = methodBeginName + fieldName;
				Method method = this.gettingMethodByFieldClassGetMethod(objectClass, methodName);
				System.out.println(method);
				String dataFetch = method.invoke(object).toString();
				data.add(dataFetch);
			}
			return data;
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
		
		public String[] gettingFieldsName(Class objectClass) {
			Field[] fields = this.gettingFields(objectClass);
			int length = fields.length;
			String[] fieldsName = new String[length];
			for(int i=0;i<length;i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				fieldsName[i] = fieldName;
			}
			return fieldsName;
		}
		
		public Field[] gettingFields(Class objectClass) {
			Field[] fields = objectClass.getDeclaredFields();
			return fields;
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
		
		public Method gettingMethodByFieldClass(Class objectClass,String name,Class fieldClass) throws Exception{
			Method method = objectClass.getMethod(name,fieldClass);
			return method;
		}
		
		public Method gettingMethodByFieldClassGetMethod(Class objectClass,String name) throws Exception{
			Method method = objectClass.getMethod(name);
			return method;
		}
			
	//setting functions
	//resultSet to ArrayList
		public ArrayList settingObjectFieldValueByResultSetInArrayList(Object object,ResultSet result) throws Exception{
			ArrayList list = new ArrayList();
			Class objectClass = object.getClass();
			try {
				System.out.println(result);
				ResultSetMetaData resultMeta = result.getMetaData();
				int columnNumber = resultMeta.getColumnCount();
				System.out.println(columnNumber);
				String[] columnName = this.gettingColumnNameFromBase(result);
				Class[] fieldsClass = this.gettingFieldsClass(objectClass, columnName);
				
				
					while(result.next()) {
						Object newInstance = this.settersMethodInvoke(objectClass, columnName, fieldsClass, result);
						list.add(newInstance);
					}
				System.out.println(list.size());
			}catch(Exception e) {
				throw e;
			}
			return list;
		}

	//resultSet to Vector
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
		
		public Vector settingObjectFieldValueByResultSetInVectorComplexeData(ResultSet result) throws Exception{
			Vector list = new Vector();
			String[] columnName = this.gettingColumnNameFromBase(result);
			
			while(result.next()) {
				Vector data = new Vector();
				for(int i=0;i<columnName.length;i++) {
					data.add(result.getObject(i+1));
				}
				list.add(data);
			}
			System.out.println(list.size());
			return list;
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
}
