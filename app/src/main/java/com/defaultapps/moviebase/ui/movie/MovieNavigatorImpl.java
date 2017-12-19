package com.defaultapps.moviebase.ui.movie;

import android.content.Intent;

import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.defaultapps.moviebase.ui.person.PersonActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.thefinestartist.ytpa.YouTubePlayerActivity;
import com.thefinestartist.ytpa.enums.Orientation;

import javax.inject.Inject;

@PerFragment
public class MovieNavigatorImpl extends DefaultNavigator<MovieContract.MovieView> implements MovieContract.MovieNavigator {

    @Inject
    MovieNavigatorImpl() {}

    @Override
    public void toFullScreenVideoActivity(String videoPath) {
        Intent intent = new Intent(castToBase(), YouTubePlayerActivity.class);
        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, videoPath);
        intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.ONLY_LANDSCAPE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        castToBase().startActivity(intent);
    }

    @Override
    public void toPersonActivity(int personId) {
        Intent intent = new Intent(castToBase(), PersonActivity.class)
                .putExtra(AppConstants.PERSON_ID, personId);
        castToBase().startActivity(intent);
    }

    @Override
    public void shareAction(String message) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, message)
                .setType("text/plain")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        castToBase().startActivity(shareIntent);
    }
}
