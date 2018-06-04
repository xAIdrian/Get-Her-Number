package androidtitancom.cuteapp.challenges

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import androidtitancom.cuteapp.model.Challenge

/**
 * ChallengeViewModel will be passed click event to update the display and navigate to the next activity
 */
class ChallengeListViewModel(application:Application): AndroidViewModel(application) {

    companion object {
        private const val CHALLENGE_EXTRA = "challengelistviewmodel_challenge_extra"
    }

    private var staticChallenges = buildStaticChallenges()

    fun <T: Activity> displayDetailScreen(challenge: Challenge,
                                                  context: T) {
        val intent = Intent(context, ChallengeDetailActivity::class.java)
        intent.putExtra(CHALLENGE_EXTRA, challenge)
        context.startActivity(intent)
    }

    fun getChallenges() : ArrayList<Challenge> = staticChallenges

    private fun buildStaticChallenges() : ArrayList<Challenge> {
        return arrayListOf(
                Challenge("Approach Now!","We all know THAT feeling.  Get over approach anxiety with a little on the line.",
                        "Needs Instructions"),
                Challenge("Sidewalk Soldier", "Who knew it was so easy to ignore what strangers are going to think about you",
                        "Needs Instructions")
        )
    }
}