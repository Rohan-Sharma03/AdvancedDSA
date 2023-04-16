
/**
 * Author : Rohan Sharma
 * Created : 16 April 2023
 * Last Update : 16 April 2023
 * Topic : Booyer Moore String matching Algortihm with bad character and good
 * character suffix
 * Reference Link : https://favtutor.com/blogs/boyer-moore-algorithm
 */

public class BoyerMoore {
    private static final int ALPHABET_SIZE = 26;

    // Boyer-Moore string search algorithm
    public static int search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        // Preprocess bad character heuristic

        // Create an array to store the bad character shift values for each alphabet
        // character
        int[] badChar = new int[ALPHABET_SIZE];

        // Initialize all bad character shift values to -1
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            badChar[i] = -1;
        }

        // Compute the bad character shift values based on the pattern
        for (int i = 0; i < patternLength; i++) {
            char c = pattern.charAt(i);
            int index = c - 'a'; // Calculate the index of the character in the alphabet
            badChar[index] = i;
        }

        // Preprocess good suffix heuristic

        // Create arrays to store the good suffix shift values and border values
        int[] goodSuffix = new int[patternLength + 1];
        int[] border = new int[patternLength + 1];

        // Call the helper method to generate the good suffix heuristic
        generateGoodSuffix(pattern, goodSuffix, border);

        int shift = 0; // Starting shift position in the text

        // Search for pattern in text

        // Iterate through the text until the pattern is found or the text is fully
        // searched
        while (shift <= textLength - patternLength) {
            int j = patternLength - 1;
            // Compare characters from right to left between the text and pattern
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }
            if (j < 0) {
                // Pattern found, return the shift position
                return shift;
            } else {
                // Apply the bad character and good suffix heuristics to determine the next
                // shift position
                char c = text.charAt(shift + j);
                int index = c - 'a'; // Calculate the index of the character in the alphabet
                int badCharShift = badChar[index];
                int goodSuffixShift = j - border[j + 1];
                shift += Math.max(1, Math.max(badCharShift, goodSuffixShift));
            }
        }

        // Pattern not found return -1
        return -1;
    }

    // Method to generate good suffix heuristic
    private static void generateGoodSuffix(String pattern, int[] goodSuffix, int[] border) {
        int patternLength = pattern.length();
        int i = patternLength;
        int j = patternLength + 1;
        border[i] = j;

        // Compute the border values
        while (i > 0) {
            while (j <= patternLength && pattern.charAt(i - 1) != pattern.charAt(j - 1)) {
                if (goodSuffix[j] == 0) {
                    goodSuffix[j] = j - i;
                }
                j = border[j];
            }
            i--;
            j--;
            border[i] = j;
        }

        j = border[0];
        for (i = 0; i <= patternLength; i++) {
            if (goodSuffix[i] == 0) {
                goodSuffix[i] = j;
            }
            if (i == j) {
                j = border[j];
            }
        }
    }

    public static void main(String[] args) {
        String text = "sharmarohan";
        String pattern = "rohan";
        int result = search(text, pattern);
        if (result != -1) {
            System.out.println("Pattern found at index: " + result);
        } else {
            System.out.println("Pattern not found in the text.");
        }
    }
}
