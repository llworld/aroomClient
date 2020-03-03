package com.aroominn.aroom.utils.okHttp.callback;

import android.util.Log;

import com.aroominn.aroom.utils.PublicUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by yml on 17/02/24.
 */
public abstract class GosnCallback<T> extends Callback<T> {

    private static Gson mGson = new Gson();

    private Class<T> mClass;
    private TypeToken<T> mTypeToken;
    private String url;

    public void setmClass(Class<T> mClass) {
        this.mClass = mClass;
    }

    public void setmTypeToken(TypeToken<T> mTypeToken) {
        this.mTypeToken = mTypeToken;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public T parseNetworkResponse(Response response) throws IOException {
        String jsonString = response.body().string();
        Log.d("JSON", "-----url----->" + PublicUtils.substring(url) + "-----JSON----->" + jsonString);
        if (mTypeToken == null) {
            return mGson.fromJson(jsonString, mClass);
        } else {
            return mGson.fromJson(jsonString, mTypeToken.getType());
        }
    }
}
