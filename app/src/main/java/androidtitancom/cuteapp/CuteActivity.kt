package androidtitancom.cuteapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_cute_content.*


class CuteActivity : AppCompatActivity() {

    private var revealTextFade: Animation? = null
    private var hideTextFade: AlphaAnimation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cute)

        revealTextFade = AnimationUtils.loadAnimation(this, R.anim.reveal_text)
        hideTextFade = AlphaAnimation(1.0f, 0.0f)
        hideTextFade?.duration = 250


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
/*
    class ViewBundle( //add private constructor if necessary
            val rootLayout: RelativeLayout?,
            val textTitleView: TextView?,
            val titleView: TextView?,
            val chancer: TextView?,
            val noChancer: TextView?
    ) {

        private constructor(builder: Builder) : this(builder.rootLayout, builder.textTitleView, builder.titleView, builder.chancer, builder.noChancer)

        class Builder {
            var rootLayout: RelativeLayout? = null
                private set
            var textTitleView: TextView? = null
                private set
            var titleView: TextView? = null
                private set
            var chancer : TextView? = null
                private set
            var noChancer : TextView? = null
                private set

            fun rootLayout(relativeLayout: RelativeLayout) = apply{ this.rootLayout = relativeLayout}
            fun texTitleView(textView: TextView) = apply { this.textTitleView = textView }
            fun titleView(textView: TextView) = apply { this.titleView = textView }
            fun chancer(textView: TextView) = apply { this.chancer = textView }
            fun noChancer(textView: TextView) = apply { this.noChancer = textView }


            fun build() = ViewBundle(this)
        }
    }
    */
}

