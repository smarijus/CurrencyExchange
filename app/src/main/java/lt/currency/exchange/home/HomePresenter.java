package lt.currency.exchange.home;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import lt.currency.exchange.base.BasePresenter;
import lt.currency.exchange.base.ErrorHandler;
import lt.currency.exchange.common.CurrentBalanceUseCase;
import lt.currency.exchange.common.exceptions.NotEnoughMoneyException;
import lt.currency.exchange.constans.enums.CurrencyType;

public class HomePresenter extends BasePresenter<IHomeView> {

    private final CurrentBalanceUseCase currentBalanceUseCase;

    @Inject
    public HomePresenter(ErrorHandler errorHandler, CurrentBalanceUseCase currentBalanceUseCase) {
        super(errorHandler);
        this.currentBalanceUseCase = currentBalanceUseCase;
    }

    public void loadData() {
        loadCurrentBalance();
        loadPaymentFee();
        loadCurrentFromCurrency();
    }

    private void loadPaymentFee() {
        subscriptions.add(currentBalanceUseCase.loadPaymentFee().subscribe(
                currencyTypes -> getView().onPaymentFeeReady(currencyTypes),
                throwable -> getView().showError(throwable, errorHandler.getErrorMessage(throwable))));
    }

    private void loadCurrentFromCurrency() {
        subscriptions.add(currentBalanceUseCase.loadCurrentFromCurrency().subscribe(
                currencyTypes -> getView().onCurrencyTypesReady(currencyTypes),
                throwable -> getView().showError(throwable, errorHandler.getErrorMessage(throwable))));
    }

    private void loadCurrentBalance() {
        subscriptions.add(currentBalanceUseCase.loadCurrentBalance()
                .subscribe(currentBalanceItems -> getView().onDataReady(currentBalanceItems),
                        throwable -> getView().showError(throwable, errorHandler.getErrorMessage(throwable))));
    }

    public void loadToCurrency(CurrencyType fromCurrencyType) {
        subscriptions.add(currentBalanceUseCase.loadToCurrency(fromCurrencyType)
                .subscribe(currentBalanceItems -> getView().onLoadToCurrency(currentBalanceItems),
                        throwable -> getView().showError(throwable, errorHandler.getErrorMessage(throwable))));
    }

    public void add(Disposable subscribe) {
        subscriptions.add(subscribe);
    }

    public void onNextClick(String fromAmount, CurrencyType fromCurrencyType, CurrencyType toCurrencyType) {
        subscriptions.add(currentBalanceUseCase.exchange(fromAmount, fromCurrencyType, toCurrencyType)
                .doOnSubscribe(o -> getView().enableConvertButton(false))
                .doOnTerminate(() -> getView().enableConvertButton(true))
                .subscribe(exchangeMessage -> {
                            loadData();
                            getView().setMessage(exchangeMessage);
                        },
                        throwable -> showError(throwable, errorHandler.getErrorMessage(throwable))));
    }

    private void showError(Throwable throwable, String errorMessage) {
        if (throwable instanceof NotEnoughMoneyException) {
            getView().setMessage(errorMessage);
        } else {
            getView().showError(throwable, errorMessage);
        }
    }

    public void onTextChange(String inputValue) {
        getView().enableConvertButton(currentBalanceUseCase.parse(inputValue) != null);
        getView().setMessage(null);
    }
}
