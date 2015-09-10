/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import java.net.URL;
/*  5:   */ 
/*  6:   */ public class ClassClassPath
/*  7:   */   implements ClassPath
/*  8:   */ {
/*  9:   */   private Class thisClass;
/* 10:   */   
/* 11:   */   public ClassClassPath(Class c)
/* 12:   */   {
/* 13:54 */     this.thisClass = c;
/* 14:   */   }
/* 15:   */   
/* 16:   */   ClassClassPath()
/* 17:   */   {
/* 18:66 */     this(Object.class);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public InputStream openClassfile(String classname)
/* 22:   */   {
/* 23:73 */     String jarname = "/" + classname.replace('.', '/') + ".class";
/* 24:74 */     return this.thisClass.getResourceAsStream(jarname);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public URL find(String classname)
/* 28:   */   {
/* 29:83 */     String jarname = "/" + classname.replace('.', '/') + ".class";
/* 30:84 */     return this.thisClass.getResource(jarname);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void close() {}
/* 34:   */   
/* 35:   */   public String toString()
/* 36:   */   {
/* 37:94 */     return this.thisClass.getName() + ".class";
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ClassClassPath
 * JD-Core Version:    0.7.0.1
 */