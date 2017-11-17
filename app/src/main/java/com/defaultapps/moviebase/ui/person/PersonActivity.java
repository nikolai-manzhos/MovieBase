package com.defaultapps.moviebase.ui.person;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.utils.AppConstants;

public class PersonActivity extends BaseActivity {

    @Override
    protected int provideLayout() {
        return R.layout.activity_person;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            int staffId = getIntent().getIntExtra(AppConstants.PERSON_ID, -1);
            PersonViewImpl staffView = PersonViewImpl.createInstance(staffId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, staffView)
                    .commit();
        }
    }

}
