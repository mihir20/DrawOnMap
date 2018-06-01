package `in`.aeroconsultancy.drawonmap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(),
        OnMapReadyCallback{

    final val LINEDRAWING:Int? = 1
    final val POLYGONDRAWING:Int? = 2
    var OPTIONSELECTED:Int? = null

    var polyLine:Polyline? = null

    var lines = ArrayList<LatLng>()

    var polygon:Polygon? = null

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        addFab.isEnabled = false
        addFab.setOnClickListener{

            if (OPTIONSELECTED == LINEDRAWING){
            lines.add(mMap.cameraPosition.target)
                loadLines()
            }else{
                lines.add(mMap.cameraPosition.target)
                loadPoints()
            }
        }
        undoFab.setOnClickListener{
            mMap.clear()
            if(lines.size != 0){
            lines.removeAt(lines.size-1)
                loadLines()
            }
        }
        optionFab.setOnClickListener{
            if (lineFab.visibility == View.GONE){
                lineFab.visibility = View.VISIBLE
                polygonFab.visibility = View.VISIBLE
            }else{
                lineFab.visibility = View.GONE
                polygonFab.visibility = View.GONE
            }
        }
        lineFab.setOnClickListener {
            OPTIONSELECTED = LINEDRAWING
            lineFab.visibility = View.GONE
            polygonFab.visibility = View.GONE
        }
        polygonFab.setOnClickListener {
            OPTIONSELECTED = POLYGONDRAWING
            lineFab.visibility = View.GONE
            polygonFab.visibility = View.GONE
        }
    }

    private fun loadPoints() {
        polygon = mMap.addPolygon(PolygonOptions().clickable(false).addAll(lines))
    }

    private fun loadLines() {

        polyLine = mMap.addPolyline(PolylineOptions().clickable(false).addAll(lines))
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addFab.isEnabled = true

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14f))

    }

}
