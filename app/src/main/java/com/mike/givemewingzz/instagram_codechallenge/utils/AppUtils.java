package com.mike.givemewingzz.instagram_codechallenge.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.Location;
import android.util.Log;
import android.view.View;

import com.mike.givemewingzz.instagram_codechallenge.core.InstagramDemoApplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GiveMeWingzz on 1/8/2016.
 */
public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();
    private static final int BUFFER_SIZE = 4 * 1024;

    static Typeface tfRobotRegular = null;
    static Typeface tfRobotMedium = null;
    static Typeface tfRobotBold = null;
    static Typeface tfRobotoLight = null;

    public static double getCalculatedDistance(double startLatitude, double startLongitude, double targetLatitude, double targetLongitude) {

        Location startLocation = new Location("Start Location");

        startLocation.setLatitude(startLatitude);
        startLocation.setLongitude(startLongitude);

        Location targetLocation = new Location("Target Location");

        targetLocation.setLatitude(targetLatitude);
        targetLocation.setLongitude(targetLongitude);

        float distance = startLocation.distanceTo(targetLocation);

        double tempDistanceInMiles = distance * 0.000621371192;

        return tempDistanceInMiles;

    }

    public static List<String> removedChars(
            String inputValue,
            String splittingCharacter,
            final int desiredFirstOffset,
            final int desiredLastOffset) {

        int startOffset = 0;
        final int charLength = inputValue.length();

        String newValue;
        String splittedValue;

        List<String> tempList = new ArrayList<>();

        newValue = inputValue.substring(startOffset, charLength - desiredLastOffset);

        Log.d(TAG, "New Value after removing last : " + desiredLastOffset + " :: " + newValue);
        newValue = newValue.substring(newValue.lastIndexOf("[") + 1);

        Log.d(TAG, "New Value after removing first : " + desiredFirstOffset + " :: " + newValue);
        Log.d(TAG, "New Value : " + newValue);


        String[] tempString = newValue.split(splittingCharacter);

        final int tempStringChar = tempString.length;

        Log.d(TAG, "TempStringChar : " + tempStringChar);

        for (int j = 0; j < tempStringChar; j++) {
            splittedValue = tempString[j];
            if (!splittedValue.endsWith("\"")) {
                splittedValue = splittedValue + "\"";
            } else if (!splittedValue.startsWith("\"")) {
                splittedValue = "\"" + splittedValue;
            }
            tempList.add(splittedValue);
            Log.d(TAG, "New Value after removing first Splitted: " + splittedValue);
        }

        List<String> toFlush = new ArrayList<>();
        String quotes = null;
        for (String escapes : tempList) {
            quotes = escapes.substring(0, escapes.length() - 1);
            Log.d(TAG, "Splitted Quotes : " + quotes);
            toFlush.add(quotes);
        }

        List<String> toReturn = new ArrayList<>();
        for (String esc : toFlush) {

            toReturn.add(esc.substring(1));
            for (String q : toReturn) {
                Log.d(TAG, "Splitted ESC : " + q);
            }

        }

        tempList.clear();
        toFlush.clear();
        return toReturn;
    }

    public static class HashClass {

        String tags = "love865031550#instagood449894189#tbt330975918#photooftheday329565929#cute317526550#me312143752#beautiful302217857#happy298949584#followme285656404#follow283113291#fashion249761415#picoftheday249469254#selfie247968893#girl226858112#summer222785526#friends220777452#instadaily214556879#fun210524201#smile202348140#like193577034#igers187110932#food171748631#instamood169519946#instalike167834410#repost159819651#family156268022#likeforlike154596028#amazing154123665#nofilter150591734#art146024042#style145496369#bestoftheday145383770#follow4follow142997901#life136436128#nature133890613#swag127890452#instagram122066449#sun119900570#pretty112398020#followforfollow110976057#sky106892447#fitness106679013#f4f105719027#beauty105090662#beach103203363#music102338701#lol99996524#hair99442571#l4l98046074#party93723166#cool92652052#iphoneonly92099891#dog91395638#photo90828125#girls90290068#webstagram88356899#sunset87591926#night85800321#funny85271869#baby82832848#iphonesia82272351#tweegram82144463#cat81309127#my80303367#hot79588256#foodporn77543743#followback76650250#travel75379656#christmas74917549#black73630000#pink73554083#makeup71319610#instacool70737770#blue70674383#yummy69608539#igdaily67433125#instalove67410650#wcw65433308#red64927612#awesome64106371#instagramhub63283539#instafollow62653851#sweet62235261#work61240846#healthy61006477#bored59954982#nice59509122#birthday58909872#model58505570#likes57879237#all_shots57752570#eyes57369075#gym57231873#onedirection56294191#throwback55516027#look55227852#day55168631#white54952238#instago54555016";

        LinkedHashMap<String, String> popularHashTags = new LinkedHashMap<>();

        public HashClass() {
            addTags();
        }

        public void addTags() {

            String[] split = tags.split("#");

            String key;
            String value = null;
            String tempKey = null;

            for (String keyVals : split) {

                keyVals.trim();

                if (!keyVals.matches("^[0-9]{5}$")) {
                    key = keyVals.trim();
                    value = extractNumbers(key);
                    String baseKey = key.replace(value, "");

                    tempKey = baseKey;

                }

                popularHashTags.put(tempKey, value);

            }

        }

        public String extractNumbers(String str) {
            Matcher matcher = Pattern.compile("\\d+").matcher(str);
            if (!matcher.find())
                throw new NumberFormatException("For input string [" + str + "]");

            String tempGroup = String.valueOf(Integer.parseInt(matcher.group()));

            return tempGroup;
        }

        public LinkedHashMap<String, String> getPopularHashTags() {
            return popularHashTags;
        }

        public List<String> getHashTagsList() {

            List<String> hashList = new ArrayList<>();

            for (Map.Entry<String, String> values : popularHashTags.entrySet()) {

                String value = values.getValue();
                String key = values.getKey();

                Log.d(TAG, "Hash Key : " + key + " : Hash Values : " + value);

                hashList.add(key);

            }

            return hashList;

        }

    }

    public static Typeface getTfRobotoRegular() {
        if (tfRobotRegular == null) {
            AssetManager mgr = InstagramDemoApplication.getInstance().getResources().getAssets();
            tfRobotRegular = Typeface.createFromAsset(mgr, "fonts/roboto_regular.ttf");
        }

        return tfRobotRegular;
    }

    public static Typeface getTfRobotoLight() {
        if (tfRobotoLight == null) {
            AssetManager mgr = InstagramDemoApplication.getInstance().getResources().getAssets();
            tfRobotoLight = Typeface.createFromAsset(mgr, "fonts/roboto_light.ttf");
        }

        return tfRobotoLight;
    }

    public static Typeface getTfRobotMedium() {
        if (tfRobotMedium == null) {
            AssetManager mgr = InstagramDemoApplication.getInstance().getResources().getAssets();
            tfRobotMedium = Typeface.createFromAsset(mgr, "fonts/roboto_medium.ttf");
        }

        return tfRobotMedium;
    }

    public static Typeface getTfRobotBold() {
        if (tfRobotBold == null) {
            AssetManager mgr = InstagramDemoApplication.getInstance().getResources().getAssets();
            tfRobotBold = Typeface.createFromAsset(mgr, "fonts/roboto_bold.ttf");
        }

        return tfRobotBold;
    }

    public static void toggleVisibility(View... views) {

        for (View current : views) {
            if (current.getVisibility() == View.VISIBLE) {
                current.setVisibility(View.INVISIBLE);
            } else {
                current.setVisibility(View.VISIBLE);
            }
        }

    }

}
