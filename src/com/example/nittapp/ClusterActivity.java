package com.example.nittapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ClusterActivity extends Activity {

	ListView eventsList;
	EventDb events;
	static int pageNo=1;
	List<String> listForEvents;
	Intent i;
	static String clustername;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cluster);
		eventsList = (ListView) findViewById(R.id.lvCluster);
	    events=new EventDb(getApplicationContext());
	    i=getIntent();
	    setList();
		
		eventsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				{
					if(pageNo==1){
					events.open();
					Log.e("name of clicked position",
							listForEvents.get(position));
					clustername=listForEvents.get(position);
					//clustername = events.getClusterId(listForEvents.get(position));
					events.close();
					pageNo++;
					}
				
				else{
					events.open();
					Intent intent=new Intent(ClusterActivity.this, EventDetails.class);
					intent.putExtra("EventId",events.getEventId(listForEvents.get(position)) );
					events.close();
					startActivity(intent);
					
				}

				
				setList();

			}
		}});
	}

		
		

		private void setList() {
			// TODO Auto-generated method stub
			events.open();
			switch (pageNo) {
			

			case 1:
				listForEvents = events.getClusters(i.getStringExtra("orgname"));
				eventsList.setAdapter(new ArrayAdapter<String>(ClusterActivity.this,
						android.R.layout.simple_list_item_1, listForEvents));
				break;
			case 2:
				listForEvents = events.getEvents(clustername);
				eventsList.setAdapter(new ArrayAdapter<String>(ClusterActivity.this,
						android.R.layout.simple_list_item_1, listForEvents));
				Log.e("Setting events", "yayy");
				break;

			}
			events.close();

		}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cluster, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// Things to Do

			pageNo--;
			if (pageNo== 0) {
				
				finish();
			}

			setList();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
}