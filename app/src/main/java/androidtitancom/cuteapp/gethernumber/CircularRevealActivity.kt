package androidtitancom.cuteapp.gethernumber

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidtitancom.cuteapp.R

abstract class CircularRevealActivity : AppCompatActivity() {

    lateinit var revealTextFade: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        revealTextFade = AnimationUtils.loadAnimation(this, R.anim.reveal_text)
    }

    fun circularRevealActivity(rootLayout: RelativeLayout) {

        val cx = rootLayout.width.div(2)
        val cy = rootLayout.height.div(2)

        val finalRadius = Math.max(rootLayout.width, rootLayout.height).toFloat()

        // create the animator for this view (the start radius is zero)
        val circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0f, finalRadius)
        circularReveal.duration = 1000

        // make the view visible and start the animation
        rootLayout.setVisibility(View.VISIBLE)
        circularReveal.start()

        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                handleAnimations(View.VISIBLE, revealTextFade)
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }

    fun exitReveal(rootLayout: RelativeLayout, isDBB: Boolean = false) {
        // previously visible view

        // get the center for the clipping circle
        val cx = rootLayout.measuredWidth.div(2)
        val cy = rootLayout.measuredHeight.div(2)

        // get the initial radius for the clipping circle
        val initialRadius = rootLayout.width.div(2)

        // create the animation (the final radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy,
                initialRadius.toFloat(), 0f)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                rootLayout.visibility = View.INVISIBLE

                if(isDBB) {
                    finish()
                } else {
                    launchSharedAnimationActivity()
                }
            }
        })

        // start the animation
        anim.start()
    }

    abstract fun launchSharedAnimationActivity()


    abstract fun handleAnimations(visibility: Int, animation: Animation)
}
