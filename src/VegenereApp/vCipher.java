/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VegenereApp;

/**
 *
 * @author H P
 */
public class vCipher {
    // Hàm chuẩn hóa key
    public static String normalizeKey(String key) {
        if (key == null) {
            return "";  // Nếu key bị null → trả về chuỗi rỗng để tránh lỗi.
        }
        StringBuilder sb = new StringBuilder();  // Dùng StringBuilder để ghép chuỗi hiệu quả.
        for (char c : key.toCharArray()) {  // Duyệt qua từng ký tự trong key.
            if (Character.isLetter(c)) {  // Chỉ giữ lại các ký tự là chữ cái.
                sb.append(Character.toUpperCase(c));  // Chuyển chữ thành in hoa rồi thêm vào kết quả.
            }
        }
        return sb.toString();  // Trả về key đã chuẩn hóa 
    }
    public static String encrypt(String plaintext, String key) {//Hàm giải mã
        String k = normalizeKey(key);  // Chuẩn hóa key trước khi dùng.
        if (k.isEmpty()) {
            throw new IllegalArgumentException("Key must contain at least one letter.");
        }
        // Nếu key trống sau khi chuẩn hóa → báo lỗi.

        StringBuilder out = new StringBuilder();  // Dùng để xây chuỗi kết quả Ciphertext.
        int keyLen = k.length();  // Độ dài của key
        int keyIndex = 0;  // Vị trí hiện tại trong key.

        // Duyệt qua từng ký tự trong plaintext
        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {  // Nếu ký tự là chữ cái → thực hiện mã hóa
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                // Xác định “cơ sở” để tính mã (A cho chữ in hoa, a cho chữ thường)

                int p = ch - base;  // Đưa ký tự về dạng số
                char keyChar = k.charAt(keyIndex % keyLen);  // Lấy ký tự khóa tương ứng
                int shift = keyChar - 'A';  // Độ dịch của ký tự khóa
                char cipherChar = (char) ((p + shift) % 26 + base);
                // Tính ký tự mã hóa: (vị trí + độ dịch) mod 26 rồi cộng lại để ra ký tự mới.

                out.append(cipherChar);  // Thêm ký tự mã hóa vào kết quả.
                keyIndex++;  // Tăng vị trí trong key.
            } else {
                out.append(ch);  // Nếu không phải chữ → giữ nguyên.
            }
        }
        return out.toString();  // Trả về chuỗi mã hóa hoàn chỉnh.
    }
    // Hàm giải mã 

    public static String decrypt(String ciphertext, String key) {
        String k = normalizeKey(key);  // Chuẩn hóa key.
        if (k.isEmpty()) {
            throw new IllegalArgumentException("Key must contain at least one letter.");
        }

        StringBuilder out = new StringBuilder();  // Dùng để ghép chuỗi giải mã.
        int keyLen = k.length();
        int keyIndex = 0;

        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {  // Chỉ giải mã ký tự là chữ cái.
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int c = ch - base;  // Chuyển ký tự thành số (A→0, B→1, …)
                char keyChar = k.charAt(keyIndex % keyLen);  // Lấy ký tự khóa tương ứng.
                int shift = keyChar - 'A';  // Độ dịch của ký tự khóa.
                char plainChar = (char) ((c - shift + 26) % 26 + base);
                // Giải mã: trừ độ dịch, cộng 26 để tránh âm, rồi mod 26.

                out.append(plainChar);  // Thêm ký tự giải mã vào kết quả.
                keyIndex++;
            } else {
                out.append(ch);  // Ký tự không phải chữ → giữ nguyên.
            }
        }
        return out.toString();  // Trả về chuỗi đã giải mã.
    }
    public static String generateRepeatingKey(String plaintext, String key) {
        String normalizedKey = normalizeKey(key); // Chuẩn hóa key (loại bỏ ký tự lạ, viết hoa)
        if (normalizedKey.isEmpty()) {
            throw new IllegalArgumentException("Key must contain at least one letter.");
        }

        // Đếm số lượng ký tự trong plaintext (tùy chọn: có thể chỉ đếm ký tự là chữ)
        int length = plaintext.length();

        StringBuilder fullKey = new StringBuilder();

        int keyIndex = 0; // vị trí trong key gốc
        for (int i = 0; i < length; i++) {
            char ch = plaintext.charAt(i);
            if (Character.isLetter(ch)) {
                // Nếu ký tự là chữ → thêm ký tự key tương ứng
                fullKey.append(normalizedKey.charAt(keyIndex % normalizedKey.length()));
                keyIndex++;
            } else {
                // Nếu ký tự không phải chữ → vẫn thêm ký tự placeholder (để giữ độ dài)
                fullKey.append(ch);
            }
        }

        return fullKey.toString(); // Trả về key đã mở rộng
    }
}
