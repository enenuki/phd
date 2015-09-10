/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FileInputStream;
/*  5:   */ import java.io.FileNotFoundException;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.io.InputStream;
/*  8:   */ import java.net.MalformedURLException;
/*  9:   */ import java.net.URI;
/* 10:   */ import java.net.URL;
/* 11:   */ 
/* 12:   */ final class DirClassPath
/* 13:   */   implements ClassPath
/* 14:   */ {
/* 15:   */   String directory;
/* 16:   */   
/* 17:   */   DirClassPath(String dirName)
/* 18:   */   {
/* 19:38 */     this.directory = dirName;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public InputStream openClassfile(String classname)
/* 23:   */   {
/* 24:   */     try
/* 25:   */     {
/* 26:43 */       char sep = File.separatorChar;
/* 27:44 */       String filename = this.directory + sep + classname.replace('.', sep) + ".class";
/* 28:   */       
/* 29:46 */       return new FileInputStream(filename.toString());
/* 30:   */     }
/* 31:   */     catch (FileNotFoundException e) {}catch (SecurityException e) {}
/* 32:50 */     return null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public URL find(String classname)
/* 36:   */   {
/* 37:54 */     char sep = File.separatorChar;
/* 38:55 */     String filename = this.directory + sep + classname.replace('.', sep) + ".class";
/* 39:   */     
/* 40:57 */     File f = new File(filename);
/* 41:58 */     if (f.exists()) {
/* 42:   */       try
/* 43:   */       {
/* 44:60 */         return f.getCanonicalFile().toURI().toURL();
/* 45:   */       }
/* 46:   */       catch (MalformedURLException e) {}catch (IOException e) {}
/* 47:   */     }
/* 48:65 */     return null;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void close() {}
/* 52:   */   
/* 53:   */   public String toString()
/* 54:   */   {
/* 55:71 */     return this.directory;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.DirClassPath
 * JD-Core Version:    0.7.0.1
 */