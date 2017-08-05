package com.project.raizasocial.Services;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.raizasocial.Constants;
import com.project.raizasocial.JSON.models.EventAttendanceJSONModel;
import com.project.raizasocial.JSON.models.EventJSONModel;
import com.project.raizasocial.JSON.models.FriendJSONModel;
import com.project.raizasocial.JSON.models.GeofenceJSONModel;
import com.project.raizasocial.JSON.models.LocationJSONModel;
import com.project.raizasocial.JSON.models.UserJSONModel;
import com.project.raizasocial.sqlite.models.Event;
import com.project.raizasocial.sqlite.models.EventAttendance;
import com.project.raizasocial.sqlite.models.Friend;
import com.project.raizasocial.sqlite.models.GeofenceModel;
import com.project.raizasocial.sqlite.models.Location;

import java.text.ParseException;
import java.util.List;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;


public class RestService {
    private final CommunityAppWebService mCommunityAppWebService;
    private final CommunityAppWebService mCommunityAppWebService2;

    /**
     * A service to REST-ful client operations
     */
    public RestService() {
        Gson gson = new GsonBuilder()
                .setDateFormat(Constants.DATE_FORMAT)
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.SERVER2)
                .setErrorHandler(new myErrorHandler())
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mCommunityAppWebService = restAdapter.create(CommunityAppWebService.class);

        Gson gson2 = new GsonBuilder()
                .setDateFormat(Constants.DATE_TIME_FORMAT)
                .create();

