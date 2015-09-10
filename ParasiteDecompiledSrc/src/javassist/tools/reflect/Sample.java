/*  1:   */ package javassist.tools.reflect;
/*  2:   */ 
/*  3:   */ public class Sample
/*  4:   */ {
/*  5:   */   private Metaobject _metaobject;
/*  6:   */   private static ClassMetaobject _classobject;
/*  7:   */   
/*  8:   */   public Object trap(Object[] args, int identifier)
/*  9:   */     throws Throwable
/* 10:   */   {
/* 11:27 */     Metaobject mobj = this._metaobject;
/* 12:28 */     if (mobj == null) {
/* 13:29 */       return ClassMetaobject.invoke(this, identifier, args);
/* 14:   */     }
/* 15:31 */     return mobj.trapMethodcall(identifier, args);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static Object trapStatic(Object[] args, int identifier)
/* 19:   */     throws Throwable
/* 20:   */   {
/* 21:37 */     return _classobject.trapMethodcall(identifier, args);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static Object trapRead(Object[] args, String name)
/* 25:   */   {
/* 26:41 */     if (args[0] == null) {
/* 27:42 */       return _classobject.trapFieldRead(name);
/* 28:   */     }
/* 29:44 */     return ((Metalevel)args[0])._getMetaobject().trapFieldRead(name);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static Object trapWrite(Object[] args, String name)
/* 33:   */   {
/* 34:48 */     Metalevel base = (Metalevel)args[0];
/* 35:49 */     if (base == null) {
/* 36:50 */       _classobject.trapFieldWrite(name, args[1]);
/* 37:   */     } else {
/* 38:52 */       base._getMetaobject().trapFieldWrite(name, args[1]);
/* 39:   */     }
/* 40:54 */     return null;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.Sample
 * JD-Core Version:    0.7.0.1
 */