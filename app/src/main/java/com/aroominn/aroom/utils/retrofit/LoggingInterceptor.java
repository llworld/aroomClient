package com.aroominn.aroom.utils.retrofit;


import com.aroominn.aroom.BuildConfig;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.SharedUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();
    Request.Builder requestBuilder = original.newBuilder()
            .header("token", SharedUtils.getInstance().getToken());
    Request request = requestBuilder.build();
    if (BuildConfig.DEBUG) {
        L.e(String.format("request_JSON %s on %s%n%s",
                request.url(), request.body(), request.headers()));
    }
    return chain.proceed(request);
  }
}