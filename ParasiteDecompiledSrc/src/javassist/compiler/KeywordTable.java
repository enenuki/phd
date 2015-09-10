/*  1:   */ package javassist.compiler;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public final class KeywordTable
/*  6:   */   extends HashMap
/*  7:   */ {
/*  8:   */   public int lookup(String name)
/*  9:   */   {
/* 10:22 */     Object found = get(name);
/* 11:23 */     if (found == null) {
/* 12:24 */       return -1;
/* 13:   */     }
/* 14:26 */     return ((Integer)found).intValue();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void append(String name, int t)
/* 18:   */   {
/* 19:30 */     put(name, new Integer(t));
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.KeywordTable
 * JD-Core Version:    0.7.0.1
 */