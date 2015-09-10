/*   1:    */ package org.apache.log4j.lf5;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringWriter;
/*   7:    */ 
/*   8:    */ public abstract class LogRecord
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11: 41 */   protected static long _seqCount = 0L;
/*  12:    */   protected LogLevel _level;
/*  13:    */   protected String _message;
/*  14:    */   protected long _sequenceNumber;
/*  15:    */   protected long _millis;
/*  16:    */   protected String _category;
/*  17:    */   protected String _thread;
/*  18:    */   protected String _thrownStackTrace;
/*  19:    */   protected Throwable _thrown;
/*  20:    */   protected String _ndc;
/*  21:    */   protected String _location;
/*  22:    */   
/*  23:    */   public LogRecord()
/*  24:    */   {
/*  25: 65 */     this._millis = System.currentTimeMillis();
/*  26: 66 */     this._category = "Debug";
/*  27: 67 */     this._message = "";
/*  28: 68 */     this._level = LogLevel.INFO;
/*  29: 69 */     this._sequenceNumber = getNextId();
/*  30: 70 */     this._thread = Thread.currentThread().toString();
/*  31: 71 */     this._ndc = "";
/*  32: 72 */     this._location = "";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public LogLevel getLevel()
/*  36:    */   {
/*  37: 87 */     return this._level;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setLevel(LogLevel level)
/*  41:    */   {
/*  42: 98 */     this._level = level;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public abstract boolean isSevereLevel();
/*  46:    */   
/*  47:    */   public boolean hasThrown()
/*  48:    */   {
/*  49:111 */     Throwable thrown = getThrown();
/*  50:112 */     if (thrown == null) {
/*  51:113 */       return false;
/*  52:    */     }
/*  53:115 */     String thrownString = thrown.toString();
/*  54:116 */     return (thrownString != null) && (thrownString.trim().length() != 0);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isFatal()
/*  58:    */   {
/*  59:123 */     return (isSevereLevel()) || (hasThrown());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getCategory()
/*  63:    */   {
/*  64:134 */     return this._category;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setCategory(String category)
/*  68:    */   {
/*  69:156 */     this._category = category;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getMessage()
/*  73:    */   {
/*  74:166 */     return this._message;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setMessage(String message)
/*  78:    */   {
/*  79:176 */     this._message = message;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public long getSequenceNumber()
/*  83:    */   {
/*  84:188 */     return this._sequenceNumber;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setSequenceNumber(long number)
/*  88:    */   {
/*  89:200 */     this._sequenceNumber = number;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public long getMillis()
/*  93:    */   {
/*  94:212 */     return this._millis;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setMillis(long millis)
/*  98:    */   {
/*  99:223 */     this._millis = millis;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getThreadDescription()
/* 103:    */   {
/* 104:236 */     return this._thread;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setThreadDescription(String threadDescription)
/* 108:    */   {
/* 109:249 */     this._thread = threadDescription;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getThrownStackTrace()
/* 113:    */   {
/* 114:270 */     return this._thrownStackTrace;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setThrownStackTrace(String trace)
/* 118:    */   {
/* 119:280 */     this._thrownStackTrace = trace;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Throwable getThrown()
/* 123:    */   {
/* 124:291 */     return this._thrown;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setThrown(Throwable thrown)
/* 128:    */   {
/* 129:304 */     if (thrown == null) {
/* 130:305 */       return;
/* 131:    */     }
/* 132:307 */     this._thrown = thrown;
/* 133:308 */     StringWriter sw = new StringWriter();
/* 134:309 */     PrintWriter out = new PrintWriter(sw);
/* 135:310 */     thrown.printStackTrace(out);
/* 136:311 */     out.flush();
/* 137:312 */     this._thrownStackTrace = sw.toString();
/* 138:    */     try
/* 139:    */     {
/* 140:314 */       out.close();
/* 141:315 */       sw.close();
/* 142:    */     }
/* 143:    */     catch (IOException e) {}
/* 144:319 */     out = null;
/* 145:320 */     sw = null;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String toString()
/* 149:    */   {
/* 150:327 */     StringBuffer buf = new StringBuffer();
/* 151:328 */     buf.append("LogRecord: [" + this._level + ", " + this._message + "]");
/* 152:329 */     return buf.toString();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getNDC()
/* 156:    */   {
/* 157:338 */     return this._ndc;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setNDC(String ndc)
/* 161:    */   {
/* 162:347 */     this._ndc = ndc;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getLocation()
/* 166:    */   {
/* 167:356 */     return this._location;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setLocation(String location)
/* 171:    */   {
/* 172:365 */     this._location = location;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static synchronized void resetSequenceNumber()
/* 176:    */   {
/* 177:373 */     _seqCount = 0L;
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected static synchronized long getNextId()
/* 181:    */   {
/* 182:381 */     _seqCount += 1L;
/* 183:382 */     return _seqCount;
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.LogRecord
 * JD-Core Version:    0.7.0.1
 */