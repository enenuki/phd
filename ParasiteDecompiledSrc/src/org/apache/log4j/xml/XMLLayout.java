/*   1:    */ package org.apache.log4j.xml;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Set;
/*   5:    */ import org.apache.log4j.Layout;
/*   6:    */ import org.apache.log4j.helpers.Transform;
/*   7:    */ import org.apache.log4j.spi.LocationInfo;
/*   8:    */ import org.apache.log4j.spi.LoggingEvent;
/*   9:    */ 
/*  10:    */ public class XMLLayout
/*  11:    */   extends Layout
/*  12:    */ {
/*  13: 70 */   private final int DEFAULT_SIZE = 256;
/*  14: 71 */   private final int UPPER_LIMIT = 2048;
/*  15: 73 */   private StringBuffer buf = new StringBuffer(256);
/*  16: 74 */   private boolean locationInfo = false;
/*  17: 75 */   private boolean properties = false;
/*  18:    */   
/*  19:    */   public void setLocationInfo(boolean flag)
/*  20:    */   {
/*  21: 89 */     this.locationInfo = flag;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean getLocationInfo()
/*  25:    */   {
/*  26: 96 */     return this.locationInfo;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setProperties(boolean flag)
/*  30:    */   {
/*  31:105 */     this.properties = flag;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean getProperties()
/*  35:    */   {
/*  36:114 */     return this.properties;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void activateOptions() {}
/*  40:    */   
/*  41:    */   public String format(LoggingEvent event)
/*  42:    */   {
/*  43:129 */     if (this.buf.capacity() > 2048) {
/*  44:130 */       this.buf = new StringBuffer(256);
/*  45:    */     } else {
/*  46:132 */       this.buf.setLength(0);
/*  47:    */     }
/*  48:137 */     this.buf.append("<log4j:event logger=\"");
/*  49:138 */     this.buf.append(Transform.escapeTags(event.getLoggerName()));
/*  50:139 */     this.buf.append("\" timestamp=\"");
/*  51:140 */     this.buf.append(event.timeStamp);
/*  52:141 */     this.buf.append("\" level=\"");
/*  53:142 */     this.buf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
/*  54:143 */     this.buf.append("\" thread=\"");
/*  55:144 */     this.buf.append(Transform.escapeTags(event.getThreadName()));
/*  56:145 */     this.buf.append("\">\r\n");
/*  57:    */     
/*  58:147 */     this.buf.append("<log4j:message><![CDATA[");
/*  59:    */     
/*  60:    */ 
/*  61:150 */     Transform.appendEscapingCDATA(this.buf, event.getRenderedMessage());
/*  62:151 */     this.buf.append("]]></log4j:message>\r\n");
/*  63:    */     
/*  64:153 */     String ndc = event.getNDC();
/*  65:154 */     if (ndc != null)
/*  66:    */     {
/*  67:155 */       this.buf.append("<log4j:NDC><![CDATA[");
/*  68:156 */       Transform.appendEscapingCDATA(this.buf, ndc);
/*  69:157 */       this.buf.append("]]></log4j:NDC>\r\n");
/*  70:    */     }
/*  71:160 */     String[] s = event.getThrowableStrRep();
/*  72:161 */     if (s != null)
/*  73:    */     {
/*  74:162 */       this.buf.append("<log4j:throwable><![CDATA[");
/*  75:163 */       for (int i = 0; i < s.length; i++)
/*  76:    */       {
/*  77:164 */         Transform.appendEscapingCDATA(this.buf, s[i]);
/*  78:165 */         this.buf.append("\r\n");
/*  79:    */       }
/*  80:167 */       this.buf.append("]]></log4j:throwable>\r\n");
/*  81:    */     }
/*  82:170 */     if (this.locationInfo)
/*  83:    */     {
/*  84:171 */       LocationInfo locationInfo = event.getLocationInformation();
/*  85:172 */       this.buf.append("<log4j:locationInfo class=\"");
/*  86:173 */       this.buf.append(Transform.escapeTags(locationInfo.getClassName()));
/*  87:174 */       this.buf.append("\" method=\"");
/*  88:175 */       this.buf.append(Transform.escapeTags(locationInfo.getMethodName()));
/*  89:176 */       this.buf.append("\" file=\"");
/*  90:177 */       this.buf.append(Transform.escapeTags(locationInfo.getFileName()));
/*  91:178 */       this.buf.append("\" line=\"");
/*  92:179 */       this.buf.append(locationInfo.getLineNumber());
/*  93:180 */       this.buf.append("\"/>\r\n");
/*  94:    */     }
/*  95:183 */     if (this.properties)
/*  96:    */     {
/*  97:184 */       Set keySet = event.getPropertyKeySet();
/*  98:185 */       if (keySet.size() > 0)
/*  99:    */       {
/* 100:186 */         this.buf.append("<log4j:properties>\r\n");
/* 101:187 */         Object[] keys = keySet.toArray();
/* 102:188 */         Arrays.sort(keys);
/* 103:189 */         for (int i = 0; i < keys.length; i++)
/* 104:    */         {
/* 105:190 */           String key = keys[i].toString();
/* 106:191 */           Object val = event.getMDC(key);
/* 107:192 */           if (val != null)
/* 108:    */           {
/* 109:193 */             this.buf.append("<log4j:data name=\"");
/* 110:194 */             this.buf.append(Transform.escapeTags(key));
/* 111:195 */             this.buf.append("\" value=\"");
/* 112:196 */             this.buf.append(Transform.escapeTags(String.valueOf(val)));
/* 113:197 */             this.buf.append("\"/>\r\n");
/* 114:    */           }
/* 115:    */         }
/* 116:200 */         this.buf.append("</log4j:properties>\r\n");
/* 117:    */       }
/* 118:    */     }
/* 119:204 */     this.buf.append("</log4j:event>\r\n\r\n");
/* 120:    */     
/* 121:206 */     return this.buf.toString();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean ignoresThrowable()
/* 125:    */   {
/* 126:214 */     return false;
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.xml.XMLLayout
 * JD-Core Version:    0.7.0.1
 */