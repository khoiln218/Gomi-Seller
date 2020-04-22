package vn.gomicorp.seller.main.home.withdrawn.bank;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.data.source.model.data.Bank;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class BankListViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {

    public static final int INIT_PAGE = 1;

    public MutableLiveData<BankListAdapter> bankListAdapter = new MutableLiveData<>();

    private ArrayList<Bank> banks;
    private BankListAdapter adapter;

    private int page;

    public BankListViewModel() {
        page = INIT_PAGE;
        banks = new ArrayList<>();
        adapter = new BankListAdapter(banks);
        bankListAdapter.setValue(adapter);
    }

    void requestBankList() {
        loaded();
        updateBank();
    }

    private void updateBank() {
        adapter.setBankList(banks);
    }

    @Override
    public void onRefresh() {
        page = INIT_PAGE;
        requestBankList();
    }

    void showLoading() {
        showProgressing();
    }
}
