
public class Main{

    public static void main(String[] args){

        Man.setNumber(100);
        // 위의 100값이 언제 어디든 공유됨 - 스태틱이기 때문 주소가 1개기 때문에 다 그것만 참조중
        System.out.println(Man.number);

        //=======================================================//

        // 싱글톤.java의 겟인스턴스를 가져옴
        // (아무값도 없어야 함 - 왜?)
        // 1. null값을 줬기 때문에
        // 2. if 문의 뉴 싱글톤 (최초의 한번만 널)
        // 때문에 Singleton.getInstance(); 안에 아무 값도 없는 것

        Singleton.getInstance().setNumber(10);
        int number = Singleton.getInstance().getNumber();

        System.out.println(number);


    }
}
