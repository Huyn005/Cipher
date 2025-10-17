/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SingleLetterCodeTable;
import java.util.*;
/**
 *
 * @author H P
 */
public class SLCD {
   public boolean isValidKey(String key){//Hàm định dạng key
       if(key==null) return false; //Đảm bảo key không null
       key=key.trim().toUpperCase(); //Xóa khoảng trắng 2 đầu của key
       if(key.length()!=26) return false;// Đảm bảo key có 26 kí tự
       boolean[] used = new boolean[26];//Tạo một mảng kiểu boolean có 26 kí tự
       for(char c: key.toCharArray()){//Duyệt mảng
           if(c<'A'||c>'Z') return false;//Đảm bảo key chỉ nằm trong bảng chữ cái
           if(used[c-'A']) return false;//Đảm bảo chữ cái không trùng lặp
           used[c-'A']=true;//kích hoạt chữ cái xuất hiện lần đầu
       }
       return true;
   }
   public static String generateRandomKey(){//Hàm sinh Key
       List<Character> letters= new ArrayList<>();//Tạo danh sách kí tự
       for(char c='A';c<='Z';c++) letters.add(c);//Thêm bảng chữ cái từ A đến Z và mảng.
       Collections.shuffle(letters);//thực hiện xáo trộn thứ tự của các chữ cái.
       StringBuilder sb=new StringBuilder();//tạo một biến String Builder
       for(char c: letters) sb.append(c);//Ghép các chuỗi lại với nhau
       return sb.toString();  //Trả về kiểu String để trả về giá trị của hàm 
   }
   public static String encrypt(String plain, String key){//Hàm mã hóa
       key=key.toUpperCase();//Upper các kí tự trong Key để đồng bộ dễ thực hiện
       StringBuilder out = new StringBuilder();//tạo biến để lưu chuỗi
       for(char c: plain.toCharArray()){//Duyệt toàn plain text.
           if(Character.isUpperCase(c)){//Nếu chữ Hoa thì duyệt theo chỉ số của chữ hoa
               out.append(key.charAt(c-'A'));
           }else if(Character.isLowerCase(c)){
               char enc=key.charAt(c-'a');//Nếu là chữ thường thì duyệt theo chỉ số của chữ thường
               out.append(Character.toLowerCase(enc));
           }else{
               out.append(c);//Giữ nguyên các kí tự khác như chữ , số , kí tự đặc biệt.
           }
   }
       return out.toString();//Trả về kiểu String để trả về giá trị của hàm.
}
   public static String decrypt(String cipher, String key){
       key=key.toUpperCase();// Upper key để đồng bộ, dễ giải mã
       char[] inverse= new char[26]; //Tạo một mảng char gồm 26 kí tự
       for(int i=0;i<26;i++){// duyệt từ 0 đến 25
           inverse[key.charAt(i)-'A']=(char)('A'+i);
       }
       StringBuilder out= new StringBuilder();
       for(char c: cipher.toCharArray()){//Duyệt chuỗi cipher từ Cipher text
           if(Character.isUpperCase(c)){// Nếu là chữ hoa
               out.append(inverse[c-'A']);//Duyệt theo chỉ số của chữ hoa
           }else if(Character.isLowerCase(c)){//Nếu là chữ thường
               char dec=inverse[Character.toUpperCase(c)-'A'];//Duyệt theo chỉ số của chữ thường
               out.append(Character.toUpperCase(dec));
           }else{
               out.append(c);//Giữ nguyên các kí tự khác như: số, kí tự đặc biệt...
           }
       }return out.toString();//Trả về kiểu String để trả về cho hàm.
   }
}   
