package com.commit.egusajo.presentation.ui.global.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.commit.egusajo.R
import com.commit.egusajo.app.App
import com.commit.egusajo.databinding.FragmentFundDetailBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.global.detail.adapter.FundListAdapter
import com.commit.egusajo.presentation.ui.global.detail.adapter.PresentImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundDetailFragment : BaseFragment<FragmentFundDetailBinding>(R.layout.fragment_fund_detail) {

    private val viewModel: FundDetailViewModel by viewModels()

    private val args: FundDetailFragmentArgs by navArgs()
    private val fundId by lazy { args.fundId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.setFundId(fundId)
        binding.rvParticipate.adapter = FundListAdapter()
        initStateObserver()
        initEventObserver()
        viewModel.getFundDetail(fundId)

    }

    private fun initStateObserver() {
        repeatOnStarted {
            viewModel.uiState.collect {
                if (it.fundDetail.presentImages.isNotEmpty()) {
                    setImgRecyclerView(it.fundDetail.presentImages)
                }
            }
        }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is FundDetailEvents.NavigateToFundPayment -> findNavController().toFundPaymentFragment(
                        it.fundId
                    )

                    is FundDetailEvents.GoToProductLink -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        requireContext().startActivity(intent)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setImgRecyclerView(data: List<String>) {
        binding.rvPresentImg.adapter = PresentImageAdapter(data)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.rvPresentImg)
        binding.ciIndicator.attachToRecyclerView(binding.rvPresentImg, pagerSnapHelper)
    }

    private fun NavController.toFundPaymentFragment(fundId: Int) {
        val action =
            FundDetailFragmentDirections.actionFundDetailFragmentToFundPaymentFragment(fundId)
        this.navigate(action)
    }

}

@BindingAdapter("participateText")
fun bindParticipateText(tv: TextView, state: Boolean) {
    if (state) {
        tv.text = "모금 참여"
    } else {
        tv.text = "이미 참여한 펀딩입니다"
    }
}