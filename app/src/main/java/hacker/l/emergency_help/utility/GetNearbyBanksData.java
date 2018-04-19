package hacker.l.emergency_help.utility;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyBanksData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    double lat, lng;
    String distance;
    List<HashMap<String, String>> nearbyPlacesList = null;

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyBanksData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            UrlConnection urlConnection = new UrlConnection();
            googlePlacesData = urlConnection.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");

        DataParser dataParser = new DataParser();
        if (result != null) {
            nearbyPlacesList = dataParser.parse(result);
            ShowNearbyPlaces(nearbyPlacesList);
            Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        }
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        DecimalFormat decimalFormat = new DecimalFormat("#0");
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            lat = Double.parseDouble(googlePlace.get("lat"));
            lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            calcsDistance();
            markerOptions.position(latLng);
            markerOptions.title(distance + ":" + placeName + " : " + vicinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }

    }

    public void calcsDistance() {
        int po = url.indexOf("=");
        int endPo = url.indexOf("&");
        String sub = url.substring(po + 1, endPo);
        String[] address = sub.split(",");
        double lat1 = Double.parseDouble(address[0]);
        double lon1 = Double.parseDouble(address[1]);

        Location cueerntLoc = new Location("");
        cueerntLoc.setLatitude(lat);
        cueerntLoc.setLongitude(lng);
        Location loc2 = new Location("");
        loc2.setLatitude(lat1);
        loc2.setLongitude(lon1);
        float distanceInMeters = cueerntLoc.distanceTo(loc2);
        distance=Utility.formatDist(distanceInMeters);
//        distance = distance(lat1, lon1, lat, lng);
//        Location locationA = new Location("point A");
//
//        locationA.setLatitude(lat1);
//        locationA.setLongitude(lat1);
//
//        Location locationB = new Location("point B");
//
//        locationB.setLatitude(lat);
//        locationB.setLongitude(lng);
//
//        distance = locationA.distanceTo(locationB);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // haversine great circle distance approximation, returns meters
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
