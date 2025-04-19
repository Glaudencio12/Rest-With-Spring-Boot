package br.com.Glaudencio12.converters;

import br.com.Glaudencio12.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public static double convertToDouble(String strNumber) throws UnsupportedMathOperationException {
        String number = strNumber.replace(",", ".");
        if (strNumber.isEmpty()) {
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        String number = strNumber.replace(",", ".");
        if (strNumber.isEmpty()) {
            return false;
        }
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}

