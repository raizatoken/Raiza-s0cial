package com.project.raizasocial.utils;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import rx.functions.Func1;

public class ToMostProbableActivity implements Func1<ActivityRecognitionResult, DetectedActivity> {
    /**
     * {@inheritDoc}
     * @param activityRecognitionResult
     * @return
     */
    @Override
    public DetectedActivity call(ActivityRecognitionResult activityRecognitionResult) {
        return activityRecognitionResult.getMostProbableActivity();
    }
}
