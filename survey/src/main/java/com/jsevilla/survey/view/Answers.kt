package com.jsevilla.survey.view

import com.google.gson.Gson

object Answers {

    private var instance: Answers? = null
    private val answeredHashmap: LinkedHashMap<String, String> = LinkedHashMap()

    fun putAnswer(key: String, value: String) {
        answeredHashmap[key] = value
    }

    fun getJson(): String? {
        val gson = Gson()
        return gson.toJson(answeredHashmap, LinkedHashMap::class.java)
    }

    override fun toString(): String {
        return answeredHashmap.toString()
    }

    fun getInstance(): Answers? {
        if (instance == null) {
            synchronized(Answers::class.java) {
                if (instance == null) {
                    instance = Answers
                }
            }
        }
        return instance
    }
}
