package filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

public class TSPFileReader {

    public double[][] readTSPData() throws FileNotFoundException, RuntimeException {

        File file = new File("src/resources/a280.tsp");

        Scanner dimensionScanner = new Scanner(file);
        int dimension = -1;
        String line = "";

        while (dimensionScanner.hasNextLine()) {
            line = dimensionScanner.nextLine().trim();
            if (line.startsWith("DIMENSION:")) {
                dimension = Integer.parseInt(line.split(" ")[1]);
                break;
            }
        }
        dimensionScanner.close();

        if (dimension == -1) {
            throw new RuntimeException("Could not find dimension in file");
        }

        double[][] dist = new double[dimension][dimension];

        Scanner nodeScanner = new Scanner(file);

        while (nodeScanner.hasNextLine()) {
            line = nodeScanner.nextLine().trim();
            String[] splittedLine = line.split(" ");
            if(splittedLine.length !=3) {
                continue;
            }

            double[] node = new double[3];
            try {
                node[0] = Double.parseDouble(splittedLine[0]);
                node[1] = Double.parseDouble(splittedLine[1]);
                node[2] = Double.parseDouble(splittedLine[2]);
            } catch (NumberFormatException e) {
                continue;
            }


        }
        nodeScanner.close();

//        while ((line = csvReader.readLine()) != null) {
//            String[] lineArgs = line.split(",");
//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < i; j++) {
//                    dist[i][j] = dimensionScanner.nextDouble();
//                    dist[j][i] = dist[i][j];
//                }
//            }
//        }

        return dist;
    }
}