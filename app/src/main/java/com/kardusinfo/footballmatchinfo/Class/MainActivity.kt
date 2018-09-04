package com.kardusinfo.footballmatchinfo.Class

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kardusinfo.footballmatchinfo.Interface.MainView
import com.kardusinfo.footballmatchinfo.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.onRefresh
import com.kardusinfo.footballmatchinfo.Specials.Event
import com.kardusinfo.footballmatchinfo.Specials.League
import com.kardusinfo.footballmatchinfo.Specials.Team

class MainActivity : AppCompatActivity(), MainView, AnkoLogger {

    private lateinit var recyclerAdapter: MainAdapter
    private lateinit var spinnerAdapeter: ArrayAdapter<String>
    private val events: MutableList<Event> = mutableListOf()
    private val leagueIds: MutableList<String> = mutableListOf()
    private val leagueNames: MutableList<String> = mutableListOf()
    private var leagueId = "4328" 
    private var status = 1

    private lateinit var presenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        presenter.getLastMatch(leagueId)
        presenter.getLeagueList()

        recyclerAdapter = MainAdapter(this, events) {
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        swipeRefresh.setColorSchemeResources(android.R.color.holo_red_dark, android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_bright)

        swipeRefresh.onRefresh {
            if (status == 1) {
                presenter.getLastMatch(leagueId)
            } else if (status == 2) {
                presenter.getNextMatch(leagueId)
            }
        }

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_next -> {
                    presenter.getNextMatch(leagueId)
                    status = 2
                    true
                }
                R.id.action_prev -> {
                    presenter.getLastMatch(leagueId)
                    status = 1
                    true
                }
                else -> {
                    false
                }
            }
        }

        spinnerAdapeter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, leagueNames)
        spLeague.adapter = spinnerAdapeter
        spLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueId = leagueIds[position]
                if (status == 1) {
                    presenter.getLastMatch(leagueId)
                } else if (status == 2) {
                    presenter.getNextMatch(leagueId)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showTeamList(teams: List<Team>) {
        swipeRefresh.isRefreshing = false
        info { "team: " + teams[0].teamName }
    }

    override fun showMatchList(events: List<Event>) {
        swipeRefresh.isRefreshing = false
        this.events.clear()
        this.events.addAll(events)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun showLeagueList(leagues: List<League>) {
        leagueNames.clear()
        leagueIds.clear()
        for (league in leagues) {
            leagueNames.add(league.leagueName!!)
            leagueIds.add(league.leagueId!!)
        }
        spinnerAdapeter.notifyDataSetChanged()

    }
}

