package com.senproj.myminifigcollection3;

import com.senproj.myminifigcollection3.database.MinifigureContent;

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
import android.widget.TextView;
import android.os.Build;

public class FigDetailActivity extends ActionBarActivity {
	
	/**
	 * The argument representing the selected Minifigure List Item ID that this Activity represents
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public static final String ARG_SELECTED_FIGID = "figID"; //selected figures's app id
	public static final String ARG_SELECTED_BLFIGID = "bricklinkID"; //selected figures bricklink id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fig_detail);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		final TextView tvFigID = (TextView)findViewById(R.id.FigID_figDetail);
		final TextView tvBLFigID = (TextView)findViewById(R.id.BLFigID_figDetail);
		
		//retrieve extra data from intent
				Intent extras = getIntent();
				if (extras == null) {
					return;
				}
				// get data via the key
				String FigID = extras.getStringExtra(ARG_SELECTED_FIGID);
				String BLFigID = extras.getStringExtra(ARG_SELECTED_BLFIGID);
				if (FigID != null) {
					tvFigID.setText(FigID);
					tvBLFigID.setText(BLFigID);
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fig_detail, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_fig_detail,
					container, false);
			return rootView;
		}
	}
}
