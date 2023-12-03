package com.commit.egusajo.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.commit.egusajo.BuildConfig
import com.commit.egusajo.MainNavDirections
import com.commit.egusajo.R
import com.commit.egusajo.databinding.ActivityMainBinding
import com.commit.egusajo.presentation.base.BaseActivity
import com.commit.egusajo.util.Constants.RC_PERMISSION
import com.commit.egusajo.util.toMultiPart
import dagger.hilt.android.AndroidEntryPoint
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import okhttp3.MultipartBody

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    private lateinit var neededPermissionList: MutableList<String>
    private val requiredPermissionList =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(  // 안드로이드 13 이상 필요한 권한들
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(  // 안드로이드 13 미만 필요한 권한들
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootpayAnalytics.init(this, BuildConfig.BOOT_PAY_KEY)

        setBottomNavigation()
        setBottomNavigationListener()
        initEventObserver()
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

            if (destination.id == R.id.fund_fragment || destination.id == R.id.home_fragment || destination.id == R.id.mypage_fragment) {
                binding.bnv.visibility = View.VISIBLE
                binding.btnFund.visibility = View.VISIBLE
            } else {
                binding.bnv.visibility = View.INVISIBLE
                binding.btnFund.visibility = View.INVISIBLE
            }
        }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is MainEvent.GoToGallery -> onCheckPermissions()
                    is MainEvent.OpenBootPay -> goBootpayRequest(
                        it.presentName,
                        it.presentId,
                        it.price
                    )
                    is MainEvent.ShowSnackMessage -> showCustomSnack(binding.guide, it.msg)
                    is MainEvent.ShowToastMessage -> showCustomToast(it.msg)
                }
            }
        }
    }

    private fun onCheckPermissions() {
        neededPermissionList = mutableListOf()

        requiredPermissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) neededPermissionList.add(permission)
        }

        if (neededPermissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                neededPermissionList.toTypedArray(),
                RC_PERMISSION
            )
        } else {
            openGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_PERMISSION) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val galleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                if(viewModel.gallerySelectType.value is GallerySelectType.MultiSelect){
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    type = "image/*"
                }
            }

        galleryLauncher.launch(galleryIntent)
    }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                if(viewModel.gallerySelectType.value is GallerySelectType.MultiSelect){
                    val clipData = result.data?.clipData
                    val fileList = mutableListOf<MultipartBody.Part>()
                    if (clipData != null) {
                        for (i in 0 until clipData.itemCount) {
                            val uri = clipData.getItemAt(i).uri
                            fileList.add(uri.toMultiPart(this))
                        }
                        viewModel.imagesToUrls(fileList)
                    }
                } else if (viewModel.gallerySelectType.value is GallerySelectType.SingleSelect){
                    val uri = result.data?.data
                    uri?.let {
                        viewModel.imageToUrl(it.toMultiPart(this))
                    }
                }
            }
        }

    private fun goBootpayRequest(
        presentName: String,
        presentId: String,
        price: Int,
    ) {
        val bootUser = BootUser().setPhone("010-1234-5678")
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))

        val stuck = 1 //재고 있음

        Bootpay.init(this)
            .setApplicationId(BuildConfig.BOOT_PAY_KEY) // 해당 프로젝트(안드로이드)의 application id 값
            .setContext(this)
            .setBootUser(bootUser)
            .setBootExtra(bootExtra)
            .setUX(UX.PG_DIALOG)
            .setPG(PG.KCP)
            .setName(presentName) // 결제할 상품명
            .setOrderId(presentId) // 결제 고유번호expire_month
            .setPrice(price) // 결제할 금액
            .addItem(presentName, 1, presentName, price,presentName,presentName,presentName) // 주문정보에 담길 상품정보, 통계를 위해 사용
            .onConfirm { message ->
                if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                Log.d("confirm", message);
            }
            .onDone { message ->
                viewModel.paymentState(
                    PaymentState.Success
                )
            }
            .onReady { message ->
                Log.d("ready", message)
            }
            .onCancel { message ->
                viewModel.paymentState(
                    PaymentState.Error(message)
                )
            }
            .onError{ message ->
                viewModel.paymentState(
                    PaymentState.Error(message)
                )
            }
            .request();
    }
}
