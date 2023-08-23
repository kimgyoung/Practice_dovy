public class test2 {
    public static void main(String[] args) {

        // 서점
        // 판타지, 부동산
        // 로맨스, SA
        // 경매, 중매


        int value1 = 1;
        int value2 = 2;

        switch(value1)
        {
            case 1:
                System.out.println("판타지");

                switch (value2)
                {
                    case 1:
                        System.out.println("로맨스");
                        break;

                    case 2:
                        System.out.println("SF");
                        break;

                }
                break;

            case 2:
                System.out.println("부동산");

                switch (value2)
                {
                    case 1:
                        System.out.println("SA");
                        break;

                    case 2:
                        System.out.println("경매");
                        break;
                }

            break;
        }

        /*
        final int 부동산 = 0;
        final int 판타지 = 1;


        int value1 = 판타지;

        if((value1 & 부동산) == 1)
        {
            System.out.println("부동산");
        }
        else
        {
            System.out.println("판타지");
        }

        /*
        if((value1 & 부동산) == 1)
        {
            System.out.println("부동산");
        }
        if((value1 & 판타지) == 1)
        {
            System.out.println("판타지");
        }
        */

        // Datatype (in java)
        // 정수
        // char;    2Byte (유니코드) (문자 - 아스키코드 참조)
        // short;   2Byte
        // int;     4Byte
        // long;    8Byte

        // 실수(소수점)
        // float;  // 4Byte
        // double; // 8Byte

        /*
        // 변수 선언
        int number;

        // 초기화
        number = 0;
        */

        // 선언과 동시에 초기화
        int number = 10;


        // for (반복문)
        // 횟수
        // 초기문, 조건문, 증감문
        // for(초기문; 조건문; 증감문)


        for (int i = 0; i < 10; ++i)
        {
            System.out.println(i + "Hello World");

        }


        // while (반복문)
        // 무한 반복
        // while(조건문)
        // if (횟수를 정해 놓고 쓸 때는) 무조건 초기문, 조건문, 증감문 필요

        // 초기문
        int i= 10;

        // 조건문
        while (i < 10)
        {
            // 증감문
            ++i;
        }















    }
}
