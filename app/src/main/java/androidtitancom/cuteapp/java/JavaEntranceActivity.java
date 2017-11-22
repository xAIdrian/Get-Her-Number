package androidtitancom.cuteapp.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidtitancom.cuteapp.R;


public class JavaEntranceActivity extends AppCompatActivity {

    //@Bind(R.id.fab)
    FloatingActionButton fab;
    //@Bind(R.id.fadeView)
    View circleView;

    Animation pulse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_entrance);
        //ButterKnife.bind(this);

        getSupportActionBar().setTitle("");


        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        final Animation pulseNfade = AnimationUtils.loadAnimation(this, R.anim.fading_scale);

        pulse.setRepeatCount(Animation.INFINITE);

        fab.startAnimation(pulse);
        circleView.startAnimation(pulseNfade);

        pulseNfade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                circleView.startAnimation(pulseNfade);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        fab.setOnClickListener(v -> {

            Intent intent = new Intent(JavaEntranceActivity.this, JavaCuteActivity.class);
            startActivity(intent);

        });

    }
}
