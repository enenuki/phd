/*  1:   */ package org.apache.bcel.verifier.statics;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.Type;
/*  4:   */ 
/*  5:   */ public final class LONG_Upper
/*  6:   */   extends Type
/*  7:   */ {
/*  8:68 */   private static LONG_Upper singleInstance = new LONG_Upper();
/*  9:   */   
/* 10:   */   private LONG_Upper()
/* 11:   */   {
/* 12:72 */     super((byte)15, "Long_Upper");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static LONG_Upper theInstance()
/* 16:   */   {
/* 17:77 */     return singleInstance;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.LONG_Upper
 * JD-Core Version:    0.7.0.1
 */