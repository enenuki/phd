/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.LogLog;
/*   4:    */ import org.apache.log4j.helpers.OnlyOnceErrorHandler;
/*   5:    */ import org.apache.log4j.spi.ErrorHandler;
/*   6:    */ import org.apache.log4j.spi.Filter;
/*   7:    */ import org.apache.log4j.spi.LoggingEvent;
/*   8:    */ import org.apache.log4j.spi.OptionHandler;
/*   9:    */ 
/*  10:    */ public abstract class AppenderSkeleton
/*  11:    */   implements Appender, OptionHandler
/*  12:    */ {
/*  13:    */   protected Layout layout;
/*  14:    */   protected String name;
/*  15:    */   protected Priority threshold;
/*  16: 53 */   protected ErrorHandler errorHandler = new OnlyOnceErrorHandler();
/*  17:    */   protected Filter headFilter;
/*  18:    */   protected Filter tailFilter;
/*  19: 64 */   protected boolean closed = false;
/*  20:    */   
/*  21:    */   public AppenderSkeleton() {}
/*  22:    */   
/*  23:    */   protected AppenderSkeleton(boolean isActive) {}
/*  24:    */   
/*  25:    */   public void activateOptions() {}
/*  26:    */   
/*  27:    */   public void addFilter(Filter newFilter)
/*  28:    */   {
/*  29:102 */     if (this.headFilter == null)
/*  30:    */     {
/*  31:103 */       this.headFilter = (this.tailFilter = newFilter);
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35:105 */       this.tailFilter.setNext(newFilter);
/*  36:106 */       this.tailFilter = newFilter;
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected abstract void append(LoggingEvent paramLoggingEvent);
/*  41:    */   
/*  42:    */   public void clearFilters()
/*  43:    */   {
/*  44:128 */     this.headFilter = (this.tailFilter = null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void finalize()
/*  48:    */   {
/*  49:140 */     if (this.closed) {
/*  50:141 */       return;
/*  51:    */     }
/*  52:143 */     LogLog.debug("Finalizing appender named [" + this.name + "].");
/*  53:144 */     close();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ErrorHandler getErrorHandler()
/*  57:    */   {
/*  58:155 */     return this.errorHandler;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Filter getFilter()
/*  62:    */   {
/*  63:166 */     return this.headFilter;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final Filter getFirstFilter()
/*  67:    */   {
/*  68:178 */     return this.headFilter;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Layout getLayout()
/*  72:    */   {
/*  73:186 */     return this.layout;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public final String getName()
/*  77:    */   {
/*  78:197 */     return this.name;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Priority getThreshold()
/*  82:    */   {
/*  83:207 */     return this.threshold;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isAsSevereAsThreshold(Priority priority)
/*  87:    */   {
/*  88:219 */     return (this.threshold == null) || (priority.isGreaterOrEqual(this.threshold));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public synchronized void doAppend(LoggingEvent event)
/*  92:    */   {
/*  93:231 */     if (this.closed)
/*  94:    */     {
/*  95:232 */       LogLog.error("Attempted to append to closed appender named [" + this.name + "].");
/*  96:233 */       return;
/*  97:    */     }
/*  98:236 */     if (!isAsSevereAsThreshold(event.getLevel())) {
/*  99:237 */       return;
/* 100:    */     }
/* 101:240 */     Filter f = this.headFilter;
/* 102:243 */     while (f != null) {
/* 103:244 */       switch (f.decide(event))
/* 104:    */       {
/* 105:    */       case -1: 
/* 106:    */         return;
/* 107:    */       case 1: 
/* 108:    */         break;
/* 109:    */       case 0: 
/* 110:247 */         f = f.getNext();
/* 111:    */       }
/* 112:    */     }
/* 113:251 */     append(event);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public synchronized void setErrorHandler(ErrorHandler eh)
/* 117:    */   {
/* 118:261 */     if (eh == null) {
/* 119:264 */       LogLog.warn("You have tried to set a null error-handler.");
/* 120:    */     } else {
/* 121:266 */       this.errorHandler = eh;
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setLayout(Layout layout)
/* 126:    */   {
/* 127:278 */     this.layout = layout;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setName(String name)
/* 131:    */   {
/* 132:287 */     this.name = name;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setThreshold(Priority threshold)
/* 136:    */   {
/* 137:302 */     this.threshold = threshold;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public abstract boolean requiresLayout();
/* 141:    */   
/* 142:    */   public abstract void close();
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.AppenderSkeleton
 * JD-Core Version:    0.7.0.1
 */