package com.android.apps.googleimagesearch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PreferencesActivity extends Activity {

	EditText etSize;
	EditText etFilter;
	EditText etType;
	EditText etSiteFilter;
	Spinner spnSize;
	Spinner spnType;
	Spinner spnColor;
	UserPreference userPref;
	private static final String USER_PREFERENCE = "user_preference";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		setUpViews();
	}
	
	
	private void setUpViews(){
		etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
		spnSize = (Spinner)findViewById(R.id.spnSize);
		spnColor = (Spinner)findViewById(R.id.spnColor);
		spnType = (Spinner)findViewById(R.id.spnType);
	}
	

	public void onSave(View v){
		//check for null strings
		
		String size = spnSize.getSelectedItem().toString();
		String type = spnType.getSelectedItem().toString();
		String colorFilter = spnColor.getSelectedItem().toString();
		String siteFilter = etSiteFilter.getText().toString();
		userPref = new UserPreference(size, colorFilter, type, siteFilter);
		Intent data = new Intent();
		data.putExtra(USER_PREFERENCE, userPref);
		setResult(RESULT_OK, data);
		finish();
	}
}
