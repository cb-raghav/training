package week2.day1_2;

import java.util.*;

public class WordLength {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        ArrayList<String> words = new ArrayList<String>(); // initialise the list of words
        boolean acceptInput = true;
        System.out.println("Use \'### \' to halt user input");    
        do { // Keep accepting strings from the user until they wish to stop
            System.out.print("Enter a string: ");
            String inp = s.next();
            if(inp.equals("###")) {
                acceptInput = false;
            }
            else {
                words.add(inp);
            }
        }
        while(acceptInput);

        Map<Integer, ArrayList<String> > wordLength = new HashMap<Integer, ArrayList<String> >();
        for(int i = 0; i < words.size(); i++) { // iterate through the input list of words
            String currWord = words.get(i);
            System.out.print("\nCurrent word: \"" + currWord + "\"");
            // get the current word's length
            int currLength = currWord.length(); 
            // fetch the corresponding list of words having that length from the map
            ArrayList<String> currList = wordLength.get(currLength); 
            if(currList == null) { // if no such list exists, create from scratch
                currList = new ArrayList<String>();
            }
            currList.add(currWord); // add the current word to the list
            wordLength.put(currLength, currList); // add the updated list back to the map
        }

        for(Map.Entry<Integer, ArrayList<String> > m : wordLength.entrySet()) {
            // display the contents of the map
            int length = m.getKey();
            System.out.print("\n\nWords with length " + length + ": ");
            ArrayList<String> wordList = m.getValue();
            int size = wordList.size();
            for(int i = 0; i < size; i++) {
                System.out.print(wordList.get(i));
                if(i != size - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();
    }
}