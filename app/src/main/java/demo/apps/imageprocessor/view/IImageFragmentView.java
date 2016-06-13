package demo.apps.imageprocessor.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface IImageFragmentView {
    void addBitmapToImageView(ImageView destImage, Bitmap bitmap);
    void replaceToDetailFragment(int id);
}
