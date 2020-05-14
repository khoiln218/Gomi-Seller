package vn.gomisellers.apps.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.gomisellers.apps.main.notification.NotificationAdapter;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public class NotificationBinding {
    @BindingAdapter("setNotifyAdapter")
    public static void setNotifyAdapter(RecyclerView recyclerView, NotificationAdapter adapter) {
        if (recyclerView.getAdapter() == null && adapter != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            adapter.addOnScrollListener(recyclerView);
            recyclerView.setAdapter(adapter);
        }
    }
}
