package com.aroominn.aroom.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.retrofit.LolipopBitmapMemoryCacheSupplier;

//import com.facebook.cache.disk.DiskCacheConfig;
//import com.facebook.common.util.ByteConstants;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
//import com.facebook.imagepipeline.common.ResizeOptions;
//import com.facebook.imagepipeline.core.ImagePipelineConfig;
//import com.facebook.imagepipeline.listener.RequestListener;
//import com.facebook.imagepipeline.listener.RequestLoggingListener;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * author : yml
 * Created on 2017/6/15 12:16.
 */
public class FrescoUtils {

//    public static void init(Context context, OkHttpClient.Builder mOkHttpClient){
//        Set<RequestListener> requestListeners = new HashSet<>();
//        requestListeners.add(new RequestLoggingListener());
//
//
//        //小图片的磁盘配置,用来储存用户头像之类的小图
//        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context)
//                .setBaseDirectoryPath(context.getCacheDir())//缓存图片基路径
//                .setBaseDirectoryName(context.getString(R.string.app_name))//文件夹名
//                .setMaxCacheSize(20 * ByteConstants.MB)//默认缓存的最大大小。
//                .setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)//缓存的最大大小,使用设备时低磁盘空间。
//                .setMaxCacheSizeOnVeryLowDiskSpace(5 * ByteConstants.MB)//缓存的最大大小,当设备极低磁盘空间
//                .build();
//
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(context, mOkHttpClient.build())
//                .setDownsampleEnabled(true)
//                .setResizeAndRotateEnabledForNetwork(true)
//                .setBitmapMemoryCacheParamsSupplier(new LolipopBitmapMemoryCacheSupplier((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)))
//                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
//                .setBitmapsConfig(Bitmap.Config.RGB_565)
//                .setRequestListeners(requestListeners)
//                .build();
//
//        Fresco.initialize(context, config);
//    }

    /**
     * 优化显示图片
     * @param uri
     * @param draweeView
     */
//    public static void displayImage(Context context, String uri, SimpleDraweeView draweeView){
//        ImageRequest request = ImageRequestBuilder
//                .newBuilderWithSource(Uri.parse(uri))
//                .setResizeOptions(new ResizeOptions(ScreenUtils.dip2px(context,120), ScreenUtils.dip2px(context,120)))
//                .build();
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setImageRequest(request)
//                .setOldController(draweeView.getController())
//                .setAutoPlayAnimations(true)
//                .build();
//        draweeView.setController(controller);
//    }



    /**
     * 加载本地图片
     *
     * @param resId
     * @param draweeView
     */
//    public static void displayLocalImage(int resId, SimpleDraweeView draweeView) {
//        String url = "res://com.aroominn.aroom/" + resId;
//        Uri uri = Uri.parse(url);
//        draweeView.setImageURI(uri);
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request).setAutoPlayAnimations(true).build();
//        draweeView.setController(controller);
//    }
}
