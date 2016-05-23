package com.x.xjson;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;

public class t1 {

	protected Shell shell;
	 private Text toAddrText;
	    private Text topicText;
	    private Text ccText;
	    private Text labelText;
	    private Button sendBtn;
	    private Button timSendBtn;
	    private Button saveBtn;
	    private Composite composite;
	    private Group group;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			t1 window = new t1();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 450);
		shell.setLayout(new GridLayout(5, false));
		
		Label toLabel = new Label(shell, SWT.NONE);
        toLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toLabel.setText("person");
        
        toAddrText = new Text(shell, SWT.BORDER);
        toAddrText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        
        composite = new Composite(shell, SWT.NONE);
        composite.setLayout(new FillLayout(SWT.HORIZONTAL));
        GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 4);
        gd_composite.widthHint = 171;
        composite.setLayoutData(gd_composite);
        
        group = new Group(composite, SWT.NONE);
        group.setText("list");
        group.setLayout(new FillLayout(SWT.HORIZONTAL));
        
        List list = new List(group, SWT.BORDER);
        list.setItems(new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p"});
        Label topiclabel = new Label(shell, SWT.NONE);
        topiclabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        topiclabel.setText("主题");
        
        topicText = new Text(shell, SWT.BORDER);
        topicText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        
        Label ccLabel = new Label(shell, SWT.NONE);
        ccLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        ccLabel.setText("抄送");
        
        ccText = new Text(shell, SWT.BORDER);
        ccText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        
        
        Label contentLabel = new Label(shell, SWT.NONE);
        contentLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        contentLabel.setText("内容");
        
        
        labelText = new Text(shell, SWT.BORDER);
        //GridData gd_labelText = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
        GridData gd_labelText = new GridData(GridData.FILL_BOTH);
        gd_labelText.heightHint = 232;
        labelText.setLayoutData(gd_labelText);
        new Label(shell, SWT.NONE);
        
        sendBtn = new Button(shell, SWT.NONE);
        sendBtn.setText("发送");
        
        timSendBtn = new Button(shell, SWT.NONE);
        timSendBtn.setText("定时发送");
        
        saveBtn = new Button(shell, SWT.NONE);
        saveBtn.setText("存草稿");
        new Label(shell, SWT.NONE);
	}
}
