package com.mike.givemewingzz.instagram_codechallenge.service.api_call;

/**
 * Created by GiveMeWingzz on 1/7/2016.
 */
public class GetSearchResult {

//    public static final String TAG = GetSearchResult.class.getSimpleName();
//
//    // Keeping this value constant since not required now.
//    private static final String ANYTIME_KEY = "Anytime";
//
//    public static void getUserSelfInfo(final Context context, String what, String where, final LocationDetailsBundle locationDetailsBundle) {
//
//        RetrofitInterface retrofitInterface = BaseClient.getRetrofitInterface();
//        retrofitInterface.searchQueries(what, ANYTIME_KEY, where, new Callback<List<BusinessDetail>>() {
//            @Override
//            public void success(List<BusinessDetail> detailList, Response response) {
//
//                try {
//                    Realm realm = Realm.getDefaultInstance();
//                    realm.beginTransaction();
//                    realm.clear(BusinessDetail.class);
//
//                    if (detailList != null) {
//                        realm.copyToRealm(detailList);
//                        realm.commitTransaction();
//
//                        for (BusinessDetail businessDetails : detailList) {
//                            OttoHelper.post(new SuccessEvent(businessDetails, response, locationDetailsBundle));
//                        }
//                    }
//
//                } catch (NullPointerException npe) {
//                    Log.e(TAG, "Missing element while fetching business details.", npe);
//                    OttoHelper.post(new ErrorEvent(context.getString(R.string.business_error)));
//                }
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                Log.e(TAG, "Search Failure with result\n" + error);
//                String errText;
//                RetrofitError.Kind err = error.getKind();
//
//                switch (err) {
//                    case NETWORK:
//
//                        errText = context.getString(R.string.business_error);
//                        break;
//
//                    case CONVERSION:
//
//                        errText = context.getString(R.string.business_error);
//                        break;
//
//                    case HTTP:
//
//                        if (HttpURLConnection.HTTP_NOT_FOUND == error.getResponse().getStatus()) {
//                            errText = context.getString(R.string.business_error);
//                        } else {
//                            errText = context.getString(R.string.business_error);
//                        }
//                        break;
//
//                    default:
//                        errText = context.getString(R.string.business_error);
//                }
//
//                // Let the user know via Snackbar at the moment.
//                OttoHelper.post(new ErrorEvent(errText));
//
//            }
//        });
//
//    }
//
//    // Success class for passing success along the event bus to ourselves.
//    public static class SuccessEvent {
//
//        private BusinessDetail businessDetail;
//        private Response response;
//        private LocationDetailsBundle locationDetailsBundle;
//
//        private SuccessEvent(BusinessDetail businessDetail, Response response, LocationDetailsBundle locationDetailsBundle) {
//            this.businessDetail = businessDetail;
//            this.response = response;
//            this.locationDetailsBundle = locationDetailsBundle;
//        }
//
//        public BusinessDetail getBusinessDetail() {
//            return businessDetail;
//        }
//
//        public Response getResponse() {
//            return response;
//        }
//
//        public LocationDetailsBundle getLocationDetailsBundle() {
//            return locationDetailsBundle;
//        }
//
//        public void setLocationDetailsBundle(LocationDetailsBundle locationDetailsBundle) {
//            this.locationDetailsBundle = locationDetailsBundle;
//        }
//    }
//
//    // Error class for passing errors along the event bus to ourselves.
//    public static class ErrorEvent {
//        private String message;
//
//        private ErrorEvent(String message) {
//            this.message = message;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//    }

}
