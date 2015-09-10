/*  1:   */ package org.apache.commons.lang;
/*  2:   */ 
/*  3:   */ public class NullArgumentException
/*  4:   */   extends IllegalArgumentException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1174360235354917591L;
/*  7:   */   
/*  8:   */   public NullArgumentException(String argName)
/*  9:   */   {
/* 10:61 */     super((argName == null ? "Argument" : argName) + " must not be null.");
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.NullArgumentException
 * JD-Core Version:    0.7.0.1
 */