package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public interface AppDataSource {
    void signin(SignInRequest request, ResultListener<AccountInfo> callback);

    void signup(SignUpRequest request, ResultListener<AccountInfo> callback);
}
