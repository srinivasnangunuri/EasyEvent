package com.asep.easyevent;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends ActionBarActivity {

	private Spinner topics, events;
	private String topic, event;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		topics = (Spinner) findViewById(R.id.topics);
        events = (Spinner) findViewById(R.id.events);
        addItems();
	}

	private void addItems() {
		List<String> topiclist = new ArrayList<String>();
		topiclist.add("Topic");
		topiclist.add("Topic1");
		topiclist.add("Topic2");
		topiclist.add("Topic3");
		topiclist.add("Topic4");
		topiclist.add("Topic5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                topiclist);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topics.setAdapter(dataAdapter);
        topics.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                topic = (String.valueOf(topics.getSelectedItem()));
                addItemsOnEvents(topic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void addItemsOnTopics(){
        List<String> topiclist = new ArrayList<String>();
        topiclist.add("Topic");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                topiclist);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topics.setAdapter(dataAdapter);
        topics.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                topic = (String.valueOf(topics.getSelectedItem()));
                addItemsOnEvents(topic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    public void addItemsOnEvents(String topicname) {
        List<String> eventlist = new ArrayList<String>();
        eventlist.add("Section");
        //RetrieveListInf sectionList = new RetrieveListImpl();
        if(topicname.contains("Topic"))
        {
            Toast.makeText(getApplicationContext(), "Please Select a Course name",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,
                    eventlist);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            events.setAdapter(dataAdapter);
            events.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    topic = (String.valueOf(topics.getSelectedItem()));
                    event = (String.valueOf(events.getSelectedItem()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
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
