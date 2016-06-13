package demo.apps.imageprocessor.filters;

import android.graphics.Bitmap;
import android.graphics.Color;

import demo.apps.imageprocessor.common.Utils;

public class BicubicFilter implements IFilter {
    private double ratio;

    public BicubicFilter(double ratio){
        this.ratio = ratio;
    }

    public Bitmap Process(Bitmap src) {
        int newW = (int)(src.getWidth()*ratio);
        int newH = (int)(src.getHeight()*ratio);
        Bitmap res = Bitmap.createScaledBitmap(src, newW, newH, false);

        double xRatio = (float)(src.getWidth())/(float)newW;
        double yRatio = (float)(src.getHeight())/(float)newH;

        for(int j=0; j < newH; j++)
            for(int i=0; i < newW; i++)
            {
                int x = (int)(xRatio * i);
                int y = (int)(yRatio * j);

                //need to optimize it
                double[][][] ndata = new double[3][4][4];
                for( int X = 0; X < 4; X++ )
                    for( int Y = 0; Y < 4; Y++ ) {
                        int xx = x + X;
                        int yy = y + Y;
                        xx = Math.min(Math.max(0, xx), src.getWidth()-1);
                        yy = Math.min(Math.max(0, yy), src.getHeight()-1);
                        int c = src.getPixel(xx, yy);
                        ndata[0][X][Y] = Utils.r(c);
                        ndata[1][X][Y] = Utils.g(c);
                        ndata[2][X][Y] = Utils.b(c);
                    }

                double fracx = (xRatio * i) - x;
                double fracy = (yRatio * j) - y;

                double r = bicubicComponent(ndata[0], fracx, fracy);
                double g = bicubicComponent(ndata[1], fracx, fracy);
                double b = bicubicComponent(ndata[2], fracx, fracy);

                int c = Color.rgb(to255(r), to255(g), to255(b));

                res.setPixel(i, j, c);
            }

        return res;
    }

    private int to255(double x) {
        return Math.max(0, Math.min((int)(x*255), 255));
    }

    private double CubicPolate(double A, double B, double C, double D, double t)
    {
        double a = -A / 2.0 + (3.0*B) / 2.0 - (3.0*C) / 2.0 + D / 2.0;
        double b = A - (5.0*B) / 2.0 + 2.0*C - D / 2.0;
        double c = -A / 2.0 + C / 2.0;
        double d = B;

        return a*t*t*t + b*t*t + c*t + d;
    }

    private double bicubicComponent(double[][] ndata, double fracx, double fracy) {
        double x1 = CubicPolate( ndata[0][0], ndata[1][0], ndata[2][0], ndata[3][0], fracx );
        double x2 = CubicPolate( ndata[0][1], ndata[1][1], ndata[2][1], ndata[3][1], fracx );
        double x3 = CubicPolate( ndata[0][2], ndata[1][2], ndata[2][2], ndata[3][2], fracx );
        double x4 = CubicPolate( ndata[0][3], ndata[1][3], ndata[2][3], ndata[3][3], fracx );

        double y1 = CubicPolate( x1, x2, x3, x4, fracy );

        return y1;
    }


}
