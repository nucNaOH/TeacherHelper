package cn.edu.nuc.mycallname.mainview;

import com.teacher.helper.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Canvas myCanvas;
	private Paint myPaint;
	private Thread myThread;
	private boolean threadFlag;
	private Bitmap bitmap1,bitmap2;
    private int m=0;
	private float bmapW, bmapH;//bitmap的宽度和高度
	private float bmapX, bmapY;//bitmap的坐标
	private float screenH;//屏幕宽度和高度
	

	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		myPaint = new Paint();
		myPaint.setColor(Color.WHITE);
		setFocusable(true);
	}

	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			myDraw();
			myLogic();
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;
			try {
				if (runTime < 50) {
					Thread.sleep(50 - runTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
             m++;
		}

	}

	public void myDraw() {
		try {
			myCanvas = sfh.lockCanvas();
			myCanvas.drawRGB(255, 255, 255);
			myCanvas.drawBitmap(bitmap1, bmapX, bmapY, myPaint);
			myCanvas.drawBitmap(bitmap2, bmapX+bmapW-0.5f, bmapY, myPaint);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sfh.unlockCanvasAndPost(myCanvas);
		}

	}

	public void myLogic() {
		
		bmapX-=4;
		if(bmapX<-bmapW){
			bmapX=0;
		}
		
		

	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {
		threadFlag = true;
		myThread = new Thread(this);
		myThread.start();
		if(m>100){
			myThread.stop();
		}
		bitmap1 = BitmapFactory.decodeResource(this.getResources(),
			R.drawable.lit_bg);
		bitmap2 = bitmap1;
		System.out.println("输出"+bitmap2.getWidth());
		bmapW = bitmap1.getWidth();
		bmapH = bitmap1.getHeight();
		this.getWidth();
		screenH = this.getHeight();
		bmapY = screenH - bmapH;//获取bitmap Y坐标

	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		threadFlag = false;
		bitmap1.recycle();
	}

}
