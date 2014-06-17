package com.android.apps.googleimagesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ShareActionProvider;

import com.android.apps.googleimagesearch.UserPreferencesFragment.EditNameDialogListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

public class SearchActivity  extends FragmentActivity implements EditNameDialogListener {
	EditText etQuery;
	GridView gvImages; 
	Button btnSearch;
	int requestCode = 200;
	String query="";
	ArrayList<ImageResult> imageResults;
	ImageResultArrayAdapter imageAdapter;
	SmartImageView imageView;
	//user preferences
	UserPreference userPref;
	String imgtype="";
	String as_sitesearch="";
	String imgcolor="";
	String imgsz="";
	ShareActionProvider miShareAction;
	
	private static final String USER_PREFERENCE = "user_preference";

		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		imageResults = new ArrayList<ImageResult>();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		
		gvImages.setAdapter(imageAdapter);
		gvImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImageResult imageResult = imageResults.get(position);
				showImage(imageResult);
				
				setupShareIntent(imageResult);
			}
		
		});
		
		
		gvImages.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				getImages(page);
			}
		});
		
		
	}
	
	private void setUpViews(){
		gvImages=(GridView)findViewById(R.id.gvImages);
	}
	
	public void setupShareIntent(ImageResult result) {
	    // Fetch Bitmap Uri locally
		String url = result.getFullUrl();
	    	Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_TEXT, url);
	    shareIntent.setType("text/plain");
	    startActivity(Intent.createChooser(shareIntent,"Share using"));
	    // Attach share event to the menu item provider
	    
	}
	
		
	public void getImages(int page){
		AsyncHttpClient client = new AsyncHttpClient();
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

		client.get(url.toString(),new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject response){
				JSONArray imageJSONResults = null;
				try{
					imageJSONResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJSONResults));										
				}catch(Exception e){ 
					e.printStackTrace();
				}
			}
			
		});
	}

	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_preferences, menu);
       MenuItem searchItem = menu.findItem(R.id.action_search);
       SearchView searchView = (SearchView)searchItem.getActionView();
       
       MenuItem item = menu.findItem(R.id.menu_item_share);
        miShareAction = (ShareActionProvider) item.getActionProvider();
       
       searchView.setOnQueryTextListener(new OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String searchQuery) {
                // perform query here
        	   imageAdapter.clear();
        	   query = searchQuery;
        	   getImages(0);
        	   return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
       return super.onCreateOptionsMenu(menu);
       //return true;
   }
	
	
	public void onPreferences(MenuItem mi){
		showFilterDialog();
	}
	
	private void showFilterDialog() {
	      FragmentManager fm = getSupportFragmentManager();
	      UserPreferencesFragment editNameDialog = UserPreferencesFragment.newInstance("Image Filters");
	      editNameDialog.show(fm, "fragment_edit_name");
	  }
	
	@Override
	public void onFinishEditDialog(UserPreference userPref) {
		if(userPref !=null){
			imgtype = userPref.getType();
			as_sitesearch = userPref.getSiteFilter();
			imgcolor = userPref.getColorFilter();
			imgsz = userPref.getSize();
		}
		
	}
	
	private void showImage(ImageResult imageResult){
		 AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
		 LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		 View layout = inflater.inflate(R.layout.smart_image_fragment,(ViewGroup) findViewById(R.id.layout_image));
		 imageView = (SmartImageView) layout.findViewById(R.id.ivResult);
		 imageView.setImageUrl(imageResult.getFullUrl());
		 imageView.setImageDrawable(imageView.getDrawable());
		 imageDialog.setView(layout);
	     imageDialog.setCancelable(true);
	     imageDialog.setPositiveButton("ok", new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.dismiss();
	            }

	        });
	     imageDialog.create();
	     imageDialog.show();
	}
	

	


}
