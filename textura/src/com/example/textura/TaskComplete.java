package com.example.textura;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TaskComplete extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskcomplete);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_complete, menu);
		return true;
	}

}
