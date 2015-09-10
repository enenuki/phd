/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import org.hibernate.EntityMode;
/*   4:    */ import org.hibernate.engine.OptimisticLockStyle;
/*   5:    */ 
/*   6:    */ public class HierarchyDetails
/*   7:    */ {
/*   8:    */   private final EntityBinding rootEntityBinding;
/*   9:    */   private final InheritanceType inheritanceType;
/*  10:    */   private final EntityMode entityMode;
/*  11:    */   private final EntityIdentifier entityIdentifier;
/*  12:    */   private EntityDiscriminator entityDiscriminator;
/*  13:    */   private OptimisticLockStyle optimisticLockStyle;
/*  14:    */   private BasicAttributeBinding versioningAttributeBinding;
/*  15:    */   private Caching caching;
/*  16:    */   private boolean explicitPolymorphism;
/*  17:    */   
/*  18:    */   public HierarchyDetails(EntityBinding rootEntityBinding, InheritanceType inheritanceType, EntityMode entityMode)
/*  19:    */   {
/*  20: 49 */     this.rootEntityBinding = rootEntityBinding;
/*  21: 50 */     this.inheritanceType = inheritanceType;
/*  22: 51 */     this.entityMode = entityMode;
/*  23: 52 */     this.entityIdentifier = new EntityIdentifier(rootEntityBinding);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public EntityBinding getRootEntityBinding()
/*  27:    */   {
/*  28: 56 */     return this.rootEntityBinding;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public InheritanceType getInheritanceType()
/*  32:    */   {
/*  33: 60 */     return this.inheritanceType;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public EntityMode getEntityMode()
/*  37:    */   {
/*  38: 64 */     return this.entityMode;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public EntityIdentifier getEntityIdentifier()
/*  42:    */   {
/*  43: 68 */     return this.entityIdentifier;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public EntityDiscriminator getEntityDiscriminator()
/*  47:    */   {
/*  48: 72 */     return this.entityDiscriminator;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public OptimisticLockStyle getOptimisticLockStyle()
/*  52:    */   {
/*  53: 76 */     return this.optimisticLockStyle;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setOptimisticLockStyle(OptimisticLockStyle optimisticLockStyle)
/*  57:    */   {
/*  58: 80 */     this.optimisticLockStyle = optimisticLockStyle;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setEntityDiscriminator(EntityDiscriminator entityDiscriminator)
/*  62:    */   {
/*  63: 84 */     this.entityDiscriminator = entityDiscriminator;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public BasicAttributeBinding getVersioningAttributeBinding()
/*  67:    */   {
/*  68: 88 */     return this.versioningAttributeBinding;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setVersioningAttributeBinding(BasicAttributeBinding versioningAttributeBinding)
/*  72:    */   {
/*  73: 92 */     this.versioningAttributeBinding = versioningAttributeBinding;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Caching getCaching()
/*  77:    */   {
/*  78: 96 */     return this.caching;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCaching(Caching caching)
/*  82:    */   {
/*  83:100 */     this.caching = caching;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isExplicitPolymorphism()
/*  87:    */   {
/*  88:104 */     return this.explicitPolymorphism;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setExplicitPolymorphism(boolean explicitPolymorphism)
/*  92:    */   {
/*  93:108 */     this.explicitPolymorphism = explicitPolymorphism;
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.HierarchyDetails
 * JD-Core Version:    0.7.0.1
 */