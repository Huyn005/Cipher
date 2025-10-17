/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CaesarCipherApp;

/**
 *
 * @author H P
 */
//class để thực hiện phần logic của Caesar Cipher
public class CaesarCipher {
    public static String encrypt(String plaintext ,int key){//Tạo class để xử lý phần mã hóa.
        StringBuilder sb = new StringBuilder();// tạo một chuỗi String để nhập phần plain text,cùng với đó cấp vùng nhớ cho nó.
        int k=key;//Khai báo một biến k để nhập key, 
        for(char ch: plaintext.toCharArray()){//chuyển chuỗi plaintext thành 1 mảng ký tự tên ch, và thực hiện duyệt qua từng ký tự.
            if(ch>='A' && ch <='Z'){//kiểm tra xem plain có ký tự in hoa hay không?
                sb.append((char)('A'+(ch-'A'+k)%26));//Vì theo bảng mã ASCII, A là 65, thế nên ta lấy ký tự trong chuỗi -A(65) + key để dịch chuyển và mod cho 26 để đảm bảo nằm trong phạm vi từ 1 đến 25
            }else if(ch >= 'a' && ch <='z'){//Tương tự như vậy nhưng với chữ thường
                sb.append((char)('a'+(ch-'a'+k)%26));
        }else{
                sb.append(ch);//trả về các ký tự không có ở 2 if trên ví dụ như: khoảng trắng, số và các ký tự đặc biết.
            }
    }
        return sb.toString();//chuyển từ StringBuilder thành String để trả về kết quả.
 }
    public static String decrypt(String ciphertext, int key){//Hàm này dùng để giải mã dựa và hàm mã hóa ở trên
        return encrypt(ciphertext, -key);//Điền biến vào là -key để dịch ngược lại
    }
}
    
