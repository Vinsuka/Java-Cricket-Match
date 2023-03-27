import java.io.*;
public class ExtraMethod {
    public static void changePlayer(String fileDirection, int lineNumber, String text) {
        try {
            // input the modified file content to the StringBuffer "input" to changePlayer Method
            BufferedReader file = new BufferedReader(new FileReader(fileDirection));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            int total = 0;
            //reads the given file
            while ((line = file.readLine()) != null) {
                //increase the total to given number of lines
                total++;
                if (total == lineNumber) {
                    //Print lines in the given file
                    System.out.println(total + " " + line);
                    line = text;
                }
                //Adding the modified content.
                inputBuffer.append(line);
                //Creating a space
                inputBuffer.append('\n');
            }
            //Close the file
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileAdd = new FileOutputStream(fileDirection);
            fileAdd.write(inputBuffer.toString().getBytes());
            fileAdd.close();

        //Exception error message!
        } catch (Exception e) {
            System.out.println("Error when reading file.");
        }
    }

    public static void AddToSummary(String text) {
        try {
            // input the modified file content to the StringBuffer "input" to AddToSummary
            //Refer the file path
            File f = new File("Files/summary.txt");
            //Read the file
            BufferedReader file = new BufferedReader(new FileReader(f));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            //Add to the summary file.
            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            //Close the file
            file.close();
            inputBuffer.append(text);
            // write the new string with the replaced line OVER the same file
            File k = new File("Files/summary.txt");
            FileOutputStream fileAdd = new FileOutputStream(k);
            fileAdd.write(inputBuffer.toString().getBytes());
            fileAdd.close();

        } catch (Exception e) {
            System.out.println("Error when reading file.");
        }
    }

    public static void replaceNewSum() {
        try {
            // write the new string with the replaced line OVER the same file
            File f = new File("Files/summary.txt");
            FileOutputStream fileAdd = new FileOutputStream(f);
            //Write to summary file
            fileAdd.write("".getBytes());
            fileAdd.close();
        //Exception message
        } catch (Exception e) {
            System.out.println("Error when reading file.");
        }
    }

    public static void SummaryDisplay() {
        try {
            // input the (modified) file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader("Files/summary.txt"));
            String line;
            //Read the summary file
            while ((line = file.readLine()) != null) {
                System.out.println(line);
            }
            file.close();
        //Exception error message!
        } catch (Exception e) {
            System.out.println("Error when reading file.");
        }
    }
}