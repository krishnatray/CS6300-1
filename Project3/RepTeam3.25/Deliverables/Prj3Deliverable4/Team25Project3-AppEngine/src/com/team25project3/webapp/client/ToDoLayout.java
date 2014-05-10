package com.team25project3.webapp.client;

import java.security.acl.Owner;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.apache.tools.ant.taskdefs.Parallel.TaskList;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.team25project3.webapp.server.PMF;
import com.team25project3.webapp.shared.FieldVerifier;
import com.team25project3.webapp.shared.TaskContainer;
import com.team25project3.webapp.shared.TaskInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ToDoLayout implements EntryPoint {

	/*
	 * Global variables for this program
	 */
	final Button addButton = new Button("Add New");
	final Button syncButton = new Button("Save");
	final Button refreshButton = new Button("Refresh List");
	final Button logoutButton = new Button("Log Out");
	DateBox dateBox = new DateBox();
	ListBox priorityBox = new ListBox(false);
	CheckBox completedBox = new CheckBox("Completed");
	CheckBox noDueBox = new CheckBox("NoDueDate");
	HorizontalPanel masterPanel;
	VerticalPanel listPanel;
	VerticalPanel taskPanel;
	TextCell textCell;
	CellList<String> cellList;
	ScrollPanel scrollList;
	Webapp master;
	TextBox taskTitle = new TextBox();
	TextArea taskNotes = new TextArea();

	private int validDate = 0;
	private int newTaskCount = 1;
	private List<String> taskNames;
	private long user;
	private int taskPos = -1;
	private int prevPos = -1;
	private TaskContainer taskList;
	private int currentTaskNumber = 0;
	private RetrieveTaskServiceAsync retrieveTaskSvc = GWT
			.create(RetrieveTaskService.class);
	private SaveTaskServiceAsync saveTaskSvc = GWT
			.create(SaveTaskService.class);
	/**
	 * 
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final RegisterServiceAsync registerService = GWT
			.create(RegisterService.class);

	public void setUserId(long userid) {
		user = userid;
	}

	public void update() {

		taskNames.set(taskPos, taskList.getTaskAtPos(taskPos).getName());
		cellList.setVisibleRange(0, taskNames.size());
		cellList.setRowCount(taskNames.size(), true);
		cellList.setRowData(0, taskNames);
	}

	public void addNewTask() {
		// saveText();
		Date date = new Date();
		int findName = 0;
		TaskInfo temp = new TaskInfo();
		temp.setName("New Task" + newTaskCount);
		temp.setLastUpdated(String.valueOf(date.getTime()));
		temp.setId(String.valueOf(date.getTime()));
		temp.setNoDueTime(0);
		while (findName == 0) {
			int match = 0;
			for (int i = 0; i < taskList.getCount(); i++) {
				if (taskList.getTaskAtPos(i).getName().equals(temp.getName())) {
					match = 1;
					i = taskList.getCount();
				}
			}
			if (match == 0) {
				findName = 1;
			} else {
				newTaskCount++;
				temp.setName("New Task" + newTaskCount);
			}
		}
		taskNames.add(temp.getName());

		taskList.addTask(temp);
		cellList.setVisibleRange(0, taskNames.size());
		cellList.setRowCount(taskNames.size(), true);
		cellList.setRowData(0, taskNames);
		taskPos = taskNames.size() - 1;
		newTaskCount++;
		getText();
	}

	public void setupTask() {

		retrieveTaskSvc.retrieveTask(String.valueOf(user),
				new AsyncCallback<TaskInfo[]>() {

					public void onFailure(Throwable caught) {
					}

					public void onSuccess(TaskInfo[] result) {
						// TaskInfo tsk = result.getTaskAtPos(0);
						for (int i = 0; i < result.length; i++) {
							TaskInfo tsk = new TaskInfo();
							tsk.setName(result[i].getName());
							tsk.setNote(result[i].getNote());
							tsk.setPriority(result[i].getPriority());
							tsk.setDueTime(result[i].getDueTime());
							tsk.setId(result[i].getId());
							tsk.setLastUpdated(result[i].getLastUpdated());
							tsk.setChecked(result[i].getChecked());
							tsk.setNoDueTime(result[i].getNoDueTime());
							taskList.addTask(tsk);
						}

						refreshList();

						cellList.setVisibleRange(0, taskNames.size());
						cellList.setRowCount(taskNames.size(), true);
						cellList.setRowData(0, taskNames);
						cellList.redraw();

					}
				});
	}

	public void saveText() {
		int change = 0;
		Date date = new Date();
		if (!taskList.getTaskAtPos(taskPos).getName()
				.equals(taskTitle.getText())) {
			change = 1;
			int j = 0;
			int run = 0;
			String name = taskTitle.getText();
			String temp = name;
			while (run == 0) {
				if (taskNames.contains(temp)) {
					j = j + 1;
					temp = name + j;
				} else {
					run = 1;
				}
			}
			taskList.getTaskAtPos(taskPos).setName(temp);
			update();
		}
		if (!taskList.getTaskAtPos(taskPos).getNote()
				.equals(taskNotes.getText())) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setNote(taskNotes.getText());
		}
		if (!taskList.getTaskAtPos(taskPos).getDueTime()
				.equals(String.valueOf(dateBox.getValue().getTime()))) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setDueTime(
					String.valueOf(dateBox.getValue().getTime()));
		}
		if (taskList.getTaskAtPos(taskPos).getPriority() != priorityBox
				.getSelectedIndex()) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setPriority(
					priorityBox.getSelectedIndex());
		}

		if ((completedBox.getValue() == true)
				&& (taskList.getTaskAtPos(taskPos).getChecked() == 0)) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setChecked(1);
		}
		if ((completedBox.getValue() == false)
				&& (taskList.getTaskAtPos(taskPos).getChecked() == 1)) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setChecked(0);
		}

		if ((noDueBox.getValue() == true)
				&& (taskList.getTaskAtPos(taskPos).getNoDueTime() == 0)) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setNoDueTime(1);
		}
		if ((noDueBox.getValue() == false)
				&& (taskList.getTaskAtPos(taskPos).getNoDueTime() == 1)) {
			change = 1;
			taskList.getTaskAtPos(taskPos).setNoDueTime(0);
		}

		if (change == 1) {
			taskList.getTaskAtPos(taskPos).setLastUpdated(
					String.valueOf(date.getTime()));
		}
		update();
	}

	public void getText() {

		taskTitle.setText(taskList.getTaskAtPos(taskPos).getName());
		taskNotes.setText(taskList.getTaskAtPos(taskPos).getNote());
		if (taskList.getTaskAtPos(taskPos).getDueTime() == null) {
			Date date = new Date();
			dateBox.setValue(date);
		} else {
			Date date = new Date(Long.parseLong(taskList.getTaskAtPos(taskPos)
					.getDueTime()));
			dateBox.setValue(date);
		}

		if (taskList.getTaskAtPos(taskPos).getNoDueTime() == 1) {
			dateBox.setVisible(false);
		}
		if (taskList.getTaskAtPos(taskPos).getNoDueTime() == 0) {
			dateBox.setVisible(true);
		}

		priorityBox.setItemSelected(taskList.getTaskAtPos(taskPos)
				.getPriority(), true);

		if (taskList.getTaskAtPos(taskPos).getNoDueTime() == 1) {
			noDueBox.setValue(true);
		} else
			noDueBox.setValue(false);

		if (taskList.getTaskAtPos(taskPos).getChecked() == 1) {
			completedBox.setValue(true);
		} else
			completedBox.setValue(false);
	}

	public void refreshList() {
		taskList.sortTask();
		ArrayList<String> temps = new ArrayList<String>();
		taskNames = new ArrayList<String>();
		for (int i = 0; i < taskList.getCount(); i++) {
			TaskInfo temp = taskList.getTaskAtPos(i);

			// Window.alert(temp.getName());
			taskNames.add(temp.getName());
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		taskList = new TaskContainer();

		// Create list horizontal Panel
		listPanel = new VerticalPanel();
		taskPanel = new VerticalPanel();
		masterPanel = new HorizontalPanel();

		// Create a cell to render everytihng
		textCell = new TextCell();

		// //Create a CellList that uses the cell;
		cellList = new CellList<String>(textCell);
		cellList.setStyleName("gwt-centerText");

		scrollList = new ScrollPanel();
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				Scheduler.get().scheduleDeferred(
						new Scheduler.ScheduledCommand() {
							public void execute() {
								// layout stuff This works. Just need to add the
								// right code
								scrollList.setSize(
										"100%",
										""
												+ (Window.getClientHeight()
														- scrollList
																.getAbsoluteTop() - 5)
												+ "px");
							}
						});
			}

		});

		// Add a selection model to handle user selection.
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		cellList.setSelectionModel(selectionModel);

		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						String selected = selectionModel.getSelectedObject();
						if (selected != null) {
							if (validDate == 0) {
								if (taskPos != -1)
									saveText();
								taskPos = taskNames.indexOf(selected);
								getText();
							}
						}
					}
				});

		scrollList.add(cellList);

		setupTask();

		// Make visible
		DOM.getElementById("ToDoApp").getStyle().setDisplay(Display.BLOCK);
		
		
		// Create a handler for the sendButton and nameField
				class LogOutHandler implements ClickHandler, KeyUpHandler {
					/**
					 * Fired when the user clicks on the refresh.
					 */
					public void onClick(ClickEvent event) {
						Window.Location.reload();
					}

					@Override
					public void onKeyUp(KeyUpEvent event) {
						// TODO Auto-generated method stub

					}

				}
				;

		class SyncHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				// addNewTask();
				// Window.alert("Sync not implemented");
				if (validDate == 0) {
					saveText();
					TaskInfo[] toSave = new TaskInfo[taskList.getCount()];
					for (int i = 0; i < taskList.getCount(); i++) {
						toSave[i] = taskList.getTaskAtPos(i);
					}
					toSave[0].setUserId(String.valueOf(user));

					saveTaskSvc.saveTask(toSave, new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							Window.alert("Failed to save");
						}

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							// Window.alert("Save successful");
						}
					});
				} else {
					Window.alert("Select Valid Date");
				}
			}

			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub

			}

		}
		;
		
		LogOutHandler lHandler = new LogOutHandler();
		logoutButton.addClickHandler(lHandler);

		final SyncHandler sHandler = new SyncHandler();
		syncButton.addClickHandler(sHandler);
		
		// Create a handler for the sendButton and nameField
				class AddHandler implements ClickHandler, KeyUpHandler {
					/**
					 * Fired when the user clicks on the sendButton.
					 */
					public void onClick(ClickEvent event) {
						addNewTask();
						sHandler.onClick(event);
					}

					@Override
					public void onKeyUp(KeyUpEvent event) {
						// TODO Auto-generated method stub

					}

				}
				;
				
		AddHandler handler = new AddHandler();
		addButton.addClickHandler(handler);
		
		
		
		// Create a handler for the sendButton and nameField
		class RefreshHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				selectionModel.clear();
				taskList.clear();
				setupTask();
				taskPos = -1;
			}

			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub

			}

		}
		;
		
		RefreshHandler rhandler = new RefreshHandler();
		refreshButton.addClickHandler(rhandler);


		// taskPanel.add(addButton);
		taskPanel.addStyleName("gwt-ScrollList");
		taskPanel.setSize("100%", "100%");

		// Task Title
		final Label taskTitleLabel = new Label();
		taskTitleLabel.setText("Task Name:");
		taskPanel.add(taskTitleLabel);
		taskTitle.setStyleName("gwt-nameText");
		taskPanel.add(taskTitle);

		// Create a datebox
		final Label dateLabel = new Label();
		dateLabel.setText("Date Due:");
		taskPanel.add(dateLabel);
		@SuppressWarnings("deprecation")
		DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date today = new Date();
				// Calendar yesterday = Calendar.getInstance();
				// yesterday.add(Calendar.DATE, -1);

				if (event.getValue().before(today)) {
					if (((event.getValue().getYear() == today.getYear())
							&& (event.getValue().getDay() == today.getDay()) && (event
							.getValue().getMonth() == today.getMonth()))) {
						// do nothing
					} else {
						validDate = -1;
						Window.alert("Invalid Due Date");
					}
				} else {
					validDate = 0;
				}
			}
		});

		dateBox.addStyleName("gwt-dropBox");
		taskPanel.add(dateBox);

		// Task Notes
		final Label taskNotesLabel = new Label();
		taskNotesLabel.setText("Task Notes:");
		taskPanel.add(taskNotesLabel);
		taskNotes.setStyleName("gwt-textArea");
		taskPanel.add(taskNotes);

		// Priority DropBox
		final Label priorityLabel = new Label();
		priorityLabel.setText("Priority:");
		taskPanel.add(priorityLabel);
		priorityBox.setStyleName("gwt-dropBox");
		priorityBox.addItem("Low");
		priorityBox.addItem("Medium");
		priorityBox.addItem("High");
		taskPanel.add(priorityBox);

		// Completed Checkbox
		final Label checkLabel = new Label();
		checkLabel.setText("Completed");
		taskPanel.add(checkLabel);
		taskPanel.add(completedBox);

		// Completed Checkbox
		final Label NoDueLabel = new Label();
		NoDueLabel.setText("No Due Date");
		taskPanel.add(NoDueLabel);
		taskPanel.add(noDueBox);
		noDueBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue() == true)
					dateBox.setVisible(false);
				else
					dateBox.setVisible(true);
			}
		});

		// Add it to the root panel
		listPanel.addStyleName("gwt-ScrollList");
		listPanel.setWidth("100%");
		listPanel.add(scrollList);

		masterPanel.add(listPanel);
		masterPanel.add(taskPanel);
		masterPanel.setCellWidth(listPanel, "30%");
		masterPanel.setSize("100%", "100%");

		// Applying everything to RootPanel
		RootPanel.get("addButton").add(addButton);
		RootPanel.get("syncButton").add(syncButton);
		RootPanel.get("refreshButton").add(refreshButton);
		RootPanel.get("logoutButton").add(logoutButton);
		RootPanel.get("UserInterface").add(masterPanel);

		// Draft get item
		// System.out.print(priorityBox.getItemText(priorityBox.getSelectedIndex()));

		scrollList.setSize(
				"100%",
				""
						+ (Window.getClientHeight()
								- scrollList.getAbsoluteTop() - 5) + "px");

	}

}
