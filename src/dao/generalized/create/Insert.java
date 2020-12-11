package dao.generalized.create;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.dbconnect.DBconnect;

public class Insert {

    String getClassName(Class<? extends Object> c){
        String name = c.getName();
        int lastIndex = name.lastIndexOf(".");
        String className = name.substring(lastIndex+1);
        return className;
    }
    String getPreparedQuery(Object obj){
        String query = "";
        Class<? extends Object> c = obj.getClass();
        String className = this.getClassName(c);
        Field[] fields = c.getDeclaredFields();
        query += "insert into "+className+" values(";
        for(int i=0;i<fields.length;i++){
            if(i == fields.length-1){
                query += "?)";
                break;
            }
            if(i == 0){
            	query+=className+"Sequence.nextval,";
            }else{
            query += "?,";
            }
        }
        return query;
    }
    String getPreparedQuery(Object obj,Object selling){
        String query = "";
        Class<? extends Object> c = obj.getClass();
        Class<? extends Object> sell = selling.getClass();
        String className = this.getClassName(c);
        String classSelling = this.getClassName(sell);
        Field[] fields = c.getDeclaredFields();
        query += "insert into "+className+" values(";
        for(int i=0;i<fields.length;i++){
            if(i == fields.length-1){
                query += "?)";
                break;
            }
            if(i == 0){
            	query+=className+"Sequence.nextval";
            }else if(i == 1){
            	query+=classSelling+"Sequence.nextval";
            }
            query += "?,";
        }
        return query;
    }
	public int insert(Object obj, DBconnect d)throws Exception{
		Connection c=d.getConnect();
//		Connection c=null;
//		if(d == null){
//			try{
//				d=new DBconnect("SellCarProject","1234");
//				c=d.getConnect();
//			}catch(Exception e){
//				d.closeConnect();
//				throw e;
//			}
//		}
		int reponse = 0;
		PreparedStatement ps = null;
		try{
			String preparedQuery = this.getPreparedQuery(obj);
			System.out.println(preparedQuery);
            ps = c.prepareStatement(preparedQuery);
			Class<? extends Object> cl = obj.getClass();
			String className=this.getClassName(cl);
			Field[] fields = cl.getDeclaredFields();
			for(int i=1;i<fields.length;i++){
				
				String fieldName = fields[i].getName();
				String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
				System.out.println(methodName);
				Method method = cl.getMethod(methodName);
				if(method.getName().equalsIgnoreCase("get"+className)){
					
				}else{
				ps.setObject(i, method.invoke(obj));
				}
			}
			reponse = ps.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally{
			if(ps != null){
				ps.close();
			}
//			if(c != null){
//				c.close();
//			}
		}
		return reponse;
	}
	public int insert(Object obj,Object obj2, DBconnect d)throws Exception{
		Connection c=null;
		if(d == null){
			try{
				d=new DBconnect("SellCarProject","1234");
				c=d.getConnect();
			}catch(Exception e){
				d.closeConnect();
				throw e;
			}
		}
		int reponse = 0;
		PreparedStatement ps = null;
		try{
			String preparedQuery = this.getPreparedQuery(obj,obj2);
			System.out.println(preparedQuery);
            ps = c.prepareStatement(preparedQuery);
			Class<? extends Object> cl = obj.getClass();
			Field[] fields = cl.getDeclaredFields();
			for(int i=fields.length;i>2;i++){
				String fieldName = fields[i].getName();
				String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
				Method method = cl.getMethod(methodName);
				ps.setObject(i-1, method.invoke(obj));
			}
			reponse = ps.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally{
			if(ps != null){
				ps.close();
			}
			if(c != null){
				c.close();
			}
		}
		return reponse;
	}
}
