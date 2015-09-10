/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ 
/*  11:    */ public class CompositeNestedGeneratedValueGenerator
/*  12:    */   implements IdentifierGenerator, Serializable, IdentifierGeneratorAggregator
/*  13:    */ {
/*  14:    */   private final GenerationContextLocator generationContextLocator;
/*  15:105 */   private List generationPlans = new ArrayList();
/*  16:    */   
/*  17:    */   public CompositeNestedGeneratedValueGenerator(GenerationContextLocator generationContextLocator)
/*  18:    */   {
/*  19:108 */     this.generationContextLocator = generationContextLocator;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void addGeneratedValuePlan(GenerationPlan plan)
/*  23:    */   {
/*  24:112 */     this.generationPlans.add(plan);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Serializable generate(SessionImplementor session, Object object)
/*  28:    */     throws HibernateException
/*  29:    */   {
/*  30:116 */     Serializable context = this.generationContextLocator.locateGenerationContext(session, object);
/*  31:    */     
/*  32:118 */     Iterator itr = this.generationPlans.iterator();
/*  33:119 */     while (itr.hasNext())
/*  34:    */     {
/*  35:120 */       GenerationPlan plan = (GenerationPlan)itr.next();
/*  36:121 */       plan.execute(session, object, context);
/*  37:    */     }
/*  38:124 */     return context;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void registerPersistentGenerators(Map generatorMap)
/*  42:    */   {
/*  43:131 */     Iterator itr = this.generationPlans.iterator();
/*  44:132 */     while (itr.hasNext())
/*  45:    */     {
/*  46:133 */       GenerationPlan plan = (GenerationPlan)itr.next();
/*  47:134 */       plan.registerPersistentGenerators(generatorMap);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static abstract interface GenerationPlan
/*  52:    */   {
/*  53:    */     public abstract void execute(SessionImplementor paramSessionImplementor, Object paramObject1, Object paramObject2);
/*  54:    */     
/*  55:    */     public abstract void registerPersistentGenerators(Map paramMap);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static abstract interface GenerationContextLocator
/*  59:    */   {
/*  60:    */     public abstract Serializable locateGenerationContext(SessionImplementor paramSessionImplementor, Object paramObject);
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.CompositeNestedGeneratedValueGenerator
 * JD-Core Version:    0.7.0.1
 */