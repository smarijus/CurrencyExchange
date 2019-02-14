package lt.currency.exchange.di.module;

import dagger.Module;
import dagger.Provides;
import lt.currency.exchange.networking.ApiService;
import lt.currency.exchange.networking.SchedulerProvider;
import lt.currency.exchange.networking.repository.ExchangeRepository;

@Module
public class RepositoryModule {

    @Provides
    public ExchangeRepository provideExchangeRepository(ApiService apiService, SchedulerProvider schedulerProvider) {
        return new ExchangeRepository(apiService, schedulerProvider);
    }
}
