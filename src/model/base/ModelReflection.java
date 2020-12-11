package model.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ModelReflection {
		/*------------Pseudo code singleton Java -------------------*/
		//Pourquoi cette methode?
		//volatile -> rehefa instancer le classe de izy rery no instance miasa @ thread rehetra
		//synchronized -> verification de l instance et verrou
		private volatile static ModelReflection single_instance = null;
		protected Class<?> classrelected;
		protected Table annotation;
		private HashMap<Field,String> hash=new HashMap<Field,String>();
		private Object[] values;
		private ModelReflection() {
//			this.methodselect = this.get
		}
		String[] splitvalue = {",","/","-",":"};
		public static ModelReflection getInstance() {
			 if (single_instance == null) 
		        { 
		            // To make thread safe 
		            synchronized (ModelReflection.class) 
		            { 
		                // check again as multiple threads 
		                // can reach above step 
		                if (single_instance==null) 
		                	single_instance = new ModelReflection(); 
		            } 
		        } 
			 return single_instance;
		}
		/*-------------------------------*/
		
	   public String getClassName(Class<?> c){
	        String name = c.getName();
	        int lastIndex = name.lastIndexOf(".");
	        String className = name.substring(lastIndex+1);
	        return className;
	    }
	   
	   //maka anarana table any @ bdd
	   private String getTableName(Class<?> c) {
		   String table = this.getClassName(c);
		   return "tb_"+table+" ";
	   }
	   public String getTableName(Object o) {
		   Class<?> c = o.getClass();
		   this.classrelected = c;
		   String table = this.getTableName(c);
		   return table;
	   }
	   //maka anle field invokena @ objet
	   public Field[] toInvokeObject() {
		  Field[] temp = new Field[hash.keySet().size()];
		  Field[] f = hash.keySet().toArray(temp);
		  return f;
	   }
	   
	   public Object[] valueObject() {
		   Object[] col = hash.values().toArray();
		   return col;
	   }
	   
	   public String getSql(Object o) {
		   	Class<?> c = o.getClass();
			Table p = c.getDeclaredAnnotation(Table.class);
			this.annotation = p;
			this.getTableName(o);
			Field[] f = this.toInvoke(o);
//			String column= p.column();
			String column="";
			String[] temp=new String[0];
			Object[] col = hash.values().toArray();

			for(int i=0;i<col.length;i++) {
				column += String.valueOf(col[i]);
				System.out.println("column"+column);
				if(i + 1 != col.length) {
					column += ",";
				}
			}
			
			for(String item : splitvalue) {
				if(column.contains(item)) {
					column.replaceAll(",", column);
				}
			}
			//asorina @ le colonne mila invokena ny misy @liste
//			Object[] val = col;
			
			this.setValues(column.split(","));
			String sql = "SELECT "+column+" FROM ";
			if(p!=null) {
				sql+=p.name()+" ";
			}
			else {
				sql+=c.getSimpleName();
			}
			return sql;
	   }
//	   private HashMap<Field,String> getHash(){
//		   
//	   }
	   
	   public Field[] toInvoke(Object o) {
		   Class<?> c =this.classrelected;
		   System.out.println(c.getSimpleName());
		   Table p = c.getDeclaredAnnotation(Table.class);
		   Field[] f = c.getDeclaredFields();
		   List<Field> identifiedfield = new ArrayList<Field>();
		   for(int i=0;i<f.length;i++) {
			   if(f[i].isAnnotationPresent(Table.class) && f[i] != null) {
				   String col = f[i].getDeclaredAnnotation(Table.class).column();
				   //zay misy annotation (column) ihany no raisina
				  
				   if(!col.isEmpty()) {
					   System.out.println("col\t"+col);
					   identifiedfield.add(f[i]);
					   System.out.println("f[i].getName\t"+f[i].getName());
					   hash.put(f[i], col);
				   }
				   
			   }
			   else if(f[i].isAnnotationPresent(IgnoreColumn.class) && f[i] != null) {
				  
			   }
			   else{
				   hash.put(f[i], f[i].getName());
			   }
		   }
		   Field[] val =  new Field[identifiedfield.size()];
		   identifiedfield.toArray(val);
		   return val;
	   }
	   
	   //maka field sy method avy @ class
	   public HashMap<String,Method[]> getFieldSelectFromClass(Object o) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		   HashMap<String,Method[]> mapmethod = new HashMap<String,Method[]>();
		   Class<?> c = o.getClass();
		   Field[] f = c.getDeclaredFields();
		   List<Method> temp = new LinkedList();
		   List<Method> templistmethod = new LinkedList();
		   for(int i=0;i<f.length;i++) {
			   String fieldName = f[i].getName();
			   String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

			   Method method =c.getMethod(methodName, f[i].getType());

			   Type listType = method.getGenericParameterTypes()[0];
			   System.out.println(method.getName() + "type" +listType.getTypeName());
			   
			   //list ve le izy
				   if(f[i].getGenericType() instanceof Collection) {
					   Type elementType = ((ParameterizedType) f[i].getGenericType()).getActualTypeArguments()[0];
					   System.out.println(elementType.getTypeName());
//					   Class<?> derivation = Class.f
//					   System.out.println(derivation);
					   Constructor<? extends Type> construct = elementType.getClass().getConstructor();
					   
					   Object tempobj = construct.newInstance();
					   HashMap<String, Method[]> derivmethod = this.getFieldSelectFromClass(tempobj);
					   Method[] m = derivmethod.get("main");
					   for(int k=0;k<m.length;k++) {
						   templistmethod.add(m[k]);
					   }
//					   temp.add(Array.)
				   }else {
					   temp.add(method);
				   }
		   }
		   Method[] temptab = new Method[temp.size()];
		   Method[] tempderiv = new Method[templistmethod.size()];
		   Method[] resultmain = temp.toArray(temptab);
		   Method[] resultsecondary = templistmethod.toArray(tempderiv);
		   
		   mapmethod.put("main", resultmain);
		   mapmethod.put("derivation", resultsecondary);
		   
		   return mapmethod;
	   }

	public Table getAnnotation() {
		return annotation;
	}

	public HashMap<Field, String> getHash() {
		return hash;
	}

	public void setHash(HashMap<Field, String> hash) {
		this.hash = hash;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	
	   
	   
	   
}
