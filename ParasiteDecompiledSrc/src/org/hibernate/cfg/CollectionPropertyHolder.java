/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import javax.persistence.JoinTable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.annotations.common.reflection.XClass;
/*   6:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*   7:    */ import org.hibernate.mapping.Collection;
/*   8:    */ import org.hibernate.mapping.Join;
/*   9:    */ import org.hibernate.mapping.KeyValue;
/*  10:    */ import org.hibernate.mapping.PersistentClass;
/*  11:    */ import org.hibernate.mapping.Property;
/*  12:    */ import org.hibernate.mapping.Table;
/*  13:    */ 
/*  14:    */ public class CollectionPropertyHolder
/*  15:    */   extends AbstractPropertyHolder
/*  16:    */ {
/*  17:    */   Collection collection;
/*  18:    */   
/*  19:    */   public CollectionPropertyHolder(Collection collection, String path, XClass clazzToProcess, XProperty property, PropertyHolder parentPropertyHolder, Mappings mappings)
/*  20:    */   {
/*  21: 50 */     super(path, parentPropertyHolder, clazzToProcess, mappings);
/*  22: 51 */     this.collection = collection;
/*  23: 52 */     setCurrentProperty(property);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getClassName()
/*  27:    */   {
/*  28: 56 */     throw new AssertionFailure("Collection property holder does not have a class name");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getEntityOwnerClassName()
/*  32:    */   {
/*  33: 60 */     return null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Table getTable()
/*  37:    */   {
/*  38: 64 */     return this.collection.getCollectionTable();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void addProperty(Property prop, XClass declaringClass)
/*  42:    */   {
/*  43: 68 */     throw new AssertionFailure("Cannot add property to a collection");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public KeyValue getIdentifier()
/*  47:    */   {
/*  48: 72 */     throw new AssertionFailure("Identifier collection not yet managed");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isOrWithinEmbeddedId()
/*  52:    */   {
/*  53: 76 */     return false;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public PersistentClass getPersistentClass()
/*  57:    */   {
/*  58: 80 */     return this.collection.getOwner();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean isComponent()
/*  62:    */   {
/*  63: 84 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isEntity()
/*  67:    */   {
/*  68: 88 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getEntityName()
/*  72:    */   {
/*  73: 92 */     return this.collection.getOwner().getEntityName();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addProperty(Property prop, Ejb3Column[] columns, XClass declaringClass)
/*  77:    */   {
/*  78: 97 */     throw new AssertionFailure("addProperty to a join table of a collection: does it make sense?");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Join addJoin(JoinTable joinTableAnn, boolean noDelayInPkColumnCreation)
/*  82:    */   {
/*  83:101 */     throw new AssertionFailure("Add a <join> in a second pass");
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.CollectionPropertyHolder
 * JD-Core Version:    0.7.0.1
 */