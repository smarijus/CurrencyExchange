package lt.currency.exchange.di.componens;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import lt.currency.exchange.ApplicationApp;
import lt.currency.exchange.base.ErrorHandler;
import lt.currency.exchange.common.AppSharedPreferences;
import lt.currency.exchange.common.CurrentBalanceUseCase;
import lt.currency.exchange.common.DataHolder;
import lt.currency.exchange.di.module.ApplicationModule;
import lt.currency.exchange.di.module.RepositoryModule;
import lt.currency.exchange.home.HomeController;
import lt.currency.exchange.networking.ApiService;
import lt.currency.exchange.networking.SchedulerProvider;
import lt.currency.exchange.networking.repository.ExchangeRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class})
public interface IApplicationComponent {

    void inject(ApplicationApp applicationApp);

    void inject(HomeController homeController);

    Context getContext();

    ErrorHandler getErrorHandler();

    CurrentBalanceUseCase getCurrentBalanceUseCase();

    DataHolder getDataHolder();

    Retrofit getRetrofit();

    SchedulerProvider getSchedulerProvider();

    Gson getGson();

    GsonConverterFactory getGsonConverterFactory();

    AppSharedPreferences getAppSharedPreferences();

    ApiService getApiService();

    ExchangeRepository getExchangeRepository();
}
