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
public class keyGenerator {
    public static String fromPlaintextByFrequency(String plaintext, boolean jToI) {
        if (plaintext == null) {
            return "";
        }
        Map<Character, Integer> freq = new HashMap<>();
        String up = plaintext.toUpperCase(Locale.ROOT);
        for (char ch : up.toCharArray()) {
            if (ch < 'A' || ch > 'Z') {
                continue;
            }
            if (ch == 'J' && jToI) {
                ch = 'I';
            }
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }
        List<Character> chars = new ArrayList<>(freq.keySet());
        chars.sort((a, b) -> {
            int cmp = Integer.compare(freq.get(b), freq.get(a));
            if (cmp != 0) {
                return cmp;
            }
            return Character.compare(a, b);
        });
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J' && jToI) {
                continue;
            }
            if (sb.indexOf(String.valueOf(c)) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static String normalizeKey(String rawKey, boolean jToI) {
        if (rawKey == null) {
            return "";
        }
        String up = rawKey.toUpperCase(Locale.ROOT);
        StringBuilder sb = new StringBuilder();
        for (char ch : up.toCharArray()) {
            if (ch < 'A' || ch > 'Z') {
                continue;
            }
            if (ch == 'J' && jToI) {
                ch = 'I';
            }
            if (sb.indexOf(String.valueOf(ch)) == -1) {
                sb.append(ch);
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J' && jToI) {
                continue;
            }
            if (sb.indexOf(String.valueOf(c)) == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
