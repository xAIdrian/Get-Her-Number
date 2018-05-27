package androidtitancom.cuteapp


import android.Manifest
import android.app.DialogFragment
import android.os.Bundle
import android.app.Fragment
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Spanned
import kotlinx.android.synthetic.main.dialog_fragment_onboarding.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [OnboardingDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnboardingDialogFragment : DialogFragment() {
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment OnboardingDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): OnboardingDialogFragment {
            val fragment = OnboardingDialogFragment()
            val args = Bundle()
            /*
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            */
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            //assign arguments
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.dialog_fragment_onboarding, container, false)

        view.onboardingTextView.text = fromHtml(getString(R.string.onboarding))
        view.disclaimerTextView.text = fromHtml(getString(R.string.disclaimer))

        view.nextClickTextView.setOnClickListener {
            dismiss()
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE),1)
        }

        return view
    }

    private fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }
}
