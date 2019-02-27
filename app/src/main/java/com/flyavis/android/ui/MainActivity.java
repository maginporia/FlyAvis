package com.flyavis.android.ui;

import android.app.ActivityManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.flyavis.android.R;
import com.flyavis.android.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BottomNavigationView navigation;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //init dataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navigation = binding.navigation;
        toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        initNavigation();
        initStyle();
    }

    /*
     * Navigation Architecture Component
     *
     *  res/navigation/nav_graph.xml
     *  Android Studio 3.3
     *  or 3.2 :Settings>experimental>Navigation Editor
     *
     *  用來管理fragment
     * */
    private void initNavigation() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) fragmentManager.findFragmentById(R.id.fragment);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupWithNavController(navigation, navController);
        //設定最外層fragment
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.layout.checklist_fragment);
        topLevelDestinations.add(R.layout.my_trips_fragment);
        topLevelDestinations.add(R.layout.spilt_bills_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(topLevelDestinations)
                .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        //toolBar & bottomNavigation控制
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.addNewTripFragment:
                    setNavigationVisibility(false);
                    break;
                case R.id.planningFragment:
                    setToolBarVisibility(true);
                    setNavigationVisibility(false);
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.plan_menu);
                    break;
                case R.id.planHelperFragment:
                    toolbar.getMenu().clear();
                    setNavigationVisibility(false);
                    break;
                default:
                    toolbar.getMenu().clear();
                    setNavigationVisibility(true);
                    setToolBarVisibility(false);
                    break;

            }
        });
    }

    //Colors and more
    private void initStyle() {
        //recent header color
        ActivityManager.TaskDescription taskDescription
                = new ActivityManager
                .TaskDescription(getString(R.string.app_name), BitmapFactory
                .decodeResource(getResources(), R.mipmap.ic_launcher_round), Color.WHITE);
        this.setTaskDescription(taskDescription);
        //Set light status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }
    }

    /**
     * Show ToolBar or not
     *
     * @param visible True or false
     */
    private void setToolBarVisibility(boolean visible) {
        if (toolbar.isShown() && !visible) {
            toolbar.setVisibility(View.GONE);
        } else if (!toolbar.isShown() && visible) {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show BottomNavigationView or not
     *
     * @param visible True or false
     */
    public void setNavigationVisibility(boolean visible) {
        if (navigation.isShown() && !visible) {
            navigation.setVisibility(View.GONE);
        } else if (!navigation.isShown() && visible) {
            navigation.setVisibility(View.VISIBLE);
        }
    }
}
