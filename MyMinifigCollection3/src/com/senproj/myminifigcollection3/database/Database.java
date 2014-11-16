package com.senproj.myminifigcollection3.database;

import android.provider.BaseColumns;

public final class Database {
	
	private Database() {}
	
	//this class table will handle all calls for main category info
		public static final class MainCategories implements BaseColumns {
			// To prevent someone from accidentally instantiating the Categories class,
		    // give it an empty constructor.
			private MainCategories() {}
			
			public static final String TABLE_NAME = "tblMainCategories";
			public static final String CATEGORY_ID = "CategoryID";
			public static final String MAIN_CATEGORY = "CategoryName";
			public static final String LOCATION = "FoundInTable";
			public static final String DEFAULT_SORT_ORDER = "CategoryName ASC";
			
		}
	
	//this class table will handle all calls for main and sub class info
	public static final class Categories implements BaseColumns {
		// To prevent someone from accidentally instantiating the Categories class,
	    // give it an empty constructor.
		private Categories() {}
		
		public static final String TABLE_NAME = "tblCategories";
		public static final String CATEGORY_ID = "CategoryID";
		public static final String MAIN_CATEGORY = "CategoryName";
		public static final String HAS_P_SUB = "Has_P_Sub";
		public static final String HAS_S_SUB = "Has_S_Sub";
		public static final String HAS_T_SUB = "Has_T_Sub";
		public static final String PRIMARY_SUB = "PrimarySub";
		public static final String SECONDARY_SUB = "SecondarySub";
		public static final String TERTIARY_SUB = "TertiarySub";
		public static final String LOCATION = "FoundInTable";
		public static final String DEFAULT_SORT_ORDER = "CategoryName ASC";
		
	}
	
	
	//this class table will use input table name to pull minifigure data from the requested table
	public static final class Minifigures implements BaseColumns {
		// To prevent someone from accidentally instantiating the Minifigures class,
	    // give it an empty constructor.
		private Minifigures() {}
				//currently set value, will alter to take input table name   **********************
		//public static final String TABLE_NAME = "tblAtlantis";
		public static final String FIG_ID = "FigID";
		public static final String BRICKLINK_ID = "BLFigID";
		public static final String DESCRIPTION = "Description";
		public static final String CATEGORY_ID = "CategoryID";
		public static final String MAIN_CATEGORY = "MainCategory";
		public static final String SUB_CATEGORIES = "SubCategories";
		public static final String PRODUCTION_YEAR = "ProdYear";
		public static final String IN_COLLECTION = "InCollection";
		public static final String DEFAULT_SORT_ORDER = "FigID ASC";
		
	}

}
