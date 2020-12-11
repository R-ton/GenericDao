package test;

import java.util.List;

import dao.dbconnect.DBconnect;
import model.example.Employee;
import model.select.GeneralizedDao;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DBconnect d = new DBconnect("oracle");
		try {
			GeneralizedDao<Employee> generalized = new GeneralizedDao<Employee>();
			//generalized.where("name","=","Rakoto");
			//generalized.and();
			//generalized.where("name","andry");
			//generalized.orderBy("id");
			
			List<Employee> emp = generalized.selectAnnotation(d, new Employee(),false,1);
			//emp = new GeneralizedDao<Employee>().selectAnnotation(d, new Employee(), null, false,1);
			System.out.print("size:"+emp.size()+" ");
			System.out.println(emp.get(0).getDeptemployee());
			/*for(int i=0;i<emp.size();i++) {
				System.out.println(emp.get(i).getDeptemployee().get(0).getName());
			}*/
			generalized.deleteWhere("id", "=", "7");
			generalized.deleteAnd();
			generalized.deleteWhere("name", "=", "andry");
			generalized.delete(new Employee(),d);
		}catch(Exception e) {
			throw e;
		}finally {
			d.closeConnect();
		}
	}

}
