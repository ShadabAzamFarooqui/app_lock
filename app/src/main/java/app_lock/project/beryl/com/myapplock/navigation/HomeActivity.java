package app_lock.project.beryl.com.myapplock.navigation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import app_lock.project.beryl.com.myapplock.AllAppFragment;
import app_lock.project.beryl.com.myapplock.AppInfo;
import app_lock.project.beryl.com.myapplock.AppLockConstants;

import app_lock.project.beryl.com.myapplock.PasswordFragment;
import app_lock.project.beryl.com.myapplock.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Drawer.Result result = null;
    FragmentManager fragmentManager;
    Context context;
    Dialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    long numOfTimesAppOpened = 0;
    boolean isRated = false;
    public static ProgressDialog mProgressDialog;
    Handler mHandler;
    int mDrawerCloseTIme = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait...");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        context = getApplicationContext();
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        numOfTimesAppOpened = sharedPreferences.getLong(AppLockConstants.NUM_OF_TIMES_APP_OPENED, 0) + 1;
        isRated = sharedPreferences.getBoolean(AppLockConstants.IS_RATED, false);
        editor.putLong(AppLockConstants.NUM_OF_TIMES_APP_OPENED, numOfTimesAppOpened);
        editor.commit();


        //Google Analytics
//        Tracker t = ((AppLockApplication) getApplicationContext()).getTracker(AppLockApplication.TrackerName.APP_TRACKER);
//        t.setScreenName(AppLockConstants.MAIN_SCREEN);
//        t.send(new HitBuilders.AppViewBuilder().build());

        if (Build.VERSION.SDK_INT > 20) {
            Toast.makeText(getApplicationContext(), "If you have not allowed , allow App Lock so that it can work properly from sliding menu options", Toast.LENGTH_LONG).show();
        }
        fragmentManager = getSupportFragmentManager();

        mProgressDialog.show();
        getSupportActionBar().setTitle("All Applications");
        Fragment f = AllAppFragment.newInstance(AppLockConstants.ALL_APPS);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
//        AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Show All Applications Clicked", "show_all_applications_clicked", "");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) {
            mProgressDialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().setTitle("All Applications");
                    Fragment f = AllAppFragment.newInstance(AppLockConstants.ALL_APPS);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
//                    AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Show All Applications Clicked", "show_all_applications_clicked", "");
                }
            }, mDrawerCloseTIme);
        } else if (id == R.id.locked) {
            mProgressDialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().setTitle("Locked Applications");
                    Fragment f = AllAppFragment.newInstance(AppLockConstants.LOCKED);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
//                    AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Show Locked Applications Clicked", "show_locked_applications_clicked", "");

                }
            }, mDrawerCloseTIme);

        } else if (id == R.id.unlocked) {
            mProgressDialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().setTitle("Unlocked Applications");
                    Fragment f = AllAppFragment.newInstance(AppLockConstants.UNLOCKED);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
//                    AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Show Unlocked Applications Clicked", "show_unLocked_applications_clicked", "");

                }
            }, mDrawerCloseTIme);

        } else if (id == R.id.change_pass) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    getSupportActionBar().setTitle("Change Password");
                    Fragment f = PasswordFragment.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, f).commit();
//                    AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Password Changed Clicked", "password_changed_clicked", "");

                }
            }, mDrawerCloseTIme);


        } else if (id == R.id.allow_access) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    final Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "If you have not allowed , allow App Lock so that it can work properly", Toast.LENGTH_LONG).show();
//                    AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Allow Access", "allow_access", "");
//            result.setSelection(0);

                }
            }, mDrawerCloseTIme);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static List<AppInfo> getListOfInstalledApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<AppInfo> installedApps = new ArrayList();
        List<PackageInfo> apps = packageManager.getInstalledPackages(PackageManager.SIGNATURE_MATCH);
        if (apps != null && !apps.isEmpty()) {

            for (int i = 0; i < apps.size(); i++) {
                PackageInfo p = apps.get(i);
                ApplicationInfo appInfo = null;
                try {
                    appInfo = packageManager.getApplicationInfo(p.packageName, 0);
                    AppInfo app = new AppInfo();
                    app.setName(p.applicationInfo.loadLabel(packageManager).toString());
                    app.setPackageName(p.packageName);
                    app.setVersionName(p.versionName);
                    app.setVersionCode(p.versionCode);
                    app.setIcon(p.applicationInfo.loadIcon(packageManager));

                    //check if the application is not an application system
//                    Intent launchIntent = app.getLaunchIntent(context);
//                    if (launchIntent != null && (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    installedApps.add(app);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            //sort the list of applications alphabetically
            if (installedApps.size() > 0) {
                Collections.sort(installedApps, new Comparator() {

                    @Override
                    public int compare(Object a1, Object a2) {
                        final AppInfo app1 = (AppInfo) a1;
                        final AppInfo app2 = (AppInfo) a2;
                        return app1.getName().toLowerCase(Locale.getDefault()).compareTo(app2.getName().toLowerCase(Locale.getDefault()));
                    }
                });
            }
            return installedApps;
        }
        return null;
    }
}
