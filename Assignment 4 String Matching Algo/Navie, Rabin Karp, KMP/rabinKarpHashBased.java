
/**
 * Author : Rohan Sharma
 * Created : 6 April 2023
 * Last Update : 6 April 2023
 * Topic : Rabin Karp String matching Algortihm using hash function
 * Reference Link : N/A
 */

public record rabinKarpHashBased() {
    // Global Var to Store pervious hash values of substrings in given text i.e (to
    // make hash function a rolling hash function).
    static int prevHash = 0;
    // base value to be used in hash function;
    static int base = 10;

    // function to compute hash value of given string (i.e 1'st substring ).
    public static int computeHashValue(String p) {
        // susString len
        int plen = p.length();
        // var to store Hash Value of Given String;
        int hv = 0;
        for (char c : p.toCharArray()) {
            hv += (c - 'a' + 1) * Math.pow(base, --plen);
        }
        return hv;
    }

    // function to compute hash value of given string (i.e customized for given
    // text).
    public static int computeHashValue(String T, int i, int plen) {
        // var to save current hash value
        int hv = 0;
        // if it is 1'st substring,
        if (i == 0) {
            // var to store Hash Value of subString;
            hv = computeHashValue(T.substring(0, plen));
            // update prevHash as it will be used to calculate next hash value.
            prevHash = hv;
        }
        if (i > 0) {
            // System.out.println(prevHash + " prev hash");
            // computing hash value for next substring in given text.
            /**
             * val 1 2 2 2 3 2 1 2 3 3 2 4 2 2
             * idx 0 1 2 3 4 5 6 7 8 9 10 11 12 13
             * chr a b b b c b a b c c b d a b
             * 
             * st1 abbcb => 22321
             * st2 bbcba => 23212
             */

            System.out.println(T.charAt(i - 1) + " replace by " + T.charAt(plen + i -
                    1));
            hv = (Math.abs(prevHash - ((T.charAt(i - 1) - 'a' + 1) * (int) (Math.pow(base, plen - 1)))) * base)
                    + (T.charAt(plen + i - 1) - 'a' + 1) * 1;
            System.out.println(hv + " new hash, using rolling function");
            prevHash = hv;
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
        System.out.println("Pattern Hash Value : " + patternHash);
        // generating hash value for sub Strings of pattern size in given string.
        for (int i = 0; i < slen - plen + 1; i++) {
            String sub = T.substring(i, i + plen);
            int hash = computeHashValue(T, i, plen);
            System.out.println(sub + " " + hash);
            if (patternHash == hash) {
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
            } else {
                found = false;
            }
        }
        if (found) {
            System.out.println("Pattern Found.");
        } else {
            System.out.println("Pattern not Found.");
        }
    }
}
