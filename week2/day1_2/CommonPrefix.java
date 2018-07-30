package week2.day1_2;

import java.util.*;

public class CommonPrefix {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        ArrayList<String> wordList = new ArrayList<String>();
        boolean acceptInput = true;
        System.out.println("Use \'### \' to halt user input");  
        do { // Keep accepting strings from the user until they wish to stop
            System.out.print("Enter a string: ");
            String inp = s.next();
            if(inp.equals("###")) {
                acceptInput = false;
            }
            else {
                wordList.add(inp);
            }
        }
        while(acceptInput);
        
        Collections.sort(wordList); // sort the input word list
        Map<String, ArrayList<String> > commonPrefix = new HashMap<String, ArrayList<String> >();
        for(int i = 0; i < wordList.size(); i++) {
            String currWord = wordList.get(i);
            System.out.print("\nCurrent word: \"" + currWord + "\"");
            // get the 3-letter prefix of the current word
            String currPrefix = currWord.substring(0, Math.min(currWord.length(), 3)); 
            // fetch the corresponding list of words having that prefix from the map
            ArrayList<String> currList = commonPrefix.get(currPrefix);
            if(currList == null) { // if no such list exists, create from scratch
                currList = new ArrayList<String>();
            }
            currList.add(currWord); // add the current word to the list
            commonPrefix.put(currPrefix, currList); // add the updated list to the map
        }
        
        // initialise a tree map to store the prefixes in dictionary order
        Map< String, ArrayList<String> > sortedByPrefix = new TreeMap< String, ArrayList<String> >(commonPrefix);
        for(Map.Entry<String, ArrayList<String> > m : sortedByPrefix.entrySet()) {
            String prefix = m.getKey();
            System.out.print("\n\nWords with prefix " + prefix + ": ");
            ArrayList<String> words = m.getValue();
            int size = words.size();
            for(int i = 0; i < size; i++) {
                System.out.print(words.get(i));
                if(i != size - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();
    }
}