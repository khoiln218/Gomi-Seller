package vn.gomisellers.apps.widgets;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.data.source.model.data.Location;

/**
 * Created by KHOI LE on 4/27/2020.
 */
public class LocationView extends WheelView {
    private List<Location> locations;

    public LocationView(Context context) {
        super(context);
    }

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLocations(List<Location> list) {
        locations = list;
        List<String> items = new ArrayList<>();
        for (Location item : list)
            items.add(item.getName());
        setItems(items);
    }

    public Location getSelectedLocation() {
        return locations.get(getSeletedIndex());
    }
}
