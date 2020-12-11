package main;

import java.sql.Connection;
import java.util.Map;

import configuration.BasicConfig;
import service.ClassStructure;
import tableClass.Departement;

public class ConfigTester {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> environment = BasicConfig.environment();
		//Collection values = environment.values();
		//System.out.println(values);
		/*for (Map.Entry<String, String> entry : environment.entrySet()) {
			String k = entry.getKey();
			String v = entry.getValue();
			System.out.println("Key: " + k + ", Value: " + v);
		}*/
		Connection connection = null;
		BasicConfig basicConfig = new BasicConfig();
		ClassStructure classStructure = new ClassStructure();
		Departement department = new Departement();
		classStructure.gettingStaticField(department);
		try {
			connection = basicConfig.createConnection("postgresql");
			classStructure.simpleSelectSql(connection,department, "select * from Departement");
		}
		catch(Exception e) {
			throw e;
		}finally {
			if(connection!=null) {
				connection.close();
			}
			
		}
		
		
	}

}
