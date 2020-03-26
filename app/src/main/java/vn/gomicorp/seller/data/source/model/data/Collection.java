package vn.gomicorp.seller.data.source.model.data;

import android.os.Parcelable;

import java.util.List;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class Collection {
    int type;
    String name;
    List<Parcelable> data;

    public Collection(int type, List<Parcelable> data) {
        this.type = type;
        this.data = data;
    }

    public Collection(int type, String name, List<Parcelable> data) {
        this.type = type;
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Parcelable> getData() {
        return data;
    }

    public void setData(List<Parcelable> data) {
        this.data = data;
    }
}
