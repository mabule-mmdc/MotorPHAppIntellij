package org.motorph;

public class Deductions {
    public static float computeSSS(float grossSalary) {
        float startSalaryRange = 3250;
        float contribution = 135;

        if (grossSalary < 3250) {
            return contribution;
        } else if (grossSalary >= 24750) {
            contribution = 1125;
            return contribution;
        } else {
            while (true) {
                float endSalaryRange = startSalaryRange + 500;
                contribution += 22.5;

                if (grossSalary >= startSalaryRange && grossSalary < endSalaryRange) {
                    return contribution;
                } else {
                    startSalaryRange = endSalaryRange;
                }
            }
        }
    }

    public static float computePhilHealth(float monthlyBasicSalary) {
        double contribution = 0.03;
        double totalContribution = 300;
        double totalEmployeeContribution = 0;

        if (monthlyBasicSalary > 10000) {
            if (monthlyBasicSalary >= 60000) {
                totalContribution = 1800;
            } else {
                totalContribution = monthlyBasicSalary * contribution;
            }
        }

        totalEmployeeContribution = totalContribution / 2;

        return (float)totalEmployeeContribution;
    }

    public static float computePagIBIG(float monthlyBasicSalary) {
        double contribution = 0.01;
        double totalEmployeeContribution = 0;

        if (monthlyBasicSalary > 1500) {
            contribution = 0.02;
        }

        totalEmployeeContribution = monthlyBasicSalary * contribution;

        if (totalEmployeeContribution > 100) {
            totalEmployeeContribution = 100;
        }

        return (float)totalEmployeeContribution;
    }


    public static float computeWithholdingTax(float taxableIncome) {
        double taxPercentage;
        double excessBaseline;
        double addedTax = 0;
        double finalTax;

        if (taxableIncome <= 20832) return 0;

        if (taxableIncome < 33333) {
            taxPercentage = 0.2;
            excessBaseline = 20833;
        } else if (taxableIncome < 66667) {
            taxPercentage = 0.25;
            addedTax = 2500;
            excessBaseline = 33333;
        } else if (taxableIncome < 166667) {
            taxPercentage = 0.3;
            addedTax = 10833;
            excessBaseline = 66667;
        } else if (taxableIncome < 666667) {
            taxPercentage = 0.32;
            addedTax = 40833.33;
            excessBaseline = 166667;
        } else {
            taxPercentage = 0.35;
            addedTax = 200833.33;
            excessBaseline = 666667;
        }

        finalTax = addedTax + ((taxableIncome - excessBaseline) * taxPercentage);

        return (float)finalTax;
    }
}
