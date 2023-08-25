import java.util.logging.XMLFormatter;

public class test5 {
    public static void main(String[] args) {

        // ** 다차원 배열
        //long[][][] l = new long[2][2][2];

        /*
        long[][][] l = new long[][][]{
                {
                        {1,2},
                        {3,4}
                },
                {
                        {5,6},
                        {7,8}
                }

        };

         */

        //한반에 30명씩// 5반// 1, 2 ,3 학년
        // int[][][] i = new int[3][5][30];


        /*
        // 2차원 배열
        int [][] i = new int[2][2];

        i[0][0] = 1;
        i[0][1] = 2;
        i[1][0] = 3;
        i[1][1] = 4;

         */

        final int X = 3;
        final int Y = 3;
        final int Z = 2;

        // 3차원 배열
        int[][][] array = new int[Z][Y][X];

        array[0][0][0] = 1;     // or ?;
        array[0][0][1] = 2;     // or ?;
        array[0][1][0] = 3;     // or ?;
        array[0][1][1] = 4;     // or ?;
        array[1][0][0] = 5;     // or ?;
        array[1][0][1] = 6;     // or ?;
        array[1][1][0] = 7;     // or ?;
        array[1][1][1] = 8;     // or ?;


        /*
        for(int i = 0; i < 2; ++i)
        {
            for(int j = 0; j < 2; ++j)
            {
               for(int k = 0; k < 2; ++k)
                   System.out.println(array[i][j][k]);
            }
        }

         */

        int n = 0;
        for(int i = 0; i < Z; ++i)
        {
            for(int j = 0; j < Y; ++j)
            {
                for(int k = 0; k < X; ++k)
                {
                    array[i][j][k]= ++n;
                }

            }
        }


        String name = "홍길동";
        boolean isEqual = name.equals("홍길동");
        System.out.println(isEqual);

        /*
        int[] i;
        {
            int[] n = new int[]{10, 20, 30, 40};
            i = n;
        }

        //main i = 변수
        //-> n의 값을 i(name)에 주면
        //-> 메모리는 사라지고 없고{n스택} = 영역
        //(이름을 다 입력 받았으면 n스택을 없앰)
        //-> n이 받았던 값을 i가 가짐
        //
        //최종적으로 (입력 받았던)데이터만 남음
        //= 공간 최적화 = 메모리를 효율적으로 사용

         */




    }
}
