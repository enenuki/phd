/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import java.io.Writer;
/*   7:    */ import org.apache.log4j.helpers.CountingQuietWriter;
/*   8:    */ import org.apache.log4j.helpers.LogLog;
/*   9:    */ import org.apache.log4j.helpers.OptionConverter;
/*  10:    */ import org.apache.log4j.spi.LoggingEvent;
/*  11:    */ 
/*  12:    */ public class RollingFileAppender
/*  13:    */   extends FileAppender
/*  14:    */ {
/*  15: 50 */   protected long maxFileSize = 10485760L;
/*  16: 55 */   protected int maxBackupIndex = 1;
/*  17: 57 */   private long nextRollover = 0L;
/*  18:    */   
/*  19:    */   public RollingFileAppender() {}
/*  20:    */   
/*  21:    */   public RollingFileAppender(Layout layout, String filename, boolean append)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 79 */     super(layout, filename, append);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public RollingFileAppender(Layout layout, String filename)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 90 */     super(layout, filename);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getMaxBackupIndex()
/*  34:    */   {
/*  35: 98 */     return this.maxBackupIndex;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public long getMaximumFileSize()
/*  39:    */   {
/*  40:109 */     return this.maxFileSize;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void rollOver()
/*  44:    */   {
/*  45:131 */     if (this.qw != null)
/*  46:    */     {
/*  47:132 */       long size = ((CountingQuietWriter)this.qw).getCount();
/*  48:133 */       LogLog.debug("rolling over count=" + size);
/*  49:    */       
/*  50:    */ 
/*  51:136 */       this.nextRollover = (size + this.maxFileSize);
/*  52:    */     }
/*  53:138 */     LogLog.debug("maxBackupIndex=" + this.maxBackupIndex);
/*  54:    */     
/*  55:140 */     boolean renameSucceeded = true;
/*  56:142 */     if (this.maxBackupIndex > 0)
/*  57:    */     {
/*  58:144 */       File file = new File(this.fileName + '.' + this.maxBackupIndex);
/*  59:145 */       if (file.exists()) {
/*  60:146 */         renameSucceeded = file.delete();
/*  61:    */       }
/*  62:149 */       for (int i = this.maxBackupIndex - 1; (i >= 1) && (renameSucceeded); i--)
/*  63:    */       {
/*  64:150 */         file = new File(this.fileName + "." + i);
/*  65:151 */         if (file.exists())
/*  66:    */         {
/*  67:152 */           File target = new File(this.fileName + '.' + (i + 1));
/*  68:153 */           LogLog.debug("Renaming file " + file + " to " + target);
/*  69:154 */           renameSucceeded = file.renameTo(target);
/*  70:    */         }
/*  71:    */       }
/*  72:158 */       if (renameSucceeded)
/*  73:    */       {
/*  74:160 */         File target = new File(this.fileName + "." + 1);
/*  75:    */         
/*  76:162 */         closeFile();
/*  77:    */         
/*  78:164 */         file = new File(this.fileName);
/*  79:165 */         LogLog.debug("Renaming file " + file + " to " + target);
/*  80:166 */         renameSucceeded = file.renameTo(target);
/*  81:170 */         if (!renameSucceeded) {
/*  82:    */           try
/*  83:    */           {
/*  84:172 */             setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
/*  85:    */           }
/*  86:    */           catch (IOException e)
/*  87:    */           {
/*  88:175 */             if ((e instanceof InterruptedIOException)) {
/*  89:176 */               Thread.currentThread().interrupt();
/*  90:    */             }
/*  91:178 */             LogLog.error("setFile(" + this.fileName + ", true) call failed.", e);
/*  92:    */           }
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:187 */     if (renameSucceeded) {
/*  97:    */       try
/*  98:    */       {
/*  99:191 */         setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
/* 100:192 */         this.nextRollover = 0L;
/* 101:    */       }
/* 102:    */       catch (IOException e)
/* 103:    */       {
/* 104:195 */         if ((e instanceof InterruptedIOException)) {
/* 105:196 */           Thread.currentThread().interrupt();
/* 106:    */         }
/* 107:198 */         LogLog.error("setFile(" + this.fileName + ", false) call failed.", e);
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:207 */     super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
/* 116:208 */     if (append)
/* 117:    */     {
/* 118:209 */       File f = new File(fileName);
/* 119:210 */       ((CountingQuietWriter)this.qw).setCount(f.length());
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setMaxBackupIndex(int maxBackups)
/* 124:    */   {
/* 125:226 */     this.maxBackupIndex = maxBackups;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setMaximumFileSize(long maxFileSize)
/* 129:    */   {
/* 130:243 */     this.maxFileSize = maxFileSize;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setMaxFileSize(String value)
/* 134:    */   {
/* 135:260 */     this.maxFileSize = OptionConverter.toFileSize(value, this.maxFileSize + 1L);
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void setQWForFiles(Writer writer)
/* 139:    */   {
/* 140:265 */     this.qw = new CountingQuietWriter(writer, this.errorHandler);
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected void subAppend(LoggingEvent event)
/* 144:    */   {
/* 145:276 */     super.subAppend(event);
/* 146:277 */     if ((this.fileName != null) && (this.qw != null))
/* 147:    */     {
/* 148:278 */       long size = ((CountingQuietWriter)this.qw).getCount();
/* 149:279 */       if ((size >= this.maxFileSize) && (size >= this.nextRollover)) {
/* 150:280 */         rollOver();
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.RollingFileAppender
 * JD-Core Version:    0.7.0.1
 */