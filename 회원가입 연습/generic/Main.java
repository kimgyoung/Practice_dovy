import generic.Animal.Dog;
import generic.Factory;
import generic.Generic;

import java.util.ArrayList;
import java.util.List;

import static generic.Generic.output;

public class Main {
    public static void main(String[] args) {

        // 함수를 태블릿의 형태로 만들 수 있음
        Integer[] iArray = {1, 2, 3, 4, 5};
        output(iArray);

        Character[] cArray = {'H','i'};
        output(cArray);

        String[] Sstring = {"문자열", "입니다"};
        output(Sstring);

// ---------------------------------------------------------------


        Generic<Integer> generic1 = new Generic();
        Generic<String> generic2 = new Generic();

        Integer output1 = generic1.output(10);
        System.out.println(output1);

        String output2 = generic2.output("Hello world");
        System.out.println(output2);

        // 문자열로 처리 <String>으로 명명
        // -> Generic 클래스의 U를 String 으로

        // if <Integer>면 int형으로 명명 -> int만 사용 가능

        Generic.Inner<String> uInner = new Generic.Inner<>();
        String output3 = uInner.output("Hello Generic");
        //String output3 = uInner.output(10); XXX

        System.out.println(output3);

        //Generic<Long> generic3 = new Generic();


        // 값을 받았을 때 10이라고 넣으면 10이 되는 것
        // <Integer>면 int String이면 string 으로..<>
        // <List> 도 마찬 가지 / add = output
        // 뭘 넣던지 <>안의 값으로 바뀜

        Factory<Dog> dogFactory = new Factory<>(Dog.class);
        Dog dog = dogFactory.create();
        dog.output();

        List<?> i = new ArrayList<>();

        i = new ArrayList<Integer>();
        i = new ArrayList<String>();
        i = new ArrayList<Long>();




    }
}
