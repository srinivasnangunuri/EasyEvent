package com.asep.easyevent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.asep.util.DBConn;
import com.asep.util.Event;
import com.asep.util.Topic;

public class AddEventActivity extends Activity {

	private Spinner topicspinner;
	private Topic topic;
	private List<Topic> topiclist;
	private String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		View view = findViewById(R.id.addEventLayout);
		Intent intent = getIntent();
		userName = intent.getStringExtra("username");
		// view.setBackgroundColor(Color.rgb(176, 196, 222));
		topicspinner = (Spinner) findViewById(R.id.topics);
		createLists();// This will be replaced by EventDao method, which in
						// backgound connects to the web service
		addItemsOnTopics();
	}

	private void createLists() {
		topiclist = new ArrayList<Topic>();
		Topic temp = new Topic("topic1", 1);
		temp.events.add(Event.getEvent("event11"));
		temp.events.add(Event.getEvent("event12"));
		temp.events.add(Event.getEvent("event13"));
		topiclist.add(temp);
		temp = new Topic("topic2", 2);
		temp.events.add(Event.getEvent("event21"));
		temp.events.add(Event.getEvent("event22"));
		temp.events.add(Event.getEvent("event23"));
		topiclist.add(temp);
		temp = new Topic("topic3", 3);
		temp.events.add(Event.getEvent("event31"));
		temp.events.add(Event.getEvent("event32"));
		temp.events.add(Event.getEvent("event33"));
		topiclist.add(temp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		return true;
	}

	public void addEventAction(View v) {
		String eventName = ((EditText) findViewById(R.id.eventname)).getText()
				.toString().trim();
		String eventVenue = ((EditText) findViewById(R.id.eventvenue))
				.getText().toString().trim();
		String eventDesc = ((EditText) findViewById(R.id.eventdesc)).getText()
				.toString().trim();
		new ActionAsync().execute(new String[] { eventName, eventVenue,
				eventDesc, topic.getTopicId() + "" });

	}

	class ActionAsync extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			final String eventName = params[0];
			final String eventVenue = params[1];
			final String eventDesc = params[2];
			final String topicId = params[3];

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Event " + eventName + " TopicID " + topicId,
							Toast.LENGTH_SHORT);
					toast.show();
				}
			});

			return null;

		}

	}

	public void addItemsOnTopics() {
		List<String> topicString = new ArrayList<String>();
		for (int i = 0; i < topiclist.size(); i++) {
			topicString.add(topiclist.get(i).name);
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, topicString);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		topicspinner.setAdapter(dataAdapter);
		topicspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				topic = topiclist.get(topicspinner.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

}
