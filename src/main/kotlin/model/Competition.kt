package model

import com.google.gson.annotations.SerializedName

data class Competition(@SerializedName("id") val id: String,
                       @SerializedName("name") val name: String,
                       @SerializedName("currentSeason") val currentSeason: Season? = null) {
    fun getMatchday(): String = currentSeason?.currentMatchday ?: "0"
}