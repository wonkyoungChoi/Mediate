package com.wk.mediate.ui.Register.Select

import android.Manifest
import android.R.attr.x
import android.R.attr.y
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.wk.mediate.databinding.ActivitySelectAreaBinding
import com.wk.mediate.ui.Register.Select.Area.SelectAreaViewModel
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class SelectAreaActivity : AppCompatActivity(), MapView.CurrentLocationEventListener, MapView.MapViewEventListener  {
    private lateinit var binding: ActivitySelectAreaBinding
    private lateinit var model : SelectAreaViewModel
    private val ACCESS_FINE_LOCATION = 1000     // Request Code
    var centerPoint: MapPoint? = null
    val currentLocationMarker: MapPOIItem = MapPOIItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        model = ViewModelProvider(this).get(SelectAreaViewModel::class.java)
        binding.mvKakaoMap.setZoomLevel(4, true)
        binding.mvKakaoMap.setCurrentLocationEventListener(this)
        binding.mvKakaoMap.setMapViewEventListener(this)
        permissionCheck()

        binding.clSelectAreaAim.setOnClickListener {
            if (checkLocationService()) {

                // GPS가 켜져있을 경우
                permissionCheck()
                Log.d("CENTER", binding.mvKakaoMap.mapCenterPoint.toString())
            } else {
                // GPS가 꺼져있을 경우
                Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }


    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                if(isFirstCheck) {
                    Toast.makeText(context, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    startTracking()
                } else {
                    startTracking()
                }
            }

            override fun onPermissionDenied(deniedPermissions: List<String?>) {
                Toast.makeText(
                    context,
                    "권한이 거부되었습니다.\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setRationaleMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
            .setDeniedMessage(
                "권한을 거부하시면 위치 서비스를 사용할 수 없습니다." +
                        "\n\n설정에서 권한을 허용해주세요. \n[설정] > [권한]"
            )
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }

    // 권한 요청 후 행동
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("REQUEST", requestCode.toString())
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
                startTracking()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }
    }



    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mvKakaoMap.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

    }

    // 위치추적 중지
    private fun stopTracking() {
        binding.mvKakaoMap.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
        binding.mvKakaoMap.setShowCurrentLocationMarker(false)
    }


    override fun onCurrentLocationUpdate(
        mapView: MapView?,
        point: MapPoint?,
        accuracy: Float
    ) {
        println("point: $point")
        if (point != null) {
            // 현재 위치 업데이트
            centerPoint = point
            val a = centerPoint
            currentLocationMarker.moveWithAnimation(point, false)
            currentLocationMarker.alpha = 1f
            if (mapView != null) {
                binding.mvKakaoMap.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                        point.mapPointGeoCoord.latitude,
                        point.mapPointGeoCoord.longitude
                    ), false
                )
                val marker = MapPOIItem()
                marker.apply {
                    itemName = "지정 위치"
                    mapPoint = MapPoint.mapPointWithGeoCoord(point.mapPointGeoCoord.latitude,
                        point.mapPointGeoCoord.longitude)
                    markerType = MapPOIItem.MarkerType.BluePin
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin
                }
                val circle1 = MapCircle(
                    MapPoint.mapPointWithGeoCoord(point.mapPointGeoCoord.latitude,
                        point.mapPointGeoCoord.longitude),  // center
                    1000,  // radius
                    Color.argb(255, 0, 112, 255),  // strokeColor
                    Color.argb(30, 0, 112, 255) // fillColor
                )

                binding.mvKakaoMap.removeAllPOIItems()
                binding.mvKakaoMap.removeAllCircles()
                binding.mvKakaoMap.addPOIItem(marker)
                binding.mvKakaoMap.addCircle(circle1)

                stopTracking()
            }
        }
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
        Toast.makeText(applicationContext, "단말의 각도 값 요청", Toast.LENGTH_SHORT).show()
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
        Toast.makeText(applicationContext, "위치 요청 실패", Toast.LENGTH_SHORT).show()
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
        Toast.makeText(applicationContext, "위치 요청이 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }



    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewCenterPointMoved(mapView: MapView?, point: MapPoint?) {
        if (point != null) {
            // 현재 위치 업데이트
            centerPoint = point
            val a = centerPoint
            Log.d("CENTERPOINT", a?.mapPointGeoCoord?.latitude.toString())
            currentLocationMarker.moveWithAnimation(point, false)
            currentLocationMarker.alpha = 1f
            if (mapView != null) {
                binding.mvKakaoMap.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                        point.mapPointGeoCoord.latitude,
                        point.mapPointGeoCoord.longitude
                    ), false
                )
                val circle1 = MapCircle(
                    MapPoint.mapPointWithGeoCoord(point.mapPointGeoCoord.latitude,
                        point.mapPointGeoCoord.longitude),  // center
                    1000,  // radius
                    Color.argb(255, 0, 112, 255),  // strokeColor
                    Color.argb(30, 0, 112, 255) // fillColor
                )

                val marker = MapPOIItem()
                marker.apply {
                    itemName = "지정 위치"
                    mapPoint = MapPoint.mapPointWithGeoCoord(point.mapPointGeoCoord.latitude,
                        point.mapPointGeoCoord.longitude)
                    markerType = MapPOIItem.MarkerType.BluePin
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin
                }
                stopTracking()

                binding.mvKakaoMap.removeAllPOIItems()
                binding.mvKakaoMap.removeAllCircles()
                binding.mvKakaoMap.addPOIItem(marker)
                binding.mvKakaoMap.addCircle(circle1)

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

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    //    private fun addTab(text: String) {
//        binding.tabLayoutArea.addTab(binding.tabLayoutArea.newTab().setText(text))
//    }

//    private fun observeDoResult() {
//        model.getDoResult().observe(this, {
//            Log.d("Observe", "Obeserve")
//            for (item in it) {
//                addTab(item)
//                Log.d("Item", item)
//            }
//        })
//    }

//    private fun initTabLayout() {
//        binding.tabLayoutArea.addOnTabSelectedListener(object :
//            TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                changeSelectedTabItemFontFamily(tab!!.position, NORMAL, R.color.white)
//
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Log.d("click", tab!!.position.toString())
//                changeSelectedTabItemFontFamily(tab.position, BOLD, R.color.black)
//
//                when (tab.position) {
//
//
//                    0 -> {
//                        Log.d("Click", "Click")
//                        model.loadSiGunGu("11*000000")
//                    }
//                    1 -> {
//                        tab.text = "학사공지"
//                    }
//                    2 -> {
//                        tab.text = "장학공지"
//                    }
//                    3 -> {
//                        tab.text = "채용공지"
//                    }
//                }
//
//                pageIndex = 1
//                noticeAdapter.resetList(menu_idx)
//                isListEmpty = true
//                noticeAdapter.notifyDataSetChanged()
//                model.loadNotice(pageIndex, bbs_mst_idx, menu_idx)
//            }
//        })
//    }

//    private fun changeSelectedTabItemFontFamily(tabPosition: Int, @FontRes fontFamilyRes: Int, @ColorRes textColor: Int) {
//        val linearLayout = (this.binding.tabLayoutArea.getChildAt(0) as ViewGroup).getChildAt(
//            tabPosition
//        ) as LinearLayout
//        val tabTextView = linearLayout.getChildAt(1) as TextView
//        val typeface = Typeface.defaultFromStyle(fontFamilyRes)
//        tabTextView.setTextColor(ContextCompat.getColor(this, textColor))
//        tabTextView.typeface = typeface
//    }
//
//    private fun initSiGunGuRecyclerView() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = SelectAreaAdapter()
//        binding.recyclerView.adapter = adapter
//    }
//
//    private fun observeSiGunGuResult() {
//        model.getSiGunGuResult().observe(this, {
//            adapter.setList(it)
//            adapter.notifyDataSetChanged()
//        })
//    }
}
