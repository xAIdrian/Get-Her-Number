package androidtitancom.cuteapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HowsHeDoingActivity extends AppCompatActivity {

    @Bind(R.id.rootLayout)
    RelativeLayout rootLayout;

    @Bind(R.id.heartImage)
    ImageView heartImage;

    @Bind(R.id.cuteTitleText)
    TextView textTitle;
    @Bind(R.id.questionTitle)
    TextView titleView;
    @Bind(R.id.titleOne)
    TextView chancer;
    @Bind(R.id.titleTwo)
    TextView noChancer;
    @Bind(R.id.revealTitleText)
            TextView revealText;
    @Bind(R.id.secondRevealLayout)
            RelativeLayout secondRevealLayout;

    Animation revealTextFade;
    Animation hideTextFade;

    CalendarDatePickerDialogFragment calendarDialog;
    CalendarDatePickerDialogFragment.OnDateSetListener dateSetListener;
    CalendarDatePickerDialogFragment.OnDialogDismissListener dismissListener;

    Handler handler;
    private String dateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howshedoing);
        ButterKnife.bind(this);

        handler = new Handler();

        //overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        revealTextFade = AnimationUtils.loadAnimation(this, R.anim.reveal_text);
        hideTextFade = new AlphaAnimation(1.0f, 0.0f);
        hideTextFade.setDuration(250);

        DatePickerConstructor();
        // Show date picker calendarDialog.
        calendarDialog = new CalendarDatePickerDialogFragment();
        calendarDialog.setOnDateSetListener(dateSetListener);
        calendarDialog.setOnDismissListener(dismissListener);

        if (savedInstanceState == null) {
            rootLayout.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
            }
        }

        chancer.setOnClickListener((v) -> {

            noChancer.startAnimation(hideTextFade);
            heartImage.startAnimation(hideTextFade);
            titleView.startAnimation(hideTextFade);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    noChancer.setVisibility(View.INVISIBLE);
                    heartImage.setVisibility(View.INVISIBLE);
                    titleView.setVisibility(View.INVISIBLE);

                    TranslateAnimation translateOutDown = new TranslateAnimation(0, 0, 0, 500);
                    translateOutDown.setDuration(300);
                    TranslateAnimation translateOutUp = new TranslateAnimation(0, 0, 0, -500);
                    translateOutUp.setDuration(300);

                    chancer.startAnimation(translateOutDown);
                    textTitle.startAnimation(translateOutUp);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            chancer.setVisibility(View.INVISIBLE);
                            noChancer.setVisibility(View.INVISIBLE);
                            textTitle.setVisibility(View.INVISIBLE);

                            dialogBuilder();
                        }
                    }, translateOutDown.getDuration());
                }
            }, hideTextFade.getDuration());
        });


        calendarDialog.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                dateSelected = "You picked the following date: " + (dayOfMonth + 1) + "/" + (monthOfYear + 1) + "/" + year;
                Log.e("Stringer", dateSelected);

                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("day", (dayOfMonth + 1));
                bundle.putInt("month", (monthOfYear + 1));
                bundle.putInt("year", year);

                MyAwesomeDialogFragment dialogFragment = MyAwesomeDialogFragment.newInstance();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "dialogFrag");
            }
        });

        noChancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HowsHeDoingActivity.this, "Don\'t lie to yourself.  He\'s a cutie", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void dialogBuilder() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("So, what kind of date are we going on?");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1);
        arrayAdapter.add("Paint the town red");
        arrayAdapter.add("A relaxed drink in the classy part of town");
        arrayAdapter.add("Sushi and white wine");
        arrayAdapter.add("Rock climbing and granola bars");
        arrayAdapter.add("Watch a movie and bourbon on the rocks");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        HowsHeDoingActivity.this.finish();

                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        dialog.dismiss();

                        FragmentManager fm = getSupportFragmentManager();
                        calendarDialog.show(fm, "calendarDialog");

                    }
                });
        builderSingle.show();
    }

    private void DatePickerConstructor() {
        // Create date picker listener.
        dateSetListener = new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                // Set date from user input.
                Calendar date = Calendar.getInstance();
                date.set(Calendar.HOUR_OF_DAY, 9);
                date.set(Calendar.MINUTE, 0);
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, monthOfYear);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth + 1);

            }

        };

        // Create dismiss listener.
        dismissListener = new CalendarDatePickerDialogFragment.OnDialogDismissListener() {
            @Override
            public void onDialogDismiss(DialogInterface dialoginterface) {
                // Do something when the user dismisses the calendarDialog.

            }
        };
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        exitReveal();

    }

    private void circularRevealActivity() {

        int cx = rootLayout.getWidth() / 2;
        int cy = rootLayout.getHeight() / 2;

        float finalRadius = Math.max(rootLayout.getWidth(), rootLayout.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE);
        circularReveal.start();

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                        textTitle.setVisibility(View.VISIBLE);
                        textTitle.startAnimation(revealTextFade);
                        titleView.setVisibility(View.VISIBLE);
                        titleView.startAnimation(revealTextFade);
                        chancer.setVisibility(View.VISIBLE);
                        chancer.startAnimation(revealTextFade);
                        noChancer.setVisibility(View.VISIBLE);
                        noChancer.startAnimation(revealTextFade);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void exitReveal() {
        // previously visible view

        // get the center for the clipping circle
        int cx = rootLayout.getMeasuredWidth() / 2;
        int cy = rootLayout.getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = rootLayout.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootLayout.setVisibility(View.INVISIBLE);
                HowsHeDoingActivity.this.finish();
            }
        });

        // start the animation
        anim.start();
    }
}
