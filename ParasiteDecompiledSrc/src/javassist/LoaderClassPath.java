/*  1:   */ package javassist;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import java.lang.ref.WeakReference;
/*  5:   */ import java.net.URL;
/*  6:   */ 
/*  7:   */ public class LoaderClassPath
/*  8:   */   implements ClassPath
/*  9:   */ {
/* 10:   */   private WeakReference clref;
/* 11:   */   
/* 12:   */   public LoaderClassPath(ClassLoader cl)
/* 13:   */   {
/* 14:48 */     this.clref = new WeakReference(cl);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String toString()
/* 18:   */   {
/* 19:52 */     Object cl = null;
/* 20:53 */     if (this.clref != null) {
/* 21:54 */       cl = this.clref.get();
/* 22:   */     }
/* 23:56 */     return cl == null ? "<null>" : cl.toString();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public InputStream openClassfile(String classname)
/* 27:   */   {
/* 28:65 */     String cname = classname.replace('.', '/') + ".class";
/* 29:66 */     ClassLoader cl = (ClassLoader)this.clref.get();
/* 30:67 */     if (cl == null) {
/* 31:68 */       return null;
/* 32:   */     }
/* 33:70 */     return cl.getResourceAsStream(cname);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public URL find(String classname)
/* 37:   */   {
/* 38:81 */     String cname = classname.replace('.', '/') + ".class";
/* 39:82 */     ClassLoader cl = (ClassLoader)this.clref.get();
/* 40:83 */     if (cl == null) {
/* 41:84 */       return null;
/* 42:   */     }
/* 43:86 */     return cl.getResource(cname);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void close()
/* 47:   */   {
/* 48:93 */     this.clref = null;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.LoaderClassPath
 * JD-Core Version:    0.7.0.1
 */