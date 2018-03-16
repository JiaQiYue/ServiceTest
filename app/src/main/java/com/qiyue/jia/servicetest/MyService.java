package com.qiyue.jia.servicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

  private int count = 0;
  private DownloadBinder mBinder = new DownloadBinder();
  class DownloadBinder extends Binder{

    public void satrtDownload() {
      Log.d("Myservice","开始下载任务!");
    }

    public int getProgress() {
      Log.d("MyService","得到当前下载进度!");
      return 0;
    }
  }


  public MyService() {
  }

  @Override
  public IBinder onBind(Intent intent) {

    return mBinder;
  }

  //创建服务时调用
  @Override
  public void onCreate() {
    super.onCreate();
    Log.d("MyService","服务的onCreate方法!");

  }

  //每次启动服务时调用
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d("MyService","服务的onStartCommand方法");

    //前台服务(状态栏通知)
    NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    //跳转至mainActivity
    intent.setClass(this,MainActivity.class);
    //放置一个顺序用来区分每一个通知
    intent.putExtra("intent","intent~~~~~"+count);

    //参数1:context 上下文对象
    //参数2:发送者私有的请求码(Private request code for the sender)
    //参数3:intent 意图对象
    //参数4:必须为FLAG_ONE_SHOT,FLAG_NO_CREATE,FLAG_CANCEL_CURRENT,FLAG_UPDATE_CURRENT,中的一个
    PendingIntent pi = PendingIntent.getActivity(this, count, intent, PendingIntent.FLAG_CANCEL_CURRENT);//用户点击了改通知,才会跳转至mainActivity中

    //intent.putExtra("intent","other intent~~~~" + count);
    //我这里还可以给一个uri参数,点击notification可以打开百度首页,但是intent.setClass就不可以要了
//    Uri uri = Uri.parse("http:///baidu.com");
    //设置uri到intent中去
//    intent.setData(uri);
    //设置为展示uri的内容,系统会自动给出可以打开uri的应用,需要你选择
//    intent.setAction(Intent.ACTION_VIEW);

    //用bundle来传参
//    Bundle bundle = new Bundle();
//    bundle.putString("key", "bundle string--->" + count);
//    intent.putExtra("bundle", bundle);//这里把bundle放到intent中,可以在ServiceActivity获取出来

    //参数1,2,3,4和前面的一样
    //最后一个参数是:指定目标activity如何创建的额外参数

//        PendingIntent pi = PendingIntent.getActivity(this, count, intent, PendingIntent.FLAG_ONE_SHOT, bundle);//API 16以上

//    PendingIntent pi = PendingIntent.getService(this, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    Notification.Builder builder = new Notification.Builder(this)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentIntent(pi)
      .setContentText("this is notification contentText msg--->" + count)
      .setContentTitle("this is contentTitle--->" + count)
      .setTicker("this is msg's hint count--->" + count)
      .setNumber(count);
    //        Notification no = new Notification.Builder(this).build();//这个需要在API 16以上才可以用,我这里向下兼容到API 14(4.0)
    Notification notify2 = builder.getNotification();//而getNotification()方法已经被弃用了
    count++;
    notify2.flags |= Notification.FLAG_AUTO_CANCEL;//这里指定这个通知点击之后或者可以被清除按钮清除。 FLAG_NO_CLEAR 通知不能取消
    //参数1:此notification的识别号
    nm.notify(count, notify2);

    return super.onStartCommand(intent, flags, startId);

  }

  //服务销毁时调用
  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d("MyService","服务的onDestroy方法");

  }
}
