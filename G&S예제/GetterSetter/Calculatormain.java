public class Calculatormain {
    public static void main(String[] args) {

        Calculator calc = new Calculator();

        calc.add(10);
        System.out.println(calc.getCurrentValue());

        calc.subtact(5);
        System.out.println(calc.getCurrentValue());

    }
}
