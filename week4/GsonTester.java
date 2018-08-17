import com.google.gson.*;
import java.util.*;

public class GsonTester { 
   public static void main(String[] args) { 
      Map< String, List<String> > locations = new HashMap<>();
      List<String> indianStates = Arrays.asList("Tamil Nadu", "Karnataka", "West Bengal", 
             "Kerala", "Andhra Pradesh", "Maharashtra");
      List<String> americanStates = Arrays.asList("Alabama", "Washington", "California",
             "North Carolina", "New York", "Texas");

      locations.put("India", indianStates);
      locations.put("United States of America", americanStates);

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String json = gson.toJson(locations);
      System.out.println(json);
   } 
}