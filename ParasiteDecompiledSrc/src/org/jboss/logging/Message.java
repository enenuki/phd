/*  1:   */ package org.jboss.logging;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.annotation.Documented;
/*  5:   */ import java.lang.annotation.Retention;
/*  6:   */ import java.lang.annotation.RetentionPolicy;
/*  7:   */ import java.lang.annotation.Target;
/*  8:   */ 
/*  9:   */ @Target({java.lang.annotation.ElementType.METHOD})
/* 10:   */ @Retention(RetentionPolicy.CLASS)
/* 11:   */ @Documented
/* 12:   */ public @interface Message
/* 13:   */ {
/* 14:   */   public static final int NONE = 0;
/* 15:   */   public static final int INHERIT = -1;
/* 16:   */   
/* 17:   */   int id() default -1;
/* 18:   */   
/* 19:   */   String value();
/* 20:   */   
/* 21:   */   Format format() default Format.PRINTF;
/* 22:   */   
/* 23:   */   public static enum Format
/* 24:   */   {
/* 25:81 */     PRINTF,  MESSAGE_FORMAT;
/* 26:   */     
/* 27:   */     private Format() {}
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.logging.Message
 * JD-Core Version:    0.7.0.1
 */