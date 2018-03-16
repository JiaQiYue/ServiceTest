package com.qiyue.jia.servicetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final int UPDATE_TEXT = 1;
  private Button mBtn_change_tv;
  private TextView mTv_text;
  private Button mBtn_skip_service_activity;

  private Handler handler = new Handler() {

    public void handleMessage(Message msg) {

      switch (msg.what) {
        case UPDATE_TEXT:
          //在这里进行UI操作:
          mTv_text.setText("Nice to meet You");
          break;
          default:
            break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mBtn_change_tv = (Button) findViewById(R.id.btn_change_tv);
    mTv_text = (TextView) findViewById(R.id.tv_text);
    mBtn_skip_service_activity = (Button) findViewById(R.id.btn_skip_service_activity);

    mBtn_change_tv.setOnClickListener(this);
    mBtn_skip_service_activity.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {

    switch (view.getId()) {
      case R.id.btn_change_tv:
          new Thread(new Runnable() {
            @Override
            public void run() {
//              mTv_text.setText("Nice to meet You");
              Message message = new Message();
              message.what = UPDATE_TEXT;

              handler.sendMessage(message);

            }
          }).start();
        break;

      case R.id.btn_skip_service_activity:
        //跳转至服务活动窗口
        Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
        startActivity(intent);
        finish();
        break;
      default:
        break;
    }
  }
}
