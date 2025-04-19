package br.com.Glaudencio12.math;

public class SimpleMath {
    public double sum(double numberOne, double numberTwo){
        return numberOne + numberTwo;
    }

    public double subtraction(double numberOne, double numberTwo){
        return numberOne - numberTwo;
    }

    public double multiplication(double numberOne, double numberTwo){
        return numberOne * numberTwo;
    }

    public double division(double numberOne, double numberTwo){
        return numberOne / numberTwo;
    }

    public double average(double numberOne, double numberTwo){
        return sum(numberOne, numberTwo) / 2;
    }

    public double squareRoot(double number){
        return Math.sqrt(number);
    }
}
