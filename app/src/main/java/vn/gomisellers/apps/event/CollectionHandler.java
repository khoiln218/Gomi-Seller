package vn.gomisellers.apps.event;

import vn.gomisellers.apps.data.source.model.data.Collection;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public interface CollectionHandler {
    void onSelect(Collection collection);
}
