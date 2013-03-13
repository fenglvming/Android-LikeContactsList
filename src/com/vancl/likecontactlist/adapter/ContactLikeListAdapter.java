package com.vancl.likecontactlist.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vancl.likecontactlist.R;
import com.vancl.likecontactlist.model.BaseListModel;
import com.vancl.likecontactlist.utils.CharactorUtils;
import com.vancl.likecontactlist.widgets.ListSideBar;

public class ContactLikeListAdapter extends BaseAdapter{
	private Context context;
	private List<BaseListModel> models; 
	private LayoutInflater inflater;
	//保存索引首次出现在列表中的位置.
	private HashMap<String, Integer> alphaIndexer;
	//索引值,与ListSideBar中的一致.
	private String[] sections;
	
	private ListSideBar listSideBar;
	
	public ContactLikeListAdapter(Context context,List<BaseListModel> models,ListSideBar sideBar){
		this.context = context;
		this.models = models;
		this.inflater = LayoutInflater.from(context);
		this.alphaIndexer = new HashMap<String, Integer>();
		
		for (int i =0; i <models.size(); i++) {
			String name = CharactorUtils.getAlpha(models.get(i).getItemName());
			if(!alphaIndexer.containsKey(name)){
				//只记录在list中首次出现的位置
				alphaIndexer.put(name, i);
			}
		}
		
		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(
				sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);
		listSideBar = sideBar;
		
	}
	
	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public Object getItem(int arg0) {
		return models.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.number = (TextView) convertView
					.findViewById(R.id.number);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		BaseListModel baseListModel = models.get(position);
		holder.name.setText(baseListModel.getItemName());
		holder.number.setVisibility(View.INVISIBLE);
		holder.imageView.setVisibility(View.INVISIBLE);
		
		// 当前联系人的sortKey
		String currentStr = CharactorUtils.getAlpha(baseListModel.getItemName());
		// 上一个联系人的sortKey
		String previewStr = (position - 1) >= 0 ? CharactorUtils.getAlpha(models.get(
				position - 1).getItemName()) : " ";
		/**
		 * 判断显示#、A-Z的TextView隐藏与可见
		 */
		if (!previewStr.equals(currentStr)) { // 当前联系人的sortKey！=上一个联系人的sortKey，说明当前联系人是新组。
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	public int scrollToString(String s){
		int index = -1;
		if(alphaIndexer.containsKey(s)){
			index = alphaIndexer.get(s);
		}
		return index;
	}
	
	private static class ViewHolder {
		TextView alpha;
		TextView name;
		TextView number;
		ImageView imageView;
	}
}
