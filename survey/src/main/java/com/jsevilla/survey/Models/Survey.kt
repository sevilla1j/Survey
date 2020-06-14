package com.jsevilla.survey.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Survey(
    @SerializedName(value = "survey_properties")
    @Expose
    var properties: Properties,

    @SerializedName(value = "questions")
    @Expose
    var question: List<Question>
) : Serializable
