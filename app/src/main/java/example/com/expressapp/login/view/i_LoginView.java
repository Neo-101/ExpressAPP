package example.com.expressapp.login.view;

/**
 * Created by lxs on 2016/5/12.
 */
public interface i_LoginView
{
    void initViews(); //初始化界面
    void onLoginWrong();//显示登陆失败界面
    void onLoginRight();//显示登陆成功界面，并跳转到下一个界面
    void Login_loading();//显示正在登陆
}
