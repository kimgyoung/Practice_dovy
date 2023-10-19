package generic;

import generic.Animal.Animal;

public class Factory <T extends Animal>{
    // Class 대문자!!!!!
    // class NO!
    private Class<T> type;

    public Factory(Class<T> type){
        this.type = type;
    }

    public T create(){
        try {
            return type.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
