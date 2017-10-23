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
import android.widget.RelativeLayout
import android.widget.TextView


class CuteActivity : AppCompatActivity() {

    private var revealTextFade: Animation? = null
    private var hideTextFade: AlphaAnimation? = null

    private var viewBundle: ViewBundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cute)

        revealTextFade = AnimationUtils.loadAnimation(this, R.anim.reveal_text)
        hideTextFade = AlphaAnimation(1.0f, 0.0f)
        hideTextFade?.duration = 250

        val rootLayout : RelativeLayout = findViewById(R.id.rootLayout)
        val textTitleView : TextView = findViewById(R.id.cuteTitleText)
        val titleView : TextView = findViewById(R.id.questionTitle)
        val chancer : TextView = findViewById(R.id.titleOne)
        val noChancer : TextView = findViewById(R.id.titleTwo)

        viewBundle = ViewBundle.Builder()
                .texTitleView(textTitleView)
                .titleView(titleView)
                .chancer(chancer)
                .noChancer(noChancer)
                .build()

        setupSharedElementTransition(rootLayout, viewBundle)

        if (savedInstanceState == null) {
            rootLayout.visibility = View.INVISIBLE

            val viewTreeObserver = rootLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        //circularRevealActivity()
                        Log.e("CuteActivity", "onGlobalLayout")
                        rootLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                })
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitReveal(viewBundle)
    }

    private fun setupSharedElementTransition(rootLayout: RelativeLayout, viewBundle: ViewBundle?) {

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
                if (viewBundle != null) {
                    circularRevealActivity(viewBundle)
                }
            }
        })
    }

    private fun circularRevealActivity(viewBundle: ViewBundle) {

        val cx = viewBundle.rootLayout?.getWidth()?.div(2)
        val cy = viewBundle.rootLayout?.getHeight()?.div(2)

        val finalRadius = Math.max(viewBundle.rootLayout!!.width!!, viewBundle.rootLayout!!.height).toFloat()

        // create the animator for this view (the start radius is zero)
        val circularReveal = ViewAnimationUtils.createCircularReveal(viewBundle.rootLayout, cx!!, cy!!, 0f, finalRadius)
        circularReveal.duration = 1000

        // make the view visible and start the animation
        viewBundle.rootLayout.setVisibility(View.VISIBLE)
        circularReveal.start()

        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                viewBundle.textTitleView?.setVisibility(View.VISIBLE)
                viewBundle.textTitleView?.startAnimation(revealTextFade)
                viewBundle.titleView?.setVisibility(View.VISIBLE)
                viewBundle.titleView?.startAnimation(revealTextFade)
                viewBundle.chancer?.setVisibility(View.VISIBLE)
                viewBundle.chancer?.startAnimation(revealTextFade)
                viewBundle.noChancer?.setVisibility(View.VISIBLE)
                viewBundle.noChancer?.startAnimation(revealTextFade)

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
    }

    internal fun exitReveal(viewBundle: ViewBundle?) {
        // previously visible view

        // get the center for the clipping circle
        val cx = viewBundle?.rootLayout?.getMeasuredWidth()?.div(2)
        val cy = viewBundle?.rootLayout?.getMeasuredHeight()?.div(2)

        // get the initial radius for the clipping circle
        val initialRadius = viewBundle?.rootLayout?.width?.div(2)

        // create the animation (the final radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(viewBundle?.rootLayout, cx!!, cy!!, initialRadius?.toFloat()!!, 0f)

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                viewBundle.rootLayout.visibility = View.INVISIBLE
                finish()
            }
        })

        // start the animation
        anim.start()
    }

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
}

