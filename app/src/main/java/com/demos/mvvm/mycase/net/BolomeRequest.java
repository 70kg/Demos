package com.demos.mvvm.mycase.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangpeng on 16/8/1.
 */
public class BolomeRequest<T> extends Request<T> {

    private Response.Listener<T> mListener;

    private final Class<T> typeParameterClass;

    private final byte[] mPostBody;

    /**
     * Default number of retries for protocol requests
     */
    public static final int PROTOCOL_MAX_RETRIES = 0;

    /**
     * Socket timeout in milliseconds for protocol requests
     */
    public static final int PROTOCOL_TIMEOUT_MS = 10000;

    /**
     * Default backoff multiplier for protocol requests
     */
    public static final float PROTOCOL_BACKOFF_MULT = 2f;

    public static final String RESULT = "result";

    public static final String DATA = "data";

    /**
     * Uri.withAppendedPath(UnicunApi.BASE_URI, action).toString()
     */
    public BolomeRequest(int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mPostBody = null;
        mListener = listener;
        setRetryPolicy(new DefaultRetryPolicy(PROTOCOL_TIMEOUT_MS,
                PROTOCOL_MAX_RETRIES, PROTOCOL_BACKOFF_MULT));
        typeParameterClass = null;
    }

    public BolomeRequest(int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener, Class<T> classParameter) {
        super(method, url, errorListener);
        mPostBody = null;
        mListener = listener;
        typeParameterClass = classParameter;
        setRetryPolicy(new DefaultRetryPolicy(PROTOCOL_TIMEOUT_MS,
                PROTOCOL_MAX_RETRIES, PROTOCOL_BACKOFF_MULT));
    }

    public BolomeRequest(int method, String url, byte[] postBody,
                         Response.Listener<T> listener,
                         Response.ErrorListener errorListener, Class<T> classParameter) {
        super(method, url, errorListener);
        mPostBody = postBody;
        mListener = listener;
        typeParameterClass = classParameter;
        setRetryPolicy(new DefaultRetryPolicy(PROTOCOL_TIMEOUT_MS,
                PROTOCOL_MAX_RETRIES, PROTOCOL_BACKOFF_MULT));
    }

    public BolomeRequest(int method, String url, byte[] postBody,
                         Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mPostBody = postBody;
        mListener = listener;
        setRetryPolicy(new DefaultRetryPolicy(PROTOCOL_TIMEOUT_MS,
                PROTOCOL_MAX_RETRIES, PROTOCOL_BACKOFF_MULT));
        typeParameterClass = null;
    }

    @Override
    public void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }


    @Override
    public Map<String, String> getHeaders() {
        return createRequestHeaders();
    }

    public Map<String, String> createRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("tourId", "077da1bca48d0856");
        return headers;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        if (typeParameterClass == null) {
            return Response.error(new ParseError());
        }
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(jsonString).getAsJsonObject();
            int result = gson.fromJson(object.get(RESULT), int.class);
            T resultData = typeParameterClass.newInstance();
            if (result == 0) {
                JsonObject data = object.getAsJsonObject(DATA);
                resultData = gson.fromJson(data, typeParameterClass);
            }
            return Response.success(resultData, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException je) {
            return Response.error(new ParseError(je));
        } catch (InstantiationException e) {
            return Response.error(new ParseError(e));
        } catch (IllegalAccessException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public byte[] getBody() {
        return mPostBody;
    }

}
