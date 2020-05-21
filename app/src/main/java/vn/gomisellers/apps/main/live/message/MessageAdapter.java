package vn.gomisellers.apps.main.live.message;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.gomisellers.apps.databinding.ListMsgItemBinding;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<MessageBean> messageBeanList;

    public MessageAdapter(List<MessageBean> messageBeanList) {
        this.messageBeanList = messageBeanList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return MyViewHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(messageBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageBeanList == null ? 0 : messageBeanList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ListMsgItemBinding binding;

        public static MyViewHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            ListMsgItemBinding binding = ListMsgItemBinding.inflate(inflater, viewGroup, false);
            return new MyViewHolder(binding);
        }

        private MyViewHolder(ListMsgItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MessageBean messageBean) {
            binding.setMsg(messageBean);
            binding.executePendingBindings();
        }
    }
}
