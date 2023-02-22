import java.util.Scanner;

public class TestMethods {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 7; i++) {
            String line = sc.nextLine();
            System.out.print("System.out.println(\"");
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j)=='\\'){
                    System.out.print("\\\\");
                }else{
                    System.out.print(line.charAt(j));
                }
            }
            System.out.println("\");");
        }       
    }
}
