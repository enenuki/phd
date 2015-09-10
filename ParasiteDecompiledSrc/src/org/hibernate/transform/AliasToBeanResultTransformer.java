/*   1:    */ package org.hibernate.transform;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.property.ChainedPropertyAccessor;
/*   6:    */ import org.hibernate.property.PropertyAccessor;
/*   7:    */ import org.hibernate.property.PropertyAccessorFactory;
/*   8:    */ import org.hibernate.property.Setter;
/*   9:    */ 
/*  10:    */ public class AliasToBeanResultTransformer
/*  11:    */   extends AliasedTupleSubsetResultTransformer
/*  12:    */ {
/*  13:    */   private final Class resultClass;
/*  14:    */   private boolean isInitialized;
/*  15:    */   private String[] aliases;
/*  16:    */   private Setter[] setters;
/*  17:    */   
/*  18:    */   public AliasToBeanResultTransformer(Class resultClass)
/*  19:    */   {
/*  20: 66 */     if (resultClass == null) {
/*  21: 67 */       throw new IllegalArgumentException("resultClass cannot be null");
/*  22:    */     }
/*  23: 69 */     this.isInitialized = false;
/*  24: 70 */     this.resultClass = resultClass;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength)
/*  28:    */   {
/*  29: 77 */     return false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Object transformTuple(Object[] tuple, String[] aliases)
/*  33:    */   {
/*  34:    */     Object result;
/*  35:    */     try
/*  36:    */     {
/*  37: 84 */       if (!this.isInitialized) {
/*  38: 85 */         initialize(aliases);
/*  39:    */       } else {
/*  40: 88 */         check(aliases);
/*  41:    */       }
/*  42: 91 */       result = this.resultClass.newInstance();
/*  43: 93 */       for (int i = 0; i < aliases.length; i++) {
/*  44: 94 */         if (this.setters[i] != null) {
/*  45: 95 */           this.setters[i].set(result, tuple[i], null);
/*  46:    */         }
/*  47:    */       }
/*  48:    */     }
/*  49:    */     catch (InstantiationException e)
/*  50:    */     {
/*  51:100 */       throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
/*  52:    */     }
/*  53:    */     catch (IllegalAccessException e)
/*  54:    */     {
/*  55:103 */       throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
/*  56:    */     }
/*  57:106 */     return result;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void initialize(String[] aliases)
/*  61:    */   {
/*  62:110 */     PropertyAccessor propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(this.resultClass, null), PropertyAccessorFactory.getPropertyAccessor("field") });
/*  63:    */     
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:116 */     this.aliases = new String[aliases.length];
/*  69:117 */     this.setters = new Setter[aliases.length];
/*  70:118 */     for (int i = 0; i < aliases.length; i++)
/*  71:    */     {
/*  72:119 */       String alias = aliases[i];
/*  73:120 */       if (alias != null)
/*  74:    */       {
/*  75:121 */         this.aliases[i] = alias;
/*  76:122 */         this.setters[i] = propertyAccessor.getSetter(this.resultClass, alias);
/*  77:    */       }
/*  78:    */     }
/*  79:125 */     this.isInitialized = true;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void check(String[] aliases)
/*  83:    */   {
/*  84:129 */     if (!Arrays.equals(aliases, this.aliases)) {
/*  85:130 */       throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean equals(Object o)
/*  90:    */   {
/*  91:137 */     if (this == o) {
/*  92:138 */       return true;
/*  93:    */     }
/*  94:140 */     if ((o == null) || (getClass() != o.getClass())) {
/*  95:141 */       return false;
/*  96:    */     }
/*  97:144 */     AliasToBeanResultTransformer that = (AliasToBeanResultTransformer)o;
/*  98:146 */     if (!this.resultClass.equals(that.resultClass)) {
/*  99:147 */       return false;
/* 100:    */     }
/* 101:149 */     if (!Arrays.equals(this.aliases, that.aliases)) {
/* 102:150 */       return false;
/* 103:    */     }
/* 104:153 */     return true;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int hashCode()
/* 108:    */   {
/* 109:157 */     int result = this.resultClass.hashCode();
/* 110:158 */     result = 31 * result + (this.aliases != null ? Arrays.hashCode(this.aliases) : 0);
/* 111:159 */     return result;
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.AliasToBeanResultTransformer
 * JD-Core Version:    0.7.0.1
 */