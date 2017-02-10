package com.example.excelParser; 
 
import com.example.excelParser.MyGestureListener.OnFlingListener;
import com.teacher.helper.R;

import android.content.Context; 
import android.util.AttributeSet; 
import android.view.GestureDetector; 
import android.view.MotionEvent; 
import android.view.View; 
import android.widget.ViewFlipper; 
 
public class MyViewFlipper extends ViewFlipper implements OnFlingListener { 
 
    private GestureDetector mGestureDetector = null; 
 
    private OnViewFlipperListener mOnViewFlipperListener = null; 
 
    public MyViewFlipper(Context context) { 
        super(context); 
    } 
 
    public MyViewFlipper(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 
 
    public void setOnViewFlipperListener(OnViewFlipperListener mOnViewFlipperListener) { 
        this.mOnViewFlipperListener = mOnViewFlipperListener; 
        MyGestureListener myGestureListener = new MyGestureListener(); 
        myGestureListener.setOnFlingListener(this); 
        mGestureDetector = new GestureDetector(myGestureListener); 
    } 
 
    @Override 
    public boolean onInterceptTouchEvent(MotionEvent ev) { 
        if (null != mGestureDetector) { 
            return mGestureDetector.onTouchEvent(ev); 
        } else { 
            return super.onInterceptTouchEvent(ev); 
        } 
    } 
 
    @Override 
    public void flingToNext() { 
        if (null != mOnViewFlipperListener) { 
            int childCnt = getChildCount(); 
            if (childCnt == 2) { 
                removeViewAt(1); 
            } 
            addView(mOnViewFlipperListener.getNextView(), 0); 
            if (0 != childCnt) { 
                setInAnimation(getContext(), R.anim.slide_in_right); 
                setOutAnimation(getContext(), R.anim.slide_out_left); 
                setDisplayedChild(0); 
            } 
        } 
    } 
 
    @Override 
    public void flingToPrevious() { 
        if (null != mOnViewFlipperListener) { 
            int childCnt = getChildCount(); 
            if (childCnt == 2) { 
                removeViewAt(1); 
            } 
            addView(mOnViewFlipperListener.getPreviousView(), 0); 
            if (0 != childCnt) { 
                setInAnimation(getContext(), android.R.anim.slide_in_left); 
                setOutAnimation(getContext(), android.R.anim.slide_out_right); 
                setDisplayedChild(0); 
            } 
        } 
    } 
 
    public interface OnViewFlipperListener { 
        View getNextView(); 
 
        View getPreviousView(); 
    } 
} 