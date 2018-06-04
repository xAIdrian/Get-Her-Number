package androidtitancom.cuteapp.challenges

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidtitancom.cuteapp.R

import androidtitancom.cuteapp.model.Challenge

import kotlinx.android.synthetic.main.fragment_challenge_list_item.view.*

class ChallengeListAdapter(
        private val mValues: List<Challenge>,
        private val mListener: onAdapterClickListener?) : RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Challenge
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onChallengeSelected(item)
        }
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_challenge_list_item, parent, false)
        return ViewHolder(view)
    }

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        when(item.title) {
            "Approach Now!" -> {
                holder.mIconImageView.setImageResource(R.drawable.ic_run_fast)
            }
            "Sidewalk Soldier" -> {
                holder.mIconImageView.setImageResource(R.drawable.ic_subway_variant)
            }
        }

        holder.mTitleView.text = item.title
        holder.mInstructionView.text = item.subTitle

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override
    fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIconImageView : ImageView = mView.icon_imageView
        val mTitleView: TextView = mView.item_number
        val mInstructionView: TextView = mView.content
    }
}
