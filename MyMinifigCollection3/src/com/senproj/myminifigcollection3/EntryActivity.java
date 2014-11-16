package com.senproj.myminifigcollection3;

import java.io.IOException;

import com.senproj.myminifigcollection3.database.DatabaseHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class EntryActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		DatabaseHelper myDbHelper = new DatabaseHelper(EntryActivity.this);
		try {

			myDbHelper.createDataBase();
			Toast.makeText(EntryActivity.this, "Database creation successful", Toast.LENGTH_SHORT).show();
	        

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}
		        
	}

	/** Called when the user clicks the View Catalog button */
	public void ViewMainCategories(View view) {
		Intent intent = new Intent(getApplicationContext(), MainCategoryActivity.class);
		startActivity(intent);	    
	}

	/** Called when the user clicks the View Collection button */
	public void ViewCollectionMainCategories(View view) {
		Intent intent = new Intent(getApplicationContext(), MainCategoryActivity.class);
		startActivity(intent);	    //switch to collection method***************
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_entry,
					container, false);
			return rootView;
		}
	}
}
