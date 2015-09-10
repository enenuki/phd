/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public abstract class Ref
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   public boolean has(Context cx)
/*  9:   */   {
/* 10:51 */     return true;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public abstract Object get(Context paramContext);
/* 14:   */   
/* 15:   */   public abstract Object set(Context paramContext, Object paramObject);
/* 16:   */   
/* 17:   */   public boolean delete(Context cx)
/* 18:   */   {
/* 19:60 */     return false;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Ref
 * JD-Core Version:    0.7.0.1
 */