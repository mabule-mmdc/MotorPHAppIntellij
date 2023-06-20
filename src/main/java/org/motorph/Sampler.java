package org.motorph;

import java.util.Scanner;
public class Sampler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String grossSalaryString = scanner.nextLine();
        System.out.println("Deducted amount is " + Deductions.computeWithholdingTax(Float.parseFloat(grossSalaryString)));
    }
}
