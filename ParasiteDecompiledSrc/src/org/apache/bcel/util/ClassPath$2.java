/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ 
/*   8:    */ class ClassPath$2
/*   9:    */   extends ClassPath.ClassFile
/*  10:    */ {
/*  11:    */   private final File val$file;
/*  12:    */   private final ClassPath.Dir this$0;
/*  13:    */   
/*  14:    */   public InputStream getInputStream()
/*  15:    */     throws IOException
/*  16:    */   {
/*  17:305 */     return new FileInputStream(this.val$file);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String getPath()
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24:308 */       return this.val$file.getCanonicalPath();
/*  25:    */     }
/*  26:    */     catch (IOException e) {}
/*  27:309 */     return null;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public long getTime()
/*  31:    */   {
/*  32:312 */     return this.val$file.lastModified();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public long getSize()
/*  36:    */   {
/*  37:313 */     return this.val$file.length();
/*  38:    */   }
/*  39:    */   
/*  40:    */   ClassPath$2(ClassPath.Dir this$0, File val$file)
/*  41:    */   {
/*  42:313 */     this.this$0 = this$0;this.val$file = val$file;
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ClassPath.2
 * JD-Core Version:    0.7.0.1
 */