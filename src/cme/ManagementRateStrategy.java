package cme;

import java.math.BigDecimal;

public class ManagementRateStrategy implements RateStrategy {
    @Override
    public BigDecimal applyRate(BigDecimal calculatedCost) {
        BigDecimal minimumPayable = BigDecimal.valueOf(5);
        return calculatedCost.max(minimumPayable);
    }
}
