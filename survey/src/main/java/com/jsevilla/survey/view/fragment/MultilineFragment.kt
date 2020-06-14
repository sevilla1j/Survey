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
import com.jsevilla.survey.R
import com.jsevilla.survey.SurveyActivity
import com.jsevilla.survey.view.Answers
import kotlinx.android.synthetic.main.fragment_footer.*
import kotlinx.android.synthetic.main.fragment_multi_line.*

class MultilineFragment : Fragment() {

    private var dataQuestion: Question? = null

    private lateinit var mContext: FragmentActivity
    private lateinit var inputMethodManager: InputMethodManager

    private fun init() {
        editTextMultiLine.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        btnSiguiente.setOnClickListener {
            Answers.getInstance()?.putAnswer(
                textTitle.text.toString(),
                editTextMultiLine.text.toString().trim()
            )
            (mContext as SurveyActivity).goToNext()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_multi_line, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        mContext = requireActivity()

        arguments?.let {
            dataQuestion = it.getSerializable("data") as Question?

            fragmentError.visibility = View.GONE
            containerMultiLine.visibility = View.VISIBLE
            footerMultiLine.visibility = View.VISIBLE

        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            containerMultiLine.visibility = View.GONE
            footerMultiLine.visibility = View.GONE
        }

        dataQuestion?.let {
            if (it.required) {
                btnSiguiente.visibility = View.GONE
                editTextMultiLine.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        if (s.length > 3) {
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
            editTextMultiLine.requestFocus()
        }

        inputMethodManager =
            mContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editTextMultiLine, 0)
    }
}
