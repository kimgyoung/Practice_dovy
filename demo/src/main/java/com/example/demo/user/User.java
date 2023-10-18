package com.example.demo.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// ** 생성자를 생성 해준다.
// ** 현재 생성자 생성 옵션이 protected로 설정 됨.

@Entity
//  ** 제약사항
// 1. Entity 어노테이션이 붙어 있는 클래스는 기본 생성자를 가지고 있어야 한다.
//    - 'public' 또는 'protected' 접근 수준을 가져야 한다.
// 2. @Entity 어노테이션이 붙어 있는 클래스는 상속을 받거나, 다른 Entity를  상속받을 수 있다.
// 3. Entity 클래스의 필드는 관계형 맵핑을 위해서 다른 어노테이션을 추가할 수 있다.
//    - @Column, @ID@, @oneToMany, @MantToOne 등등..

@Table(name = "com/example/demo/user")
// ** Table 이름을 'user'로 설정한다.

public class User {

    @Id // ** 해당 필드를 PK로 설정한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ** PK 자동 설정
    private int id;


    // ** length = 45 : 데이터베이스에서의 길이를 45로 설정
    // ** nullable = false : 이 컬럼을 null 값으로 둘 수 없다.
    @Column(length = 45, nullable = false)
    private String userName;


    // ** length = 100 : 데이터베이스에서의 길이를 100으로 설정
    // ** nullable = false : 이 컬럼을 null 값으로 둘 수 없다.
    // ** unique = true : 이 컬럼의 값을 유일한 값으로 설정
    //                  - 값이 중복될 수 없음.
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    private int age;

    // ** 빌더 패턴을 쉽게 구현할 수 있도록 해준다.
    // ** 주로 인자가 많거나, 인자를 선택적으로 지정해야 하는 경우 사용 됨
    @Builder
    public User(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
