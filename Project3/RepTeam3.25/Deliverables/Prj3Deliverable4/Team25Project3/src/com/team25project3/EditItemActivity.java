package com.team25project3;

import java.util.Calendar;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.team25project3.R;
import com.team25project3.database.ToDoItem;
import com.team25project3.database.ToDoItemDataSource;

@TargetApi(9)
public class EditItemActivity extends Activity {

    private ToDoItemDataSource itemdatasource;
    private long userId = 0;
    private long itemId = 0;
    private boolean checked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        userId = getIntent().getLongExtra("com.example.cs6300todolist.userid",
                0);
        itemId = getIntent().getLongExtra("com.example.cs6300todolist.itemid",
                0);
        itemdatasource = new ToDoItemDataSource(this);
        itemdatasource.open();
        final EditText itemNameEditText = (EditText) findViewById(R.id.editText41);
        final EditText itemNoteEditText = (EditText) findViewById(R.id.editText42);
        final CheckBox setDueTimeCheckBox = (CheckBox) findViewById(R.id.checkBox41);
        final DatePicker dueDatePicker = (DatePicker) findViewById(R.id.datePicker41);
        final SeekBar priorityBar = (SeekBar) findViewById(R.id.seekBar41);
        if (itemId == 0) {
            this.setTitle("Add New Item");
            setDueTimeCheckBox.setChecked(false);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 2);
            dueDatePicker.updateDate(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            priorityBar.setProgress(0);
            dueDatePicker.setVisibility(View.GONE);
        } else {
            this.setTitle("Edit Item");
            ToDoItem item = itemdatasource.getItemByItemId(itemId);
            if (item == null) {
                Toast toast = Toast.makeText(this, "Task does not exist.",
                        Toast.LENGTH_LONG);
                toast.show();
                finish();
                this.setResult(RESULT_CANCELED);
                return;
            }
            itemNameEditText.setText(item.getName());
            itemNoteEditText.setText(item.getNote());
            setDueTimeCheckBox.setChecked(!item.isNoDueTime());
            checked = item.isChecked();
            // finishCheckBox.setChecked(item.isChecked());
            Calendar cal = Calendar.getInstance();
            if (item.isNoDueTime()) {
                cal.add(Calendar.DAY_OF_MONTH, 2);
            } else {
                cal.setTimeInMillis(item.getDueTime());
            }
            dueDatePicker.updateDate(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            priorityBar.setProgress((int) item.getPriority());
            if (item.isNoDueTime()) {
                dueDatePicker.setVisibility(View.GONE);
            } else {
                dueDatePicker.setVisibility(View.VISIBLE);
            }
        }
        setDueTimeCheckBox
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked) {
                        if (buttonView.isChecked()) {
                            dueDatePicker.setVisibility(View.VISIBLE);
                        } else {
                            dueDatePicker.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveClick(View view) {
        final EditText itemNameEditText = (EditText) findViewById(R.id.editText41);
        final EditText itemNoteEditText = (EditText) findViewById(R.id.editText42);
        final CheckBox setDueTimeCheckBox = (CheckBox) findViewById(R.id.checkBox41);
        final DatePicker dueDatePicker = (DatePicker) findViewById(R.id.datePicker41);
        final SeekBar priorityBar = (SeekBar) findViewById(R.id.seekBar41);
        // save data here
        if (itemNameEditText.getText().toString().isEmpty()) {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Task Creation Error");
            alertDialog.setMessage("Task name cannot be empty.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        if (itemId == 0) {
            // Add item
            String name = itemNameEditText.getText().toString();
            if (name.isEmpty()) {
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Task Creation Error");
                alertDialog.setMessage("Task name cannot be empty.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
            }
            String note = itemNoteEditText.getText().toString();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 1);
            cal.set(dueDatePicker.getYear(), dueDatePicker.getMonth(),
                    dueDatePicker.getDayOfMonth(), 0, 0, 0);
            if (setDueTimeCheckBox.isChecked()) {
                Calendar nowcal = Calendar.getInstance();
                nowcal.set(Calendar.MILLISECOND, 0);
                nowcal.set(Calendar.SECOND, 0);
                nowcal.set(Calendar.MINUTE, 0);
                nowcal.set(Calendar.HOUR_OF_DAY, 0);
                if (nowcal.after(cal)) {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Create Task Error");
                    alertDialog
                            .setMessage("Due date cannot be earlier than today.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
            }
            long duetime = cal.getTimeInMillis();
            long curTime = new Date().getTime();
            ToDoItem item = itemdatasource.createItem(curTime, userId, name, note,
                    duetime, !setDueTimeCheckBox.isChecked(), false,
                    priorityBar.getProgress());
            if (item != null) {
                Toast toast = Toast.makeText(this, "Task created.",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            // Update item
            ToDoItem item = new ToDoItem();
            item.setName(itemNameEditText.getText().toString());
            item.setNote(itemNoteEditText.getText().toString());
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 1);
            cal.set(dueDatePicker.getYear(), dueDatePicker.getMonth(),
                    dueDatePicker.getDayOfMonth(), 0, 0, 0);
            if (setDueTimeCheckBox.isChecked()) {
                Calendar nowcal = Calendar.getInstance();
                nowcal.set(Calendar.MILLISECOND, 0);
                nowcal.set(Calendar.SECOND, 0);
                nowcal.set(Calendar.MINUTE, 0);
                nowcal.set(Calendar.HOUR_OF_DAY, 0);
                if (nowcal.after(cal)) {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Create Task Error");
                    alertDialog
                            .setMessage("Due date cannot be earlier than today.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
            }
            item.setDueTime(cal.getTimeInMillis());
            item.setId(itemId);
            item.setUserId(userId);
            item.setNoDueTime(!setDueTimeCheckBox.isChecked());
            item.setChecked(checked);
            item.setPriority(priorityBar.getProgress());
            itemdatasource.updateItem(item);
            Toast toast = Toast.makeText(this, "Task updated.",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        this.setResult(RESULT_OK);
        this.finish();
    }

    public void onCancelClick(View view) {
        Toast toast = Toast
                .makeText(this, "Cancel Editing.", Toast.LENGTH_LONG);
        toast.show();
        this.setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        final EditText itemNameEditText = (EditText) findViewById(R.id.editText41);
        final EditText itemNoteEditText = (EditText) findViewById(R.id.editText42);
        final CheckBox setDueTimeCheckBox = (CheckBox) findViewById(R.id.checkBox41);
        final DatePicker dueDatePicker = (DatePicker) findViewById(R.id.datePicker41);
        final SeekBar priorityBar = (SeekBar) findViewById(R.id.seekBar41);
        // save data here
        if (itemId == 0) {
            // Add item
            String name = itemNameEditText.getText().toString();
            String note = itemNoteEditText.getText().toString();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 1);
            cal.set(dueDatePicker.getYear(), dueDatePicker.getMonth(),
                    dueDatePicker.getDayOfMonth(), 0, 0, 0);
            if (setDueTimeCheckBox.isChecked()) {
                Calendar nowcal = Calendar.getInstance();
                nowcal.set(Calendar.MILLISECOND, 0);
                nowcal.set(Calendar.SECOND, 0);
                nowcal.set(Calendar.MINUTE, 0);
                nowcal.set(Calendar.HOUR_OF_DAY, 0);
                if (nowcal.after(cal)) {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Create Task Error");
                    alertDialog
                            .setMessage("Due date cannot be earlier than today.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
            }
            long duetime = cal.getTimeInMillis();
            long curTime = new Date().getTime();
            ToDoItem item = itemdatasource.createItem(curTime, userId, name, note,
                    duetime, !setDueTimeCheckBox.isChecked(), false,
                    priorityBar.getProgress());
            if (item != null) {
                Toast toast = Toast.makeText(this, "Task created." + userId,
                        Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            // Update item
            ToDoItem item = new ToDoItem();
            item.setName(itemNameEditText.getText().toString());
            item.setNote(itemNoteEditText.getText().toString());
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 1);
            cal.set(dueDatePicker.getYear(), dueDatePicker.getMonth(),
                    dueDatePicker.getDayOfMonth(), 0, 0, 0);
            if (setDueTimeCheckBox.isChecked()) {
                Calendar nowcal = Calendar.getInstance();
                nowcal.set(Calendar.MILLISECOND, 0);
                nowcal.set(Calendar.SECOND, 0);
                nowcal.set(Calendar.MINUTE, 0);
                nowcal.set(Calendar.HOUR_OF_DAY, 0);
                if (nowcal.after(cal)) {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Create Task Error");
                    alertDialog
                            .setMessage("Due date cannot be earlier than today.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                            "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
            }
            item.setDueTime(cal.getTimeInMillis());
            item.setId(itemId);
            item.setUserId(userId);
            item.setNoDueTime(!setDueTimeCheckBox.isChecked());
            item.setChecked(checked);
            item.setPriority(priorityBar.getProgress());
            itemdatasource.updateItem(item);
            Toast toast = Toast.makeText(this, "Task updated.",
                    Toast.LENGTH_LONG);
            toast.show();
        }
        this.setResult(RESULT_OK);
        this.finish();
        super.onBackPressed();
    }
}
