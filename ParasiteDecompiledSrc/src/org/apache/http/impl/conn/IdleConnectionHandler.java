/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.concurrent.TimeUnit;
/*   8:    */ import org.apache.commons.logging.Log;
/*   9:    */ import org.apache.commons.logging.LogFactory;
/*  10:    */ import org.apache.http.HttpConnection;
/*  11:    */ 
/*  12:    */ @Deprecated
/*  13:    */ public class IdleConnectionHandler
/*  14:    */ {
/*  15: 53 */   private final Log log = LogFactory.getLog(getClass());
/*  16:    */   private final Map<HttpConnection, TimeValues> connectionToTimes;
/*  17:    */   
/*  18:    */   public IdleConnectionHandler()
/*  19:    */   {
/*  20: 61 */     this.connectionToTimes = new HashMap();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void add(HttpConnection connection, long validDuration, TimeUnit unit)
/*  24:    */   {
/*  25: 74 */     long timeAdded = System.currentTimeMillis();
/*  26: 76 */     if (this.log.isDebugEnabled()) {
/*  27: 77 */       this.log.debug("Adding connection at: " + timeAdded);
/*  28:    */     }
/*  29: 80 */     this.connectionToTimes.put(connection, new TimeValues(timeAdded, validDuration, unit));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean remove(HttpConnection connection)
/*  33:    */   {
/*  34: 92 */     TimeValues times = (TimeValues)this.connectionToTimes.remove(connection);
/*  35: 93 */     if (times == null)
/*  36:    */     {
/*  37: 94 */       this.log.warn("Removing a connection that never existed!");
/*  38: 95 */       return true;
/*  39:    */     }
/*  40: 97 */     return System.currentTimeMillis() <= times.timeExpires;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void removeAll()
/*  44:    */   {
/*  45:105 */     this.connectionToTimes.clear();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void closeIdleConnections(long idleTime)
/*  49:    */   {
/*  50:116 */     long idleTimeout = System.currentTimeMillis() - idleTime;
/*  51:118 */     if (this.log.isDebugEnabled()) {
/*  52:119 */       this.log.debug("Checking for connections, idle timeout: " + idleTimeout);
/*  53:    */     }
/*  54:122 */     for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet())
/*  55:    */     {
/*  56:123 */       HttpConnection conn = (HttpConnection)entry.getKey();
/*  57:124 */       TimeValues times = (TimeValues)entry.getValue();
/*  58:125 */       long connectionTime = times.timeAdded;
/*  59:126 */       if (connectionTime <= idleTimeout)
/*  60:    */       {
/*  61:127 */         if (this.log.isDebugEnabled()) {
/*  62:128 */           this.log.debug("Closing idle connection, connection time: " + connectionTime);
/*  63:    */         }
/*  64:    */         try
/*  65:    */         {
/*  66:131 */           conn.close();
/*  67:    */         }
/*  68:    */         catch (IOException ex)
/*  69:    */         {
/*  70:133 */           this.log.debug("I/O error closing connection", ex);
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void closeExpiredConnections()
/*  77:    */   {
/*  78:141 */     long now = System.currentTimeMillis();
/*  79:142 */     if (this.log.isDebugEnabled()) {
/*  80:143 */       this.log.debug("Checking for expired connections, now: " + now);
/*  81:    */     }
/*  82:146 */     for (Map.Entry<HttpConnection, TimeValues> entry : this.connectionToTimes.entrySet())
/*  83:    */     {
/*  84:147 */       HttpConnection conn = (HttpConnection)entry.getKey();
/*  85:148 */       TimeValues times = (TimeValues)entry.getValue();
/*  86:149 */       if (times.timeExpires <= now)
/*  87:    */       {
/*  88:150 */         if (this.log.isDebugEnabled()) {
/*  89:151 */           this.log.debug("Closing connection, expired @: " + times.timeExpires);
/*  90:    */         }
/*  91:    */         try
/*  92:    */         {
/*  93:154 */           conn.close();
/*  94:    */         }
/*  95:    */         catch (IOException ex)
/*  96:    */         {
/*  97:156 */           this.log.debug("I/O error closing connection", ex);
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static class TimeValues
/* 104:    */   {
/* 105:    */     private final long timeAdded;
/* 106:    */     private final long timeExpires;
/* 107:    */     
/* 108:    */     TimeValues(long now, long validDuration, TimeUnit validUnit)
/* 109:    */     {
/* 110:172 */       this.timeAdded = now;
/* 111:173 */       if (validDuration > 0L) {
/* 112:174 */         this.timeExpires = (now + validUnit.toMillis(validDuration));
/* 113:    */       } else {
/* 114:176 */         this.timeExpires = 9223372036854775807L;
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.IdleConnectionHandler
 * JD-Core Version:    0.7.0.1
 */