package com.defaultapps.moviebase.ui.movie;

import android.content.ComponentName;
import android.content.Intent;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.person.PersonActivity;
import com.thefinestartist.ytpa.YouTubePlayerActivity;
import com.thefinestartist.ytpa.enums.Orientation;

import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;


public class MovieNavigatorTest extends BaseRobolectricTest {

    @Mock
    private MovieContract.MovieView view;

    private MovieContract.MovieNavigator movieNavigator;
    private ShadowActivity shadowActivity;

    @Override
    public void setup() throws Exception {
        super.setup();
        movieNavigator = new MovieNavigatorImpl();
        movieNavigator.onAttach(view);
        when(view.provideActivity()).thenReturn(activity);
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void openFullScreenActivity() {
        final String ANY_VIDEO_PATH = "/2921jdfsdga2";
        movieNavigator.toFullScreenVideoActivity(ANY_VIDEO_PATH);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(ANY_VIDEO_PATH,
                intent.getStringExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID));
        assertEquals(Orientation.ONLY_LANDSCAPE,
                intent.getSerializableExtra(YouTubePlayerActivity.EXTRA_ORIENTATION));
        assertEquals(Intent.FLAG_ACTIVITY_NEW_TASK,
                intent.getFlags());
        assertEquals(intent.getComponent(),
                new ComponentName(activity, YouTubePlayerActivity.class));
    }

    @Test
    public void openPersonActivity() {
        final int ANY_PERSON_ID = 21414141;
        movieNavigator.toPersonActivity(ANY_PERSON_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, PersonActivity.class));
    }

    @Test
    public void shareMovie() {
        final String ANY_MESSAGE = "looooong message";
        movieNavigator.shareAction(ANY_MESSAGE);

        assertEquals(shadowActivity.peekNextStartedActivity().getAction(),
                Intent.ACTION_SEND);
    }
}
