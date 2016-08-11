package example.com.expressapp.setting.view;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.login.view.LoginActivity;
import example.com.expressapp.setting.presenter.iLogoutPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements iSetting{
    private Button logoutButton;
    private CheckBoxPreference switchCompatPref;
    private Preference personInfoPref;

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
        View thisView=super.onCreateView(inflater,container,savedInstanceState);
        thisView.setBackgroundResource(R.color.windowBackground);
/*        iLogout=new LogoutPresenterImpl(this);
        logoutButton=(Button) thisView.findViewById(R.id.setting_fragment_layout_logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iLogout.LogoutJudge(handler);
            }
        });
*/        return thisView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_fragment_preferences);
        switchCompatPref=(CheckBoxPreference)getPreferenceManager().findPreference(getString(R.string.checkBoxPreference));
        personInfoPref=getPreferenceManager().findPreference(getString(R.string.personInfoPreference));
        switchCompatPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean checked=Boolean.valueOf(newValue.toString());
                if(checked) Log.d("Test","checked");
                return true;
            }
        });
        personInfoPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_right_enter,R.animator.fragment_slide_left_exit);
                fragmentTransaction.replace(R.id.basispage_layout_content,new PersonInfoFragment()).commit();
                Log.d("Test","click");
                return true;
            }
        });
    }
}
