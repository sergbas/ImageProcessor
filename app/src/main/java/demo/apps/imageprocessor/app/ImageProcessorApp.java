package demo.apps.imageprocessor.app;

import android.app.Application;
import android.content.Context;

import demo.apps.imageprocessor.di.components.DaggerIImageProcessorAppComponent;
import demo.apps.imageprocessor.di.components.IImageProcessorAppComponent;
import demo.apps.imageprocessor.di.modules.ImageProcessorAppModule;

public class ImageProcessorApp extends Application {

    private IImageProcessorAppComponent appComponent;

    public static ImageProcessorApp get(Context c) {
        return (ImageProcessorApp) c.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildGraphAndInject();
    }

    public IImageProcessorAppComponent getAppComponent() {
        return appComponent;
    }

    public void buildGraphAndInject() {
        appComponent = DaggerIImageProcessorAppComponent.builder()
                .imageProcessorAppModule(new ImageProcessorAppModule(this))
                .build();
        appComponent.inject(this);
    }
}
