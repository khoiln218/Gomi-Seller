package vn.gomisellers.apps.main.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.gomisellers.apps.databinding.NotifyItemBinding;
import vn.gomisellers.apps.event.NotificationHandler;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public class NotificationAdapter extends RecyclerView.Adapter {

    private List<Notification> notificationList;
    private NotificationHandler notificationHandler;

    public NotificationAdapter(NotificationHandler notificationHandle) {
        this.notificationHandler = notificationHandle;
    }

    public void setNotifications(List<Notification> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }

    public void notifyItemChanged(Notification notification) {
        if (notification.isRead()) return;
        int indexChange = notificationList.indexOf(notification);
        if (indexChange != -1) {
            notificationList.get(indexChange).setRead(true);
            notifyItemChanged(indexChange);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NotificationHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NotificationHolder) holder).bind(notificationList.get(position), notificationHandler);
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
}
