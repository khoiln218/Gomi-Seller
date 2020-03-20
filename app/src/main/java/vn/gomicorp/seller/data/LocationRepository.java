package vn.gomicorp.seller.data;

import java.util.List;

import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Location;
import vn.gomicorp.seller.data.source.remote.LocationRemoteDataSource;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class LocationRepository implements LocationDataSource {
    private volatile static LocationRepository INSTANCE = null;
    private LocationDataSource mRemoteDataSource;

    private LocationRepository() {
        mRemoteDataSource = new LocationRemoteDataSource();
    }

    public static LocationRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (LocationRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocationRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getLocationCountry(ResultListener<ResponseData<List<Location>>> callback) {
        checkNotNull(callback);
        mRemoteDataSource.getLocationCountry(callback);
    }

    @Override
    public void getLocationProvince(int id, ResultListener<ResponseData<List<Location>>> callback) {
        checkNotNull(callback);
        mRemoteDataSource.getLocationProvince(id, callback);
    }

    @Override
    public void getLocationDistrict(int id, ResultListener<ResponseData<List<Location>>> callback) {
        checkNotNull(callback);
        mRemoteDataSource.getLocationDistrict(id, callback);
    }
}
