package cme;

import java.math.BigDecimal;

public class StaffRateStrategy implements RateStrategy {
    @Override
    public BigDecimal applyRate(BigDecimal calculatedCost) {
        BigDecimal maximumPayable = BigDecimal.valueOf(10);
        return calculatedCost.min(maximumPayable);
    }
}
