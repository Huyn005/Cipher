/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlayFairApp;
import java.util.*;
/**
 *
 * @author H P
 */
public class PlayFair {
    char[][] table = new char[5][5];
    Map<Character, int[]> pos = new HashMap<>();
    boolean jToI;
    char paddingChar;
    public PlayFair(String key, boolean jToI, char paddingChar) {
        this.jToI = jToI;
        this.paddingChar = paddingChar;
        buildTable(key == null ? "" : key);
    }
    public void buildTable(String key) {
        boolean[] used = new boolean[26];
        Arrays.fill(used, false);
        StringBuilder pool = new StringBuilder();
        key = normalize(key);
        for (char c : key.toCharArray()) {
            if (!contains(pool, c)) {
                pool.append(c);
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J' && jToI) {
                continue; // skip J if J->I
            }
            if (!contains(pool, c)) {
                pool.append(c);
            }
        }
        if (pool.length() < 25) {
            throw new IllegalArgumentException("Failed to build table, pool length=" + pool.length());
        }
        int idx = 0;
        pos.clear();
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                char ch = pool.charAt(idx++);
                table[r][c] = ch;
                pos.put(ch, new int[]{r, c});
            }
        }
    }
    private static boolean contains(CharSequence sb, char ch) {
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }
    private String normalize(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        s = s.toUpperCase(Locale.ROOT);
        for (char ch : s.toCharArray()) {
            if (ch < 'A' || ch > 'Z') {
                continue;
            }
            if (ch == 'J' && jToI) {
                ch = 'I';
            }
            if (!contains(sb, ch)) {
                }
            sb.append(ch);
        }
        return sb.toString();
    }
    public String preprocessText(String plaintext, boolean removeNonLetters) {
        if (plaintext == null) {
            return "";
        }
        StringBuilder cleaned = new StringBuilder();
        String up = plaintext.toUpperCase(Locale.ROOT);
        for (char ch : up.toCharArray()) {
            if (ch < 'A' || ch > 'Z') {
                if (!removeNonLetters) {
                    continue;
                } else {
                    continue;
                }
            }
            if (ch == 'J' && jToI) {
                ch = 'I';
            }
            cleaned.append(ch);
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < cleaned.length();) {
            char a = cleaned.charAt(i);
            char b = (i + 1 < cleaned.length()) ? cleaned.charAt(i + 1) : 0;
            if (b == 0) {
                out.append(a).append(paddingChar);
                break;
            }
            if (a == b) {
                out.append(a).append(paddingChar);
                i += 1; // only advance by 1
            } else {
                out.append(a).append(b);
                i += 2;
            }
        }
        return out.toString();
    }
    public String encrypt(String plaintext) {
        String processed = preprocessText(plaintext, true);
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < processed.length(); i += 2) {
            char a = processed.charAt(i);
            char b = processed.charAt(i + 1);
            cipher.append(encryptPair(a, b));
        }
        return cipher.toString();
    }

    public String decrypt(String ciphertext) {
        if (ciphertext == null) {
            return "";
        }
        String up = ciphertext.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
        if (jToI) {
            up = up.replace('J', 'I');
        }
        StringBuilder plain = new StringBuilder();
        for (int i = 0; i < up.length(); i += 2) {
            char a = up.charAt(i);
            char b = (i + 1 < up.length()) ? up.charAt(i + 1) : paddingChar;
            plain.append(decryptPair(a, b));
        }
        return plain.toString();
    }
    private String encryptPair(char a, char b) {
        int[] p1 = pos.get(a);
        int[] p2 = pos.get(b);
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Characters not in table: " + a + "," + b);
        }
        int r1 = p1[0], c1 = p1[1], r2 = p2[0], c2 = p2[1];
        char x, y;
        if (r1 == r2) {
            x = table[r1][(c1 + 1) % 5];
            y = table[r2][(c2 + 1) % 5];
        } else if (c1 == c2) {
            x = table[(r1 + 1) % 5][c1];
            y = table[(r2 + 1) % 5][c2];
        } else {
            x = table[r1][c2];
            y = table[r2][c1];
        }
        return "" + x + y;
    }
    private String decryptPair(char a, char b) {
        int[] p1 = pos.get(a);
        int[] p2 = pos.get(b);
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Characters not in table: " + a + "," + b);
        }
        int r1 = p1[0], c1 = p1[1], r2 = p2[0], c2 = p2[1];
        char x, y;
        if (r1 == r2) {
            x = table[r1][(c1 + 4) % 5];
            y = table[r2][(c2 + 4) % 5];
        } else if (c1 == c2) {
            x = table[(r1 + 4) % 5][c1];
            y = table[(r2 + 4) % 5][c2];
        } else {
            x = table[r1][c2];
            y = table[r2][c1];
        }
        return "" + x + y;
    }
    public char[][] getTableCopy() {
        char[][] copy = new char[5][5];
        for (int r = 0; r < 5; r++) {
            System.arraycopy(table[r], 0, copy[r], 0, 5);
        }
        return copy;
    }
}
