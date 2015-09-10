/*  1:   */ package org.apache.log4j.net;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.net.ServerSocket;
/*  5:   */ import java.net.Socket;
/*  6:   */ import org.apache.log4j.Category;
/*  7:   */ import org.apache.log4j.LogManager;
/*  8:   */ import org.apache.log4j.Logger;
/*  9:   */ import org.apache.log4j.PropertyConfigurator;
/* 10:   */ import org.apache.log4j.xml.DOMConfigurator;
/* 11:   */ 
/* 12:   */ public class SimpleSocketServer
/* 13:   */ {
/* 14:46 */   static Logger cat = Logger.getLogger(SimpleSocketServer.class);
/* 15:   */   static int port;
/* 16:   */   
/* 17:   */   public static void main(String[] argv)
/* 18:   */   {
/* 19:53 */     if (argv.length == 2) {
/* 20:54 */       init(argv[0], argv[1]);
/* 21:   */     } else {
/* 22:56 */       usage("Wrong number of arguments.");
/* 23:   */     }
/* 24:   */     try
/* 25:   */     {
/* 26:60 */       cat.info("Listening on port " + port);
/* 27:61 */       ServerSocket serverSocket = new ServerSocket(port);
/* 28:   */       for (;;)
/* 29:   */       {
/* 30:63 */         cat.info("Waiting to accept a new client.");
/* 31:64 */         Socket socket = serverSocket.accept();
/* 32:65 */         cat.info("Connected to client at " + socket.getInetAddress());
/* 33:66 */         cat.info("Starting new socket node.");
/* 34:67 */         new Thread(new SocketNode(socket, LogManager.getLoggerRepository()), "SimpleSocketServer-" + port).start();
/* 35:   */       }
/* 36:   */     }
/* 37:   */     catch (Exception e)
/* 38:   */     {
/* 39:71 */       e.printStackTrace();
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   static void usage(String msg)
/* 44:   */   {
/* 45:77 */     System.err.println(msg);
/* 46:78 */     System.err.println("Usage: java " + SimpleSocketServer.class.getName() + " port configFile");
/* 47:   */     
/* 48:80 */     System.exit(1);
/* 49:   */   }
/* 50:   */   
/* 51:   */   static void init(String portStr, String configFile)
/* 52:   */   {
/* 53:   */     try
/* 54:   */     {
/* 55:85 */       port = Integer.parseInt(portStr);
/* 56:   */     }
/* 57:   */     catch (NumberFormatException e)
/* 58:   */     {
/* 59:87 */       e.printStackTrace();
/* 60:88 */       usage("Could not interpret port number [" + portStr + "].");
/* 61:   */     }
/* 62:91 */     if (configFile.endsWith(".xml")) {
/* 63:92 */       DOMConfigurator.configure(configFile);
/* 64:   */     } else {
/* 65:94 */       PropertyConfigurator.configure(configFile);
/* 66:   */     }
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SimpleSocketServer
 * JD-Core Version:    0.7.0.1
 */