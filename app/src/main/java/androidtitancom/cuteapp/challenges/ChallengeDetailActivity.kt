package androidtitancom.cuteapp.challenges

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import androidtitancom.cuteapp.R
import androidtitancom.cuteapp.model.Challenge
import kotlinx.android.synthetic.main.activity_challenge_detail.*

class ChallengeDetailActivity : AppCompatActivity() {

    companion object {
        val CHALLENGE_DETAIL_EXTRA = "challenge_detail_string"
    }

    var challengeTitle : String = ""
    var challengeSubTitle : String = ""
    var challengeHtmlInstruction : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_detail)
        setSupportActionBar(toolbar)

        intent.extras?.let {
            val challenge : Challenge =
                    intent.extras.getParcelable(CHALLENGE_DETAIL_EXTRA)
            challengeTitle = challenge.title
            challengeSubTitle = challenge.subTitle
            challengeHtmlInstruction = challenge.htmlInstruction

        }

        if (savedInstanceState != null) {

        } else {

        }


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}
