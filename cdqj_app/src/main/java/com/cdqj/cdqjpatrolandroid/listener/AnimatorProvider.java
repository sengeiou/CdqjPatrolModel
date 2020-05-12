package com.cdqj.cdqjpatrolandroid.listener;

import android.animation.Animator;
import android.view.View;

/**
 * StateView 关联AnimatorProvider
 */
public interface AnimatorProvider {

    Animator showAnimation(View view);

    Animator hideAnimation(View view);
}