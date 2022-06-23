package com.wk.mediate.ui.register.selectInfo

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.wk.mediate.BuildConfig.KAKAO_API_KEY
import com.wk.mediate.R
import com.wk.mediate.data.models.SelectRegisterInfo
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.present.config.ViewBindingFragment
import com.wk.mediate.present.config.LoadingProgress
import com.wk.mediate.databinding.FragmentSelectAreaBinding
import com.wk.mediate.present.extension.setAlphaClickable
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.present.views.register.selectInfo.RegisterSelectFinishFragment
import com.wk.mediate.present.views.register.selectInfo.SelectRegisterViewModel
import com.wk.mediate.present.views.register.selectInfo.subject.SelectSubjectFragment
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.*

@AndroidEntryPoint
class SelectAreaFragment : ViewBindingFragment<FragmentSelectAreaBinding>(), MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener  {
    private val viewModel : SelectRegisterViewModel by viewModels()
    private val currentLocationMarker: MapPOIItem = MapPOIItem()
    private var circleRadius = 1000

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionCheck()
        observeSelectResigterResult()

        binding?.run {
            btNextnactive.setAlphaClickable(true)
            btBack.setAlphaClickable(true)

            clSelectAreaAim.setOnClickListener {
                if (checkLocationService()) {
                    // GPS가 켜져있을 경우
                    Toast.makeText(requireContext(), "현재 위치 추적중...", Toast.LENGTH_SHORT).show()
                    permissionCheck()
                } else {
                    // GPS가 꺼져있을 경우
                    Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
                }
            }

            seekBar.setOnSeekBarChangeListener(SeekBarChangeListener())

            btNextnactive.setOnClickListener {
                //튜터의 경우 과목 선택
                if(SelectRegisterInfo.type == "TUTOR") {
                    SelectRegisterInfo.tutorInfo.address = tvRealArea.text.toString()
                    SelectRegisterInfo.tutorInfo.latitude = mvKakaoMap.mapCenterPoint.mapPointGeoCoord.latitude
                    SelectRegisterInfo.tutorInfo.longitude = mvKakaoMap.mapCenterPoint.mapPointGeoCoord.longitude
                    val fragment = SelectSubjectFragment()
                    pushFragment(fragment)
                } else {
                    //튜티 회원가입 완료
                    SelectRegisterInfo.tuteeInfo.address = tvRealArea.text.toString()
                    SelectRegisterInfo.tuteeInfo.latitude = mvKakaoMap.mapCenterPoint.mapPointGeoCoord.latitude
                    SelectRegisterInfo.tuteeInfo.longitude = mvKakaoMap.mapCenterPoint.mapPointGeoCoord.longitude
                    viewModel.getLivedataSelectTuteeRegisterResult(SelectRegisterInfo.tuteeInfo)
                }

            }

            btBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            requireActivity().onBackPressedDispatcher
                    .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            if(isEnabled) {
                                showConfirm(requireContext().getString(R.string.alert_cancel_select_info), requireContext().getString(R.string.alert_cancel_select_info_out), requireContext().getString(R.string.confirm) ,{
                                    isEnabled = false
                                    moveToRoot()
                                    Toast.makeText(requireContext(),requireContext().getString(R.string.cancel_select_info), Toast.LENGTH_SHORT).show()
                                    SelectRegisterInfo.tutorInfo = TutorInfo("", "", null, "", 0.0, 0.0, "", "", "")
                                    SelectRegisterInfo.tuteeInfo = TuteeInfo("", "", "", 0.0, 0.0, "", "")
                                }, requireContext().getString(R.string.cancel), {})
                            }
                        }
                    })
        }
    }

    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = requireActivity().getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)

        val permissionlistener: PermissionListener = object : PermissionListener {
            //권한 허용했을 경우
            override fun onPermissionGranted() {
                if(isFirstCheck) {
                    Toast.makeText(context, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    startTracking()
                } else {
                    startTracking()
                }
            }

            //권한 거부했을 경우
            override fun onPermissionDenied(deniedPermissions: List<String?>) {
                Toast.makeText(context, "권한이 거부되었습니다.\n$deniedPermissions", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                .setDeniedMessage("권한을 거부하시면 위치 서비스를 사용할 수 없습니다." + "\n\n설정에서 권한을 허용해주세요. \n[설정] > [권한]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check()
    }


    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 위치추적 시작
    private fun startTracking() {
        binding?.run {
            mvKakaoMap.setCurrentLocationEventListener(this@SelectAreaFragment)
            mvKakaoMap.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        }
    }

    // 위치추적 중지
    private fun stopTracking() {
        binding?.run {
            mvKakaoMap.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
            //현재위치 마커 없애기
            mvKakaoMap.setShowCurrentLocationMarker(false)
            mvKakaoMap.setMapViewEventListener(this@SelectAreaFragment)
        }
    }

    //setCurrentLocationEventListener 관련 Override
    override fun onCurrentLocationUpdate(mapView: MapView?, point: MapPoint?, accuracy: Float) {
        if (point != null) {
            // 현재 위치 업데이트
            binding?.mvKakaoMap?.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                            point.mapPointGeoCoord.latitude,
                            point.mapPointGeoCoord.longitude
                    ), false
            )
            currentLocationMarker.moveWithAnimation(point, false)
            currentLocationMarker.alpha = 1f
            mapMarkerAndCircle(mapView, point)
            stopTracking()
            reverseGeoCoder()
            setCamera()
        }
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
        Toast.makeText(requireContext(), "단말의 각도 값 요청", Toast.LENGTH_SHORT).show()
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
        Toast.makeText(requireContext(), "위치 요청 실패", Toast.LENGTH_SHORT).show()
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
        Toast.makeText(requireContext(), "위치 요청이 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }


    //setMapViewEventListener 관련 override

    override fun onMapViewInitialized(p0: MapView?) {
    }

    override fun onMapViewCenterPointMoved(mapView: MapView?, point: MapPoint?) {
        if (point != null) {
            // 현재 위치 업데이트
            binding?.run {
                mvKakaoMap.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(
                                point.mapPointGeoCoord.latitude,
                                point.mapPointGeoCoord.longitude
                        ), false
                )
                currentLocationMarker.moveWithAnimation(point, false)
                currentLocationMarker.alpha = 1f
                mapMarkerAndCircle(mapView, point)
                mvKakaoMap.setShowCurrentLocationMarker(false)
                reverseGeoCoder()
            }

        }
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(mapView: MapView?, point: MapPoint?) {
    }

    override fun onMapViewMoveFinished(mapView: MapView?, point: MapPoint?) {
        if (point != null) {
            // 현재 위치 업데이트
            mapMarkerAndCircle(mapView, point)
        }
    }

    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, addressStr: String?) {
        binding?.tvRealArea?.text = addressStr
    }

    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {

    }

    private fun mapMarkerAndCircle(mapView: MapView?, point: MapPoint?) {
        if (mapView != null) {

            val marker = MapPOIItem()
            marker.apply {
                itemName = "지정 위치"
                if (point != null) {
                    mapPoint = MapPoint.mapPointWithGeoCoord(
                            point.mapPointGeoCoord.latitude,
                            point.mapPointGeoCoord.longitude
                    )
                }
                markerType = MapPOIItem.MarkerType.BluePin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }

            binding?.run {
                mvKakaoMap.removeAllPOIItems()
                mvKakaoMap.removeAllCircles()
                mvKakaoMap.addPOIItem(marker)
                mvKakaoMap.addCircle(addCircle())
                mvKakaoMap.setShowCurrentLocationMarker(false)
            }
        }
    }

    inner class SeekBarChangeListener: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            circleRadius = (progress + 1) * 1000
            binding?.run {
                when (progress) {
                    0 -> tv1km.setTextColor(
                            ContextCompat.getColor(
                                    requireContext(),
                                    R.color.main_blue
                            )
                    )
                    2 -> tv3km.visibility = View.VISIBLE
                    4 -> tv5km.setTextColor(
                            ContextCompat.getColor(
                                    requireContext(),
                                    R.color.main_blue
                            )
                    )
                    else -> {
                        tv1km.setTextColor(
                                ContextCompat.getColor(
                                        requireContext(),
                                        R.color.dark_grey
                                )
                        )
                        tv3km.visibility = View.GONE
                        tv5km.setTextColor(
                                ContextCompat.getColor(
                                        requireContext(),
                                        R.color.dark_grey
                                )
                        )
                    }
                }
            }

            setCamera()

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }

    }

    private fun reverseGeoCoder() {
        binding?.run {
            val reverseGeoCoder = MapReverseGeoCoder(KAKAO_API_KEY, mvKakaoMap.mapCenterPoint, this@SelectAreaFragment, requireActivity())
            reverseGeoCoder.startFindingAddress()
        }
    }

    private fun addCircle(): MapCircle {
        return MapCircle(
                MapPoint.mapPointWithGeoCoord(
                        binding?.mvKakaoMap?.mapCenterPoint?.mapPointGeoCoord!!.latitude,
                        binding?.mvKakaoMap?.mapCenterPoint?.mapPointGeoCoord!!.longitude
                ),  // center
                circleRadius,  // radius
                Color.argb(255, 0, 112, 255),  // strokeColor
                Color.argb(30, 0, 112, 255) // fillColor
        )
    }

    private fun setCamera() {
        // 지도뷰의 중심좌표와 줌레벨을 Circle이 모두 나오도록 조정.
        val mapPointBound = addCircle().bound
        val padding = 100 // px
        binding?.run {
            mvKakaoMap.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBound, padding))
            mvKakaoMap.removeAllCircles()
            mvKakaoMap.addCircle(addCircle())
        }
    }

    //회원가입 관련 LiveData 관찰
    private fun observeSelectResigterResult() {
        viewModel.registerResult.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    LoadingProgress.shared.dismiss()
                    binding?.run {
                        when (dataHandler.data?.body) {
                            "회원가입이 완료되었습니다." -> {
                                Toast.makeText(context, dataHandler.data.body, Toast.LENGTH_SHORT)
                                    .show()
                                val fragment = RegisterSelectFinishFragment()
                                replaceFragment(fragment)
                            }
                            else -> {
                                Toast.makeText(context, dataHandler.data?.body, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                    }
                }
                is DataHandler.ERROR -> {
                    LoadingProgress.shared.dismiss()
                }
                is DataHandler.LOADING -> {
                    LoadingProgress.shared.show(requireActivity())
                }
                is DataHandler.FAIL -> {
                    //TODO FAIL 처리
                }
            }
        }
    }
}
