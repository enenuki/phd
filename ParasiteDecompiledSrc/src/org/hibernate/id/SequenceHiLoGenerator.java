/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ import org.hibernate.MappingException;
/*  6:   */ import org.hibernate.dialect.Dialect;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.id.enhanced.AccessCallback;
/*  9:   */ import org.hibernate.id.enhanced.OptimizerFactory.LegacyHiLoAlgorithmOptimizer;
/* 10:   */ import org.hibernate.internal.util.config.ConfigurationHelper;
/* 11:   */ import org.hibernate.type.Type;
/* 12:   */ 
/* 13:   */ public class SequenceHiLoGenerator
/* 14:   */   extends SequenceGenerator
/* 15:   */ {
/* 16:   */   public static final String MAX_LO = "max_lo";
/* 17:   */   private int maxLo;
/* 18:   */   private OptimizerFactory.LegacyHiLoAlgorithmOptimizer hiloOptimizer;
/* 19:   */   
/* 20:   */   public void configure(Type type, Properties params, Dialect d)
/* 21:   */     throws MappingException
/* 22:   */   {
/* 23:59 */     super.configure(type, params, d);
/* 24:   */     
/* 25:61 */     this.maxLo = ConfigurationHelper.getInt("max_lo", params, 9);
/* 26:63 */     if (this.maxLo >= 1) {
/* 27:64 */       this.hiloOptimizer = new OptimizerFactory.LegacyHiLoAlgorithmOptimizer(getIdentifierType().getReturnedClass(), this.maxLo);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public synchronized Serializable generate(final SessionImplementor session, Object obj)
/* 32:   */   {
/* 33:73 */     if (this.maxLo < 1)
/* 34:   */     {
/* 35:75 */       IntegralDataTypeHolder value = null;
/* 36:76 */       while ((value == null) || (value.lt(0L))) {
/* 37:77 */         value = super.generateHolder(session);
/* 38:   */       }
/* 39:79 */       return value.makeValue();
/* 40:   */     }
/* 41:82 */     this.hiloOptimizer.generate(new AccessCallback()
/* 42:   */     {
/* 43:   */       public IntegralDataTypeHolder getNextValue()
/* 44:   */       {
/* 45:85 */         return SequenceHiLoGenerator.this.generateHolder(session);
/* 46:   */       }
/* 47:   */     });
/* 48:   */   }
/* 49:   */   
/* 50:   */   OptimizerFactory.LegacyHiLoAlgorithmOptimizer getHiloOptimizer()
/* 51:   */   {
/* 52:97 */     return this.hiloOptimizer;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.SequenceHiLoGenerator
 * JD-Core Version:    0.7.0.1
 */