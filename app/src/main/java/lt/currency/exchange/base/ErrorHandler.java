package lt.currency.exchange.base;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import lt.currency.exchange.R;
import lt.currency.exchange.common.exceptions.NotEnoughMoneyException;

@Singleton
public class ErrorHandler {

    private final Context context;

    @Inject
    public ErrorHandler(Context context) {
        this.context = context;
    }

    public String getErrorMessage(Throwable throwable) {
        if (throwable instanceof NotEnoughMoneyException) {
            return context.getString(R.string.error_not_enough_money);
        }
        return context.getString(R.string.error_message_generic);
    }
}
