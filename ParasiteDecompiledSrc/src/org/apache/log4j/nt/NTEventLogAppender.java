/*   1:    */ package org.apache.log4j.nt;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.AppenderSkeleton;
/*   4:    */ import org.apache.log4j.Layout;
/*   5:    */ import org.apache.log4j.Priority;
/*   6:    */ import org.apache.log4j.TTCCLayout;
/*   7:    */ import org.apache.log4j.helpers.LogLog;
/*   8:    */ import org.apache.log4j.spi.LoggingEvent;
/*   9:    */ 
/*  10:    */ public class NTEventLogAppender
/*  11:    */   extends AppenderSkeleton
/*  12:    */ {
/*  13: 42 */   private int _handle = 0;
/*  14: 44 */   private String source = null;
/*  15: 45 */   private String server = null;
/*  16:    */   
/*  17:    */   public NTEventLogAppender()
/*  18:    */   {
/*  19: 49 */     this(null, null, null);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public NTEventLogAppender(String source)
/*  23:    */   {
/*  24: 53 */     this(null, source, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public NTEventLogAppender(String server, String source)
/*  28:    */   {
/*  29: 57 */     this(server, source, null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public NTEventLogAppender(Layout layout)
/*  33:    */   {
/*  34: 61 */     this(null, null, layout);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public NTEventLogAppender(String source, Layout layout)
/*  38:    */   {
/*  39: 65 */     this(null, source, layout);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public NTEventLogAppender(String server, String source, Layout layout)
/*  43:    */   {
/*  44: 69 */     if (source == null) {
/*  45: 70 */       source = "Log4j";
/*  46:    */     }
/*  47: 72 */     if (layout == null) {
/*  48: 73 */       this.layout = new TTCCLayout();
/*  49:    */     } else {
/*  50: 75 */       this.layout = layout;
/*  51:    */     }
/*  52:    */     try
/*  53:    */     {
/*  54: 79 */       this._handle = registerEventSource(server, source);
/*  55:    */     }
/*  56:    */     catch (Exception e)
/*  57:    */     {
/*  58: 81 */       e.printStackTrace();
/*  59: 82 */       this._handle = 0;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void close() {}
/*  64:    */   
/*  65:    */   public void activateOptions()
/*  66:    */   {
/*  67: 93 */     if (this.source != null) {
/*  68:    */       try
/*  69:    */       {
/*  70: 95 */         this._handle = registerEventSource(this.server, this.source);
/*  71:    */       }
/*  72:    */       catch (Exception e)
/*  73:    */       {
/*  74: 97 */         LogLog.error("Could not register event source.", e);
/*  75: 98 */         this._handle = 0;
/*  76:    */       }
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void append(LoggingEvent event)
/*  81:    */   {
/*  82:106 */     StringBuffer sbuf = new StringBuffer();
/*  83:    */     
/*  84:108 */     sbuf.append(this.layout.format(event));
/*  85:109 */     if (this.layout.ignoresThrowable())
/*  86:    */     {
/*  87:110 */       String[] s = event.getThrowableStrRep();
/*  88:111 */       if (s != null)
/*  89:    */       {
/*  90:112 */         int len = s.length;
/*  91:113 */         for (int i = 0; i < len; i++) {
/*  92:114 */           sbuf.append(s[i]);
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:119 */     int nt_category = event.getLevel().toInt();
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:125 */     reportEvent(this._handle, sbuf.toString(), nt_category);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void finalize()
/* 106:    */   {
/* 107:131 */     deregisterEventSource(this._handle);
/* 108:132 */     this._handle = 0;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setSource(String source)
/* 112:    */   {
/* 113:141 */     this.source = source.trim();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getSource()
/* 117:    */   {
/* 118:146 */     return this.source;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean requiresLayout()
/* 122:    */   {
/* 123:154 */     return true;
/* 124:    */   }
/* 125:    */   
/* 126:    */   private native int registerEventSource(String paramString1, String paramString2);
/* 127:    */   
/* 128:    */   private native void reportEvent(int paramInt1, String paramString, int paramInt2);
/* 129:    */   
/* 130:    */   private native void deregisterEventSource(int paramInt);
/* 131:    */   
/* 132:    */   static
/* 133:    */   {
/* 134:    */     String[] archs;
/* 135:    */     try
/* 136:    */     {
/* 137:164 */       archs = new String[] { System.getProperty("os.arch") };
/* 138:    */     }
/* 139:    */     catch (SecurityException e)
/* 140:    */     {
/* 141:166 */       archs = new String[] { "amd64", "ia64", "x86" };
/* 142:    */     }
/* 143:168 */     boolean loaded = false;
/* 144:169 */     for (int i = 0; i < archs.length; i++) {
/* 145:    */       try
/* 146:    */       {
/* 147:171 */         System.loadLibrary("NTEventLogAppender." + archs[i]);
/* 148:172 */         loaded = true;
/* 149:    */       }
/* 150:    */       catch (UnsatisfiedLinkError e)
/* 151:    */       {
/* 152:175 */         loaded = false;
/* 153:    */       }
/* 154:    */     }
/* 155:178 */     if (!loaded) {
/* 156:179 */       System.loadLibrary("NTEventLogAppender");
/* 157:    */     }
/* 158:    */   }
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.nt.NTEventLogAppender
 * JD-Core Version:    0.7.0.1
 */