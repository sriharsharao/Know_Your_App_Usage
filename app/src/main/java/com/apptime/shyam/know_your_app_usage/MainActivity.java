package com.apptime.shyam.know_your_app_usage;

import android.annotation.SuppressLint;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    List<String> packnames;
    ArrayList<String> timew;
    String PackName;
    String timealpsed,lastseen;
    Button NameB,TimeB;

    long tot=0;
    int sortF=0;

    ArrayList<String> sysapps;
    ArrayList<String> datatime;

    ArrayList<String> datap;
    ImageView imageView;

    List<AppList> installedApps;
    static int pr=0;
    long totalt=0;


    List<UsageStats> queryUsageStats;
    List<UsageStats> queryUsageStatsWeekly;
    static ArrayList<String> uncheckedFromSP;

    LayerDrawable getBorders(int bgColor, int borderColor,
                             int left, int top, int right, int bottom){
        // Initialize new color drawables
        ColorDrawable borderColorDrawable = new ColorDrawable(borderColor);
        ColorDrawable backgroundColorDrawable = new ColorDrawable(bgColor);

        // Initialize a new array of drawable objects
        Drawable[] drawables = new Drawable[]{
                borderColorDrawable,
                backgroundColorDrawable
        };

        // Initialize a new layer drawable instance from drawables array
        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        // Set padding for background color layer
        layerDrawable.setLayerInset(
                1, // Index of the drawable to adjust [background color layer]
                left, // Number of pixels to add to the left bound [left border]
                top, // Number of pixels to add to the top bound [top border]
                right, // Number of pixels to add to the right bound [right border]
                bottom // Number of pixels to add to the bottom bound [bottom border]
        );

        // Finally, return the one or more sided bordered background drawable
        return layerDrawable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final LayerDrawable bottomBorder = getBorders(
                Color.parseColor("#edf4fb"), // Background color
                Color.parseColor("#FF8c00"), // Border color
                0, // Left border in pixels
                0, // Top border in pixels
                0, // Right border in pixels
                12 // Bottom border in pixels
        );

        NameB = (Button) (findViewById(R.id.NameB));
        TimeB = (Button) (findViewById(R.id.TimeB));


        timew = new ArrayList<>();
        packnames = new ArrayList<String>();
        datap = new ArrayList<String>();
        datap.add("com.facebook.katana");
        datap.add("com.whatsapp");
        datap.add("com.instagram.android");
        datap.add("com.twitter.android");
        datap.add("com.android.chrome");
        datap.add("com.bsb.hike");
        datap.add("com.google.android.youtube");
        datatime = new ArrayList<String>();
        for (int q = 0; q < 7; q++) {
            datatime.add("NA");
            timew.add(String.valueOf(0));
        }
        sysapps = new ArrayList<String>();
        sysapps.add("com.google.android.youtube");
        sysapps.add("com.android.chrome");
        sysapps.add("com.google.android.apps.photos");
        sysapps.add("com.google.android.apps.messaging");
        sysapps.add("com.android.camera");
        sysapps.add("com.google.android.calculator");
        sysapps.add("com.google.android.calendar");
        sysapps.add("com.google.android.gm");
        sysapps.add("com.google.android.music");
        sysapps.add("com.google.android.apps.maps");
        sysapps.add("com.google.android.contacts");


        @SuppressLint("WrongConstant") final UsageStatsManager mUsageStatsManager = (UsageStatsManager) MainActivity.this.getSystemService("usagestats");

        final long currentTime = System.currentTimeMillis();
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        final long beginTime = cal.getTimeInMillis();
        queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(), currentTime);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }


        final ListView userInstalledApps = (ListView) findViewById(R.id.apps_list);

        installedApps = getInstalledApps();
        Date now = new Date();
        Calendar calnow = Calendar.getInstance();
        calnow.setTime(now);


        final AppAdapter installedAppAdapter = new AppAdapter(MainActivity.this, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
        final long currentTimeWeekly = System.currentTimeMillis();
        final Calendar calWeekly = Calendar.getInstance();
        calWeekly.add(Calendar.DAY_OF_WEEK, -7);
        final long beginTimeWeekly = calWeekly.getTimeInMillis();
        queryUsageStatsWeekly = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, cal.getTimeInMillis(), currentTimeWeekly);
        if (queryUsageStatsWeekly == null || queryUsageStatsWeekly.isEmpty()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }


        TimeB.setBackground(bottomBorder);
        TimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortF=0;
                tot=0;
                NameB.setTextColor(Color.parseColor("#666666"));
                NameB.setBackgroundColor(Color.parseColor("#edf4fb"));
                TimeB.setTextColor(Color.parseColor("#FF8c00"));
                TimeB.setBackgroundColor(Color.parseColor("#edf4fb"));
                TimeB.setBackground(bottomBorder);
                NameB.setBackgroundColor(Color.parseColor("#d5dbe1"));
                installedApps = getInstalledApps();
                installedAppAdapter.notifyDataSetChanged();
                final AppAdapter installedAppAdapter = new AppAdapter(MainActivity.this, installedApps);
                userInstalledApps.setAdapter(installedAppAdapter);
            }
        });

        NameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortF=1;
                tot=0;
                TimeB.setTextColor(Color.parseColor("#666666"));
                TimeB.setBackgroundColor(Color.parseColor("#edf4fb"));
                NameB.setTextColor(Color.parseColor("#FF8c00"));
                NameB.setBackgroundColor(Color.parseColor("#edf4fb"));
                NameB.setBackground(bottomBorder);
                TimeB.setBackgroundColor(Color.parseColor("#d5dbe1"));
                installedAppAdapter.notifyDataSetChanged();
                installedApps = getInstalledApps();
                installedAppAdapter.notifyDataSetChanged();
                final AppAdapter installedAppAdapter = new AppAdapter(MainActivity.this, installedApps);
                userInstalledApps.setAdapter(installedAppAdapter);
            }
        });


    }


    private List<AppList> getInstalledApps() {
        List<AppList> res = new ArrayList<AppList>();
        List<PackageInfo> packs = MainActivity.this.getPackageManager().getInstalledPackages(0);

        uncheckedFromSP =new ArrayList<String>();
        ArrayList<PackageInfo> temp=new ArrayList<PackageInfo>();
        for(PackageInfo k:packs)
        {
            temp.add(k);
        }
        packs.clear();
        for(PackageInfo k:temp)
        {
            if(!uncheckedFromSP.contains(k.packageName))
            {
                packs.add(k);
            }
        }

        if(sortF==1) {
            Collections.sort(packs, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo s1, PackageInfo s2) {
                    return s1.applicationInfo.loadLabel(MainActivity.this.getPackageManager()).toString().compareToIgnoreCase(s2.applicationInfo.loadLabel(MainActivity.this.getPackageManager()).toString());
                }
            });
        }

        for (int j = 0; j < packs.size(); j++) {

            PackageInfo p = packs.get(j);
            if ((isSystemPackage(p) == false)) {
                String appName = p.applicationInfo.loadLabel(MainActivity.this.getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(MainActivity.this.getPackageManager());
                PackName=p.packageName;
                timealpsed="NA";
                totalt=0;
                lastseen="NA";
                long s=0,m=0,h=0;
                for(UsageStats i: queryUsageStats) {
                    if (i.getPackageName().equals(PackName)) {
                        DateFormat mDateFormat = new SimpleDateFormat();
                        long seconds = i.getTotalTimeInForeground();
                        totalt+=seconds;
                        long seconds1 = i.getLastTimeStamp();

                        lastseen= mDateFormat.format(new Date(seconds1));
                    }
                }
                totalt = totalt / 1000;
                tot+=totalt;
                s = totalt % 60;
                m = (totalt / 60) % 60;
                h = (totalt / (60 * 60)) % 24;
                timealpsed= String.format("%02d:%02d:%02d", h, m, s);
                for(int q=0;q<7;q++){
                    if(datap.get(q).equals(PackName)){
                        datatime.set(q,timealpsed);
                    }
                }

                res.add(new AppList(appName, "Total Time: "+timealpsed,"Last Seen: "+lastseen,icon,PackName));

            }
            else{
                for(String k:sysapps)
                {
                    if(k.equals(p.packageName)){
                        String appName = p.applicationInfo.loadLabel(MainActivity.this.getPackageManager()).toString();
                        Drawable icon = p.applicationInfo.loadIcon(MainActivity.this.getPackageManager());
                        PackName=p.packageName;
                        timealpsed="";
                        totalt=0;
                        lastseen="NA";
                        long s=0,m=0,h=0;
                        for(UsageStats i: queryUsageStats) {
                            if (i.getPackageName().equals(PackName)) {
                                DateFormat mDateFormat = new SimpleDateFormat();
                                long seconds = i.getTotalTimeInForeground();
                                totalt+=seconds;
                                long seconds1 = i.getLastTimeStamp();

                                lastseen= mDateFormat.format(new Date(seconds1));
                            }
                        }       totalt = totalt / 1000;
                        tot+=totalt;
                        s = totalt % 60;
                        m = (totalt / 60) % 60;
                        h = (totalt / (60 * 60)) % 24;
                        timealpsed= String.format("%02d:%02d:%02d", h, m, s);
                        for(int q=0;q<7;q++){
                            if(datap.get(q).equals(PackName)){
                                datatime.set(q,timealpsed);
                            }
                        }
                        res.add(new AppList(appName, "Total Time: "+timealpsed,"Last Seen: "+lastseen,icon,PackName));


                    }
                }
            }
        }

        if(sortF==0) {
            Collections.sort(res, new Comparator<AppList>() {
                @Override
                public int compare(AppList s2, AppList s1) {
                    return (s1.getTime().compareToIgnoreCase(s2.getTime()));
                }
            });
        }
        return res;
    }


    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
public void alarm(View view)
{
    Intent intent = new Intent (this, alarm.class);
    startActivity(intent);
}

    public void not(View view)
    {
        Intent intent = new Intent (this, MyBroadcastReceiver2.class);
        startActivity(intent);
    }



}
