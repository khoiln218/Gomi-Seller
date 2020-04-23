package vn.gomisellers.apps.data.source.model.api;

/**
 * Created by KHOI LE on 4/17/2020.
 */
public class ChangeAvatarRequest extends BaseRequest {
    /**
     * UserId : a4c1dd3d-c271-47af-ba14-85e115c61719
     * Avatar :
     */

    private String Avatar;

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
}
