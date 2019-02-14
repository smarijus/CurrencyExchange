package lt.currency.exchange;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bluelinelabs.conductor.RouterTransaction;

import lt.currency.exchange.base.BaseRouterActivity;
import lt.currency.exchange.home.HomeController;

public class MainActivity extends BaseRouterActivity {

    @Override
    public void onRouterInit(@Nullable Bundle savedInstanceState) {
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new HomeController()));
        }
    }
}
