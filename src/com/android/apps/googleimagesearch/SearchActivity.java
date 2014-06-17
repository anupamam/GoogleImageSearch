package com.android.apps.googleimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
<<<<<<< HEAD
=======
import org.lucasr.smoothie.ItemManager;
>>>>>>> 3e3baec031574fd583dfc4f3718e678e0f46dcfc

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.Menu;
import android.view.MenuItem;
=======
>>>>>>> 3e3baec031574fd583dfc4f3718e678e0f46dcfc
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

<<<<<<< HEAD

=======
>>>>>>> 3e3baec031574fd583dfc4f3718e678e0f46dcfc
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvImages; 
	Button btnSearch;
<<<<<<< HEAD
	int requestCode = 200;
	ArrayList<ImageResult> imageResults;
	ImageResultArrayAdapter imageAdapter;
	//user preferences
	UserPreference userPref;
	String imgtype="";
	String as_sitesearch="";
	String imgcolor="";
	String imgsz="";
	
	private static final String USER_PREFERENCE = "user_preference";
=======
	ArrayList<ImageResult> imageResults;
	ImageResultArrayAdapter imageAdapter;
>>>>>>> 3e3baec031574fd583dfc4f3718e678e0f46dcfc
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		imageResults = new ArrayList<ImageResult>();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		imageAdapter.clear();
		imageResults.clear();
		gvImages.setAdapter(imageAdapter);
		gvImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("url", imageResult);
				startActivity(i);
				
			}
		
		});
	
		gvImages.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				//call async request to load more data ---smoothie
				
				getImages(page);
			}
		});
	}
	
	private void setUpViews(){
		etQuery=(EditText)findViewById(R.id.etQuery);
		gvImages=(GridView)findViewById(R.id.gvImages);
		btnSearch=(Button)findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v){
		imageAdapter.clear();
		getImages(0);
	}
	
	public void getImages(int page){
		String query = etQuery.getText().toString();
		AsyncHttpClient client = new AsyncHttpClient();
		
<<<<<<< HEAD
		StringBuilder url =  new StringBuilder();
		url.append("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" + (page*8) + "&v=1.0&q=" + Uri.encode(query));
		if(imgtype.length()>0)
			url.append("&imgtype=" + imgtype);
		if(as_sitesearch.length()>0)
			url.append("&as_sitesearch="+ as_sitesearch);
		if(imgcolor.length()>0)
			url.append("&imgcolor="+ imgcolor);
		if(imgsz.length()>0)
			url.append("&imgsz="+ imgsz);

		//Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
		
		client.get(url.toString(),new JsonHttpResponseHandler(){
=======
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+ "start="+ (page*8) +"&v=1.0&q=" + Uri.encode(query), 
				new JsonHttpResponseHandler(){
>>>>>>> 3e3baec031574fd583dfc4f3718e678e0f46dcfc
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJSONResults = null;
				try{
					imageJSONResults = response.getJSONObject("responseData").getJSONArray("results");
					//
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJSONResults));										
				}catch(Exception e){ 
					e.printStackTrace();
				}
			}
			
		});
	}
<<<<<<< HEAD
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_preferences, menu);
       return true;
   }
	
	
	public void onPreferences(MenuItem mi){
		//Toast.makeText(this, "Preferences!", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, PreferencesActivity.class);
		//get User Preferences
		
		startActivityForResult(intent, requestCode);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Toast.makeText(getApplicationContext(), "GotActivityResult", Toast.LENGTH_LONG).show();
		if (resultCode == RESULT_OK && requestCode == 200) {
			userPref = (UserPreference)data.getSerializableExtra(USER_PREFERENCE);
			if(userPref !=null){
				imgtype = userPref.getType();
				as_sitesearch = userPref.getSiteFilter();
				imgcolor = userPref.getColorFilter();
				imgsz = userPref.getSize();
				//Toast.makeText(getApplicationContext(), imgsz, Toast.LENGTH_LONG).show();
			}
			
		}
		
		
		
	}
=======
>>>>>>> 3e3baec031574fd583dfc4f3718e678e0f46dcfc
}
