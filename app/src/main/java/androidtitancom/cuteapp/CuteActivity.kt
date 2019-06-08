package androidtitancom.cuteapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cute.*
import kotlinx.android.synthetic.main.content_cute.*


class CuteActivity : CircularRevealActivity() {

    private lateinit var optionSelected: String

    companion object {
        val OPTION_EXTRA: String = "cuteactivity.option.extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cute)

        setupSharedElementTransition()

        val handler = Handler()

        val hideTextFade = AlphaAnimation(1.0f, 0.0f)
        hideTextFade.duration = 250

        //onClickListeners
        chanceTextView.setOnClickListener {

            noChanceTextView.startAnimation(hideTextFade)
            heartImageView.startAnimation(hideTextFade)
            questionTextView.startAnimation(hideTextFade)

            handler.postDelayed({

                noChanceTextView.visibility = View.INVISIBLE
                heartImageView.visibility = View.INVISIBLE
                questionTextView.visibility = View.INVISIBLE

                val translateOutDown = TranslateAnimation(0F, 0F, 0F, 500F)
                val translateOutUp = TranslateAnimation(0F, 0F, 0F, -500F)
                translateOutDown.duration = 300
                translateOutUp.duration = 300

                chanceTextView.startAnimation(translateOutDown)
                titleTextView.startAnimation(translateOutUp)

                //display dialog for date and calendar
                handler.postDelayed({

                    chanceTextView.visibility = View.INVISIBLE
                    noChanceTextView.visibility = View.INVISIBLE
                    titleTextView.visibility = View.INVISIBLE

                    buildDateDialog()

                }, translateOutDown.duration)

            }, hideTextFade.duration)
        }

        noChanceTextView.setOnClickListener { Toast.makeText(this, R.string.lies, Toast.LENGTH_LONG).show() }
    }

    private fun buildDateDialog() {

        val builder = AlertDialog.Builder(this)
        val optionAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        builder.setTitle(getString(R.string.what_kind))

        optionAdapter.add(getString(R.string.crazy))
        optionAdapter.add(getString(R.string.classy))
        optionAdapter.add(getString(R.string.basic))
        optionAdapter.add(getString(R.string.adventurous))
        optionAdapter.add(getString(R.string.chill))

        builder.setAdapter(optionAdapter) { dialog, which ->
            run {
                dialog.dismiss()
                //this will be stored to know who selected what
                //reflect in the UI hints
                optionSelected = optionAdapter.getItem(which)

                exitReveal(rootRelativeLayout)
            }
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun launchSharedAnimationActivity() {

        val intent = Intent(this, ClosingActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, fab, resources.getString(R.string.fab_activity_transition))

        intent.putExtra(OPTION_EXTRA, optionSelected)
        startActivity(intent, options.toBundle())

    }

    private fun setupSharedElementTransition() {

        val sharedElementEnterTransition = window.sharedElementEnterTransition
        sharedElementEnterTransition.addListener(object : android.transition.Transition.TransitionListener {
            override fun onTransitionEnd(transition: android.transition.Transition?) {
            }

            override fun onTransitionResume(transition: android.transition.Transition?) {
            }

            override fun onTransitionPause(transition: android.transition.Transition?) {
            }

            override fun onTransitionCancel(transition: android.transition.Transition?) {
            }

            override fun onTransitionStart(transition: android.transition.Transition?) {
                Log.e("CuteActivty", "onTransitionStart")
                circularRevealActivity(rootRelativeLayout)
            }
        })
    }

    override fun onBackPressed() {
        exitReveal(rootRelativeLayout, true)
    }

    override fun handleAnimations(visibility: Int, animation: Animation) {

        titleTextView.visibility = visibility
        titleTextView.startAnimation(animation)
        questionTextView.visibility = visibility
        questionTextView.startAnimation(animation)
        chanceTextView.visibility = visibility
        chanceTextView.startAnimation(animation)
        noChanceTextView.visibility = visibility
        noChanceTextView.startAnimation(animation)
    }
}

