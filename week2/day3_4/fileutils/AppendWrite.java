package week2.day3_4.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class AppendWrite {
    public static void main(String[] args) throws IOException {
        String workingDirectory = System.getProperty("user.dir");
        File inputFile1 = FileUtils.getFile(workingDirectory + "/week2/day3_4/fileutils/input1.txt");
        File inputFile2 = FileUtils.getFile(workingDirectory + "/week2/day3_4/fileutils/input2.txt");
        
        String input1 = FileUtils.readFileToString(inputFile1, StandardCharsets.UTF_8);
        String input2 = FileUtils.readFileToString(inputFile2, StandardCharsets.UTF_8);
        
        String output = input1 + input2;
        File outputFile = new File("/Users/raghav/source/training/week2/day3_4/fileutils/output.txt");
        FileUtils.writeStringToFile(outputFile, output, StandardCharsets.UTF_8);
        
        //System.out.println(input1); System.out.println(input2);
        System.out.println();
    }
}