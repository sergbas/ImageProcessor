package demo.apps.imageprocessor.common;

public class Utils {
    public static int R(int c) {
        return (c >> 16) & 0xff;
    }

    public static int G(int c) {
        return (c >> 8) & 0xff;
    }

    public static int B(int c) {
        return c & 0xff;
    }

    public static double r(int c) {
        return R(c)/255.0;
    }

    public static double g(int c) {
        return G(c)/255.0;
    }

    public static double b(int c) {
        return B(c)/255.0;
    }
}
