package com.example.textura.logic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.textura.R;
import com.example.textura.bean.TaskBean;
import com.example.textura.db.MyDBHelper;

/**
 * Adapter for the current page ListView
 * 
 * 
 */
public class TaskListAdapter extends BaseAdapter {
	private List<TaskBean> data;
	private Context context;

	public TaskListAdapter(Context context) {
		this.context = context;
	}

	/**
	 * Initialize data, get the task from database
	 */
	public void init() {
		MyDBHelper dbHelper = new MyDBHelper(context, MyDBHelper.DB_NAME, null, MyDBHelper.DB_VERSION);
		Cursor cursor = dbHelper.query("state=?", new String[]{"0"});
		if (cursor != null && cursor.getCount() > 0) {
			data = new ArrayList<TaskBean>();
			cursor.moveToFirst();
			do {
				TaskBean taskBean = new TaskBean();
				taskBean.setId(cursor.getInt(cursor.getColumnIndex("taskId")));
				taskBean.setName(cursor.getString(cursor.getColumnIndex("name")));
				taskBean.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
				taskBean.setClassificationName(cursor.getString(cursor.getColumnIndex("classificationName")));
				taskBean.setClassificationId(cursor.getInt(cursor.getColumnIndex("classificationId")));
				taskBean.setClassificationColor(cursor.getInt(cursor.getColumnIndex("classificationColor")));
				taskBean.setYear(cursor.getInt(cursor.getColumnIndex("year")));
				taskBean.setMouth(cursor.getInt(cursor.getColumnIndex("mouth")));
				taskBean.setDay(cursor.getInt(cursor.getColumnIndex("day")));
				taskBean.setHour(cursor.getInt(cursor.getColumnIndex("hour")));
				taskBean.setMinute(cursor.getInt(cursor.getColumnIndex("minute")));
				taskBean.setPriority(cursor.getInt(cursor.getColumnIndex("priority")));
				taskBean.setState(cursor.getInt(cursor.getColumnIndex("state")));

				data.add(taskBean);
			} while (cursor.moveToNext());
		}
	}

	@Override
	public int getCount() {
		Log.d("Adapter", "data size="+(data == null ? 0 : data.size()));
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data == null ? null : data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		
		//Reuse the view
		if (convertView != null) {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		} else {
			view = LayoutInflater.from(context).inflate(R.layout.task_list_row, null);
			
			//Assign value to ViewHolder
			holder = new ViewHolder();
			holder.iv = (ImageView) view.findViewById(R.id.classification_tag);
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.detail = (TextView) view.findViewById(R.id.detail);
			holder.complete = (Button) view.findViewById(R.id.complete);
			view.setTag(holder);
		}

		holder.iv.setBackgroundColor(data.get(position).getClassificationColor());
		holder.name.setText(data.get(position).getName());
		holder.detail.setText(data.get(position).getDetail());

		holder.complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TaskBean task = data.get(position);
				task.setState(1);
				MyDBHelper dbHelper = new MyDBHelper(context, MyDBHelper.DB_NAME, null, MyDBHelper.DB_VERSION);
				dbHelper.update(task);
				data.remove(task);
				notifyDataSetChanged();
			}
		});
		return view;
	}

	/**
	 * Record the use of the widget of the item in listview, to avoid the re-search then waste the resources of the system 
	 * 记录listview的item中需要更改的控件的引用,避免多次查找浪费系统资源
	 *
	 */
	class ViewHolder {
		public ImageView iv;
		public TextView name;
		public TextView detail;
		public Button complete;
	}

}
