package lt.currency.exchange.networking.repository;

import java.math.BigDecimal;

import javax.inject.Inject;

import io.reactivex.Observable;
import lt.currency.exchange.constans.enums.CurrencyType;
import lt.currency.exchange.model.response.ExchangeResponse;
import lt.currency.exchange.networking.ApiService;
import lt.currency.exchange.networking.SchedulerProvider;

public class ExchangeRepository extends BaseRepository {

    @Inject
    public ExchangeRepository(ApiService api, SchedulerProvider schedulerProvider) {
        super(api, schedulerProvider);
    }

    public Observable<ExchangeResponse> exchange(BigDecimal fromAmount, CurrencyType fromCurrency, CurrencyType toCurrency) {
        return api.exchange(fromAmount.toPlainString(), fromCurrency.name(), toCurrency.name())
                .compose(schedulerProvider.applySchedulers());
    }
}
