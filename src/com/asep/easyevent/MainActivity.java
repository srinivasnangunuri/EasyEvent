package com.asep.easyevent;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.asep.dao.EventDao;
import com.asep.dao.SubscriptionDao;
import com.asep.dao.TopicDao;
import com.asep.util.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
		// view.setBackgroundColor(Color.rgb(176, 196, 222));
		topicspinner = (Spinner) findViewById(R.id.topics);
		eventspinner = (Spinner) findViewById(R.id.events);
		getLists();// This will be replaced by TopicDao method, which in
		// backgound connects to the web service
		Intent intent = getIntent();
		userName = intent.getStringExtra("username");
		createMyUpcomingEventsList(userName);

	}
	private void getLists() {
		try {
			topiclist = new getAllTopics().execute().get();
			addItemsOnTopics();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void subscribeTopicAction(View v) {
		try {
			new Subscribe().execute(new String[] { userName, topic.getTopicId()+"" }).get();
			createMyUpcomingEventsList(userName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showDetail(View v) {
		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra("name", event.getName());
		intent.putExtra("description", event.getDescription());
		startActivity(intent);
	}
	
	public void refresh(View v){
		topicspinner.setAdapter(null);
		eventspinner.setAdapter(null);
		getLists();
		createMyUpcomingEventsList(userName);
	}

	public void showAddEvent(View v) {
		Intent intent = new Intent(this, AddEventActivity.class);
		intent.putExtra("username", userName);
		startActivity(intent);
	}

	public void addItemsOnTopics() {
		List<String> topicString = new ArrayList<String>();
		for (int i = 0; i < topiclist.size(); i++) {
			topicString.add(topiclist.get(i).getName());
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
				addItemsOnEvents(topic);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	public void addItemsOnEvents(Topic topicObject) {
		List<String> eventString = new ArrayList<String>();
		for (int i = 0; i < topicObject.getEvents().size(); i++) {
			eventString.add(topicObject.getEvents().get(i).getName());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, eventString);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		eventspinner.setAdapter(dataAdapter);
		eventspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				topic = topiclist.get(topicspinner.getSelectedItemPosition());
				event = topic.getEvents().get(eventspinner.getSelectedItemPosition());
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

	public void createMyUpcomingEventsList(String username) {
		try {
			UpcomingEvents ue = new UpcomingEvents();
			List<Event> myEvents = ue.execute(username).get();
			LinearLayout linear = (LinearLayout) findViewById(R.id.linearLayout2);
			linear.removeAllViews();
			for (int i = 0; i < myEvents.size(); i++) {
				final TextView tv = new TextView(this);

				tv.setId(i);
				tv.setText(myEvents.get(i).getName());
				tv.setHint(myEvents.get(i).getDescription());

				tv.setTextColor(Color.rgb(28, 142, 25));
				tv.setPaddingRelative(0, 5, 0, 10);
				tv.setTextSize(25);

				View line = new View(this);
				line.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						1));
				line.setBackgroundColor(Color.rgb(51, 51, 51));
				linear.addView(line);

				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent(MainActivity.this,
								EventDetailActivity.class);
						intent.putExtra("name", tv.getText().toString());
						intent.putExtra("description", tv.getHint().toString());
						startActivity(intent);

					}
				});

				linear.addView(tv);
			}
		} catch (Exception e) {
			return;
		}
	}

	class Subscribe extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			String username = params[0];
			String topic_id = params[1];
			SubscriptionDao.subscribeTopic(username, Integer.parseInt(topic_id));
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Subscribed", Toast.LENGTH_SHORT);
					toast.show();
				}
			});

			return null;

		}

	}

	class UpcomingEvents extends AsyncTask<String, Void, List<Event>> {
		@Override
		protected List<Event> doInBackground(String... params) {
			String username = params[0];
			List<Event> myEvents = SubscriptionDao.getMyUpcomingEvents(username);
			return myEvents;
		}
	}
	
	class getAllTopics extends AsyncTask<String, Void, List<Topic>> {
		@Override
		protected List<Topic> doInBackground(String... params) {
			List<Topic> myTopics = TopicDao.getTopics();
			return myTopics;
		}
	}

}
