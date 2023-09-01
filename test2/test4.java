import java.util.Scanner;

public class test4 {
    public static void main(String[] args) {

        /*
        char[] numbers = {'A', 'B', 'C', 'D', 'E'};

        for(int i = 0; i < numbers.length; ++i)
        {
            System.out.println(numbers[i]);
        }
        */

        //System.out.println(numbers[numbers.length - 1]);


        //System.out.println(new String(numbers));

        //int length = numbers.length;

        //System.out.printf("%d\n",length);

        /*
        // ** Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);

        // ** 사용자에게 입력 안내 메시지 출력
        System.out.print("이름을 입력해 주세요:");

        // ** 사용자로 부터 한 줄을 입력 받아 name 변수에 저장
        String name = scanner.nextLine();

        // ** 입력 받은 이를 출력
        System.out.println("입력하신 이름은" + name + "입니다.");

        // ** Scanner 객체 닫기
        scanner.close();
*/

        //for문 길이가 3인 배열 /new 문자열 - 스트링을 배열3 만들어서/ 홍길동 임꺽정 이목룡 -> 순차적으로 출력

        Scanner scanner = new Scanner(System.in);
        String[] names = new String[3];

        for(int i = 0; i < names.length; ++i)
        {
            System.out.print("이름을 입력해 주세요:");
            names[i] = scanner.nextLine();
        }

        for(int i = 0; i < names.length; ++i)
        {
            System.out.println("입력하신 이름은" + names[i] + "입니다.");
        }










    }
}
