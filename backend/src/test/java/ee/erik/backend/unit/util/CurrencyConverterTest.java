package ee.erik.backend.unit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ee.erik.backend.util.CurrencyConverter;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterTest {
    @Test
    public void converterShouldReturnDefaultValueWhenCurrencyNameIsRandomString() {
        BigDecimal valueInEuros = new BigDecimal(1.00);
        assertEquals(CurrencyConverter.convertTo(valueInEuros, "woeifhwfw"), valueInEuros);
    }

    @Test
    public void converterShouldReturnDefaultValueWhenCurrencyNameIsNull() {
        BigDecimal valueInEuros = new BigDecimal(1.00);;
        assertEquals(CurrencyConverter.convertTo(valueInEuros, null), valueInEuros);
    }
}
