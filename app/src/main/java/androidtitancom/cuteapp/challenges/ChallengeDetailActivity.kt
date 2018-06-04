package androidtitancom.cuteapp.challenges

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import androidtitancom.cuteapp.R
import androidtitancom.cuteapp.challenges.ChallengeListViewModel.Companion.CHALLENGE_EXTRA
import androidtitancom.cuteapp.model.Challenge
import kotlinx.android.synthetic.main.activity_challenge_detail.*

class ChallengeDetailActivity : AppCompatActivity() {

    //todo BOTTOM SHEET TO PAY https://www.androidhive.info/2017/12/android-working-with-bottom-sheet/
    companion object {
        const val CHALLENGE_DETAIL_EXTRA = "challenge_detail_string"
    }
    private var challenge : Challenge? = null

    var challengeTitle : String = ""
    var challengeSubTitle : String = ""
    var challengeHtmlInstruction : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_detail)
        setSupportActionBar(toolbar)

        if (savedInstanceState != null) {
            mapExtra(savedInstanceState.getParcelable(CHALLENGE_DETAIL_EXTRA))
        } else {
            intent.extras?.let {
                mapExtra(intent.extras.getParcelable(CHALLENGE_EXTRA))
            }
        }

        toolbar_layout.title = challengeTitle

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(
                CHALLENGE_DETAIL_EXTRA, challenge
        )
        super.onSaveInstanceState(outState)
    }

    private fun mapExtra(challenge: Challenge?) {
        challengeTitle = challenge?.title?: ""
        challengeSubTitle = challenge?.subTitle?: ""
        challengeHtmlInstruction = challenge?.htmlInstruction?: ""
    }
}
