package com.libraray.spinnerview.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libraray.spinnerview.R;
import com.libraray.spinnerview.dao.UserAccountDao;

import java.util.ArrayList;
import java.util.List;

public class SpinnerView extends RelativeLayout {

    private EditText    mEt_main;
    private ImageView   mIv_arrow_main;
    private PopupWindow mPw;

    private List<String> mDatas = new ArrayList<>();
    private UserAccountDao mAccountDao;
    private MyAdapter      mAdapter;

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();

        initPopupWindow();

        initData();

        initListener();
    }

    public SpinnerView(Context context) {
        super(context, null);
    }


    private void initPopupWindow() {

        mPw = new PopupWindow(-2, -2);

        View view = View.inflate(getContext(), R.layout.lv_popwind_main, null);

        ListView lv_pop_main = (ListView) view.findViewById(R.id.lv_pop_main);

        mAdapter = new MyAdapter();

        lv_pop_main.setAdapter(mAdapter);

        mPw.setContentView(view);

        mPw.setFocusable(true);

        mPw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    private void initListener() {

        mIv_arrow_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPw.isShowing()) {

                    mPw.dismiss();

                } else {

                    mPw.showAsDropDown(mEt_main, 0, 0);

                }
            }
        });

    }



    private void initData() {

        mAccountDao = new UserAccountDao(getContext());
/*

        for (int i=0; i<4;i++){

            //mDatas.add("108565360"+i);

            mAccountDao.insert("108565360" + i);

        }
*/

        mDatas = mAccountDao.getAllAccount();

        Log.d("tagtag","size=="+mDatas.size());

    }


    //提供一个接口接收数据
    public interface  OnLoginChangeListener{

        public String getLoginAccount();

    }
    public OnLoginChangeListener mOnLoginChangeListener;

    public void setOnLoginChangeListener(OnLoginChangeListener onLoginChangeListener){

        mOnLoginChangeListener = onLoginChangeListener;

        String loginAccount = mOnLoginChangeListener.getLoginAccount();

        //有数据了！
        //保存数据到数据库
        //mAccountDao.delete(loginAccount);
        mAccountDao.insert(loginAccount);
        //刷新界面
        mDatas = mAccountDao.getAllAccount();
        mAdapter.notifyDataSetChanged();
    }


    //传递edittext输入的数据
    public String getLoginAccount(){

        String str = mEt_main.getText().toString().trim();

        return str;
    }


    private void initView() {

        View view = View.inflate(getContext(), R.layout.spinnerview, this);

        mEt_main = (EditText) view.findViewById(R.id.et_main);

        mIv_arrow_main = (ImageView) view.findViewById(R.id.iv_arrow_main);

    }


    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            if (mDatas!=null&&mDatas.size()>0){

                return mDatas.size();
            }

            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            final ViewHold viewHold;

            if (convertView == null){

                convertView = View.inflate(getContext(),R.layout.item_lv_main,null);

                viewHold = new ViewHold();

                convertView.setTag(viewHold);


                viewHold.iv_user = (ImageView) convertView.findViewById(R.id.iv_user_item_lv_main);

                viewHold.tv_account = (TextView) convertView.findViewById(R.id.tv_accout_item_lv_main);

                viewHold.iv_delete = (ImageView) convertView.findViewById(R.id.iv_dele_item_lv_main);



            }else {

                viewHold = (ViewHold) convertView.getTag();

            }

            viewHold.tv_account.setText(mDatas.get(position));


            final View finalConvertView = convertView;
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v==viewHold.iv_delete){

                        mAccountDao.delete(mDatas.get(position));

                        mDatas.remove(position);

                        MyAdapter.this.notifyDataSetChanged();

                    }else if (v== finalConvertView){

                        mEt_main.setText(mDatas.get(position));
                        mEt_main.setSelection(mDatas.get(position).length());
                    }

                        mPw.dismiss();
                }
            };
            convertView.setOnClickListener(onClickListener);
            viewHold.iv_delete.setOnClickListener(onClickListener);

            return convertView;
        }

        class ViewHold{

            ImageView iv_user;

            TextView tv_account;

            ImageView iv_delete;

        }
    }
}