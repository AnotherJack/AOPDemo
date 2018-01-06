package io.github.anotherjack.testlib;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

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

    @Around("call(* android.widget.Toast.setText(java.lang.CharSequence))")
    public void handleToastText(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log.d(TAG," start handleToastText");
        proceedingJoinPoint.proceed(new Object[]{"处理过的toast"}); //这里把它的参数换了
        Log.d(TAG," end handleToastText");

    }

}
