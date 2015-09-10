/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.ref.Reference;
/*   5:    */ import java.lang.ref.ReferenceQueue;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.concurrent.TimeUnit;
/*  10:    */ import java.util.concurrent.locks.Lock;
/*  11:    */ import java.util.concurrent.locks.ReentrantLock;
/*  12:    */ import org.apache.commons.logging.Log;
/*  13:    */ import org.apache.commons.logging.LogFactory;
/*  14:    */ import org.apache.http.annotation.GuardedBy;
/*  15:    */ import org.apache.http.conn.ConnectionPoolTimeoutException;
/*  16:    */ import org.apache.http.conn.OperatedClientConnection;
/*  17:    */ import org.apache.http.conn.routing.HttpRoute;
/*  18:    */ import org.apache.http.impl.conn.IdleConnectionHandler;
/*  19:    */ 
/*  20:    */ @Deprecated
/*  21:    */ public abstract class AbstractConnPool
/*  22:    */   implements RefQueueHandler
/*  23:    */ {
/*  24:    */   private final Log log;
/*  25:    */   protected final Lock poolLock;
/*  26:    */   @GuardedBy("poolLock")
/*  27:    */   protected Set<BasicPoolEntry> leasedConnections;
/*  28:    */   @GuardedBy("poolLock")
/*  29:    */   protected int numConnections;
/*  30:    */   protected volatile boolean isShutDown;
/*  31:    */   protected Set<BasicPoolEntryRef> issuedConnections;
/*  32:    */   protected ReferenceQueue<Object> refQueue;
/*  33:    */   protected IdleConnectionHandler idleConnHandler;
/*  34:    */   
/*  35:    */   protected AbstractConnPool()
/*  36:    */   {
/*  37: 90 */     this.log = LogFactory.getLog(getClass());
/*  38: 91 */     this.leasedConnections = new HashSet();
/*  39: 92 */     this.idleConnHandler = new IdleConnectionHandler();
/*  40: 93 */     this.poolLock = new ReentrantLock();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void enableConnectionGC()
/*  44:    */     throws IllegalStateException
/*  45:    */   {}
/*  46:    */   
/*  47:    */   public final BasicPoolEntry getEntry(HttpRoute route, Object state, long timeout, TimeUnit tunit)
/*  48:    */     throws ConnectionPoolTimeoutException, InterruptedException
/*  49:    */   {
/*  50:122 */     return requestPoolEntry(route, state).getPoolEntry(timeout, tunit);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public abstract PoolEntryRequest requestPoolEntry(HttpRoute paramHttpRoute, Object paramObject);
/*  54:    */   
/*  55:    */   public abstract void freeEntry(BasicPoolEntry paramBasicPoolEntry, boolean paramBoolean, long paramLong, TimeUnit paramTimeUnit);
/*  56:    */   
/*  57:    */   public void handleReference(Reference<?> ref) {}
/*  58:    */   
/*  59:    */   protected abstract void handleLostEntry(HttpRoute paramHttpRoute);
/*  60:    */   
/*  61:    */   public void closeIdleConnections(long idletime, TimeUnit tunit)
/*  62:    */   {
/*  63:162 */     if (tunit == null) {
/*  64:163 */       throw new IllegalArgumentException("Time unit must not be null.");
/*  65:    */     }
/*  66:166 */     this.poolLock.lock();
/*  67:    */     try
/*  68:    */     {
/*  69:168 */       this.idleConnHandler.closeIdleConnections(tunit.toMillis(idletime));
/*  70:    */     }
/*  71:    */     finally
/*  72:    */     {
/*  73:170 */       this.poolLock.unlock();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void closeExpiredConnections()
/*  78:    */   {
/*  79:175 */     this.poolLock.lock();
/*  80:    */     try
/*  81:    */     {
/*  82:177 */       this.idleConnHandler.closeExpiredConnections();
/*  83:    */     }
/*  84:    */     finally
/*  85:    */     {
/*  86:179 */       this.poolLock.unlock();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public abstract void deleteClosedConnections();
/*  91:    */   
/*  92:    */   public void shutdown()
/*  93:    */   {
/*  94:195 */     this.poolLock.lock();
/*  95:    */     try
/*  96:    */     {
/*  97:198 */       if (this.isShutDown) {
/*  98:    */         return;
/*  99:    */       }
/* 100:202 */       Iterator<BasicPoolEntry> iter = this.leasedConnections.iterator();
/* 101:203 */       while (iter.hasNext())
/* 102:    */       {
/* 103:204 */         BasicPoolEntry entry = (BasicPoolEntry)iter.next();
/* 104:205 */         iter.remove();
/* 105:206 */         closeConnection(entry.getConnection());
/* 106:    */       }
/* 107:208 */       this.idleConnHandler.removeAll();
/* 108:    */       
/* 109:210 */       this.isShutDown = true;
/* 110:    */     }
/* 111:    */     finally
/* 112:    */     {
/* 113:213 */       this.poolLock.unlock();
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void closeConnection(OperatedClientConnection conn)
/* 118:    */   {
/* 119:224 */     if (conn != null) {
/* 120:    */       try
/* 121:    */       {
/* 122:226 */         conn.close();
/* 123:    */       }
/* 124:    */       catch (IOException ex)
/* 125:    */       {
/* 126:228 */         this.log.debug("I/O error closing connection", ex);
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.AbstractConnPool
 * JD-Core Version:    0.7.0.1
 */