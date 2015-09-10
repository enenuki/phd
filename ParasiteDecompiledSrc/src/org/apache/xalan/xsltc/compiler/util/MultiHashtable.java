/*  1:   */ package org.apache.xalan.xsltc.compiler.util;
/*  2:   */ 
/*  3:   */ import java.util.Hashtable;
/*  4:   */ import java.util.Vector;
/*  5:   */ 
/*  6:   */ public final class MultiHashtable
/*  7:   */   extends Hashtable
/*  8:   */ {
/*  9:   */   static final long serialVersionUID = -6151608290510033572L;
/* 10:   */   
/* 11:   */   public Object put(Object key, Object value)
/* 12:   */   {
/* 13:34 */     Vector vector = (Vector)get(key);
/* 14:35 */     if (vector == null) {
/* 15:36 */       super.put(key, vector = new Vector());
/* 16:   */     }
/* 17:37 */     vector.add(value);
/* 18:38 */     return vector;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object maps(Object from, Object to)
/* 22:   */   {
/* 23:42 */     if (from == null) {
/* 24:42 */       return null;
/* 25:   */     }
/* 26:43 */     Vector vector = (Vector)get(from);
/* 27:44 */     if (vector != null)
/* 28:   */     {
/* 29:45 */       int n = vector.size();
/* 30:46 */       for (int i = 0; i < n; i++)
/* 31:   */       {
/* 32:47 */         Object item = vector.elementAt(i);
/* 33:48 */         if (item.equals(to)) {
/* 34:49 */           return item;
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:53 */     return null;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.MultiHashtable
 * JD-Core Version:    0.7.0.1
 */