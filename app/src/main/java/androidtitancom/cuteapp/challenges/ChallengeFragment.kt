package androidtitancom.cuteapp.challenges

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidtitancom.cuteapp.R

import androidtitancom.cuteapp.model.Challenge

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ChallengeFragment.OnListFragmentInteractionListener] interface.
 */
class ChallengeFragment : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                ChallengeFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_challenge_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ChallengeAdapter(buildStaticChallenges(), listener)
            }
        }
        return view
    }

    override
    fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override
    fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun buildStaticChallenges() : ArrayList<Challenge> {
        return arrayListOf(
                Challenge("Approach Now!","We all know THAT feeling.  Get over approach anxiety with a little on the line.",
                        "Needs Instructions"),
                Challenge("Sidewalk Soldier", "Who knew it was so easy to ignore what strangers are going to think about you?",
                        "Needs Instructions")
        )
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Challenge)
    }
}
