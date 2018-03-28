package com.defaultapps.moviebase.ui.about;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.defaultapps.moviebase.di.scope.PerFragment;

import javax.inject.Inject;

@PerFragment
class AboutAnimationFactory {

    @Inject
    AboutAnimationFactory() {}

    RotateAnimation createRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new OvershootInterpolator());
        return rotateAnimation;
    }

    TranslateAnimation createTranslateAnimation(float fromY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                0,
                0,
                fromY,
                0);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        return translateAnimation;
    }

    ScaleAnimation createScaleAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0f, 1f,
                0f, 1f,
                Animation.RELATIVE_TO_SELF, .5f,
                Animation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        return scaleAnimation;
    }

}
