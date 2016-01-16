package com.mike.givemewingzz.instagram_codechallenge.service;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;

/**
 * Created by GiveMeWingzz on 1/7/2016.
 */
public class LocationUtils {

    public static class GeolocationFromString extends AsyncTask<String, Void, Address> {
        Context context;

        public GeolocationFromString(Context context) {
            this.context = context;
        }

        @Override
        protected Address doInBackground(String[] params) {
            Geocoder mGeocoder = new Geocoder(context);
            try {
                // Get initial address from city name, this will not have a postal code.
                Address address = mGeocoder.getFromLocationName(params[0], 1).get(0);
                return address;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Address address) {
            EventBusSingleton.post(new Geolocation(address));
        }

        // Result event class.
        public static class Geolocation {
            private double latitude;
            private double longitude;
            private Address address;

            private Geolocation(Address address) {
                this.address = address;
                this.latitude = this.address.getLatitude();
                this.longitude = this.address.getLongitude();
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }
    }

}
