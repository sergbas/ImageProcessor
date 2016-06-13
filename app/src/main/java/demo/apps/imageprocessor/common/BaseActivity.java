package demo.apps.imageprocessor.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import demo.apps.imageprocessor.di.components.IImageProcessorAppComponent;
import demo.apps.imageprocessor.app.ImageProcessorApp;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(ImageProcessorApp.get(this).getAppComponent());
    }

    protected abstract void setupComponent(IImageProcessorAppComponent appComponent);

}
