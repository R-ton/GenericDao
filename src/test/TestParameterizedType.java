package test;

import java.awt.List;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import model.example.*;;
public class TestParameterizedType {

	public static <T> void main(String[] args) {
		// TODO Auto-generated method stub
			LinkedList<Department> dept = new LinkedList<Department>();
			if(dept instanceof Collection) {
				System.out.println("is parameterized true");
			}
		
		HashMap<String,String> hash = new HashMap<String,String>();
		hash.put("1", "a");
		hash.put("2", "b");
		Set<String> value=hash.keySet();
		System.out.println(value);
	}

}
