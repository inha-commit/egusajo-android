package com.commit.egusajo.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.MainNavDirections
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentHomeBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.home.adapter.HomeFundAdapter
import com.commit.egusajo.presentation.ui.toFundDetail
import com.commit.egusajo.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.reset()
        binding.vm = viewModel
        viewModel.getFundList()
        initEventsObserver()
        setScrollEventListener()
        binding.rvFund.adapter = HomeFundAdapter()
    }

    private fun initEventsObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is HomeEvents.NavigateToFundDetail -> findNavController().toFundDetail(it.fundId)
                    else -> {}
                }
            }
        }
    }

    private fun setScrollEventListener() {

        binding.rvFund.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                if (lastVisibleItemPosition == itemTotalCount) {
                    viewModel.getFundList()
                }
            }
        })
    }
}