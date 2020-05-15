package vn.gomisellers.apps.main.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.adapter.holder.LoadingHolder;
import vn.gomisellers.apps.data.source.model.data.Notification;
import vn.gomisellers.apps.databinding.NotifyItemBinding;
import vn.gomisellers.apps.event.NotificationHandler;
import vn.gomisellers.apps.event.OnLoadMoreListener;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public class NotificationAdapter extends RecyclerView.Adapter {

    private List<Notification> notificationList;
    private NotificationHandler notificationHandler;
    private OnLoadMoreListener onLoadMoreListener;

    private int pastVisibleItems;
    private int totalItemCount;

    boolean isLoading = true;

    public NotificationAdapter(OnLoadMoreListener onLoadMoreListener, NotificationHandler notificationHandle) {
        this.notificationHandler = notificationHandle;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void addOnScrollListener(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findLastVisibleItemPosition();

                    if (!isLoading && (pastVisibleItems + 5) >= totalItemCount) {
                        if (onLoadMoreListener != null)
                            onLoadMoreListener.onLoadMore();
                    }
                }
            });
        }
    }

    public void setNotifications(List<Notification> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemType.VIEW_ITEM) {
            return NotificationHolder.getInstance(parent);
        }
        return LoadingHolder.getInstance(parent);
    }

    public void setLoading() {
        isLoading = true;
    }

    @Override
    public int getItemViewType(int position) {
        Notification notification = notificationList.get(position);
        if (notification != null)
            return ItemType.VIEW_ITEM;
        return ItemType.VIEW_LOADING;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NotificationHolder) holder).bind(notificationList.get(position), notificationHandler);
        if (position == getItemCount() - 1)
            isLoading = false;
    }

    @Override
    public int getItemCount() {
        return notificationList == null ? 0 : notificationList.size();
    }

    static class NotificationHolder extends RecyclerView.ViewHolder {

        private NotifyItemBinding binding;

        public static NotificationHolder getInstance(ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            NotifyItemBinding binding = NotifyItemBinding.inflate(inflater, viewGroup, false);
            return new NotificationHolder(binding);
        }

        private NotificationHolder(NotifyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Notification notification, NotificationHandler notificationHandler) {
            binding.setNotificationHandler(notificationHandler);
            binding.setNotification(notification);
            binding.executePendingBindings();
        }
    }

    interface ItemType {
        int VIEW_LOADING = 0;
        int VIEW_ITEM = 1;
    }
}
