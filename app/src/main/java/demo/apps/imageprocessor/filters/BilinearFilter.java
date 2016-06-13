package demo.apps.imageprocessor.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import demo.apps.imageprocessor.common.Utils;

public class BilinearFilter implements IFilter {

    private double ratio;

    public BilinearFilter(double ratio){
        this.ratio = ratio;
    }

    public Bitmap Process(Bitmap src) {
        int newW = (int)(src.getWidth()*ratio);
        int newH = (int)(src.getHeight()*ratio);
        Bitmap res = Bitmap.createScaledBitmap(src, newW, newH, false);

        double nXFactor = (double) src.getWidth() / (double) newW;
        double nYFactor = (double) src.getHeight() / (double) newH;
        double fraction_x, fraction_y, one_minus_x, one_minus_y;
        int ceil_x, ceil_y, floor_x, floor_y;
        int c1, c2, c3, c4;
        int red, green, blue;

        for (int x = 0; x < newW; ++x)
            for (int y = 0; y < newH; ++y) {
                floor_x = (int)Math.floor(x * nXFactor);
                floor_y = (int)Math.floor(y * nYFactor);
                ceil_x = floor_x + 1;
                if (ceil_x >= src.getWidth()) ceil_x = floor_x;
                ceil_y = floor_y + 1;
                if (ceil_y >= src.getHeight()) ceil_y = floor_y;
                fraction_x = x * nXFactor - floor_x;
                fraction_y = y * nYFactor - floor_y;
                one_minus_x = 1.0 - fraction_x;
                one_minus_y = 1.0 - fraction_y;

                c1 = src.getPixel(floor_x, floor_y);
                c2 = src.getPixel(ceil_x, floor_y);
                c3 = src.getPixel(floor_x, ceil_y);
                c4 = src.getPixel(ceil_x, ceil_y);

                blue = f(one_minus_x, one_minus_y, fraction_x, fraction_y, Utils.B(c1), Utils.B(c2), Utils.B(c3), Utils.B(c4));
                green = f(one_minus_x, one_minus_y, fraction_x, fraction_y, Utils.G(c1), Utils.G(c2), Utils.G(c3), Utils.G(c4));
                red = f(one_minus_x, one_minus_y, fraction_x, fraction_y, Utils.R(c1), Utils.R(c2), Utils.R(c3), Utils.R(c4));

                res.setPixel(x, y, Color.rgb(red, green, blue));
            }
        return res;
    }

    private int f(double one_minus_x, double one_minus_y, double fraction_x, double fraction_y, int c1, int c2, int c3, int c4) {
        int b1 = (int)(one_minus_x * c1 + fraction_x * c2);
        int b2 = (int)(one_minus_x * c3 + fraction_x * c4);
        int c = (int)(one_minus_y * (double)(b1) + fraction_y * (double)(b2));
        return c;
    }

}
