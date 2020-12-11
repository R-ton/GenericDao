package model.base;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import dao.dbconnect.DBconnect;
import model.select.GeneralizedDao;

public abstract class ResultSetList implements ResultSet{
		Connection conn;
		GeneralizedDao gen;
		
	public List<?> getList(String label) throws Exception{
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
		System.out.println("label"+label);
		Class c = Class.forName(label);
		Object obj =c.newInstance();
//		ModelReflection model = ModelReflection.getInstance();
//		Field[] f = model.toInvoke(o);
//		String sql = model.getSql(o);
		Type t = Type.getType(c);
		List<?> temp = gen.selectAnnotation(gen.getD(), obj, false,0);
		return temp;
	}
}
