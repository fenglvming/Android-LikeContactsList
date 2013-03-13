package com.vancl.likecontactlist.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.vancl.likecontactlist.model.BaseListModel;

public class ExpandableContactLikeListAdapter<T extends BaseListModel> extends BaseExpandableListAdapter{
	private List<String> groups;
	private List<List<T>> leafItems;
	private Context context;
	public ExpandableContactLikeListAdapter(Context context,List<String> groups,List<List<T>> items){
		this.context = context;
		this.groups = groups;
		this.leafItems = items;
	}

	@Override
	public T getChild(int groupPosition, int childPosition) {
		List<T> group = leafItems.get(groupPosition);
		T item = group.get(childPosition);
		return item;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		T item = leafItems.get(groupPosition).get(childPosition);   
        return getGenericView(item.getItemName());  
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return leafItems.get(groupPosition).size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String string = groups.get(groupPosition);    
        return getGenericView(string);  
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	//创建组/子视图    
    public TextView getGenericView(String s) {    
        // Layout parameters for the ExpandableListView    
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(    
                ViewGroup.LayoutParams.FILL_PARENT, 40);  
        TextView text = new TextView(context);    
        text.setLayoutParams(lp);    
        // Center the text vertically    
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);    
        // Set the text starting position    
        text.setPadding(36, 0, 0, 0);    
        text.setText(s);    
        return text;    
    }    
	
}
