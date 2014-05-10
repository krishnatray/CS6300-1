package com.team25project3;

import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.team25project3.R;
import com.team25project3.database.DueDateComparator;
import com.team25project3.database.PriorityComparator;
import com.team25project3.database.ToDoItem;
import com.team25project3.database.ToDoItemDataSource;

@TargetApi(5)
public class ToDoListActivity extends Activity {

    private ToDoItemDataSource itemdatasource;
    private long userId = 0;
    private static final int SORT_DUEDATE = 0;
    private static final int SORT_PRIORITY = 1;
    private int sortType = 0;
    private boolean hide = false;

    public List<ToDoItem> getNewList() {
        List<ToDoItem> newList = null;
        if (itemdatasource == null) {
            return null;
        }
        if (hide) {
            newList = itemdatasource.getInCompleteListByUId(userId);
        } else {
            newList = itemdatasource.getToDoListByUId(userId);
        }
        if (sortType == SORT_DUEDATE) {
            Collections.sort(newList, new DueDateComparator());
        } else {
            Collections.sort(newList, new PriorityComparator());
        }
        return newList;
    }

    public boolean getHide() {
        return this.hide;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        userId = getIntent().getLongExtra("com.example.cs6300todolist.userid",
                0);
        String userName = getIntent().getStringExtra(
                "com.example.cs6300todolist.username");
        if (userId == 0) {
            finish();
            return;
        }
        this.setTitle("ToDo list for user " + userName);
        hide = false;
        sortType = SORT_DUEDATE;
        itemdatasource = new ToDoItemDataSource(this);
        itemdatasource.open();
        final List<ToDoItem> todoList = getNewList();
        final CheckBox cb = (CheckBox) findViewById(R.id.checkBoxHide);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                if (buttonView.isChecked()) {
                    hide = true;
                } else {
                    hide = false;
                }
                List<ToDoItem> newList = getNewList();
                final ListView toDoListView = (ListView) findViewById(R.id.listView1);
                ToDoListAdapter adapter = (ToDoListAdapter) toDoListView
                        .getAdapter();
                adapter.updateList(newList);
                adapter.notifyDataSetChanged();
            }
        });
        final ListView toDoListView = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<ToDoItem> adapter = new ToDoListAdapter(this, todoList,
                itemdatasource);
        toDoListView.setClickable(true);
        toDoListView.setLongClickable(true);
        toDoListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @SuppressLint("NewApi")
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                final ToDoItem currentItem = todoList.get(position);
                PopupMenu popupMenu = new PopupMenu(ToDoListActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.list_popup_menu,
                        popupMenu.getMenu());
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.edit_menu) {
                                    // edit
                                    Intent myIntent = new Intent(
                                            ToDoListActivity.this,
                                            EditItemActivity.class);
                                    myIntent.putExtra(
                                            "com.example.cs6300todolist.userid",
                                            userId);
                                    myIntent.putExtra(
                                            "com.example.cs6300todolist.itemid",
                                            currentItem.getId());
                                    startActivityForResult(myIntent, 0);
                                } else {
                                    // delete
                                    itemdatasource.deleteItem(currentItem);
                                    List<ToDoItem> todoList = getNewList();
                                    final ListView toDoListView = (ListView) findViewById(R.id.listView1);
                                    ToDoListAdapter adapter = (ToDoListAdapter) toDoListView
                                            .getAdapter();
                                    adapter.updateList(todoList);
                                    adapter.notifyDataSetChanged();
                                }
                                return true;
                            }
                        });
                popupMenu.show();
                return true;
            }
        });
        toDoListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_duedate:
            sortType = SORT_DUEDATE;
            List<ToDoItem> todoList = getNewList();
            final ListView toDoListView = (ListView) findViewById(R.id.listView1);
            ToDoListAdapter adapter = (ToDoListAdapter) toDoListView
                    .getAdapter();
            adapter.updateList(todoList);
            adapter.notifyDataSetChanged();
            return true;
        case R.id.menu_priority:
            sortType = SORT_PRIORITY;
            List<ToDoItem> todoListp = getNewList();
            final ListView toDoListViewp = (ListView) findViewById(R.id.listView1);
            ToDoListAdapter adapterp = (ToDoListAdapter) toDoListViewp
                    .getAdapter();
            adapterp.updateList(todoListp);
            adapterp.notifyDataSetChanged();
            return true;
        case R.id.menu_changepwd:
            Intent myIntent = new Intent(this, AddUserActivity.class);
            myIntent.putExtra("com.example.cs6300todolist.userid", userId);
            startActivityForResult(myIntent, 0);
            return true;
        case R.id.menu_sync:
            new SyncTasks(this).execute();        	
            return true;
        case R.id.menu_logout:
            setResult(RESULT_OK);
            finish();
            return true;
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.setResult(RESULT_OK);
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            List<ToDoItem> todoList = getNewList();
            final ListView toDoListView = (ListView) findViewById(R.id.listView1);
            ToDoListAdapter adapter = (ToDoListAdapter) toDoListView
                    .getAdapter();
            adapter.updateList(todoList);
            adapter.notifyDataSetChanged();
        }
    }

    public void onAddItemClick(View view) {
        Intent myIntent = new Intent(view.getContext(), EditItemActivity.class);
        myIntent.putExtra("com.example.cs6300todolist.userid", userId);
        startActivityForResult(myIntent, 0);
    }
}
