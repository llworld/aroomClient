package com.aroominn.aroom.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.widget.TextView;

import com.aroominn.aroom.BuildConfig;
import com.aroominn.aroom.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * 公用工具类
 * Created by yml on 2016/5/28.
 */
public class PublicUtils {

    /**
     * 去掉dialog
     */
    public static void dismisProgressDialog(Dialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将double转成int
     *
     * @param
     * @return
     */
    public static int double2int(Double value) {
        String priced = String.valueOf(value);
        String str = priced.substring(0, priced.lastIndexOf("."));
        return Integer.parseInt(str);
    }

    /**
     * 将double转成String
     *
     * @param value 数据源
     * @return 截取
     */
    public static String double2String(String value) {
        return value.substring(0, value.lastIndexOf("."));
    }

    /**
     * 去掉价格多余的数
     *
     * @param value 数据源
     * @return 截取
     */
    public static String clearPrice(String value) {
        String str = value.substring(0, value.lastIndexOf("."));
        String end = value.substring(value.lastIndexOf(".") + 1, value.length());
        if (Integer.parseInt(end) > 0) {
            return str + "." + end;
        } else {
            return str;
        }
    }

    /**
     * 添加中划线
     *
     * @param textView 数据源
     * @return 截取
     */
    public static TextView addLine(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        return textView;
    }

    /**
     * 获取图片后缀
     *
     * @param value 数据源
     * @return 截取
     */
    public static String getLastOfPhoto(String value) {
        return value.substring(value.lastIndexOf("."));
    }

    /**
     * 获取请求签名
     *
     * @return 参数
     */
    /*public static List<PoiItem> getSortList(List<PoiItem> data, final String localPoi) {
        int position = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getTitle().equals(localPoi)) {
                position = i;
            }
        }
        if (position != -1) {
            Collections.swap(data, position, 0);
        }
        return data;
    }*/

    /**
     * 获取系统当前时间
     *
     * @param context
     * @return
     */
    public static String getCurrentTime(Context context) {
        String currentTime = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_ABBREV_ALL);
        return currentTime;

    }


    /**
     * 计算秒数
     *
     * @param second 数据源
     * @return 截取
     */
    public static String getPlayTime(int second) {
        StringBuilder sb = new StringBuilder();
        int minute = second / 60;
        int second_after = second % 60;

        sb.append(minute < 10 ? "0" + minute : minute);
        sb.append(":");
        sb.append(second_after < 10 ? "0" + second_after : second_after);
        return sb.toString();
    }

    /**
     * 监测是否是低版本
     *
     * @return
     */
    public static boolean isLowVersion() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 验证手机号
     *
     * @param phoneNum
     * @return
     */
    public static Boolean isPhoneNumAvaliable(String phoneNum) {
        String telRegex = "[1][3578]\\d{9}";
        return phoneNum.matches(telRegex);
    }

    /**
     * 截取地址字符串
     *
     * @param url
     * @return
     */
    public static String substring(String url) {
        String str = url.substring(url.lastIndexOf("/") + 1, url.length());
        return str;
    }

    /**
     * 截取集合后五位
     *
     * @param stringList
     * @return
     */
    public static List<String> subList(List<String> stringList) {
        List<String> newList = new ArrayList<>();
        for (int i = stringList.size() - 1; i >= 0; i--) {
            if (stringList.size() - i <= 5) {
                newList.add(stringList.get(i));
            }
        }
        return newList;
    }


    /**
     * 获取终端ID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            L.e("version", "-------error------->" + e.toString());
        }
        return versionName;
    }

    /**
     * 返回当前程序版本名
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            L.e("version", "-------error------->" + e.toString());
        }
        return versionCode;
    }

    /**
     * 获取设备号
     *
     * @param context
     * @return
     */
    //    public static String getDeviceId(Context context) {
    //        return JPushInterface.getUdid(context);
    //    }


    /**
     * 获取15位随机数
     *
     * @return
     */
    public static String getOutTradeNo() {
        long numcode = (long) ((Math.random() * 9 + 1) * 100000000000000L);
        String result = String.valueOf(numcode);
        return result;
    }

    /**
     * 获取5位随机数
     *
     * @return
     */
    public static String getFiveTradeNo() {
        long numcode = (long) ((Math.random() * 9 + 1) * 10000);
        String result = String.valueOf(numcode);
        return result;
    }


