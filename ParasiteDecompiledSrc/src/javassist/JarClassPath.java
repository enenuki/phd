/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.net.MalformedURLException;
/*   7:    */ import java.net.URI;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.util.jar.JarEntry;
/*  10:    */ import java.util.jar.JarFile;
/*  11:    */ 
/*  12:    */ final class JarClassPath
/*  13:    */   implements ClassPath
/*  14:    */ {
/*  15:    */   JarFile jarfile;
/*  16:    */   String jarfileURL;
/*  17:    */   
/*  18:    */   JarClassPath(String pathname)
/*  19:    */     throws NotFoundException
/*  20:    */   {
/*  21:    */     try
/*  22:    */     {
/*  23:128 */       this.jarfile = new JarFile(pathname);
/*  24:129 */       this.jarfileURL = new File(pathname).getCanonicalFile().toURI().toURL().toString();
/*  25:    */       
/*  26:131 */       return;
/*  27:    */     }
/*  28:    */     catch (IOException e)
/*  29:    */     {
/*  30:134 */       throw new NotFoundException(pathname);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public InputStream openClassfile(String classname)
/*  35:    */     throws NotFoundException
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39:141 */       String jarname = classname.replace('.', '/') + ".class";
/*  40:142 */       JarEntry je = this.jarfile.getJarEntry(jarname);
/*  41:143 */       if (je != null) {
/*  42:144 */         return this.jarfile.getInputStream(je);
/*  43:    */       }
/*  44:146 */       return null;
/*  45:    */     }
/*  46:    */     catch (IOException e)
/*  47:    */     {
/*  48:149 */       throw new NotFoundException("broken jar file?: " + this.jarfile.getName());
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public URL find(String classname)
/*  53:    */   {
/*  54:154 */     String jarname = classname.replace('.', '/') + ".class";
/*  55:155 */     JarEntry je = this.jarfile.getJarEntry(jarname);
/*  56:156 */     if (je != null) {
/*  57:    */       try
/*  58:    */       {
/*  59:158 */         return new URL("jar:" + this.jarfileURL + "!/" + jarname);
/*  60:    */       }
/*  61:    */       catch (MalformedURLException e) {}
/*  62:    */     }
/*  63:162 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void close()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:167 */       this.jarfile.close();
/*  71:168 */       this.jarfile = null;
/*  72:    */     }
/*  73:    */     catch (IOException e) {}
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toString()
/*  77:    */   {
/*  78:174 */     return this.jarfile == null ? "<null>" : this.jarfile.toString();
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.JarClassPath
 * JD-Core Version:    0.7.0.1
 */