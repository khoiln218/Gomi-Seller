package vn.gomisellers.apps.shopinfo;

import java.util.List;

import vn.gomisellers.apps.data.source.model.data.Location;

/**
 * Created by KHOI LE on 4/27/2020.
 */
public class LocationList {
    private List<Location> locations;
    private int selectIndex;

    public LocationList(List<Location> locations, int selectId) {
        this.locations = locations;
        this.selectIndex = selectId;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }
}
