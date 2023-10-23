import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //User user = new User();
        //user.Initialize();
        //user.Update();

        //String str = "Hello World";
        //String str = new String("Hello World");

        // 주소로 접근 하려면 . 이 필요
        //str.length();

        // name, password, age 초기화 하고 출력하라

        // ** 생성자 호출 및 초기화
        User user = new User("홍길동", "11112222", 26);

        // ** 초기화된 내용을 각각 받아옴
        String name = user.getName();
        String password = user.getPassword();
        int age = user.getAge();
        //System.out.println(user.getName()); = 홍길동이 나오는지 검증

        //** 비교 연산 수행
        name = "임꺽정";

        //** 새로운 값 셋팅
        user.setName(name);

        //System.out.println(user.getName());
        user.Update();


    }
}