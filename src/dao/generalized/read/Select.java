package dao.generalized.read;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import dao.dbconnect.DBconnect;

public class Select<T> {
    String getClassName(Class<?> c){
        String name = c.getName();
        int lastIndex = name.lastIndexOf(".");
        String className = name.substring(lastIndex+1);
        return className;
    }
    public List<T> selectDistinct(DBconnect d,Object obj, String distinct,String othercolumn,String where,boolean close) throws Exception{
    	Connection c=null;
		if(d==null){
			d=new DBconnect("SellCarProject","1234");
			c=d.getConnect();
		}else{
			c=d.getConnect();
		}
		List<T> listeV = new LinkedList<T>();
		PreparedStatement ps = null;
		ResultSet rs=null;
		try{
			Class<?> cl = obj.getClass();
			String tableName = this.getClassName(cl)+" ";
			String query = "select distinct("+distinct+"),"+othercolumn+" from "+tableName;
			if(othercolumn == null) {
				query = "select distinct("+distinct+") from "+tableName;
			}
			if(where != null){
				query += where;
			}
			System.out.println(query);
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			String[] strfi = null;
			if(othercolumn == null) {
				strfi = new String[1];
				strfi[0]=distinct;
			}else {
				strfi = new String[2];
				strfi[0]=distinct;
				strfi[1]=othercolumn;
			}
			
			while(rs.next()){
				Object o = cl.newInstance();
				for(int i=0;i<strfi.length;i++){
					Field fields = cl.getDeclaredField(strfi[i]);
					
//					String fieldName = fields[i].getName();
				String fieldName = fields.getName();
//					System.out.println(fieldName);
					String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					String resultSetName = "get" + this.getClassName(fields.getType()).substring(0,1).toUpperCase()+this.getClassName(fields.getType()).substring(1);
					Method resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
					// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
					Method fieldMethod = cl.getMethod(fieldMethodName, fields.getType());
					fieldMethod.invoke(o, resultSetMethod.invoke(rs, fieldName));
				}
				listeV.add((T) o);
			}
			
		} catch(Exception e){
			
			throw e;
		} finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(close == true) {
				if(c != null){
					c.close();
				}
			}
		}
		return listeV;
    }
    public List<T> select(DBconnect d, String mainsql, Object objmaintainer,String where,boolean close) throws Exception{
		Connection c=null;
		if(d==null){
			d=new DBconnect("SellCarProject","1234");
			c=d.getConnect();
		}else{
			c=d.getConnect();
		}
		List<T> listeV = new LinkedList<T>();
		PreparedStatement ps = null;
		ResultSet rs=null;
		try{
			Class<?> cl = objmaintainer.getClass();
			String query = mainsql;
			if(where != null){
				query += where;
			}
			boolean fieldnotexist=false;
			System.out.println(query);
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			Field[] fields = cl.getDeclaredFields();
			
			while(rs.next()){
				Object o = cl.newInstance();
				for(int i=0;i<fields.length;i++){
					String fieldName = fields[i].getName();
					System.out.println(fieldName);
					String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					String resultSetName = "get" + this.getClassName(fields[i].getType()).substring(0,1).toUpperCase()+this.getClassName(fields[i].getType()).substring(1);
					Method resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
					// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
					Method fieldMethod = cl.getMethod(fieldMethodName, fields[i].getType());
					if(resultSetMethod==null || resultSetName==null) {fieldnotexist=true;continue;}
					try {
						fieldMethod.invoke(o, resultSetMethod.invoke(rs, fieldName));
						}catch(InvocationTargetException e) {
							//fieldnotexist=true;
							continue;
						}
				}
				if(fieldnotexist) continue;
				listeV.add((T) o);
			}
			
		} catch(Exception e){
			
			throw e;
		} finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(close == true) {
				if(c != null){
					c.close();
				}
			}
		}
		return listeV;
    }
	public List<T> select(DBconnect d, Object obj, String where,boolean close)throws Exception{
		Connection c=null;
		if(d==null){
			d=new DBconnect("GestionPlace","1234");
			c=d.getConnect();
		}else{
			c=d.getConnect();
		}
		List<T> listeV = new LinkedList<T>();
		PreparedStatement ps = null;
		ResultSet rs=null;
		try{
			Class<?> cl = obj.getClass();
			String tableName = this.getClassName(cl)+" ";
			String query = "select * from "+tableName;
			if(where != null){
				query += where;
			}
			boolean fieldnotexist=false;
			System.out.println(query);
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			Field[] fields = cl.getDeclaredFields();
			
			while(rs.next()){
				Object o = cl.newInstance();
				for(int i=0;i<fields.length;i++){
					String fieldName = fields[i].getName();
					System.out.println(fieldName);
					String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					String resultSetName = "get" + this.getClassName(fields[i].getType()).substring(0,1).toUpperCase()+this.getClassName(fields[i].getType()).substring(1);
					Method resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
					// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
					Method fieldMethod = cl.getMethod(fieldMethodName, fields[i].getType());
					if(resultSetMethod==null || resultSetName==null) {fieldnotexist=true;continue;}
					try {
						fieldMethod.invoke(o, resultSetMethod.invoke(rs, fieldName));
						}catch(InvocationTargetException e) {
							//fieldnotexist=true;
							continue;
						}
				}
				if(fieldnotexist) break;
				listeV.add((T) o);
			}
			
		} catch(Exception e){
			
			throw e;
		} finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(close == true) {
				if(c != null){
					c.close();
				}
			}
		}
		return listeV;
	}
	public Object[] selectWithoutInterrupt(DBconnect d, Object obj, String where)throws Exception{
		Connection c=null;
		if(d==null){
			d=new DBconnect("SellCarProject","1234");
			c=d.getConnect();
		}else{
			c=d.getConnect();
		}
		Object[] liste = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		try{
			Class<?> cl = obj.getClass();
			String tableName = this.getClassName(cl)+" ";
			String query = "select * from "+tableName;
			if(where != null){
				query += where;
			}
			System.out.println(query);
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			Field[] fields = cl.getDeclaredFields();
			Vector<Object> listeV = new Vector<Object>();
			while(rs.next()){
				Object o = cl.newInstance();
				for(int i=0;i<fields.length;i++){
					String fieldName = fields[i].getName();
					System.out.println(fieldName);
					String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					String resultSetName = "get" + this.getClassName(fields[i].getType()).substring(0,1).toUpperCase()+this.getClassName(fields[i].getType()).substring(1);
					Method resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
					// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
					Method fieldMethod = cl.getMethod(fieldMethodName, fields[i].getType());
					
					fieldMethod.invoke(o, resultSetMethod.invoke(rs, fieldName));
				}
				listeV.add(o);
			}
			liste = listeV.toArray();
		} catch(Exception e){
			if(c != null){
				c.close();
			}
			throw e;
		} finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
//			if(c != null){
//				c.close();
//			}
		}
		return liste;
	}
	
	public List<T> selectWithSuper(DBconnect d, Object obj, String where, boolean close)throws Exception{
		Connection c=null;
		if(d==null){
			d=new DBconnect("SellCarProject","1234");
			c=d.getConnect();
		}else{
			c=d.getConnect();
		}
		List<T> listeV = new LinkedList<T>();
		PreparedStatement ps = null;
		ResultSet rs=null;
		try{
			Class<?> cl = obj.getClass();
			Class<?> sup=cl.getSuperclass();
			String tableName = this.getClassName(cl)+" ";
			String query = "select * from "+tableName;
			if(where != null){
				query += where;
			}
			boolean fieldnotexist=false;
			System.out.println(query);
			ps = c.prepareStatement(query);
			rs = ps.executeQuery();
			Field[] fields = cl.getDeclaredFields();
			Field[] fieldsuper=sup.getDeclaredFields();
			while(rs.next()){
				Object o = cl.newInstance();
				for(int i=0;i<fields.length;i++){
					String fieldName = fields[i].getName();
					System.out.println(fieldName);
					String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					String resultSetName = "get" + this.getClassName(fields[i].getType()).substring(0,1).toUpperCase()+this.getClassName(fields[i].getType()).substring(1);
					Method resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
					// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
					Method fieldMethod = cl.getMethod(fieldMethodName, fields[i].getType());
					if(resultSetMethod==null || resultSetName==null) {fieldnotexist=true;break;}
					try {
					fieldMethod.invoke(o, resultSetMethod.invoke(rs, fieldName));
					}catch(InvocationTargetException e) {
						//fieldnotexist=true;
						break;
					}
					for(int j=0;j<fieldsuper.length;j++){
						String fieldName1 = fieldsuper[j].getName();
						System.out.println(fieldName1);
						String fieldMethodName1 = "set"+fieldName1.substring(0,1).toUpperCase()+fieldName1.substring(1);
						String resultSetName1 = "get" + this.getClassName(fieldsuper[j].getType()).substring(0,1).toUpperCase()+this.getClassName(fieldsuper[j].getType()).substring(1);
						Method resultSetMethod1 = ResultSet.class.getMethod(resultSetName1,String.class);
						// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
						Method fieldMethod1 = cl.getMethod(fieldMethodName1, fieldsuper[j].getType());
						System.out.println(fieldMethod1.getName());
						fieldMethod1.invoke(o, resultSetMethod1.invoke(rs, fieldName1));
					}
				}
				if(fieldnotexist) break;
				listeV.add((T) o);
//				for(int j=0;j<fieldsuper.length;j++){
//					String fieldName = fieldsuper[j].getName();
//					System.out.println(fieldName);
//					String fieldMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
//					String resultSetName = "get" + this.getClassName(fieldsuper[j].getType()).substring(0,1).toUpperCase()+this.getClassName(fieldsuper[j].getType()).substring(1);
//					Method resultSetMethod = ResultSet.class.getMethod(resultSetName,String.class);
//					// System.out.println("resultat = "+resultSetMethod.invoke(rs,fieldName));
//					Method fieldMethod = cl.getMethod(fieldMethodName, fieldsuper[j].getType());
//					fieldMethod.invoke(sup.getDeclaringClass().newInstance(), resultSetMethod.invoke(rs, fieldName));
//
//				}
//				listeV.add(o2);
			}
			
		} catch(Exception e){
			
			throw e;
		} finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
			if(close == true) {
				if(c != null){
					c.close();
				}
			}
		}
		return listeV;
	}
}
