/**
 * Author : Rohan Sharma
 * Created : 24 March 2023
 * Last Update : 24 March 2023
 * Topic : B Tree
 * Reference Link : https://www.programiz.com/dsa/b-tree
 */

public class navieMethod {

    public static void main(String[] args) {
        // Given String for pattern matching
        String T = "abbcbabccbdab";

        // Given pattern to be searched
        String P = "bccbd";
        int n = T.length();
        int m = P.length();
        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            if (T.charAt(i) == P.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == m) {
            System.out.println("Pattern found at index " + (i - j));
        } else {
            System.out.println("Pattern not found");
        }
        // Print "Hello World" to the terminal window.
        System.out.println("Hello World");
    }

}