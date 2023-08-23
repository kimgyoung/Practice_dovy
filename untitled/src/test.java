public class test {
    public static void main(String[] args) {

       // ** 분기문
       // if-else
       // if(조건)

        // ** 산술 연산자
        // [+] 더하기
        // [-] 빼기
        // [*] 곱하기
        // [/] 나누기
        // [%] 나머지

        /*
        982347892765
        난수 % 45 + 1 = (0 ~44)+1
        1 ~45
        */

        // ** 연산자 결합 사용 (이항 연산자 여야 함)
        // [=+] 더하기
        // [=-] 빼기
        // [=*] 곱하기
        // [=/] 나누기
        // [=%] 나머지


        // ** 비교 연산자 (항상 A기준)
        // [>]  A > B : A가 B보다 큰가?
        // [<]  A < B : A가 B보다 작은가?
        // [<=] A >= B : A가 B보다 크거나 같은가?
        // [>=] A <= B : A가 B보다 작거나 같은가?

        // [==] A == B A가 B와 같은가?
        // [!=] A != B A가 B와 다른가(같지 않은가)?


        // ** 논리 연산자
        // [&&] AND 그리고 : A와 B를 모두 충족
        // [||] OR 또는 : A와 B 중 하나만 충족
        // [!]  NOT ~아니다. : A가 아닐 때


        // ** 단항 연산자
        // [++] : 1씩 증가
        // 전위연산 : ++num
        // 후위연산 : nim++

        // [--] : 1씩 감소
        // 전위연산 : --num
        // 후위연산 : num--


        // ** 삼항 연산자
        // [A ? B : C] A 조건이 맞다면 B, 아니면 C를 반환

        /*
        int num1 = 10;
        int num2 = 20;

        int num3 = (num1 < num2) ? 100 : 200;
        */


        // 홀이면 홀 짝이면 짝으로 출력 /비교연산자 & 산술연산자
        //if(num1)


        int num1 = 20;

        if (num1%2 == 0){
            System.out.println("짝수");
        }
        else{
            System.out.println("홀수");
        }


        // ** 음수인지 양수 인지 확인하는 코드 작성

        int num2 = -20;

        if(num2 < 0)
        {
            System.out.println("음수");
        }
        else
        {
            System.out.println("양수");
        }


        // ** 학점


        int num3 = 80;

        if(num3 >= 95)
        {
            System.out.println("A 입니다.");
        }
        else if(num3 >= 85)
        {
            System.out.println("B 입니다.");
        }
        else if(num3 >= 75)
        {
            System.out.println("C 입니다.");
        }
        else if(num3 >= 65)
        {
            System.out.println("D 입니다.");
        }
        else
        {
            System.out.println("F 입니다.");
        }


        // ** 초를 (시)(분)(초)
        // 300초를 분으로 나타내기.

        /*
        int second = 705;

        int min = second / 60;
        second -= min * 60;

        System.out.println(min + "초" + second + "분");
        */

        //int month = 6;

        /*
        int value = 2;

        switch(value)
        {
            case 1:
                break;
            case 4:
                break;
            case 3:
                break;
            case 2:
                break;

        }
        */


        /*
        int value = 4;

        switch(value)
        {
            case 1:
                System.out.println("봄");
                break;

            case 2:
                System.out.println("여름");
                break;

            case 3:
                System.out.println("가을");
                break;

            case 4:
                System.out.println("겨울");
                break;

        }
        */


        int value = 12;

        switch(value)
        {
            case 1:
                System.out.println(value + "월");
                break;

            case 2:
                System.out.println(value + "월");
                break;

            case 3:
                System.out.println(value + "월");
                break;

            case 4:
                System.out.println(value + "월");
                break;

            case 5:
                System.out.println(value + "월");
                break;

            case 6:
                System.out.println(value + "월");
                break;

            case 7:
                System.out.println(value + "월");
                break;

            case 8:
                System.out.println(value + "월");
                break;

            case 9:
                System.out.println(value + "월");
                break;

            case 10:
                System.out.println(value + "월");
                break;

            case 11:
                System.out.println(value + "월");
                break;

            case 12:
                System.out.println(value + "월");
                break;

            default:
                System.out.println(value + "입력값이 1~12 사이의 값이 아닙니다.");
                break;
                // default 는 아무것도 해당 하는 값이 없을 때 무조건 출력되게 하는


        }











    }
}
