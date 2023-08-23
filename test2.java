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

        // Datatype

        char;
        short;
        int;
        long;
        float;
        double;
        




    }
}
