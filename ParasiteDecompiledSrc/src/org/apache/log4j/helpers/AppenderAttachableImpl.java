/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.log4j.Appender;
/*   6:    */ import org.apache.log4j.spi.AppenderAttachable;
/*   7:    */ import org.apache.log4j.spi.LoggingEvent;
/*   8:    */ 
/*   9:    */ public class AppenderAttachableImpl
/*  10:    */   implements AppenderAttachable
/*  11:    */ {
/*  12:    */   protected Vector appenderList;
/*  13:    */   
/*  14:    */   public void addAppender(Appender newAppender)
/*  15:    */   {
/*  16: 45 */     if (newAppender == null) {
/*  17: 46 */       return;
/*  18:    */     }
/*  19: 48 */     if (this.appenderList == null) {
/*  20: 49 */       this.appenderList = new Vector(1);
/*  21:    */     }
/*  22: 51 */     if (!this.appenderList.contains(newAppender)) {
/*  23: 52 */       this.appenderList.addElement(newAppender);
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int appendLoopOnAppenders(LoggingEvent event)
/*  28:    */   {
/*  29: 59 */     int size = 0;
/*  30: 62 */     if (this.appenderList != null)
/*  31:    */     {
/*  32: 63 */       size = this.appenderList.size();
/*  33: 64 */       for (int i = 0; i < size; i++)
/*  34:    */       {
/*  35: 65 */         Appender appender = (Appender)this.appenderList.elementAt(i);
/*  36: 66 */         appender.doAppend(event);
/*  37:    */       }
/*  38:    */     }
/*  39: 69 */     return size;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Enumeration getAllAppenders()
/*  43:    */   {
/*  44: 81 */     if (this.appenderList == null) {
/*  45: 82 */       return null;
/*  46:    */     }
/*  47: 84 */     return this.appenderList.elements();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Appender getAppender(String name)
/*  51:    */   {
/*  52: 96 */     if ((this.appenderList == null) || (name == null)) {
/*  53: 97 */       return null;
/*  54:    */     }
/*  55: 99 */     int size = this.appenderList.size();
/*  56:101 */     for (int i = 0; i < size; i++)
/*  57:    */     {
/*  58:102 */       Appender appender = (Appender)this.appenderList.elementAt(i);
/*  59:103 */       if (name.equals(appender.getName())) {
/*  60:104 */         return appender;
/*  61:    */       }
/*  62:    */     }
/*  63:106 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isAttached(Appender appender)
/*  67:    */   {
/*  68:117 */     if ((this.appenderList == null) || (appender == null)) {
/*  69:118 */       return false;
/*  70:    */     }
/*  71:120 */     int size = this.appenderList.size();
/*  72:122 */     for (int i = 0; i < size; i++)
/*  73:    */     {
/*  74:123 */       Appender a = (Appender)this.appenderList.elementAt(i);
/*  75:124 */       if (a == appender) {
/*  76:125 */         return true;
/*  77:    */       }
/*  78:    */     }
/*  79:127 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void removeAllAppenders()
/*  83:    */   {
/*  84:137 */     if (this.appenderList != null)
/*  85:    */     {
/*  86:138 */       int len = this.appenderList.size();
/*  87:139 */       for (int i = 0; i < len; i++)
/*  88:    */       {
/*  89:140 */         Appender a = (Appender)this.appenderList.elementAt(i);
/*  90:141 */         a.close();
/*  91:    */       }
/*  92:143 */       this.appenderList.removeAllElements();
/*  93:144 */       this.appenderList = null;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void removeAppender(Appender appender)
/*  98:    */   {
/*  99:154 */     if ((appender == null) || (this.appenderList == null)) {
/* 100:155 */       return;
/* 101:    */     }
/* 102:156 */     this.appenderList.removeElement(appender);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void removeAppender(String name)
/* 106:    */   {
/* 107:166 */     if ((name == null) || (this.appenderList == null)) {
/* 108:166 */       return;
/* 109:    */     }
/* 110:167 */     int size = this.appenderList.size();
/* 111:168 */     for (int i = 0; i < size; i++) {
/* 112:169 */       if (name.equals(((Appender)this.appenderList.elementAt(i)).getName()))
/* 113:    */       {
/* 114:170 */         this.appenderList.removeElementAt(i);
/* 115:171 */         break;
/* 116:    */       }
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.AppenderAttachableImpl
 * JD-Core Version:    0.7.0.1
 */