package com.commit.egusajo.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.commit.egusajo.MainNavDirections
import com.commit.egusajo.R
import com.commit.egusajo.databinding.ActivityMainBinding
import com.commit.egusajo.presentation.base.BaseActivity
import com.commit.egusajo.presentation.ui.fund.FundFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBottomNavigation()
        setBottomNavigationListener()
    }

    private fun setBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_frag) as NavHostFragment
        navController = navHostFragment.navController

        with(binding) {
            bnv.itemIconTintList = null
            bnv.apply {
                setupWithNavController(navController)
                setOnItemSelectedListener { item ->
                    NavigationUI.onNavDestinationSelected(item, navController)
                    navController.popBackStack(item.itemId, inclusive = false)
                    true
                }
            }

            btnFund.setOnClickListener {
                val action = MainNavDirections.actionGlobalToFundFragment()
                navController.navigate(action)
            }

        }
    }

    private fun setBottomNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fund_fragment) {
                val menu = binding.bnv.menu
                menu.getItem(1).isChecked = true
            }

            if (destination.id == R.id.fund_fragment || destination.id == R.id.home_fragment || destination.id == R.id.mypage_fragment){
                binding.bnv.visibility = View.VISIBLE
                binding.btnFund.visibility = View.VISIBLE
            } else {
                binding.bnv.visibility = View.INVISIBLE
                binding.btnFund.visibility = View.INVISIBLE
            }
        }
    }
}