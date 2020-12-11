package dao.generalized.update;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import dao.dbconnect.DBconnect;
import model.base.Table;

public class Update {
	  /*String getClassName(Class<? extends Object> c){
	        String name = c.getName();
	        int lastIndex = name.lastIndexOf(".");
	        String className = name.substring(lastIndex+1);
	        return className;
	    }
	    String getPreparedQuery(Object obj,String id){
	        String query = "";
	        Class<? extends Object> c = obj.getClass();
	        String className = this.getClassName(c);
	        Field[] fields = c.getDeclaredFields();
	        query += "update "+className+" SET ";
	        for(int i=0;i<fields.length;i++){
	            if(i == fields.length-1){
	                query += fields[i].getName()+"=? where id"+className+" like ?";
	                break;
	            }
	            if(i == 0){
//	            	query+=className+"Sequence.nextval,";
	            }else{
	            query += fields[i].getName()+" =?,";
	            }
	        }
	        return query;
	    }
	    public void update(Object obj,String id,DBconnect d) throws Exception{
	    	Connection c=d.getConnect();
	    	PreparedStatement ps = null;
			try{
				String preparedQuery = this.getPreparedQuery(obj,id);
				
	            ps = c.prepareStatement(preparedQuery);
				Class<? extends Object> cl = obj.getClass();
				String className=this.getClassName(cl);
				Field[] fields = cl.getDeclaredFields();
				int i=0,j=0;
				for(i=1;i<fields.length;i++){
					
					String fieldName = fields[i].getName();
					String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					System.out.println(methodName);
					Method method = cl.getMethod(methodName);
					if(method.getName().equalsIgnoreCase("get"+className)){
						
					}else{
					ps.setObject(i, method.invoke(obj));
					}
					j++;
				}
				
				ps.setObject(i, id);
				System.out.println(preparedQuery);
				ps.executeUpdate();
			} catch(Exception e){
				e.printStackTrace();
				throw e;
			} finally{
				if(ps != null){
					ps.close();
				}
//				if(c != null){
//					c.close();
//				}
			}
	    }*/
	    
	    private String query;
	    private ArrayList<String> value = new ArrayList();
		private ArrayList<String> where =new ArrayList();
		
		public String getQuery() {
			return query;
		}

		public void setQuery(String query) {
			this.query = query;
		}

		public ArrayList<String> getWhere() {
			return where;
		}

		public void setWhere(ArrayList<String> where) {
			this.where = where;
		}

		String getClassName(Class<? extends Object> c){
	        String name = c.getName();
	        int lastIndex = name.lastIndexOf(".");
	        String className = name.substring(lastIndex+1);
	        return className;
		}
		  
		public String getPreparedQuery(Object obj){
	        String query = "";
	        Class<? extends Object> c = obj.getClass();
	        String className = this.getClassName(c);
	        Table table = null;
	        
	        if(c.isAnnotationPresent(Table.class)) {
	        	table = c.getDeclaredAnnotation(Table.class);
	        	className = table.name();
	        }
	        
	        query += "update "+className+" set " ;
	        this.query = query;
	        return query;
		}
		
		public void addValue(String column,String value) {
			String sql = column+"='"+value+"";
			this.value.add(sql);
		}
		
		private void setSqlValue() throws Exception{
			String query =this.query;
			for(int i=0;i<this.value.size();i++) {
				if(i!=this.value.size()-1) {
					query+=value.get(i)+" , ";
				}
				else {
					query+=value.get(i);
				}
			}
			
			this.query = query;
		}
		
		public void where(String column,String operation,String value) throws Exception{
			 String where = column +operation+"'"+value+"'";
			 if(this.where.size()!=0) {
				 String data = this.where.get(this.where.size()-1);
				 System.out.println(data);
				 if(!data.equalsIgnoreCase("and") && !data.equalsIgnoreCase("or")) {
					 throw new Exception("Requete invalide . AND ou OR requis pour deux sous requetes");
				 }
				 this.where.add(where);
			 }
			 else {
				 this.where.add(where);
			 }
			 System.out.println(this.where.size() +"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		}
		
		public void ifSqlValid() throws Exception{
			 int last = this.where.size();
			 if(last!=0) {
				 String before = this.where.get(last-1);
				 if(before.equalsIgnoreCase("OR") || before.equalsIgnoreCase("AND")) {
					 throw new Exception("Requête SQL non valid . '"+before.toUpperCase()+"' ne doit pas terminer une requête ou nécessite une colonne et un valeur à rechercher");
				 }
			 }
		}
		
		public void generatingWhere()throws Exception {
			 String sql = this.query;
			 System.out.println(this.where.size()+"----------[]]]]]]]]]]]]]]]]]]]]]]]]]]");
			 try {
				 this.ifSqlValid();
				 for(int i=0;i<this.where.size();i++) {
					 if(i == 0) {
						 sql+= " where ";
					 }
					 String add = this.where.get(i);
					 sql += add+" ";
					 System.out.println("+============================="+sql);
				 }
			 }
			 catch(Exception e) {
				 throw e;
			 }
			 
			 this.query = sql;
		}
		
		public void and() throws Exception{
			 int last = this.where.size();
			 if(last!=0) {
				 String before = this.where.get(last-1);
				 System.out.println(before);
				 this.where.add("and");
			 }
			 else {
				 throw new Exception("Operation 'AND' non valide . 'AND' doit etre appelé après qu'une recherche ait déja été effectuée avec 'WHERE'");
			 }
		 }
		
		public void or() throws Exception{
			 int last = this.where.size();
			 if(last!=0) {
				 String before = this.where.get(last-1);
				 System.out.println(before);
				 this.where.add("or");
			 }
			 else {
				 throw new Exception("Operation 'OR' non valide . 'OR' doit etre appelé après qu'une recherche ait déja été effectuée avec 'WHERE'");
			 }
		 }
}
