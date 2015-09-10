/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import org.apache.commons.io.IOUtils;
/*   9:    */ 
/*  10:    */ public class DeferredFileOutputStream
/*  11:    */   extends ThresholdingOutputStream
/*  12:    */ {
/*  13:    */   private ByteArrayOutputStream memoryOutputStream;
/*  14:    */   private OutputStream currentOutputStream;
/*  15:    */   private File outputFile;
/*  16:    */   private final String prefix;
/*  17:    */   private final String suffix;
/*  18:    */   private final File directory;
/*  19: 90 */   private boolean closed = false;
/*  20:    */   
/*  21:    */   public DeferredFileOutputStream(int threshold, File outputFile)
/*  22:    */   {
/*  23:104 */     this(threshold, outputFile, null, null, null);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DeferredFileOutputStream(int threshold, String prefix, String suffix, File directory)
/*  27:    */   {
/*  28:121 */     this(threshold, null, prefix, suffix, directory);
/*  29:122 */     if (prefix == null) {
/*  30:123 */       throw new IllegalArgumentException("Temporary file prefix is missing");
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   private DeferredFileOutputStream(int threshold, File outputFile, String prefix, String suffix, File directory)
/*  35:    */   {
/*  36:138 */     super(threshold);
/*  37:139 */     this.outputFile = outputFile;
/*  38:    */     
/*  39:141 */     this.memoryOutputStream = new ByteArrayOutputStream();
/*  40:142 */     this.currentOutputStream = this.memoryOutputStream;
/*  41:143 */     this.prefix = prefix;
/*  42:144 */     this.suffix = suffix;
/*  43:145 */     this.directory = directory;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected OutputStream getStream()
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:163 */     return this.currentOutputStream;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected void thresholdReached()
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:178 */     if (this.prefix != null) {
/*  56:179 */       this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
/*  57:    */     }
/*  58:181 */     FileOutputStream fos = new FileOutputStream(this.outputFile);
/*  59:182 */     this.memoryOutputStream.writeTo(fos);
/*  60:183 */     this.currentOutputStream = fos;
/*  61:184 */     this.memoryOutputStream = null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isInMemory()
/*  65:    */   {
/*  66:200 */     return !isThresholdExceeded();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public byte[] getData()
/*  70:    */   {
/*  71:214 */     if (this.memoryOutputStream != null) {
/*  72:216 */       return this.memoryOutputStream.toByteArray();
/*  73:    */     }
/*  74:218 */     return null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public File getFile()
/*  78:    */   {
/*  79:238 */     return this.outputFile;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void close()
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:250 */     super.close();
/*  86:251 */     this.closed = true;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void writeTo(OutputStream out)
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:267 */     if (!this.closed) {
/*  93:269 */       throw new IOException("Stream not closed");
/*  94:    */     }
/*  95:272 */     if (isInMemory())
/*  96:    */     {
/*  97:274 */       this.memoryOutputStream.writeTo(out);
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:278 */       FileInputStream fis = new FileInputStream(this.outputFile);
/* 102:    */       try
/* 103:    */       {
/* 104:280 */         IOUtils.copy(fis, out);
/* 105:    */       }
/* 106:    */       finally
/* 107:    */       {
/* 108:282 */         IOUtils.closeQuietly(fis);
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.DeferredFileOutputStream
 * JD-Core Version:    0.7.0.1
 */