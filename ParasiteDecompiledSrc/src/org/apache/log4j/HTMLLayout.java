/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import org.apache.log4j.helpers.Transform;
/*   5:    */ import org.apache.log4j.spi.LocationInfo;
/*   6:    */ import org.apache.log4j.spi.LoggingEvent;
/*   7:    */ 
/*   8:    */ public class HTMLLayout
/*   9:    */   extends Layout
/*  10:    */ {
/*  11: 36 */   protected final int BUF_SIZE = 256;
/*  12: 37 */   protected final int MAX_CAPACITY = 1024;
/*  13: 39 */   static String TRACE_PREFIX = "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
/*  14: 42 */   private StringBuffer sbuf = new StringBuffer(256);
/*  15:    */   /**
/*  16:    */    * @deprecated
/*  17:    */    */
/*  18:    */   public static final String LOCATION_INFO_OPTION = "LocationInfo";
/*  19:    */   public static final String TITLE_OPTION = "Title";
/*  20: 66 */   boolean locationInfo = false;
/*  21: 68 */   String title = "Log4J Log Messages";
/*  22:    */   
/*  23:    */   public void setLocationInfo(boolean flag)
/*  24:    */   {
/*  25: 83 */     this.locationInfo = flag;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean getLocationInfo()
/*  29:    */   {
/*  30: 91 */     return this.locationInfo;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setTitle(String title)
/*  34:    */   {
/*  35:102 */     this.title = title;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getTitle()
/*  39:    */   {
/*  40:110 */     return this.title;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getContentType()
/*  44:    */   {
/*  45:118 */     return "text/html";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void activateOptions() {}
/*  49:    */   
/*  50:    */   public String format(LoggingEvent event)
/*  51:    */   {
/*  52:131 */     if (this.sbuf.capacity() > 1024) {
/*  53:132 */       this.sbuf = new StringBuffer(256);
/*  54:    */     } else {
/*  55:134 */       this.sbuf.setLength(0);
/*  56:    */     }
/*  57:137 */     this.sbuf.append(Layout.LINE_SEP + "<tr>" + Layout.LINE_SEP);
/*  58:    */     
/*  59:139 */     this.sbuf.append("<td>");
/*  60:140 */     this.sbuf.append(event.timeStamp - LoggingEvent.getStartTime());
/*  61:141 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*  62:    */     
/*  63:143 */     String escapedThread = Transform.escapeTags(event.getThreadName());
/*  64:144 */     this.sbuf.append("<td title=\"" + escapedThread + " thread\">");
/*  65:145 */     this.sbuf.append(escapedThread);
/*  66:146 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*  67:    */     
/*  68:148 */     this.sbuf.append("<td title=\"Level\">");
/*  69:149 */     if (event.getLevel().equals(Level.DEBUG))
/*  70:    */     {
/*  71:150 */       this.sbuf.append("<font color=\"#339933\">");
/*  72:151 */       this.sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
/*  73:152 */       this.sbuf.append("</font>");
/*  74:    */     }
/*  75:154 */     else if (event.getLevel().isGreaterOrEqual(Level.WARN))
/*  76:    */     {
/*  77:155 */       this.sbuf.append("<font color=\"#993300\"><strong>");
/*  78:156 */       this.sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
/*  79:157 */       this.sbuf.append("</strong></font>");
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:159 */       this.sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
/*  84:    */     }
/*  85:161 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*  86:    */     
/*  87:163 */     String escapedLogger = Transform.escapeTags(event.getLoggerName());
/*  88:164 */     this.sbuf.append("<td title=\"" + escapedLogger + " category\">");
/*  89:165 */     this.sbuf.append(escapedLogger);
/*  90:166 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/*  91:168 */     if (this.locationInfo)
/*  92:    */     {
/*  93:169 */       LocationInfo locInfo = event.getLocationInformation();
/*  94:170 */       this.sbuf.append("<td>");
/*  95:171 */       this.sbuf.append(Transform.escapeTags(locInfo.getFileName()));
/*  96:172 */       this.sbuf.append(':');
/*  97:173 */       this.sbuf.append(locInfo.getLineNumber());
/*  98:174 */       this.sbuf.append("</td>" + Layout.LINE_SEP);
/*  99:    */     }
/* 100:177 */     this.sbuf.append("<td title=\"Message\">");
/* 101:178 */     this.sbuf.append(Transform.escapeTags(event.getRenderedMessage()));
/* 102:179 */     this.sbuf.append("</td>" + Layout.LINE_SEP);
/* 103:180 */     this.sbuf.append("</tr>" + Layout.LINE_SEP);
/* 104:182 */     if (event.getNDC() != null)
/* 105:    */     {
/* 106:183 */       this.sbuf.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : xx-small;\" colspan=\"6\" title=\"Nested Diagnostic Context\">");
/* 107:184 */       this.sbuf.append("NDC: " + Transform.escapeTags(event.getNDC()));
/* 108:185 */       this.sbuf.append("</td></tr>" + Layout.LINE_SEP);
/* 109:    */     }
/* 110:188 */     String[] s = event.getThrowableStrRep();
/* 111:189 */     if (s != null)
/* 112:    */     {
/* 113:190 */       this.sbuf.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : xx-small;\" colspan=\"6\">");
/* 114:191 */       appendThrowableAsHTML(s, this.sbuf);
/* 115:192 */       this.sbuf.append("</td></tr>" + Layout.LINE_SEP);
/* 116:    */     }
/* 117:195 */     return this.sbuf.toString();
/* 118:    */   }
/* 119:    */   
/* 120:    */   void appendThrowableAsHTML(String[] s, StringBuffer sbuf)
/* 121:    */   {
/* 122:199 */     if (s != null)
/* 123:    */     {
/* 124:200 */       int len = s.length;
/* 125:201 */       if (len == 0) {
/* 126:202 */         return;
/* 127:    */       }
/* 128:203 */       sbuf.append(Transform.escapeTags(s[0]));
/* 129:204 */       sbuf.append(Layout.LINE_SEP);
/* 130:205 */       for (int i = 1; i < len; i++)
/* 131:    */       {
/* 132:206 */         sbuf.append(TRACE_PREFIX);
/* 133:207 */         sbuf.append(Transform.escapeTags(s[i]));
/* 134:208 */         sbuf.append(Layout.LINE_SEP);
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getHeader()
/* 140:    */   {
/* 141:218 */     StringBuffer sbuf = new StringBuffer();
/* 142:219 */     sbuf.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + Layout.LINE_SEP);
/* 143:220 */     sbuf.append("<html>" + Layout.LINE_SEP);
/* 144:221 */     sbuf.append("<head>" + Layout.LINE_SEP);
/* 145:222 */     sbuf.append("<title>" + this.title + "</title>" + Layout.LINE_SEP);
/* 146:223 */     sbuf.append("<style type=\"text/css\">" + Layout.LINE_SEP);
/* 147:224 */     sbuf.append("<!--" + Layout.LINE_SEP);
/* 148:225 */     sbuf.append("body, table {font-family: arial,sans-serif; font-size: x-small;}" + Layout.LINE_SEP);
/* 149:226 */     sbuf.append("th {background: #336699; color: #FFFFFF; text-align: left;}" + Layout.LINE_SEP);
/* 150:227 */     sbuf.append("-->" + Layout.LINE_SEP);
/* 151:228 */     sbuf.append("</style>" + Layout.LINE_SEP);
/* 152:229 */     sbuf.append("</head>" + Layout.LINE_SEP);
/* 153:230 */     sbuf.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">" + Layout.LINE_SEP);
/* 154:231 */     sbuf.append("<hr size=\"1\" noshade>" + Layout.LINE_SEP);
/* 155:232 */     sbuf.append("Log session start time " + new Date() + "<br>" + Layout.LINE_SEP);
/* 156:233 */     sbuf.append("<br>" + Layout.LINE_SEP);
/* 157:234 */     sbuf.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">" + Layout.LINE_SEP);
/* 158:235 */     sbuf.append("<tr>" + Layout.LINE_SEP);
/* 159:236 */     sbuf.append("<th>Time</th>" + Layout.LINE_SEP);
/* 160:237 */     sbuf.append("<th>Thread</th>" + Layout.LINE_SEP);
/* 161:238 */     sbuf.append("<th>Level</th>" + Layout.LINE_SEP);
/* 162:239 */     sbuf.append("<th>Category</th>" + Layout.LINE_SEP);
/* 163:240 */     if (this.locationInfo) {
/* 164:241 */       sbuf.append("<th>File:Line</th>" + Layout.LINE_SEP);
/* 165:    */     }
/* 166:243 */     sbuf.append("<th>Message</th>" + Layout.LINE_SEP);
/* 167:244 */     sbuf.append("</tr>" + Layout.LINE_SEP);
/* 168:245 */     return sbuf.toString();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getFooter()
/* 172:    */   {
/* 173:253 */     StringBuffer sbuf = new StringBuffer();
/* 174:254 */     sbuf.append("</table>" + Layout.LINE_SEP);
/* 175:255 */     sbuf.append("<br>" + Layout.LINE_SEP);
/* 176:256 */     sbuf.append("</body></html>");
/* 177:257 */     return sbuf.toString();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean ignoresThrowable()
/* 181:    */   {
/* 182:265 */     return false;
/* 183:    */   }
/* 184:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.HTMLLayout
 * JD-Core Version:    0.7.0.1
 */