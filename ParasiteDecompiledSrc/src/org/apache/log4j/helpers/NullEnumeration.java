/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.NoSuchElementException;
/*  5:   */ 
/*  6:   */ public class NullEnumeration
/*  7:   */   implements Enumeration
/*  8:   */ {
/*  9:31 */   private static final NullEnumeration instance = new NullEnumeration();
/* 10:   */   
/* 11:   */   public static NullEnumeration getInstance()
/* 12:   */   {
/* 13:38 */     return instance;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean hasMoreElements()
/* 17:   */   {
/* 18:43 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object nextElement()
/* 22:   */   {
/* 23:48 */     throw new NoSuchElementException();
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.NullEnumeration
 * JD-Core Version:    0.7.0.1
 */