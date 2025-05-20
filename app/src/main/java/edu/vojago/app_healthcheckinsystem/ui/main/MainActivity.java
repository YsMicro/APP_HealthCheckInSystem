
package edu.vojago.app_healthcheckinsystem.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.vojago.app_healthcheckinsystem.R;
import edu.vojago.app_healthcheckinsystem.ui.chart.ChartFragment;
import edu.vojago.app_healthcheckinsystem.ui.profile.ProfileFragment;
import edu.vojago.app_healthcheckinsystem.ui.record.RecordFragment;

public class MainActivity extends AppCompatActivity {

    private final NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_record) {
                    selectedFragment = new RecordFragment();
                } else if (item.getItemId() == R.id.nav_chart) {
                    selectedFragment = new ChartFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        // 默认加载第一个Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RecordFragment())
                .commit();
    }
}