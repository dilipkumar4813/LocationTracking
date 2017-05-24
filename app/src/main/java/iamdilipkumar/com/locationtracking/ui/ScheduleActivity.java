package iamdilipkumar.com.locationtracking.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.locationtracking.R;
import iamdilipkumar.com.locationtracking.services.BackgroundTrackingService;

public class ScheduleActivity extends FragmentActivity implements OnMapReadyCallback {

    private boolean shiftStatus = false;
    Intent mIntent;

    @OnClick(R.id.btn_start_stop_shift)
    void startOrStopShift(Button startStopShift) {
        if (shiftStatus) {
            shiftStatus = false;
            startStopShift.setText(getString(R.string.start_shift));
            stopService(mIntent);
        } else {
            shiftStatus = true;
            startStopShift.setText(getString(R.string.stop_shift));
            startService(mIntent);
        }

    }

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mIntent = new Intent(this, BackgroundTrackingService.class);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        drawPoyline();
    }

    private void drawPoyline() {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.parseColor("#FF4081"));
        polylineOptions.add(new LatLng(-34, 151));
        polylineOptions.add(new LatLng(-34, 152));
        mMap.addPolyline(polylineOptions);
    }
}
