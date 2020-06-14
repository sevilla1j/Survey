package com.jsevilla.survey.view.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jsevilla.survey.Models.Question
import com.jsevilla.survey.R
import com.jsevilla.survey.SurveyActivity
import com.jsevilla.survey.view.Answers
import kotlinx.android.synthetic.main.fragment_footer.*
import kotlinx.android.synthetic.main.fragment_radio_button.*

class RadioFragment : Fragment() {

    private var dataQuestion: Question? = null

    private lateinit var mContext: FragmentActivity
    private lateinit var radioButtonArrayList: ArrayList<RadioButton>

    private fun init() {
        btnSiguiente.setOnClickListener {
            (mContext as SurveyActivity).goToNext()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_radio_button, container, false)

    private fun dataCollect() {
        var choice = ""
        var atLeastChecked = false
        for (radioButton in radioButtonArrayList) {
            if (radioButton.isChecked) {
                atLeastChecked = true
                choice = choice + radioButton.text.toString() + ", "
            }
        }

        if (choice.isNotEmpty()) {
            Answers.getInstance()?.putAnswer(textTitle.text.toString(), choice)
        }

        dataQuestion?.let {
            if (it.required) {
                if (atLeastChecked) {
                    btnSiguiente.visibility = View.VISIBLE
                } else {
                    btnSiguiente.visibility = View.GONE
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        radioButtonArrayList = ArrayList()
        mContext = requireActivity()

        arguments?.let {
            dataQuestion = it.getSerializable("data") as Question?

            fragmentError.visibility = View.GONE
            footerRadioButton.visibility = View.VISIBLE
            containerRadioButton.visibility = View.VISIBLE

        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            footerRadioButton.visibility = View.GONE
            containerRadioButton.visibility = View.GONE
        }

        dataQuestion?.let {
            it.questionTitle.let { questionTitle ->
                textTitle.text = questionTitle
            }

            val dataChoice = it.choices
            if (it.randomChoices) {
                dataChoice.shuffle()
            }

            for (choice in dataChoice) {
                val mRadioButton = RadioButton(mContext)
                mRadioButton.text = choice
                mRadioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                mRadioButton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                radioGroup.addView(mRadioButton)
                radioButtonArrayList.add(mRadioButton)
                mRadioButton.setOnCheckedChangeListener { _, _ ->
                    dataCollect()
                }
            }
        }
    }
}
