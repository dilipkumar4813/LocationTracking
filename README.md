# Location tracking

Android application will track the users location, store them in database for mapping when the start the shift. Check for permissions, a modular approach to simplify the development of application. Show the route taken based on the location stored.

## Features

* Load Google map
* Store users location using fused location api
* Show dialogs for permissions
* Show route on the screen once clicked on stop shift
* Landscape support

## How to use the source code

Download or clone the repository on the your local machine. Open the application in Android Studio and let the studio handle the magic to create gradle wrappers and other gradle scripts necessary for local configuration.

Almost there, as your final step : Just open the google_maps_api.xml and add your API key that you created using the Google API console.

## Screenshots of the application

![Location Screenshot 1](https://github.com/dilipkumar4813/LocationTracking/blob/master/snapshots/device-2017-05-24-230900.png)

![Location Screenshot 2](https://github.com/dilipkumar4813/LocationTracking/blob/master/snapshots/device-2017-05-24-230838.png)

![Location Screenshot 3](https://github.com/dilipkumar4813/LocationTracking/blob/master/snapshots/device-2017-05-24-215956.png)

![Location Screenshot 4](https://github.com/dilipkumar4813/LocationTracking/blob/master/snapshots/device-2017-05-24-220101.png)

## Libraries

* [Fused API](https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi)
* [Butterknife](http://jakewharton.github.io/butterknife/)
* [Schematic](https://github.com/SimonVT/schematic)
