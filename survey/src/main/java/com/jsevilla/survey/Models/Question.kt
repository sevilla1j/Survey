package com.jsevilla.survey.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Question(
    @SerializedName(value = "question_type")
    @Expose
    var questionType: String,

    @SerializedName(value = "question_title")
    @Expose
    var questionTitle: String,

    @SerializedName(value = "description")
    @Expose
    var description: String,

    @SerializedName(value = "required")
    @Expose
    var required: Boolean,

    @SerializedName(value = "random_choices")
    @Expose
    var randomChoices: Boolean,

    @SerializedName(value = "min")
    @Expose
    var min: Int,

    @SerializedName(value = "max")
    @Expose
    var max: Int,

    @SerializedName(value = "number_of_lines")
    @Expose
    var numberOfLines: Int,

    @SerializedName(value = "choices")
    @Expose
    var choices: ArrayList<String>
) : Serializable
