package androidtitancom.cuteapp.challenges

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import androidtitancom.cuteapp.R
import androidtitancom.cuteapp.model.Challenge
import kotlinx.android.synthetic.main.activity_comfort_zone_challenge.*

class ComfortZoneActivity : AppCompatActivity(), ChallengeFragment.OnListFragmentInteractionListener {

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comfort_zone_challenge)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override
    fun onListFragmentInteraction(item: Challenge) {
        //
    }
}
