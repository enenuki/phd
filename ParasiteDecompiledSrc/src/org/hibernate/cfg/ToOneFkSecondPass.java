/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.AnnotationException;
/*   6:    */ import org.hibernate.AssertionFailure;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.cfg.annotations.TableBinder;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.mapping.Component;
/*  11:    */ import org.hibernate.mapping.KeyValue;
/*  12:    */ import org.hibernate.mapping.ManyToOne;
/*  13:    */ import org.hibernate.mapping.OneToOne;
/*  14:    */ import org.hibernate.mapping.PersistentClass;
/*  15:    */ import org.hibernate.mapping.Property;
/*  16:    */ import org.hibernate.mapping.ToOne;
/*  17:    */ 
/*  18:    */ public class ToOneFkSecondPass
/*  19:    */   extends FkSecondPass
/*  20:    */ {
/*  21:    */   private boolean unique;
/*  22:    */   private Mappings mappings;
/*  23:    */   private String path;
/*  24:    */   private String entityClassName;
/*  25:    */   
/*  26:    */   public ToOneFkSecondPass(ToOne value, Ejb3JoinColumn[] columns, boolean unique, String entityClassName, String path, Mappings mappings)
/*  27:    */   {
/*  28: 61 */     super(value, columns);
/*  29: 62 */     this.mappings = mappings;
/*  30: 63 */     this.unique = unique;
/*  31: 64 */     this.entityClassName = entityClassName;
/*  32: 65 */     this.path = (entityClassName != null ? path.substring(entityClassName.length() + 1) : path);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getReferencedEntityName()
/*  36:    */   {
/*  37: 70 */     return ((ToOne)this.value).getReferencedEntityName();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isInPrimaryKey()
/*  41:    */   {
/*  42: 75 */     if (this.entityClassName == null) {
/*  43: 75 */       return false;
/*  44:    */     }
/*  45: 76 */     PersistentClass persistentClass = this.mappings.getClass(this.entityClassName);
/*  46: 77 */     Property property = persistentClass.getIdentifierProperty();
/*  47: 78 */     if (this.path == null) {
/*  48: 79 */       return false;
/*  49:    */     }
/*  50: 81 */     if (property != null) {
/*  51: 83 */       return this.path.startsWith(property.getName() + ".");
/*  52:    */     }
/*  53: 88 */     if (this.path.startsWith("id."))
/*  54:    */     {
/*  55: 89 */       KeyValue valueIdentifier = persistentClass.getIdentifier();
/*  56: 90 */       String localPath = this.path.substring(3);
/*  57: 91 */       if ((valueIdentifier instanceof Component))
/*  58:    */       {
/*  59: 92 */         Iterator it = ((Component)valueIdentifier).getPropertyIterator();
/*  60: 93 */         while (it.hasNext())
/*  61:    */         {
/*  62: 94 */           Property idProperty = (Property)it.next();
/*  63: 95 */           if (localPath.startsWith(idProperty.getName())) {
/*  64: 95 */             return true;
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:101 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void doSecondPass(Map persistentClasses)
/*  73:    */     throws MappingException
/*  74:    */   {
/*  75:105 */     if ((this.value instanceof ManyToOne))
/*  76:    */     {
/*  77:106 */       ManyToOne manyToOne = (ManyToOne)this.value;
/*  78:107 */       PersistentClass ref = (PersistentClass)persistentClasses.get(manyToOne.getReferencedEntityName());
/*  79:108 */       if (ref == null) {
/*  80:109 */         throw new AnnotationException("@OneToOne or @ManyToOne on " + StringHelper.qualify(this.entityClassName, this.path) + " references an unknown entity: " + manyToOne.getReferencedEntityName());
/*  81:    */       }
/*  82:116 */       BinderHelper.createSyntheticPropertyReference(this.columns, ref, null, manyToOne, false, this.mappings);
/*  83:117 */       TableBinder.bindFk(ref, null, this.columns, manyToOne, this.unique, this.mappings);
/*  84:121 */       if (!manyToOne.isIgnoreNotFound()) {
/*  85:121 */         manyToOne.createPropertyRefConstraints(persistentClasses);
/*  86:    */       }
/*  87:    */     }
/*  88:123 */     else if ((this.value instanceof OneToOne))
/*  89:    */     {
/*  90:124 */       ((OneToOne)this.value).createForeignKey();
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:127 */       throw new AssertionFailure("FkSecondPass for a wrong value type: " + this.value.getClass().getName());
/*  95:    */     }
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ToOneFkSecondPass
 * JD-Core Version:    0.7.0.1
 */