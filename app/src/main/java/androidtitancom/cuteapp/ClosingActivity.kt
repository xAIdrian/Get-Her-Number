package androidtitancom.cuteapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidtitancom.cuteapp.model.CuteUser

import kotlinx.android.synthetic.main.activity_closing.*
import kotlinx.android.synthetic.main.content_closing.*

class ClosingActivity : AppCompatActivity() {

    lateinit var cutie: CuteUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closing)

        //view
        toolbar.title = resources.getString(R.string.welcome_back)
        toolbar.setTitleTextColor(getColor(R.color.colorAccent))

        val extras = intent.extras
        val optionSelected = extras?.getString(CuteActivity.OPTION_EXTRA)

        if (optionSelected != null && optionSelected.isNotEmpty()) {
            cutie = CuteUser(optionSelected)
            setHintImage(optionSelected)
        }

        if (savedInstanceState == null) {
            //closingRootRelativeLayout.visibility = View.INVISIBLE

            val viewTreeObserver = closingRootRelativeLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        //circularRevealActivity(closingRootRelativeLayout)

                        Log.e("ClosingActivity", "onGlobalLayout")
                        closingRootRelativeLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                })
            }
        }
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
}
