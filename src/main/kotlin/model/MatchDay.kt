package model

import com.google.gson.annotations.SerializedName

data class MatchDay(@SerializedName("competition") val competition: Competition,
                    @SerializedName("matches") val matches: List<Match>)