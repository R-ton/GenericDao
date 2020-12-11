package string;

public class StringFunction {
	public String upperCaseString(String text,int begin,int last) {
		String newText = text.substring(begin, last);
		newText = newText.toUpperCase();
		text = text.substring(last);
		newText +=text;
		System.out.println(newText);
		return newText;
	}
	
	public static String[] split(String text,String agentSplit){
		String[] list = text.split(agentSplit);
		for(int i=0;i<list.length;i++){
			System.out.println(list[i]);
		}
		return list;
	}
	
	public String replace(String text,String toReplace,String replaceTo){
		String newText = "";
		
		newText = text.replaceAll(toReplace, replaceTo);
		
		System.out.println(newText);
		
		return newText;
	}
}
