package androidtitancom.cuteapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.support.v4.app.ActivityOptionsCompat
import kotlinx.android.synthetic.main.activity_entrance.*


class EntranceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        //request permission to send text messages
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE),1);

        //view
        toolbar.title = resources.getString(R.string.hey_you)
        toolbar.setTitleTextColor(getColor(R.color.colorAccent))

        fab.setOnClickListener {
            newAnimationIntent(this, fab)
        }

        //handling our animations
        val pulseAnimation : Animation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        val pulseAndFadeAnimation : Animation = AnimationUtils.loadAnimation(this, R.anim.fading_scale);

        pulseAnimation.repeatCount = Animation.INFINITE
        pulseAndFadeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                circleView.startAnimation(pulseAndFadeAnimation)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })

        fab.startAnimation(pulseAnimation)
        circleView.startAnimation(pulseAndFadeAnimation)
    }

    private fun newAnimationIntent(context : Context, view : View) {
        val intent = Intent(context, CuteActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, resources.getString(R.string.fab_activity_transition))

        startActivity(intent, options.toBundle())
    }
}
