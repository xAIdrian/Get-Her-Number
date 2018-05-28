package androidtitancom.cuteapp.gethernumber

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.GravityCompat
import android.view.Gravity
import android.view.MenuItem
import androidtitancom.cuteapp.OnboardingDialogFragment
import androidtitancom.cuteapp.R
import androidtitancom.cuteapp.challenges.ComfortZoneActivity
import kotlinx.android.synthetic.main.activity_press_button.*


class PressButtonActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        nav_view.setNavigationItemSelectedListener(this)

        fab.setOnClickListener {
            newAnimationIntent(this, fab)
        }

        settingsImageView.setOnClickListener {

            drawer_layout.openDrawer(Gravity.START)

            /*
            these will go in our actual settings
            val builder = AlertDialog.Builder(this@PressButtonActivity)
                    .setTitle(R.string.tech_stuff)
                    .setMessage(R.string.eula)
                    .setPositiveButton(R.string.ok, { dialog, _ ->
                        dialog.dismiss()
                    })
            builder.create().show() */

        }

        startAnimation()
    }

    override
    fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_comfort_zone -> {
                startActivity(Intent(this@PressButtonActivity, ComfortZoneActivity::class.java))
            }
            R.id.nav_settings -> {
                Snackbar.make(currentFocus, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
            R.id.nav_info -> {
                Snackbar.make(currentFocus, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun startAnimation() {
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
