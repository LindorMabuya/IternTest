import com.google.common.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by lilo on 2016-07-02.
 */
public class App {

    public static void main(String[] args) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat(" yyyy MMMM dd HH:mm");
        Date date = new Date();
        Scanner scanner = new Scanner(System.in);
        String dir, duplicates = "";
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;

        //TODO creating text file for storing names off duplicated files
        File logFile = getFile();

        System.out.println("Enter directory you want to scan or 1 to exit");
        dir = scanner.nextLine();

        //TODO.... validation
        while (!dir.equalsIgnoreCase("1")) {
            while (!new File((dir)).isDirectory()) {
                System.out.println("Invalid directory try again or 1 to exit");
                dir = scanner.next();
                System.out.flush();
                if (dir.equalsIgnoreCase("1")) {
                    System.exit(0);
                }
            }

            final File folder = new File(dir);
            File[] files = folder.listFiles();
            fileWriter = new FileWriter(logFile.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);

            //TODO... checking for duplications
            for (int i = 0; i < files.length; i++) {
                for (int j = i + 1; j < files.length; j++) {
                    if (files[i].isFile() && files[j].isFile()) {
                        if (Files.asByteSource(files[i]).contentEquals(Files.asByteSource(files[j]))) {
                            duplicates += files[i].getName().toUpperCase() + " is a duplicate of " + files[j].getName().toUpperCase() + "\n";
                        }
                    }
                }
            }

            //TODO.... if the are no duplicate files don't write any thing to the file
            if (!duplicates.equalsIgnoreCase("")) {
                bufferedWriter.write("Path: " +
                        new File(dir).getAbsoluteFile() +
                        "\n\t\t\t" + dateFormat.format(date) +
                        "\n" + duplicates + "\n\n");
                duplicates = "";
            }

            System.out.println("Enter directory you want to scan or 1 to exit");
            dir = scanner.next();

            if (dir.equals("1"))
                bufferedWriter.close();
        }
    }

    private static File getFile() throws IOException {
        File logFile = new File("duplicate Log File.txt");
        if (logFile.exists()) {
            logFile.createNewFile();
        }
        return logFile;
    }
}
