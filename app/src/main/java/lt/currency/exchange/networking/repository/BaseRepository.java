package lt.currency.exchange.networking.repository;

import lt.currency.exchange.networking.ApiService;
import lt.currency.exchange.networking.SchedulerProvider;

public abstract class BaseRepository {

    protected final ApiService api;
    protected final SchedulerProvider schedulerProvider;

    public BaseRepository(ApiService api, SchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
    }

}