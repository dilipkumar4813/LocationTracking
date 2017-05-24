package iamdilipkumar.com.locationtracking.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

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
            if (access) {
                shiftStatus = true;
                startStopShift.setText(getString(R.string.stop_shift));
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
        ButterKnife.bind(this);

        if (MapsUtils.isServiceRunning(this, BackgroundTrackingService.class)) {
            shiftStatus = true;
            startStopShift.setText(getString(R.string.stop_shift));
        }

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
        checkPermissionAndEnableCurrentLocation();

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

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
