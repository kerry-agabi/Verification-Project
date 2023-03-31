package cme;

import java.math.BigDecimal;

public interface RateStrategy {
    BigDecimal applyRate(BigDecimal calculatedCost);
}
