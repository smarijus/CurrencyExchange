package lt.currency.exchange.home;

import java.util.List;

import lt.currency.exchange.base.IBaseView;
import lt.currency.exchange.constans.enums.CurrencyType;

interface IHomeView extends IBaseView {

    void onDataReady(List<CurrentBalanceItem> currentBalanceItems);

    void onCurrencyTypesReady(List<CurrencyType> fromCurrencyItems);

    void onLoadToCurrency(List<CurrencyType> toCurrencyItems);

    void enableConvertButton(boolean enabled);

    void setMessage(String message);

    void onPaymentFeeReady(List<CurrentBalanceItem> paymentFeeList);
}
