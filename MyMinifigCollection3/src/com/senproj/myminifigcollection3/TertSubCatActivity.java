package com.senproj.myminifigcollection3;

import com.senproj.myminifigcollection3.database.CategoryContent;

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

public class TertSubCatActivity extends ActionBarActivity {

	/**
	 * The argument representing the selected Secondary Sub-category Item ID that this Activity represents
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public static final String ARG_SELECTED_NAME = "selectedCategoryName"; //secondary sub-category
	public static final String ARG_SELECTED_MAIN = "selectedMainCategory"; //main category

	ListView listviewtertsubs;
	ListView listviewminis;
	
	//an instance for item selected of the selected Main Category MAIN_ITEM
		private CategoryContent.TertiarySubItem mItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tert_sub_cat);
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
		String mainCat = extras.getStringExtra(ARG_SELECTED_MAIN);
		if (parentCat != null) {
			//call to initialize CategoryContent ITEMS (Main Category List) from database
			CategoryContent.setTertiarySubContext(this, parentCat, mainCat);
		}

		// Get ListView object from xml
		listviewtertsubs = (ListView) findViewById(R.id.tertsubcategorylist);


		// List adapter.
		final ArrayAdapter<CategoryContent.TertiarySubItem> adapter = new ArrayAdapter<CategoryContent.TertiarySubItem>(this,
				android.R.layout.simple_list_item_activated_1, android.R.id.text1, CategoryContent.TSUB_ITEMS);
		listviewtertsubs.setAdapter(adapter);
		
		listviewtertsubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			String selectedId = Long.toString(id);
			//get Main CategoryID from passed id Main_Item_Map
			mItem = (CategoryContent.TertiarySubItem) adapter.getItem(position);
			
			Intent figListIntent = new Intent(TertSubCatActivity.this, FigList.class);
			figListIntent.putExtra(FigList.ARG_ITEM_ID, selectedId);
			figListIntent.putExtra(FigList.ARG_ITEM_LEVEL, "Tertiary");
			figListIntent.putExtra(FigList.ARG_SELECTED_NAME, mItem.categoryName);
			figListIntent.putExtra(FigList.ARG_SELECTED_NAME, mItem.tertiarySub);
			figListIntent.putExtra(FigList.ARG_SEARCH_TABLE, mItem.foundInTable);
			startActivity(figListIntent); 
		}

		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tert_sub_cat, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_tert_sub_cat,
					container, false);
			return rootView;
		}
	}
}
