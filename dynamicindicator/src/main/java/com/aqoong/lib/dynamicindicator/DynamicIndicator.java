package com.aqoong.lib.dynamicindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andy
 *
 * Custom UI
 * for Progress UI used indicator
 * <p>
 *
 * email : han.andy@cherrypica.com
 * date  : 04/04/2019
 **/
public class DynamicIndicator extends GridLayout
{
    private final String TAG = getClass().getSimpleName();

    public static final int MODE_DOT = 10001;
    public static final int MODE_LINE = 10002;

    private int maxSize;
    private CharSequence[] textList;
    private int iconDefault;
    private int iconSelect;
    //    private int itemMargin;
    private int mode;

    private int colorOff;
    private int colorOn;
    private float itemWidth;

    private boolean strTrace;

    private ArrayList<IndicatorItem> indicatorList;

    public DynamicIndicator(Context context)
    {
        super(context);
    }

    public DynamicIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DynamicIndicator);
        try
        {
//            itemMargin = (int)typedArray.getDimension(R.styleable.CustomIndicator_itemMargin, 0);
            maxSize = typedArray.getInt(R.styleable.DynamicIndicator_maxSize, 0);
            textList = typedArray.getTextArray(R.styleable.DynamicIndicator_android_entries);
            iconDefault = typedArray.getResourceId(R.styleable.DynamicIndicator_iconOff, 0);
            iconSelect = typedArray.getResourceId(R.styleable.DynamicIndicator_iconOn, 0);
            mode = typedArray.getInteger(R.styleable.DynamicIndicator_mode, MODE_DOT);
            colorOff = typedArray.getColor(R.styleable.DynamicIndicator_colorOff, ContextCompat.getColor(context, R.color.indicator_off));
            colorOn = typedArray.getColor(R.styleable.DynamicIndicator_colorOn, ContextCompat.getColor(context, R.color.indicator_on));
            strTrace = typedArray.getBoolean(R.styleable.DynamicIndicator_strTraceEnable, false);

            itemWidth = typedArray.getDimension(R.styleable.DynamicIndicator_itemWidth, 30);
        }finally
        {
            typedArray.recycle();
        }

        try
        {
            inflateViews(context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inflateViews(Context context) throws Exception
    {
        if(textList == null){
            textList = new CharSequence[maxSize];
        }

        indicatorList = new ArrayList<>();
        for(int i = 0 ; i < textList.length ; i++){

            IndicatorItem item = new IndicatorItem(context);

            if(i == 0){
                item.vLineLeft.setVisibility(INVISIBLE);
            }

            if(i == textList.length
                    -1){
                item.vLineRight.setVisibility(INVISIBLE);
            }

            this.addView(item);
            indicatorList.add(item);
        }

        drawEmptyIndicator();
    }

    public void selectDot(int position){
        selectDot(position, null);
    }

    public void selectDot(int position, String text){

        for(int i = 0 ; i < indicatorList.size() ; i++){
            IndicatorItem indicatorItem = indicatorList.get(i);
            String tempText = "";
            if(text != null && i == position){
                tempText = text;
            }else{
                tempText = textList[i] != null ? textList[i].toString() : "";
            }

            switch(mode){
                case MODE_DOT:
                    indicatorItem.vLineLeft.setBackgroundColor(colorOff);
                    indicatorItem.vLineRight.setBackgroundColor(colorOff);

                    if(i == position){
                        indicatorItem.setStateText(true, tempText);
                        indicatorItem.setStateDot(true);
                    }else{
                        indicatorItem.setStateText(false, tempText);
                        indicatorItem.setStateDot(false);
                    }
                    break;
                case MODE_LINE:
                    if(i < position){
                        indicatorItem.vLineLeft.setBackgroundColor(colorOn);
                        indicatorItem.vLineRight.setBackgroundColor(colorOn);
                        indicatorItem.setStateText(false, tempText);
                        indicatorItem.setStateDot(true);

                    }else if(i == position){
                        indicatorItem.vLineLeft.setBackgroundColor(colorOn);
                        indicatorItem.vLineRight.setBackgroundColor(colorOff);
                        indicatorItem.setStateText(true, tempText);
                        indicatorItem.setStateDot(true);

                    }else{
                        indicatorItem.vLineLeft.setBackgroundColor(colorOff);
                        indicatorItem.vLineRight.setBackgroundColor(colorOff);
                        indicatorItem.setStateText(false, tempText);
                        indicatorItem.setStateDot(false);
                    }
                    break;
            }
        }
    }

    private void drawEmptyIndicator()
    {
        for(int i = 0 ; i < indicatorList.size() ; i++){
            IndicatorItem indicatorItem = indicatorList.get(i);

            indicatorItem.vLineLeft.setBackgroundColor(colorOff);
            indicatorItem.vLineRight.setBackgroundColor(colorOff);
            indicatorItem.setStateDot(false);
            indicatorItem.setStateText(false, textList[i] != null ? textList[i].toString() : "");
        }
    }

    public void refreshIndicator(){
        try
        {
            removeAllViewsInLayout();
            inflateViews(getContext());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class IndicatorItem extends RelativeLayout
    {
        public TextView tvText;
        public View     vLineLeft;
        public View     vLineRight;
        public ImageView ivDotImg;

        public IndicatorItem(Context context)
        {
            super(context);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            inflater.inflate(R.layout.indicator_item, this);
            findViewById(R.id.parent).getLayoutParams().width = (int) itemWidth;

            ivDotImg = createDotView(this);
            vLineLeft = findViewById(R.id.line_left);
            vLineLeft.getLayoutParams().width = ((int) itemWidth/2);

            vLineRight = findViewById(R.id.line_right);
            vLineRight.getLayoutParams().width = ((int) itemWidth/2);

            tvText = findViewById(R.id.text);
            if(!strTrace)tvText.setVisibility(INVISIBLE);
        }

        private ImageView createDotView(View parentView){
            ImageView imgDot = parentView.findViewById(R.id.image);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin = itemMargin;
//        params.rightMargin = itemMargin;
//        params.gravity = Gravity.CENTER;
//
//        imgDot.setLayoutParams(params);
            imgDot.setImageResource(iconDefault);

            return imgDot;
        }

        public void setStateText(boolean isSelect, String text){
            if(isSelect){
                tvText.setText(text);
                tvText.setTextColor(colorOn);
                tvText.setVisibility(VISIBLE);
            }else{
                tvText.setText(text);
                tvText.setTextColor(colorOff);
                if(!strTrace){
                    tvText.setVisibility(INVISIBLE);
                }
            }
        }

        public void setStateDot(boolean isSelect){
            if(isSelect){
                if(iconSelect > 0)
                {
                    ivDotImg.setImageResource(iconSelect);
                }else{
                    ivDotImg.setSelected(true);
                }
            }else{
                if(iconSelect > 0)
                {
                    ivDotImg.setImageResource(iconDefault);
                }else{
                    ivDotImg.setSelected(false);
                }
            }
        }
    }


    public DynamicIndicator setMaxSize(int size){ this.maxSize = size; return this;}
    public int getMaxSize(){ return maxSize = textList.length; }
    public DynamicIndicator setTextList(String...texts){
        this.textList = texts;
        refreshIndicator();
        return this;
    }
    public CharSequence[] getTextList(){return this.textList;}
    public DynamicIndicator setMode(final int mode){
        this.mode = mode;return this;
    }
    public int getMode(){
        return this.mode;
    }
    public void setColumnCount(int count){ super.setColumnCount(count);}
    public int getColumnCount(){return getColumnCount();}
    public DynamicIndicator setItemWidth(float itemWidthDp){
        this.itemWidth = getResources().getDisplayMetrics().density * itemWidthDp;
        return this;
    }
    public DynamicIndicator setStringTraceMode(boolean isTraceMode){
        this.strTrace = isTraceMode;
        return this;
    }
    public boolean getStringTraceMode(){return this.strTrace;}
    public float getItemWidth(){return itemWidth;}
}
