package vn.gomicorp.seller.data.source.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ResetPwdRequest {

    /**
     * UserId : 1a240dcd-ec33-4159-b155-1fbc80f7b0ab
     * NewPassword : 123456@
     * VerifyCode : xu8xZkRahfpOazAwUXr9RCsnSTdxRmASMqfuzJ/qQdZqPMwRAiUKcEjMEaFiQKWHjQQ/BnJwass+0NR3MIjnXZomWeooh77P5AUF/SXouptE8h3QOmhhoCOBUk7PDygNfct3sgEhxouP5Qb/prjUjN5/ilQXnOXZXWXfjLQN+Y7Wkrz9cuT8XtBqnV6z/g8La5Fah9uXAq99Iht1gE6H7g==
     */

    private String UserId;
    private String NewPassword;
    private String VerifyCode;

    @JsonProperty("UserId")
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    @JsonProperty("NewPassword")
    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    @JsonProperty("VerifyCode")
    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String VerifyCode) {
        this.VerifyCode = VerifyCode;
    }
}
