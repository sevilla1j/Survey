package com.jsevilla.survey

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.jsevilla.survey.Adapter.AdapterFragment
import com.jsevilla.survey.Models.Properties
import com.jsevilla.survey.Models.Question
import com.jsevilla.survey.Models.Survey
import com.jsevilla.survey.view.Answers
import com.jsevilla.survey.view.fragment.*
import kotlinx.android.synthetic.main.activity_survey.*

class SurveyActivity : AppCompatActivity() {

    private var mSurvey: Survey? = null
    private var mPager: ViewPager? = null
    private var styleString: String? = null
    private var listFragment: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        intent?.extras?.let {
            val bundle = it

            fragmentError.visibility = View.GONE
            pager.visibility = View.VISIBLE

            mSurvey = Gson().fromJson(
                bundle.getString("json_survey"),
                Survey::class.java
            )

            if (bundle.containsKey("style")) {
                styleString = bundle.getString("style")
            }
        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            pager.visibility = View.GONE
        }

        mSurvey.let {
            it?.properties?.skipIntro?.let { it1 ->
                if (it1) {
                    val fragmentStart = StartFragment()
                    fragmentData(fragmentStart, it.properties, styleString)
                    listFragment.add(fragmentStart)
                }
            }

            it?.question?.let {
                listFragment.addAll(listFragmentPosition(it))
            }

            it?.properties?.endMessage?.let { properties ->
                val fragmentEnd = EndFragment()
                fragmentData(fragmentEnd, it.properties, styleString)
                listFragment.add(fragmentEnd)
            }
        } ?: kotlin.run {
            fragmentError.visibility = View.VISIBLE
            pager.visibility = View.GONE
        }

        mPager = findViewById(R.id.pager)
        val mPagerAdapter = AdapterFragment(supportFragmentManager, listFragment)
        mPager.let {
            it?.adapter = mPagerAdapter
        }
    }

    private fun listFragmentPosition(question: List<Question>): ArrayList<Fragment> {
        val listFragment: ArrayList<Fragment> = ArrayList()
        for (mQuestion in question) {
            when (mQuestion.questionType) {
                "String" -> {
                    val fragmentText = TextFragment()
                    fragmentData(fragmentText, mQuestion, styleString)
                    listFragment.add(fragmentText)
                }
                "Checkboxes" -> {
                    val fragmentCheck = CheckFragment()
                    fragmentData(fragmentCheck, mQuestion, styleString)
                    listFragment.add(fragmentCheck)
                }
                "Radioboxes" -> {
                    val fragmentRadio = RadioFragment()
                    fragmentData(fragmentRadio, mQuestion, styleString)
                    listFragment.add(fragmentRadio)
                }
                "Number" -> {
                    val fragmentNumber = NumberFragment()
                    fragmentData(fragmentNumber, mQuestion, styleString)
                    listFragment.add(fragmentNumber)
                }
                "StringMultiline" -> {
                    val fragmentMultiLine = MultilineFragment()
                    fragmentData(fragmentMultiLine, mQuestion, styleString)
                    listFragment.add(fragmentMultiLine)
                }
            }
        }

        return listFragment
    }

    private fun fragmentData(fragment: Fragment, properties: Properties, style: String?) {
        val mBundle = Bundle()
        mBundle.putSerializable("survery_properties", properties)
        mBundle.putString("style", style)
        fragment.arguments = mBundle
    }

    private fun fragmentData(fragment: Fragment, question: Question, style: String?) {
        val mBundle = Bundle()
        mBundle.putSerializable("data", question)
        mBundle.putString("style", style)
        fragment.arguments = mBundle
    }

    override fun onBackPressed() {
        if (mPager!!.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager!!.currentItem = mPager!!.currentItem - 1
        }
    }

    fun goToNext() {
        mPager?.currentItem = mPager!!.currentItem + 1
    }

    fun eventSurveyCompleted(answers: Answers) {
        val returnIntent = Intent()
        returnIntent.putExtra("answers", answers.getJson())
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
