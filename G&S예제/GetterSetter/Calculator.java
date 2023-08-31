public class Calculator {
    private double currentValue;


    public Calculator() {
        this.currentValue = 10.0;
    }

    public double getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public void add(double value){
        this.currentValue += value;
    }

    public void subtact(double value){
        this.currentValue -= value;
    }
}
