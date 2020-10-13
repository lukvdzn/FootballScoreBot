package model

import com.google.gson.annotations.SerializedName

data class Player(@SerializedName("id") val id: String,
                  @SerializedName("name") val name: String,
                  @SerializedName("dateOfBirth") val dateOfBirth: String?,
                  @SerializedName("countryOfBirth") val countryOfBirth: String?,
                  @SerializedName("nationality") val nationality: String?,
                  @SerializedName("position") val position: String?)