package model

import com.google.gson.annotations.SerializedName

data class Season(@SerializedName("startDate") val startDate: String,
                  @SerializedName("endDate") val endDate: String,
                  @SerializedName("currentMatchday") val currentMatchday: String)