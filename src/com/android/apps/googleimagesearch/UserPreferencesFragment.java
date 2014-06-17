package com.android.apps.googleimagesearch;



import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UserPreferencesFragment extends DialogFragment {
	EditText etSize;
	EditText etFilter;
	EditText etType;
	EditText etSiteFilter;
	Spinner spnSize;
	Spinner spnType;
	Spinner spnColor;
	Button btnDone;
	UserPreference userPref;
	private static final String USER_PREFERENCE = "user_preference";

	public interface EditNameDialogListener {
        void onFinishEditDialog(UserPreference userPref);
    }
	
	public UserPreferencesFragment() {
		// Empty constructor required for DialogFragment
	}
	
	public static UserPreferencesFragment newInstance(String title) {
		UserPreferencesFragment frag = new UserPreferencesFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_user_preferences, container);
		String title = getArguments().getString("title", "Enter Name");
		getDialog().setTitle(title);
		// Show soft keyboard automatically
		setUpViews(view);
		
		btnDone = (Button)view.findViewById(R.id.btSave);
		btnDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditNameDialogListener listener = (EditNameDialogListener) getActivity();
				String size = spnSize.getSelectedItem().toString();
				String type = spnType.getSelectedItem().toString();
				String colorFilter = spnColor.getSelectedItem().toString();
				String siteFilter = etSiteFilter.getText().toString();
				userPref = new UserPreference(size, colorFilter, type, siteFilter);
				listener.onFinishEditDialog(userPref);
		        dismiss();
			}
		});
		getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}
	
	private void setUpViews(View view){
		etSiteFilter = (EditText)view.findViewById(R.id.etSiteFilter);
		spnSize = (Spinner)view.findViewById(R.id.spnSize);
		spnColor = (Spinner)view.findViewById(R.id.spnColor);
		spnType = (Spinner)view.findViewById(R.id.spnType);
	}
	
	
}
