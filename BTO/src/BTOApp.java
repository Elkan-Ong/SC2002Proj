import java.sql.Array;
import java.util.Scanner;
import java.util.ArrayList;

import Project.HDBProject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BTOApp {
    public static void main(String[] args) {

    }

    public static ArrayList<HDBProject> readProjects() {
        ArrayList<HDBProject> result = new ArrayList<HDBProject>();
        try (BufferedReader br = new BufferedReader(new FileReader("BTO/src/Files/ProjectList.csv"))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return
    }



}
