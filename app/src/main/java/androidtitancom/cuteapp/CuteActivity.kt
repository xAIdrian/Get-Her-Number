package androidtitancom.cuteapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ArrayAdapter
import androidtitancom.cuteapp.model.CuteUser
import kotlinx.android.synthetic.main.activity_cute_content.*


class CuteActivity : AppCompatActivity() {

    lateinit var cutie: CuteUser

    private var revealTextFade: Animation? = null
    //private var hideTextFade: AlphaAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cute)

        var handler = Handler()

        revealTextFade = AnimationUtils.loadAnimation(this, R.anim.reveal_text)
        val hideTextFade = AlphaAnimation(1.0f, 0.0f)
        hideTextFade.duration = 250

        setupSharedElementTransition()

        if (savedInstanceState == null) {
            rootRelativeLayout.visibility = View.INVISIBLE

            val viewTreeObserver = rootRelativeLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        circularRevealActivity()

                        Log.e("CuteActivity", "onGlobalLayout")
                        rootRelativeLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                })
            }
        }

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

        builder.setNegativeButton(getString(R.string.picker_cancel), { dialog, _ ->
            run {
                dialog.dismiss()
                this.finish()
            }
        })

        builder.setAdapter(optionAdapter, { dialog, which ->
            run {
                //this will be stored to know who selected what
                //reflect in the UI hints
                val optionSelected = optionAdapter.getItem(which)

                cutie = CuteUser(optionSelected)
                setHintImage(optionSelected)

                dialog.dismiss()
                //calendarDialog.show(supportFragmentManager, getString(R.string.calendar_identify))

            }
        })
        builder.show()
    }

    private fun setHintImage(optionSelected: String) {

        when(optionSelected) {
            getString(R.string.crazy) -> selectionImageView.setImageResource(R.drawable.ic_alert_octagram)
            getString(R.string.classy) -> selectionImageView.setImageResource(R.drawable.ic_glass_flute)
            getString(R.string.basic) -> selectionImageView.setImageResource(R.drawable.ic_airplane_off)
            getString(R.string.adventurous) -> selectionImageView.setImageResource(R.drawable.ic_tree)
            getString(R.string.chill) -> selectionImageView.setImageResource(R.drawable.ic_movie)
        }
    }

    override fun onBackPressed() {
        exitReveal()
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
                circularRevealActivity()
            }
        })
    }

    private fun circularRevealActivity() {

        val cx = rootRelativeLayout.width.div(2)
        val cy = rootRelativeLayout.height.div(2)

        val finalRadius = Math.max(rootRelativeLayout.width, rootRelativeLayout.height).toFloat()

        // create the animator for this view (the start radius is zero)
        val circularReveal = ViewAnimationUtils.createCircularReveal(rootRelativeLayout, cx, cy, 0f, finalRadius)
        circularReveal.duration = 1000

        // make the view visible and start the animation
        rootRelativeLayout.setVisibility(View.VISIBLE)
        circularReveal.start()

        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                titleTextView.setVisibility(View.VISIBLE)
                titleTextView.startAnimation(revealTextFade)
                questionTextView.setVisibility(View.VISIBLE)
                questionTextView.startAnimation(revealTextFade)
                chanceTextView.setVisibility(View.VISIBLE)
                chanceTextView.startAnimation(revealTextFade)
                noChanceTextView.setVisibility(View.VISIBLE)
                noChanceTextView.startAnimation(revealTextFade)
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }

    private fun exitReveal() {
        // previously visible view

        // get the center for the clipping circle
        val cx = rootRelativeLayout.measuredWidth.div(2)
        val cy = rootRelativeLayout.measuredHeight.div(2)

        // get the initial radius for the clipping circle
        val initialRadius = rootRelativeLayout.width.div(2)

        // create the animation (the final radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(rootRelativeLayout, cx, cy,
                initialRadius.toFloat(), 0f)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                rootRelativeLayout.visibility = View.INVISIBLE
                finish()
            }
        })

        // start the animation
        anim.start()
    }
}

