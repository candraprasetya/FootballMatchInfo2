package com.kardusinfo.footballmatchinfo.Specials

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class League(
        @SerializedName("idLeague")
        val leagueId: String? = null,

        @SerializedName("strLeague")
        val leagueName: String? = null,

        @SerializedName("strLeagueAlternate")
        val alternateName: String? = null,

        @SerializedName("strSport")
        val type: String? = null
) : Serializable

data class LeagueResponse(val leagues: List<League>)