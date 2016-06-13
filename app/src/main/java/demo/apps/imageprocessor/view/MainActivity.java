package demo.apps.imageprocessor.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import demo.apps.imageprocessor.R;
import demo.apps.imageprocessor.di.IHasComponent;
import demo.apps.imageprocessor.di.components.DaggerIMainActivityComponent;
import demo.apps.imageprocessor.di.components.IMainActivityComponent;
import demo.apps.imageprocessor.di.components.IImageProcessorAppComponent;
import demo.apps.imageprocessor.common.BaseActivity;
import demo.apps.imageprocessor.di.modules.MainActivityModule;
import demo.apps.imageprocessor.presenter.MainActivityPresenterImpl;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent> {

    @Inject
    MainActivityPresenterImpl presenter;

    private IMainActivityComponent mainActivityComponent;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        ImageFragment listFragment = (ImageFragment)fragmentManager.findFragmentByTag("ImageFragment");
        if (listFragment == null){
            listFragment = new ImageFragment();
        }

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            presenter.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupComponent(IImageProcessorAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .iImageProcessorAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mainActivityComponent.inject(this);
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }

    // -----  IMainActivityView implement method

    @Override
    public void popFragmentFromStack() {
        fragmentManager.popBackStack();
    }
}
