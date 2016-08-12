package example.com.expressapp.setting.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.login.view.LoginActivity;
import example.com.expressapp.setting.model.PersonalInfo;
import example.com.expressapp.setting.presenter.PresenterImpl;
import example.com.expressapp.setting.presenter.iPresenter;

/**
 * Created by lxs on 2016/8/11.
 */
public class PersonInfoFragment extends Fragment implements iPersonInfo{
    private PersonalInfo personalInfo;
    private adminGUID mGuid;
    private iPresenter ipersonalInfoPresenter;
    private iPresenter ilogoutPresenter;
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            Log.d("PersonalInfo","5");
            if(msg.what==2)
            {
                Log.d("PersonalInfo",msg.obj.toString());
                personalInfo=new PersonalInfo(msg.obj.toString());
                Log.d("PersonalInfo",personalInfo.getTelephone());
                usernameTextView.setText(personalInfo.getTelephone());
                nicknameTextView.setText(personalInfo.getNickName());
                mailTextView.setText(personalInfo.getEmail());
                idNumTextView.setText(personalInfo.getPersonalNum());
                phoneNumberTextView.setText(personalInfo.getTelephone());
            }
            else if(msg.what==1)
            {
                if(msg.obj.toString().equals("1")||msg.obj.toString().equals("2"))
                {
                    Log.d("Waiting","1");
                    Intent intent=new Intent(PersonInfoFragment.this.getActivity(), LoginActivity.class);
                    Log.d("Waiting","2");
                    startActivity(intent);
                }
                else
                    android.util.Log.d("test","Fail "+msg.obj.toString());
            }
        };
    };
    FloatingActionButton logoutFab;
    TextView usernameTextView;
    TextView nicknameTextView;
    TextView mailTextView;
    TextView idNumTextView;
    TextView phoneNumberTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View thisView=inflater.inflate(R.layout.personinfo_fragment_layout,container,false);
        logoutFab=(FloatingActionButton)thisView.findViewById(R.id.personinfo_fragment_logout_fab);
        usernameTextView=(TextView)thisView.findViewById(R.id.personinfo_fragment_username_textview);
        nicknameTextView=(TextView)thisView.findViewById(R.id.personinfo_fragment_nickname_textview);
        mailTextView=(TextView)thisView.findViewById(R.id.personinfo_fragment_mail_textview);
        idNumTextView=(TextView)thisView.findViewById(R.id.personinfo_fragment_idnum_textview);
        phoneNumberTextView=(TextView)thisView.findViewById(R.id.personinfo_fragment_phonenumber_textview);
        ipersonalInfoPresenter=new PresenterImpl(this);
        ilogoutPresenter=new PresenterImpl(this);
        logoutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilogoutPresenter.logoutJudge(handler);
            }
        });
        ipersonalInfoPresenter.personalInfoJudge(handler);
        return thisView;
    }

    public PersonInfoFragment(adminGUID mGuid)
    {
        this.mGuid=mGuid;
    }

    public String getGUID()
    {
        return mGuid.getGUID();
    }
}
