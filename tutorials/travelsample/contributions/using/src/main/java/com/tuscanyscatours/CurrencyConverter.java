package com.tuscanyscatours;
import java.math.BigDecimal;
import org.osoa.sca.annotations.Remotable;

@Remotable
public interface CurrencyConverter {
    BigDecimal convert(BigDecimal amount);
}
