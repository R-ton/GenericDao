package test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import model.base.ModelReflection;
import model.base.Table;
import model.example.Employee;

public class TestAnnotation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Employee emp = new Employee();
		Class<?> c = emp.getClass();
		Table p = c.getDeclaredAnnotation(Table.class);
		   Field[] f = c.getDeclaredFields();
		   List<Field> identifiedfield = new ArrayList<Field>();
		   for(int i=0;i<f.length;i++) {
			   if(f[i].isAnnotationPresent(Table.class) && f[i] != null) {
				  System.out.println(f[i].getName());
				  System.out.println(f[i].getAnnotation(Table.class).column());
			   }
		   }
		System.out.println(p.name());
		
		String sql = ModelReflection.getInstance().getSql(emp);
		System.out.println(sql);
	}

}
