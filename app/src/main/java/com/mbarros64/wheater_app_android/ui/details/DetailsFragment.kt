package com.mbarros64.wheater_app_android.ui.details

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.mbarros64.wheater_app_android.R
import com.mbarros64.wheater_app_android.data.model.Forecast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : DialogFragment() {

    private val viewModel by viewModel<DetailsViewModel>()

    private lateinit var key: String

    private var forecast: Forecast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle -> bundle.getString(FORECAST_KEY)?.let { key = it } }
        forecast = viewModel.getForecast(key)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val forecastView = inflater.inflate(R.layout.dialog_details, null)

        val description = forecastView.findViewById<TextView>(R.id.dialog_description_text_view)
        val image = forecastView.findViewById<ImageView>(R.id.dialog_image_image_view)

        description.text = forecast?.description
        forecast?.imageUrl?.let {
            Glide.with(this)
                .asDrawable()
                .load(it)
                .placeholder(R.drawable.ic_placeholder_black_24dp)
                .into(image!!)
        }

        return AlertDialog.Builder(requireActivity()).apply {
            setTitle(forecast?.head)
            setView(forecastView)
            setNeutralButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()
    }

    companion object {

        const val TAG = "DetailsFragment"
        private const val FORECAST_KEY = "FORECAST_KEY"

        fun newInstance(key: String): DetailsFragment {
            val args = Bundle().apply {
                putString(FORECAST_KEY, key)
            }

            return DetailsFragment()
                .apply {
                arguments = args
            }
        }
    }
}
