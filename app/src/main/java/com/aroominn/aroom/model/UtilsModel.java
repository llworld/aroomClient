package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UtilsModel {

    //    上传文件
    void upFile(BaseImpl context, String text, List<MultipartBody.Part> parts);
}