        RestAdapter restAdapter2 = new RestAdapter.Builder()
                .setEndpoint(Constants.SERVER2)
                .setErrorHandler(new myErrorHandler())
                .setConverter(new GsonConverter(gson2))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mCommunityAppWebService2 = restAdapter2.create(CommunityAppWebService.class);
    }

    /**
     * A class for Session Request Interceptor
     */
    public class SessionRequestInterceptor implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Accept", "application/json");
        }
    }

    /**
     * A function to return the service running
     * @return CommunityAppWebService
     */
    public CommunityAppWebService getService() {
        return mCommunityAppWebService;
    }

    /**
     * A function to return the service running with different date time format
     * @return
     */
    public CommunityAppWebService getService2() { return mCommunityAppWebService2; }

    /**
     * Community App Web interface
     */
    public interface CommunityAppWebService {
        /**
         * User registration
         * @param userJSONModel
         * @param callback
         */
        @POST(Constants.URL_REGISTER)
        public void registerUser(
                @Body UserJSONModel userJSONModel,
                Callback<UserJSONModel> callback);

        /**
         * Friend list fetcher
         */
        @GET(Constants.URL_FRIEND_LIST)
        public void fetchFriendList(
                @Path("email") String email,
                Callback<List<FriendJSONModel>> callback);

        /**
         *  Geofence list fetcher
         */
        @GET(Constants.URL_GEOFENCE_LIST)
        public void fetchGeofenceList(Callback<List <GeofenceJSONModel>> callback);

        /**
         * Event Creator
         */
        @POST(Constants.URL_CREATE_EVENT)
        public void createEvent(
                @Body EventJSONModel eventJSONModel,
                Callback<EventJSONModel> callback);

        /**
         * Event list fetcher
         */
        @GET(Constants.URL_EVENT_OPEN_LIST)
        public void fetchOpenEventList(Callback<List <EventJSONModel>> callback);

        /**
         * Event attendance registration
         * @param eventAttendanceJSONModel
         * @param callback
         */
        @POST(Constants.URL_EVENT_ATTENDANCE)
        public void attendEvent(
                @Body EventAttendanceJSONModel eventAttendanceJSONModel,
                Callback<EventAttendanceJSONModel> callback);

        /**
         * Get the Event attendance list
         * @param callback
         */
        @GET(Constants.URL_EVENT_ATTENDANCE_LIST)
        public void fetchEventAttendanceList(
                @Path("event_id") int event_id,
                Callback<List <EventAttendanceJSONModel>> callback);


        /**
         * Post the location update to the server
         */
        @POST(Constants.URL_LOCATION_ACTIVITY)
        public void updateLocationActivity(
                @Body LocationJSONModel locationJSONModel,
                Callback<LocationJSONModel> callback);

        /**
         * Get Online users in a geofence
         */
        @GET(Constants.URL_LOCATION_ACTIVE_USER_LIST)
        public List<LocationJSONModel> activeLocationUsers(
                @Path("gid") int gid,
                Callback<List <LocationJSONModel>> callback);
    }

    /**
     * Function to get the list of friends
     * @param email
     */
    public void fetchFriendList(String email) {
        mCommunityAppWebService.fetchFriendList(email,
                new Callback<List<FriendJSONModel>>() {
                    /**
                     * {@inheritDoc}
                     * @param friendJSONModels
                     * @param response
                     */
                    @Override
                    public void success(List<FriendJSONModel> friendJSONModels, Response response) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for (FriendJSONModel friendJSONModel : friendJSONModels) {
                                if (friendJSONModel != null) {
                                    Friend.findOrCreateFromModel(friendJSONModel);
                                }
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                    /**
                     * {@inheritDoc}
                     * @param error
                     */
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    /**
     * Function to fetch the list of geofence
     */
    public void fetchGeofenceList() {
        mCommunityAppWebService.fetchGeofenceList(
                new Callback<List<GeofenceJSONModel>>() {
                    /**
                     * {@inheritDoc}
                     * @param geofenceJSONModels
                     * @param response
                     */
                    @Override
                    public void success(List<GeofenceJSONModel> geofenceJSONModels, Response response) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for (GeofenceJSONModel geofenceJSONModel : geofenceJSONModels) {
                                if (geofenceJSONModel != null) {
                                    GeofenceModel.findOrCreateFromModel(geofenceJSONModel);
                                }
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                    /**
                     * {@inheritDoc}
                     * @param error
                     */
                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
    }

    /**
     * Function to fetch the event list
     */
    public void fetchOpenEventList() {
        mCommunityAppWebService2.fetchOpenEventList(
                new Callback<List<EventJSONModel>>() {
                    /**
                     * {@inheritDoc}
                     * @param eventJSONModels
                     * @param response
                     */
                    @Override
                    public void success(List<EventJSONModel> eventJSONModels, Response response) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for(EventJSONModel eventJSONModel : eventJSONModels) {
                                if (eventJSONModel != null) {
                                    Event event = Event.findOrCreateFromModel(eventJSONModel);
                                    fetchAttendanceList(event.getEvent_id());
                                    event.save();
                                }
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                    /**
                     * {@inheritDoc}
                     * @param error
                     */
                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
    }

    /**
     *
     */
    public void fetchAttendanceList(int event_id) {
        mCommunityAppWebService2.fetchEventAttendanceList(event_id,
                new Callback<List<EventAttendanceJSONModel>>() {
                    /**
                     * {@inheritDoc}
                     * @param eventAttendanceJSONModels
                     * @param response
                     */
                    @Override
                    public void success(List<EventAttendanceJSONModel> eventAttendanceJSONModels,
                                        Response response) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for (EventAttendanceJSONModel attendanceJSONModel: eventAttendanceJSONModels) {
                                if (attendanceJSONModel != null) {
                                    EventAttendance eventAttendance = EventAttendance
                                            .findOrCreateFromModel(attendanceJSONModel);
                                    eventAttendance.save();
                                }
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
                    /**
                     * {@inheritDoc}
                     * @param error
                     */
                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
    }

    /**
     * A function to send user location updates to the server
     */
    public void updateUserLocationActivity(LocationJSONModel locationJSONModel) {
        mCommunityAppWebService2.updateLocationActivity(locationJSONModel,
                new Callback<LocationJSONModel>() {
                    /**
                     * {@inheritDoc}
                     * @param locationJSONModel
                     * @param response
                     */
                    @Override
                    public void success(LocationJSONModel locationJSONModel, Response response) {
                        Location location = Location.getLocationObject(
                                locationJSONModel.getDate_time());
                        if (location != null) location.delete();
                    }

                    /**
                     * {@inheritDoc}
                     * @param error
                     */
                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
    }

    /**
     * Custom error handler
     */
    public class myErrorHandler implements ErrorHandler {
        /**
         * {@inheritDoc}
         * @param cause
         * @return
         */
        @Override
        public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            if (r != null && r.getStatus() == 400) return new Exception(cause);
            else {
                if (r != null && r.getStatus() == 405) {
                    return new Exception(cause);
                }
            }
            return cause;
        }
    }
}