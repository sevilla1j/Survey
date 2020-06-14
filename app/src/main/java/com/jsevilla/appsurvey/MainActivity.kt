package com.jsevilla.appsurvey

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jsevilla.survey.SurveyActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnExampleOne.setOnClickListener {
            val intent = Intent(this@MainActivity, SurveyActivity::class.java)
            intent.putExtra("json_survey", loadSurveyJson("example_survey_1.json"))
            startActivityForResult(intent, SURVEY_REQUEST)
        }

        btnExampleTwo.setOnClickListener {
            val intent = Intent(this@MainActivity, SurveyActivity::class.java)
            intent.putExtra("json_survey", loadSurveyJson("example_survey_2.json"))
            startActivityForResult(intent, SURVEY_REQUEST)
        }

        btnExampleThree.setOnClickListener {
            val intent = Intent(this@MainActivity, SurveyActivity::class.java)
            intent.putExtra("json_survey", loadSurveyJson("example_survey_3.json"))
            startActivityForResult(intent, SURVEY_REQUEST)
        }

        btnExampleFour.setOnClickListener {
            val intent = Intent(this@MainActivity, SurveyActivity::class.java)
            intent.putExtra("json_survey", loadSurveyJson("example_survey_4.json"))
            startActivityForResult(intent, SURVEY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                data?.extras?.getString("answers")?.let {
                    Log.d("****", "****************** WE HAVE ANSWERS ******************")
                    Log.v("ANSWERS JSON", it)
                    Log.d("****", "*****************************************************")
                }
            }
        }
    }

    private fun loadSurveyJson(data: String): String? {
        val charset: Charset = Charsets.UTF_8
        return try {
            val dataString: InputStream = assets.open(data)
            val size: Int = dataString.available()
            val buffer = ByteArray(size)
            dataString.read(buffer)
            dataString.close()
            String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    companion object {
        const val SURVEY_REQUEST = 1000
    }
}
