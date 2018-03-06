package week2.day12;

import java.util.*;

public class commonPrefix {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        ArrayList<String> wordList = new ArrayList<String>();
        int opt = 1;    
        do { // Keep accepting strings from the user until they wish to stop
            System.out.print("Enter a string: ");
            String inp = s.next();
            wordList.add(inp);
            System.out.print("Do you wish to continue entering input? (1/0) - ");
            opt = s.nextInt();
        }
        while(opt == 1);
        
        Collections.sort(wordList);
        Map<String, ArrayList<String> > commonPrefix = new HashMap<String, ArrayList<String> >();
        for(int i = 0; i < wordList.size(); i++) {
            String currWord = wordList.get(i);
            System.out.print("\nCurrent word: \"" + currWord + "\"");
            String currPrefix = currWord.substring(0, Math.min(currWord.length(), 3)); 
            ArrayList<String> currList = commonPrefix.get(currPrefix);
            if(currList == null) {
                currList = new ArrayList<String>();
            }
            currList.add(currWord);
            commonPrefix.put(currPrefix, currList);
        }
        
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