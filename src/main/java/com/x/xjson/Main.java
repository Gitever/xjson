/**
 * 
 * File Name: f.java 
 * Package Name: com.x.xjson
 * Date: 2016年5月23日 下午2:10:27
 */
package com.x.xjson;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author x
 *
 */
public class Main {
	protected Shell shell;
	private Text txtPara;
	private Text txtJson;
	private Text txtMDencode;
	private Text txtMDdecode;
	private Text txtBASEencode;
	private Text txtBASEdecode;
	private static String UTF8 = "utf-8";

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	/**
	 * 
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 650);
		shell.setText("SWT");
		
		// 得到屏幕的宽度和高度 
        int  screenHeight  =  Toolkit.getDefaultToolkit().getScreenSize().height;
        int  screenWidth  =  Toolkit.getDefaultToolkit().getScreenSize().width;
        // 得到Shell窗口的宽度和高度 
        int  shellHeight  =  shell.getBounds().height;
        int  shellWidth  =  shell.getBounds().width;
        // 如果窗口大小超过屏幕大小，让窗口与屏幕等大 
        if (shellHeight  >  screenHeight)
                 shellHeight  =  screenHeight;
        if (shellWidth  >  screenWidth)
                shellWidth  =  screenWidth;
       // 让窗口在屏幕中间显示 
        shell.setLocation(( (screenWidth  -  shellWidth)  /   2 ),((screenHeight  -  shellHeight)  /   2 ) );
		
		Label lblUrl = new Label(shell, SWT.NONE);
		lblUrl.setBounds(10, 10, 33, 17);
		lblUrl.setText("URL:");
		
		Label lblPara = new Label(shell, SWT.NONE);
		lblPara.setBounds(10, 33, 33, 17);
		lblPara.setText("Data:");
		
		final Combo cmbURL = new Combo(shell, SWT.NONE);
		cmbURL.setBounds(49, 10, 499, 25);
		
		String propUrl = PropUtil.getValue("url");
		if (propUrl != null && !"".equals(propUrl)) {
			String[] urls = propUrl.split(",");
			cmbURL.setItems(urls);
		}
		cmbURL.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				// dll 索引
				int index = cmbURL.getSelectionIndex();
				String propPara = PropUtil.getValue("param"+index);
				txtPara.setText("");
				
				if (propPara!= null) {
					txtPara.setText(Base64Util.decode(propPara));
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		Combo cmbCT = new Combo(shell, SWT.READ_ONLY);
		cmbCT.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		cmbCT.setItems(new String[]{"application/x-www-form-urlencoded","multipart/form-data","application/json","text/xml","",""});
		cmbCT.setBounds(647, 33, 127, 24);
		cmbCT.select(0);
		
		Combo cmbAccept = new Combo(shell, SWT.READ_ONLY);
		cmbAccept.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		cmbAccept.setItems(new String[]{"application/x-www-form-urlencoded","text/html","text/xml","application/x-javascript","application/json"});
		cmbAccept.setBounds(647, 64, 127, 25);
		cmbAccept.select(0);
		
		final Combo cmbType = new Combo(shell, SWT.READ_ONLY);
		cmbType.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		cmbType.setItems(new String[] {"GET", "POST"});
		cmbType.setBounds(647, 95, 127, 25);
		cmbType.select(1);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setBounds(561, 36, 73, 17);
		lblNewLabel.setText("ContentType");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.RIGHT);
		lblNewLabel_1.setBounds(561, 67, 73, 22);
		lblNewLabel_1.setText("Accept");
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.RIGHT);
		lblNewLabel_2.setBounds(598, 98, 36, 17);
		lblNewLabel_2.setText("Type");
		
		Button btnNewButton = new Button(shell, SWT.CENTER);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String url = cmbURL.getText();
				String para = txtPara.getText();
				String respJson = null;
				if ("".equals(url)) {
					JOptionPane.showMessageDialog(null, "请求啥呢");
					return;
				}
				
				if (cmbType.getSelectionIndex() == 0) { // get
					respJson = new HttpAccessor().getResponseByGet(url, UTF8);
				}else{ // post
					respJson = new HttpAccessor().getResponseByPost(url, para, UTF8);
				}
				
				txtJson.setText(respJson);
				String fotmatStr = FormatJSON.format(txtJson.getText());
				txtJson.setText(fotmatStr);
				
				int index = cmbURL.getItems().length;
				PropUtil.updateProperties("url", url + ",");				
				PropUtil.updateProperties("param"+ (index <= 0 ? 0 : index), Base64Util.encode(para));
				cmbURL.add(url, index);
			}
		});
		btnNewButton.setBounds(694, 5, 80, 27);
		btnNewButton.setText("go");
		
		txtPara = new Text(shell, SWT.BORDER | SWT.WRAP);
		txtPara.setBounds(49, 36, 499, 96);
		txtPara.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
					txtPara.selectAll();
				}
			}
		});
		
		Button btnMd = new Button(shell, SWT.CHECK);
		btnMd.setBounds(562, 10, 49, 17);
		btnMd.setText("MD5");	
		
		Button btnBase = new Button(shell, SWT.CHECK);
		btnBase.setBounds(617, 10, 65, 17);
		btnBase.setText("BASE64");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(10, 135, 764, 467);
		
		TabItem tbtmJson = new TabItem(tabFolder, SWT.NONE);
		tbtmJson.setText("json");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmJson.setControl(composite);
		
		txtJson = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);		
		txtJson.setBounds(10, 40, 736, 387);
		txtJson.setFont(SWTResourceManager.getFont("Consolas", 11, SWT.NORMAL));
		
		Button btnJson = new Button(composite, SWT.NONE);
		btnJson.setBounds(10, 10, 80, 19);
		btnJson.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fotmatStr = FormatJSON.format(txtJson.getText());
				txtJson.setText(fotmatStr);
			}
		});
		btnJson.setText("format");
		
		Button button = new Button(composite, SWT.NONE);
		button.setBounds(96, 10, 80, 19);
		button.setText("/");
		
		Button btnn = new Button(composite, SWT.NONE);
		btnn.setBounds(182, 10, 80, 19);
		btnn.setText("/n");
		
		final Button ckBase = new Button(composite, SWT.CHECK);
		ckBase.setBounds(348, 11, 65, 17);
		ckBase.setText("BASE64");
		ckBase.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(ckBase.getSelection()){
					if (!"".equals(txtJson.getText())) {
						txtJson.setText(Base64Util.encode(txtJson.getText()));
					}
				}else{
					if (!"".equals(txtJson.getText())) {
						txtJson.setText(Base64Util.decode(txtJson.getText()));
					}
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		txtJson.addKeyListener(new KeyListener() {			
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
		            txtJson.selectAll();
		        }
			}
		});
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("xml");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_1);
		
		Tree tree = new Tree(composite_1, SWT.BORDER);
		tree.setBounds(10, 10, 736, 417);
		
		TabItem tbtmNewItem_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("view");
		
		TabItem tbtmMd = new TabItem(tabFolder, SWT.NONE);
		tbtmMd.setText("MD5");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmMd.setControl(composite_2);
		
		Label lblNewLabel_3 = new Label(composite_2, SWT.NONE);
		lblNewLabel_3.setBounds(10, 10, 36, 17);
		lblNewLabel_3.setText("原文：");
		
		txtMDencode = new Text(composite_2, SWT.BORDER | SWT.WRAP);
		txtMDencode.setBounds(46, 7, 684, 240);
		txtMDencode.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent arg0) {

			}
			
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
					txtMDencode.selectAll();
				}
			}
		});
		
		Label label = new Label(composite_2, SWT.NONE);
		label.setBounds(10, 308, 36, 17);
		label.setText("密文：");
		
		txtMDdecode = new Text(composite_2, SWT.BORDER);
		txtMDdecode.setBounds(46, 286, 684, 51);
		txtMDdecode.setFont(SWTResourceManager.getFont("微软雅黑", 23, SWT.NORMAL));
		
		Button btnMDencode = new Button(composite_2, SWT.NONE);
		btnMDencode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!"".equals(txtMDencode.getText())) {
					String encode = MD5Util.getMD5(txtMDencode.getText());
					txtMDdecode.setText(encode);
				}
			}
		});
		btnMDencode.setToolTipText("没有密文库");
		btnMDencode.setBounds(46, 253, 80, 27);
		btnMDencode.setText("加密");
		
		TabItem tbtmBase = new TabItem(tabFolder, SWT.NONE);
		tbtmBase.setText("BASE64");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmBase.setControl(composite_3);
		
		Label label_1 = new Label(composite_3, SWT.NONE);
		label_1.setBounds(10, 10, 36, 17);
		label_1.setText("原文：");
		
		Label label_2 = new Label(composite_3, SWT.NONE);
		label_2.setBounds(10, 218, 36, 17);
		label_2.setText("密文：");
		
		txtBASEencode = new Text(composite_3, SWT.BORDER | SWT.WRAP);
		txtBASEencode.setBounds(52, 10, 694, 166);
		txtBASEencode.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent arg0) {

			}
			
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
					txtBASEencode.selectAll();
				}
			}
		});
		
		txtBASEdecode = new Text(composite_3, SWT.BORDER | SWT.WRAP);
		txtBASEdecode.setBounds(52, 215, 694, 212);
		txtBASEdecode.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent arg0) {

			}
			
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
					txtBASEdecode.selectAll();
				}
			}
		});
		
		Button btnBencode = new Button(composite_3, SWT.NONE);
		btnBencode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!"".equals(txtBASEencode.getText())) {
					String decode = Base64Util.encode(txtBASEencode.getText());
					txtBASEdecode.setText(decode);
				}
			}
		});
		btnBencode.setBounds(52, 182, 80, 27);
		btnBencode.setText("加密");
		
		Button btnBdecode = new Button(composite_3, SWT.NONE);
		btnBdecode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!"".equals(txtBASEdecode.getText())) {
					String encode = Base64Util.decode(txtBASEdecode.getText());
					txtBASEencode.setText(encode);
				}
			}
		});
		btnBdecode.setBounds(138, 182, 80, 27);
		btnBdecode.setText("解密");
		
		TabItem tbtmAes = new TabItem(tabFolder, SWT.NONE);
		tbtmAes.setText("AES");
	}
}
