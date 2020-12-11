package dao.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;

import configuration.BasicConfig;

public class DBconnect {
	String database;
	DriverManager m;
	Connection connect;
	
	String usr,pwd;
	public String getUsr(){
		return this.usr;
	}
	public String getPwd(){
		return this.pwd;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public DBconnect(String user,String password)throws Exception{
		try{
			// String url="jdbc:postgresql://localhost:5432/oracle";
			String url="jdbc:oracle:thin:@localhost:1521:xe";
			
			this.usr=user;
			this.pwd=password;
			// String user="postgres";
			// String pwd="1234";
			// this.m=new DriverManager();
			this.connect=DriverManager.getConnection(url,this.usr,this.pwd);
			this.connect.setAutoCommit(false);
//			this.stat=this.connect.createStatement();
			if (this.connect != null ) {
				System.out.println("DB connected");
			}else {
				System.out.println("Failed to connect");
			}
		}catch(Exception e){
			//afficher message erreur
			throw e;
		}
	}
	
	public DBconnect(String database)throws Exception{
		try{
			BasicConfig basicConfig = new BasicConfig();
			this.connect= basicConfig.createConnection(database);
			this.connect.setAutoCommit(false);
			this.database = database;
			if (this.connect == null ) {
				throw new Exception("Failed to connect");
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	public Connection getConnect() throws Exception{
		return this.connect;
	}
	public void closeConnect() throws Exception{
		if(!this.connect.isClosed()) {
			this.connect.close();
		}
		
	}
}
