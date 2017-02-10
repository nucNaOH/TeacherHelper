package cn.edu.nuc.mycallname.mainview;

import com.teacher.helper.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class TurnplateView extends View implements  OnTouchListener{
	
	

	private OnTurnplateListener onTurnplateListener;
	public void setOnTurnplateListener(OnTurnplateListener onTurnplateListener) {
		this.onTurnplateListener = onTurnplateListener;
	}
	/**
	 * 画笔：点、线
	 */
	private Paint mPaint = new Paint();
	/**
	 * 画笔：圆
	 */
	private Paint paintCircle =  new Paint();
	/**
	 * 图标列表
	 */
	private Bitmap[] icons = new Bitmap[10];
	/**
	 * point列表
	 */
	private Point[] points;
	/**
	 * 数目
	 */
	private static final int PONIT_NUM = 6;
	
	/**
	 * 圆心坐标
	 */
	private int mPointX=0, mPointY=0;
	/**
	 * 半径
	 */
	private int mRadius = 0;
	/**
	 * 每两个点间隔的角度
	 */
	private int mDegreeDelta;
	/**
	 * 每次转动的角度差
	 */
	private int tempDegree = 0;
	/**
	 * 选中的图标标识 999：未选中任何图标
	 */
	private int chooseBtn=999;
	private Matrix mMatrix = new Matrix();  
	public TurnplateView(Context context, int px, int py, int radius) {
		super(context);		
		mPaint.setColor(Color.parseColor("#66FFFFFF"));
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Paint.Style.STROKE);
		paintCircle.setAntiAlias(true);
		paintCircle.setColor(Color.TRANSPARENT);
		loadIcons();
		mPointX = px;
		mPointY = py;
		mRadius = radius;
		
		initPoints();
		computeCoordinates();
	}
	
	/**
	 * 
	 * 方法名：loadBitmaps 
	 * 功能：装载图片
	 * 参数：
	 * @param key
	 * @param d
	 * 创建人：huanghsh  
	 * 创建时间：2011-11-28
	 */
	public void loadBitmaps(int key,Drawable d){
		Bitmap bitmap = Bitmap.createBitmap(60,72,Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		d.setBounds(0, 0, 60, 72);
		d.draw(canvas);
		icons[key]=bitmap;
	}

	public void loadIcons(){
		Resources r = getResources();	
		loadBitmaps(0, r.getDrawable(R.drawable.lit_bmp_index_one));
		loadBitmaps(1, r.getDrawable(R.drawable.lit_bmp_index_two));
		loadBitmaps(2, r.getDrawable(R.drawable.lit_bmp_index_three));
		loadBitmaps(3, r.getDrawable(R.drawable.lit_bmp_index_four));
		loadBitmaps(4, r.getDrawable(R.drawable.lit_bmp_index_five));
		loadBitmaps(5, r.getDrawable(R.drawable.lit_bmp_index_six));
//		loadBitmaps(6, r.getDrawable(R.drawable.lit_bmp_index_seven));
//		loadBitmaps(7, r.getDrawable(R.drawable.lit_bmp_index_eight));
	}

	
	/**
	 * 
	 * 方法名：initPoints 
	 * 功能：初始化每个点
	 * 参数：
	 * 创建人：huanghsh  
	 * 创建时间：2011-11-28
	 */
	 
	private void initPoints() {
		points = new Point[PONIT_NUM];
		Point point;
		int angle = 0;
		mDegreeDelta = 360/PONIT_NUM;
		
		for(int index=0; index<PONIT_NUM; index++) {
			point = new Point();
			point.angle = angle;
			angle += mDegreeDelta;
			point.bitmap = icons[index];
			point.flag=index;
			points[index] = point;
			
		}
	}
	
	/**
	 * 
	 * 方法名：resetPointAngle 
	 * 功能：重新计算每个点的角度
	 * 参数：
	 * @param x
	 * @param y
	 * 创建人：huanghsh  
	 * 创建时间：2011-11-28
	 */	
	private void resetPointAngle(float x, float y) {
		int degree = computeMigrationAngle(x, y);
		for(int index=0; index<PONIT_NUM; index++) {			
			points[index].angle += degree;		
			if(points[index].angle>360){
				points[index].angle -=360;
			}else if(points[index].angle<0){
				points[index].angle +=360;
			}
			
		}
	}
	

	private void computeCoordinates() {
		Point point;
		for(int index=0; index<PONIT_NUM; index++) {
			point = points[index];
			point.x = mPointX+ (float)(mRadius * Math.cos(point.angle*Math.PI/180));
			point.y = mPointY+ (float)(mRadius * Math.sin(point.angle*Math.PI/180));	
			point.x_c = mPointX+(point.x-mPointX)/2;
			point.y_c = mPointY+(point.y-mPointY)/2;
			//Log.e(TAG, point.angle+"");
		}
	}
	
	private int computeMigrationAngle(float x, float y) {
		int a=0;
		float distance = (float)Math.sqrt(((x-mPointX)*(x-mPointX) + (y-mPointY)*(y-mPointY)));
		int degree = (int)(Math.acos((x-mPointX)/distance)*180/Math.PI);
		if(y < mPointY) {
			degree = -degree;
		}	
		if(tempDegree!=0){
			a = degree - tempDegree;
		}
		tempDegree=degree;		
		return a;
	}
	
	private void computeCurrentDistance(float x, float y) {
		for(Point point:points){
			float distance = (float)Math.sqrt(((x-point.x)*(x-point.x) + (y-point.y)*(y-point.y)));			
			if(distance<31){
				chooseBtn =  point.flag;
				break;
			}else{
				chooseBtn = 999;
			}
		}	
	}
	
	private void switchScreen(MotionEvent event){
		computeCurrentDistance(event.getX(), event.getY());
		//Log.e(TAG,chooseBtn+"");	
		onTurnplateListener.onPointTouch(chooseBtn);
		
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {		
		 int action = event.getAction();
	        switch (action) {
	        case MotionEvent.ACTION_DOWN:
	            break;
	        case MotionEvent.ACTION_MOVE:
	        	resetPointAngle(event.getX(), event.getY());
	    		computeCoordinates();
	    		invalidate();
	            break;
	        case MotionEvent.ACTION_UP:
	        	switchScreen(event);
	        	tempDegree = 0;
	        	invalidate();
	            break;
	        case MotionEvent.ACTION_CANCEL:
	        	//系统在运行到一定程度下无法继续响应你的后续动作时会产生此事件。
	        	//一般仅在代码中将其视为异常分支情况处理
	            break;
	        }
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawCircle(mPointX, mPointY, mRadius+2, mPaint);
//		canvas.drawPoint(mPointX, mPointY, mPaint);
		
		for(int index=0; index<PONIT_NUM; index++) {
//			canvas.drawPoint(points[index].x_c, points[index].y_c, mPaint);
			drawInCenter(canvas, points[index].bitmap, points[index].x, points[index].y,points[index].flag);
		}
		
		
	}
	
	void drawInCenter(Canvas canvas, Bitmap bitmap, float left, float top,int flag) {
		canvas.drawPoint(left, top, mPaint);
		if(chooseBtn==flag){
			//Log.e("Width", bitmap.getWidth()+";"+70f/bitmap.getWidth());
			//Log.e("Height", bitmap.getHeight()+";"+70f/bitmap.getHeight());
			mMatrix.setScale(60f/bitmap.getWidth(), 72f/bitmap.getHeight());   
			mMatrix.postTranslate(left-35, top-35);  
			canvas.drawBitmap(bitmap, mMatrix, null); 
		}else{
			canvas.drawBitmap(bitmap, left-bitmap.getWidth()/2, top-bitmap.getHeight()/2, null);
		}
		
		
	}	
	
	class Point {
		
		/**
		 * 位置标识
		 */
		int flag;
		/**
		 * 图片
		 */
		Bitmap bitmap;
		
		/**
		 * 角度
		 */
		int angle;
		
		/**
		 * x坐标
		 */
		float x;
		
		/**
		 * y坐标
		 */
		float y;
		
		/**
		 * 点与圆心的中心x坐标
		 */
		float x_c;
		/**
		 * 点与圆心的中心y坐标
		 */
		float y_c;
	}

	 public static interface OnTurnplateListener {
	       
	     public void onPointTouch(int flag);
	       	             	        
	 }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return false;
	}
	
	
}
