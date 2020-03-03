# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#   Brvah开始
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
#   Brvah结束


# glide 4.x
-keep class com.bumptech.glide.Glide { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# renderscript
-keep class android.support.v8.renderscript.** { *; }

# kotlin协程
# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

#极光
#-dontoptimize
#-dontpreverify
#
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
#
#-dontwarn cn.jiguang.**
#-keep class cn.jiguang.** { *; }

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


-dontwarn com.squareup.picasso.**


#JMessage
#-dontoptimize
#-dontpreverify
#-keepattributes  EnclosingMethod,Signature
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#
#-dontwarn cn.jiguang.**
#-keep class cn.jiguang.** { *; }
#
#-dontwarn cn.jmessage.**
#-keep class cn.jmessage.**{ *; }
#
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}

#========================gson================================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#========================protobuf================================
-keep class com.google.protobuf.** {*;}

#JSMS
#-keep class cn.jpush.sms.SMSSDK {*;}
#-keep class cn.jpush.sms.listener.** {*;}


# ------融云混淆
#-keepattributes Exceptions,InnerClasses
#
#-keepattributes Signature

## RongCloud SDK
#-keep class io.rong.** {*;}
#-keep class cn.rongcloud.** {*;}
#-keep class * implements io.rong.imlib.model.MessageContent {*;}
#-dontwarn io.rong.push.**
#-dontnote com.xiaomi.**
#-dontnote com.google.android.gms.gcm.**
#-dontnote io.rong.**
#
## VoIP
#-keep class io.agora.rtc.** {*;}
#
## Location
#-keep class com.amap.api.**{*;}
#-keep class com.amap.api.services.**{*;}
#
## 红包
#-keep class com.google.gson.** { *; }
#-keep class com.uuhelper.Application.** {*;}
#-keep class net.sourceforge.zbar.** { *; }
#-keep class com.google.android.gms.** { *; }
#-keep class com.alipay.** {*;}
#-keep class com.jrmf360.rylib.** {*;}
#
#-ignorewarnings
##融云EventBus需要以onEvent开头
#-keepclassmembers class ** {
# public void onEvent*(**);
#}
## ------融云混淆  end
