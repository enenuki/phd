/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.FilterWriter;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InterruptedIOException;
/*  10:    */ import java.io.Writer;
/*  11:    */ import org.apache.log4j.helpers.LogLog;
/*  12:    */ import org.apache.log4j.helpers.QuietWriter;
/*  13:    */ import org.apache.log4j.spi.ErrorHandler;
/*  14:    */ 
/*  15:    */ public class FileAppender
/*  16:    */   extends WriterAppender
/*  17:    */ {
/*  18: 54 */   protected boolean fileAppend = true;
/*  19: 58 */   protected String fileName = null;
/*  20: 62 */   protected boolean bufferedIO = false;
/*  21: 67 */   protected int bufferSize = 8192;
/*  22:    */   
/*  23:    */   public FileAppender() {}
/*  24:    */   
/*  25:    */   public FileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 93 */     this.layout = layout;
/*  29: 94 */     setFile(filename, append, bufferedIO, bufferSize);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public FileAppender(Layout layout, String filename, boolean append)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35:109 */     this.layout = layout;
/*  36:110 */     setFile(filename, append, false, this.bufferSize);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public FileAppender(Layout layout, String filename)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42:121 */     this(layout, filename, true);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setFile(String file)
/*  46:    */   {
/*  47:136 */     String val = file.trim();
/*  48:137 */     this.fileName = val;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean getAppend()
/*  52:    */   {
/*  53:145 */     return this.fileAppend;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getFile()
/*  57:    */   {
/*  58:152 */     return this.fileName;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void activateOptions()
/*  62:    */   {
/*  63:163 */     if (this.fileName != null)
/*  64:    */     {
/*  65:    */       try
/*  66:    */       {
/*  67:165 */         setFile(this.fileName, this.fileAppend, this.bufferedIO, this.bufferSize);
/*  68:    */       }
/*  69:    */       catch (IOException e)
/*  70:    */       {
/*  71:168 */         this.errorHandler.error("setFile(" + this.fileName + "," + this.fileAppend + ") call failed.", e, 4);
/*  72:    */       }
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:173 */       LogLog.warn("File option not set for appender [" + this.name + "].");
/*  77:174 */       LogLog.warn("Are you using FileAppender instead of ConsoleAppender?");
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected void closeFile()
/*  82:    */   {
/*  83:183 */     if (this.qw != null) {
/*  84:    */       try
/*  85:    */       {
/*  86:185 */         this.qw.close();
/*  87:    */       }
/*  88:    */       catch (IOException e)
/*  89:    */       {
/*  90:188 */         if ((e instanceof InterruptedIOException)) {
/*  91:189 */           Thread.currentThread().interrupt();
/*  92:    */         }
/*  93:193 */         LogLog.error("Could not close " + this.qw, e);
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean getBufferedIO()
/*  99:    */   {
/* 100:207 */     return this.bufferedIO;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getBufferSize()
/* 104:    */   {
/* 105:216 */     return this.bufferSize;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setAppend(boolean flag)
/* 109:    */   {
/* 110:233 */     this.fileAppend = flag;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setBufferedIO(boolean bufferedIO)
/* 114:    */   {
/* 115:248 */     this.bufferedIO = bufferedIO;
/* 116:249 */     if (bufferedIO) {
/* 117:250 */       this.immediateFlush = false;
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setBufferSize(int bufferSize)
/* 122:    */   {
/* 123:260 */     this.bufferSize = bufferSize;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
/* 127:    */     throws IOException
/* 128:    */   {
/* 129:281 */     LogLog.debug("setFile called: " + fileName + ", " + append);
/* 130:284 */     if (bufferedIO) {
/* 131:285 */       setImmediateFlush(false);
/* 132:    */     }
/* 133:288 */     reset();
/* 134:289 */     FileOutputStream ostream = null;
/* 135:    */     try
/* 136:    */     {
/* 137:294 */       ostream = new FileOutputStream(fileName, append);
/* 138:    */     }
/* 139:    */     catch (FileNotFoundException ex)
/* 140:    */     {
/* 141:301 */       String parentName = new File(fileName).getParent();
/* 142:302 */       if (parentName != null)
/* 143:    */       {
/* 144:303 */         File parentDir = new File(parentName);
/* 145:304 */         if ((!parentDir.exists()) && (parentDir.mkdirs())) {
/* 146:305 */           ostream = new FileOutputStream(fileName, append);
/* 147:    */         } else {
/* 148:307 */           throw ex;
/* 149:    */         }
/* 150:    */       }
/* 151:    */       else
/* 152:    */       {
/* 153:310 */         throw ex;
/* 154:    */       }
/* 155:    */     }
/* 156:313 */     Writer fw = createWriter(ostream);
/* 157:314 */     if (bufferedIO) {
/* 158:315 */       fw = new BufferedWriter(fw, bufferSize);
/* 159:    */     }
/* 160:317 */     setQWForFiles(fw);
/* 161:318 */     this.fileName = fileName;
/* 162:319 */     this.fileAppend = append;
/* 163:320 */     this.bufferedIO = bufferedIO;
/* 164:321 */     this.bufferSize = bufferSize;
/* 165:322 */     writeHeader();
/* 166:323 */     LogLog.debug("setFile ended");
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected void setQWForFiles(Writer writer)
/* 170:    */   {
/* 171:334 */     this.qw = new QuietWriter(writer, this.errorHandler);
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected void reset()
/* 175:    */   {
/* 176:343 */     closeFile();
/* 177:344 */     this.fileName = null;
/* 178:345 */     super.reset();
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.FileAppender
 * JD-Core Version:    0.7.0.1
 */