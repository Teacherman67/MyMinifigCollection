package com.senproj.myminifigcollection3;

import com.senproj.myminifigcollection3.database.CategoryContent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;

public class FigList extends ActionBarActivity {
	
	/**
	 * The argument representing the selected minifigure list and specific figure that this Activity represents
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public static final String ARG_ITEM_LEVEL = "level"; //category level intent request comes from
	public static final String ARG_SELECTED_NAME = "selectedCategoryName";  // selected main category
	public static final String ARG_SEARCH_TABLE = "foundInTable";
	
	ListView listviewminis;
	
	//an instance for item selected of the selected Main Category MAIN_ITEM
	private MinifigureContent.MinifigListItem mItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fig_list);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		//retrieve extra data from intent
		Intent extras = getIntent();
		if (extras == null) {
			return;
		}
		// get data via the key
		String parentCat = extras.getStringExtra(ARG_SELECTED_NAME);
		String table = extras.getStringExtra(ARG_SEARCH_TABLE);
		String level = extras.getStringExtra(ARG_ITEM_LEVEL);
		if (parentCat != null) {
			//call to initialize CategoryContent ITEMS (Main Category List) from database
			MinifigureContent.setPrimaryMinifigureListContext(this, parentCat, table, level);
		}
		
		// Get ListView object from xml
		listviewminis = (ListView) findViewById(R.id.mainminifiglist);
		
		/***** set list adapters*********************/
		// Minifigure listing List adapter.
		final ArrayAdapter<MinifigureContent.MinifigListItem> figadapter = new ArrayAdapter<MinifigureContent.MinifigListItem>(this,
				android.R.layout.simple_list_item_activated_1, android.R.id.text1, MinifigureContent.MINI_LIST_ITEMS);
		listviewminis.setAdapter(figadapter);
		
		/***** set onItemClickListeners for listviews*********************/
		
		listviewminis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				String selectedId = Long.toString(id);
				//get Main CategoryID from passed id Main_Item_Map
				mItem = (MinifigureContent.MinifigListItem) figadapter.getItem(position);
				
				Intent figDetailIntent = new Intent(FigList.this, FigDetailActivity.class);
				figDetailIntent.putExtra(FigDetailActivity.ARG_ITEM_ID, selectedId);
				figDetailIntent.putExtra(FigDetailActivity.ARG_SELECTED_FIGID, mItem.figID);
				figDetailIntent.putExtra(FigDetailActivity.ARG_SELECTED_BLFIGID, mItem.blfigID);
				startActivity(figDetailIntent); 
			}

			});
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fig_list, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_fig_list,
					container, false);
			return rootView;
		}
	}
}
