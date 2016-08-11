package example.com.expressapp.setting.view;

import android.app.Fragment;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.expressapp.R;

/**
 * Created by lxs on 2016/8/11.
 */
public class PersonInfoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View thisView=inflater.inflate(R.layout.personinfo_fragment_layout,container,false);
        return thisView;
    }
}
