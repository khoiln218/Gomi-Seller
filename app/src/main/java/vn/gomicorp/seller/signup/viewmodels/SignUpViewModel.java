package vn.gomicorp.seller.signup.viewmodels;

import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.signup.models.UserInfo;

public class SignUpViewModel extends ViewModel {
    public String TAG = getClass().getSimpleName();
    public UserInfo userInfo;

    public void dangky(){
        //TODO: send info of user to server and show result
    }
}
