package com.senproj.myminifigcollection3.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.senproj.myminifigcollection3.database.DatabaseHelper;
import com.senproj.myminifigcollection3.database.Database;
import com.senproj.myminifigcollection3.database.CategoryContent.PrimarySubItem;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class MinifigureContent {

	/**
	 * Arrays of Minifigure items.
	 */
	public static List<MinifigListItem> MINI_LIST_ITEMS = new ArrayList<MinifigListItem>();
	public static List<MinifigDetailItem> MINI_DETAIL_ITEM = new ArrayList<MinifigDetailItem>();

	/**
	 * Maps of Minifigure items, by ID.
	 */
	public static Map<String, MinifigListItem> MINI_LIST_ITEMS_MAP = new HashMap<String, MinifigListItem>();
	public static Map<String, MinifigDetailItem> MINI_DETAIL_ITEM_MAP = new HashMap<String, MinifigDetailItem>();


	/**********************handle Minifigure listing Calls****************************/


	public static void setPrimaryMinifigureListContext(Context c, String parentCat, String table, String level) {
		MINI_LIST_ITEMS.clear();
		MINI_LIST_ITEMS_MAP.clear();
		DatabaseHelper myDbHelper = new DatabaseHelper(c); // SQLiteOpenHelper + SQLiteDatabase manager
		try {   //used this for testing	           
			myDbHelper.openDataBase();
		}catch(SQLException sqle){
			throw sqle;
		}

		/*************************************   GET MAIN LEVEL FIG LISTINGS   *******/
		if(level.equals("Main")){
			//columns to SELECT
			String[] cols = new String[] {Database.Minifigures._ID, Database.Minifigures.FIG_ID, Database.Minifigures.BRICKLINK_ID,
					Database.Minifigures.DESCRIPTION, Database.Minifigures.SUB_CATEGORIES, Database.Minifigures.IN_COLLECTION};
			Cursor cur = myDbHelper.queryMinifig(table, cols, null, null, null, null, null); // database query

			if (cur.moveToFirst()) {
				do {
					MinifigListItem figitem = new MinifigListItem(cur.getString(0), cur.getString(1),
							cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));
					addMinifigureItem(figitem);
				} while (cur.moveToNext());
			}

			/*************************************   GET PRIMARY SUB LEVEL FIG LISTINGS   *******/
		} else if(level.equals("Primary")){
			//columns to SELECT
			String[] cols = new String[] {Database.Minifigures._ID, Database.Minifigures.FIG_ID, Database.Minifigures.BRICKLINK_ID,
					Database.Minifigures.DESCRIPTION, Database.Minifigures.SUB_CATEGORIES, Database.Minifigures.IN_COLLECTION};
			//WHERE selection
			String selection = Database.Minifigures.SUB_CATEGORIES + " IS NOT NULL";
			Cursor cur = myDbHelper.queryMinifig(table, cols, selection, null, null, null, Database.Minifigures.DEFAULT_SORT_ORDER); // database query

			if (cur.moveToFirst()) {
				do {
					//get array of split substring(pipe delimited)
					String[] subs = cur.getString(4).split("\\|");
					//cycle through possible scenarios of split results
					if(subs.length >= 1 && subs[0].trim().equals(parentCat)) {

						MinifigListItem figitem = new MinifigListItem(cur.getString(0), cur.getString(1),
								cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));
						addMinifigureItem(figitem);
					}
				} while (cur.moveToNext());
			}

			/*************************************   GET SECONDARY SUB LEVEL FIG LISTINGS   *******/
		} else if(level.equals("Secondary")){
			//columns to SELECT
			String[] cols = new String[] {Database.Minifigures._ID, Database.Minifigures.FIG_ID, Database.Minifigures.BRICKLINK_ID,
					Database.Minifigures.DESCRIPTION, Database.Minifigures.SUB_CATEGORIES, Database.Minifigures.IN_COLLECTION};
			//WHERE selection
			String selection = Database.Minifigures.SUB_CATEGORIES + " IS NOT NULL";
			Cursor cur = myDbHelper.queryMinifig(table, cols, selection, null, null, null, Database.Minifigures.DEFAULT_SORT_ORDER); // database query

			if (cur.moveToFirst()) {
				do {
					//get array of split substring(pipe delimited)
					String[] subs = cur.getString(4).split("\\|");
					//cycle through possible scenarios of split results
					if(subs.length >= 2 && subs[1].trim().equals(parentCat)) {

						MinifigListItem figitem = new MinifigListItem(cur.getString(0), cur.getString(1),
								cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));
						addMinifigureItem(figitem);
					}
				} while (cur.moveToNext());
			}

			/*************************************   GET TERTIARY SUB LEVEL FIG LISTINGS   *******/
		} else if(level.equals("Tertiary")){
			//columns to SELECT
			String[] cols = new String[] {Database.Minifigures._ID, Database.Minifigures.FIG_ID, Database.Minifigures.BRICKLINK_ID,
					Database.Minifigures.DESCRIPTION, Database.Minifigures.SUB_CATEGORIES, Database.Minifigures.IN_COLLECTION};
			//WHERE selection
			String selection = Database.Minifigures.SUB_CATEGORIES + " IS NOT NULL";
			Cursor cur = myDbHelper.queryMinifig(table, cols, selection, null, null, null, Database.Minifigures.DEFAULT_SORT_ORDER); // database query

			if (cur.moveToFirst()) {
				do {
					//get array of split substring(pipe delimited)
					String[] subs = cur.getString(4).split("\\|");
					//cycle through possible scenarios of split results
					if(subs.length >= 3 && subs[2].trim().equals(parentCat)) {

						MinifigListItem figitem = new MinifigListItem(cur.getString(0), cur.getString(1),
								cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));
						addMinifigureItem(figitem);
					}
				} while (cur.moveToNext());
			}

		}


		myDbHelper.close();

	}

	private static void addMinifigureItem(MinifigListItem item) {
		MINI_LIST_ITEMS.add(item);
		MINI_LIST_ITEMS_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class MinifigListItem {
		public String id;
		public String figID;
		public String blfigID;
		public String description;
		public String subCategories;
		public String inCollection;

		public MinifigListItem(String id, String figID, String blfigID, String description,
				String subCategories, String inCollection) {
			this.id = id;
			this.figID = figID;
			this.blfigID = blfigID;
			this.description = description;
			this.subCategories = subCategories;
			this.inCollection = inCollection;

		}
		@Override
		public String toString() {
			String output = " " + figID + ": " + description;
			return output;
		}
	}

	/**********************handle Minifigure detail Calls****************************/


	private static void addItem(MinifigDetailItem item) {
		MINI_DETAIL_ITEM.add(item);
		MINI_DETAIL_ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class MinifigDetailItem {  ///***********temporary placeholder, change all data
		public String id;
		public String figID;
		public String blfigID;
		public String description;
		public String categoryID;
		public String categoryName;
		public String subCategories;
		public String prodYear;
		public String inCollection;

		public MinifigDetailItem(String id, String figID, String blfigID, String description,
				String categoryID, String categoryName, String subCategories, String prodYear, String inCollection) {
			this.id = id;
			this.figID = figID;
			this.blfigID = blfigID;
			this.description = description;
			this.categoryID = categoryID;
			this.categoryName = categoryName;
			this.subCategories = subCategories;
			this.prodYear = prodYear;
			this.inCollection = inCollection;
		}
		@Override
		public String toString() {
			String output = " " + figID + ": " + description;
			return output;
		}
	}

}
