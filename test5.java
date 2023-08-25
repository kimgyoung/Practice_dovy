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

        // 3차원 배열
        int[][][] array = new int[Z][Y][X];

        array[0][0][0] = 1;
        array[0][0][1] = 2;
        array[0][1][0] = 3;
        array[0][1][1] = 4;
        array[1][0][0] = 5;
        array[1][0][1] = 6;
        array[1][1][0] = 7;
        array[1][1][1] = 8;


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



        final int X = 3;
        final int Y = 3;
        final int Z = 2;

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




        








    }
}
