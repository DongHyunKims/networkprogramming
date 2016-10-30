package com.mail.client;


import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class SendMail extends javax.swing.JFrame {

	  // Used by addNotify
	  boolean frameSizeAdjusted = false;
	  private JLabel idLabel;
	  private JLabel pwdLabel;
	 // private JLabel portLabel;
	  private JLabel fromLabel;
	  private JLabel toLabel; 
	  private JLabel subLabel;
	  private JLabel smtpLabel;	  
	  private JTextField idText;	  
	  private JTextField pwdText;
	  private JTextField senderText;
	  private JTextField receiverText;
	  private JTextField subjectText;
	  private JComboBox smtpServer; 
	  private JScrollPane scrollPane2;
	  private JTextArea bodyContent;
	  private JButton sendBtn;
	  private JButton cancelBtn;
	  private JScrollPane scrollPane;
	  //private JList outputList;
	  private JEditorPane serverLogPane;
	  
	  private SMTPClient smtpClient;




	  private DefaultListModel defaultmodel;




	  public SendMail() {

		  
	    setTitle("Mail");
	    getContentPane().setLayout(null);
	    setSize(736, 380);
	    setVisible(false);
	    
	    
	    
	    idLabel = new JLabel();
	    idLabel.setText("ID:");
	    getContentPane().add(idLabel);
	    idLabel.setBounds(12, 12, 36, 12);
	    
	    
	    
	    pwdLabel = new JLabel();
	    pwdLabel.setText("PWD:");
	    getContentPane().add(pwdLabel);
	    pwdLabel.setBounds(12, 48, 36, 12);
	    
	    
	    /*
	    fromLabel = new JLabel();
	    fromLabel.setText("From:");
	    getContentPane().add(fromLabel);
	    fromLabel.setBounds(12, 84, 36, 12);
	    */
	    
	    
	    toLabel = new JLabel();
	    toLabel.setText("To:");
	    getContentPane().add(toLabel);
	    toLabel.setBounds(12,84, 36, 12);
	    
	    subLabel = new JLabel();
	    subLabel.setText("Sub:");
	    getContentPane().add(subLabel);
	    subLabel.setBounds(12, 120, 48, 12);
	    
	    smtpLabel = new JLabel();
	    smtpLabel.setText("SMTP Server:");
	    getContentPane().add(smtpLabel);
	    smtpLabel.setBounds(12, 156, 84, 12);
	    /*
	    portLabel = new JLabel();
	    portLabel.setText("Port:");
	    getContentPane().add(portLabel);
	    smtpLabel.setBounds(12, 228, 84, 12);
	    */
	    
	    
	    idText = new JTextField();
	    getContentPane().add(idText);
	    idText.setBounds(96, 12, 300, 24);
	    	    
	    pwdText = new JTextField();
	    getContentPane().add(pwdText);
	    pwdText.setBounds(96, 48, 300, 24);
	    
	    /*
	    senderText = new JTextField();
	    getContentPane().add(senderText);
	    senderText.setBounds(96, 84, 300, 24);
	    */

	    receiverText = new JTextField();
	    getContentPane().add(receiverText);
	    receiverText.setBounds(96, 84, 300, 24);
	    
	    subjectText = new JTextField();
	    getContentPane().add(subjectText);
	    subjectText.setBounds(96, 120, 300, 24);
	    
	    
	    String [] smtpHostList = { "smtp.gmail.com", "smtp.mail.yahoo.com", "smtp.naver.com"};
	    smtpServer = new JComboBox(smtpHostList);
	    getContentPane().add(smtpServer);
	    smtpServer.setBounds(96, 156, 300, 24);

	    
	    scrollPane2 = new JScrollPane();
	    getContentPane().add(scrollPane2);
	    scrollPane2.setBounds(12, 192, 384, 108);
	    
	    bodyContent = new JTextArea();
	    bodyContent.setText("Enter your message here.");
	    scrollPane2.getViewport().add(bodyContent);
	    bodyContent.setBounds(0, 0, 381, 105);
	    
	    sendBtn = new JButton();
	    sendBtn.setText("Send");
	    sendBtn.setActionCommand("Send");
	    getContentPane().add(sendBtn);
	    sendBtn.setBounds(60, 320, 132, 24);
	    
	    cancelBtn = new JButton();
	    cancelBtn.setText("Cancel");
	    cancelBtn.setActionCommand("Cancel");
	    getContentPane().add(cancelBtn);
	    cancelBtn.setBounds(216, 320, 120, 24);
	    
	    scrollPane = new JScrollPane();
	    getContentPane().add(scrollPane);
	    scrollPane.setBounds(408, 12, 312, 328);
	    
	    /*
	    outputList = new JList();
	    getContentPane().add(outputList);
	    outputList.setBounds(408, 12, 309, 323);
	*/
	    
	    serverLogPane  = new JEditorPane();
	    getContentPane().add(serverLogPane);
	    serverLogPane.setBounds(408, 12, 309, 323);

	    SymAction lSymAction = new SymAction();
	    sendBtn.addActionListener(lSymAction);
	    cancelBtn.addActionListener(lSymAction);
	    //defaultmodel = new DefaultListModel();
	    //outputList.setModel(defaultmodel);
	    //defaultmodel.addElement("Server output displayed here:");
	    serverLogPane.setText("Server output displayed here:");
	    
	    
	    scrollPane.getViewport().setView(serverLogPane);
	    scrollPane2.getViewport().setView(bodyContent);
	  }


	  public void setVisible(boolean check) {
	    if (check)
	      setLocation(50, 50);
	    super.setVisible(check);
	  }

	
	  static public void main(String args[]) {
	    (new SendMail()).show();
	  }
	  
	  
	  
	  
	  
	  
	  


	  public void addNotify() {
	    // Record the size of the window prior to
	    // calling parents addNotify.
	    Dimension size = getSize();

	    super.addNotify();

	    if (frameSizeAdjusted)
	      return;
	    frameSizeAdjusted = true;

	    // Adjust size of frame according to the
	    // insets and menu bar
	    Insets insets = getInsets();
	    JMenuBar menuBar = getRootPane().getJMenuBar();
	    int menuBarHeight = 0;
	    if (menuBar != null)
	      menuBarHeight = menuBar.getPreferredSize().height;
	    setSize(insets.left + insets.right + size.width, insets.top
	        + insets.bottom + size.height + menuBarHeight);
	  }



	  /**
	   * Internal class created by VisualCafe to route the events to the correct
	   * functions.
	   * 
	   * @author VisualCafe
	   * @version 1.0
	   */
	  class SymAction implements java.awt.event.ActionListener {

	    /**
	     * Route the event to the correction method.
	     * 
	     * @param event
	     *            The event.
	     */
	    public void actionPerformed(java.awt.event.ActionEvent event) {
	      Object object = event.getSource();
	      if (object == sendBtn)
	        sendActionPerformed(event);
	      else if (object == cancelBtn)
	    	  cancelActionPerformed(event);
	    }
	  }


	

	  
	
	  void sendActionPerformed(ActionEvent event) {

		  String hostName = (String)smtpServer.getSelectedItem();
		  String senderEmail = idText.getText();
		  String senderPwd = pwdText.getText();
		  String recieverEmail = receiverText.getText();
		  String content =  bodyContent.getText();
		  String sub = subjectText.getText();
		  
		  System.out.println("hostName : " + hostName);
		  System.out.println("senderEmail : " + senderEmail);
		  System.out.println("senderPwd : "+senderPwd);
		  System.out.println("recieverEmail : " + recieverEmail);
		  System.out.println("content : " + content);
		  System.out.println("sub : " + sub);
		  
		  smtpClient = new SMTPClient(hostName, senderEmail, senderPwd, recieverEmail, content, sub);
		  String serverLog = smtpClient.connectMail();
		  System.out.println("serverLog " + serverLog );
		  
		  serverLogPane.setText(serverLog + "\n");
		  
	
	    	
	    	
	  }


	  
	  
	  //cancle btn Listener
	  void cancelActionPerformed(java.awt.event.ActionEvent event) {
	    
		  if(smtpClient != null){
		  smtpClient.closeAll();
		  }
		  System.exit(0);
	    

	  }
	}