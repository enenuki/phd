/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.RandomAccessFile;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import org.apache.commons.io.IOUtils;
/*   9:    */ 
/*  10:    */ public class MagicNumberFileFilter
/*  11:    */   extends AbstractFileFilter
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = -547733176983104172L;
/*  15:    */   private final byte[] magicNumbers;
/*  16:    */   private final long byteOffset;
/*  17:    */   
/*  18:    */   public MagicNumberFileFilter(byte[] magicNumber)
/*  19:    */   {
/*  20:112 */     this(magicNumber, 0L);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public MagicNumberFileFilter(String magicNumber)
/*  24:    */   {
/*  25:137 */     this(magicNumber, 0L);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public MagicNumberFileFilter(String magicNumber, long offset)
/*  29:    */   {
/*  30:161 */     if (magicNumber == null) {
/*  31:162 */       throw new IllegalArgumentException("The magic number cannot be null");
/*  32:    */     }
/*  33:164 */     if (magicNumber.length() == 0) {
/*  34:165 */       throw new IllegalArgumentException("The magic number must contain at least one byte");
/*  35:    */     }
/*  36:167 */     if (offset < 0L) {
/*  37:168 */       throw new IllegalArgumentException("The offset cannot be negative");
/*  38:    */     }
/*  39:171 */     this.magicNumbers = magicNumber.getBytes();
/*  40:172 */     this.byteOffset = offset;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public MagicNumberFileFilter(byte[] magicNumber, long offset)
/*  44:    */   {
/*  45:206 */     if (magicNumber == null) {
/*  46:207 */       throw new IllegalArgumentException("The magic number cannot be null");
/*  47:    */     }
/*  48:209 */     if (magicNumber.length == 0) {
/*  49:210 */       throw new IllegalArgumentException("The magic number must contain at least one byte");
/*  50:    */     }
/*  51:212 */     if (offset < 0L) {
/*  52:213 */       throw new IllegalArgumentException("The offset cannot be negative");
/*  53:    */     }
/*  54:216 */     this.magicNumbers = new byte[magicNumber.length];
/*  55:217 */     System.arraycopy(magicNumber, 0, this.magicNumbers, 0, magicNumber.length);
/*  56:218 */     this.byteOffset = offset;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean accept(File file)
/*  60:    */   {
/*  61:239 */     if ((file != null) && (file.isFile()) && (file.canRead()))
/*  62:    */     {
/*  63:240 */       RandomAccessFile randomAccessFile = null;
/*  64:    */       try
/*  65:    */       {
/*  66:242 */         byte[] fileBytes = new byte[this.magicNumbers.length];
/*  67:243 */         randomAccessFile = new RandomAccessFile(file, "r");
/*  68:244 */         randomAccessFile.seek(this.byteOffset);
/*  69:245 */         int read = randomAccessFile.read(fileBytes);
/*  70:    */         boolean bool;
/*  71:246 */         if (read != this.magicNumbers.length) {
/*  72:247 */           return false;
/*  73:    */         }
/*  74:249 */         return Arrays.equals(this.magicNumbers, fileBytes);
/*  75:    */       }
/*  76:    */       catch (IOException ioe) {}finally
/*  77:    */       {
/*  78:253 */         IOUtils.closeQuietly(randomAccessFile);
/*  79:    */       }
/*  80:    */     }
/*  81:257 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String toString()
/*  85:    */   {
/*  86:268 */     StringBuilder builder = new StringBuilder(super.toString());
/*  87:269 */     builder.append("(");
/*  88:270 */     builder.append(new String(this.magicNumbers));
/*  89:271 */     builder.append(",");
/*  90:272 */     builder.append(this.byteOffset);
/*  91:273 */     builder.append(")");
/*  92:274 */     return builder.toString();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.MagicNumberFileFilter
 * JD-Core Version:    0.7.0.1
 */