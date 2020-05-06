package vn.gomisellers.apps.main.notification;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public class Notification {
    private int id;
    private boolean isRead;

    public Notification(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
