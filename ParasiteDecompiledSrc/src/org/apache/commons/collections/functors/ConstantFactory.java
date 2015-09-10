/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Factory;
/*  5:   */ 
/*  6:   */ public class ConstantFactory
/*  7:   */   implements Factory, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -3520677225766901240L;
/* 10:41 */   public static final Factory NULL_INSTANCE = new ConstantFactory(null);
/* 11:   */   private final Object iConstant;
/* 12:   */   
/* 13:   */   public static Factory getInstance(Object constantToReturn)
/* 14:   */   {
/* 15:53 */     if (constantToReturn == null) {
/* 16:54 */       return NULL_INSTANCE;
/* 17:   */     }
/* 18:56 */     return new ConstantFactory(constantToReturn);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ConstantFactory(Object constantToReturn)
/* 22:   */   {
/* 23:67 */     this.iConstant = constantToReturn;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object create()
/* 27:   */   {
/* 28:76 */     return this.iConstant;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object getConstant()
/* 32:   */   {
/* 33:86 */     return this.iConstant;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ConstantFactory
 * JD-Core Version:    0.7.0.1
 */