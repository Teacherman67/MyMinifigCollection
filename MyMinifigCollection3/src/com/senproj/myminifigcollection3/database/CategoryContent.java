package com.senproj.myminifigcollection3.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.senproj.myminifigcollection3.database.DatabaseHelper;
import com.senproj.myminifigcollection3.database.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class CategoryContent {


	/**
	 * Arrays of category items.
	 */
	public static List<MainCategoryItem> MAIN_ITEMS = new ArrayList<MainCategoryItem>();
	public static List<PrimarySubItem> PSUB_ITEMS = new ArrayList<PrimarySubItem>();
	public static List<SecondarySubItem> SSUB_ITEMS = new ArrayList<SecondarySubItem>();
	public static List<TertiarySubItem> TSUB_ITEMS = new ArrayList<TertiarySubItem>();

	/**
	 * Maps of category items, by ID.
	 */
	public static Map<String, MainCategoryItem> MAIN_ITEM_MAP = new HashMap<String, MainCategoryItem>();
	public static Map<String, PrimarySubItem> PSUB_ITEM_MAP = new HashMap<String, PrimarySubItem>();
	public static Map<String, SecondarySubItem> SSUB_ITEM_MAP = new HashMap<String, SecondarySubItem>();
	public static Map<String, TertiarySubItem> TSUB_ITEM_MAP = new HashMap<String, TertiarySubItem>();


	/**********************handle Main Category Calls****************************/

	public static void setMainCategoryContext(Context c) {
		
		MAIN_ITEMS.clear();
		MAIN_ITEM_MAP.clear();
		DatabaseHelper myDbHelper = new DatabaseHelper(c); // SQLiteOpenHelper + SQLiteDatabase manager
		try {   //used this for testing	           
			myDbHelper.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}
		String[] cols = new String[] {Database.Categories._ID, Database.Categories.CATEGORY_ID, Database.Categories.MAIN_CATEGORY,
				 Database.Categories.LOCATION, Database.Categories.HAS_P_SUB};
		//Cursor cur = myDbHelper.queryMain("tblMainCategories", null, null, null, null, null, null); // database query
		Cursor cur = myDbHelper.queryCategory(true, Database.Categories.TABLE_NAME, cols, null, null,
				Database.Categories.MAIN_CATEGORY, null, Database.Categories.MAIN_CATEGORY, null);
		if (cur.moveToFirst()) {
			do {
				MainCategoryItem item = new MainCategoryItem(cur.getString(0), cur.getString(1), cur.getString(2),
						cur.getString(3), myDbHelper.queryMainCount(cur.getString(3)), cur.getString(4));
				addItem(item);
			} while (cur.moveToNext());
		}
		
		myDbHelper.close();
	}


	private static void addItem(MainCategoryItem item) {
		MAIN_ITEMS.add(item);
		MAIN_ITEM_MAP.put(item.id, item);
	}

	/**
	 * A Main Category listing item representing a main category detail item.
	 */
	public static class MainCategoryItem {
		public String id;
		public String categoryID;
		public String categoryName;
		public String foundInTable;
		public String count;
		public String hasPSub;

		public MainCategoryItem(String id, String categoryID, String categoryName, String foundInTable, String count, String hasPSub) {
			this.id = id;
			this.categoryID = categoryID;
			this.categoryName = categoryName;
			this.foundInTable = foundInTable;
			this.count = count;
			this.hasPSub = hasPSub;
		}
		@Override
		public String toString() {
			String output = categoryName + ": " + hasPSub; //set this to desired output before pub
			return output;
		}
	}
		
		
		
		/********************handle Primary Sub Category calls**************************/
		
		private static void addPrimarySubItem(PrimarySubItem item) {
			PSUB_ITEMS.add(item);
			PSUB_ITEM_MAP.put(item.id, item);
		}
		
		public static void setPrimarySubContext(Context c, String parentCat) {
			PSUB_ITEMS.clear();
			PSUB_ITEM_MAP.clear();
			DatabaseHelper myDbHelper = new DatabaseHelper(c); // SQLiteOpenHelper + SQLiteDatabase manager
			try {   //used this for testing	           
				myDbHelper.openDataBase();
			}catch(SQLException sqle){
				throw sqle;
			}
			
			String[] cols = new String[] {Database.Categories._ID, Database.Categories.CATEGORY_ID, Database.Categories.MAIN_CATEGORY,
					Database.Categories.PRIMARY_SUB, Database.Categories.LOCATION, Database.Categories.HAS_S_SUB};
			String selection = Database.Categories.MAIN_CATEGORY + "=? and " + Database.Categories.PRIMARY_SUB + " IS NOT NULL";
			String[] selectionArgs = new String[] {parentCat};
			Cursor cur = myDbHelper.queryCategory(true, Database.Categories.TABLE_NAME, cols, selection, selectionArgs,
					Database.Categories.PRIMARY_SUB, null, Database.Categories.PRIMARY_SUB, null); // database query
			
			//add View All option
			String viewalltext = "View All of " + parentCat + "including subs";
			PrimarySubItem viewallitem = new PrimarySubItem("8888", "8888", viewalltext, viewalltext, "blank", "0");
			addPrimarySubItem(viewallitem);
			//add View only option
			String viewonlytext = "View Only from  " + parentCat + "with no subs";
			PrimarySubItem viewonlyitem = new PrimarySubItem("9999", "9999", viewonlytext, viewonlytext, "blank", "0");
			addPrimarySubItem(viewonlyitem);
			
			if (cur.moveToFirst()) {
				do {
					PrimarySubItem subitem = new PrimarySubItem(cur.getString(0), cur.getString(1), cur.getString(2),
							cur.getString(3), cur.getString(4), cur.getString(5));
					addPrimarySubItem(subitem);
				} while (cur.moveToNext());
			}
			
						
			myDbHelper.close();
		}
		
		/**
		 * A PrimarySubItem representing a primary sub category of a main category.
		 */
		public static class PrimarySubItem {
			public String id;
			public String categoryID;
			public String categoryName;
			public String primarySub;
			public String foundInTable;
			public String hasSSub;

			public PrimarySubItem(String id, String categoryID, String categoryName, String primary, String foundInTable, String hasSSub) {
				this.id = id;
				this.categoryID = categoryID;
				this.categoryName = categoryName;
				this.primarySub = primary;
				this.foundInTable = foundInTable;
				this.hasSSub = hasSSub;
			}

		@Override
		public String toString() {
			if(categoryName == "View All"){
				return primarySub;
			} else
			return primarySub + ": " + hasSSub;
		}
	}
	
		/********************handle Secondary Sub Category calls**************************/
		
		private static void addSecondarySubItem(SecondarySubItem item) {
			SSUB_ITEMS.add(item);
			SSUB_ITEM_MAP.put(item.id, item);
		}
		
		public static void setSecondarySubContext(Context c, String parentCat, String mainCat) {
			SSUB_ITEMS.clear();
			SSUB_ITEM_MAP.clear();
			DatabaseHelper myDbHelper = new DatabaseHelper(c); // SQLiteOpenHelper + SQLiteDatabase manager
			try {   //used this for testing	           
				myDbHelper.openDataBase();
			}catch(SQLException sqle){
				throw sqle;
			}
			
			String[] cols = new String[] {Database.Categories._ID, Database.Categories.CATEGORY_ID, Database.Categories.MAIN_CATEGORY,
					Database.Categories.SECONDARY_SUB, Database.Categories.LOCATION, Database.Categories.HAS_T_SUB};
			String selection = Database.Categories.MAIN_CATEGORY + "=? and " + Database.Categories.PRIMARY_SUB + "=? and " +
					Database.Categories.SECONDARY_SUB + " IS NOT NULL";
			String[] selectionArgs = new String[] {mainCat, parentCat};
			Cursor cur = myDbHelper.queryCategory(true, Database.Categories.TABLE_NAME, cols, selection, selectionArgs,
					Database.Categories.SECONDARY_SUB, null, Database.Categories.SECONDARY_SUB, null); // database query
			
			//add View All option
			String viewalltext = "View All of " + parentCat + "including subs";
			SecondarySubItem viewallitem = new SecondarySubItem("8888", "8888", viewalltext, viewalltext, "blank", "0");
			addSecondarySubItem(viewallitem);
			//add View only option
			String viewonlytext = "View Only from  " + parentCat + "with no subs";
			SecondarySubItem viewonlyitem = new SecondarySubItem("9999", "9999", viewonlytext, viewonlytext, "blank", "0");
			addSecondarySubItem(viewonlyitem);			
			
			if (cur.moveToFirst()) {
				do {
					SecondarySubItem secsubitem = new SecondarySubItem(cur.getString(0), cur.getString(1), cur.getString(2),
							cur.getString(3), cur.getString(4), cur.getString(5));
					addSecondarySubItem(secsubitem);
				} while (cur.moveToNext());
			}
			
						
			myDbHelper.close();
		}
		
		/**
		 * A SecondarySubItem representing a secondary sub category of a main category/primary sub category.
		 */
		public static class SecondarySubItem {
			public String id;
			public String categoryID;
			public String categoryName;
			public String secondarySub;
			public String foundInTable;
			public String hasTSub;

			public SecondarySubItem(String id, String categoryID, String categoryName, String secondary, String foundInTable, String hasTSub) {
				this.id = id;
				this.categoryID = categoryID;
				this.categoryName = categoryName;
				this.secondarySub = secondary;
				this.foundInTable = foundInTable;
				this.hasTSub = hasTSub;
			}

		@Override
		public String toString() {
			if(categoryName == "View All"){
				return secondarySub;
			} else
			return secondarySub;
		}
	}
		
		
		/********************handle Tertiary Sub Category calls**************************/
		
		private static void addTertiarySubItem(TertiarySubItem item) {
			TSUB_ITEMS.add(item);
			TSUB_ITEM_MAP.put(item.id, item);
		}
		
		public static void setTertiarySubContext(Context c, String parentCat, String mainCat) {
			TSUB_ITEMS.clear();
			TSUB_ITEM_MAP.clear();
			DatabaseHelper myDbHelper = new DatabaseHelper(c); // SQLiteOpenHelper + SQLiteDatabase manager
			try {   //used this for testing	           
				myDbHelper.openDataBase();
			}catch(SQLException sqle){
				throw sqle;
			}
			
			String[] cols = new String[] {Database.Categories._ID, Database.Categories.CATEGORY_ID, Database.Categories.MAIN_CATEGORY,
					Database.Categories.TERTIARY_SUB, Database.Categories.LOCATION};
			String selection = Database.Categories.MAIN_CATEGORY + "=? and " + Database.Categories.SECONDARY_SUB + "=? and " + Database.Categories.TERTIARY_SUB + " IS NOT NULL";
			String[] selectionArgs = new String[] {mainCat, parentCat};
			Cursor cur = myDbHelper.queryCategory(true, Database.Categories.TABLE_NAME, cols, selection, selectionArgs,
					Database.Categories.TERTIARY_SUB, null, Database.Categories.TERTIARY_SUB, null); // database query
			
			//add View All option
			String viewalltext = "View All of " + parentCat + "including subs";
			TertiarySubItem viewallitem = new TertiarySubItem("9999", "9999", viewalltext, "View All", "blank");
			addTertiarySubItem(viewallitem);
			//add View only option
			String viewonlytext = "View Only from  " + parentCat + "with no subs";
			TertiarySubItem viewonlyitem = new TertiarySubItem("9999", "9999", viewonlytext, "View Only", "blank");
			addTertiarySubItem(viewonlyitem);
			
			
			if (cur.moveToFirst()) {
				do {
					TertiarySubItem tertsubitem = new TertiarySubItem(cur.getString(0), cur.getString(1), cur.getString(2),
							cur.getString(3), cur.getString(4));
					addTertiarySubItem(tertsubitem);
				} while (cur.moveToNext());
			}
			
						
			myDbHelper.close();
		}
		
		/**
		 * A PrimarySubItem representing a primary sub category of a main category.
		 */
		public static class TertiarySubItem {
			public String id;
			public String categoryID;
			public String categoryName;
			public String tertiarySub;
			public String foundInTable;

			public TertiarySubItem(String id, String categoryID, String categoryName, String tertiary, String foundInTable) {
				this.id = id;
				this.categoryID = categoryID;
				this.categoryName = categoryName;
				this.tertiarySub = tertiary;
				this.foundInTable = foundInTable;
			}

		@Override
		public String toString() {
			return tertiarySub;
		}
	}
}

