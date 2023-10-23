import java.util.Scanner;

public class test6 {

    public static String getname(String name)
    {
        System.out.println("입력하신 이름은" + name + "입니다.");

        return name;
    }



    public static void main(String[] args) {


        // ** Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);
        String[] names = new String[3];

        // ** 입력: 사용자에게 입력 안내 메시지 출력, 한 줄을 입력 받아 name 변수에 저장
        for(int i = 0; i < names.length; ++i)
        {
            System.out.print("이름을 입력해 주세요:");
            names[i] = scanner.nextLine();
        }

        // **  출력: 입력 받은 이를 출력
        for(int i = 0; i < names.length; ++i)
        {
            getname(names[i]);
            //System.out.println("입력하신 이름은" + names[i] + "입니다.");
        }


        // ** 사용자에게 입력 안내 메시지 출력
        System.out.print("비교할 이름을 입력해 주세요:");

        String name = scanner.nextLine();


        // ** 비교: 문자열 비교
        for(int i = 0; i < 3; ++i)
        {
            boolean isEqual = name.equals(names[i]);

            if(isEqual == true)
            {
                System.out.println("리스트에 ["+ names[i] +"] 이 있습니다.");
                break;
            }
        }


        scanner.close();








    }
}
