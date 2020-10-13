package model

import com.google.gson.annotations.SerializedName

data class Team(@SerializedName("id") val id: String,
                @SerializedName("name") val name: String,
                @SerializedName("website") val website: String?,
                @SerializedName("founded") val founded: String?,
                @SerializedName("venue") val venue: String?,
                @SerializedName("squad") val squad: List<Player> = emptyList())