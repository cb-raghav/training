package week2.day3_4;

import java.io.File;
import java.util.*;

public class MoveFiles { 
    /**
     * recursive function to move all files from a source directory to a target directory
     * @param fileList - list of files / directories in the source directory 
     */
    public static void moveFiles(File[] sourceList, Map<String, Integer> fileCounts, String targetPath) {
        for(int i = 0; i < sourceList.length; i++) {
            File currSource = sourceList[i];
            if(currSource.isDirectory()) { // if it is a sub-directory, call the function again
                moveFiles(currSource.listFiles(), fileCounts, targetPath);
            }
            else { // else, move the file to the target directory
                String currName = currSource.getName(); //.split("\\.")[0]; 
                if(!currName.startsWith(".")) { // ignore files like ".DS_Store" that start with '.'
                    String newName;
                    if(fileCounts.get(currName) != null) { // there are other files with the same name
                        // get the current count from the map
                        int count = fileCounts.get(currName);

                        // build the new file name i.e., a.txt --> a-1.txt
                        String[] split = currName.split("\\.");
                        String plainName = split[0]; String extension = split[1];
                        plainName += ("-" + Integer.toString(count));
                        newName = (plainName + "." + extension);
                        
                        // update the map
                        fileCounts.put(currName, ++count);
                    }
                    else { // there are no other files with the same name
                        fileCounts.put(currName, 1); // add the file count to the map
                        newName = currName;
                    }
                    String oldPath = currSource.getParent() + "/" + currName;
                    String newPath = targetPath + "/" + newName;
                    System.out.println("Moving " + oldPath + " ===> " + newPath + " ...");
                    
                    // move the file to the target directory
                    currSource.renameTo(new File(newPath));
                }
                
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        s.useDelimiter("\n");
        System.out.print("Enter the source directory's path: ");
        String sourcePath = s.next(); // accept the source directory's path
        File inputDirectory = new File(sourcePath);
        if(!inputDirectory.exists()) {
            System.out.println("ERROR: no such directory exists!");
        }
        else {
            System.out.print("Enter the target directory's path: ");
            String targetPath = s.next(); // accept the target directory's path
            File targetDirectory = new File(targetPath);
            if(!targetDirectory.exists()) {
                // if the target directory doesn't exist, create it
                targetDirectory.mkdirs();
            }
            Map<String, Integer> fileCounts =  new HashMap<String, Integer>();
            moveFiles(inputDirectory.listFiles(), fileCounts, targetPath);
        }
    }
}