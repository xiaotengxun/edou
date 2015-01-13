package com.yc.view.tools;

import com.yc.log.LogUtil;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class HomeViewAnimation extends Animation {
	float centerX, centerY, centerZ;
	Camera camera = new Camera();
	int dureTime = 4000;

	public HomeViewAnimation() {
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		applyTransformationNew(interpolatedTime, t);
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		centerX = width / 2;
		centerY = height / 2;
		setDuration(dureTime);
		setRepeatCount(INFINITE);
		setFillAfter(true);
		setInterpolator(new LinearInterpolator());
		super.initialize(width, height, parentWidth, parentHeight);
	}

	private void applyTransformationNew(float interpolatedTime, Transformation t) {

		final Matrix matrix = t.getMatrix();
		camera.save();
		int deg = (int) (180 * interpolatedTime);
		if (deg <= 45 && deg >= 0) {
			camera.rotateY(deg);
		} else if (deg > 45 && deg <= 90) {
			deg = 90 - deg;
			camera.rotateY(deg);
		} else if (deg > 90 && deg <= 135) {
			deg = 90 - deg;
			camera.rotateY(deg);
		} else if (deg > 135 && deg <= 180) {
			deg = deg - 180;
			camera.rotateY(deg);
		}
		camera.getMatrix(matrix);
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
		camera.restore();
	}

}
