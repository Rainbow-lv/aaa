package com.lll.flowlayout02;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlowLayout extends FrameLayout {
    //设置水平距离
    int H_DISTANCE = 20;
    //设置竖直距离
    int V_DISTANCE = 20;
    private float mTextSize;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context,AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.flow);
        mTextSize = array.getDimension(R.styleable.flow_textSize, 0);
    }

    public FlowLayout(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.flow);
        mTextSize = array.getDimension(R.styleable.flow_textSize, 0);//得到的值单位是像素
    }
    public void addTextView(String keys){
        //加载xml的布局
        TextView textView = (TextView) View.inflate(getContext(), R.layout.flow_item, null);
        textView.setText(keys);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);//像素
        //设置布局的自适应宽高
        LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //控件设置上布局的参数
        textView.setLayoutParams(params);

        this.addView(textView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取本控件的宽度
        int width = getWidth();
        //行数
        int row = 0;
        //子控件左边的坐标
        int disWidth = H_DISTANCE;
        for (int i=0;i<getChildCount();i++){
            //查找子控件
            View view = getChildAt(i);
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();
            //判断右边的控件宽度是否超过了屏幕的宽度
            if (disWidth + viewWidth > width){
                //行数增加
                row++;
                //还远左边坐标
                disWidth = H_DISTANCE;
            }
           int viewTop = row * viewHeight+row * V_DISTANCE;
            view.layout(disWidth,viewTop,disWidth+viewWidth,viewTop+viewHeight);
            //记录下一次子控件左边的坐标
            disWidth += (viewWidth+H_DISTANCE);
        }
    }

}
