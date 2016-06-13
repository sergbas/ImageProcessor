package demo.apps.imageprocessor.common;

import android.support.v4.app.Fragment;

import demo.apps.imageprocessor.di.IHasComponent;

public abstract class BaseFragment extends Fragment {
    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>)getActivity()).getComponent());
    }
}
