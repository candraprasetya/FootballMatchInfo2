package com.kardusinfo.footballmatchinfo.Specials

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("idTeam")
        val teamId: String? = null,

        @SerializedName("strTeam")
        val teamName: String? = null,

        @SerializedName("strTeamBadge")
        val teamBadge: String? = null
)

data class TeamResonse(val teams: List<Team>)