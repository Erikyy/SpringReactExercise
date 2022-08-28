package ee.erik.backend.unit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ee.erik.backend.util.CurrencyConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterTest {

    //uses same rounding mode as currency converter
    static BigDecimal valueInEuros = new BigDecimal(1.00).setScale(2, RoundingMode.HALF_EVEN);

    @Test
    public void converterShouldReturnDefaultValueWhenCurrencyNameIsRandomString() {

        assertEquals(valueInEuros, CurrencyConverter.convertTo(valueInEuros, "woeifhwfw"));
    }

    @Test
    public void converterShouldReturnDefaultValueWhenCurrencyNameIsNull() {

        assertEquals(valueInEuros, CurrencyConverter.convertTo(valueInEuros, null));
    }
}
