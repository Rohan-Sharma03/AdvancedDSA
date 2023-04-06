
/**
 * Author : Rohan Sharma
 * Created : 6 April 2023
 * Last Update : 6 April 2023
 * Topic : B Tree
 * Reference Link : https://www.programiz.com/dsa/b-tree
 */

public record rabinKarpHashBased() {
    // Global Var to Store pervious hash values of substrings in given text i.e (to
    // make hash function a rolling hash function).
    static int prevHash = 0;

    // function to compute hash value of given string.
    public static int computeHashValue(String p) {
        // base value of power
        int base = 26;
        int plen = p.length();
        // var to store Hash Value of Given String;
        int hv = 0;
        for (char c : p.toCharArray()) {
            hv += (c - 'a' + 1) * Math.pow(base, --plen);
        }
        return hv;
    }

    public static void main(String args[]) {
        // Given String for pattern matching
        String T = "abbcbabccbdab";
        // Given pattern to be searched
        String P = "bccbd";
        // String and pattern length
        int slen = T.length();
        int plen = P.length();
        boolean found = true;
        // var to store pattern Hash Value.
        int patternHash = computeHashValue(P);
        // generating hash value for sub Strings of pattern size in given string.
        for (int i = 0; i < slen - plen + 1; i++) {
            String sub = T.substring(i, i + plen);
            System.out.println(sub + " " + computeHashValue(sub));
            if (patternHash == computeHashValue(sub)) {
                System.out
                        .println("Hash value Found Same with subString in Given Text at shift value of :" + (i + 1));
                for (int j = 0; j < plen; j++) {
                    if (P.charAt(j) != sub.charAt(j)) {
                        System.out.println("Characters not matched");
                        found = false;
                        break;
                    }
                }
                found = true;
                break;
            }
        }
        if (found) {
            System.out.println("Pattern Found.");
        } else {
            System.out.println("Pattern not Found.");
        }
    }
}
