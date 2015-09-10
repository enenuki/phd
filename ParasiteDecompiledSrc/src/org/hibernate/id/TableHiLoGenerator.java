/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.id.enhanced.AccessCallback;
/*  8:   */ import org.hibernate.id.enhanced.OptimizerFactory.LegacyHiLoAlgorithmOptimizer;
/*  9:   */ import org.hibernate.internal.util.config.ConfigurationHelper;
/* 10:   */ import org.hibernate.type.Type;
/* 11:   */ 
/* 12:   */ public class TableHiLoGenerator
/* 13:   */   extends TableGenerator
/* 14:   */ {
/* 15:   */   public static final String MAX_LO = "max_lo";
/* 16:   */   private OptimizerFactory.LegacyHiLoAlgorithmOptimizer hiloOptimizer;
/* 17:   */   private int maxLo;
/* 18:   */   
/* 19:   */   public void configure(Type type, Properties params, Dialect d)
/* 20:   */   {
/* 21:62 */     super.configure(type, params, d);
/* 22:63 */     this.maxLo = ConfigurationHelper.getInt("max_lo", params, 32767);
/* 23:65 */     if (this.maxLo >= 1) {
/* 24:66 */       this.hiloOptimizer = new OptimizerFactory.LegacyHiLoAlgorithmOptimizer(type.getReturnedClass(), this.maxLo);
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public synchronized Serializable generate(final SessionImplementor session, Object obj)
/* 29:   */   {
/* 30:72 */     if (this.maxLo < 1)
/* 31:   */     {
/* 32:74 */       IntegralDataTypeHolder value = null;
/* 33:75 */       while ((value == null) || (value.lt(0L))) {
/* 34:76 */         value = generateHolder(session);
/* 35:   */       }
/* 36:78 */       return value.makeValue();
/* 37:   */     }
/* 38:81 */     this.hiloOptimizer.generate(new AccessCallback()
/* 39:   */     {
/* 40:   */       public IntegralDataTypeHolder getNextValue()
/* 41:   */       {
/* 42:84 */         return TableHiLoGenerator.this.generateHolder(session);
/* 43:   */       }
/* 44:   */     });
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.TableHiLoGenerator
 * JD-Core Version:    0.7.0.1
 */