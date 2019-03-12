package com.example.susu.boxuegu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DefaultDatabaseErrorHandler;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import activity.RegisterActivity;
import utils.Md5Utils;

public class LoginActivity extends AppCompatActivity {
//文本框
private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    //登陆变量
    private EditText et_user_name,et_psw;
    private String userName,psw,spPsw;
    //下方注册，找回
    private  TextView tv_register,tv_find_psw;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        //实例化控件
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        title_bar=findViewById(R.id.title_bar);
        //返回按钮
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        //设置文本
        tv_main_title.setText("登录");
        title_bar.setBackgroundColor(Color.TRANSPARENT);

        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);

        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
        btn_login=findViewById(R.id.btn_login);
        //注册按钮
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //登陆按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
userName=et_user_name.getText().toString().trim();
psw=et_psw.getText().toString().trim();
//加密密码
String Md5Psw=Md5Utils.md5(psw);
spPsw=readPsw(userName);
if (TextUtils.isEmpty(userName)){
    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
    return;

}else if (TextUtils.isEmpty(psw)){
    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
    return;
}else if (spPsw.equals(Md5Psw)){
    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
    saveLoginStatus(true,userName);
    Intent intent=new Intent();
    setResult(1,intent);
    LoginActivity.this.finish();
    return;

}else if (!TextUtils.isEmpty(spPsw)&&!spPsw.equals(Md5Psw)){
    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
    return;
}
else{
    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
}

            }
        });
    }

    //通过用户名获取密码
    public String readPsw(String userName){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
    return  sharedPreferences.getString(userName,"");

    }
    //存储用户名和登陆状态
    public void saveLoginStatus(boolean status,String userName){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",userName);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==1){
            String username=data.getStringExtra("username");
            et_user_name.setText(username);
            //设置光标的位置
            et_user_name.setSelection(username.length());
        }
    }
}
