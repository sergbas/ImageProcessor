package demo.apps.imageprocessor.di.modules;

import demo.apps.imageprocessor.presenter.ImageFragmentPresenterImpl;
import demo.apps.imageprocessor.presenter.MainActivityPresenterImpl;
import demo.apps.imageprocessor.view.IMainActivityView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private IMainActivityView view;

    public MainActivityModule(IMainActivityView view) {
        this.view = view;
    }

    @Provides
    public IMainActivityView provideView() {
        return view;
    }

    @Provides
    public MainActivityPresenterImpl provideMainActivityPresenterImpl (IMainActivityView view){
        return  new MainActivityPresenterImpl(view);
    }

	@Provides
    public ImageFragmentPresenterImpl provideImageFragmentPresenterImpl() {
        return new ImageFragmentPresenterImpl();
    } 
}
