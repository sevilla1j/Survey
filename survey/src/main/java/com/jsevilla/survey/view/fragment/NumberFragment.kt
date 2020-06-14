package com.jsevilla.survey.view.fragment

import android.app.Service
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jsevilla.survey.Models.Question
import com.jsevilla.survey.SurveyActivity
import com.jsevilla.survey.view.Answers
import kotlinx.android.synthetic.main.fragment_footer.*
import kotlinx.android.synthetic.main.fragment_text.*

class NumberFragment : Fragment() {

    private var dataQuestion: Question? = null

    private lateinit var mContext: FragmentActivity
    private lateinit var inputMethodManager: InputMethodManager

    private fun init() {
        editTextSimple.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        btnSiguiente.setOnClickListener {
            Answers.getInstance()?.putAnswer(
                textTitle.text.toString(),
                editTextSimple.text.toString().trim()
            )
            (mContext as SurveyActivity).goToNext()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(com.jsevilla.survey.R.layout.fragment_text, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        mContext = requireActivity()

        arguments?.let {
            dataQuestion = it.getSerializable("data") as Question?

            fragmentError.visibility = View.GONE
            containerText.visibility = View.VISIBLE
            footerMultiLine.visibility = View.VISIBLE

        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            containerText.visibility = View.GONE
            footerMultiLine.visibility = View.GONE
        }

        dataQuestion?.let {
            if (it.required) {
                btnSiguiente.visibility = View.GONE
                editTextSimple.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        if (s.length >= 1) {
                            btnSiguiente.visibility = View.VISIBLE
                        } else {
                            btnSiguiente.visibility = View.GONE
                        }
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
            }

            textTitle.text = it.questionTitle
            editTextSimple.requestFocus()
        }

        inputMethodManager =
            mContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editTextSimple, 0)
    }
}
