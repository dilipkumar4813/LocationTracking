package iamdilipkumar.com.locationtracking.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import iamdilipkumar.com.locationtracking.data.LocationColumns;
import iamdilipkumar.com.locationtracking.data.LocationContentProvider;

/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class BackgroundTrackingService extends Service {

    private static final String TAG = BackgroundTrackingService.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000; // Change this to 10000
    private static final float LOCATION_DISTANCE = 0f; // Change this to 0 if its not working 10f
    private Context mContext;

    /**
     * Class to implement location listener which will be used for storing the users
     * Locations
     */
    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "Latitude" + location.getLatitude() + " Longitude:"
                    + location.getLongitude());

            ContentValues cv = new ContentValues();
            cv.put(LocationColumns.UID, 123);
            cv.put(LocationColumns.LATITUDE, String.valueOf(location.getLatitude()));
            cv.put(LocationColumns.LONGITUDE, String.valueOf(location.getLongitude()));
            cv.put(LocationColumns.TIME_STAMP, "timestamp");

            mContext.getContentResolver()
                    .insert(LocationContentProvider.ContentLocations.CONTENT_URI, cv);

            Toast.makeText(mContext, "Location"
                    + String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();

            mLastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        mContext = this;

        initializeLocationManager();

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.d(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.d(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.d(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    /**
     * Method to initialize the location manager
     */
    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
