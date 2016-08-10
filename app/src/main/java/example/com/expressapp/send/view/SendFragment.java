package example.com.expressapp.send.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.expressapp.R;
import example.com.expressapp.searchinformation.model.ExpressInfoManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {
    ExpressInfoManager mExpressInfoManager;


    public SendFragment(ExpressInfoManager expressInfoManager) {
        // Required empty public constructor
        this.mExpressInfoManager=expressInfoManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.send_fragment_layout, container, false);
    }

}
