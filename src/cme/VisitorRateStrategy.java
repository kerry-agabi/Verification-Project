package cme;

import java.math.BigDecimal;

public class VisitorRateStrategy implements RateStrategy {
    @Override
    public BigDecimal applyRate(BigDecimal calculatedCost) {
        BigDecimal freeAmount = BigDecimal.valueOf(10);
        if (calculatedCost.compareTo(freeAmount) <= 0) {
            return BigDecimal.ZERO;
        }
        return calculatedCost.subtract(freeAmount).multiply(BigDecimal.valueOf(0.5));
    }
}
