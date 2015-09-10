/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.awt.Dialog;
/*   4:    */ import java.awt.Window;
/*   5:    */ import java.io.BufferedInputStream;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileInputStream;
/*   8:    */ import java.io.FileNotFoundException;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.text.DateFormat;
/*  12:    */ import java.text.ParseException;
/*  13:    */ import java.text.SimpleDateFormat;
/*  14:    */ import java.util.Date;
/*  15:    */ import javax.swing.SwingUtilities;
/*  16:    */ import org.apache.log4j.lf5.Log4JLogRecord;
/*  17:    */ import org.apache.log4j.lf5.LogLevel;
/*  18:    */ import org.apache.log4j.lf5.LogLevelFormatException;
/*  19:    */ import org.apache.log4j.lf5.LogRecord;
/*  20:    */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*  21:    */ import org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog;
/*  22:    */ import org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog;
/*  23:    */ 
/*  24:    */ public class LogFileParser
/*  25:    */   implements Runnable
/*  26:    */ {
/*  27:    */   public static final String RECORD_DELIMITER = "[slf5s.start]";
/*  28:    */   public static final String ATTRIBUTE_DELIMITER = "[slf5s.";
/*  29:    */   public static final String DATE_DELIMITER = "[slf5s.DATE]";
/*  30:    */   public static final String THREAD_DELIMITER = "[slf5s.THREAD]";
/*  31:    */   public static final String CATEGORY_DELIMITER = "[slf5s.CATEGORY]";
/*  32:    */   public static final String LOCATION_DELIMITER = "[slf5s.LOCATION]";
/*  33:    */   public static final String MESSAGE_DELIMITER = "[slf5s.MESSAGE]";
/*  34:    */   public static final String PRIORITY_DELIMITER = "[slf5s.PRIORITY]";
/*  35:    */   public static final String NDC_DELIMITER = "[slf5s.NDC]";
/*  36: 69 */   private static SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,S");
/*  37:    */   private LogBrokerMonitor _monitor;
/*  38:    */   LogFactor5LoadingDialog _loadDialog;
/*  39: 72 */   private InputStream _in = null;
/*  40:    */   
/*  41:    */   public LogFileParser(File file)
/*  42:    */     throws IOException, FileNotFoundException
/*  43:    */   {
/*  44: 79 */     this(new FileInputStream(file));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public LogFileParser(InputStream stream)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 83 */     this._in = stream;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void parse(LogBrokerMonitor monitor)
/*  54:    */     throws RuntimeException
/*  55:    */   {
/*  56: 95 */     this._monitor = monitor;
/*  57: 96 */     Thread t = new Thread(this);
/*  58: 97 */     t.start();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void run()
/*  62:    */   {
/*  63:106 */     int index = 0;
/*  64:107 */     int counter = 0;
/*  65:    */     
/*  66:109 */     boolean isLogFile = false;
/*  67:    */     
/*  68:111 */     this._loadDialog = new LogFactor5LoadingDialog(this._monitor.getBaseFrame(), "Loading file...");
/*  69:    */     try
/*  70:    */     {
/*  71:116 */       String logRecords = loadLogFile(this._in);
/*  72:118 */       while ((counter = logRecords.indexOf("[slf5s.start]", index)) != -1)
/*  73:    */       {
/*  74:119 */         LogRecord temp = createLogRecord(logRecords.substring(index, counter));
/*  75:120 */         isLogFile = true;
/*  76:122 */         if (temp != null) {
/*  77:123 */           this._monitor.addMessage(temp);
/*  78:    */         }
/*  79:126 */         index = counter + "[slf5s.start]".length();
/*  80:    */       }
/*  81:129 */       if ((index < logRecords.length()) && (isLogFile))
/*  82:    */       {
/*  83:130 */         LogRecord temp = createLogRecord(logRecords.substring(index));
/*  84:132 */         if (temp != null) {
/*  85:133 */           this._monitor.addMessage(temp);
/*  86:    */         }
/*  87:    */       }
/*  88:137 */       if (!isLogFile) {
/*  89:138 */         throw new RuntimeException("Invalid log file format");
/*  90:    */       }
/*  91:140 */       SwingUtilities.invokeLater(new Runnable()
/*  92:    */       {
/*  93:    */         public void run()
/*  94:    */         {
/*  95:142 */           LogFileParser.this.destroyDialog();
/*  96:    */         }
/*  97:    */       });
/*  98:    */     }
/*  99:    */     catch (RuntimeException e)
/* 100:    */     {
/* 101:147 */       destroyDialog();
/* 102:148 */       displayError("Error - Invalid log file format.\nPlease see documentation on how to load log files.");
/* 103:    */     }
/* 104:    */     catch (IOException e)
/* 105:    */     {
/* 106:151 */       destroyDialog();
/* 107:152 */       displayError("Error - Unable to load log file!");
/* 108:    */     }
/* 109:155 */     this._in = null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void displayError(String message)
/* 113:    */   {
/* 114:162 */     LogFactor5ErrorDialog error = new LogFactor5ErrorDialog(this._monitor.getBaseFrame(), message);
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void destroyDialog()
/* 118:    */   {
/* 119:171 */     this._loadDialog.hide();
/* 120:172 */     this._loadDialog.dispose();
/* 121:    */   }
/* 122:    */   
/* 123:    */   private String loadLogFile(InputStream stream)
/* 124:    */     throws IOException
/* 125:    */   {
/* 126:179 */     BufferedInputStream br = new BufferedInputStream(stream);
/* 127:    */     
/* 128:181 */     int count = 0;
/* 129:182 */     int size = br.available();
/* 130:    */     
/* 131:184 */     StringBuffer sb = null;
/* 132:185 */     if (size > 0) {
/* 133:186 */       sb = new StringBuffer(size);
/* 134:    */     } else {
/* 135:188 */       sb = new StringBuffer(1024);
/* 136:    */     }
/* 137:191 */     while ((count = br.read()) != -1) {
/* 138:192 */       sb.append((char)count);
/* 139:    */     }
/* 140:195 */     br.close();
/* 141:196 */     br = null;
/* 142:197 */     return sb.toString();
/* 143:    */   }
/* 144:    */   
/* 145:    */   private String parseAttribute(String name, String record)
/* 146:    */   {
/* 147:203 */     int index = record.indexOf(name);
/* 148:205 */     if (index == -1) {
/* 149:206 */       return null;
/* 150:    */     }
/* 151:209 */     return getAttribute(index, record);
/* 152:    */   }
/* 153:    */   
/* 154:    */   private long parseDate(String record)
/* 155:    */   {
/* 156:    */     try
/* 157:    */     {
/* 158:214 */       String s = parseAttribute("[slf5s.DATE]", record);
/* 159:216 */       if (s == null) {
/* 160:217 */         return 0L;
/* 161:    */       }
/* 162:220 */       Date d = _sdf.parse(s);
/* 163:    */       
/* 164:222 */       return d.getTime();
/* 165:    */     }
/* 166:    */     catch (ParseException e) {}
/* 167:224 */     return 0L;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private LogLevel parsePriority(String record)
/* 171:    */   {
/* 172:229 */     String temp = parseAttribute("[slf5s.PRIORITY]", record);
/* 173:231 */     if (temp != null) {
/* 174:    */       try
/* 175:    */       {
/* 176:233 */         return LogLevel.valueOf(temp);
/* 177:    */       }
/* 178:    */       catch (LogLevelFormatException e)
/* 179:    */       {
/* 180:235 */         return LogLevel.DEBUG;
/* 181:    */       }
/* 182:    */     }
/* 183:240 */     return LogLevel.DEBUG;
/* 184:    */   }
/* 185:    */   
/* 186:    */   private String parseThread(String record)
/* 187:    */   {
/* 188:244 */     return parseAttribute("[slf5s.THREAD]", record);
/* 189:    */   }
/* 190:    */   
/* 191:    */   private String parseCategory(String record)
/* 192:    */   {
/* 193:248 */     return parseAttribute("[slf5s.CATEGORY]", record);
/* 194:    */   }
/* 195:    */   
/* 196:    */   private String parseLocation(String record)
/* 197:    */   {
/* 198:252 */     return parseAttribute("[slf5s.LOCATION]", record);
/* 199:    */   }
/* 200:    */   
/* 201:    */   private String parseMessage(String record)
/* 202:    */   {
/* 203:256 */     return parseAttribute("[slf5s.MESSAGE]", record);
/* 204:    */   }
/* 205:    */   
/* 206:    */   private String parseNDC(String record)
/* 207:    */   {
/* 208:260 */     return parseAttribute("[slf5s.NDC]", record);
/* 209:    */   }
/* 210:    */   
/* 211:    */   private String parseThrowable(String record)
/* 212:    */   {
/* 213:264 */     return getAttribute(record.length(), record);
/* 214:    */   }
/* 215:    */   
/* 216:    */   private LogRecord createLogRecord(String record)
/* 217:    */   {
/* 218:268 */     if ((record == null) || (record.trim().length() == 0)) {
/* 219:269 */       return null;
/* 220:    */     }
/* 221:272 */     LogRecord lr = new Log4JLogRecord();
/* 222:273 */     lr.setMillis(parseDate(record));
/* 223:274 */     lr.setLevel(parsePriority(record));
/* 224:275 */     lr.setCategory(parseCategory(record));
/* 225:276 */     lr.setLocation(parseLocation(record));
/* 226:277 */     lr.setThreadDescription(parseThread(record));
/* 227:278 */     lr.setNDC(parseNDC(record));
/* 228:279 */     lr.setMessage(parseMessage(record));
/* 229:280 */     lr.setThrownStackTrace(parseThrowable(record));
/* 230:    */     
/* 231:282 */     return lr;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private String getAttribute(int index, String record)
/* 235:    */   {
/* 236:287 */     int start = record.lastIndexOf("[slf5s.", index - 1);
/* 237:289 */     if (start == -1) {
/* 238:290 */       return record.substring(0, index);
/* 239:    */     }
/* 240:293 */     start = record.indexOf("]", start);
/* 241:    */     
/* 242:295 */     return record.substring(start + 1, index).trim();
/* 243:    */   }
/* 244:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.LogFileParser
 * JD-Core Version:    0.7.0.1
 */