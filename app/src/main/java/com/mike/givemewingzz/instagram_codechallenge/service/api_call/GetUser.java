package com.mike.givemewingzz.instagram_codechallenge.service.api_call;

import android.content.Context;
import android.util.Log;

import com.mike.givemewingzz.instagram_codechallenge.R;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.Token;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.User;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.UserWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.UsersList;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.UsersListWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaCaptionFrom;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaCaptions;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImages;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImagesLow;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImagesStandard;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaImagesThumb;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfo;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaInfoWrapper;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.data.MediaLocation;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTag;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagCaptionFrom;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagCaptions;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImages;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImagesLow;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImagesStandard;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagImagesThumb;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagLikes;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagUsers;
import com.mike.givemewingzz.instagram_codechallenge.appmodel.media.tags.MediaTagWrapper;
import com.mike.givemewingzz.instagram_codechallenge.service.BaseClient;
import com.mike.givemewingzz.instagram_codechallenge.service.RetrofitInterface;
import com.mike.givemewingzz.instagram_codechallenge.utils.EventBusSingleton;

import java.net.HttpURLConnection;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by GiveMeWingzz on 1/11/2016.
 */
public class GetUser {

    private static final String TAG = GetUser.class.getSimpleName();

    public static void getUserSelfInfo(final Context context) {

        RetrofitInterface retrofitInterface = BaseClient.getRetrofitInterface();
        retrofitInterface.getSelfUserInfo(new Callback<UserWrapper>() {
            @Override
            public void success(UserWrapper userUserWrapper, Response response) {

                User user = userUserWrapper.getData();

                Token token = new Token();
                token.setUserId(user.getUserId());
                token.setUserName(user.getUserName());
                token.setUserFullName(user.getUserFullName());
                token.setUserProfileImage(user.getUserProfilePicture());
                token.setUserBio(user.getUserBio());
                token.setUserMediaCount(user.getUserTypeCounts().getUserMediaCount());
                token.setUserFollows(user.getUserTypeCounts().getUserFollows());
                token.setFollowedBy(user.getUserTypeCounts().getUserFollowedBy());

                token.storeUserInfo(context);

                EventBusSingleton.post(new ProfileSuccess());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "RetrofitError : " + error.getBody(), error);
                EventBusSingleton.post(new ProfileFailure());
            }
        });

    }

    public static void getUserInfoByID(final Context context, String userID) {

        RetrofitInterface retrofitInterface = BaseClient.getRetrofitInterface();
        retrofitInterface.getUserInfoByID(userID, new Callback<UserWrapper>() {
            @Override
            public void success(UserWrapper userUserWrapper, Response response) {

                User user = userUserWrapper.getData();

                EventBusSingleton.post(new ProfileSuccess());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "RetrofitError : " + error.getBody(), error);
                EventBusSingleton.post(new ProfileFailure());
            }
        });

    }

    public static void getSearchedUserByName(final Context context, String userName) {

        RetrofitInterface retrofitInterface = BaseClient.getRetrofitInterface();
        retrofitInterface.getSearchedUserByName(userName, new Callback<UsersListWrapper>() {
            @Override
            public void success(UsersListWrapper searchedUsersWrapper, Response response) {

                try {

                    RealmList<UsersList> searchedUsers = searchedUsersWrapper.getData();

                    Log.d(TAG, "Media info Count : " + searchedUsers.size());

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    realm.clear(UsersListWrapper.class);
                    realm.clear(UsersList.class);

                    realm.copyToRealm(searchedUsers);

                    realm.commitTransaction();
                    // Todo: Publish Success

                    EventBusSingleton.post(new ProfileSearchSuccess(searchedUsers));

                } catch (NullPointerException npe) {
                    Log.e(TAG, "Missing element somewhere in getPlans response", npe);
                    EventBusSingleton.post(new ProfileSearchFailure("Failure in response"));
                    // Todo: Publish error
                }

            }

            @Override
            public void failure(RetrofitError error) {

                String errText;
                RetrofitError.Kind err = error.getKind();

                switch (err) {

                    case NETWORK:
                        errText = context.getString(R.string.error_network);
                        break;
                    case CONVERSION:
                        errText = context.getString(R.string.error_conversion);
                        break;
                    case HTTP:
                        if (HttpURLConnection.HTTP_NOT_FOUND == error.getResponse().getStatus()) {
                            errText = context.getString(R.string.error_server);
                        } else {
                            errText = context.getString(R.string.error_server);
                        }
                        break;
                    default:
                        errText = context.getString(R.string.error_server);

                }

                EventBusSingleton.post(new ProfileSearchFailure(errText));
            }
        });

    }

    public static void getUserRecentMedia() {

        RetrofitInterface retrofitInterface = BaseClient.getRetrofitInterface();
        retrofitInterface.getUserRecentMedia(new Callback<MediaInfoWrapper>() {
            @Override
            public void success(MediaInfoWrapper mediaInfoWrapper, Response response) {

                try {

                    RealmList<MediaInfo> mediaInfo = mediaInfoWrapper.getData();

                    Log.d(TAG, "Media info Count : " + mediaInfo.size());

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    realm.clear(MediaInfoWrapper.class);
                    realm.clear(MediaInfo.class);
                    realm.clear(MediaCaptions.class);
                    realm.clear(MediaCaptionFrom.class);
                    realm.clear(MediaImages.class);
                    realm.clear(MediaImagesLow.class);
                    realm.clear(MediaImagesThumb.class);
                    realm.clear(MediaImagesStandard.class);
                    realm.clear(MediaLocation.class);

                    realm.copyToRealm(mediaInfo);

                    realm.commitTransaction();
                    // Todo: Publish Success

                    EventBusSingleton.post(new MediaInfoSuccess(mediaInfo));

                } catch (NullPointerException npe) {
                    Log.e(TAG, "Missing element somewhere in getPlans response", npe);
                    EventBusSingleton.post(new MediaInfoFailure("Failure in response"));
                    // Todo: Publish error
                }

            }

            @Override
            public void failure(RetrofitError error) {

                EventBusSingleton.post(new MediaTagInfoFailure("Failure in response"));

            }
        });

    }

    public static class MediaInfoSuccess {

        public RealmList<MediaInfo> mediaInfos;

        public MediaInfoSuccess(RealmList<MediaInfo> mediaInfo) {
            this.mediaInfos = mediaInfo;
        }

        public RealmList<MediaInfo> getMediaInfo() {
            return mediaInfos;
        }

        public void setMediaInfo(RealmList<MediaInfo> mediaInfo) {
            this.mediaInfos = mediaInfo;
        }
    }

    public static class MediaTagInfoSuccess {

        public RealmList<MediaTag> mediaTags;

        public MediaTagInfoSuccess(RealmList<MediaTag> mediaInfo) {
            this.mediaTags = mediaInfo;
        }

        public RealmList<MediaTag> getMediaInfo() {
            return mediaTags;
        }

        public void setMediaInfo(RealmList<MediaTag> mediaInfo) {
            this.mediaTags = mediaInfo;
        }
    }

    public static class MediaTagInfoFailure {

        public String errorMessage;

        public MediaTagInfoFailure(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class MediaTagSuccess {

        public RealmList<MediaTag> mediaInfos;

        public MediaTagSuccess(RealmList<MediaTag> mediaInfo) {
            this.mediaInfos = mediaInfo;
        }

        public RealmList<MediaTag> getMediaInfo() {
            return mediaInfos;
        }

        public void setMediaInfo(RealmList<MediaTag> mediaInfo) {
            this.mediaInfos = mediaInfo;
        }
    }

    public static void getMediaForTagName(final Context context, String tag_name) {

        RetrofitInterface retrofitInterface = BaseClient.getRetrofitInterface();
        retrofitInterface.getMediaForTagName(tag_name, new Callback<MediaTagWrapper>() {
            @Override
            public void success(MediaTagWrapper mediaTagWrapper, Response response) {

                try {

                    RealmList<MediaTag> mediaInfo = mediaTagWrapper.getData();

                    Log.d(TAG, "Media Tag info Count : " + mediaInfo.size());

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    realm.clear(MediaTagWrapper.class);
                    realm.clear(MediaTag.class);
                    realm.clear(MediaTagLikes.class);
                    realm.clear(MediaTagUsers.class);
                    realm.clear(MediaTagCaptions.class);
                    realm.clear(MediaTagCaptionFrom.class);
                    realm.clear(MediaTagImages.class);
                    realm.clear(MediaTagImagesLow.class);
                    realm.clear(MediaTagImagesThumb.class);
                    realm.clear(MediaTagImagesStandard.class);

                    realm.copyToRealm(mediaInfo);

                    realm.commitTransaction();
                    // Todo: Publish Success

                    EventBusSingleton.post(new MediaTagInfoSuccess(mediaInfo));

                } catch (NullPointerException npe) {
                    Log.e(TAG, "Missing element somewhere in Media Tag response", npe);
                    EventBusSingleton.post(new MediaTagInfoFailure(""));
                    // Todo: Publish error
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Missing element somewhere in Media Tag response", error);

                String errText;
                RetrofitError.Kind err = error.getKind();

                switch (err) {

                    case NETWORK:
                        errText = context.getString(R.string.error_network);
                        break;
                    case CONVERSION:
                        errText = context.getString(R.string.error_conversion);
                        break;
                    case HTTP:
                        if (HttpURLConnection.HTTP_NOT_FOUND == error.getResponse().getStatus()) {
                            errText = context.getString(R.string.error_server);
                        } else {
                            errText = context.getString(R.string.error_server);
                        }
                        break;
                    default:
                        errText = context.getString(R.string.error_server);

                }

                EventBusSingleton.post(new MediaTagInfoFailure(errText));
            }
        });

    }

    public static class ProfileSearchSuccess {

        public RealmList<UsersList> usersList;

        public ProfileSearchSuccess(RealmList<UsersList> usersList) {
            this.usersList = usersList;
        }

        public RealmList<UsersList> getUsersList() {
            return usersList;
        }

        public void setUsersList(RealmList<UsersList> usersList) {
            this.usersList = usersList;
        }
    }

    public static class ProfileSearchFailure {

        public String errorMessage;

        public ProfileSearchFailure(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class ProfileSuccess {

        public User user;

        public ProfileSuccess() {
        }
    }

    public static class ProfileFailure {
        public ProfileFailure() {
        }
    }

    private static class MediaInfoFailure {
        public MediaInfoFailure(String errorMessage) {
        }
    }
}
