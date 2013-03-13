package com.vancl.likecontactlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.vancl.likecontactlist.adapter.ContactLikeListAdapter;
import com.vancl.likecontactlist.model.BaseListModel;
import com.vancl.likecontactlist.widgets.ListSideBar;
import com.vancl.likecontactlist.widgets.ListSideBar.OnTouchingLetterChangedListener;

public class MainActivity extends Activity {
	private ListSideBar listSideBar;
	private TextView fastPosition;
	private ListView listView;
	private ContactLikeListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//no title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listSideBar = (ListSideBar)findViewById(R.id.fast_scroller);
		fastPosition = (TextView)findViewById(R.id.fast_position);
		listView = (ListView)findViewById(R.id.contactLikeList);
		listSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListenerImpl());
		adapter = new ContactLikeListAdapter(MainActivity.this, initDatas(), listSideBar);
		listView.setAdapter(adapter);
	}
	private List<BaseListModel> initDatas(){
		 List<BaseListModel> ret = new ArrayList<BaseListModel>();
		 
		 return ret;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public class OnTouchingLetterChangedListenerImpl implements OnTouchingLetterChangedListener{
		@Override
		public void onTouchingLetterChanged(String s) {
			fastPosition.setText(s);
			fastPosition.setVisibility(View.VISIBLE);
			int index = adapter.scrollToString(s);
			if(index >= 0){
				listView.setSelectionFromTop(index,0);
			}
		}

		@Override
		public void onTouchOver() {
			fastPosition.setText("");
			fastPosition.setVisibility(View.INVISIBLE);
		}
	}
    
}
