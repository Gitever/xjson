/**
 * 
 * File Name: a.java 
 * Package Name: com.x.xjson
 * Date: 2016年5月20日 上午11:16:42
 */
package com.x.xjson;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

/**
 * @author x
 *
 */
public class Main {

	protected Shell shell;
	private Text txtURL;
	private Text txtPara;
	private Text txtJson;
	private Text txtMDencode;
	private Text txtMDdecode;
	private Text txtBASEencode;
	private Text txtBASEdecode;

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
		shell.setText("SWT Application");
		
		Label lblUrl = new Label(shell, SWT.NONE);
		lblUrl.setBounds(10, 10, 33, 17);
		lblUrl.setText("URL:");
		
		txtURL = new Text(shell, SWT.BORDER);
		txtURL.setBounds(49, 7, 455, 23);
		
		Label lblPara = new Label(shell, SWT.NONE);
		lblPara.setBounds(10, 33, 33, 17);
		lblPara.setText("Data:");
		
		Combo cmbCT = new Combo(shell, SWT.READ_ONLY);
		cmbCT.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		cmbCT.setItems(new String[]{"application/x-www-form-urlencoded","multipart/form-data","application/json","text/xml","",""});
		cmbCT.setBounds(647, 33, 127, 24);
		//cmbCT.setText("application/json");
		cmbCT.select(0);
		
		Combo cmbAccept = new Combo(shell, SWT.READ_ONLY);
		cmbAccept.setFont(SWTResourceManager.getFont("微软雅黑", 8, SWT.NORMAL));
		cmbAccept.setItems(new String[]{"application/x-www-form-urlencoded","text/html","text/xml","application/x-javascript","application/json"});
		cmbAccept.setBounds(647, 64, 127, 25);
		//cmbAccept.setText("application/json");
		cmbAccept.select(0);
		
		Combo cmbType = new Combo(shell, SWT.READ_ONLY);
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
				String url = txtURL.getText();
				String para = txtPara.getText();
				try {
					//String respJson = new HttpAccessor().getResponseByPost(url, cmbCT.getText(), "utf-8", new String[]{"params", para});
					
					String respJson = new HttpAccessor().getPost0(url, para, "utf-8");
					
					txtJson.setText(respJson);
					String fotmatStr = FormatJSON.format(txtJson.getText());
					txtJson.setText(fotmatStr);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				MessageDialog.openConfirm(Display.getCurrent().getActiveShell(),"确认","确实要保退出吗？");
			}
		});
		btnNewButton.setBounds(694, 5, 80, 27);
		btnNewButton.setText("请求");
		
		txtPara = new Text(shell, SWT.BORDER | SWT.WRAP);
		txtPara.setBounds(49, 36, 499, 96);
		txtPara.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
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
		btnJson.setText("格式化");
		
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
					System.out.println("yes");
				}else{
					System.out.println("no");
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		txtJson.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
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
		tbtmNewItem_1.setText("视图");
		
		TabItem tbtmMd = new TabItem(tabFolder, SWT.NONE);
		tbtmMd.setText("MD5");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmMd.setControl(composite_2);
		
		Label lblNewLabel_3 = new Label(composite_2, SWT.NONE);
		lblNewLabel_3.setBounds(10, 10, 36, 17);
		lblNewLabel_3.setText("原文：");
		
		txtMDencode = new Text(composite_2, SWT.BORDER | SWT.WRAP);
		txtMDencode.setBounds(46, 7, 684, 207);
		txtMDencode.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
					txtMDencode.selectAll();
				}
			}
		});
		
		Label label = new Label(composite_2, SWT.NONE);
		label.setBounds(10, 271, 36, 17);
		label.setText("密文：");
		
		txtMDdecode = new Text(composite_2, SWT.BORDER);
		txtMDdecode.setBounds(46, 255, 684, 51);
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
		btnMDencode.setBounds(46, 222, 80, 27);
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
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
		btnBencode.setBounds(10, 182, 80, 27);
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
		btnBdecode.setBounds(106, 182, 80, 27);
		btnBdecode.setText("解密");
		
		TabItem tbtmAes = new TabItem(tabFolder, SWT.NONE);
		tbtmAes.setText("AES");
		
		Button ckByte = new Button(shell, SWT.CHECK);
		ckByte.setToolTipText("流传输");
		ckByte.setBounds(510, 10, 49, 17);
		ckByte.setText("Byte");
	}
}
