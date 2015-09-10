/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  4:   */ 
/*  5:   */ public class AnsiTrimFunction
/*  6:   */   extends TrimFunctionTemplate
/*  7:   */ {
/*  8:   */   protected String render(TrimFunctionTemplate.Options options, String trimSource, SessionFactoryImplementor factory)
/*  9:   */   {
/* 10:34 */     return "trim(" + options.getTrimSpecification().getName() + ' ' + options.getTrimCharacter() + " from " + trimSource + ')';
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.AnsiTrimFunction
 * JD-Core Version:    0.7.0.1
 */