package classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
	public static HashMap<String,List> data = new HashMap();
	
	public boolean isExist(String sql) {
		if(this.data!=null) {
			Collection values = this.data.values();
					System.out.println(values);
					for (Map.Entry<String, List> entry : this.data.entrySet()) {
						String k = entry.getKey();
						if(k.equalsIgnoreCase(sql)) {
							return true;
						}
			}
			return false;
		}
		return false;
	}
}
