/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.FileWriter;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import java.io.OutputStreamWriter;
/*   9:    */ import java.io.Writer;
/*  10:    */ import org.apache.commons.io.FileUtils;
/*  11:    */ import org.apache.commons.io.IOUtils;
/*  12:    */ 
/*  13:    */ public class LockableFileWriter
/*  14:    */   extends Writer
/*  15:    */ {
/*  16:    */   private static final String LCK = ".lck";
/*  17:    */   private final Writer out;
/*  18:    */   private final File lockFile;
/*  19:    */   
/*  20:    */   public LockableFileWriter(String fileName)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 77 */     this(fileName, false, null);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public LockableFileWriter(String fileName, boolean append)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 89 */     this(fileName, append, null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LockableFileWriter(String fileName, boolean append, String lockDir)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35:102 */     this(new File(fileName), append, lockDir);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public LockableFileWriter(File file)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41:114 */     this(file, false, null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public LockableFileWriter(File file, boolean append)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47:126 */     this(file, append, null);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public LockableFileWriter(File file, boolean append, String lockDir)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:139 */     this(file, null, append, lockDir);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public LockableFileWriter(File file, String encoding)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:151 */     this(file, encoding, false, null);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public LockableFileWriter(File file, String encoding, boolean append, String lockDir)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65:168 */     file = file.getAbsoluteFile();
/*  66:169 */     if (file.getParentFile() != null) {
/*  67:170 */       FileUtils.forceMkdir(file.getParentFile());
/*  68:    */     }
/*  69:172 */     if (file.isDirectory()) {
/*  70:173 */       throw new IOException("File specified is a directory");
/*  71:    */     }
/*  72:177 */     if (lockDir == null) {
/*  73:178 */       lockDir = System.getProperty("java.io.tmpdir");
/*  74:    */     }
/*  75:180 */     File lockDirFile = new File(lockDir);
/*  76:181 */     FileUtils.forceMkdir(lockDirFile);
/*  77:182 */     testLockDir(lockDirFile);
/*  78:183 */     this.lockFile = new File(lockDirFile, file.getName() + ".lck");
/*  79:    */     
/*  80:    */ 
/*  81:186 */     createLock();
/*  82:    */     
/*  83:    */ 
/*  84:189 */     this.out = initWriter(file, encoding, append);
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void testLockDir(File lockDir)
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:201 */     if (!lockDir.exists()) {
/*  91:202 */       throw new IOException("Could not find lockDir: " + lockDir.getAbsolutePath());
/*  92:    */     }
/*  93:205 */     if (!lockDir.canWrite()) {
/*  94:206 */       throw new IOException("Could not write to lockDir: " + lockDir.getAbsolutePath());
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void createLock()
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:217 */     synchronized (LockableFileWriter.class)
/* 102:    */     {
/* 103:218 */       if (!this.lockFile.createNewFile()) {
/* 104:219 */         throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
/* 105:    */       }
/* 106:222 */       this.lockFile.deleteOnExit();
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private Writer initWriter(File file, String encoding, boolean append)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:237 */     boolean fileExistedAlready = file.exists();
/* 114:238 */     OutputStream stream = null;
/* 115:239 */     Writer writer = null;
/* 116:    */     try
/* 117:    */     {
/* 118:241 */       if (encoding == null)
/* 119:    */       {
/* 120:242 */         writer = new FileWriter(file.getAbsolutePath(), append);
/* 121:    */       }
/* 122:    */       else
/* 123:    */       {
/* 124:244 */         stream = new FileOutputStream(file.getAbsolutePath(), append);
/* 125:245 */         writer = new OutputStreamWriter(stream, encoding);
/* 126:    */       }
/* 127:    */     }
/* 128:    */     catch (IOException ex)
/* 129:    */     {
/* 130:248 */       IOUtils.closeQuietly(writer);
/* 131:249 */       IOUtils.closeQuietly(stream);
/* 132:250 */       FileUtils.deleteQuietly(this.lockFile);
/* 133:251 */       if (!fileExistedAlready) {
/* 134:252 */         FileUtils.deleteQuietly(file);
/* 135:    */       }
/* 136:254 */       throw ex;
/* 137:    */     }
/* 138:    */     catch (RuntimeException ex)
/* 139:    */     {
/* 140:256 */       IOUtils.closeQuietly(writer);
/* 141:257 */       IOUtils.closeQuietly(stream);
/* 142:258 */       FileUtils.deleteQuietly(this.lockFile);
/* 143:259 */       if (!fileExistedAlready) {
/* 144:260 */         FileUtils.deleteQuietly(file);
/* 145:    */       }
/* 146:262 */       throw ex;
/* 147:    */     }
/* 148:264 */     return writer;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void close()
/* 152:    */     throws IOException
/* 153:    */   {
/* 154:    */     try
/* 155:    */     {
/* 156:276 */       this.out.close();
/* 157:    */     }
/* 158:    */     finally
/* 159:    */     {
/* 160:278 */       this.lockFile.delete();
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void write(int idx)
/* 165:    */     throws IOException
/* 166:    */   {
/* 167:290 */     this.out.write(idx);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void write(char[] chr)
/* 171:    */     throws IOException
/* 172:    */   {
/* 173:300 */     this.out.write(chr);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void write(char[] chr, int st, int end)
/* 177:    */     throws IOException
/* 178:    */   {
/* 179:312 */     this.out.write(chr, st, end);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void write(String str)
/* 183:    */     throws IOException
/* 184:    */   {
/* 185:322 */     this.out.write(str);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void write(String str, int st, int end)
/* 189:    */     throws IOException
/* 190:    */   {
/* 191:334 */     this.out.write(str, st, end);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void flush()
/* 195:    */     throws IOException
/* 196:    */   {
/* 197:343 */     this.out.flush();
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.LockableFileWriter
 * JD-Core Version:    0.7.0.1
 */