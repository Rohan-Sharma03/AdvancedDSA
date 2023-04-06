public class kmp {
    public static void main(String args[]) {
        // Given String for pattern matching
        String T = "ababcabcabababd";
        // Given pattern to be searched
        String P = "ababd";
        // String and pattern length
        int slen = T.length();
        int plen = P.length();
        boolean found = true;
        // pi Table index.
        int j = -1;
        // Text Index.
        int i = 0;
        // characters of pi table
        char charInPiTable[] = P.toCharArray();
        // pi table
        int piTable[] = new int[plen];
        // pi table first index is always 0
        piTable[0] = 0;
        // pointer for filling pi table.
        int piI = 0, piJ = 0;

        // lps table algo
        for (piJ = 1; piJ < plen; piJ++) {
            // while traversing for find substring if both the character are same update the
            // pie table and shift right the piI pointer by 1.
            if (charInPiTable[piI] == charInPiTable[piJ]) {
                piTable[piJ] = piI + 1;
                piI++;
            } else {
                if (piI != 0) {
                    piI = piTable[piI - 1];
                    piJ--;
                } else {
                    piTable[piJ] = 0;
                }
            }
        }

        // Comparison algo where i not backtraking
        while (j < plen) {
            if (j == plen - 1) {
                found = true;
                break;
            } else if (i == slen) {
                found = false;
                break;
            } else if (T.charAt(i) == P.charAt(j + 1)) {
                i++;
                j++;
            } else {
                if (j != -1) {
                    j = piTable[j] - 1;
                } else {
                    i++;
                }
            }
        }
        if (found) {
            System.out.println("Pattern found at index " + (i - j));
        } else {
            System.out.println("Pattern not found");
        }
    }
}