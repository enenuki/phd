/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.ListIterator;
/*   6:    */ import java.util.Queue;
/*   7:    */ import org.apache.commons.logging.Log;
/*   8:    */ import org.apache.commons.logging.LogFactory;
/*   9:    */ import org.apache.http.annotation.NotThreadSafe;
/*  10:    */ import org.apache.http.conn.OperatedClientConnection;
/*  11:    */ import org.apache.http.conn.params.ConnPerRoute;
/*  12:    */ import org.apache.http.conn.routing.HttpRoute;
/*  13:    */ import org.apache.http.util.LangUtils;
/*  14:    */ 
/*  15:    */ @NotThreadSafe
/*  16:    */ public class RouteSpecificPool
/*  17:    */ {
/*  18: 54 */   private final Log log = LogFactory.getLog(getClass());
/*  19:    */   protected final HttpRoute route;
/*  20:    */   @Deprecated
/*  21:    */   protected final int maxEntries;
/*  22:    */   protected final ConnPerRoute connPerRoute;
/*  23:    */   protected final LinkedList<BasicPoolEntry> freeEntries;
/*  24:    */   protected final Queue<WaitingThread> waitingThreads;
/*  25:    */   protected int numEntries;
/*  26:    */   
/*  27:    */   @Deprecated
/*  28:    */   public RouteSpecificPool(HttpRoute route, int maxEntries)
/*  29:    */   {
/*  30: 84 */     this.route = route;
/*  31: 85 */     this.maxEntries = maxEntries;
/*  32: 86 */     this.connPerRoute = new ConnPerRoute()
/*  33:    */     {
/*  34:    */       public int getMaxForRoute(HttpRoute route)
/*  35:    */       {
/*  36: 88 */         return RouteSpecificPool.this.maxEntries;
/*  37:    */       }
/*  38: 90 */     };
/*  39: 91 */     this.freeEntries = new LinkedList();
/*  40: 92 */     this.waitingThreads = new LinkedList();
/*  41: 93 */     this.numEntries = 0;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public RouteSpecificPool(HttpRoute route, ConnPerRoute connPerRoute)
/*  45:    */   {
/*  46:104 */     this.route = route;
/*  47:105 */     this.connPerRoute = connPerRoute;
/*  48:106 */     this.maxEntries = connPerRoute.getMaxForRoute(route);
/*  49:107 */     this.freeEntries = new LinkedList();
/*  50:108 */     this.waitingThreads = new LinkedList();
/*  51:109 */     this.numEntries = 0;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final HttpRoute getRoute()
/*  55:    */   {
/*  56:119 */     return this.route;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final int getMaxEntries()
/*  60:    */   {
/*  61:129 */     return this.maxEntries;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isUnused()
/*  65:    */   {
/*  66:142 */     return (this.numEntries < 1) && (this.waitingThreads.isEmpty());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getCapacity()
/*  70:    */   {
/*  71:152 */     return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final int getEntryCount()
/*  75:    */   {
/*  76:164 */     return this.numEntries;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public BasicPoolEntry allocEntry(Object state)
/*  80:    */   {
/*  81:174 */     if (!this.freeEntries.isEmpty())
/*  82:    */     {
/*  83:175 */       ListIterator<BasicPoolEntry> it = this.freeEntries.listIterator(this.freeEntries.size());
/*  84:176 */       while (it.hasPrevious())
/*  85:    */       {
/*  86:177 */         BasicPoolEntry entry = (BasicPoolEntry)it.previous();
/*  87:178 */         if ((entry.getState() == null) || (LangUtils.equals(state, entry.getState())))
/*  88:    */         {
/*  89:179 */           it.remove();
/*  90:180 */           return entry;
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:184 */     if ((getCapacity() == 0) && (!this.freeEntries.isEmpty()))
/*  95:    */     {
/*  96:185 */       BasicPoolEntry entry = (BasicPoolEntry)this.freeEntries.remove();
/*  97:186 */       entry.shutdownEntry();
/*  98:187 */       OperatedClientConnection conn = entry.getConnection();
/*  99:    */       try
/* 100:    */       {
/* 101:189 */         conn.close();
/* 102:    */       }
/* 103:    */       catch (IOException ex)
/* 104:    */       {
/* 105:191 */         this.log.debug("I/O error closing connection", ex);
/* 106:    */       }
/* 107:193 */       return entry;
/* 108:    */     }
/* 109:195 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void freeEntry(BasicPoolEntry entry)
/* 113:    */   {
/* 114:207 */     if (this.numEntries < 1) {
/* 115:208 */       throw new IllegalStateException("No entry created for this pool. " + this.route);
/* 116:    */     }
/* 117:211 */     if (this.numEntries <= this.freeEntries.size()) {
/* 118:212 */       throw new IllegalStateException("No entry allocated from this pool. " + this.route);
/* 119:    */     }
/* 120:215 */     this.freeEntries.add(entry);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void createdEntry(BasicPoolEntry entry)
/* 124:    */   {
/* 125:229 */     if (!this.route.equals(entry.getPlannedRoute())) {
/* 126:230 */       throw new IllegalArgumentException("Entry not planned for this pool.\npool: " + this.route + "\nplan: " + entry.getPlannedRoute());
/* 127:    */     }
/* 128:236 */     this.numEntries += 1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean deleteEntry(BasicPoolEntry entry)
/* 132:    */   {
/* 133:252 */     boolean found = this.freeEntries.remove(entry);
/* 134:253 */     if (found) {
/* 135:254 */       this.numEntries -= 1;
/* 136:    */     }
/* 137:255 */     return found;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void dropEntry()
/* 141:    */   {
/* 142:266 */     if (this.numEntries < 1) {
/* 143:267 */       throw new IllegalStateException("There is no entry that could be dropped.");
/* 144:    */     }
/* 145:270 */     this.numEntries -= 1;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void queueThread(WaitingThread wt)
/* 149:    */   {
/* 150:283 */     if (wt == null) {
/* 151:284 */       throw new IllegalArgumentException("Waiting thread must not be null.");
/* 152:    */     }
/* 153:287 */     this.waitingThreads.add(wt);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean hasThread()
/* 157:    */   {
/* 158:298 */     return !this.waitingThreads.isEmpty();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public WaitingThread nextThread()
/* 162:    */   {
/* 163:308 */     return (WaitingThread)this.waitingThreads.peek();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void removeThread(WaitingThread wt)
/* 167:    */   {
/* 168:318 */     if (wt == null) {
/* 169:319 */       return;
/* 170:    */     }
/* 171:321 */     this.waitingThreads.remove(wt);
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.RouteSpecificPool
 * JD-Core Version:    0.7.0.1
 */