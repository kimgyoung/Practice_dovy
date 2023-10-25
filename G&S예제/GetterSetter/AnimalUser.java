public class AnimalUser {
        private String name;
        private int age;

            public AnimalUser(String name, int age){
            this.name = name;
            this.age = age;
        }

        public String getName(){return name;}
        public void setName(String name){this.name = name;}

        public int getAge(){return age;}
        public void setAge(int age){this.age = age;}


        //메서드 동물소리
        public void makeSound(){
                System.out.print(age +"살"+ " " + name + "가 짖습니다.");
        }

}
