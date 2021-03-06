package com.me.obo.inrouter;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Keep;

//import com.alibaba.android.arouter.facade.Postcard;
//import com.alibaba.android.arouter.facade.callback.NavigationCallback;
//import com.alibaba.android.arouter.launcher.ARouter;
import com.joybar.librouter.IRouterManagerService;
import com.joybar.librouter.InterceptorCallback;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.joybar.librouter.interceptor.RouteInterceptor;
import com.me.obo.annotation.BigData;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by obo on 2017/11/23.
 */
@Keep
public class InRouter {
    public static void init(Application application) {
//        ARouter.init(application);
    }
    public static void inject(Object activity) {
//        ARouter.getInstance().inject(activity);

        Field []fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(BigData.class) != null) {
                Object data = BigDataManager.removeBigData(field.getName());
                if (data != null) {
                    try {
                        field.set(activity, data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    String mPath;

    IRouterManagerService postcard;
    Rule rule;

    Bundle bundle = new Bundle();

    private InterceptorCallback mNavigationCallback;
    private RouteInterceptor interceptor;

    public InRouter(String module, String path) {
        rule = new Rule(module, path);
    }

    public InRouter withCallBack(InterceptorCallback navigationCallback) {
        mNavigationCallback = navigationCallback;
        return this;
    }

    public InRouter withRouteInterceptor(RouteInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }


    public void navigation(Activity activity) {
//        postcard.navigation(activity);
        IRouterManagerService service = Router.with(activity).buildRule(rule);
        if (mNavigationCallback != null) {
            service.withInterceptorCallback(mNavigationCallback);
        }

        if (interceptor != null) {
            service.addInterceptor(interceptor);
        }
        service.withExtra(bundle);
        service.go();
    }

//    public void navigation(Activity activity, int requetsCode) {
//        postcard.navigation(activity, requetsCode, mNavigationCallback);
//    }



    /////////////////////////////
    //with Params

    //With Bundle
    public InRouter withBundle(Bundle bundle) {
//        postcard.with(bundle);
        return this;
    }

    //With Int
    public InRouter withInt(String key, int value) {
//        postcard.withInt(key, value);
        bundle.putInt(key, value);
        return this;
    }

    //With Int
    public InRouter withLong(String key, long value) {
//        postcard.withLong(key, value);
        bundle.putLong(key, value);
        return this;
    }

    //With String
    public InRouter withString(String key, String value) {
//        postcard.withString(key, value);
        bundle.putString(key, value);
        return this;
    }

    //With Serializable
    public InRouter withSerializable(String key, Serializable value) {
//        postcard.withSerializable(key, value);
        bundle.putSerializable(key, value);
        return this;
    }

    //With Serializable
    public InRouter withParcelable(String key, Parcelable value) {
//        postcard.withParcelable(key, value);
        bundle.putParcelable(key, value);
        return this;
    }

    //With Object
    public InRouter withObject(String key, Object value) {
//        postcard.withObject(key, value);

        return this;
    }

    //With short
    public InRouter withShort(String key, short value) {
//        postcard.withShort(key, value);
        bundle.putShort(key, value);
        return this;
    }

    //With boolean
    public InRouter withBoolean(String key, boolean value) {
//        postcard.withBoolean(key, value);
        bundle.putBoolean(key, value);
        return this;
    }

    //With byte
    public InRouter withByte(String key, byte value) {
        bundle.putByte(key, value);
//        postcard.withByte(key, value);
        return this;
    }

    //With byte
    public InRouter withChar(String key, char value) {
        bundle.putChar(key, value);
//        postcard.withChar(key, value);
        return this;
    }

    //With bigData
    public InRouter withBigBitmap(String key, Bitmap value) {
        BigDataManager.putBigData(key, value);
        return this;
    }

    //////////////////////////////////////
    // Animation

    //With transition
    public InRouter withTransition(int enterAnim, int exitAnim) {
//        postcard.withTransition(enterAnim, exitAnim);
        return this;
    }

}
