/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileNotFoundException;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.RandomAccessFile;
/*   7:    */ import org.apache.commons.io.FileUtils;
/*   8:    */ import org.apache.commons.io.IOUtils;
/*   9:    */ 
/*  10:    */ public class Tailer
/*  11:    */   implements Runnable
/*  12:    */ {
/*  13:    */   private final File file;
/*  14:    */   private final long delay;
/*  15:    */   private final boolean end;
/*  16:    */   private final TailerListener listener;
/*  17:132 */   private volatile boolean run = true;
/*  18:    */   
/*  19:    */   public Tailer(File file, TailerListener listener)
/*  20:    */   {
/*  21:140 */     this(file, listener, 1000L);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Tailer(File file, TailerListener listener, long delay)
/*  25:    */   {
/*  26:150 */     this(file, listener, 1000L, false);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Tailer(File file, TailerListener listener, long delay, boolean end)
/*  30:    */   {
/*  31:162 */     this.file = file;
/*  32:163 */     this.delay = delay;
/*  33:164 */     this.end = end;
/*  34:    */     
/*  35:    */ 
/*  36:167 */     this.listener = listener;
/*  37:168 */     listener.init(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static Tailer create(File file, TailerListener listener, long delay, boolean end)
/*  41:    */   {
/*  42:181 */     Tailer tailer = new Tailer(file, listener, delay, end);
/*  43:182 */     Thread thread = new Thread(tailer);
/*  44:183 */     thread.setDaemon(true);
/*  45:184 */     thread.start();
/*  46:185 */     return tailer;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static Tailer create(File file, TailerListener listener, long delay)
/*  50:    */   {
/*  51:197 */     return create(file, listener, delay, false);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static Tailer create(File file, TailerListener listener)
/*  55:    */   {
/*  56:209 */     return create(file, listener, 1000L, false);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public File getFile()
/*  60:    */   {
/*  61:218 */     return this.file;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public long getDelay()
/*  65:    */   {
/*  66:227 */     return this.delay;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void run()
/*  70:    */   {
/*  71:234 */     RandomAccessFile reader = null;
/*  72:    */     try
/*  73:    */     {
/*  74:236 */       long last = 0L;
/*  75:237 */       long position = 0L;
/*  76:239 */       while ((this.run) && (reader == null))
/*  77:    */       {
/*  78:    */         try
/*  79:    */         {
/*  80:241 */           reader = new RandomAccessFile(this.file, "r");
/*  81:    */         }
/*  82:    */         catch (FileNotFoundException e)
/*  83:    */         {
/*  84:243 */           this.listener.fileNotFound();
/*  85:    */         }
/*  86:246 */         if (reader == null)
/*  87:    */         {
/*  88:    */           try
/*  89:    */           {
/*  90:248 */             Thread.sleep(this.delay);
/*  91:    */           }
/*  92:    */           catch (InterruptedException e) {}
/*  93:    */         }
/*  94:    */         else
/*  95:    */         {
/*  96:253 */           position = this.end ? this.file.length() : 0L;
/*  97:254 */           last = System.currentTimeMillis();
/*  98:255 */           reader.seek(position);
/*  99:    */         }
/* 100:    */       }
/* 101:260 */       while (this.run)
/* 102:    */       {
/* 103:263 */         long length = this.file.length();
/* 104:265 */         if (length < position)
/* 105:    */         {
/* 106:268 */           this.listener.fileRotated();
/* 107:    */           try
/* 108:    */           {
/* 109:273 */             save = reader;
/* 110:274 */             reader = new RandomAccessFile(this.file, "r");
/* 111:275 */             position = 0L;
/* 112:    */           }
/* 113:    */           catch (FileNotFoundException e)
/* 114:    */           {
/* 115:    */             RandomAccessFile save;
/* 116:280 */             this.listener.fileNotFound();
/* 117:    */           }
/* 118:    */         }
/* 119:    */         else
/* 120:    */         {
/* 121:288 */           if (length > position)
/* 122:    */           {
/* 123:291 */             last = System.currentTimeMillis();
/* 124:292 */             position = readLines(reader);
/* 125:    */           }
/* 126:294 */           else if (FileUtils.isFileNewer(this.file, last))
/* 127:    */           {
/* 128:300 */             position = 0L;
/* 129:301 */             reader.seek(position);
/* 130:    */             
/* 131:    */ 
/* 132:304 */             last = System.currentTimeMillis();
/* 133:305 */             position = readLines(reader);
/* 134:    */           }
/* 135:    */           try
/* 136:    */           {
/* 137:309 */             Thread.sleep(this.delay);
/* 138:    */           }
/* 139:    */           catch (InterruptedException e) {}
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:316 */       this.listener.handle(e);
/* 146:    */     }
/* 147:    */     finally
/* 148:    */     {
/* 149:319 */       IOUtils.closeQuietly(reader);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void stop()
/* 154:    */   {
/* 155:327 */     this.run = false;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private long readLines(RandomAccessFile reader)
/* 159:    */     throws IOException
/* 160:    */   {
/* 161:338 */     String line = reader.readLine();
/* 162:339 */     while (line != null)
/* 163:    */     {
/* 164:340 */       this.listener.handle(line);
/* 165:341 */       line = reader.readLine();
/* 166:    */     }
/* 167:343 */     return reader.getFilePointer();
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.Tailer
 * JD-Core Version:    0.7.0.1
 */