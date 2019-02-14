package lt.currency.exchange;

import android.app.Application;
import android.content.Context;

import io.reactivex.plugins.RxJavaPlugins;
import lt.currency.exchange.di.componens.DaggerIApplicationComponent;
import lt.currency.exchange.di.componens.IApplicationComponent;
import lt.currency.exchange.di.module.ApplicationModule;
import lt.currency.exchange.di.module.RepositoryModule;
import timber.log.Timber;

import static io.reactivex.internal.functions.Functions.emptyConsumer;


public class ApplicationApp extends Application {

    private IApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent(this).inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        RxJavaPlugins.setErrorHandler(emptyConsumer());
    }

    public static IApplicationComponent getAppComponent(Context context) {
        ApplicationApp app = (ApplicationApp) context.getApplicationContext();
        if (app.applicationComponent == null) {
            app.applicationComponent = DaggerIApplicationComponent.builder()
                    .applicationModule(app.getApplicationModule())
                    .repositoryModule(app.getRepositoryModule())
                    .build();
        }
        return app.applicationComponent;
    }

    protected RepositoryModule getRepositoryModule() {
        return new RepositoryModule();
    }

    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }
}
