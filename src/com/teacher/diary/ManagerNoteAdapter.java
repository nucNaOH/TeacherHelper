package com.teacher.diary;
import java.util.ArrayList;
import java.util.List;

import com.teacher.helper.R;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;




public class ManagerNoteAdapter extends BaseAdapter{
	
	private Context context = null;
	private List<dailyNote> list = null;
	
	public ManagerNoteAdapter(Context context, List<dailyNote> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.deletedaily, null);
			//使用viewHolder来寻找控�?			vh.time = (TextView)convertView.findViewById(R.id.time);
			vh.notitle = (TextView)convertView.findViewById(R.id.notitle);
			vh.cb = (CheckBox) convertView.findViewById(R.id.cb);
			  // 为view设置标签
			convertView.setTag(vh);
		} else {
			//填充数据
			vh = (ViewHolder) convertView.getTag();
		}
		//绑定数据到控�?		vh.time.setText(list.get(position).getRiqi());
		vh.notitle.setText(list.get(position).getTitle());
		vh.cb.setOnClickListener(new CheckBoxOnCLick(position));
		//vh.cb.setChecked(list.get(position).equals("true"));
		
		return convertView;
		
	}
	
    
	
	
	class CheckBoxOnCLick implements OnClickListener {
		private int index = 0;
		public CheckBoxOnCLick(int index) {
			this.index = index;
		}
		
		public void onClick(View v) {
			dailyNote note = list.get(index);
			if (ManagerNoteActivity.cbList.contains(note.getId()+"")) {
				ManagerNoteActivity.cbList.remove(note.getId()+"");
			} else {
				ManagerNoteActivity.cbList.add(note.getId()+"");
			}
//			  Cursor cursor = getCursor();  
//              cursor.moveToPosition(index);  
//                 
//              checkbox.setChecked(checkbox.isChecked());  
//              if(checkbox.isChecked())//如果被�?中则将id保存到集合中  
//              {  
//                  selection.add(cursor.getInt(cursor.getColumnIndex(mIdColumn)));  
//              }  
//              else//否则移除  
//              {  
//                  selection.remove(new Integer(cursor.getInt(cursor.getColumnIndex(mIdColumn))));  
//                  Toast.makeText(context, "has removed " + cursor.getInt(cursor.getColumnIndex(mIdColumn)), 0).show();  
//              }  
//		}
//		public ArrayList<Integer> getSelectedItems()  
//	     {  
//	         return selection;  
	     }  
	}
	//首先创建viewHolder
	public final class ViewHolder {
		public TextView time;
		public TextView notitle;
		public CheckBox cb;
	}
	 
}
