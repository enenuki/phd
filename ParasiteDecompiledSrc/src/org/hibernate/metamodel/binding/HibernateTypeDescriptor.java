/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class HibernateTypeDescriptor
/*  8:   */ {
/*  9:   */   private String explicitTypeName;
/* 10:   */   private String javaTypeName;
/* 11:   */   private boolean isToOne;
/* 12:40 */   private Map<String, String> typeParameters = new HashMap();
/* 13:   */   private Type resolvedTypeMapping;
/* 14:   */   
/* 15:   */   public String getExplicitTypeName()
/* 16:   */   {
/* 17:45 */     return this.explicitTypeName;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setExplicitTypeName(String explicitTypeName)
/* 21:   */   {
/* 22:49 */     this.explicitTypeName = explicitTypeName;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getJavaTypeName()
/* 26:   */   {
/* 27:53 */     return this.javaTypeName;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setJavaTypeName(String javaTypeName)
/* 31:   */   {
/* 32:57 */     this.javaTypeName = javaTypeName;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean isToOne()
/* 36:   */   {
/* 37:61 */     return this.isToOne;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setToOne(boolean toOne)
/* 41:   */   {
/* 42:65 */     this.isToOne = toOne;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Map<String, String> getTypeParameters()
/* 46:   */   {
/* 47:69 */     return this.typeParameters;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setTypeParameters(Map<String, String> typeParameters)
/* 51:   */   {
/* 52:73 */     this.typeParameters = typeParameters;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public Type getResolvedTypeMapping()
/* 56:   */   {
/* 57:77 */     return this.resolvedTypeMapping;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setResolvedTypeMapping(Type resolvedTypeMapping)
/* 61:   */   {
/* 62:81 */     this.resolvedTypeMapping = resolvedTypeMapping;
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.HibernateTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */