package utils;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class Utils {
    public static void deleteFromFile(String line, File file) {
        try {
            File copy = new File(file.toPath().toString() + "Copy");
            Files.copy(file.toPath(), copy.toPath());

            BufferedReader bufferedReader = new BufferedReader(new FileReader(copy));
            PrintWriter printWriter = new PrintWriter(file);

            String line1 = bufferedReader.readLine();
            while (line1 != null) {
                if (!line.equals(line1)) {
                    printWriter.println(line1);
                }
                line1 = bufferedReader.readLine();
            }
            bufferedReader.close();
            printWriter.flush();
            printWriter.close();
            Files.delete(copy.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String link, File file, String id) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(link + " " + id);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUnique(String link, File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(" ");
                String fileLink = row[0];
                if (link.equals(fileLink)) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
