package androidtitancom.cuteapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_press_button.*


class PressButtonActivity : AppCompatActivity() {
    companion object {
        val HAS_BEEN_ONBOARDED_PREFERENCE = "entraceactivity.shouldonboardpreference"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_press_button)

        showOnboardingDialog()

        //view
        toolbar.title = resources.getString(R.string.hey_you)
        toolbar.setTitleTextColor(getColor(R.color.colorAccent))

        fab.setOnClickListener {
            newAnimationIntent(this, fab)
        }

        settingsImageView.setOnClickListener {

            val builder = AlertDialog.Builder(this@PressButtonActivity)
                    .setTitle(R.string.tech_stuff)
                    .setMessage(R.string.eula)
                    .setPositiveButton(R.string.ok, { dialog, _ ->
                        dialog.dismiss()
                    })
            builder.create().show()

        }

        //handling our animations
        val pulseAnimation : Animation = AnimationUtils.loadAnimation(this, R.anim.pulse)
        val pulseAndFadeAnimation : Animation = AnimationUtils.loadAnimation(this, R.anim.fading_scale)

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

    private fun showOnboardingDialog() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val onboarded = sharedPreferences.getBoolean(HAS_BEEN_ONBOARDED_PREFERENCE, false)

        //if first time entering the app show the dialog fragment
        if (!onboarded) {
            val onboardingFragment = OnboardingDialogFragment()
            onboardingFragment.isCancelable = false
            onboardingFragment.show(fragmentManager, getString(R.string.onboarding_fragment))

            sharedPreferences.edit().putBoolean(HAS_BEEN_ONBOARDED_PREFERENCE, true).apply()
        }
    }

    private fun newAnimationIntent(context : Context, view : View) {
        val intent = Intent(context, CuteActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, resources.getString(R.string.fab_activity_transition))

        startActivity(intent, options.toBundle())
    }
}
