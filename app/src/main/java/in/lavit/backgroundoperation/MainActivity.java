package in.lavit.backgroundoperation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private String TAG = "MainActivity";

    /////
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,MyService.class);
        Intent alarmRec = new Intent(this,AlarmReceiver.class);
        ///Create an object of alarmManager
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmIntent = PendingIntent.getBroadcast(this,0,alarmRec,0);


    }

    public void startService(View view) {
        startService(intent);
    }

    public void stopService(View view) {
        stopService(intent);
    }

    public void setAlarm(View view) {
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 35);

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);

    }

    public void scheduleAlarm(View view) {
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build();
        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled!");
        } else {
            Log.d(TAG, "Job not scheduled");
        }
    }
}
