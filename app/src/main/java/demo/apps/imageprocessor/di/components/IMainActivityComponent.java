package demo.apps.imageprocessor.di.components;

import demo.apps.imageprocessor.di.ActivityScope;
import demo.apps.imageprocessor.di.modules.MainActivityModule;
import demo.apps.imageprocessor.view.ImageFragment;
import demo.apps.imageprocessor.view.MainActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = IImageProcessorAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {
    void inject(MainActivity activity);
    void inject(ImageFragment imageFragment);
}
