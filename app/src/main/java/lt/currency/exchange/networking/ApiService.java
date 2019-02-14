package lt.currency.exchange.networking;

import io.reactivex.Observable;
import lt.currency.exchange.model.response.ExchangeResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    Observable<ExchangeResponse> exchange(@Path("fromAmount") String fromAmount,
                                          @Path("fromCurrency") String fromCurrency,
                                          @Path("toCurrency") String toCurrency);
}
