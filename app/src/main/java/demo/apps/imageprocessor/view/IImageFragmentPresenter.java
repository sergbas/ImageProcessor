package demo.apps.imageprocessor.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

import demo.apps.imageprocessor.common.BaseFragmentPresenter;
import demo.apps.imageprocessor.filters.IFilter;

public interface IImageFragmentPresenter extends BaseFragmentPresenter<IImageFragmentView> {
    void onImageLoad();
    void addBitmapToImageView(ImageView destImage, Bitmap bitmap);
    Bitmap scale(Bitmap bmp, IFilter filter, int iterations);
}