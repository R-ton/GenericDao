package classes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;

import string.StringFunction;

public class DatabaseStructure {
	String driver;
	String url;
	String address;
	String port;
	String user;
	String password;
	String database;
	
	public DatabaseStructure() {
		super();
	}
		
	public DatabaseStructure(JSONObject data) throws Exception{
		super();
		BasicReflect basicReflect = new BasicReflect();
		basicReflect.setMethod(this, data);
	}
	
	public DatabaseStructure(String driver, String url, String address, String port, String user, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.address = address;
		this.port = port;
		this.user = user;
		this.password = password;
	}
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	
}
