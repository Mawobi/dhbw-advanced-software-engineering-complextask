package applications;

import filereader.TSPFileReader;

import java.io.FileNotFoundException;

public class Application01 {
    public static void main(String[] args) throws FileNotFoundException {
        TSPFileReader tspFileReader = new TSPFileReader();
        tspFileReader.readTSPData();
    }
}
