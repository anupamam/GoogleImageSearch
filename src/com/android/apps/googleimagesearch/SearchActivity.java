package com.android.apps.googleimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lucasr.smoothie.ItemManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvImages; 
	Button btnSearch;
	ArrayList<ImageResult> imageResults;
	ImageResultArrayAdapter imageAdapter;
	
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
		
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+ "start="+ (page*8) +"&v=1.0&q=" + Uri.encode(query), 
				new JsonHttpResponseHandler(){
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
}
