/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.net.InetAddress;
/*   6:    */ import java.net.ServerSocket;
/*   7:    */ import java.net.Socket;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ import org.apache.log4j.Category;
/*  10:    */ import org.apache.log4j.Hierarchy;
/*  11:    */ import org.apache.log4j.Level;
/*  12:    */ import org.apache.log4j.LogManager;
/*  13:    */ import org.apache.log4j.Logger;
/*  14:    */ import org.apache.log4j.PropertyConfigurator;
/*  15:    */ import org.apache.log4j.spi.LoggerRepository;
/*  16:    */ import org.apache.log4j.spi.RootLogger;
/*  17:    */ 
/*  18:    */ public class SocketServer
/*  19:    */ {
/*  20: 90 */   static String GENERIC = "generic";
/*  21: 91 */   static String CONFIG_FILE_EXT = ".lcf";
/*  22: 93 */   static Logger cat = Logger.getLogger(SocketServer.class);
/*  23:    */   static SocketServer server;
/*  24:    */   static int port;
/*  25:    */   Hashtable hierarchyMap;
/*  26:    */   LoggerRepository genericHierarchy;
/*  27:    */   File dir;
/*  28:    */   
/*  29:    */   public static void main(String[] argv)
/*  30:    */   {
/*  31:105 */     if (argv.length == 3) {
/*  32:106 */       init(argv[0], argv[1], argv[2]);
/*  33:    */     } else {
/*  34:108 */       usage("Wrong number of arguments.");
/*  35:    */     }
/*  36:    */     try
/*  37:    */     {
/*  38:111 */       cat.info("Listening on port " + port);
/*  39:112 */       ServerSocket serverSocket = new ServerSocket(port);
/*  40:    */       for (;;)
/*  41:    */       {
/*  42:114 */         cat.info("Waiting to accept a new client.");
/*  43:115 */         Socket socket = serverSocket.accept();
/*  44:116 */         InetAddress inetAddress = socket.getInetAddress();
/*  45:117 */         cat.info("Connected to client at " + inetAddress);
/*  46:    */         
/*  47:119 */         LoggerRepository h = (LoggerRepository)server.hierarchyMap.get(inetAddress);
/*  48:120 */         if (h == null) {
/*  49:121 */           h = server.configureHierarchy(inetAddress);
/*  50:    */         }
/*  51:124 */         cat.info("Starting new socket node.");
/*  52:125 */         new Thread(new SocketNode(socket, h)).start();
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57:129 */       e.printStackTrace();
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   static void usage(String msg)
/*  62:    */   {
/*  63:136 */     System.err.println(msg);
/*  64:137 */     System.err.println("Usage: java " + SocketServer.class.getName() + " port configFile directory");
/*  65:    */     
/*  66:139 */     System.exit(1);
/*  67:    */   }
/*  68:    */   
/*  69:    */   static void init(String portStr, String configFile, String dirStr)
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:145 */       port = Integer.parseInt(portStr);
/*  74:    */     }
/*  75:    */     catch (NumberFormatException e)
/*  76:    */     {
/*  77:148 */       e.printStackTrace();
/*  78:149 */       usage("Could not interpret port number [" + portStr + "].");
/*  79:    */     }
/*  80:152 */     PropertyConfigurator.configure(configFile);
/*  81:    */     
/*  82:154 */     File dir = new File(dirStr);
/*  83:155 */     if (!dir.isDirectory()) {
/*  84:156 */       usage("[" + dirStr + "] is not a directory.");
/*  85:    */     }
/*  86:158 */     server = new SocketServer(dir);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public SocketServer(File directory)
/*  90:    */   {
/*  91:164 */     this.dir = directory;
/*  92:165 */     this.hierarchyMap = new Hashtable(11);
/*  93:    */   }
/*  94:    */   
/*  95:    */   LoggerRepository configureHierarchy(InetAddress inetAddress)
/*  96:    */   {
/*  97:171 */     cat.info("Locating configuration file for " + inetAddress);
/*  98:    */     
/*  99:    */ 
/* 100:174 */     String s = inetAddress.toString();
/* 101:175 */     int i = s.indexOf("/");
/* 102:176 */     if (i == -1)
/* 103:    */     {
/* 104:177 */       cat.warn("Could not parse the inetAddress [" + inetAddress + "]. Using default hierarchy.");
/* 105:    */       
/* 106:179 */       return genericHierarchy();
/* 107:    */     }
/* 108:181 */     String key = s.substring(0, i);
/* 109:    */     
/* 110:183 */     File configFile = new File(this.dir, key + CONFIG_FILE_EXT);
/* 111:184 */     if (configFile.exists())
/* 112:    */     {
/* 113:185 */       Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
/* 114:186 */       this.hierarchyMap.put(inetAddress, h);
/* 115:    */       
/* 116:188 */       new PropertyConfigurator().doConfigure(configFile.getAbsolutePath(), h);
/* 117:    */       
/* 118:190 */       return h;
/* 119:    */     }
/* 120:192 */     cat.warn("Could not find config file [" + configFile + "].");
/* 121:193 */     return genericHierarchy();
/* 122:    */   }
/* 123:    */   
/* 124:    */   LoggerRepository genericHierarchy()
/* 125:    */   {
/* 126:199 */     if (this.genericHierarchy == null)
/* 127:    */     {
/* 128:200 */       File f = new File(this.dir, GENERIC + CONFIG_FILE_EXT);
/* 129:201 */       if (f.exists())
/* 130:    */       {
/* 131:202 */         this.genericHierarchy = new Hierarchy(new RootLogger(Level.DEBUG));
/* 132:203 */         new PropertyConfigurator().doConfigure(f.getAbsolutePath(), this.genericHierarchy);
/* 133:    */       }
/* 134:    */       else
/* 135:    */       {
/* 136:205 */         cat.warn("Could not find config file [" + f + "]. Will use the default hierarchy.");
/* 137:    */         
/* 138:207 */         this.genericHierarchy = LogManager.getLoggerRepository();
/* 139:    */       }
/* 140:    */     }
/* 141:210 */     return this.genericHierarchy;
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.SocketServer
 * JD-Core Version:    0.7.0.1
 */