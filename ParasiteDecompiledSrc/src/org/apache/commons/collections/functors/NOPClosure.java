/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Closure;
/*  5:   */ 
/*  6:   */ public class NOPClosure
/*  7:   */   implements Closure, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 3518477308466486130L;
/* 10:37 */   public static final Closure INSTANCE = new NOPClosure();
/* 11:   */   
/* 12:   */   public static Closure getInstance()
/* 13:   */   {
/* 14:46 */     return INSTANCE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void execute(Object input) {}
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.NOPClosure
 * JD-Core Version:    0.7.0.1
 */