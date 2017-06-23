package com.defaultapps.moviebase.ui.staff;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.utils.AppConstants;

public class StaffActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        if (savedInstanceState == null) {
            int staffMode = getIntent().getIntExtra(AppConstants.CREW_OR_CAST, 0);
            int staffId = getIntent().getIntExtra(AppConstants.STAFF_ID, -1);
            StaffViewImpl staffView = StaffViewImpl.createInstance(staffMode, staffId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, staffView)
                    .commit();
        }
    }

}
