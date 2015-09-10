/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class Undefined
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 9195680630202616767L;
/*  9:50 */   public static final Object instance = new Undefined();
/* 10:   */   
/* 11:   */   public Object readResolve()
/* 12:   */   {
/* 13:58 */     return instance;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Undefined
 * JD-Core Version:    0.7.0.1
 */