package io.github.anotherjack.testlib;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import io.github.anotherjack.testlib.annotation.RequestPermissions;
import io.reactivex.functions.Consumer;

/**
 * Created by jack on 2018/1/5.
 */

@Aspect
public class MyAspect {
    private static final String TAG = "AOPDemo";

    //类上有@ToolbarActivity注解的Activity的onCreate方法
    @After("execution(* android.app.Activity.onCreate(..)) && within(@io.github.anotherjack.testlib.annotation.ToolbarActivity *)")
//    @After("execution(* android.app.Activity.onCreate(..))")
    public void addToolbar(JoinPoint joinPoint) throws Throwable {
        String signatureStr = joinPoint.getSignature().toString();
        Log.d(TAG, signatureStr + " --> addToolbar");
    }


//    @Before("call(* android.widget.Toast.show())")
//    public void changeToast(JoinPoint joinPoint) throws Throwable {
//        Toast toast = (Toast) joinPoint.getTarget();
//        toast.setText("修改后的toast");
//        Log.d(TAG, " --> changeToast");
//    }

//    @Around("call(* android.widget.Toast.setText(java.lang.CharSequence))")
//    public void handleToastText(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Log.d(TAG," start handleToastText");
//        proceedingJoinPoint.proceed(new Object[]{"处理过的toast"}); //这里把它的参数换了
//        Log.d(TAG," end handleToastText");
//
//    }

    //任意注解有@RequestPermissions方法的调用
    @Around("call(* *..*.*(..)) && @annotation(requestPermissions)")
    public void requestPermissions(final ProceedingJoinPoint proceedingJoinPoint, RequestPermissions requestPermissions) throws Exception{
        Log.d(TAG,"----------request permission");
        String[] permissions = requestPermissions.value(); //获取到注解里的权限数组

        Object target = proceedingJoinPoint.getTarget();
        Activity activity = null;
        if (target instanceof Activity){
            activity = (Activity) target;
        }else if (target instanceof Fragment){
            activity = ((Fragment)target).getActivity();
        }

        RxPermissions rxPermissions = new RxPermissions(activity);
        final Activity finalActivity = activity;
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>(){
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if(granted){
                            try {
                                proceedingJoinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }else {
                            Toast.makeText(finalActivity,"未获取到权限，不能拍照",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}
