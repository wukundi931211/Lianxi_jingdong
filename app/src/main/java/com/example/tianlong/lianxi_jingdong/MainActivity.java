package com.example.tianlong.lianxi_jingdong;

import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tianlong.lianxi_jingdong.activity.BaseActivity;
import com.example.tianlong.lianxi_jingdong.network.UrlConstant;
import com.example.tianlong.lianxi_jingdong.network.VolleySingleton;
import com.example.tianlong.lianxi_jingdong.view.TitleBar;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private TitleBar main_titleBar;
    private RelativeLayout main_relativeLayout;
    private Button main_btn_search;
    private EditText main_edit_search;
    private TagFlowLayout main_tagFlowLayout;
    private TabLayout main_tabLayout;
    private ViewPager main_viewPager;
    private static final String TAG = "SEARCH";
    UrlConstant urlConstant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initView() {
        //标题栏
        main_titleBar = findViewById(R.id.title_bar);
        main_relativeLayout = findViewById(R.id.search_title);
        //搜索按钮
        main_btn_search = findViewById(R.id.btn_search);
        //搜索框
        main_edit_search = findViewById(R.id.edit_search);
        //流式布局
        main_tagFlowLayout = findViewById(R.id.main_flowlayout);
        //tablayout
        main_tabLayout = findViewById(R.id.main_tablayout);
        //viewpager
        main_viewPager = findViewById(R.id.main_viewPager);
        //单击事件
        main_btn_search.setOnClickListener(this);

        //搜索框操作方法

            main_edit_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String keyword = s.toString();
                    if (!TextUtils.isEmpty(keyword)){
                        //请求的方法数据
                            get(s.toString());

                    }
                }
            });
    }
    //单击搜索的方法
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search:
                String s = main_edit_search.getText().toString();
                get(s);
                break;
        }
    }

    /**
     * 利用关键字搜索网络数据
     * 请求的方法数据
     * @param keyword
     */
    public void get(String keyword) {
        //取消上次的请求
        VolleySingleton.getInstance2().cancelReq(TAG);
        //拼接URL
        urlConstant = new UrlConstant();
        //拼接加转码   MIME字符串之间
        //urlConstant.HOST + urlConstant.SEARCH+"?keyword="
        String url = "http://39.108.3.12:3000/v1/search/restaurant?keyword="+ URLEncoder.encode(keyword);
        //组装请求
        //请求方法，URL，正确响应，错误响应
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //请求数据正确的监听
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                Toast.makeText(MainActivity.this,response+"",Toast.LENGTH_SHORT).show();

            }
            //请求数据失败的监听
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.setTag(TAG);
        //添加到队列执行请求
        VolleySingleton.getInstance1().addToRequestQueue(request);

    }
}
