/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InterruptedIOException;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import java.net.ServerSocket;
/*   7:    */ import java.net.Socket;
/*   8:    */ import java.util.AbstractList;
/*   9:    */ import java.util.Enumeration;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Vector;
/*  12:    */ import org.apache.log4j.AppenderSkeleton;
/*  13:    */ import org.apache.log4j.Layout;
/*  14:    */ import org.apache.log4j.helpers.LogLog;
/*  15:    */ import org.apache.log4j.spi.LoggingEvent;
/*  16:    */ 
/*  17:    */ public class TelnetAppender
/*  18:    */   extends AppenderSkeleton
/*  19:    */ {
/*  20:    */   private SocketHandler sh;
/*  21:    */   private int port;
/*  22:    */   
/*  23:    */   public TelnetAppender()
/*  24:    */   {
/*  25: 64 */     this.port = 23;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean requiresLayout()
/*  29:    */   {
/*  30: 70 */     return true;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void activateOptions()
/*  34:    */   {
/*  35:    */     try
/*  36:    */     {
/*  37: 77 */       this.sh = new SocketHandler(this.port);
/*  38: 78 */       this.sh.start();
/*  39:    */     }
/*  40:    */     catch (InterruptedIOException e)
/*  41:    */     {
/*  42: 81 */       Thread.currentThread().interrupt();
/*  43: 82 */       e.printStackTrace();
/*  44:    */     }
/*  45:    */     catch (IOException e)
/*  46:    */     {
/*  47: 84 */       e.printStackTrace();
/*  48:    */     }
/*  49:    */     catch (RuntimeException e)
/*  50:    */     {
/*  51: 86 */       e.printStackTrace();
/*  52:    */     }
/*  53: 88 */     super.activateOptions();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getPort()
/*  57:    */   {
/*  58: 93 */     return this.port;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setPort(int port)
/*  62:    */   {
/*  63: 98 */     this.port = port;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void close()
/*  67:    */   {
/*  68:104 */     if (this.sh != null)
/*  69:    */     {
/*  70:105 */       this.sh.close();
/*  71:    */       try
/*  72:    */       {
/*  73:107 */         this.sh.join();
/*  74:    */       }
/*  75:    */       catch (InterruptedException ex)
/*  76:    */       {
/*  77:109 */         Thread.currentThread().interrupt();
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void append(LoggingEvent event)
/*  83:    */   {
/*  84:117 */     if (this.sh != null)
/*  85:    */     {
/*  86:118 */       this.sh.send(this.layout.format(event));
/*  87:119 */       if (this.layout.ignoresThrowable())
/*  88:    */       {
/*  89:120 */         String[] s = event.getThrowableStrRep();
/*  90:121 */         if (s != null)
/*  91:    */         {
/*  92:122 */           StringBuffer buf = new StringBuffer();
/*  93:123 */           for (int i = 0; i < s.length; i++)
/*  94:    */           {
/*  95:124 */             buf.append(s[i]);
/*  96:125 */             buf.append("\r\n");
/*  97:    */           }
/*  98:127 */           this.sh.send(buf.toString());
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected class SocketHandler
/* 105:    */     extends Thread
/* 106:    */   {
/* 107:140 */     private Vector writers = new Vector();
/* 108:141 */     private Vector connections = new Vector();
/* 109:    */     private ServerSocket serverSocket;
/* 110:143 */     private int MAX_CONNECTIONS = 20;
/* 111:    */     
/* 112:    */     public void finalize()
/* 113:    */     {
/* 114:146 */       close();
/* 115:    */     }
/* 116:    */     
/* 117:    */     public void close()
/* 118:    */     {
/* 119:    */       Enumeration e;
/* 120:154 */       synchronized (this)
/* 121:    */       {
/* 122:155 */         for (e = this.connections.elements(); e.hasMoreElements();) {
/* 123:    */           try
/* 124:    */           {
/* 125:157 */             ((Socket)e.nextElement()).close();
/* 126:    */           }
/* 127:    */           catch (InterruptedIOException ex)
/* 128:    */           {
/* 129:159 */             Thread.currentThread().interrupt();
/* 130:    */           }
/* 131:    */           catch (IOException ex) {}catch (RuntimeException ex) {}
/* 132:    */         }
/* 133:    */       }
/* 134:    */       try
/* 135:    */       {
/* 136:167 */         this.serverSocket.close();
/* 137:    */       }
/* 138:    */       catch (InterruptedIOException ex)
/* 139:    */       {
/* 140:169 */         Thread.currentThread().interrupt();
/* 141:    */       }
/* 142:    */       catch (IOException ex) {}catch (RuntimeException ex) {}
/* 143:    */     }
/* 144:    */     
/* 145:    */     public synchronized void send(String message)
/* 146:    */     {
/* 147:177 */       Iterator ce = this.connections.iterator();
/* 148:178 */       for (Iterator e = this.writers.iterator(); e.hasNext();)
/* 149:    */       {
/* 150:179 */         ce.next();
/* 151:180 */         PrintWriter writer = (PrintWriter)e.next();
/* 152:181 */         writer.print(message);
/* 153:182 */         if (writer.checkError())
/* 154:    */         {
/* 155:183 */           ce.remove();
/* 156:184 */           e.remove();
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:    */     
/* 161:    */     public void run()
/* 162:    */     {
/* 163:    */       for (;;)
/* 164:    */       {
/* 165:194 */         if (!this.serverSocket.isClosed()) {
/* 166:    */           try
/* 167:    */           {
/* 168:196 */             Socket newClient = this.serverSocket.accept();
/* 169:197 */             PrintWriter pw = new PrintWriter(newClient.getOutputStream());
/* 170:198 */             if (this.connections.size() < this.MAX_CONNECTIONS)
/* 171:    */             {
/* 172:199 */               synchronized (this)
/* 173:    */               {
/* 174:200 */                 this.connections.addElement(newClient);
/* 175:201 */                 this.writers.addElement(pw);
/* 176:202 */                 pw.print("TelnetAppender v1.0 (" + this.connections.size() + " active connections)\r\n\r\n");
/* 177:    */                 
/* 178:204 */                 pw.flush();
/* 179:    */               }
/* 180:    */             }
/* 181:    */             else
/* 182:    */             {
/* 183:207 */               pw.print("Too many connections.\r\n");
/* 184:208 */               pw.flush();
/* 185:209 */               newClient.close();
/* 186:    */             }
/* 187:    */           }
/* 188:    */           catch (Exception e)
/* 189:    */           {
/* 190:212 */             if (((e instanceof InterruptedIOException)) || ((e instanceof InterruptedException))) {
/* 191:213 */               Thread.currentThread().interrupt();
/* 192:    */             }
/* 193:215 */             if (!this.serverSocket.isClosed()) {
/* 194:216 */               LogLog.error("Encountered error while in SocketHandler loop.", e);
/* 195:    */             }
/* 196:    */           }
/* 197:    */         }
/* 198:    */       }
/* 199:    */       try
/* 200:    */       {
/* 201:223 */         this.serverSocket.close();
/* 202:    */       }
/* 203:    */       catch (InterruptedIOException ex)
/* 204:    */       {
/* 205:225 */         Thread.currentThread().interrupt();
/* 206:    */       }
/* 207:    */       catch (IOException ex) {}
/* 208:    */     }
/* 209:    */     
/* 210:    */     public SocketHandler(int port)
/* 211:    */       throws IOException
/* 212:    */     {
/* 213:231 */       this.serverSocket = new ServerSocket(port);
/* 214:232 */       setName("TelnetAppender-" + getName() + "-" + port);
/* 215:    */     }
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.TelnetAppender
 * JD-Core Version:    0.7.0.1
 */