package week2.day12;

import java.util.*;

public class wordLength {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        ArrayList<String> words = new ArrayList<String>();
        int opt = 1;    
        do { // Keep accepting strings from the user until they wish to stop
            System.out.print("Enter a string: ");
            String inp = s.next();
            words.add(inp);
            System.out.print("Do you wish to continue entering input? (1/0) - ");
            opt = s.nextInt();
        }
        while(opt == 1);

        Map<Integer, ArrayList<String> > wordLength = new HashMap<Integer, ArrayList<String> >();
        for(int i = 0; i < words.size(); i++) {
            String currWord = words.get(i);
            System.out.print("\nCurrent word: \"" + currWord + "\"");
            int currLength = currWord.length();
            ArrayList<String> currList = wordLength.get(currLength);
            if(currList == null) {
                currList = new ArrayList<String>();
            }
            currList.add(currWord);
            wordLength.put(currLength, currList);
        }

        for(Map.Entry<Integer, ArrayList<String> > m : wordLength.entrySet()) {
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