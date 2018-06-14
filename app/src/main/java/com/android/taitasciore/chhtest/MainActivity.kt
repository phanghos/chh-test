package com.android.taitasciore.chhtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.android.taitasciore.chhtest.feature.coinlist.view.CoinListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager.addOnBackStackChangedListener { handleBackStack() }

        if (savedInstanceState == null) {
            launchCoinListFragment()
        } else {
            handleBackStack()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleBackStack() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        val enableUpNavigation = backStackCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(enableUpNavigation)
        supportActionBar?.setDisplayShowHomeEnabled(enableUpNavigation)
    }

    private fun launchCoinListFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, CoinListFragment())
                .commit()
    }
}
