/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.zip.ZipEntry;
/*   6:    */ import java.util.zip.ZipFile;
/*   7:    */ 
/*   8:    */ class ClassPath$3
/*   9:    */   extends ClassPath.ClassFile
/*  10:    */ {
/*  11:    */   private final ZipEntry val$entry;
/*  12:    */   private final ClassPath.Zip this$0;
/*  13:    */   
/*  14:    */   public InputStream getInputStream()
/*  15:    */     throws IOException
/*  16:    */   {
/*  17:329 */     return ClassPath.Zip.access$100(this.this$0).getInputStream(this.val$entry);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String getPath()
/*  21:    */   {
/*  22:330 */     return this.val$entry.toString();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public long getTime()
/*  26:    */   {
/*  27:331 */     return this.val$entry.getTime();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public long getSize()
/*  31:    */   {
/*  32:332 */     return this.val$entry.getSize();
/*  33:    */   }
/*  34:    */   
/*  35:    */   ClassPath$3(ClassPath.Zip this$0, ZipEntry val$entry)
/*  36:    */   {
/*  37:332 */     this.this$0 = this$0;this.val$entry = val$entry;
/*  38:    */   }
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassPath.3
 * JD-Core Version:    0.7.0.1
 */