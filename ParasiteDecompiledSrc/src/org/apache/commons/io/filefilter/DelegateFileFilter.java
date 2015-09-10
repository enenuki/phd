/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileFilter;
/*   5:    */ import java.io.FilenameFilter;
/*   6:    */ import java.io.Serializable;
/*   7:    */ 
/*   8:    */ public class DelegateFileFilter
/*   9:    */   extends AbstractFileFilter
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private final FilenameFilter filenameFilter;
/*  13:    */   private final FileFilter fileFilter;
/*  14:    */   
/*  15:    */   public DelegateFileFilter(FilenameFilter filter)
/*  16:    */   {
/*  17: 47 */     if (filter == null) {
/*  18: 48 */       throw new IllegalArgumentException("The FilenameFilter must not be null");
/*  19:    */     }
/*  20: 50 */     this.filenameFilter = filter;
/*  21: 51 */     this.fileFilter = null;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public DelegateFileFilter(FileFilter filter)
/*  25:    */   {
/*  26: 60 */     if (filter == null) {
/*  27: 61 */       throw new IllegalArgumentException("The FileFilter must not be null");
/*  28:    */     }
/*  29: 63 */     this.fileFilter = filter;
/*  30: 64 */     this.filenameFilter = null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean accept(File file)
/*  34:    */   {
/*  35: 75 */     if (this.fileFilter != null) {
/*  36: 76 */       return this.fileFilter.accept(file);
/*  37:    */     }
/*  38: 78 */     return super.accept(file);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean accept(File dir, String name)
/*  42:    */   {
/*  43: 91 */     if (this.filenameFilter != null) {
/*  44: 92 */       return this.filenameFilter.accept(dir, name);
/*  45:    */     }
/*  46: 94 */     return super.accept(dir, name);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toString()
/*  50:    */   {
/*  51:105 */     String delegate = this.fileFilter != null ? this.fileFilter.toString() : this.filenameFilter.toString();
/*  52:106 */     return super.toString() + "(" + delegate + ")";
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.DelegateFileFilter
 * JD-Core Version:    0.7.0.1
 */