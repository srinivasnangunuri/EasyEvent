package com.asep.easyevent;

import java.util.*;

import com.asep.util.*;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends ActionBarActivity {

	private Spinner topicspinner, eventspinner;
	private Topic topic;
	private Event event;
	private List<Topic> topiclist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		topicspinner = (Spinner) findViewById(R.id.topics);
        eventspinner = (Spinner) findViewById(R.id.events);
        createLists();
        addItemsOnTopics();
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
	
	public void showDetail(View v){
		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra("name", event.name);
		intent.putExtra("description", event.description);
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
    		eventString.add(topicObject.events.get(i).name);
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
    			event = topic.events.get(topicspinner.getSelectedItemPosition());
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
}
