/*  1:   */ package org.apache.log4j.config;
/*  2:   */ 
/*  3:   */ public class PropertySetterException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = -1352613734254235861L;
/*  7:   */   protected Throwable rootCause;
/*  8:   */   
/*  9:   */   public PropertySetterException(String msg)
/* 10:   */   {
/* 11:33 */     super(msg);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public PropertySetterException(Throwable rootCause)
/* 15:   */   {
/* 16:40 */     this.rootCause = rootCause;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getMessage()
/* 20:   */   {
/* 21:48 */     String msg = super.getMessage();
/* 22:49 */     if ((msg == null) && (this.rootCause != null)) {
/* 23:50 */       msg = this.rootCause.getMessage();
/* 24:   */     }
/* 25:52 */     return msg;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.config.PropertySetterException
 * JD-Core Version:    0.7.0.1
 */