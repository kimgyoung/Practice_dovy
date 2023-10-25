import java.util.Scanner;
public class User {

    //필드 값이 2개 존재 - 데이터는 무조건 ** private!! (가독성 부분에서 꼭 필요)
    private String name;
    private String password;
    private int age;

    //** name
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    //** password
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    //** age
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    //** 생성자
    public User(){}


    /*

    // Get
    public String getName() {return name;}
    public String getPassword() {return password;}
    public int getAge() {return age;}

    // Set
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
    public void setAge(int password) {this.age = age;}

     */


    /*
    // 1 User = 생성자(=Class 이름), 함수 이름 밖에 없어도 반환 (= 반환 형태가 없음)
    public User()
    {
        this.name = "";
        this.password = "";
        this.age = 0;
    }

    //2 복사 생성자()
    public User(String name, String password, int age)
    {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    /*
    public User(int age)
    {
        this.name = "";
        this.age = age;
    }

// 1 이랑 2 동시 생성 가능

    public User(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

     */
// 단 괄호 안 같은 거 반복X 경우의 수처럼 다달라야

/*
    // 함수는 기본적으로 ** public!!
    private String getName()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("이름을 입력해 주세요: ");
        String name = scanner.nextLine();

        return name;
    }

    // 일반 함수
    public void Initialize()
    {
        this.name = getName();
    }
    */

    public User(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public void Update()
    {
        System.out.println(name);
    }

    private void output()
    {
        System.out.println("입력하신 이름은" + name + "입니다.");
    }






}
