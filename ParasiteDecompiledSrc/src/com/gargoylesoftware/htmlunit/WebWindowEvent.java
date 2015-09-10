/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.util.EventObject;
/*   4:    */ 
/*   5:    */ public final class WebWindowEvent
/*   6:    */   extends EventObject
/*   7:    */ {
/*   8:    */   private final Page oldPage_;
/*   9:    */   private final Page newPage_;
/*  10:    */   private final int type_;
/*  11:    */   public static final int OPEN = 1;
/*  12:    */   public static final int CLOSE = 2;
/*  13:    */   public static final int CHANGE = 3;
/*  14:    */   
/*  15:    */   public WebWindowEvent(WebWindow webWindow, int type, Page oldPage, Page newPage)
/*  16:    */   {
/*  17: 53 */     super(webWindow);
/*  18: 54 */     this.oldPage_ = oldPage;
/*  19: 55 */     this.newPage_ = newPage;
/*  20: 57 */     switch (type)
/*  21:    */     {
/*  22:    */     case 1: 
/*  23:    */     case 2: 
/*  24:    */     case 3: 
/*  25: 61 */       this.type_ = type;
/*  26: 62 */       break;
/*  27:    */     default: 
/*  28: 65 */       throw new IllegalArgumentException("type must be one of OPEN, CLOSE, CHANGE but got " + type);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean equals(Object object)
/*  33:    */   {
/*  34: 78 */     if (null == object) {
/*  35: 79 */       return false;
/*  36:    */     }
/*  37: 81 */     if (getClass() == object.getClass())
/*  38:    */     {
/*  39: 82 */       WebWindowEvent event = (WebWindowEvent)object;
/*  40: 83 */       return (isEqual(getSource(), event.getSource())) && (getEventType() == event.getEventType()) && (isEqual(getOldPage(), event.getOldPage())) && (isEqual(getNewPage(), event.getNewPage()));
/*  41:    */     }
/*  42: 88 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int hashCode()
/*  46:    */   {
/*  47: 97 */     return this.source.hashCode();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Page getOldPage()
/*  51:    */   {
/*  52:105 */     return this.oldPage_;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Page getNewPage()
/*  56:    */   {
/*  57:113 */     return this.newPage_;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public WebWindow getWebWindow()
/*  61:    */   {
/*  62:121 */     return (WebWindow)getSource();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private boolean isEqual(Object object1, Object object2)
/*  66:    */   {
/*  67:    */     boolean result;
/*  68:    */     boolean result;
/*  69:127 */     if ((object1 == null) && (object2 == null))
/*  70:    */     {
/*  71:128 */       result = true;
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:    */       boolean result;
/*  76:130 */       if ((object1 == null) || (object2 == null)) {
/*  77:131 */         result = false;
/*  78:    */       } else {
/*  79:134 */         result = object1.equals(object2);
/*  80:    */       }
/*  81:    */     }
/*  82:137 */     return result;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String toString()
/*  86:    */   {
/*  87:146 */     StringBuilder buffer = new StringBuilder(80);
/*  88:147 */     buffer.append("WebWindowEvent(source=[");
/*  89:148 */     buffer.append(getSource());
/*  90:149 */     buffer.append("] type=[");
/*  91:150 */     switch (this.type_)
/*  92:    */     {
/*  93:    */     case 1: 
/*  94:152 */       buffer.append("OPEN");
/*  95:153 */       break;
/*  96:    */     case 2: 
/*  97:155 */       buffer.append("CLOSE");
/*  98:156 */       break;
/*  99:    */     case 3: 
/* 100:158 */       buffer.append("CHANGE");
/* 101:159 */       break;
/* 102:    */     default: 
/* 103:161 */       buffer.append(this.type_);
/* 104:    */     }
/* 105:164 */     buffer.append("] oldPage=[");
/* 106:165 */     buffer.append(getOldPage());
/* 107:166 */     buffer.append("] newPage=[");
/* 108:167 */     buffer.append(getNewPage());
/* 109:168 */     buffer.append("])");
/* 110:    */     
/* 111:170 */     return buffer.toString();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getEventType()
/* 115:    */   {
/* 116:175 */     return this.type_;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebWindowEvent
 * JD-Core Version:    0.7.0.1
 */