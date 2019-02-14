package lt.currency.exchange.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import lt.currency.exchange.ApplicationApp;
import lt.currency.exchange.R;
import lt.currency.exchange.base.BaseViewController;
import lt.currency.exchange.common.DecimalDigitsInputFilter;
import lt.currency.exchange.constans.enums.CurrencyType;

public class HomeController extends BaseViewController implements IHomeView {

    @BindView(R.id.payment_fee_recycler_view)
    RecyclerView paymentFeeRecyclerView;

    @BindView(R.id.balance_recycler_view)
    RecyclerView balanceRecyclerView;

    @BindView(R.id.from_currency)
    Spinner fromCurrencyView;

    @BindView(R.id.to_currency)
    Spinner toCurrencyView;

    @BindView(R.id.from_amount)
    EditText fromAmountView;

    @BindView(R.id.message)
    TextView messageView;

    @BindView(R.id.convert)
    Button convertButton;

    private final FlexibleAdapter<CurrentBalanceItem> currentBalanceAdapter = new FlexibleAdapter<>(null);
    private final FlexibleAdapter<CurrentBalanceItem> paymentFeeAdapter = new FlexibleAdapter<>(null);

    @Inject
    HomePresenter homePresenter;

    private CurrencyAdapter toCurrencyAdapter;
    private CurrencyAdapter fromCurrencyAdapter;
    private CurrencyType lastFromCurrencyItems;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ApplicationApp.getAppComponent(getActivity()).inject(this);
        return inflater.inflate(R.layout.controller_home, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        fromAmountView.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});

        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        balanceRecyclerView.setAdapter(currentBalanceAdapter);

        paymentFeeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        paymentFeeRecyclerView.setAdapter(paymentFeeAdapter);

        convertButton.setOnClickListener(v -> homePresenter.onNextClick(
                fromAmountView.getText().toString(),
                fromCurrencyAdapter.getItem(fromCurrencyView.getSelectedItemPosition()),
                toCurrencyAdapter.getItem(toCurrencyView.getSelectedItemPosition())));

    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        homePresenter.setView(this);
        homePresenter.loadData();

        homePresenter.add(RxTextView.afterTextChangeEvents(fromAmountView)
                .skipInitialValue()
                .subscribe(inputValue -> homePresenter.onTextChange(inputValue.editable().toString())));
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        homePresenter.setView(null);
    }

    @Override
    public void onDataReady(List<CurrentBalanceItem> currentBalanceItems) {
        currentBalanceAdapter.updateDataSet(currentBalanceItems);
    }

    @Override
    public void onCurrencyTypesReady(List<CurrencyType> fromCurrencyItems) {
        if (fromCurrencyAdapter != null) {
            fromCurrencyAdapter.setData(fromCurrencyItems);
            int position = fromCurrencyItems.indexOf(lastFromCurrencyItems);
            if (position != fromCurrencyView.getSelectedItemPosition()) {
                homePresenter.loadToCurrency(fromCurrencyItems.get(position < 0 ? 0 : position));
            }

            return;
        }
        fromCurrencyAdapter = new CurrencyAdapter(getActivity(), fromCurrencyItems);
        fromCurrencyView.setAdapter(fromCurrencyAdapter);
        fromCurrencyView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastFromCurrencyItems = fromCurrencyItems.get(position);
                homePresenter.loadToCurrency(lastFromCurrencyItems);
        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onLoadToCurrency(List<CurrencyType> toCurrencyItems) {
        toCurrencyAdapter = new CurrencyAdapter(getActivity(), toCurrencyItems);
        toCurrencyView.setAdapter(toCurrencyAdapter);
    }

    @Override
    public void enableConvertButton(boolean enabled) {
        convertButton.setEnabled(enabled);
    }

    @Override
    public void setMessage(String message) {
        messageView.setText(message);
    }

    @Override
    public void onPaymentFeeReady(List<CurrentBalanceItem> paymentFeeList) {
        paymentFeeAdapter.updateDataSet(paymentFeeList);
    }
}
