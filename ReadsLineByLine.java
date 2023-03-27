import java.io.*;
public class ReadsLineByLine {
    public static void main(String args[]) {

        try {
            //Create the File
            File file=new File("Files/players.txt");
            //Read the file
            FileReader FileLine=new FileReader(file);

            //creates a buffering character input stream
            BufferedReader br=new BufferedReader(FileLine);
            //Create the String Buffer
            StringBuffer element=new StringBuffer();
            String line;
            String[] teamMembers;
            while((line=br.readLine())!=null) {
                //Add line to String Buffer
                element.append(line);
                //Add a space
                element.append("\n");
                System.out.println(line);
            }
            FileLine.close();    //closes the stream
            System.out.println("Contents of File: ");

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

