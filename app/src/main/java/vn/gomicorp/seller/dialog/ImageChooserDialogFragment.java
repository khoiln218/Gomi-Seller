package vn.gomicorp.seller.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.gomicorp.seller.R;

public class ImageChooserDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_UPDATED = "EXTRA_UPDATED";

    public static ImageChooserDialogFragment instance(String title, boolean updated) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);
        args.putBoolean(EXTRA_UPDATED, updated);

        ImageChooserDialogFragment fragment = new ImageChooserDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String title;
    private boolean updated;

    private OnClickListener clickListener;

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(EXTRA_TITLE);
            updated = getArguments().getBoolean(EXTRA_UPDATED);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_image_chooser, container, false);

        View viewPhoto = view.findViewById(R.id.view_photo);
        View removePhoto = view.findViewById(R.id.remove_photo);

        ((TextView) view.findViewById(R.id.text_title)).setText(title);

        if (updated) {
            viewPhoto.setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.text_view_photo)).setText(getString(R.string.view_photo).replace("{photo}", title.toLowerCase()));

            removePhoto.setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.text_remove_photo)).setText(getString(R.string.remove_photo).replace("{photo}", title.toLowerCase()));
        } else {

            viewPhoto.setVisibility(View.GONE);
            removePhoto.setVisibility(View.GONE);
        }

        view.findViewById(R.id.take_photo).setOnClickListener(this);
        view.findViewById(R.id.choose_image).setOnClickListener(this);
        viewPhoto.setOnClickListener(this);
        removePhoto.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:
                if (clickListener != null)
                    clickListener.onTakePhoto();
                break;

            case R.id.choose_image:
                if (clickListener != null)
                    clickListener.onChooseImage();
                break;

            case R.id.view_photo:
                if (clickListener != null)
                    clickListener.onViewPhoto();
                break;

            case R.id.remove_photo:
                if (clickListener != null)
                    clickListener.onRemovePhoto();
                break;
        }
        dismiss();
    }


    public interface OnClickListener {

        void onTakePhoto();

        void onChooseImage();

        void onViewPhoto();

        void onRemovePhoto();
    }
}
