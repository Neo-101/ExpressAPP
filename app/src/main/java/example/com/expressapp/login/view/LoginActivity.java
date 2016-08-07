package example.com.expressapp.login.view;

import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import example.com.expressapp.ActivityList;
import example.com.expressapp.R;
import example.com.expressapp.basispage.view.BasisPageActivity;


public class LoginActivity extends AppCompatActivity implements i_LoginView
{

    //EditText_username 用户名输入框
    //EditText_password 密码输入框
    //ImageButton_usernamedelete 用户名输入框清空按钮
    //ImageButton_passworddelete 密码输入框清空按钮
    //AppCompatButton_login 登陆按钮
    //SwitchCompat_rememberpassword 记住密码
    TextInputLayout EditText_username,EditText_password;
    ImageButton ImageButton_usernamedelete,ImageButton_passworddelete;
    AppCompatButton AppCompatButton_login;
    SwitchCompat SwitchCompat_rememberpassword;
    SharedPreferences sharedPreferences;
    private double exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityList.addActivity(LoginActivity.this);
        initViews();
        if(sharedPreferences.getBoolean("REMEMBER_PASSWORD", true))
        {
            EditText_username.getEditText().setText(sharedPreferences.getString("USERNAME",""));
            EditText_password.getEditText().setText(sharedPreferences.getString("PASSWORD",""));
            SwitchCompat_rememberpassword.setChecked(true);
        }

        //当用户名输入框中的值改变时调用
        EditText_username.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                EditText_username.getEditText().setTextColor(getResources().getColor(R.color.textColorPrimary));
                EditText_password.getEditText().setTextColor(getResources().getColor(R.color.textColorPrimary));
                if(!EditText_username.getEditText().getText().toString().equals(""))   //输入框值不为空
                    ImageButton_usernamedelete.setVisibility(View.VISIBLE);  //显示清除按钮
                else ImageButton_usernamedelete.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s){}
        });

        //当用户名输入框中的清除按钮被点击时调用
        ImageButton_usernamedelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText_username.getEditText().setText("");
            }
        });

        //当密码输入框中的值改变时调用
        EditText_password.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                EditText_username.getEditText().setTextColor(getResources().getColor(R.color.textColorPrimary));
                EditText_password.getEditText().setTextColor(getResources().getColor(R.color.textColorPrimary));
                if(!EditText_password.getEditText().getText().toString().equals(""))    //输入框值不为空
                    ImageButton_passworddelete.setVisibility(View.VISIBLE);   //显示清除按钮
                else ImageButton_passworddelete.setVisibility(View.INVISIBLE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        //当密码输入框中的清除按钮被点击时调用
        ImageButton_passworddelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText_password.getEditText().setText("");
            }
        });

        //当记住密码按钮被点击时调用
        SwitchCompat_rememberpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(SwitchCompat_rememberpassword.isChecked())
                {
                    sharedPreferences.edit().putBoolean("REMEMBER_PASSWORD",true).commit();
                    sharedPreferences.edit().putString("USERNAME",EditText_username.getEditText().getText().toString()).commit();
                    sharedPreferences.edit().putString("PASSWORD",EditText_password.getEditText().getText().toString()).commit();
                }
                else
                {
                    sharedPreferences.edit().putBoolean("REMEMBER_PASSWORD",false).commit();
                    sharedPreferences.edit().remove("USERNAME").commit();
                    sharedPreferences.edit().remove("PASSWORD").commit();
                }

            }
        });
        
        //当登陆按钮被点击时调用
        AppCompatButton_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // String Login_result=new String();
            /*    try
                {

                    Login_result=UploadUserInformationByPostService.save(EditText_username.getText().toString(), edittext_password.getText().toString());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }*/
                onLoginRight();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityList.removeActivity(LoginActivity.this);
    }

    //当返回键被按下时调用
    @Override
    public void onBackPressed()
    {
        exitAPP();
    }

    //退出APP
    private void exitAPP()
    {
        if(System.currentTimeMillis()-exitTime>2000)
        {
            Toast.makeText(LoginActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }
        else ActivityList.exitAllActivity();
    }

    //初始化界面
    @Override
    public void initViews()
    {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.login_layout);
        exitTime=0;
        sharedPreferences=getSharedPreferences("USERINFO",MODE_PRIVATE);
        EditText_username=(TextInputLayout)findViewById(R.id.login_layout_EditText_username);
        EditText_password=(TextInputLayout)findViewById(R.id.login_layout_EditText_password);
        ImageButton_passworddelete=(ImageButton)findViewById(R.id.login_layout_ImageButton_passworddelete);
        ImageButton_usernamedelete=(ImageButton)findViewById(R.id.login_layout_ImageButton_usernamedelete);
        AppCompatButton_login=(AppCompatButton)findViewById(R.id.login_layout_AppCompatButton_login);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP)       //当系统API小于21时
        {
            ColorStateList list=getResources().getColorStateList(R.color.colorPrimary);
            AppCompatButton_login.setSupportBackgroundTintList(list);
            AppCompatButton_login.setSupportBackgroundTintMode(PorterDuff.Mode.SRC_IN);
        }
        SwitchCompat_rememberpassword=(SwitchCompat)findViewById(R.id.login_layout_SwitchCompat_rememberpassword);
    }

    //当登陆出错时调用
    @Override
    public void onLoginWrong()
    {
        Snackbar snackbar_login;
        snackbar_login=Snackbar.make(AppCompatButton_login.getRootView(),"用户名或密码错误",Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout ssl = (Snackbar.SnackbarLayout)snackbar_login.getView();
        ssl.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        ssl.setAlpha(0.7f);
        snackbar_login.show();
        EditText_username.getEditText().setTextColor(getResources().getColor(R.color.colorAccent));
        EditText_password.getEditText().setTextColor(getResources().getColor(R.color.colorAccent));
    }

    //当登陆成功时调用
    @Override
    public  void onLoginRight()
    {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            // 包含新API的代码块
            getWindow().setExitTransition(new Explode());
            Intent intent=new Intent(LoginActivity.this,BasisPageActivity.class);
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        else
        {
            // 包含旧的API的代码块
            Intent intent=new Intent(LoginActivity.this,BasisPageActivity.class);
            startActivity(intent);
        }

    }

    //加载图标
    @Override
    public void Login_loading()
    {
        dimBackground(1.0f,0.8f);
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.login_layout_progressbar);
        progressBar.setVisibility(View.VISIBLE);
    }

    //屏幕变暗
    private void dimBackground(float from,float to)
    {
        final Window window=getWindow();
        final ValueAnimator valueAnimator=ValueAnimator.ofFloat(from,to);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                WindowManager.LayoutParams layoutParams=window.getAttributes();
                layoutParams.alpha=(Float)valueAnimator.getAnimatedValue();
                window.setAttributes(layoutParams);
            }
        });
        valueAnimator.start();
    }

}
