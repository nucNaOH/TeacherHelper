package cn.edu.nuc.mycallname.mainview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.util.AttributeSet;
import android.view.View;

class MyfaceView extends View {

	private int numberOfFace = 5;
	private FaceDetector myFaceDetect;
	private FaceDetector.Face[] myFace;
	float myEyesDistance;
	int numberOfFaceDetected;

	Bitmap myBitmap;

	public MyfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		myBitmap = PicModel.face;
		myFace = new FaceDetector.Face[numberOfFace];
		myFaceDetect = new FaceDetector(myBitmap.getWidth(),
				myBitmap.getHeight(), numberOfFace);
		numberOfFaceDetected = myFaceDetect.findFaces(myBitmap, myFace);
	}

	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		canvas.drawBitmap(myBitmap, 0, 0, null);

		Paint myPaint1 = new Paint();
		myPaint1.setColor(Color.GREEN);
		myPaint1.setStyle(Paint.Style.STROKE);
		myPaint1.setStrokeWidth(3);

		Paint myPaint2 = new Paint();
		myPaint2.setStyle(Paint.Style.FILL);
		myPaint2.setColor(Color.RED);

		for (int i = 0; i < numberOfFaceDetected; i++) {
			Face face = myFace[i];
			PointF myMidPoint = new PointF();
			face.getMidPoint(myMidPoint);
			myEyesDistance = face.eyesDistance();
//			canvas.drawRect((int) (myMidPoint.x - myEyesDistance * 1.5),
//					(int) (myMidPoint.y - myEyesDistance * 1.5),
//					(int) (myMidPoint.x + myEyesDistance * 1.7),
//					(int) (myMidPoint.y + myEyesDistance * 1.7), myPaint1);

			canvas.drawCircle(myMidPoint.x, myMidPoint.y, 3, myPaint2); // Ó¡ÌÃ

			myPaint2.setColor(Color.rgb(150, 181, 221));
			canvas.drawCircle(myMidPoint.x, myMidPoint.y + myEyesDistance
					/ 1.2f, 3, myPaint2); // ÈËÖÐ

			myPaint2.setColor(Color.rgb(51, 221, 137));  
			canvas.drawCircle(myMidPoint.x, myMidPoint.y + myEyesDistance
					/ 0.7f, 3, myPaint2); // ³Ð½¬

			myPaint2.setColor(Color.rgb(46, 117, 182));  
			canvas.drawCircle(myMidPoint.x - myEyesDistance / 2, myMidPoint.y
					+ myEyesDistance / 4, 3, myPaint2);
			canvas.drawCircle(myMidPoint.x + myEyesDistance / 2, myMidPoint.y
					+ myEyesDistance / 4, 3, myPaint2);// ³ÐÆü

			myPaint2.setColor(Color.rgb(255, 192, 0));    
			canvas.drawCircle(myMidPoint.x + myEyesDistance / 2,  myMidPoint.y
					+ myEyesDistance / 0.95f, 3, myPaint2); // µØ²Ö
			canvas.drawCircle(myMidPoint.x - myEyesDistance / 2, myMidPoint.y
					+ myEyesDistance / 0.95f, 3, myPaint2); // µØ²Ö

			myPaint2.setColor(Color.rgb(112, 48, 160));    
			canvas.drawCircle(myMidPoint.x - myEyesDistance / 2, myMidPoint.y
					- myEyesDistance / 1.8f, 3, myPaint2); // Ñô°×
			canvas.drawCircle(myMidPoint.x + myEyesDistance / 2, myMidPoint.y
					- myEyesDistance / 1.8f, 3, myPaint2); // Ñô°×

			myPaint2.setColor(Color.rgb(146, 208, 80));    
			canvas.drawCircle(myMidPoint.x - myEyesDistance / 2.5f,
					myMidPoint.y + myEyesDistance / 1.5f, 3, myPaint2); // Ó­Ïã
			canvas.drawCircle(myMidPoint.x + myEyesDistance / 2.5f,
					myMidPoint.y + myEyesDistance / 1.5f, 3, myPaint2); // Ó­Ïã
		}
	}
}