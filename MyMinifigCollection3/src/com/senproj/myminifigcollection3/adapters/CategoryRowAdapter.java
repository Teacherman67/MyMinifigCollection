package com.senproj.myminifigcollection3.adapters;

import java.util.List;

import com.senproj.myminifigcollection3.R;
import com.senproj.myminifigcollection3.database.CategoryContent;







import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryRowAdapter extends ArrayAdapter<CategoryContent> {
	
	private List<CategoryContent> categoryList;
	private Context context;
	
	public CategoryRowAdapter(List<CategoryContent> categoryList, Context ctx) {
	    super(ctx, R.layout.category_list_row, categoryList);
	    this.categoryList = categoryList;
	    this.context = ctx;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	     
	    // First let's verify the convertView is not null
	    if (convertView == null) {
	        // This a new view we inflate the new layout
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.category_list_row, parent, false);
	    }
	        // Now we can fill the layout with the right values
	        TextView category = (TextView) convertView.findViewById(R.id.categoryname);
	        CategoryContent p = categoryList.get(position);
	 
	 //       category.setText(p.getName());
	 //       distView.setText("" + p.getDistance());  figure out how to get to the data
	     
	     
	    return convertView;
	}

}
