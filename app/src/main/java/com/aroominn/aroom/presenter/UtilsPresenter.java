package com.aroominn.aroom.presenter;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UtilsPresenter {

    void getStory(BaseImpl context, String text, List<MultipartBody.Part> parts);
//    void getStory(BaseImpl context, String text,Map<String, RequestBody> param);

}
