package vn.gomicorp.seller.event;

import vn.gomicorp.seller.data.source.model.data.Collection;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public interface CollectionHandler {
    void onSelect(Collection collection);
}
