/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.cfg.Mappings;
/*  6:   */ import org.hibernate.type.MetaType;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ import org.hibernate.type.TypeFactory;
/*  9:   */ import org.hibernate.type.TypeResolver;
/* 10:   */ 
/* 11:   */ public class Any
/* 12:   */   extends SimpleValue
/* 13:   */ {
/* 14:   */   private String identifierTypeName;
/* 15:39 */   private String metaTypeName = "string";
/* 16:   */   private Map metaValues;
/* 17:   */   
/* 18:   */   public Any(Mappings mappings, Table table)
/* 19:   */   {
/* 20:43 */     super(mappings, table);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getIdentifierType()
/* 24:   */   {
/* 25:47 */     return this.identifierTypeName;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setIdentifierType(String identifierType)
/* 29:   */   {
/* 30:51 */     this.identifierTypeName = identifierType;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Type getType()
/* 34:   */     throws MappingException
/* 35:   */   {
/* 36:55 */     Type metaType = getMappings().getTypeResolver().heuristicType(this.metaTypeName);
/* 37:   */     
/* 38:57 */     return getMappings().getTypeResolver().getTypeFactory().any(this.metaValues == null ? metaType : new MetaType(this.metaValues, metaType), getMappings().getTypeResolver().heuristicType(this.identifierTypeName));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setTypeByReflection(String propertyClass, String propertyName) {}
/* 42:   */   
/* 43:   */   public String getMetaType()
/* 44:   */   {
/* 45:66 */     return this.metaTypeName;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void setMetaType(String type)
/* 49:   */   {
/* 50:70 */     this.metaTypeName = type;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Map getMetaValues()
/* 54:   */   {
/* 55:74 */     return this.metaValues;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void setMetaValues(Map metaValues)
/* 59:   */   {
/* 60:78 */     this.metaValues = metaValues;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void setTypeUsingReflection(String className, String propertyName)
/* 64:   */     throws MappingException
/* 65:   */   {}
/* 66:   */   
/* 67:   */   public Object accept(ValueVisitor visitor)
/* 68:   */   {
/* 69:86 */     return visitor.accept(this);
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Any
 * JD-Core Version:    0.7.0.1
 */