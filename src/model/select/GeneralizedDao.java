package model.select;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import classes.Cache;
import classes.Pagination;
import configuration.BasicConfig;
import dao.dbconnect.DBconnect;
import dao.generalized.create.Insert;
import dao.generalized.delete.Delete;
import dao.generalized.update.Update;
import model.base.ModelReflection;
import model.base.ResultSetList;

public class GeneralizedDao<T> {
	DBconnect d;
	private String query;
	private final Delete delete = new Delete();
	private final Update update = new Update();
	private final Insert insert= new Insert();
	private ArrayList<String> where = new ArrayList();
	private ArrayList<String> group = new ArrayList();
	private ArrayList<String> order = new ArrayList();
	public static final int NONE=0;
	public static final int BEGIN = 1;
	public static final int BETWEEN=2;
	public static final int LAST=3;
	
	public DBconnect getD() {
		return d;
	}
	public void setD(DBconnect d) {
		this.d = d;
	}
	
	 String getClassName(Class<?> c){
	        String name = c.getName();
	        int lastIndex = name.lastIndexOf(".");
	        String className = name.substring(lastIndex+1);
	        return className;
	    }
	 
	 public String init(Object obj) {
		 ModelReflection reflect = ModelReflection.getInstance();
		 String query = reflect.getSql(obj);
		 this.query = query;
		 return query;
	 }
	 
	 public void where(String column,String operation,String value) {
		 String where = column +operation+"'"+value+"'";
		 this.where.add(where);
	 }
	 
	 private void generatingWhere()throws Exception {
		 String sql = this.query;
		 try {
			 this.ifSqlValid();
			 for(int i=0;i<this.where.size();i++) {
				 if(i == 0) {
					 sql+= " where ";
				 }
				 String add = this.where.get(i);
				 sql += add+" ";
			 }
		 }
		 catch(Exception e) {
			 throw e;
		 }
		 
		 this.query = sql;
	 }
	 
