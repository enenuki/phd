/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
/*  4:   */ import org.hibernate.type.descriptor.java.MutabilityPlan;
/*  5:   */ 
/*  6:   */ public class AdaptedImmutableType<T>
/*  7:   */   extends AbstractSingleColumnStandardBasicType<T>
/*  8:   */ {
/*  9:   */   private final AbstractStandardBasicType<T> baseMutableType;
/* 10:   */   
/* 11:   */   public AdaptedImmutableType(AbstractStandardBasicType<T> baseMutableType)
/* 12:   */   {
/* 13:39 */     super(baseMutableType.getSqlTypeDescriptor(), baseMutableType.getJavaTypeDescriptor());
/* 14:40 */     this.baseMutableType = baseMutableType;
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected MutabilityPlan<T> getMutabilityPlan()
/* 18:   */   {
/* 19:46 */     return ImmutableMutabilityPlan.INSTANCE;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:50 */     return "imm_" + this.baseMutableType.getName();
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AdaptedImmutableType
 * JD-Core Version:    0.7.0.1
 */