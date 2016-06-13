package demo.apps.imageprocessor.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import javax.inject.Inject;

import demo.apps.imageprocessor.filters.IFilter;
import demo.apps.imageprocessor.view.IImageFragmentPresenter;
import demo.apps.imageprocessor.view.IImageFragmentView;

public class ImageFragmentPresenterImpl implements IImageFragmentPresenter {

    private IImageFragmentView view;

    @Inject
    public ImageFragmentPresenterImpl() {
    }

    @Override
    public void init(IImageFragmentView view) {
        this.view = view;
    }

    @Override
    public void onImageLoad() {
        String url = "";
        //async image downloader
    }

    @Override
    public void addBitmapToImageView(ImageView destImage, Bitmap bitmap) {
        view.addBitmapToImageView(destImage, bitmap);
    }

    @Override
    public Bitmap scale(Bitmap bmp, IFilter filter, int iterations) {
        for(int i = 0; i < iterations; i++) {
            bmp = filter.Process(bmp);
        }

        return bmp;
    }

}
