package com.qiyue.jia.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by jia on 2018/3/15.
 */

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {


  private Button mBtn_start_service;
  private Button mButton_stop_service;
  private Button mBtn_bind_service;
  private Button mBtn_unbind_service;
  private MyService.DownloadBinder mDownloadBinder;


  private ServiceConnection connection = new ServiceConnection(){

    //活动与服务绑定成功
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      mDownloadBinder = (MyService.DownloadBinder) iBinder;
      mDownloadBinder.satrtDownload();
      mDownloadBinder.getProgress();
    }
    //活动与服务接触绑定
    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
  };


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.servicea_ctivity);
    /*设置窗口样式activity宽高start*/
//    WindowManager m = getWindowManager();
//    Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
//    WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
//    p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.5
//    p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.7
//    p.alpha = 1.0f;      //设置本身透明度
//    p.dimAmount = 0.5f;      //设置窗口外黑暗度
//    getWindow().setAttributes(p);
 /*设置窗口样式activity宽高end*/
    mBtn_start_service = (Button) findViewById(R.id.btn_start_service);
    mButton_stop_service = (Button) findViewById(R.id.btn_stop_service);
    mBtn_bind_service = (Button) findViewById(R.id.btn_bind_service);
    mBtn_unbind_service = (Button) findViewById(R.id.btn_unbind_service);

    mBtn_start_service.setOnClickListener(this);
    mButton_stop_service.setOnClickListener(this);
    mBtn_bind_service.setOnClickListener(this);
    mBtn_unbind_service.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_start_service:
        //启动服务
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
        break;
      case R.id.btn_stop_service:
        //关闭服务
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
        break;
      case R.id.btn_bind_service:
        //绑定服务
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent,connection,BIND_AUTO_CREATE);
        break;
      case R.id.btn_unbind_service:
        //接触绑定服务
        unbindService(connection);
        break;
      default:
        break;
    }

  }
}
