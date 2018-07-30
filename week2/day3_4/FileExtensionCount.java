package week2.day3_4;

import java.io.File;
import java.util.*;

public class FileExtensionCount {
    
    /**
     * function to get the extension of a given file
     * @param file - the input file
     * @return the file's extension (excluding the '.')
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        else { 
            return "";
        }
    }
    
    /**
     * recursive function to count the number of files with each extension in a directory
     * @param fileList - the list of files / sub-directories in the given directory
     * @param fileCounts - the map that stores the individual counts for each extension
     */
    public static void countFilesInDir(File[] fileList, Map<String, Integer> fileCounts) {
        for(int i = 0; i < fileList.length; i++) {
            File curr = fileList[i];
            if(curr.isDirectory()) { // if it is a sub-directory, call the function again
                countFilesInDir(curr.listFiles(), fileCounts);
            }
            else { // else, update the map
                if(!curr.getName().startsWith(".")) {
                    String ext = getFileExtension(curr);
                    System.out.println(curr.getName() + "'s extension is - " + ext);
                    if(ext != null && ext != "") {
                        if(fileCounts.get(ext) != null) {
                            int oldCount = fileCounts.get(ext);
                            fileCounts.put(ext, ++oldCount);
                        }
                        else {
                            fileCounts.put(ext, 1);
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.print("Enter the directory's path: ");
        String directoryPath = s.next();
        File inputDirectory = new File(directoryPath);
        if(!inputDirectory.exists()) {
            System.out.println("ERROR: no such directory exists!");
        }
        else {
            Map<String, Integer> fileCounts =  new HashMap<String, Integer>();
            countFilesInDir(inputDirectory.listFiles(), fileCounts);
            
            for(Map.Entry<String, Integer> m : fileCounts.entrySet()) {
                System.out.println("Number of ." + m.getKey() + " files - " + m.getValue());
            }
        }
    }
}