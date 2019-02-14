package lt.currency.exchange.common;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import javax.inject.Inject;

import lt.currency.exchange.constans.enums.CurrencyType;

public class DataHolder {

    public static final int FREE_PAY_FEE_COUNT = 5;

    Map<CurrencyType, BigDecimal> currentBalance = new EnumMap<CurrencyType, BigDecimal>(CurrencyType.class) {{
        put(CurrencyType.EUR, new BigDecimal(1000));
        put(CurrencyType.USD, new BigDecimal(0));
        put(CurrencyType.JPY, new BigDecimal(0));
    }};

    Map<CurrencyType, BigDecimal> totalFee = new EnumMap<CurrencyType, BigDecimal>(CurrencyType.class) {{
        put(CurrencyType.EUR, new BigDecimal(0));
    }};

    int transactionCount = 0;

    @Inject
    public DataHolder() {

    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void increaseTransactionCount() {
        transactionCount += 1;
    }

    public void onIncomes(CurrencyType currency, BigDecimal amount) {
        if (!currentBalance.containsKey(currency))
            currentBalance.put(currency, amount);
        else {
            BigDecimal newAmount = currentBalance.get(currency).add(amount);
            currentBalance.put(currency, newAmount);
        }
    }

    public void onOutcomes(CurrencyType fromCurrencyType, BigDecimal discharge) {
        BigDecimal newAmount = currentBalance.get(fromCurrencyType).subtract(discharge);
        if (newAmount.compareTo(BigDecimal.ZERO) > 0 ) {
            currentBalance.put(fromCurrencyType, newAmount);
        } else {
            currentBalance.remove(fromCurrencyType);
        }
    }

    public void addPayFee(CurrencyType fromCurrencyType, BigDecimal payFee) {
        if (!totalFee.containsKey(fromCurrencyType)) {
            totalFee.put(fromCurrencyType, payFee);
        } else {
            totalFee.put(fromCurrencyType, totalFee.get(fromCurrencyType).add(payFee));
        }
    }
}
