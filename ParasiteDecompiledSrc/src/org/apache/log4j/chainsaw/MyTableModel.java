/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.SortedSet;
/*  11:    */ import java.util.TreeSet;
/*  12:    */ import javax.swing.table.AbstractTableModel;
/*  13:    */ import org.apache.log4j.Category;
/*  14:    */ import org.apache.log4j.Logger;
/*  15:    */ import org.apache.log4j.Priority;
/*  16:    */ 
/*  17:    */ class MyTableModel
/*  18:    */   extends AbstractTableModel
/*  19:    */ {
/*  20: 42 */   private static final Logger LOG = Logger.getLogger(MyTableModel.class);
/*  21: 45 */   private static final Comparator MY_COMP = new Comparator()
/*  22:    */   {
/*  23:    */     public int compare(Object aObj1, Object aObj2)
/*  24:    */     {
/*  25: 49 */       if ((aObj1 == null) && (aObj2 == null)) {
/*  26: 50 */         return 0;
/*  27:    */       }
/*  28: 51 */       if (aObj1 == null) {
/*  29: 52 */         return -1;
/*  30:    */       }
/*  31: 53 */       if (aObj2 == null) {
/*  32: 54 */         return 1;
/*  33:    */       }
/*  34: 58 */       EventDetails le1 = (EventDetails)aObj1;
/*  35: 59 */       EventDetails le2 = (EventDetails)aObj2;
/*  36: 61 */       if (le1.getTimeStamp() < le2.getTimeStamp()) {
/*  37: 62 */         return 1;
/*  38:    */       }
/*  39: 65 */       return -1;
/*  40:    */     }
/*  41:    */   };
/*  42:    */   
/*  43:    */   private class Processor
/*  44:    */     implements Runnable
/*  45:    */   {
/*  46:    */     Processor(MyTableModel.1 x1)
/*  47:    */     {
/*  48: 73 */       this();
/*  49:    */     }
/*  50:    */     
/*  51:    */     public void run()
/*  52:    */     {
/*  53:    */       for (;;)
/*  54:    */       {
/*  55:    */         try
/*  56:    */         {
/*  57: 80 */           Thread.sleep(1000L);
/*  58:    */         }
/*  59:    */         catch (InterruptedException e) {}
/*  60: 85 */         synchronized (MyTableModel.this.mLock)
/*  61:    */         {
/*  62: 86 */           if (!MyTableModel.this.mPaused)
/*  63:    */           {
/*  64: 90 */             boolean toHead = true;
/*  65: 91 */             boolean needUpdate = false;
/*  66: 92 */             Iterator it = MyTableModel.this.mPendingEvents.iterator();
/*  67: 93 */             while (it.hasNext())
/*  68:    */             {
/*  69: 94 */               EventDetails event = (EventDetails)it.next();
/*  70: 95 */               MyTableModel.this.mAllEvents.add(event);
/*  71: 96 */               toHead = (toHead) && (event == MyTableModel.this.mAllEvents.first());
/*  72: 97 */               needUpdate = (needUpdate) || (MyTableModel.this.matchFilter(event));
/*  73:    */             }
/*  74: 99 */             MyTableModel.this.mPendingEvents.clear();
/*  75:101 */             if (needUpdate) {
/*  76:102 */               MyTableModel.this.updateFilteredEvents(toHead);
/*  77:    */             }
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:    */     
/*  83:    */     private Processor() {}
/*  84:    */   }
/*  85:    */   
/*  86:112 */   private static final String[] COL_NAMES = { "Time", "Priority", "Trace", "Category", "NDC", "Message" };
/*  87:116 */   private static final EventDetails[] EMPTY_LIST = new EventDetails[0];
/*  88:119 */   private static final DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance(3, 2);
/*  89:123 */   private final Object mLock = new Object();
/*  90:125 */   private final SortedSet mAllEvents = new TreeSet(MY_COMP);
/*  91:127 */   private EventDetails[] mFilteredEvents = EMPTY_LIST;
/*  92:129 */   private final List mPendingEvents = new ArrayList();
/*  93:131 */   private boolean mPaused = false;
/*  94:134 */   private String mThreadFilter = "";
/*  95:136 */   private String mMessageFilter = "";
/*  96:138 */   private String mNDCFilter = "";
/*  97:140 */   private String mCategoryFilter = "";
/*  98:142 */   private Priority mPriorityFilter = Priority.DEBUG;
/*  99:    */   
/* 100:    */   MyTableModel()
/* 101:    */   {
/* 102:150 */     Thread t = new Thread(new Processor(null));
/* 103:151 */     t.setDaemon(true);
/* 104:152 */     t.start();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getRowCount()
/* 108:    */   {
/* 109:162 */     synchronized (this.mLock)
/* 110:    */     {
/* 111:163 */       return this.mFilteredEvents.length;
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getColumnCount()
/* 116:    */   {
/* 117:170 */     return COL_NAMES.length;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getColumnName(int aCol)
/* 121:    */   {
/* 122:176 */     return COL_NAMES[aCol];
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Class getColumnClass(int aCol)
/* 126:    */   {
/* 127:182 */     return Object.class;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Object getValueAt(int aRow, int aCol)
/* 131:    */   {
/* 132:187 */     synchronized (this.mLock)
/* 133:    */     {
/* 134:188 */       EventDetails event = this.mFilteredEvents[aRow];
/* 135:190 */       if (aCol == 0) {
/* 136:191 */         return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
/* 137:    */       }
/* 138:192 */       if (aCol == 1) {
/* 139:193 */         return event.getPriority();
/* 140:    */       }
/* 141:194 */       if (aCol == 2) {
/* 142:195 */         return event.getThrowableStrRep() == null ? Boolean.FALSE : Boolean.TRUE;
/* 143:    */       }
/* 144:197 */       if (aCol == 3) {
/* 145:198 */         return event.getCategoryName();
/* 146:    */       }
/* 147:199 */       if (aCol == 4) {
/* 148:200 */         return event.getNDC();
/* 149:    */       }
/* 150:202 */       return event.getMessage();
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setPriorityFilter(Priority aPriority)
/* 155:    */   {
/* 156:217 */     synchronized (this.mLock)
/* 157:    */     {
/* 158:218 */       this.mPriorityFilter = aPriority;
/* 159:219 */       updateFilteredEvents(false);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setThreadFilter(String aStr)
/* 164:    */   {
/* 165:229 */     synchronized (this.mLock)
/* 166:    */     {
/* 167:230 */       this.mThreadFilter = aStr.trim();
/* 168:231 */       updateFilteredEvents(false);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setMessageFilter(String aStr)
/* 173:    */   {
/* 174:241 */     synchronized (this.mLock)
/* 175:    */     {
/* 176:242 */       this.mMessageFilter = aStr.trim();
/* 177:243 */       updateFilteredEvents(false);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setNDCFilter(String aStr)
/* 182:    */   {
/* 183:253 */     synchronized (this.mLock)
/* 184:    */     {
/* 185:254 */       this.mNDCFilter = aStr.trim();
/* 186:255 */       updateFilteredEvents(false);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setCategoryFilter(String aStr)
/* 191:    */   {
/* 192:265 */     synchronized (this.mLock)
/* 193:    */     {
/* 194:266 */       this.mCategoryFilter = aStr.trim();
/* 195:267 */       updateFilteredEvents(false);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void addEvent(EventDetails aEvent)
/* 200:    */   {
/* 201:277 */     synchronized (this.mLock)
/* 202:    */     {
/* 203:278 */       this.mPendingEvents.add(aEvent);
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void clear()
/* 208:    */   {
/* 209:286 */     synchronized (this.mLock)
/* 210:    */     {
/* 211:287 */       this.mAllEvents.clear();
/* 212:288 */       this.mFilteredEvents = new EventDetails[0];
/* 213:289 */       this.mPendingEvents.clear();
/* 214:290 */       fireTableDataChanged();
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void toggle()
/* 219:    */   {
/* 220:296 */     synchronized (this.mLock)
/* 221:    */     {
/* 222:297 */       this.mPaused = (!this.mPaused);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isPaused()
/* 227:    */   {
/* 228:303 */     synchronized (this.mLock)
/* 229:    */     {
/* 230:304 */       return this.mPaused;
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public EventDetails getEventDetails(int aRow)
/* 235:    */   {
/* 236:315 */     synchronized (this.mLock)
/* 237:    */     {
/* 238:316 */       return this.mFilteredEvents[aRow];
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   private void updateFilteredEvents(boolean aInsertedToFront)
/* 243:    */   {
/* 244:331 */     long start = System.currentTimeMillis();
/* 245:332 */     List filtered = new ArrayList();
/* 246:333 */     int size = this.mAllEvents.size();
/* 247:334 */     Iterator it = this.mAllEvents.iterator();
/* 248:336 */     while (it.hasNext())
/* 249:    */     {
/* 250:337 */       EventDetails event = (EventDetails)it.next();
/* 251:338 */       if (matchFilter(event)) {
/* 252:339 */         filtered.add(event);
/* 253:    */       }
/* 254:    */     }
/* 255:343 */     EventDetails lastFirst = this.mFilteredEvents.length == 0 ? null : this.mFilteredEvents[0];
/* 256:    */     
/* 257:    */ 
/* 258:346 */     this.mFilteredEvents = ((EventDetails[])filtered.toArray(EMPTY_LIST));
/* 259:348 */     if ((aInsertedToFront) && (lastFirst != null))
/* 260:    */     {
/* 261:349 */       int index = filtered.indexOf(lastFirst);
/* 262:350 */       if (index < 1)
/* 263:    */       {
/* 264:351 */         LOG.warn("In strange state");
/* 265:352 */         fireTableDataChanged();
/* 266:    */       }
/* 267:    */       else
/* 268:    */       {
/* 269:354 */         fireTableRowsInserted(0, index - 1);
/* 270:    */       }
/* 271:    */     }
/* 272:    */     else
/* 273:    */     {
/* 274:357 */       fireTableDataChanged();
/* 275:    */     }
/* 276:360 */     long end = System.currentTimeMillis();
/* 277:361 */     LOG.debug("Total time [ms]: " + (end - start) + " in update, size: " + size);
/* 278:    */   }
/* 279:    */   
/* 280:    */   private boolean matchFilter(EventDetails aEvent)
/* 281:    */   {
/* 282:372 */     if ((aEvent.getPriority().isGreaterOrEqual(this.mPriorityFilter)) && (aEvent.getThreadName().indexOf(this.mThreadFilter) >= 0) && (aEvent.getCategoryName().indexOf(this.mCategoryFilter) >= 0) && ((this.mNDCFilter.length() == 0) || ((aEvent.getNDC() != null) && (aEvent.getNDC().indexOf(this.mNDCFilter) >= 0))))
/* 283:    */     {
/* 284:379 */       String rm = aEvent.getMessage();
/* 285:380 */       if (rm == null) {
/* 286:382 */         return this.mMessageFilter.length() == 0;
/* 287:    */       }
/* 288:384 */       return rm.indexOf(this.mMessageFilter) >= 0;
/* 289:    */     }
/* 290:388 */     return false;
/* 291:    */   }
/* 292:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.MyTableModel
 * JD-Core Version:    0.7.0.1
 */