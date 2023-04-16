
/**
 * Author : Rohan Sharma
 * Created : 16 April 2023
 * Last Update : 16 April 2023
 * Topic : External Merge sort with buffer size of 1024
 * Reference Link : https://gist.github.com/gabhi/97d9182ea8df09c1e389
 */

import java.io.*;
import java.util.*;

public class ExternalMergeSort {

    public static void main(String[] args) throws IOException {
        String inputFile = "Assignment 2 External Merge Sort/input.txt"; // Input file name
        String outputFile = "Assignment 2 External Merge Sort/output.txt"; // Output file name
        int bufferSize = 1024; // Buffer size for reading and sorting data
        int numValues = 1024; // Number of values to generate in the input file
        generateInputFile(inputFile, numValues); // Generate input file with random values
        externalMergeSort(inputFile, outputFile, bufferSize); // Perform external merge sort
        System.out.println("Sorting complete. Sorted data written to " + outputFile);
    }

    public static void externalMergeSort(String inputFile, String outputFile, int bufferSize) throws IOException {
        // Read input file into chunks
        List<File> chunks = new ArrayList<>(); // List to store temporary chunk files
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int chunkSize = 0;
            List<Integer> chunk = new ArrayList<>(); // List to store data in a chunk
            while ((line = br.readLine()) != null) {
                chunk.add(Integer.parseInt(line)); // Read data from input file and add to chunk
                chunkSize++;
                if (chunkSize == bufferSize) {
                    Collections.sort(chunk); // Sort the chunk
                    File chunkFile = writeChunkToFile(chunk); // Write the chunk to a temporary file
                    chunks.add(chunkFile); // Add the temporary file to the list of chunks
                    chunk.clear(); // Clear the chunk for the next set of data
                    chunkSize = 0;
                }
            }
            if (!chunk.isEmpty()) {
                Collections.sort(chunk); // Sort the last chunk
                File chunkFile = writeChunkToFile(chunk); // Write the last chunk to a temporary file
                chunks.add(chunkFile); // Add the temporary file to the list of chunks
            }
        }

        // Merge sorted chunks
        PriorityQueue<BufferedReader> minHeap = new PriorityQueue<>((br1, br2) -> {
            // Comparator to compare values from different buffered readers
            try {
                return Integer.compare(Integer.parseInt(br1.readLine()), Integer.parseInt(br2.readLine()));
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        });

        List<BufferedWriter> tempFiles = new ArrayList<>(); // List to store temporary output files
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (File chunk : chunks) {
                BufferedReader br = new BufferedReader(new FileReader(chunk)); // Create buffered reader for each chunk
                minHeap.offer(br); // Add the buffered reader to the priority queue
                tempFiles.add(bw); // Add the buffered writer for the output file to the list of temporary files
            }
            while (!minHeap.isEmpty()) {
                BufferedReader br = minHeap.poll(); // Get the smallest value from the priority queue
                String line = br.readLine();
                if (line != null) {
                    bw.write(line); // Write the value to the output file
                    bw.newLine(); // Write a newline character to separate values
                    minHeap.offer(br); // Add the buffered reader back to the priority queue
                } else {
                    br.close(); // Close the buffered reader if it has no more values
                }
            }
        }

        // Clean up temporary chunk files
        for (File chunk : chunks) {
            if (!chunk.delete()) {
                System.err.println("Failed to delete temporary chunk file: " + chunk.getName());
            }
        }
    }

    public static File writeChunkToFile(List<Integer> chunk) throws IOException {
        File chunkFile = File.createTempFile("chunk", ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chunkFile))) {
            for (Integer value : chunk) {
                bw.write(value.toString());
                bw.newLine();
            }
        }
        return chunkFile;
    }

    public static void generateInputFile(String inputFile, int numValues) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile))) {
            Random random = new Random();
            for (int i = 0; i < numValues; i++) {
                bw.write(Integer.toString(random.nextInt()));
                bw.newLine();
            }
        }
    }
}
