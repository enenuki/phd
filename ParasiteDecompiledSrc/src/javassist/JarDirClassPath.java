/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FilenameFilter;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.net.URL;
/*   7:    */ 
/*   8:    */ final class JarDirClassPath
/*   9:    */   implements ClassPath
/*  10:    */ {
/*  11:    */   JarClassPath[] jars;
/*  12:    */   
/*  13:    */   JarDirClassPath(String dirName)
/*  14:    */     throws NotFoundException
/*  15:    */   {
/*  16: 79 */     File[] files = new File(dirName).listFiles(new FilenameFilter()
/*  17:    */     {
/*  18:    */       public boolean accept(File dir, String name)
/*  19:    */       {
/*  20: 81 */         name = name.toLowerCase();
/*  21: 82 */         return (name.endsWith(".jar")) || (name.endsWith(".zip"));
/*  22:    */       }
/*  23:    */     });
/*  24: 86 */     if (files != null)
/*  25:    */     {
/*  26: 87 */       this.jars = new JarClassPath[files.length];
/*  27: 88 */       for (int i = 0; i < files.length; i++) {
/*  28: 89 */         this.jars[i] = new JarClassPath(files[i].getPath());
/*  29:    */       }
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public InputStream openClassfile(String classname)
/*  34:    */     throws NotFoundException
/*  35:    */   {
/*  36: 94 */     if (this.jars != null) {
/*  37: 95 */       for (int i = 0; i < this.jars.length; i++)
/*  38:    */       {
/*  39: 96 */         InputStream is = this.jars[i].openClassfile(classname);
/*  40: 97 */         if (is != null) {
/*  41: 98 */           return is;
/*  42:    */         }
/*  43:    */       }
/*  44:    */     }
/*  45:101 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public URL find(String classname)
/*  49:    */   {
/*  50:105 */     if (this.jars != null) {
/*  51:106 */       for (int i = 0; i < this.jars.length; i++)
/*  52:    */       {
/*  53:107 */         URL url = this.jars[i].find(classname);
/*  54:108 */         if (url != null) {
/*  55:109 */           return url;
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59:112 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void close()
/*  63:    */   {
/*  64:116 */     if (this.jars != null) {
/*  65:117 */       for (int i = 0; i < this.jars.length; i++) {
/*  66:118 */         this.jars[i].close();
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.JarDirClassPath
 * JD-Core Version:    0.7.0.1
 */