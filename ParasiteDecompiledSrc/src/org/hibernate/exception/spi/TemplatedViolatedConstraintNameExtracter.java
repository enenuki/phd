/*  1:   */ package org.hibernate.exception.spi;
/*  2:   */ 
/*  3:   */ public abstract class TemplatedViolatedConstraintNameExtracter
/*  4:   */   implements ViolatedConstraintNameExtracter
/*  5:   */ {
/*  6:   */   protected String extractUsingTemplate(String templateStart, String templateEnd, String message)
/*  7:   */   {
/*  8:43 */     int templateStartPosition = message.indexOf(templateStart);
/*  9:44 */     if (templateStartPosition < 0) {
/* 10:45 */       return null;
/* 11:   */     }
/* 12:48 */     int start = templateStartPosition + templateStart.length();
/* 13:49 */     int end = message.indexOf(templateEnd, start);
/* 14:50 */     if (end < 0) {
/* 15:51 */       end = message.length();
/* 16:   */     }
/* 17:54 */     return message.substring(start, end);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter
 * JD-Core Version:    0.7.0.1
 */