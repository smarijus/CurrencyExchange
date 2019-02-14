package lt.currency.exchange.model.response;

import java.math.BigDecimal;

import lt.currency.exchange.constans.enums.CurrencyType;

public class ExchangeResponse {

    BigDecimal amount;

    CurrencyType currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }
}
