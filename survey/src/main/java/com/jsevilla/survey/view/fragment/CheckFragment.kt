package com.jsevilla.survey.view.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jsevilla.survey.Models.Question
import com.jsevilla.survey.R
import com.jsevilla.survey.SurveyActivity
import com.jsevilla.survey.view.Answers
import kotlinx.android.synthetic.main.fragment_check_boxes.*
import kotlinx.android.synthetic.main.fragment_footer.*

class CheckFragment : Fragment() {

    private var dataQuestion: Question? = null

    private lateinit var mContext: FragmentActivity
    private lateinit var checkBoxArrayList: ArrayList<CheckBox>

    private fun init() {
        btnSiguiente.setOnClickListener {
            (mContext as SurveyActivity).goToNext()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_check_boxes, container, false)

    private fun dataCollect() {
        var choice = ""
        var atLeastChecked = false
        for (checkBoxes in checkBoxArrayList) {
            if (checkBoxes.isChecked) {
                atLeastChecked = true
                choice = choice + checkBoxes.text.toString() + ", "
            }
        }

        if (choice.length > 2) {
            choice = choice.substring(0, (choice.length - 2))
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
        checkBoxArrayList = ArrayList()
        mContext = requireActivity()

        arguments?.let {
            dataQuestion = it.getSerializable("data") as Question?

            fragmentError.visibility = View.GONE
            footerCheckBoxes.visibility = View.VISIBLE
            containerCheckBox.visibility = View.VISIBLE

        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            footerCheckBoxes.visibility = View.GONE
            containerCheckBox.visibility = View.GONE
        }

        dataQuestion?.let {
            it.questionTitle.let { questionTitle ->
                textTitle.text = questionTitle
            }

            if (it.required) {
                btnSiguiente.visibility = View.GONE
            }

            val dataChoice = it.choices
            if (it.randomChoices) {
                dataChoice.shuffle()
            }

            for (choice in dataChoice) {
                val mCheckBox = CheckBox(mContext)
                mCheckBox.text = choice
                mCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                mCheckBox.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lnlCheckBox.addView(mCheckBox)
                checkBoxArrayList.add(mCheckBox)
                mCheckBox.setOnCheckedChangeListener { _, _ ->
                    dataCollect()
                }
            }
        }
    }
}
