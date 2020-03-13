package vn.gomicorp.seller.data;

import java.util.List;

import vn.gomicorp.seller.data.source.local.db.AppLocalDataSource;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;
import vn.gomicorp.seller.data.source.model.data.Contry;
import vn.gomicorp.seller.data.source.remote.AppRemoteDataSource;

import static androidx.core.util.Preconditions.checkNotNull;

public class AppRepository implements AppDataSource {
    private volatile static AppRepository INSTANCE = null;
    private AppDataSource mLocalDataSource;
    private AppDataSource mRemoteDataSource;

    private AppRepository() {
        mLocalDataSource = new AppLocalDataSource();
        mRemoteDataSource = new AppRemoteDataSource();
    }

    public static AppRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void signin(SignInRequest request, ResultListener<AccountInfo> callback) {
        checkNotNull(callback);

        mRemoteDataSource.signin(request, callback);
    }

    @Override
    public void signup(SignUpRequest request, ResultListener<AccountInfo> callback) {
        checkNotNull(callback);

        mRemoteDataSource.signup(request, callback);
    }

    @Override
    public void getContry(ResultListener<List<Contry>> callback) {
        mRemoteDataSource.getContry(callback);
    }
}
