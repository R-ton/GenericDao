package annotation;

public class TestAnnotation {
	   private String nom = "toto";
	   
	   /**
	    * Les instructions JavaDoc sont dans les commentaires
	    * Alors que les annotations sont en dehors de tous blocs de commentaires
	    * @deprecated
	    * @return
	    */
	   
	   @AnnotationZ
	   public String toString() {
	      return "TestAnnotation [nom=" + nom + "]";
	   }
	   
	   @AnnotationZ
	   public String faisQuelqueChose(){
	      return "Je ne fais rien...";
	   }
}
