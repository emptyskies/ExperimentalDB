package com.example.textura;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

/**
 * The main page
 *
 */
public class MainPage extends TabActivity {
	private static final String TAG = "Main";

	private TabHost tabHost;

	/** Tag of the tab*/
	private String[] tabTags = { "TaskList", "Groups", "TaskComplete", "Settings" };
	/** The name display on the tab*/
	private String[] tabNames = { "Current", "Groups", "Completed", "Settings" };
	/** The icon display on the tab */
	private int[] tabImageIds = { R.drawable.ic_list, R.drawable.ic_group, R.drawable.ic_complete,
			R.drawable.ic_settings };
	/** The activity for each tab */
	private Class[] tabContents = { TaskList.class, Groups.class, TaskComplete.class, Settings.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		//Add the tab
		for (int i = 0; i < tabTags.length; i++) {
			tabHost.addTab(tabHost.newTabSpec(tabTags[i])
					.setIndicator(tabNames[i], getResources().getDrawable(tabImageIds[i]))
					.setContent(new Intent(this, tabContents[i])));
		}
	}



}
