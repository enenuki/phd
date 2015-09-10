/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ public class IllegalClassException
/*   4:    */   extends IllegalArgumentException
/*   5:    */ {
/*   6:    */   private static final long serialVersionUID = 8063272569377254819L;
/*   7:    */   
/*   8:    */   public IllegalClassException(Class expected, Object actual)
/*   9:    */   {
/*  10: 62 */     super("Expected: " + safeGetClassName(expected) + ", actual: " + (actual == null ? "null" : actual.getClass().getName()));
/*  11:    */   }
/*  12:    */   
/*  13:    */   public IllegalClassException(Class expected, Class actual)
/*  14:    */   {
/*  15: 76 */     super("Expected: " + safeGetClassName(expected) + ", actual: " + safeGetClassName(actual));
/*  16:    */   }
/*  17:    */   
/*  18:    */   public IllegalClassException(String message)
/*  19:    */   {
/*  20: 89 */     super(message);
/*  21:    */   }
/*  22:    */   
/*  23:    */   private static final String safeGetClassName(Class cls)
/*  24:    */   {
/*  25:100 */     return cls == null ? null : cls.getName();
/*  26:    */   }
/*  27:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.IllegalClassException
 * JD-Core Version:    0.7.0.1
 */