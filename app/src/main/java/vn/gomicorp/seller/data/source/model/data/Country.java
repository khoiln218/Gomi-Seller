package vn.gomicorp.seller.data.source.model.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class Country {


    /**
     * Code : +84
     * Flag : http://192.168.0.149:2525/CountryFlag/vietnam.jpg
     * Id : 1
     * Name : Việt Nam
     */

    private String Code;
    private String Flag;
    private int Id;
    private String Name;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String Flag) {
        this.Flag = Flag;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @BindingAdapter("flag")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}