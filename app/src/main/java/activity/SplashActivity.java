package activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.susu.boxuegu.R;

import java.util.Timer;
import java.util.TimerTask;


public  class SplashActivity extends AppCompatActivity {
private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        tv_version=findViewById(R.id.tv_version);

        try {   //得到版本号
            PackageInfo packageInfo=getPackageManager().getPackageInfo(getPackageName(),0);
            tv_version.setText("V"+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("V");
        }

    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            Intent intent=new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }

    };
timer.schedule(timerTask,3000);
}
}

