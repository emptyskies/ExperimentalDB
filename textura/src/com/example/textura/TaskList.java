package com.example.textura;

import com.example.textura.bean.TaskBean;
import com.example.textura.logic.TaskListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TaskList extends Activity {
	private TaskListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasklist);

		ListView listView = (ListView) findViewById(R.id.lv);
		adapter = new TaskListAdapter(getApplicationContext());
		adapter.init();
		listView.setAdapter(adapter);

		//ListView setting the onclick event
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(TaskList.this, NewTask.class);
				//Transfer data
				intent.putExtra("TaskBean", (TaskBean) adapter.getItem(position));
				
				startActivity(intent);
			}
		});

		//Create the listener to "add" button
		findViewById(R.id.add).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TaskList.this, NewTask.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Get the newest data when back to task list page and refresh the page
		adapter.init();
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
