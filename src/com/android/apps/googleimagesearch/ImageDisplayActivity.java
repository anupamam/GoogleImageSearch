package com.android.apps.googleimagesearch;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult result = (ImageResult)getIntent().getSerializableExtra("url");
		SmartImageView ivImage = (SmartImageView)findViewById(R.id.ivResult);
		ivImage.setImageUrl(result.getFullUrl());
	}
}
