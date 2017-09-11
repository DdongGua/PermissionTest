package com.example.permissiontest.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.widget.Toast;

/**
 * Created by 亮亮 on 2017/9/11.
 */

public abstract class BaseActivity extends Activity {
    private int permissionRequestCode=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public abstract void initData() ;


    public abstract void initView() ;

    public void ifHasPermission(String Permission,int code){
       permissionRequestCode=code;
        int permissionStatus  = ContextCompat.checkSelfPermission(this, Permission);
        //判断是否开启了
        if(permissionStatus== PackageManager.PERMISSION_GRANTED){
            //打电话
            dosomething();
        }else{
            //如果没有，就需要去申请
            /**
             * 参数1 activity
             * 参数2 权限 需要声明权限写成string数组
             * 参数3  请求码
             */
            ActivityCompat.requestPermissions(this,new String[]{Permission},permissionStatus);
        }

    }
    //需要让子类去实现的方法
    public  void dosomething(){

    }
    //当调用了  ActivityCompat.requestPermissions 这个回调方法就会被调用
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissionRequestCode==requestCode){
            //如果允许了
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dosomething();
            }else{
                Toast.makeText(this, "此功能需要打开您的打电话权限，请在设置页面手动开启", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
