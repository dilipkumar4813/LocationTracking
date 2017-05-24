package iamdilipkumar.com.locationtracking.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import iamdilipkumar.com.locationtracking.R;
import iamdilipkumar.com.locationtracking.data.LocationColumns;
import iamdilipkumar.com.locationtracking.data.LocationContentProvider;
import iamdilipkumar.com.locationtracking.ui.dialog.CustomDialog;

/**
 * Created on 24/05/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class MapsUtils {

    /**
     * Method to check if location services is enabled
     *
     * @param activity - used to access location service and start activity
     */
    public static boolean checkGpsLocation(Activity activity) {
        LocationManager mLocationManager = (LocationManager) activity.getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {
            CustomDialog.buildGpsPermissionDialog(activity);
            return false;
        }

        return true;
    }

    /**
     * Method to fetch all the locations between the start and stop
     * using the content provider and plotting the lines by using the PolylineOptions
     *
     * @param activity - Method to access the content provider
     * @return - returns the polyline options
     */
    public static PolylineOptions drawPoyline(Activity activity, GoogleMap map) {
        map.clear();

        LatLng zoomLocation = null;
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.parseColor("#FF4081"));

        Cursor cursor = activity.getContentResolver()
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

                    if (zoomLocation == null) {
                        zoomLocation = new LatLng(lat, lng);
                    }
                    Log.d("dilip", "lat" + lat + " lng:" + lng);

                    polylineOptions.add(new LatLng(lat, lng));
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        if (zoomLocation != null) {
            map.moveCamera(CameraUpdateFactory.newLatLng(zoomLocation));
            map.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            CustomDialog.buildSingleButtonDialog(activity, activity.getString(R.string.no_data),
                    activity.getString(R.string.no_data_message));
        }

        return polylineOptions;
    }

    /**
     * Method to check if the service to get location is already running
     *
     * @param context      - Used to access the System services
     * @param serviceClass - Class that has to be checked if its instance is running
     * @return - boolean on success
     */
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
