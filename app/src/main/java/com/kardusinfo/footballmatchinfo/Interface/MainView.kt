package com.kardusinfo.footballmatchinfo.Interface

import com.kardusinfo.footballmatchinfo.Specials.Event
import com.kardusinfo.footballmatchinfo.Specials.League
import com.kardusinfo.footballmatchinfo.Specials.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(leagues: List<League>)
    fun showTeamList(teams: List<Team>)
    fun showMatchList(events: List<Event>)
}