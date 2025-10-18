/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChangeLineCipher;

/**
 *
 * @author H P
 */
public class clcCipher {
    public static String normalizeText(String s) {
        // Xóa toàn bộ khoảng trắng 
        // để đảm bảo dữ liệu mã hóa liền mạch
        s = s.replaceAll("\\s+", "");

        // Chuyển toàn bộ sang chữ hoa
        s = s.toUpperCase();

        // Trả về chuỗi đã chuẩn hóa
        return s;
    }
    public static int[] generatePermutationFromKeyString(String key) {
        // Bỏ khoảng trắng, đổi sang chữ hoa cho đồng nhất
        final String fikey = key.replaceAll("\\s+", "").toUpperCase();

        // Độ dài của key
        int n = key.length();

        // Tạo mảng chỉ số ban đầu
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        // Sắp xếp các chỉ số theo thứ tự chữ cái của khóa
        // Nếu ký tự trùng nhau thì giữ nguyên thứ tự xuất hiện
        java.util.Arrays.sort(index, (a, b) -> {
            int cmp = Character.compare(fikey.charAt(a), fikey.charAt(b));
            if (cmp != 0) {
                return cmp; // so sánh ký tự
            }
            return Integer.compare(a, b); // nếu bằng nhau, giữ nguyên thứ tự
        });

        // Tạo mảng kết quả để lưu thứ hạng
        int[] perm = new int[n];

        // Gán thứ hạng cho từng vị trí trong key
        for (int rank = 0; rank < n; rank++) {
            // index[rank] là vị trí ban đầu của ký tự có rank này
            perm[index[rank]] = rank + 1; // +1 để thành dạng 1-based
        }

        // Trả về mảng hoán vị
        return perm;
    }
    public static String encrypt(String plain, int[] perm, char pad) {
        // Chuẩn hóa chuỗi bản rõ (Plaintext)
        plain = normalizeText(plain);

        // Số cột = độ dài của khóa
        int cols = perm.length;

        // Tính số hàng (nếu không chia hết thì làm tròn lên)
        int rows = (plain.length() + cols - 1) / cols;

        // Tạo bảng 2 chiều (ma trận) chứa các ký tự
        char[][] matrix = new char[rows][cols];

        // Con trỏ chỉ vị trí ký tự hiện tại trong plaintext
        int pos = 0;

        //Ghi bản rõ vào bảng theo từng hàng
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (pos < plain.length()) {
                    // Nếu vẫn còn ký tự trong plaintext
                    matrix[r][c] = plain.charAt(pos++);
                } else {
                    // Nếu hết ký tự thì thêm ký tự pad (ví dụ: 'X')
                    matrix[r][c] = pad;
                }
            }
        }

        //Đọc bảng theo thứ tự hoán vị cột → tạo Ciphertext
        StringBuilder cipher = new StringBuilder();

        // Duyệt theo thứ tự rank (1 → n)
        for (int rank = 1; rank <= cols; rank++) {

            // Tìm cột nào có rank tương ứng
            int colIndex = -1;
            for (int i = 0; i < cols; i++) {
                if (perm[i] == rank) {
                    colIndex = i;
                    break;
                }
            }

            // Đọc toàn bộ ký tự trong cột này từ trên xuống
            for (int r = 0; r < rows; r++) {
                cipher.append(matrix[r][colIndex]);
            }
        }

        // Trả về chuỗi Ciphertext
        return cipher.toString();
    }
    public static String decrypt(String cipher, int[] perm, char pad) {
        // Số cột = độ dài của khóa
        int cols = perm.length;

        // Số hàng = tổng độ dài ciphertext / số cột
        int rows = cipher.length() / cols;

        // Tạo bảng 2 chiều rỗng để điền ký tự
        char[][] matrix = new char[rows][cols];

        // Con trỏ ký tự hiện tại trong ciphertext
        int pos = 0;

        // Điền các ký tự theo từng cột dựa trên thứ tự hoán vị
        for (int rank = 1; rank <= cols; rank++) {

            // Xác định cột nào tương ứng với rank hiện tại
            int colIndex = -1;
            for (int i = 0; i < cols; i++) {
                if (perm[i] == rank) {
                    colIndex = i;
                    break;
                }
            }

            // Điền các ký tự vào cột tương ứng từ trên xuống
            for (int r = 0; r < rows; r++) {
                matrix[r][colIndex] = cipher.charAt(pos++);
            }
        }

        //Đọc lại ma trận theo hàng để thu được Plaintext
        StringBuilder plain = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                plain.append(matrix[r][c]);
            }
        }

        //Xóa các ký tự pad ở cuối (nếu có)
        while (plain.length() > 0 && plain.charAt(plain.length() - 1) == pad) {
            plain.setLength(plain.length() - 1);
        }

        // Trả về Plaintext đã giải mã
        return plain.toString();
    }
}
