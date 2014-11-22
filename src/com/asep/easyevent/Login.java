package com.asep.easyevent;

import com.asep.util.DBConn;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
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

	public void login(View v) throws Exception {
		String username = ((EditText) findViewById(R.id.username)).getText()
				.toString().trim();
		String password = ((EditText) findViewById(R.id.password)).getText()
				.toString().trim();
		// String username = "leoyuchuan";
		// String password = "leoyuchuan";
		if (username.length() != 0 && password.length() != 0)
			new LoginAsync().execute(new String[] { username, password });

	}

	public void register(View v) {
		Intent intent = new Intent(this, Register.class);
		startActivity(intent);
	}

	class LoginAsync extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			if (DBConn.login(username, password)) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
			} else {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast toast = Toast.makeText(getApplicationContext(),
								"Fail to Login", Toast.LENGTH_SHORT);
						toast.show();
					}
				});

			}
			return null;
		}

	}
}
