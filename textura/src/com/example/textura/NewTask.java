package com.example.textura;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.textura.bean.TaskBean;
import com.example.textura.db.MyDBHelper;

/**
 *
 * The add and edit Task page
 * 
 */
public class NewTask extends Activity implements OnClickListener {
	/** encapsulate the date need display */
	private TaskBean taskBean;

	private TextView title;

	private EditText name;
	private TextView detail;
	private Spinner spinner;

	private TextView date;
	private TextView time;

	private Button confirm;
	private Button cancle;
	
	private RatingBar priority;

	/** True represent add Task, and False represent edit Task */
	private boolean isAdd;

	private Calendar calendar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtask);
		calendar = Calendar.getInstance();

		// Add value to the widget
		title = (TextView) findViewById(R.id.title);
		spinner = (Spinner) findViewById(R.id.expandableListView1);
		confirm = (Button) findViewById(R.id.confirm);
		cancle = (Button) findViewById(R.id.clear);
		date = (TextView) findViewById(R.id.date);
		time = (TextView) findViewById(R.id.time);
		detail = (TextView) findViewById(R.id.content);
		name = (EditText) findViewById(R.id.task_title);
		priority = (RatingBar) findViewById(R.id.ratingbar);
		
		// initiate variable that going to used for include in the data
		String Title = title.getText().toString();
		String Date = date.getText().toString();
		String Time = time.getText().toString();
		String Detail = detail.getText().toString();
		String Name = name.getText().toString();
		//String Priority = priority.getText().toString();

		// Get the data from intent 
		if (getIntent() != null) {
			taskBean = (TaskBean) getIntent().getSerializableExtra("TaskBean");
		}

		// To determine it is add Task or edit Task page
		if (taskBean == null) {
			// The add Task page need initialize the TaskBean
			taskBean = new TaskBean();
			taskBean.setId((int) System.currentTimeMillis());
			taskBean.setClassificationId(0);
			taskBean.setClassificationName(Config.TAG_NAME[0]);
			taskBean.setClassificationColor(Config.TAG_COLORS[0]);
			taskBean.setYear(calendar.get(Calendar.YEAR));
			taskBean.setMouth(calendar.get(Calendar.MONTH) + 1);
			taskBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			taskBean.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			taskBean.setMinute(calendar.get(Calendar.MINUTE));
			isAdd = true;
		} else {
			isAdd = false;
		}

		// Display the data on the page
		name.setText(taskBean.getName());
		spinner.setSelection(taskBean.getClassificationId());
		date.setText(taskBean.getMouth() + "/" + taskBean.getDay() + "/" + taskBean.getYear());
		time.setText(taskBean.getHour() + ":" + taskBean.getMinute());
		detail.setText(taskBean.getDetail());
		priority.setRating(taskBean.getPriority());
		if (isAdd) {
			title.setText("Add Task");
			confirm.setText("create");
		} else {
			title.setText("Edit Task");
			confirm.setText("confirm");
		}

		// Response the user's action 
		date.setOnClickListener(this);
		time.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancle.setOnClickListener(this);
		spinner.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView tv;
				if (convertView != null) {
					tv = (TextView) convertView;
				} else {
					tv = new TextView(NewTask.this);
				}
				tv.setText(Config.TAG_NAME[position]);
				tv.setTextColor(Color.BLACK);
				tv.setPadding(5, 3, 3, 5);
				return tv;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return Config.TAG_NAME[position];
			}

			@Override
			public int getCount() {
				return Config.TAG_NAME.length;
			}
		});
	}

	@Override
	public void onClick(View v) {
		Log.i("t", "tes");
		switch (v.getId()) {
		case R.id.confirm:// Confirm 
			insertOrUpdateTask();
			break;
		case R.id.clear:// Clear
			clear();
			break;
		case R.id.time:// Choice the time
			TimePickerDialog timePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					time.setText(hourOfDay + ":" + minute);
					taskBean.setHour(hourOfDay);
					taskBean.setMinute(minute);
				}
			}, taskBean.getHour(), taskBean.getMinute(), true);
			timePickerDialog.show();
			break;
		case R.id.date:// Choice the date
			DatePickerDialog datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					monthOfYear++;
					date.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
					taskBean.setYear(year);
					taskBean.setMouth(monthOfYear);
					taskBean.setDay(dayOfMonth);
				}
			}, taskBean.getYear(), taskBean.getMouth() - 1, taskBean.getDay());
			//Setting the initial time
			datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
			datePickerDialog.show();
			break;
		}
	}

	/**
	 * Clear the page
	 */
	private void clear() {
		name.setText(null);
		detail.setText(null);
	}

	/**
	 * Insert or update Task
	 */
	private void insertOrUpdateTask() {
		// Syn. the change to TaskBean
		taskBean.setClassificationId((int) spinner.getSelectedItemId());
		taskBean.setClassificationName(Config.TAG_NAME[taskBean.getClassificationId()]);
		taskBean.setClassificationColor(Config.TAG_COLORS[taskBean.getClassificationId()]);
		taskBean.setName(name.getText().toString());
		taskBean.setDetail(detail.getText().toString());
		
		taskBean.setPriority((int) priority.getRating());
		// the parser method that gonna be used for write data in cloud 
		// Task name can not empty
		if (TextUtils.isEmpty(taskBean.getName())) {
			Toast.makeText(this, "The task name can not be empty", Toast.LENGTH_SHORT).show();
		} else {
			// Update the value to the database 
			MyDBHelper dbHelper = new MyDBHelper(getApplicationContext(), MyDBHelper.DB_NAME, null,
					MyDBHelper.DB_VERSION);
			if (isAdd) {
				// dont forget to initiate any variable that going to be writen,in this case , i wanted to write task detail in cloud and local 
				String result = ""; // just for test logs
				String Detail = detail.getText().toString(); // initiate Detail as detail field in table
				ArrayList param = new ArrayList();
				// write param.add if we want to write down a variable from the apps that going to be write on the Cloud Database
            	param.add(new BasicNameValuePair("content", Detail));
            	
            	try{
        	    	HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://texturabeta.com/php/createtask.php"); // post ke php  ,php do the resst
        	        httppost.setEntity(new UrlEncodedFormEntity(param));
        	        HttpResponse response = httpclient.execute(httppost);
        	        HttpEntity entity = response.getEntity();
        	        InputStream is = entity.getContent();
        	        
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                            
                    }
                    is.close();

                    result=sb.toString();
                    
                    JSONArray jArray = new JSONArray(result);
                    
                    JSONObject json_data = jArray.getJSONObject(0);
                    if (json_data.getString("content")!=null){
                    	String Con = json_data.getString("content");
                    	dbHelper.add(taskBean);	
                    	
                    }
			else {
				dbHelper.update(taskBean);
			}
			 // print the error message on the LogCat in Eclipse 
			}catch(Exception e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	        }
			dbHelper.close();

			// Close the activity 
			finish();
		}
	}
}
}
