package com.wk.mediate.ui.Register.Select

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.wk.mediate.R
import com.wk.mediate.databinding.ActivitySelectAreaBinding
import com.wk.mediate.ui.Register.Select.Area.SelectAreaViewModel
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class SelectAreaActivity : AppCompatActivity(), MapView.CurrentLocationEventListener, MapView.MapViewEventListener  {
    private lateinit var binding: ActivitySelectAreaBinding
    private lateinit var model : SelectAreaViewModel
    private val currentLocationMarker: MapPOIItem = MapPOIItem()
    private var circleRadius = 1000
    private lateinit var centerPoint: MapPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SelectAreaViewModel::class.java)
        permissionCheck()

        binding.clSelectAreaAim.setOnClickListener {
            if (checkLocationService()) {
                // GPS가 켜져있을 경우
                Toast.makeText(this, "현재 위치 추적중...", Toast.LENGTH_SHORT).show()
                permissionCheck()
                Log.d("CENTER", binding.mvKakaoMap.mapCenterPoint.toString())
            } else {
                // GPS가 꺼져있을 경우
                Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(SeekBarChangeListener())

    }

    inner class SeekBarChangeListener: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            Log.d("RADIUS", progress.toString())
            circleRadius = (progress + 1) * 1000
            when (progress) {
                0 -> binding.tv1km.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
                2 -> binding.tv3km.visibility = View.VISIBLE
                4 -> binding.tv5km.setTextColor(ContextCompat.getColor(applicationContext, R.color.main_blue))
                else -> {
                    binding.tv1km.setTextColor(ContextCompat.getColor(applicationContext, R.color.dark_grey))
                    binding.tv3km.visibility = View.GONE
                    binding.tv5km.setTextColor(ContextCompat.getColor(applicationContext, R.color.dark_grey))
                }
            }

            val circle1 = MapCircle(
                    MapPoint.mapPointWithGeoCoord(centerPoint.mapPointGeoCoord.latitude,
                            centerPoint.mapPointGeoCoord.longitude),  // center
                    circleRadius,  // radius
                    Color.argb(255, 0, 112, 255),  // strokeColor
                    Color.argb(30, 0, 112, 255) // fillColor
            )

            binding.mvKakaoMap.removeAllCircles()
            binding.mvKakaoMap.addCircle(circle1)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

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
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mvKakaoMap.setZoomLevel(4, true)
        binding.mvKakaoMap.setCurrentLocationEventListener(this@SelectAreaActivity)
        binding.mvKakaoMap.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    // 위치추적 중지
    private fun stopTracking() {
        binding.mvKakaoMap.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
        binding.mvKakaoMap.setShowCurrentLocationMarker(false)
        binding.mvKakaoMap.setMapViewEventListener(this@SelectAreaActivity)
        Log.d("stopTracking", "stopTracking")
    }

    override fun onCurrentLocationUpdate(
            mapView: MapView?,
            point: MapPoint?,
            accuracy: Float
    ) {
        println("point: $point")
        Log.d("currentUpdate", "currentUpdate")
        if (point != null) {
            // 현재 위치 업데이트
            centerPoint = point
            binding.mvKakaoMap.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                            point.mapPointGeoCoord.latitude,
                            point.mapPointGeoCoord.longitude
                    ), false
            )
            currentLocationMarker.moveWithAnimation(point, false)
            currentLocationMarker.alpha = 1f
            mapMarkerAndCircle(mapView, point)
            stopTracking()
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
            binding.mvKakaoMap.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                            point.mapPointGeoCoord.latitude,
                            point.mapPointGeoCoord.longitude
                    ), false
            )
            currentLocationMarker.moveWithAnimation(point, false)
            currentLocationMarker.alpha = 1f
            mapMarkerAndCircle(mapView, point)
            binding.mvKakaoMap.setShowCurrentLocationMarker(false)
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

    private fun mapMarkerAndCircle(mapView: MapView?, point: MapPoint?) {
        if (mapView != null) {
            val circle1 = MapCircle(
                    MapPoint.mapPointWithGeoCoord(point!!.mapPointGeoCoord.latitude,
                            point.mapPointGeoCoord.longitude),  // center
                    circleRadius,  // radius
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

            binding.mvKakaoMap.removeAllPOIItems()
            binding.mvKakaoMap.removeAllCircles()
            binding.mvKakaoMap.addPOIItem(marker)
            binding.mvKakaoMap.addCircle(circle1)
            binding.mvKakaoMap.setShowCurrentLocationMarker(false)
        }
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
