/*  1:   */ package org.apache.commons.lang;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ 
/*  5:   */ public class IncompleteArgumentException
/*  6:   */   extends IllegalArgumentException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 4954193403612068178L;
/*  9:   */   
/* 10:   */   public IncompleteArgumentException(String argName)
/* 11:   */   {
/* 12:63 */     super(argName + " is incomplete.");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public IncompleteArgumentException(String argName, String[] items)
/* 16:   */   {
/* 17:73 */     super(argName + " is missing the following items: " + safeArrayToString(items));
/* 18:   */   }
/* 19:   */   
/* 20:   */   private static final String safeArrayToString(Object[] array)
/* 21:   */   {
/* 22:86 */     return array == null ? null : Arrays.asList(array).toString();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.IncompleteArgumentException
 * JD-Core Version:    0.7.0.1
 */