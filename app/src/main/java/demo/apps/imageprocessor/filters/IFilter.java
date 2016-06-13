package demo.apps.imageprocessor.filters;

import android.graphics.Bitmap;

public interface IFilter {
    public Bitmap Process(Bitmap src);
}
