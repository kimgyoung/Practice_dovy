package generic;

public class Generic<T> {

    // 함수를 T 태블릿? 형태로 만들 것이냐
    // 배열을 넘겼을 때

    // 형태가 틀린데 하나의 List로 담고 싶을 때!

    // 여러개의 오브젝트를 하나의 그.. 배열로 넘겨줄 때?
    // ex 게임할 때의 총알? Boss가 쓰는 총알 종류, 패턴, 크기 등등 도 다 틀린데 하나의 리스트에 넣어놔야 관리가 편하지
    // 같은 몬스터가 쓰는 총알이라면 형태는 틀린데 하나의 리스트로 넣고 싶을 수 있음

    public static <T> void output(T[] array){
        for(T element : array){
            System.out.println(element);

        }
    }





//---------------------------------------------------------

    private T value;

    public T output(T t){
        value = t;
        return value;
    }

    // U가 String이면 Main에 String을 넣어야 문제가 안생김(문자열)
    public static class Inner<U>{
        private U innerValue;

        public U output(U u){
            innerValue = u;
            return innerValue;
        }
    }



    /*
    public static <T> InnerClass<T> Output(){
        return new InnerClass<>();
    }


    public static class InnerClass<T>{
        public T value;
    }

     */
}
