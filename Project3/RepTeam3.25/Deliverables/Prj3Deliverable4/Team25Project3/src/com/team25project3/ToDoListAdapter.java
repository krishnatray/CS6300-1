package com.team25project3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.team25project3.R;
import com.team25project3.database.ToDoItem;
import com.team25project3.database.ToDoItemDataSource;

public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

    private final List<ToDoItem> list;
    private final Activity context;
    private ToDoItemDataSource itemdatasource;

    public ToDoListAdapter(Activity context, List<ToDoItem> list,
            ToDoItemDataSource ds) {
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.list = list;
        this.itemdatasource = ds;
    }

    public void updateList(List<ToDoItem> newlist) {
        list.clear();
        list.addAll(newlist);
    }

    static class ViewHolder {

        protected ImageView image;
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view
                    .findViewById(R.id.priorityImage);
            viewHolder.text = (TextView) view.findViewById(R.id.todoname);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.todocheck);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                boolean isChecked) {
                            ToDoItem item = (ToDoItem) viewHolder.checkbox
                                    .getTag();
                            item.setChecked(buttonView.isChecked());
                            // update database
                            //itemdatasource.updateItem(item);
                            // if we choose hide mode, we need to update the
                            // list
                            if (buttonView.isChecked()
                                    && ((ToDoListActivity) context).getHide()) {
                                List<ToDoItem> newList = ((ToDoListActivity) context)
                                        .getNewList();
                                ToDoListAdapter.this.updateList(newList);
                                ToDoListAdapter.this.notifyDataSetChanged();
                            }
                        }
                    });
            
            
            viewHolder.checkbox
            .setOnClickListener(new CompoundButton.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ToDoItem item = (ToDoItem) viewHolder.checkbox
                            .getTag();
                    item.setChecked(viewHolder.checkbox.isChecked());
                    // update database
                    itemdatasource.updateItem(item);
                    // if we choose hide mode, we need to update the
                    // list
                    if (viewHolder.checkbox.isChecked()
                            && ((ToDoListActivity) context).getHide()) {
                        List<ToDoItem> newList = ((ToDoListActivity) context)
                                .getNewList();
                        ToDoListAdapter.this.updateList(newList);
                        ToDoListAdapter.this.notifyDataSetChanged();
                    }
                }
            });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        ToDoItem currentItem = list.get(position);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentItem.getDueTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dueTimeStr = "";
        if (!currentItem.isNoDueTime()) {
            dueTimeStr = "\t(" + sdf.format(cal.getTime()) + ")";
        }
        if (currentItem.getPriority() == 0) {
            holder.image.setImageResource(R.drawable.circle_green);
        } else if (currentItem.getPriority() == 1) {
            holder.image.setImageResource(R.drawable.circle_yellow);
        } else {
            holder.image.setImageResource(R.drawable.circle_red);
        }
        // truncate name if it is greater than 15 characters
        String itemName = currentItem.getName();
        if (itemName.length() > 15) {
            itemName = itemName.substring(0, 15) + "...";
        }
        holder.text.setText(itemName + dueTimeStr);
        holder.checkbox.setChecked(list.get(position).isChecked());
        return view;
    }
}
