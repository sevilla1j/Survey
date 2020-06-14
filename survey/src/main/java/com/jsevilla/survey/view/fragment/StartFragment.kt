package com.jsevilla.survey.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jsevilla.survey.Models.Properties
import com.jsevilla.survey.R
import com.jsevilla.survey.SurveyActivity
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private var surveyProperties: Properties? = null
    private lateinit var mContext: FragmentActivity

    private fun init() {
        btnStart.setOnClickListener {
            (mContext as SurveyActivity).goToNext()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_start, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        mContext = requireActivity()

        arguments?.let {
            surveyProperties = it.getSerializable("survery_properties") as Properties?

            fragmentError.visibility = View.GONE
            btnStart.visibility = View.VISIBLE
            txtStart.visibility = View.VISIBLE

        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            btnStart.visibility = View.GONE
            txtStart.visibility = View.GONE
        }

        surveyProperties?.let {
            txtStart.text = it.introMessage
        }
    }
}
