package ee.erik.backend.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Simple currency converter, converts euros to other currencies
 * currently has eur, usd and gbp,
 * 
 * NOTE!
 * in more complex scenarios this would be a service that fetches exchange rates every day, month or year from some api and stores those values to database
 * instead of using Map   
 */
public class CurrencyConverter {

    public static String DEFAULT = "eur";

    public static Map<String, Double> rates = Map.ofEntries(
        Map.entry("eur", 1.00),
        Map.entry("usd", 1.00),
        Map.entry("gbp", 0.84)
    );

    /**
     * Converts eur to other currency, if currency is set to null, by default it uses eur as it's currency
     * 
     * @param value amount to be converted
     * @param currency eur, usd or gbp
     * @return converted value
     */
    public static BigDecimal convertTo(BigDecimal value, String currency) {
       
        double rate = rates.get(currency == null || currency.equals("") || rates.get(currency) == null ? DEFAULT : currency);
        return value.multiply(new BigDecimal(rate)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
