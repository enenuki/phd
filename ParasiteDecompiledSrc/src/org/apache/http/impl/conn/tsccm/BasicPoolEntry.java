/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.lang.ref.ReferenceQueue;
/*   4:    */ import java.util.concurrent.TimeUnit;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ import org.apache.http.conn.ClientConnectionOperator;
/*   7:    */ import org.apache.http.conn.OperatedClientConnection;
/*   8:    */ import org.apache.http.conn.routing.HttpRoute;
/*   9:    */ import org.apache.http.impl.conn.AbstractPoolEntry;
/*  10:    */ 
/*  11:    */ @NotThreadSafe
/*  12:    */ public class BasicPoolEntry
/*  13:    */   extends AbstractPoolEntry
/*  14:    */ {
/*  15:    */   private final long created;
/*  16:    */   private long updated;
/*  17:    */   private long validUntil;
/*  18:    */   private long expiry;
/*  19:    */   
/*  20:    */   @Deprecated
/*  21:    */   public BasicPoolEntry(ClientConnectionOperator op, HttpRoute route, ReferenceQueue<Object> queue)
/*  22:    */   {
/*  23: 59 */     super(op, route);
/*  24: 60 */     if (route == null) {
/*  25: 61 */       throw new IllegalArgumentException("HTTP route may not be null");
/*  26:    */     }
/*  27: 63 */     this.created = System.currentTimeMillis();
/*  28: 64 */     this.validUntil = 9223372036854775807L;
/*  29: 65 */     this.expiry = this.validUntil;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BasicPoolEntry(ClientConnectionOperator op, HttpRoute route)
/*  33:    */   {
/*  34: 76 */     this(op, route, -1L, TimeUnit.MILLISECONDS);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public BasicPoolEntry(ClientConnectionOperator op, HttpRoute route, long connTTL, TimeUnit timeunit)
/*  38:    */   {
/*  39: 91 */     super(op, route);
/*  40: 92 */     if (route == null) {
/*  41: 93 */       throw new IllegalArgumentException("HTTP route may not be null");
/*  42:    */     }
/*  43: 95 */     this.created = System.currentTimeMillis();
/*  44: 96 */     if (connTTL > 0L) {
/*  45: 97 */       this.validUntil = (this.created + timeunit.toMillis(connTTL));
/*  46:    */     } else {
/*  47: 99 */       this.validUntil = 9223372036854775807L;
/*  48:    */     }
/*  49:101 */     this.expiry = this.validUntil;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected final OperatedClientConnection getConnection()
/*  53:    */   {
/*  54:105 */     return this.connection;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected final HttpRoute getPlannedRoute()
/*  58:    */   {
/*  59:109 */     return this.route;
/*  60:    */   }
/*  61:    */   
/*  62:    */   @Deprecated
/*  63:    */   protected final BasicPoolEntryRef getWeakRef()
/*  64:    */   {
/*  65:114 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void shutdownEntry()
/*  69:    */   {
/*  70:119 */     super.shutdownEntry();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public long getCreated()
/*  74:    */   {
/*  75:126 */     return this.created;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public long getUpdated()
/*  79:    */   {
/*  80:133 */     return this.updated;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public long getExpiry()
/*  84:    */   {
/*  85:140 */     return this.expiry;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public long getValidUntil()
/*  89:    */   {
/*  90:144 */     return this.validUntil;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void updateExpiry(long time, TimeUnit timeunit)
/*  94:    */   {
/*  95:151 */     this.updated = System.currentTimeMillis();
/*  96:    */     long newExpiry;
/*  97:    */     long newExpiry;
/*  98:153 */     if (time > 0L) {
/*  99:154 */       newExpiry = this.updated + timeunit.toMillis(time);
/* 100:    */     } else {
/* 101:156 */       newExpiry = 9223372036854775807L;
/* 102:    */     }
/* 103:158 */     this.expiry = Math.min(this.validUntil, newExpiry);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isExpired(long now)
/* 107:    */   {
/* 108:165 */     return now >= this.expiry;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.BasicPoolEntry
 * JD-Core Version:    0.7.0.1
 */