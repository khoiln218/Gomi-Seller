package vn.gomisellers.apps.main.home.withdrawn.bank;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.data.source.model.data.Bank;
import vn.gomisellers.apps.databinding.HolderBankItemBinding;

/**
 * Created by KHOI LE on 4/9/2020.
 */
class BankListAdapter extends RecyclerView.Adapter {

    private List<Bank> bankList;

    public BankListAdapter(List<Bank> bankList) {
        this.bankList = bankList;
    }

    public void setBankList(List<Bank> bankList) {
        this.bankList = bankList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BankInfoHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BankInfoHolder) holder).bind(bankList.get(position));
    }

    @Override
    public int getItemCount() {
        return bankList == null ? 0 : bankList.size();
    }

    private static class BankInfoHolder extends RecyclerView.ViewHolder {
        private HolderBankItemBinding binding;

        public static BankInfoHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            HolderBankItemBinding binding = HolderBankItemBinding.inflate(inflater, viewGroup, false);
            return new BankInfoHolder(binding);
        }

        private BankInfoHolder(HolderBankItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bank bank) {
            binding.setBank(bank);
            binding.executePendingBindings();
        }
    }
}
