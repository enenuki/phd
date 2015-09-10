/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ final class SerializedLogger
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 508779982439435831L;
/*  9:   */   private final String name;
/* 10:   */   
/* 11:   */   SerializedLogger(String name)
/* 12:   */   {
/* 13:34 */     this.name = name;
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected Object readResolve()
/* 17:   */   {
/* 18:38 */     return Logger.getLogger(this.name);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.SerializedLogger
 * JD-Core Version:    0.7.0.1
 */