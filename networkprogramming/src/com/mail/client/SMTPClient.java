package com.mail.client;
import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.binary.Base64;

//import org.apache.commons.codec.binary.Base64; 
public class SMTPClient
{
    
		private String hostname;
		private int port;
		private String senderEmail;
		private String senderPassword;
		private String recieverEmail;
		private String content;
		private String subject;
		private String serverLog;
		private String clientLog;
		private BufferedReader inFromServer;
		private SSLSocket sock;
		private DataOutputStream outToServer;
		
		

	public SMTPClient(String hostname, String senderEmail,
				String senderPassword, String recieverEmail, String content,
				String subject) {
			super();
			this.hostname = hostname;
			this.senderEmail = senderEmail;
			this.senderPassword = senderPassword;
			this.recieverEmail = recieverEmail;
			this.content = content;
			this.subject = subject;
			serverLog = "";
			clientLog = "";
		}


	public SMTPClient(String hostname, int port, String senderEmail,
				String senderPassword, String recieverEmail, String content,
				String subject) {
			super();
			this.hostname = hostname;
			this.port = port;
			this.senderEmail = senderEmail;
			this.senderPassword = senderPassword;
			this.recieverEmail = recieverEmail;
			this.content = content;
			this.subject = subject;
			serverLog = "";
			clientLog = "";
		}

	public SMTPClient(String hostname, int port, String senderEmail,
				String senderPassword, String recieverEmail, String content,
				String subject, String serverLog, String clientLog) {
			super();
			this.hostname = hostname;
			this.port = port;
			this.senderEmail = senderEmail;
			this.senderPassword = senderPassword;
			this.recieverEmail = recieverEmail;
			this.content = content;
			this.subject = subject;
			this.serverLog = serverLog;
			this.clientLog = clientLog;
		}




	public SMTPClient() {
		
		super();
		
		serverLog = "";
		clientLog = "";
	}

	@Override
	public String toString() {
		return "SMTPClient [hostname=" + hostname + ", port=" + port
				+ ", senderEmail=" + senderEmail + ", senderPassword="
				+ senderPassword + ", recieverEmail=" + recieverEmail
				+ ", content=" + content + ", subject=" + subject
				+ ", serverLog=" + serverLog + ", clientLog=" + clientLog + "]";
	}




	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getServerLog() {
		return serverLog;
	}

	public void setServerLog(String serverLog) {
		this.serverLog = serverLog;
	}

	public String getClientLog() {
		return clientLog;
	}

	public void setClientLog(String clientLog) {
		this.clientLog = clientLog;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}
	
	public String getRecieverEmail() {
		return recieverEmail;
	}

