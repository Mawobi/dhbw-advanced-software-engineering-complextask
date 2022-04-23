package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TSPFileReader {

    /**
     * Reads a tsp file with nodes and returns a distance matrix of the nodes.
     *
     * @return distance matrix of nodes
     * @throws FileNotFoundException where the file is not found
     * @throws RuntimeException when no dimension is found in the file
     */
    public double[][] readTSPData() throws FileNotFoundException, RuntimeException {

        File file = new File("src/resources/a280.tsp");
        int dimension = -1;
        ArrayList<double[]> nodes = new ArrayList<>();

        // Scanning for dimension/number of nodes and the coordinates of nodes in the tsp file
        Scanner scanner = new Scanner(file);
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim().replaceAll(" +", " ");
            if (line.startsWith("DIMENSION:")) {
                dimension = Integer.parseInt(line.split(" ")[1]);
                continue;
            }
            String[] splittedLine = line.split(" ");
            if(splittedLine.length !=3) {
                continue;
            }

            double[] node = new double[3];
            try {
                Integer.parseInt(splittedLine[0]);
                node[0] = Double.parseDouble(splittedLine[1]);
                node[1] = Double.parseDouble(splittedLine[2]);
            } catch (NumberFormatException e) {
                continue;
            }
            nodes.add(node);
        }
        scanner.close();

        // If dimension is not found, throw exception
        if (dimension == -1) {
            throw new RuntimeException("Could not find dimension in file");
        }

        // Calculating distance matrix
        double[][] distanceMatrix = new double[dimension][dimension];
        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < i; j++) {
                distanceMatrix[i][j] = Math.sqrt(Math.pow(nodes.get(i)[0] - nodes.get(j)[0], 2) + Math.pow(nodes.get(i)[1] - nodes.get(j)[1], 2));
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }

        return distanceMatrix;
    }
}