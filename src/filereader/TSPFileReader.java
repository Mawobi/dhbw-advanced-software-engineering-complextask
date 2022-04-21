package filereader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class TSPFileReader {

    public double[][] readTSPData(String filePath) throws FileNotFoundException {
        // Create scanner
        Scanner sc = new Scanner(new File(filePath));
        sc.useLocale(Locale.US);
        // Read number of cities
        int n = sc.nextInt();
        // Initialize distance matrix
        double[][] dist = new double[n][n];
        // Fill distance matrix
        for(int i=0; i<n; i++){
            for(int j=0; j<i; j++){
                dist[i][j] = sc.nextDouble();
                dist[j][i] = dist[i][j];
            }
        }
        // Close scanner
        sc.close();

        return dist;
    }

}