	public void setRecieverEmail(String reciever) {
		this.recieverEmail = reciever;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String connectMail(){
    	//�뵜�젅�씠 0.5  
		int delay =500;
			
		  //蹂댁븞�냼耳볦쓣 �깮�꽦�븯湲� �쐞�븳 媛앹껜 �뙥�넗由ъ씠�떎.
          SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
          //SSLSocket sock;
          
          
		try {
			selectPort(hostname);
			//createSokcket 硫붿냼�뱶 �샇異쒖쓣 �넻�빐 SSLSocket �씤�뒪�꽩�뒪瑜� �깮�꽦�븷 �닔 �엳�떎. 
			sock = (SSLSocket) sslsocketfactory.createSocket(hostname, port);
	         //�꽌踰꾨줈 遺��꽣 媛��졇�삩�떎. �꽌踰꾩쓽 log瑜� �궓湲곌린 �쐞�븳�넻濡�.
	         inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	         
	         //�꽌踰꾩쓽 濡쒓렇瑜� �궓湲� �벐�젅�뱶瑜� �깮�꽦�븳�떎.
	          Thread tr = new Thread(new Runnable()
	          {
	               public void run()
	               {
	                    try
	                    {
	                    	
	                    	String tempLine = "";     
	                    	//inFromServer �넻濡쒕�� �넻�빐 �꽌踰꾩쓽 �쓳�떟�쓣 諛쏆븘�삩�떎. 
	                         while((tempLine = inFromServer.readLine()) != null){     	 
	                        	 //�뿬�윭媛�吏� �삁�쇅泥섎━媛� �븘�슂�븯�떎. 
	                        	 if(tempLine.startsWith("535-5.7.8")){
	                        		 serverLog = "SEVER : Login Error"; 
	                        		 break;
	                        	 }                     	 
	                        	 System.out.println("SERVER: "+tempLine);
	                        	 //諛쏆븘�삩 �쓳�떟�쓣 紐⑥븘�꽌 濡쒓렇濡� 留뚮뱺�떎.
	                        	 serverLog += "SEVER : " + tempLine + "\n";
	                         }
	                    }
	                    catch (IOException e)
	                    {
	                         e.printStackTrace();
	                    }
	               }
	          });
	          
	          tr.start();
	          
	          //硫붿씪 �꽌踰꾨줈 �굹媛��뒗 �넻濡�. 
	          outToServer = new DataOutputStream(sock.getOutputStream());
	          
	          send("EHLO "+ hostname+"\r\n",outToServer);
	          //�뒪�젅�뱶瑜� ��湲곗긽�깭濡� 留뚮뱾�뼱以��떎.
	          Thread.sleep(delay);
	          
	          
	          // 濡쒓렇�씤 �븯湲곗쐞�븳 �젙蹂�,Base64濡� �씤肄붾뵫 �썑 �젙蹂대�� 蹂대궡以��떎. 
	          String encodedToken =  Base64.encodeBase64String(senderEmail.getBytes());
	          send("AUTH LOGIN " + encodedToken +"\r\n", outToServer);
	          Thread.sleep(delay);
	          String encodedPassword =  Base64.encodeBase64String(senderPassword.getBytes());
	          send(encodedPassword + "\r\n", outToServer);
	          Thread.sleep(delay);
	          if(serverLog.equals("Login Error")){
	        	  return serverLog;
	          }
	          //蹂대궡�뒗 �씠硫붿씪 二쇱냼 
	          send("MAIL FROM: <"+senderEmail+">\r\n", outToServer);
	          Thread.sleep(delay);
	          //諛쏅뒗 �씠硫붿씪 二쇱냼 
	          send("RCPT TO: <"+recieverEmail+">\r\n", outToServer);
	          Thread.sleep(delay);
	          //�뜲�씠�꽣
	          send("DATA\r\n", outToServer);
	          Thread.sleep(delay);
	          //�젣紐�
	          send("Subject: "+subject+"\r\n", outToServer);
	          Thread.sleep(delay);
	          //�궡�슜
	          send(content, outToServer);
	          Thread.sleep(delay);
	          //硫붿꽭吏��쓽 留덉�留됱쓣 �굹���깂 
	          send("\r\n.\r\n", outToServer);
	          Thread.sleep(delay);
	          //�걹 
	          send("QUIT\r\n", outToServer);
	          
	          
	 		 //closeAll(sock,outToServer,inFromServer);
	          
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
         return serverLog;
     }
     //硫붿꽭吏�瑜� 蹂대궡�뒗 硫붿냼�뱶.
     private void send(String s, DataOutputStream ots) throws Exception
     {
    	  
    	 ots.writeBytes(s);
    	 //�겢�씪�씠�뼵�듃 濡쒓렇�룄 留뚮뱾�뼱 以��떎.
    	 clientLog +="Client : " + s + "\n";
         System.out.println("CLIENT: "+s);
        
     }
     
     //�룷�듃�꽑�깮 硫붿냼�뱶.
     private void selectPort(String hostName){
    	 
    	 if(hostName.equals("smtp.gmail.com") | hostName.equals("smtp.daum.net") | hostName.equals("smtp.naver.com")){
    		 port = 465;
    	 }
    	 

     }
     //紐⑤뱺 �넻濡� 諛� �냼耳볦쓣 �떕�뒗 硫붿냼�뱶 
     public void closeAll(){
  
    	 try {
    	  	outToServer.close();
			inFromServer.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 
     }
     
}