	 public void like(String column,String value,int between) throws Exception{
		 String where = column +" like '";
		 int last = this.where.size();
		 if(last==0) {
			 if(between == this.BEGIN) {
				 where += value+"%";
			 }
			 else if(between == this.BETWEEN) {
				 where += "%"+value+"%";
			 }
			 else if(between == this.LAST) {
				 where += "%"+value;
			 }
			 else if(between == this.NONE) {
				 where += value; 
			 }
			 else {
				 Class c =this.getClass();
				 throw new Exception("Le dernier argument doit suivre la structure "+c.getName()+".BEGIN ="+this.BEGIN+" or .BETWEEN ="+this.BETWEEN+" or .LAST="+this.LAST+" or .NONE="+this.NONE);
			 }
			 where+="'";
			 this.where.add(where);
		 }
		 else {
			 throw new Exception ("SQL non valid . OR ou AND doit etre present avant like");
		 }
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
	 
	 public void groupBy(String column) throws Exception{
		 this.group.add(column);
	 }
	 
	 private void generattingGroupBy() {
		 String sql = this.query;
		 if(this.group.size()!=0) {
			 sql +=" group by ";
			 for(int i=0;i<this.group.size();i++) {
				 if(i!=this.group.size()-1) {
					 sql += group.get(i)+",";
				 }
				 else {
					 sql += group.get(i);
				 }
			 }
		 }
		 this.query = sql;
	 }
	 
	 public void orderBy(String column)throws Exception {
		 this.order.add(column);
	 }
	 
	 private void generattingOrderBy() {
		 String sql = this.query;
		 if(this.order.size()!=0) {
			 sql +=" order by ";
			 for(int i=0;i<this.order.size();i++) {
				 if(i!=this.order.size()-1) {
					 sql += order.get(i)+",";
				 }
				 else {
					 sql += order.get(i);
				 }
			 }
		 }
		 this.query = sql;
	 }
	 
	 private void ifSqlValid() throws Exception{
		 int last = this.where.size();
		 if(last!=0) {
			 String before = this.where.get(last-1);
			 if(before.equalsIgnoreCase("OR")|| before.equalsIgnoreCase("AND")) {
				 throw new Exception("Requête SQL non valid . '"+before.toUpperCase()+"' ne doit pas terminer une requête ou nécessite une colonne et un valeur à rechercher");
			 }
		 }
	 }
	 
	 public List<T> selectAnnotation(DBconnect d,Object obj,boolean close,int putCache) throws Exception{
		 	this.setD(d);
		 	Cache cache = new Cache();
			Connection c = d.getConnect();
			List<T> listeV = new LinkedList<T>();
			PreparedStatement ps = null;
			ResultSet rs=null;
			ModelReflection reflect = ModelReflection.getInstance();
			String query = reflect.getSql(obj);
			this.query = query;
			try {
				String database = d.getDatabase();
				if(database.equals("oracle")) {
					if(this.where.size()!=0) {
						//this.and();
					}
					//this.where("rownum",">=","0");
					//this.and();
					//this.where("rownum","<=","1");
					this.generatingWhere();
					this.generattingGroupBy();
					this.generattingOrderBy();
				}
				else if(database.equalsIgnoreCase("postgresql")) {
					this.generatingWhere();
					this.generattingGroupBy();
					this.generattingOrderBy();
					Pagination pagination = new Pagination();
					//this.query = pagination.paginationLimit(database, this.query, 0, 1);
					
				}
			}catch(Exception e) {
				throw e;
			}
			if(cache.isExist(this.query)) {
				System.out.println("=========================================ao anaty cache");
				return cache.data.get(query);
			}
			try {
				System.out.println("=========================================tsy ao");
				boolean fieldnotexist=false;
				System.out.println(this.query);
				ps = c.prepareStatement(this.query);
				
				try {
					rs = ps.executeQuery();
				}catch(SQLException sql) {
					/*String ms = sql.getMessage();
					int indexquote = ms.indexOf("\"");
					char first = ms.charAt(indexquote);
					String firstquote = String.valueOf(first);
					int begin = indexquote + 1;
					while(begin < ms.length()) {
						char attempted = ms.charAt(begin);
						String attemptedstring = String.valueOf(attempted);
						if(attemptedstring.equalsIgnoreCase("\"")) {
							break;
						}
						begin++;
					}
					System.out.println(ms);
					System.out.println("begin"+begin);
					System.out.println("end"+indexquote);
					ms = ms.substring(indexquote+1,begin);//+ ms.substring(begin);
					Class<?> objClass = obj.getClass();
					String tableName = "";
					if(reflect.getAnnotation()!=null) {
						tableName = reflect.getAnnotation().name();
					}
					else {
						tableName = objClass.getSimpleName();
					}
					
					throw new Exception("La colonne "+ms +" est inexistante dans la table "+tableName);*/
					throw sql;
				}
				HashMap<String, Method[]> m = reflect.getFieldSelectFromClass(obj);
				Field[] fields= reflect.toInvoke(obj);
				
				Object[] value = reflect.getValues();
				Method[] meth = m.get("main");
				Method resultSetMethod = null;
				while(rs.next()) {
					Object o = obj.getClass().newInstance();
					for(int i=0;i<fields.length;i++) {
						//TODO verifier type si liste
						// cas liste appler method du derivation dans le HashMap
						//creer instance du derivation
						//creer instance du liste de derivation
						//affecter dans la classe principale
						
						String fieldName = fields[i].getName();
//						System.out.println(fieldName);
						String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
						String resultSetName = "get" + this.getClassName(fields[i].getType()).substring(0,1).toUpperCase()+this.getClassName(fields[i].getType()).substring(1);
						System.out.println(resultSetName);
						System.out.println("------------------------------------------gidrooooooooooooooooo "+resultSetName);
						if(resultSetName.equalsIgnoreCase("getList")) {
							resultSetMethod = ResultSetList.class.getMethod(resultSetName, String.class);
							System.out.println("------------------------------------------gidrooooooooooooooooo");
							//resultSetMethod = null;
							//resultSetName = null;
						}else {
							resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
						}
						
							System.out.println("HashValue\t"+reflect.getHash().keySet().contains((fields[i].getName())));
//							Method fieldMethod = meth[i];
							//TODO maka anle cl'e a partir valeur hashmap
							System.out.println("FieldMethodName\t"+fieldMethodName);
						Method fieldMethod = obj.getClass().getMethod(fieldMethodName, fields[i].getType());

						if(resultSetMethod==null || resultSetName==null) {fieldnotexist=true;continue;}
						try {
							System.out.println("value\t"+value[i]);
							fieldMethod.invoke(o, resultSetMethod.invoke(rs, reflect.getHash().get(fields[i])));
							}catch(InvocationTargetException e) {
								//fieldnotexist=true;
//								continue;
								throw e;
							}catch(NullPointerException nullex) {
//								continue;
								throw nullex;
							}
					}
					listeV.add((T)o);
					
				}
				if(putCache !=0) {
					Cache.data.put(query, listeV);
				}
			}catch(Exception e) {
				throw e;
			}finally {
				if(rs != null){
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(c != null) {
					c.close();
				}
			}
			return listeV;
		
	 }
	
/*===========================================================================DELETE========================================================*/	
	public void delete(Object obj,DBconnect db) throws Exception{
		Connection connection = db.getConnect();
		if(connection.isClosed()) {
			BasicConfig basicConfig = new BasicConfig();
			connection = basicConfig.createConnection(db.getDatabase());
		}
		PreparedStatement statement = null;
		try {
			String sql = this.delete.getPreparedQuery(obj);
			this.delete.generatingWhere();
			statement = connection.prepareStatement(this.delete.getQuery());
			statement.executeUpdate();
		}catch(Exception e) {	
			throw e;
		}
		finally {
			if(statement!=null) {
				statement.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
		}
	}

	
	public void deleteWhere(String column,String operation,String value) throws Exception{
		this.delete.where(column, operation, value);
	}
	
	public void deleteAnd()throws Exception {
		this.delete.and();
	}
	
	public void deleteOr() throws Exception{
		this.delete.or();
	}

/*=====================================================================================UPDATE====================================================*/
	public void update(Object obj,DBconnect db) throws Exception{
		Connection connection = db.getConnect();
		if(connection.isClosed()) {
			BasicConfig basicConfig = new BasicConfig();
			connection = basicConfig.createConnection(db.getDatabase());
		}
		PreparedStatement statement = null;
		try {
			String sql = this.update.getPreparedQuery(obj);
			this.update.generatingWhere();
			statement = connection.prepareStatement(this.update.getQuery());
			statement.executeUpdate();
		}catch(Exception e) {	
			throw e;
		}
		finally {
			if(statement!=null) {
				statement.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
		}
	}

	
	public void updateWhere(String column,String operation,String value) throws Exception{
		this.update.where(column, operation, value);
	}
	
	public void updateAnd()throws Exception {
		this.update.and();
	}
	
	public void updateOr() throws Exception{
		this.update.or();
	}

	
	
	/*public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}*/
}
