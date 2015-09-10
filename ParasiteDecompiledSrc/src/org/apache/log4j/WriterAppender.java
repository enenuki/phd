/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.FilterWriter;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.OutputStreamWriter;
/*   8:    */ import java.io.Writer;
/*   9:    */ import org.apache.log4j.helpers.LogLog;
/*  10:    */ import org.apache.log4j.helpers.QuietWriter;
/*  11:    */ import org.apache.log4j.spi.ErrorHandler;
/*  12:    */ import org.apache.log4j.spi.LoggingEvent;
/*  13:    */ 
/*  14:    */ public class WriterAppender
/*  15:    */   extends AppenderSkeleton
/*  16:    */ {
/*  17: 57 */   protected boolean immediateFlush = true;
/*  18:    */   protected String encoding;
/*  19:    */   protected QuietWriter qw;
/*  20:    */   
/*  21:    */   public WriterAppender() {}
/*  22:    */   
/*  23:    */   public WriterAppender(Layout layout, OutputStream os)
/*  24:    */   {
/*  25: 85 */     this(layout, new OutputStreamWriter(os));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public WriterAppender(Layout layout, Writer writer)
/*  29:    */   {
/*  30: 96 */     this.layout = layout;
/*  31: 97 */     setWriter(writer);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setImmediateFlush(boolean value)
/*  35:    */   {
/*  36:116 */     this.immediateFlush = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean getImmediateFlush()
/*  40:    */   {
/*  41:124 */     return this.immediateFlush;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void activateOptions() {}
/*  45:    */   
/*  46:    */   public void append(LoggingEvent event)
/*  47:    */   {
/*  48:159 */     if (!checkEntryConditions()) {
/*  49:160 */       return;
/*  50:    */     }
/*  51:162 */     subAppend(event);
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected boolean checkEntryConditions()
/*  55:    */   {
/*  56:173 */     if (this.closed)
/*  57:    */     {
/*  58:174 */       LogLog.warn("Not allowed to write to a closed appender.");
/*  59:175 */       return false;
/*  60:    */     }
/*  61:178 */     if (this.qw == null)
/*  62:    */     {
/*  63:179 */       this.errorHandler.error("No output stream or file set for the appender named [" + this.name + "].");
/*  64:    */       
/*  65:181 */       return false;
/*  66:    */     }
/*  67:184 */     if (this.layout == null)
/*  68:    */     {
/*  69:185 */       this.errorHandler.error("No layout set for the appender named [" + this.name + "].");
/*  70:186 */       return false;
/*  71:    */     }
/*  72:188 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized void close()
/*  76:    */   {
/*  77:203 */     if (this.closed) {
/*  78:204 */       return;
/*  79:    */     }
/*  80:205 */     this.closed = true;
/*  81:206 */     writeFooter();
/*  82:207 */     reset();
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void closeWriter()
/*  86:    */   {
/*  87:214 */     if (this.qw != null) {
/*  88:    */       try
/*  89:    */       {
/*  90:216 */         this.qw.close();
/*  91:    */       }
/*  92:    */       catch (IOException e)
/*  93:    */       {
/*  94:218 */         if ((e instanceof InterruptedIOException)) {
/*  95:219 */           Thread.currentThread().interrupt();
/*  96:    */         }
/*  97:223 */         LogLog.error("Could not close " + this.qw, e);
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected OutputStreamWriter createWriter(OutputStream os)
/* 103:    */   {
/* 104:236 */     OutputStreamWriter retval = null;
/* 105:    */     
/* 106:238 */     String enc = getEncoding();
/* 107:239 */     if (enc != null) {
/* 108:    */       try
/* 109:    */       {
/* 110:241 */         retval = new OutputStreamWriter(os, enc);
/* 111:    */       }
/* 112:    */       catch (IOException e)
/* 113:    */       {
/* 114:243 */         if ((e instanceof InterruptedIOException)) {
/* 115:244 */           Thread.currentThread().interrupt();
/* 116:    */         }
/* 117:246 */         LogLog.warn("Error initializing output writer.");
/* 118:247 */         LogLog.warn("Unsupported encoding?");
/* 119:    */       }
/* 120:    */     }
/* 121:250 */     if (retval == null) {
/* 122:251 */       retval = new OutputStreamWriter(os);
/* 123:    */     }
/* 124:253 */     return retval;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getEncoding()
/* 128:    */   {
/* 129:257 */     return this.encoding;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setEncoding(String value)
/* 133:    */   {
/* 134:261 */     this.encoding = value;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public synchronized void setErrorHandler(ErrorHandler eh)
/* 138:    */   {
/* 139:271 */     if (eh == null)
/* 140:    */     {
/* 141:272 */       LogLog.warn("You have tried to set a null error-handler.");
/* 142:    */     }
/* 143:    */     else
/* 144:    */     {
/* 145:274 */       this.errorHandler = eh;
/* 146:275 */       if (this.qw != null) {
/* 147:276 */         this.qw.setErrorHandler(eh);
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public synchronized void setWriter(Writer writer)
/* 153:    */   {
/* 154:294 */     reset();
/* 155:295 */     this.qw = new QuietWriter(writer, this.errorHandler);
/* 156:    */     
/* 157:297 */     writeHeader();
/* 158:    */   }
/* 159:    */   
/* 160:    */   protected void subAppend(LoggingEvent event)
/* 161:    */   {
/* 162:310 */     this.qw.write(this.layout.format(event));
/* 163:312 */     if (this.layout.ignoresThrowable())
/* 164:    */     {
/* 165:313 */       String[] s = event.getThrowableStrRep();
/* 166:314 */       if (s != null)
/* 167:    */       {
/* 168:315 */         int len = s.length;
/* 169:316 */         for (int i = 0; i < len; i++)
/* 170:    */         {
/* 171:317 */           this.qw.write(s[i]);
/* 172:318 */           this.qw.write(Layout.LINE_SEP);
/* 173:    */         }
/* 174:    */       }
/* 175:    */     }
/* 176:323 */     if (shouldFlush(event)) {
/* 177:324 */       this.qw.flush();
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean requiresLayout()
/* 182:    */   {
/* 183:336 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected void reset()
/* 187:    */   {
/* 188:346 */     closeWriter();
/* 189:347 */     this.qw = null;
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected void writeFooter()
/* 193:    */   {
/* 194:357 */     if (this.layout != null)
/* 195:    */     {
/* 196:358 */       String f = this.layout.getFooter();
/* 197:359 */       if ((f != null) && (this.qw != null))
/* 198:    */       {
/* 199:360 */         this.qw.write(f);
/* 200:361 */         this.qw.flush();
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected void writeHeader()
/* 206:    */   {
/* 207:371 */     if (this.layout != null)
/* 208:    */     {
/* 209:372 */       String h = this.layout.getHeader();
/* 210:373 */       if ((h != null) && (this.qw != null)) {
/* 211:374 */         this.qw.write(h);
/* 212:    */       }
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   protected boolean shouldFlush(LoggingEvent event)
/* 217:    */   {
/* 218:385 */     return this.immediateFlush;
/* 219:    */   }
/* 220:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.WriterAppender
 * JD-Core Version:    0.7.0.1
 */