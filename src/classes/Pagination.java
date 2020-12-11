package classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Pagination {
	/*public JSONObject getConfigurationPagination(String database) throws Exception{
		JSONParser jsonParser = new JSONParser();
		JSONObject result = new JSONObject();
		try {
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("pagination.json");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
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
	
	public String pagination(String database,String sql) throws Exception{
		String limitSql = "";
		
		JSONObject data = this.getConfigurationPagination(database);
		PaginationStructure pagination = new PaginationStructure(data);
		
		int limit = pagination.getLimit();
		
		if(database.equalsIgnoreCase("postgresql")) {
			sql += " limit "+limit;
		}
		else if(database.equalsIgnoreCase("oracle")) {
			sql += " where rownum <="+limit;
		}
		
		return sql;
	}*/
	
	public String paginationLimit(String database,String sql,int beginRow,int limit) throws Exception{
		System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppp "+database);
		if(database.equalsIgnoreCase("postgresql")) {
			System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppp");
			sql += " OFFSET "+beginRow+" ROWS FETCH NEXT "+limit+" ROWS ONLY";
		}
		else if(database.equalsIgnoreCase("oracle")) {
			sql += " rownum >="+beginRow+" and  rownum <="+(beginRow+limit);
		}
		System.out.println(sql);
		return sql;
	}
}
