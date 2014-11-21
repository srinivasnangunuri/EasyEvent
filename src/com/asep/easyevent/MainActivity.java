package com.asep.easyevent;

import java.util.*;

import com.asep.dao.EventDao;
import com.asep.easyevent.AddEventActivity.ActionAsync;
import com.asep.util.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Spinner topicspinner, eventspinner;
	private Topic topic;
	private Event event;
	private List<Topic> topiclist;
	private String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View view = findViewById(R.id.mainLayout);
	    //view.setBackgroundColor(Color.rgb(176, 196, 222));
		topicspinner = (Spinner) findViewById(R.id.topics);
        eventspinner = (Spinner) findViewById(R.id.events);
        createLists();// This will be replaced by TopicDao method, which in
		// backgound connects to the web service
        Intent intent = getIntent();
        addItemsOnTopics();
        userName = intent.getStringExtra("username");
        createMyUpcomingEventsList(intent.getStringExtra("username"));
        
        
	
	}
	
	private void createLists(){
		topiclist = new ArrayList<Topic>();
		Topic temp = new Topic("topic1");
		temp.events.add(Event.getEvent("event11"));
		temp.events.add(Event.getEvent("event12"));
		temp.events.add(Event.getEvent("event13"));
		topiclist.add(temp);
		temp = new Topic("topic2");
		temp.events.add(Event.getEvent("event21"));
		temp.events.add(Event.getEvent("event22"));
		temp.events.add(Event.getEvent("event23"));
		topiclist.add(temp);
		temp = new Topic("topic3");
		temp.events.add(Event.getEvent("event31"));
		temp.events.add(Event.getEvent("event32"));
		temp.events.add(Event.getEvent("event33"));
		topiclist.add(temp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void subscribeTopicAction(View v){

		new ActionAsync().execute(new String[] { topic.name});
	}
	
	
	public void showDetail(View v){
		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra("name", event.getName());
		intent.putExtra("description", event.getDescription());
		startActivity(intent);
	}
	
	
	public void showAddEvent(View v){
		Intent intent = new Intent(this, AddEventActivity.class);
		intent.putExtra("username", userName);
		startActivity(intent);
	}
	
    public void addItemsOnTopics(){
    	List<String> topicString = new ArrayList<String>();
    	for(int i = 0 ; i < topiclist.size(); i++){
    		topicString.add(topiclist.get(i).name);
    	}
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_spinner_item,
                topicString);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topicspinner.setAdapter(dataAdapter);
        topicspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               	topic = topiclist.get(topicspinner.getSelectedItemPosition());
            	addItemsOnEvents(topic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    public void addItemsOnEvents(Topic topicObject) {
    	List<String> eventString = new ArrayList<String>();
    	for(int i = 0 ; i < topicObject.events.size(); i++){
    		eventString.add(topicObject.events.get(i).getName());
    	}
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item,
        		eventString);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventspinner.setAdapter(dataAdapter);
        eventspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	@Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
    			topic = topiclist.get(topicspinner.getSelectedItemPosition());
    			event = topic.events.get(eventspinner.getSelectedItemPosition());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
	
  public void createMyUpcomingEventsList(String username){
	  
	  List<Event> myEvents = new EventDao().getMyUpcomingEvents(username);
		LinearLayout linear = (LinearLayout) findViewById(R.id.linearLayout2);
		for (int i = 0; i < myEvents.size(); i++) {
			final TextView tv = new TextView(this);

			tv.setId(i);
			// ed.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
			// LayoutParams.WRAP_CONTENT));
			tv.setText(myEvents.get(i).getName());
			
			tv.setTextColor(Color.rgb(28, 142, 25));
			tv.setPaddingRelative(0, 5, 0, 10);
			tv.setTextSize(25);
	
			View line = new View(this);
			line.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
			line.setBackgroundColor(Color.rgb(51, 51, 51));
			linear.addView(line);
			
			tv.setOnClickListener(new OnClickListener() {
				
				
				
				@Override
				public void onClick(View v) {
					
			    Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
			    intent.putExtra("name",tv.getText().toString());
			    intent.putExtra("description","Event Desc");
			    startActivity(intent);
					
				}
			});
			
			
			
			linear.addView(tv);
		}
  }
  
  class ActionAsync extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			final String topicName = params[0];

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Subscribed to "+topicName,
							Toast.LENGTH_SHORT);
					toast.show();
				}
			});

			return null;

		}

	}
	
	
}	

