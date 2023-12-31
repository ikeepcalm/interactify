package dev.ikeepcalm.interactify.handlers;

import dev.ikeepcalm.interactify.interfaces.DoubleInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class DoubleHandler implements DoubleInterface {


    private final Scanner scanner;

    public DoubleHandler(Scanner scanner){
        this.scanner = scanner;
    }

    @Override
    public double askForDouble(String prompt) {
        double input;
        boolean validInput;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
            input = scanner.nextDouble();
            scanner.nextLine();
            validInput = !Double.isNaN(input) && !Double.isInfinite(input);
            if (!validInput) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (!validInput);
        return input;
    }


    @Override
    public double askForDoubleInRange(String prompt, double min, double max) {
        double input;
        do {
            input = askForDouble(prompt);
        } while (input < min || input > max);
        return input;
    }


    @Override
    public double askForDoubleWithDecimalLimit(String prompt, int decimalPlaces) {
        double input;
        BigDecimal roundedValue;

        boolean validInput;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextBigDecimal()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }

            BigDecimal userInput = scanner.nextBigDecimal();
            roundedValue = userInput.setScale(decimalPlaces, RoundingMode.HALF_UP);
            input = roundedValue.doubleValue();

            validInput = !Double.isNaN(input) && !Double.isInfinite(input) && userInput.compareTo(BigDecimal.valueOf(input)) == 0;

            if (!validInput) {
                System.out.println("Invalid input. Please enter a number with up to " + decimalPlaces + " decimal places.");
            }

            scanner.nextLine();
        } while (!validInput);

        return input;
    }


    @Override
    public double roundToDecimalPlaces(double value, int decimalPlaces) {
        long factor = (long) Math.pow(10, decimalPlaces);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public String convertToScientificNotation(double value, int decimalPlaces) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.toEngineeringString();
    }

}
