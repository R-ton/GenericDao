package service;

import java.util.Vector;

import service.GeneralFunction;

public class SqlMaker {
	public String insert(Object object) throws Exception {
		String sql = "insert into ";
		GeneralFunction generalFunction = new GeneralFunction();
		Class objectClass = object.getClass();
		String objectName = objectClass.getSimpleName();
		String[] fieldsName = generalFunction.gettingFieldsName(objectClass);
		Vector data = generalFunction.gettersMethodInvoke(object,fieldsName);
		sql += objectName;
		sql += " values (";
		for(int i=0;i<data.size();i++){
			if(i<data.size()-1) {
				sql += "'"+data.get(i) +"',";
			}
			else {
				sql += "'"+data.get(i)+"'";
			}
		}
		sql+= ")";
		System.out.println(sql);
		return sql;
	}
	
	public String insertWithTableName(Object object,String tableName) throws Exception {
		String sql = "insert into ";
		GeneralFunction generalFunction = new GeneralFunction();
		Class objectClass = object.getClass();
		String[] fieldsName = generalFunction.gettingFieldsName(objectClass);
		Vector data = generalFunction.gettersMethodInvoke(object,fieldsName);
		sql += tableName;
		sql += " values (";
		for(int i=0;i<data.size();i++){
			if(i<data.size()-1) {
				sql += "'"+data.get(i) +"',";
			}
			else {
				sql += "'"+data.get(i)+"'";
			}
		}
		sql+= ")";
		System.out.println(sql);
		return sql;
	}
	
	public String update(Object object,Vector<String> columnToUpdate,Vector newData,Vector<String> columns,Vector data ){
		String sql ="update ";
		Class objectClass = object.getClass();
		String objectName = objectClass.getSimpleName();
		sql+=objectName+" set ";
		
		int columnToUpdateSize=columnToUpdate.size();
		int columnSize=columns.size();
		
		for(int i=0;i<columnToUpdateSize;i++) {
			String column = columnToUpdate.get(i);
			String newDataValue = newData.get(i).toString();
			
			if(i<columnToUpdateSize-1) {
				sql += column +"='"+newDataValue+"'," ;
			}else {
				sql += column +"='"+newDataValue+"'" ;
			}
		}
		
		sql+= " where ";
		
		for(int i=0;i<columnSize;i++) {
			String column = columns.get(i);
			String dataValue = data.get(i).toString();
			
			if(i<columnSize-1) {
				sql += column +"='"+dataValue+"'," ;
			}else {
				sql += column +"='"+dataValue+"'" ;
			}
		}
		System.out.println(sql);
		return sql;
	}
	
	public String updatingStock(double added,String idProductComponent) {
		String sql = "update ProductComponent set inStock = instock +"+added+" where idProductComponent = '"+idProductComponent+"'";
		return sql;
	}
}
