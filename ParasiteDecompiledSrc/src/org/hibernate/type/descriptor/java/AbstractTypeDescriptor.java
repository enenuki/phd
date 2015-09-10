/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.internal.util.compare.ComparableComparator;
/*   7:    */ import org.hibernate.internal.util.compare.EqualsHelper;
/*   8:    */ 
/*   9:    */ public abstract class AbstractTypeDescriptor<T>
/*  10:    */   implements JavaTypeDescriptor<T>, Serializable
/*  11:    */ {
/*  12:    */   private final Class<T> type;
/*  13:    */   private final MutabilityPlan<T> mutabilityPlan;
/*  14:    */   private final Comparator<T> comparator;
/*  15:    */   
/*  16:    */   protected AbstractTypeDescriptor(Class<T> type)
/*  17:    */   {
/*  18: 52 */     this(type, ImmutableMutabilityPlan.INSTANCE);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected AbstractTypeDescriptor(Class<T> type, MutabilityPlan<T> mutabilityPlan)
/*  22:    */   {
/*  23: 63 */     this.type = type;
/*  24: 64 */     this.mutabilityPlan = mutabilityPlan;
/*  25: 65 */     this.comparator = (Comparable.class.isAssignableFrom(type) ? ComparableComparator.INSTANCE : null);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public MutabilityPlan<T> getMutabilityPlan()
/*  29:    */   {
/*  30: 74 */     return this.mutabilityPlan;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Class<T> getJavaTypeClass()
/*  34:    */   {
/*  35: 81 */     return this.type;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int extractHashCode(T value)
/*  39:    */   {
/*  40: 88 */     return value.hashCode();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean areEqual(T one, T another)
/*  44:    */   {
/*  45: 95 */     return EqualsHelper.equals(one, another);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Comparator<T> getComparator()
/*  49:    */   {
/*  50:102 */     return this.comparator;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String extractLoggableRepresentation(T value)
/*  54:    */   {
/*  55:109 */     return value == null ? "null" : value.toString();
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected HibernateException unknownUnwrap(Class conversionType)
/*  59:    */   {
/*  60:113 */     throw new HibernateException("Unknown unwrap conversion requested: " + this.type.getName() + " to " + conversionType.getName());
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected HibernateException unknownWrap(Class conversionType)
/*  64:    */   {
/*  65:119 */     throw new HibernateException("Unknown wrap conversion requested: " + conversionType.getName() + " to " + this.type.getName());
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.AbstractTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */