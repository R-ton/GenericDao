package configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import classes.DatabaseStructure;


public class BasicConfig {

/**
 * 
 * @return
 * @throws Exception
 */
	public static Map<String,String> environment()throws Exception{
		Map<String,String> data  = null;
		
		try {
			data = System.getenv();
		}catch(Exception e) {
			throw e;
		}
		
		return data;
	}
	
/**
 * 	
 * @param database
 * @return
 * @throws Exception
 */
	public JSONObject getConfiguration(String database) throws Exception{
		JSONParser jsonParser = new JSONParser();
		JSONObject result = new JSONObject();
		try {
			
			String link = "database.properties";
			//InputStream input = this.getClass().getResourceAsStream(link);
			BufferedReader buffer = new BufferedReader(new FileReader(link));
			Object json = jsonParser.parse(buffer);
			JSONObject data = (JSONObject) json;
            result = (JSONObject) data.get(database);             
		}catch(Exception e) {
			throw e;
		}
		if(result == null) {
			throw new Exception("Database name doesn't exist . Please check the name of your database in database.json (file configuration).");
		}
		
		return result;
	}
	
/**
 * 
 * @param database
 * @return
 * @throws Exception
 */
	public Connection createConnection(String database) throws Exception{
		Connection connection = null;
		JSONObject data = null; 
		try{
			data = this.getConfiguration(database);
			DatabaseStructure structure = new DatabaseStructure(data);
			String driver = structure.getDriver();
			String address = structure.getAddress();
			String port = structure.getPort();
			String url = structure.getUrl();
			String user = structure.getUser();
			String password = structure.getPassword();
			
			Class.forName(driver);
			
			url += address+":"+port;
			
			if(database.equalsIgnoreCase("oracle")) {
				url += ":xe";
			}
			else if(database.equalsIgnoreCase("postgresql")) {
				String databaseUsed = structure.getDatabase();
				System.out.println(databaseUsed);
				url += "/"+databaseUsed;
			}
			System.out.println(url);
			connection = DriverManager.getConnection(url,user,password);
			System.out.println("connected");
			
		}catch(Exception e) {
			throw e;
		}
		
		
		return connection;
	}
	
}
