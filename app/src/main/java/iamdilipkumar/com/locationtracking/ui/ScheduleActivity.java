package iamdilipkumar.com.locationtracking.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.locationtracking.R;
import iamdilipkumar.com.locationtracking.data.LocationColumns;
import iamdilipkumar.com.locationtracking.data.LocationContentProvider;
import iamdilipkumar.com.locationtracking.services.BackgroundTrackingService;
import iamdilipkumar.com.locationtracking.ui.dialog.CustomDialog;

public class ScheduleActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private boolean shiftStatus = false;
    Intent mIntent;

    @BindView(R.id.btn_start_stop_shift)
    Button startStopShift;

    @OnClick(R.id.btn_start_stop_shift)
    void startOrStopShift(Button startStopShift) {
        if (shiftStatus) {
            shiftStatus = false;
            startStopShift.setText(getString(R.string.start_shift));
            stopService(mIntent);
            drawPoyline();
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

        if (!isGooglePlayServicesAvailable()) {
            this.finish();
        }

        if (isMyServiceRunning(BackgroundTrackingService.class)) {
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Method to fetch all the locations between the start and stop
     * using the content provider and plotting the lines by using the PolylineOptions
     */
    private void drawPoyline() {

        LatLng zoomLocation = null;

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.parseColor("#FF4081"));

        Cursor cursor = getContentResolver()
                .query(LocationContentProvider.ContentLocations.CONTENT_URI
                        , null
                        , null
                        , null
                        , null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Double lat = Double.parseDouble(
                            cursor.getString(cursor.getColumnIndex(LocationColumns.LATITUDE)));
                    Double lng = Double.parseDouble(
                            cursor.getString(cursor.getColumnIndex(LocationColumns.LATITUDE)));
                    zoomLocation = new LatLng(lat, lng);
                    Log.d("dilip", "lat" + lat + " lng:" + lng);
                    polylineOptions.add(zoomLocation);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        if (zoomLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(zoomLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            CustomDialog.buildOneButtonDialog(this, getString(R.string.no_data),
                    getString(R.string.no_data_message));
        }

        mMap.addPolyline(polylineOptions);
    }

    /**
     * Method to check if the service to get location is already running
     *
     * @param serviceClass - Class that has to be checked if its instance is running
     * @return - boolean on success rate
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if Google play services are available on the device
     * If not found then the error dialog is shown and the activity will be closed
     *
     * @return - boolean to indicate google play services availability
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }
}