    /**
     * 将数组转化为字符串（逗号拼接）
     *
     * @return
     */
    public static String getStrByIntArray(int[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                str += array[i];
                continue;
            }
            str += array[i] + ",";
        }
        return str;
    }

    /**
     * Set集合转化字符串（逗号拼接）
     *
     * @return
     */
    public static String getArrBySetArray(Set<Integer> array) {
        String str = "";
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str += list.get(i);
                continue;
            }
            str += list.get(i) + ",";
        }
        return str;
    }

    /**
     * 将字符串拼接
     *
     * @return
     */
    public static String getArrayStr(List<String> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            str += array.get(i);
            if (i != array.size() - 1) {
                str += ";";
            }
        }
        return str;
    }

    /**
     * 满减拼接
     *
     * @return
     */
    /*public static String getMinusStr(Context context, List<Shop.ShopResult.ShopData.ShopMinus> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            str += context.getResources().getString(R.string.full_text) +
                    array.get(i).getFull() + context.getResources().getString(R.string.minus_text) +
                    array.get(i).getMinus();
            if (i != array.size() - 1) {
                str += ";";
            }
        }
        return str;
    }*/


    /**
     * 满减拼接
     *
     * @return
     */
    /*public static String getDiscountStr(Context context, List<Shop.ShopResult.ShopData.ShopDiscount> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            str += context.getResources().getString(R.string.discount_start_text) +
                    array.get(i).getDiscount() + context.getResources().getString(R.string.discount_end_text);
            if (i != array.size() - 1) {
                str += ";";
            }
        }
        return str;
    }*/

    /**
     * 满赠拼接
     *
     * @return
     */
    /*public static String getGiveStr(Context context, List<Shop.ShopResult.ShopData.ShopGive> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getFull() == null) {
                str += context.getResources().getString(R.string.give_zero_text) +
                        array.get(i).getGive();
            } else {
                str += context.getResources().getString(R.string.full_text) +
                        array.get(i).getFull() + context.getResources().getString(R.string.give_end_text) +
                        array.get(i).getGive();
            }
            if (i != array.size() - 1) {
                str += ";";
            }
        }
        return str;
    }*/

    /**
     * 满返拼接
     *
     * @return
     */
    /*public static String getBackStr(Context context, List<Shop.ShopResult.ShopData.ShopBack> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            str += context.getResources().getString(R.string.full_text) +
                    array.get(i).getFull() + context.getResources().getString(R.string.back_middle_text) +
                    array.get(i).getBack() + context.getResources().getString(R.string.yuan_text);
            if (i != array.size() - 1) {
                str += ";";
            }
        }
        return str;
    }*/

    /**
     * 满返拼接
     *
     * @return
     */
    /*public static String getNewUserStr(Context context, List<Shop.ShopResult.ShopData.ShopNewUser> array) {
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getType() == 0){
                str += context.getResources().getString(R.string.new_user_minus_text) +
                        array.get(i).getNum() + context.getResources().getString(R.string.yuan_text);
            }else {
                str += context.getResources().getString(R.string.new_user_discount_text) +
                        String.valueOf(Double.parseDouble(array.get(i).getNum()) * 10) +
                        context.getResources().getString(R.string.discount_text);
            }

            if (i != array.size() - 1) {
                str += ";";
            }
        }
        return str;
    }*/


    /**
     * 获取订单状态
     *
     * @return
     */
    /*public static int getStateStr(int stateCode) {
        switch (stateCode) {
            case 1001://配货中

                return R.string.order_produce_text;

            case 1002://派送中

                return R.string.order_delivery_text;

            case 2001://待评价

                return R.string.order_comment_text;

            case 2002://已完成

                return R.string.order_finish_text;

            case 3001://退款中

                return R.string.order_refund_text;

        }
        return 0;
    }*/


    /**
     * 获取订单状态字体颜色
     *
     * @return
     */
    public static int getStateTextColor(int stateCode) {
        switch (stateCode) {
            case 1001://配货中

                return R.color.order_red;

            case 1002://派送中

                return R.color.order_red;

            case 2001://待评价

                return R.color.fontcolordeep;

            case 2002://已完成

                return R.color.fontcolordeep;

            case 3001://退款中

                return R.color.order_red;

        }
        return 0;
    }

    /**
     * 如果不是在正式包，添加拦截 打印响应json
     *
     * @param okHttpclient
     */
    public static OkHttpClient.Builder initHttpLoggingInterceptor(OkHttpClient.Builder okHttpclient) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                    new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            if (TextUtils.isEmpty(message))
                                return;
                            String s = message.substring(0, 1);
                            //如果收到想响应是json才打印
                            if ("{".equals(s) || "[".equals(s)) {
                                L.e("JSON" + message);
                            }
                        }
                    });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpclient.addInterceptor(logging);
        }
        return okHttpclient;
    }


    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param location
     * @return
     */
    /*public synchronized static String getLocationStr(AMapLocation location) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");

            if (location.getProvider().equalsIgnoreCase(
                    android.location.LocationManager.GPS_PROVIDER)) {
                // 以下信息只有提供者是GPS时才会有
                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : "
                        + location.getSatellites() + "\n");
            } else {
                // 提供者是GPS时是没有以下信息的
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
            }
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }
        return sb.toString();
    }*/
}
