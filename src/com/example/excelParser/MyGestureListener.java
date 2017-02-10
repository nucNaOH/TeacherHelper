package com.example.excelParser;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyGestureListener extends SimpleOnGestureListener {

	public OnFlingListener mOnFlingListener;

	public OnFlingListener getOnFlingListener() {
		return mOnFlingListener;
	}

	public void setOnFlingListener(OnFlingListener mOnFlingListener) {
		this.mOnFlingListener = mOnFlingListener;
	}

	@Override
	public final boolean onFling(final MotionEvent e1, final MotionEvent e2,
			final float speedX, final float speedY) {
		if (mOnFlingListener == null) {
			return super.onFling(e1, e2, speedX, speedY);
		}

		float XFrom = e1.getX();
		float XTo = e2.getX();
		float YFrom = e1.getY();
		float YTo = e2.getY();
		// ���һ�����X����ȴ���100������X�᷽����ٶȴ���100
		if (Math.abs(XFrom - XTo) > 70.0f && Math.abs(speedX) > 70.0f) {
			// X����ȴ���Y��ķ���
			if (Math.abs(XFrom - XTo) >= Math.abs(YFrom - YTo)) {
				if (XFrom > XTo) {
					// ��һ��
					// mOnFlingListener.flingToNext();
				} else {
					// ��һ��
					mOnFlingListener.flingToPrevious();
				}
			}
		} else {
			return false;
		}
		return true;
	}

	public interface OnFlingListener {
		void flingToNext();

		void flingToPrevious();
	}
}