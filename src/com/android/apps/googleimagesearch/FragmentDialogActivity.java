package com.android.apps.googleimagesearch;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class FragmentDialogActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_dialog);
		 showEditDialog();
	}
	
	private void showEditDialog() {
	      FragmentManager fm = getSupportFragmentManager();
	      UserPreferencesFragment editNameDialog = UserPreferencesFragment.newInstance("Filter Search");
	      editNameDialog.show(fm, "fragment_edit_name");
	  }
}
