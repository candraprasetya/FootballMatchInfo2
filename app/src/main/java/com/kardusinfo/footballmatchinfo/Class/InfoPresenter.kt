package com.kardusinfo.footballmatchinfo.Class

import com.google.gson.Gson
import com.kardusinfo.footballmatchinfo.Interface.InfoView
import com.kardusinfo.footballmatchinfo.MainApi.ApiRepository
import com.kardusinfo.footballmatchinfo.MainApi.MainApi
import com.kardusinfo.footballmatchinfo.Specials.Team
import com.kardusinfo.footballmatchinfo.Specials.TeamResonse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class InfoPresenter(val view: InfoView) {

    val repository = ApiRepository()
    val gson = Gson()

    fun getTeamBadge(teamName: String?) {
        doAsync {
            val team: List<Team> = gson.fromJson(repository.doRequest(MainApi.getSpecificTeam(teamName)),
                    TeamResonse::class.java).teams

            uiThread {
                team?.let { view.showTeamEmblem(it[0]) }
            }
        }
    }

}