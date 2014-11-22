package com.asep.easyevent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

import com.asep.dao.EventDao;
import com.asep.dao.TopicDao;
import com.asep.easyevent.MainActivity.getAllTopics;
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
		getLists();// This will be replaced by EventDao method, which in
						// backgound connects to the web service
		addItemsOnTopics();
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
		String topicid = (topic.getTopicId()+"").trim();
//		Toast t = Toast.makeText(getApplicationContext(), topic.getTopicId()+"", Toast.LENGTH_LONG);
//		t.show();
		try {
			new AddEvent().execute(new String[] { eventName, eventVenue,
					eventDesc, topicid }).get();
			this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	class AddEvent extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			String eventName = params[0];
			String eventVenue = params[1];
			String eventDesc = params[2];
			String topicId = params[3];
			
			EventDao.addEvents(eventName, eventDesc, eventVenue, Integer.parseInt(topicId), Calendar.getInstance().getTime());
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Event Added!",
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
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}
	
	class getAllTopics extends AsyncTask<String, Void, List<Topic>> {
		@Override
		protected List<Topic> doInBackground(String... params) {
			List<Topic> myTopics = TopicDao.getTopics();
			return myTopics;
		}
	}
}
