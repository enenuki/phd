/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.net.MalformedURLException;
/*  6:   */ import java.net.URL;
/*  7:   */ 
/*  8:   */ public class ByteArrayClassPath
/*  9:   */   implements ClassPath
/* 10:   */ {
/* 11:   */   protected String classname;
/* 12:   */   protected byte[] classfile;
/* 13:   */   
/* 14:   */   public ByteArrayClassPath(String name, byte[] classfile)
/* 15:   */   {
/* 16:60 */     this.classname = name;
/* 17:61 */     this.classfile = classfile;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void close() {}
/* 21:   */   
/* 22:   */   public String toString()
/* 23:   */   {
/* 24:70 */     return "byte[]:" + this.classname;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public InputStream openClassfile(String classname)
/* 28:   */   {
/* 29:77 */     if (this.classname.equals(classname)) {
/* 30:78 */       return new ByteArrayInputStream(this.classfile);
/* 31:   */     }
/* 32:80 */     return null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public URL find(String classname)
/* 36:   */   {
/* 37:87 */     if (this.classname.equals(classname))
/* 38:   */     {
/* 39:88 */       String cname = classname.replace('.', '/') + ".class";
/* 40:   */       try
/* 41:   */       {
/* 42:91 */         return new URL("file:/ByteArrayClassPath/" + cname);
/* 43:   */       }
/* 44:   */       catch (MalformedURLException e) {}
/* 45:   */     }
/* 46:96 */     return null;
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ByteArrayClassPath
 * JD-Core Version:    0.7.0.1
 */