package cme;

import java.math.BigDecimal;

public class StudentRateStrategy implements RateStrategy {
    @Override
    public BigDecimal applyRate(BigDecimal calculatedCost) {
        BigDecimal threshold = BigDecimal.valueOf(5.50);
        if (calculatedCost.compareTo(threshold) <= 0) {
            return calculatedCost;
        }
        return threshold.add(calculatedCost.subtract(threshold).multiply(BigDecimal.valueOf(0.67)));
    }
}
