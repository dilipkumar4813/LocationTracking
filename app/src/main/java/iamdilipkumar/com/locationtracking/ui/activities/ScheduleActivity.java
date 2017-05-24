package iamdilipkumar.com.locationtracking.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.locationtracking.R;
import iamdilipkumar.com.locationtracking.services.BackgroundTrackingService;
import iamdilipkumar.com.locationtracking.utils.MapsUtils;

public class ScheduleActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.btn_start_stop_shift)
    Button startStopShift;

    @OnClick(R.id.btn_start_stop_shift)
    void startOrStopShift(Button startStopShift) {
        if (shiftStatus) {
            shiftStatus = false;
            startStopShift.setText(getString(R.string.start_shift));
            stopService(mIntent);
            mMap.addPolyline(MapsUtils.drawPoyline(this, mMap));
        } else {
            if (access && MapsUtils.checkGpsLocation(this)) {
                shiftStatus = true;
                startStopShift.setText(getString(R.string.stop_shift));
                MapsUtils.deletePreviousLocations(this);
                startService(mIntent);
            } else {
                checkPermissionAndEnableCurrentLocation();
            }
        }
    }

    private boolean shiftStatus = false;
    private Intent mIntent;
    private GoogleMap mMap;
    private boolean access = true;

    private static final int PERMISSIONS_REQUEST_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mIntent = new Intent(this, BackgroundTrackingService.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        if (MapsUtils.isServiceRunning(this, BackgroundTrackingService.class)) {
            shiftStatus = true;
            startStopShift.setText(getString(R.string.stop_shift));
        }
    }

    @Override
    protected void onResume() {
        MapsUtils.checkGpsLocation(this);
        super.onResume();
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
        checkPermissionAndEnableCurrentLocation();

        // Add a marker in Sydney and move the camera
        LatLng bangalore = new LatLng(12.9538477, 77.3507394);
        mMap.addMarker(new MarkerOptions().position(bangalore).title("Bangalore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bangalore));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    /**
     * Check if permissions are enabled
     */
    private void checkPermissionAndEnableCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            access = false;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    access = true;
                    checkPermissionAndEnableCurrentLocation();
                } else {
                    access = false;
                }
            }
        }
    }
}
