/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.cfg.Mappings;
/*  5:   */ import org.hibernate.internal.util.ReflectHelper;
/*  6:   */ import org.hibernate.type.CollectionType;
/*  7:   */ import org.hibernate.type.PrimitiveType;
/*  8:   */ import org.hibernate.type.Type;
/*  9:   */ import org.hibernate.type.TypeFactory;
/* 10:   */ import org.hibernate.type.TypeResolver;
/* 11:   */ 
/* 12:   */ public class Array
/* 13:   */   extends List
/* 14:   */ {
/* 15:   */   private String elementClassName;
/* 16:   */   
/* 17:   */   public Array(Mappings mappings, PersistentClass owner)
/* 18:   */   {
/* 19:42 */     super(mappings, owner);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Class getElementClass()
/* 23:   */     throws MappingException
/* 24:   */   {
/* 25:46 */     if (this.elementClassName == null)
/* 26:   */     {
/* 27:47 */       Type elementType = getElement().getType();
/* 28:48 */       return isPrimitiveArray() ? ((PrimitiveType)elementType).getPrimitiveClass() : elementType.getReturnedClass();
/* 29:   */     }
/* 30:   */     try
/* 31:   */     {
/* 32:54 */       return ReflectHelper.classForName(this.elementClassName);
/* 33:   */     }
/* 34:   */     catch (ClassNotFoundException cnfe)
/* 35:   */     {
/* 36:57 */       throw new MappingException(cnfe);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public CollectionType getDefaultCollectionType()
/* 41:   */     throws MappingException
/* 42:   */   {
/* 43:64 */     return getMappings().getTypeResolver().getTypeFactory().array(getRole(), getReferencedPropertyName(), isEmbedded(), getElementClass());
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean isArray()
/* 47:   */   {
/* 48:71 */     return true;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getElementClassName()
/* 52:   */   {
/* 53:78 */     return this.elementClassName;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setElementClassName(String elementClassName)
/* 57:   */   {
/* 58:84 */     this.elementClassName = elementClassName;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public Object accept(ValueVisitor visitor)
/* 62:   */   {
/* 63:89 */     return visitor.accept(this);
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Array
 * JD-Core Version:    0.7.0.1
 */