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
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainCategoryActivity extends ActionBarActivity {

	ListView listview;

	//an instance for item selected of the selected Main Category MAIN_ITEM
	private CategoryContent.MainCategoryItem mItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_category);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}


		// Get ListView object from xml
		listview = (ListView) findViewById(R.id.maincategorylist);

		//call to initialize CategoryContent ITEMS (Main Category List) from database
		CategoryContent.setMainCategoryContext(this);

		// List adapter.
		final ArrayAdapter<CategoryContent.MainCategoryItem> adapter = new ArrayAdapter<CategoryContent.MainCategoryItem>(this,
				android.R.layout.simple_list_item_activated_1, android.R.id.text1, CategoryContent.MAIN_ITEMS);
		listview.setAdapter(adapter);


		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				String selectedId = Long.toString(id);
				//get Main CategoryID from passed id Main_Item_Map
				mItem = (CategoryContent.MainCategoryItem) adapter.getItem(position);
				//start the primary sub category activity for the selected item ID
				int sub = Integer.valueOf(mItem.hasPSub);
				switch (sub) {
				case 0:
					//no sub categories
					Intent figListIntent = new Intent(MainCategoryActivity.this, FigList.class);
					figListIntent.putExtra(FigList.ARG_ITEM_ID, selectedId);
					figListIntent.putExtra(FigList.ARG_ITEM_LEVEL, "Main");
					figListIntent.putExtra(FigList.ARG_SELECTED_NAME, mItem.categoryName);
					figListIntent.putExtra(FigList.ARG_SEARCH_TABLE, mItem.foundInTable);
					startActivity(figListIntent);      
					//finish();
					break;

				case 1:
					Intent prisubIntent = new Intent(MainCategoryActivity.this, PriSubCatActivity.class);
					prisubIntent.putExtra(PriSubCatActivity.ARG_ITEM_ID, selectedId);
					prisubIntent.putExtra(PriSubCatActivity.ARG_SELECTED_NAME, mItem.categoryName);
					startActivity(prisubIntent);      
					//finish();
					break;
				}
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_category, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main_category,
					container, false);
			return rootView;
		}
	}
}
