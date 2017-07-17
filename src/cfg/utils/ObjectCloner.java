package cfg.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author va
 */

@SuppressWarnings("serial")
public class ObjectCloner implements Serializable {
  
   private ObjectCloner(){}
  
   static public Object deepCopy(Object oldObj) throws Exception {
      ObjectOutputStream oos = null;
      ObjectInputStream ois = null;
      try {
         ByteArrayOutputStream bos = 
               new ByteArrayOutputStream(); 
         oos = new ObjectOutputStream(bos); 
         // serialize and pass the object
         oos.writeObject(oldObj);  
         oos.flush();             
         ByteArrayInputStream bin = 
               new ByteArrayInputStream(bos.toByteArray()); 
         ois = new ObjectInputStream(bin);                  
         // return the new object
         return ois.readObject(); 
      }
      catch(Exception e) {
         System.out.println("Exception in ObjectCloner = ");
         System.out.println(e);
         throw(e);
      }
      finally {
         oos.close();
         ois.close();
      }
   }
   
}