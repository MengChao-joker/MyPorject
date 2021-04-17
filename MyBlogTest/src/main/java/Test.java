
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Test {
    public static int a;

    public static void main(String[] args) {
        HashSet<Character> set = new HashSet<>();
        Scanner s = new Scanner(System.in);
        StringBuilder res = new StringBuilder("");
        while (s.hasNext()) {
            String str2 = s.nextLine();
            String str1 = s.nextLine();
            for (int i = 0; i < str1.length(); i++) {
                set.add(str1.charAt(i));
            }
            for (int i = 0; i < str2.length(); i++) {
                if (set.add(str2.charAt(i))) {
                    res.append(str2.charAt(i));
                }
            }
        }
        System.out.println(res);
    }
    public static void main2(String[] args) {
        Scanner s = new Scanner(System.in);
        int res = 0;
        int n = s.nextInt();
        int[] num = new int[3*n];
        String req = s.next();
        for(int i=0;i<n*3;i++){
            num[i] = Integer.parseInt(String.valueOf(req.charAt(i)));
        }
        Arrays.sort(num);
        for(int i=2;i<(n+1)*2;i+=2){
            res+=num[i];
        }
        System.out.print(res);
    }
    public static void main1(String[] args) {
        System.out.println(Test.a);
    }
}
