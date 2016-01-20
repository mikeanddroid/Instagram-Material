package com.mike.givemewingzz.instagram_codechallenge.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.core.BaseAuthAppCompat;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RobotoTextView;
import com.mike.givemewingzz.instagram_codechallenge.customviews.RoundedImageView;
import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;
import com.mike.givemewingzz.instagram_codechallenge.utils.image_utils.ImageLoaderUtility;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class LandingPage extends BaseAuthAppCompat implements View.OnClickListener {

    public static final String TAG = LandingPage.class.getSimpleName();

    @Bind(R.id.userFirstName)
    public RobotoTextView userFirstName;

    @Bind(R.id.userBio)
    public RobotoTextView userBio;

    @Bind(R.id.userHeadline)
    public RobotoTextView userHeadline;

    @Bind(R.id.profileBlur)
    public ImageView profileBlur;

    @Bind(R.id.profileImage)
    public RoundedImageView profilePicture;

    @Bind(R.id.landingPage)
    public ViewGroup viewGroup;

    @Bind(R.id.next)
    public Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {

            // Start and Re-enter Transitions

            TransitionInflater transitionInflater = TransitionInflater.from(this);
            Transition transition = transitionInflater.inflateTransition(R.transition.landingpage_trans);
            getWindow().setExitTransition(transition);

            Slide slide = new Slide();
            slide.setDuration(2000);
            getWindow().setReenterTransition(transition);

            // Shared element transitions
//            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_transition));

        }

        setContentView(R.layout.landing_page);

        ButterKnife.bind(this);

        viewGroup.setOnClickListener(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityOptionsCompat optionsCompat = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(LandingPage.this, null);
                }

                Intent intent = new Intent(LandingPage.this, TagsPage.class);
                startActivity(intent, optionsCompat.toBundle());

            }
        });

        Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        viewGroup.startAnimation(animation);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusSingleton.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusSingleton.register(this);
    }

    @Override
    protected void onLoginSucceeded(Token token) {
    }

    @Override
    protected void onTokenLoaded(Token token) {

        ImageLoaderUtility.blurAndLoadImage(token.getUserProfileImage(this), profileBlur);

        Token tempToken = token;
        tempToken.load(this);

        Log.d(TAG, "Auth Token : " + token.getAccessToken());

        String welcomeKey = this.getResources().getString(R.string.welcome_key);
        String welcomedUserHead = welcomeKey.toUpperCase() + " " + tempToken.getUserFullName(this).toUpperCase() + "\u0023";

        userFirstName.setText(welcomedUserHead);

        userBio.setText(tempToken.getUserBio(this));

        String headLine = " " + tempToken.getUserName(this)
                + ",...\n" + "You got " + " \u0023" + "<font color='#0091EA'>"
                + tempToken.getUserMediaCount(this) + "</font>" + " posts and" + " \u0023"
                + "<font color='#0091EA'>" + tempToken.getFollowedBy(this) + "</font>" + " followers.";

        String inject = "<font color='#0091EA'>Hey</font>";

        String finalHeadLine = inject + headLine;

        userHeadline.setText(Html.fromHtml(finalHeadLine));

        ImageLoaderUtility.loadImage(token.getUserProfileImage(this), profilePicture);

    }

    private void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        Fade fade = new Fade();
        fade.setDuration(1000);

        Explode explode = new Explode();
        explode.setDuration(1200);

//        TransitionManager.beginDelayedTransition(viewGroup);
//        TransitionManager.beginDelayedTransition(viewGroup, explode);

//        toggleVisibility(userBio, profilePicture, userHeadline);

    }

    public void toggleHeight(View... views) {

        for (View current : views) {

            ViewGroup.LayoutParams params = current.getLayoutParams();
            params.height = 100;
            params.width = 50;
            current.setLayoutParams(params);
        }

    }

}
