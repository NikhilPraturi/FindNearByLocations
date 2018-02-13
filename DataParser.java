package com.example.akhil.smartcityapp;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akhil on 31-12-2017.
 */

public class DataParser {
    String image;

    public HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePalcesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        String rating = "";
        String icon = "";
        try {
            if (!googlePlaceJson.isNull("name")) {
                if (googlePlaceJson.has("rating")) {
                    if (googlePlaceJson.getString("rating").length() != 0) {
                        try {
                            placeName = googlePlaceJson.getString("name");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!googlePlaceJson.isNull("vicinity")) {
                            try {
                                vicinity = googlePlaceJson.getString("vicinity");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (!googlePlaceJson.isNull("rating")) {
                            try {
                                rating = googlePlaceJson.getString("rating");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (!googlePlaceJson.isNull("icon")) {
                            try {
                                icon = googlePlaceJson.getString("icon");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
                        reference = googlePlaceJson.getString("reference");
                        googlePalcesMap.put("placeName", placeName);
                        googlePalcesMap.put("vicinity", vicinity);
                        googlePalcesMap.put("lat", latitude);
                        googlePalcesMap.put("lng", longitude);
                        googlePalcesMap.put("reference", reference);
                        googlePalcesMap.put("rating", rating);
                        googlePalcesMap.put("icon", icon);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return googlePalcesMap;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int count = jsonArray.length();
        List<HashMap<String, String>> placeList = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        for (int i = 0; i < count; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                if (placeMap.size() != 0) {
                    placeList.add(placeMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return placeList;
    }

    public List<HashMap<String, String>> parse(String jsonData) {


        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        try {

            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }


    public String[] parseDirections(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getPaths(jsonArray);
    }


    public String[] getPaths(JSONArray googleStepsJson) {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];
        for (int i = 0; i < count; i++) {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return polylines;
    }
public String getPath(JSONObject googlePathJson)
{
    String polyline="";
    try {
        polyline=googlePathJson.getJSONObject("polyline").getString("points");
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return polyline;

}


}
