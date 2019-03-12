
package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.susu.boxuegu.R;

import utils.Md5Utils;

public class RegisterActivity extends AppCompatActivity {
    //标题栏变量
private TextView tv_back;
private TextView tv_main_title;
private RelativeLayout title_bar;
//注册
    private EditText  et_user_name;
      private EditText  et_psw;
      private EditText  et_psw_again;
      private Button btn_register;
      //用户名，密码，确认的获取值变量
    private String username,psw,psw_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        //实例化控件,标题栏
        tv_back=findViewById(R.id.tv_back);
        tv_main_title=findViewById(R.id.tv_main_title);
        title_bar=findViewById(R.id.title_bar);
        //设置标题栏背景为透明
        title_bar.setBackgroundColor(Color.TRANSPARENT);
        //设置标题栏文字
        tv_main_title.setText("注册");
        //返回按钮
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不用设置Intnet跳转，因为是在登陆页面跳转过来的，此Activity完成之后，便可以返回,则书写代码，finish()方法;
                RegisterActivity.this.finish();
            }
        });
        //用户名，密码等实例化控件
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        et_psw_again=findViewById(R.id.et_psw_again);
        //注册按钮
        btn_register=findViewById(R.id.btn_register);
        //当点击注册按钮时完成的方法
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取文本框中书写的用户名,密码等
                getEditString();
           if (TextUtils.isEmpty(username)){
               Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
               return;
           }else if (TextUtils.isEmpty(psw)){
               Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
               return;
           }else if (TextUtils.isEmpty(psw_again)){
               Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
               return;
           }else if (isExistUserName(username)){
               Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
               return;
           }else if (!psw.equals(psw_again)){
               Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
               return;
           }
           else{
               Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
               saveRegisterInfo(username,psw);
               //实现数据回传
               Intent intent=new Intent();
               intent.putExtra("username",username);
               setResult(1,intent);
               RegisterActivity.this.finish();
           }

            }
        });
    }
    //获取文本框中的用户名,密码等文本信息的实现方法
    private void getEditString(){
        username=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        psw_again=et_psw_again.getText().toString().trim();
    }

    //存入username，psw，运用SharedPreferences存储用户名和密码
 private void saveRegisterInfo(String username,String psw){
        //把密码用MD5加密
     String md5psw=Md5Utils.md5(psw);
     SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
     SharedPreferences.Editor editor=sharedPreferences.edit();//获取编辑器
     editor.putString(username,md5psw);
     editor.commit();

 }


 //从SharedPreference中获取用户名，判断是否存在用户名
   private boolean isExistUserName(String username){
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String s=sharedPreferences.getString(username,"");//当不存在username的值时，返回后边的空格
       boolean has_userName=false;
       if (!TextUtils.isEmpty(s)){
           has_userName=true;
       }
       return has_userName;
   }
}
