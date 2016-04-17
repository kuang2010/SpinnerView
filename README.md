use way：
In activity xml：
   <com.libraray.spinnerview.view.SpinnerView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/sv_lib_activity"/>

    <Button
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:text="登   录"
        android:textColor="#ffffff"
        android:background="@drawable/bt_bg_selector_main"
        android:id="@+id/bt_login_main"
        android:onClick="login"/>
        
In activity code：

     mSv_lib_activity = (SpinnerView) findViewById(R.id.sv_lib_activity);
     
     public void login(View v){

        //获取输入的数据
        final String loginAccount = mSv_lib_activity.getLoginAccount();

        if (!TextUtils.isEmpty(loginAccount)){

            //刷新界面
            mSv_lib_activity.setOnLoginChangeListener(new SpinnerView.OnLoginChangeListener() {
                @Override
                public String getLoginAccount() {

                    return loginAccount;
                }
            });

        }else {

            Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
        }

    }
![image](https://github.com/kuang2010/SpinnerView/blob/master/showone1.png) 


![image](https://github.com/kuang2010/SpinnerView/blob/master/showtwo2.png)
