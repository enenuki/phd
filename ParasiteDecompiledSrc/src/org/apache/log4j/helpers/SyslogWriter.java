/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Writer;
/*   5:    */ import java.net.DatagramPacket;
/*   6:    */ import java.net.DatagramSocket;
/*   7:    */ import java.net.InetAddress;
/*   8:    */ import java.net.MalformedURLException;
/*   9:    */ import java.net.SocketException;
/*  10:    */ import java.net.URL;
/*  11:    */ import java.net.UnknownHostException;
/*  12:    */ 
/*  13:    */ public class SyslogWriter
/*  14:    */   extends Writer
/*  15:    */ {
/*  16: 39 */   final int SYSLOG_PORT = 514;
/*  17:    */   /**
/*  18:    */    * @deprecated
/*  19:    */    */
/*  20:    */   static String syslogHost;
/*  21:    */   private InetAddress address;
/*  22:    */   private final int port;
/*  23:    */   private DatagramSocket ds;
/*  24:    */   
/*  25:    */   public SyslogWriter(String syslogHost)
/*  26:    */   {
/*  27: 60 */     syslogHost = syslogHost;
/*  28: 61 */     if (syslogHost == null) {
/*  29: 62 */       throw new NullPointerException("syslogHost");
/*  30:    */     }
/*  31: 65 */     String host = syslogHost;
/*  32: 66 */     int urlPort = -1;
/*  33: 72 */     if ((host.indexOf("[") != -1) || (host.indexOf(':') == host.lastIndexOf(':'))) {
/*  34:    */       try
/*  35:    */       {
/*  36: 74 */         URL url = new URL("http://" + host);
/*  37: 75 */         if (url.getHost() != null)
/*  38:    */         {
/*  39: 76 */           host = url.getHost();
/*  40: 78 */           if ((host.startsWith("[")) && (host.charAt(host.length() - 1) == ']')) {
/*  41: 79 */             host = host.substring(1, host.length() - 1);
/*  42:    */           }
/*  43: 81 */           urlPort = url.getPort();
/*  44:    */         }
/*  45:    */       }
/*  46:    */       catch (MalformedURLException e)
/*  47:    */       {
/*  48: 84 */         LogLog.error("Malformed URL: will attempt to interpret as InetAddress.", e);
/*  49:    */       }
/*  50:    */     }
/*  51: 88 */     if (urlPort == -1) {
/*  52: 89 */       urlPort = 514;
/*  53:    */     }
/*  54: 91 */     this.port = urlPort;
/*  55:    */     try
/*  56:    */     {
/*  57: 94 */       this.address = InetAddress.getByName(host);
/*  58:    */     }
/*  59:    */     catch (UnknownHostException e)
/*  60:    */     {
/*  61: 97 */       LogLog.error("Could not find " + host + ". All logging will FAIL.", e);
/*  62:    */     }
/*  63:    */     try
/*  64:    */     {
/*  65:102 */       this.ds = new DatagramSocket();
/*  66:    */     }
/*  67:    */     catch (SocketException e)
/*  68:    */     {
/*  69:105 */       e.printStackTrace();
/*  70:106 */       LogLog.error("Could not instantiate DatagramSocket to " + host + ". All logging will FAIL.", e);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void write(char[] buf, int off, int len)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:115 */     write(new String(buf, off, len));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void write(String string)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:121 */     if ((this.ds != null) && (this.address != null))
/*  84:    */     {
/*  85:122 */       byte[] bytes = string.getBytes();
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:126 */       int bytesLength = bytes.length;
/*  90:127 */       if (bytesLength >= 1024) {
/*  91:128 */         bytesLength = 1024;
/*  92:    */       }
/*  93:130 */       DatagramPacket packet = new DatagramPacket(bytes, bytesLength, this.address, this.port);
/*  94:    */       
/*  95:132 */       this.ds.send(packet);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void flush() {}
/* 100:    */   
/* 101:    */   public void close()
/* 102:    */   {
/* 103:141 */     if (this.ds != null) {
/* 104:142 */       this.ds.close();
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.SyslogWriter
 * JD-Core Version:    0.7.0.1
 */