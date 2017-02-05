package jp.ac.asojuku.st.batterywarning;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{
        private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    protected void onResume(){
        super.onResume();
        mReceiver=new MyBroadcastReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver,filter);

    }
    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(mReceiver);
    }





    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){

            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                int scale=intent.getIntExtra("scale",0);
                int level=intent.getIntExtra("level",0);

                Activity mainActivity=(Activity)context;

                TextView tvTitle= (TextView) mainActivity.findViewById(R.id.tvTitle);


                String title="Battery Warning";
                tvTitle.setText(title);


                TextView tvMsg=(TextView) mainActivity.findViewById(R.id.tvMsg);
                String msg =level + "/"+scale;
                tvMsg.setText(msg);

                TextView juden=(TextView)mainActivity.findViewById(R.id.juden);
                juden.setText(intent.getIntExtra("level",0)+"%");


                if(level < 90){

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

                    builder.setSmallIcon(R.drawable.samon)
                            .setContentTitle("バッテリー残量") // 1行目
                            .setContentText("バッテリー残量が90％をきっています") // 2行目
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_SOUND);

                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(1, builder.build());
                }
            }

        }
    };
}
