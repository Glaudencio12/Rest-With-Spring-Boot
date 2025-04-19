package br.com.Glaudencio12.controllers;

import br.com.Glaudencio12.exception.DivisionByZeroException;
import br.com.Glaudencio12.exception.UnsupportedMathOperationException;
import br.com.Glaudencio12.math.SimpleMath;
import br.com.Glaudencio12.converters.NumberConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    private SimpleMath math = new SimpleMath();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/subtraction/{numberOne}/{numberTwo}")
    public double subtraction(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public double multiplication(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)throws DivisionByZeroException {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        else if (numberTwo.equals("0")) {
            throw new DivisionByZeroException("Please set a number diferent of the zero in the division");
        }
        return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/average/{numberOne}/{numberTwo}")
    public double average(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws UnsupportedMathOperationException{
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return math.average(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
    }

    @GetMapping("/squareRoot/{number}")
    public double squareRoot(@PathVariable("number") String number) throws UnsupportedMathOperationException{
        if (!NumberConverter.isNumeric(number)) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return math.squareRoot(NumberConverter.convertToDouble(number));
    }
}
