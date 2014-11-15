package com.asep.easyevent;

import com.asep.util.DBConn;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends ActionBarActivity {

	EditText username;
	EditText password;
	EditText email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		username = (EditText)findViewById(R.id.rusername);
		password = (EditText)findViewById(R.id.rpassword);
		email = (EditText)findViewById(R.id.remail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	
	public void register(View v){
		String uname = username.getText().toString();
		String pword = password.getText().toString();
		String mail = email.getText().toString();
		if(username.length()!=0 && password.length()!=0 & mail.length()!=0)
			new RegisterAsync().execute(new String[]{uname,pword,mail});
		
	}
	
	public void clear(View v){
		username.setText("");
		password.setText("");
		email.setText("");
		username.requestFocus();
	}
	
	class RegisterAsync extends AsyncTask<String,Void,Void>{
		@Override
		protected Void doInBackground(String... params) {
			String username = params[0];
			String password = params[1];
			String email = params[2];
			if(DBConn.register(username, password, email)){
				runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Toast toast = Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
				Intent intent = new Intent(getApplicationContext(), Login.class);
				startActivity(intent);
			}
			else{
				runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Toast toast = Toast.makeText(getApplicationContext(), "Fail to Register", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
				
			}
			return null;
		}
		
	}

}
