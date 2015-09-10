/*  1:   */ package org.hamcrest;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class StringDescription
/*  6:   */   extends BaseDescription
/*  7:   */ {
/*  8:   */   private final Appendable out;
/*  9:   */   
/* 10:   */   public StringDescription()
/* 11:   */   {
/* 12:12 */     this(new StringBuilder());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public StringDescription(Appendable out)
/* 16:   */   {
/* 17:16 */     this.out = out;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static String toString(SelfDescribing value)
/* 21:   */   {
/* 22:28 */     return new StringDescription().appendDescriptionOf(value).toString();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static String asString(SelfDescribing selfDescribing)
/* 26:   */   {
/* 27:35 */     return toString(selfDescribing);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void append(String str)
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:40 */       this.out.append(str);
/* 35:   */     }
/* 36:   */     catch (IOException e)
/* 37:   */     {
/* 38:42 */       throw new RuntimeException("Could not write description", e);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected void append(char c)
/* 43:   */   {
/* 44:   */     try
/* 45:   */     {
/* 46:48 */       this.out.append(c);
/* 47:   */     }
/* 48:   */     catch (IOException e)
/* 49:   */     {
/* 50:50 */       throw new RuntimeException("Could not write description", e);
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toString()
/* 55:   */   {
/* 56:58 */     return this.out.toString();
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.StringDescription
 * JD-Core Version:    0.7.0.1
 */