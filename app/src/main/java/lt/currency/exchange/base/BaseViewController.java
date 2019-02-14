package lt.currency.exchange.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import lt.currency.exchange.utils.KeyboardUtils;
import timber.log.Timber;

public abstract class BaseViewController extends ButterKnifeController implements IBaseView {

    @Override
    public boolean showError(Throwable throwable, String localizedMessage) {
        Timber.w(throwable);
        KeyboardUtils.hideKeyboard(getActivity());

        Toast.makeText(getActivity(), localizedMessage, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);
    }
}
