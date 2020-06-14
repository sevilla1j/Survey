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
import com.jsevilla.survey.view.Answers
import kotlinx.android.synthetic.main.fragment_end.*

class EndFragment : Fragment() {

    private var surveyProperties: Properties? = null
    private lateinit var mContext: FragmentActivity

    private fun init() {
        btnFinish.setOnClickListener {
            Answers.getInstance()?.let { it1 ->
                (mContext as SurveyActivity).eventSurveyCompleted(it1)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_end, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        mContext = requireActivity()

        arguments?.let {
            surveyProperties = it.getSerializable("survery_properties") as Properties

            fragmentError.visibility = View.GONE
            btnFinish.visibility = View.VISIBLE
            txtFinish.visibility = View.VISIBLE

        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            btnFinish.visibility = View.GONE
            txtFinish.visibility = View.GONE
        }

        surveyProperties?.let {
            txtFinish.text = it.endMessage
        }
    }
}
