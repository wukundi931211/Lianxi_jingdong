package com.example.tianlong.lianxi_jingdong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tianlong.lianxi_jingdong.R;



public class TitleBar extends RelativeLayout implements View.OnClickListener{

    ImageView back;//点击销毁
    TextView title;

    //设置接口
    BarListener barListener;
    public TitleBar(Context context) {
        super(context);
        initBar(context,null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化
        initBar(context, attrs);
    }

    private void initBar(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.title_bar,this);
        back = view.findViewById(R.id.tit_back);
        title = view.findViewById(R.id.tit_text);
        //确定单击
        back.setOnClickListener(this);

        //判断样式
        if (attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
             String str = typedArray.getString(R.styleable.TitleBar_title);
            title.setText(str);
            typedArray.recycle();// 回收资源 防止内存溢出
        }
    }
    /**
     * 通过代码设置标题
     * @param str
     */
    public void setTitle(String str) {
        title.setText(str);
    }

    //单击事件
    @Override
    public void onClick(View view) {
        if (view.getId() == back.getId()){
            if (barListener!=null){//处理返回事件
                barListener.back();
            }
        }
    }
    //接口
    public interface BarListener {
        void back();
    }
    //设置监听
    public void setBarListener(BarListener barListener) {
        this.barListener = barListener;
    }

    /**
     * 设置是否
     * @param isHide
     */
    public  void  setHideBack(boolean isHide){
        if (isHide){
            back.setVisibility(GONE);
        }else {
            back.setVisibility(VISIBLE);
        }
    }
}
