package example.com.expressapp.setting.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.login.view.LoginActivity;
import example.com.expressapp.setting.presenter.LogoutPresenterImpl;
import example.com.expressapp.setting.presenter.iLogoutPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements iSetting{
    private adminGUID mGuid;
    private iLogoutPresenter iLogout;
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==1)
            {
                if(msg.obj.toString().equals("1")||msg.obj.toString().equals("2"))
                {
                    Intent intent=new Intent(SettingFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                    android.util.Log.d("test","Fail "+msg.obj.toString());
            }
        };
    };

    public SettingFragment(adminGUID guid) {
        // Required empty public constructor
        mGuid=guid;
    }

    @Override
    public String getGUID()
    {
        return mGuid.getGUID();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView=inflater.inflate(R.layout.setting_fragment_layout,container,false);
        iLogout=new LogoutPresenterImpl(this);
        Button button=(Button) thisView.findViewById(R.id.logout_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iLogout.LogoutJudge(handler);
            }
        });
        return thisView;
    }

}
