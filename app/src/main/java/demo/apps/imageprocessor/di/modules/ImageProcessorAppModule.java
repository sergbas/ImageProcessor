package demo.apps.imageprocessor.di.modules;

import android.app.Application;

import demo.apps.imageprocessor.app.ImageProcessorApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageProcessorAppModule {

    private final ImageProcessorApp app;

    public ImageProcessorAppModule(ImageProcessorApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}
