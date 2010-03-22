package com.tuscanyscatours.using.impl;
import java.math.BigDecimal;
import org.osoa.sca.annotations.Property;
import com.tuscanyscatours.CurrencyConverter;

public class CurrencyConverterImpl implements CurrencyConverter {

    @Property
    protected String fromCurrency;

    @Property
    protected String toCurrency;

    public BigDecimal convert(BigDecimal amount) {
        return amount.multiply(getRate(toCurrency))
                     .divide(getRate(fromCurrency));
    }

    private BigDecimal getRate(String currency) {
        lastRate = lastRate.add(increment); // test code
        return lastRate; // test code                                                     A
    }
    
    private BigDecimal lastRate; // test code
    private BigDecimal increment; // test code

    CurrencyConverterImpl() { // test code
        try {
            lastRate = new BigDecimal("1.1");
            increment = new BigDecimal("0.2");
        } catch (Exception e) {
        }
    }
}
