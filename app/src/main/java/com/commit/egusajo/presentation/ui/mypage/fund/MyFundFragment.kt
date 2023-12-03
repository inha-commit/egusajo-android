package com.commit.egusajo.presentation.ui.mypage.fund


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentMyFundBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.home.adapter.HomeFundAdapter
import com.commit.egusajo.presentation.ui.toFundDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFundFragment : BaseFragment<FragmentMyFundBinding>(R.layout.fragment_my_fund) {

    private val viewModel: MyFundViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initEventObserver()
        setScrollEventListener()
        binding.rvFund.adapter = HomeFundAdapter()
        viewModel.getMyFundList()
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MyFundEvents.NavigateToFundDetail -> findNavController().toFundDetail(it.fundId)
                    is MyFundEvents.ShowSnackMessage -> showCustomSnack(binding.rvFund, it.msg)
                    is MyFundEvents.ShowToastMessage -> showCustomToast(it.msg)
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
                    viewModel.getMyFundList()
                }
            }
        })
    }

}