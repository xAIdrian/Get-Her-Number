package androidtitancom.cuteapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidtitancom.cuteapp.java.HowsHeDoingActivity
import android.support.v4.app.ActivityOptionsCompat



class EntranceActivity : AppCompatActivity() {

    private val ANIMATION_INTENT : String = "entranceActivity.animation_intent"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

        //view
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.title = resources.getString(R.string.hey_you)
        toolbar.setTitleTextColor(getColor(R.color.colorAccent))

        val fab : FloatingActionButton = findViewById(R.id.fab)
        val circleView : View = findViewById(R.id.fadeView)
        val settingsImageView : ImageView = findViewById(R.id.settings_imageview)

        fab.setOnClickListener {
            newAnimationIntent(this, fab)
        }
        settingsImageView.setOnClickListener {
            //val intent : Intent = Intent(applicationContext, SettingsActivity::class.java)
            //startActivity(intent)
            Toast.makeText(this, "Still need to implement our passwords and settings", Toast.LENGTH_SHORT).show()
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
        val intent : Intent = Intent(context, CuteActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, resources.getString(R.string.fab_activity_transition))
        startActivity(intent, options.toBundle())
    }

    /*
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater : MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_entrance, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {

            when(item?.itemId) {
                R.id.menu_settings -> {
                    //val intent : Intent = Intent(applicationContext, SettingsActivity::class.java)
                    //startActivity(intent)
                }
                else -> {
                    throw IllegalArgumentException("Invalid options item: " + item?.itemId)
                }
            }

            return super.onOptionsItemSelected(item)
        }
    */
    override fun onResume() {
        super.onResume()
    }


}
