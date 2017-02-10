package cn.edu.nuc.mycallname.mainview;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PicModel {
	public static Bitmap face;
	public static Bitmap newface;
	public static int surfacewidth;
	public static int surfaceheight;

	public Bitmap getFace() {
		
//		int oldWidth = face.getWidth();
//		int oldHeight = face.getHeight();
//		float scaleWidth, scaleHeight;
//
//		scaleWidth = oldWidth / width;
//		scaleHeight = oldHeight / height;
//		Matrix matrix = new Matrix();
//		matrix.postScale(scaleWidth, scaleHeight);
//
//		Bitmap newface = Bitmap.createBitmap(face, 0, 0, oldWidth, oldHeight,
//				matrix, true);
//		PicModel.newface = face;
		return face;
	}

	public void setFace(Bitmap face) {
	  
		PicModel.face = face;
		
	}
}
