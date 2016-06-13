package demo.apps.imageprocessor.di.components;

import demo.apps.imageprocessor.app.ImageProcessorApp;
import demo.apps.imageprocessor.di.modules.ImageProcessorAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ImageProcessorAppModule.class
        }
)
public interface IImageProcessorAppComponent {
    void inject(ImageProcessorApp app);
}
