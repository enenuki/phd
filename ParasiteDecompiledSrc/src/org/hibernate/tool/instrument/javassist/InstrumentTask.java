/*  1:   */ package org.hibernate.tool.instrument.javassist;
/*  2:   */ 
/*  3:   */ import org.hibernate.bytecode.buildtime.internal.JavassistInstrumenter;
/*  4:   */ import org.hibernate.bytecode.buildtime.spi.Instrumenter;
/*  5:   */ import org.hibernate.bytecode.buildtime.spi.Instrumenter.Options;
/*  6:   */ import org.hibernate.bytecode.buildtime.spi.Logger;
/*  7:   */ import org.hibernate.tool.instrument.BasicInstrumentationTask;
/*  8:   */ 
/*  9:   */ public class InstrumentTask
/* 10:   */   extends BasicInstrumentationTask
/* 11:   */ {
/* 12:   */   protected Instrumenter buildInstrumenter(Logger logger, Instrumenter.Options options)
/* 13:   */   {
/* 14:69 */     return new JavassistInstrumenter(logger, options);
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.instrument.javassist.InstrumentTask
 * JD-Core Version:    0.7.0.1
 */