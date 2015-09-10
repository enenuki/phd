/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.cfg.Mappings;
/*   9:    */ import org.hibernate.type.EntityType;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ import org.hibernate.type.TypeFactory;
/*  12:    */ import org.hibernate.type.TypeResolver;
/*  13:    */ 
/*  14:    */ public class ManyToOne
/*  15:    */   extends ToOne
/*  16:    */ {
/*  17:    */   private boolean ignoreNotFound;
/*  18:    */   private boolean isLogicalOneToOne;
/*  19:    */   
/*  20:    */   public ManyToOne(Mappings mappings, Table table)
/*  21:    */   {
/*  22: 43 */     super(mappings, table);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Type getType()
/*  26:    */     throws MappingException
/*  27:    */   {
/*  28: 47 */     return getMappings().getTypeResolver().getTypeFactory().manyToOne(getReferencedEntityName(), getReferencedPropertyName(), isLazy(), isUnwrapProxy(), isEmbedded(), isIgnoreNotFound(), this.isLogicalOneToOne);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void createForeignKey()
/*  32:    */     throws MappingException
/*  33:    */   {
/*  34: 60 */     if ((this.referencedPropertyName == null) && (!hasFormula())) {
/*  35: 61 */       createForeignKeyOfEntity(((EntityType)getType()).getAssociatedEntityName());
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void createPropertyRefConstraints(Map persistentClasses)
/*  40:    */   {
/*  41: 66 */     if (this.referencedPropertyName != null)
/*  42:    */     {
/*  43: 67 */       PersistentClass pc = (PersistentClass)persistentClasses.get(getReferencedEntityName());
/*  44:    */       
/*  45: 69 */       Property property = pc.getReferencedProperty(getReferencedPropertyName());
/*  46: 71 */       if (property == null) {
/*  47: 72 */         throw new MappingException("Could not find property " + getReferencedPropertyName() + " on " + getReferencedEntityName());
/*  48:    */       }
/*  49: 80 */       if ((!hasFormula()) && (!"none".equals(getForeignKeyName())))
/*  50:    */       {
/*  51: 81 */         List refColumns = new ArrayList();
/*  52: 82 */         Iterator iter = property.getColumnIterator();
/*  53: 83 */         while (iter.hasNext())
/*  54:    */         {
/*  55: 84 */           Column col = (Column)iter.next();
/*  56: 85 */           refColumns.add(col);
/*  57:    */         }
/*  58: 88 */         ForeignKey fk = getTable().createForeignKey(getForeignKeyName(), getConstraintColumns(), ((EntityType)getType()).getAssociatedEntityName(), refColumns);
/*  59:    */         
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64: 94 */         fk.setCascadeDeleteEnabled(isCascadeDeleteEnabled());
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object accept(ValueVisitor visitor)
/*  70:    */   {
/*  71:101 */     return visitor.accept(this);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isIgnoreNotFound()
/*  75:    */   {
/*  76:105 */     return this.ignoreNotFound;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIgnoreNotFound(boolean ignoreNotFound)
/*  80:    */   {
/*  81:109 */     this.ignoreNotFound = ignoreNotFound;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void markAsLogicalOneToOne()
/*  85:    */   {
/*  86:113 */     this.isLogicalOneToOne = true;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isLogicalOneToOne()
/*  90:    */   {
/*  91:117 */     return this.isLogicalOneToOne;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.ManyToOne
 * JD-Core Version:    0.7.0.1
 */