package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import service.SqlMaker;

public class BaseDao {
	
	public static Connection createConnectionToSqlite()throws Exception {
		Connection sqlite;
		
		try{
			Class.forName("org.sqlite.JDBC");
			
			String url = "jdbc:sqlite:D:\\Info_S5\\Mr_Tovo\\Projects\\superMarket\\base\\Base.db";	
			sqlite = DriverManager.getConnection(url);
			System.out.println("Connection completed...");
			return sqlite;
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public static Connection createConnectionToOracleDB()throws Exception {
		Connection oracleDB;
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "user";
			String password = "password";
			
			oracleDB = DriverManager.getConnection(url, user,password);
			return oracleDB;
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public static Connection getConnection() {
		Connection connection = null; 
		try {
	      Class.forName("org.postgresql.Driver");
		  System.out.println("Driver O.K.");
		  
		  String url = "jdbc:postgresql://localhost:5432/andry";
		  String user = "bobo";
		  String passwd = "1234";
		
		  connection = DriverManager.getConnection(url, user, passwd);
		  System.out.println("Connexion effective !");         
		         
	   } catch (Exception e) {
	      e.printStackTrace();
	   }   
	   return connection;
	}
	
	public String getObjectAutoIncremmentTableName(String tableName,Connection connection) throws Exception{
		boolean create = false;
		if(connection == null) {
			BaseDao baseDao = new BaseDao();
			connection = baseDao.createConnectionToSqlite();
			create = true;
		}
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM "+tableName;
		ResultSet result = statement.executeQuery(sql);
		String autoIncrement = tableName;
		int count = 1;
		
		try {
			while(result.next()) {		
				count++;
			}
			autoIncrement +="-"+count;
			System.out.println(autoIncrement);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			result.close();
			statement.close();
			if(create) {
				connection.close();
			}
		}
		return autoIncrement;
	}
	
	public String getObjectAutoIncremment(Object object,Connection connection) throws Exception{
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
		String autoIncrement = tableName;
		int count = 1;
		
		try {
			while(result.next()) {		
				count++;
			}
			autoIncrement +="-"+count;
			System.out.println(autoIncrement);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			result.close();
			statement.close();
			if(create) {
				connection.close();
			}
		}
		return autoIncrement;
	}
	
	public int executeInsert(Object object,Connection connection)throws Exception{
		SqlMaker sqlMaker = new SqlMaker();
		if(connection == null){
			connection = this.createConnectionToSqlite();
		}
		Statement statement = connection.createStatement();
		try {
	        String sql = sqlMaker.insert(object);
	        System.out.println(sql);
			statement.executeUpdate(sql);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			statement.close();
		}
		return 0;
	}
	
	public int executeInsertWithTableName(Object object,String tableName,Connection connection)throws Exception{
		SqlMaker sqlMaker = new SqlMaker();
		if(connection == null){
			connection = this.createConnectionToSqlite();
		}
		Statement statement = connection.createStatement();
		try {
	        String sql = sqlMaker.insertWithTableName(object,tableName);
	        System.out.println(sql);
			statement.executeUpdate(sql);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			statement.close();
		}
		return 0;
	}
	
	public int executeInsertSql(String sql,Connection connection)throws Exception{
		if(connection == null){
			connection = this.createConnectionToSqlite();
		}
		Statement statement = connection.createStatement();
		try {
			statement.executeUpdate(sql);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			statement.close();
		}
		return 0;
	}
	
	public int executeUpdate(String sql,Connection connection)throws Exception{
		if(connection == null){
			connection = this.createConnectionToSqlite();
		}
		Statement statement = connection.createStatement();
		try {
			statement.executeUpdate(sql);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			statement.close();
		}
		return 0;
	}
}
