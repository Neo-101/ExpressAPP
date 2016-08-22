package example.com.expressapp.setting.view;


import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SettingFragment extends PreferenceFragment implements iSetting{
    private CheckBoxPreference switchCompatPref;
    private Preference personInfoPref;
    private Preference teamInfoPref;
    private adminGUID mGuid;


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
       return thisView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_fragment_preferences);
        switchCompatPref=(CheckBoxPreference)getPreferenceManager().findPreference(getString(R.string.checkBoxPreference));
        personInfoPref=getPreferenceManager().findPreference(getString(R.string.personInfoPreference));
        teamInfoPref=getPreferenceManager().findPreference(getString(R.string.teamInfoPreference));
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
                fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_left_enter,R.animator.fragment_slide_right_exit);
                fragmentTransaction.replace(R.id.basispage_layout_content,new PersonInfoFragment(mGuid)).commit();
                Log.d("Test","click");
                return true;
            }
        });
        teamInfoPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri  uri = Uri.parse("https://github.com/Bobo1553/ExpressAPP");
                Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
        });
    }
}
