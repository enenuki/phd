/*   1:    */ package org.hibernate.cache.internal;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import org.hibernate.cache.spi.CacheDataDescription;
/*   5:    */ import org.hibernate.mapping.Collection;
/*   6:    */ import org.hibernate.mapping.PersistentClass;
/*   7:    */ import org.hibernate.mapping.Property;
/*   8:    */ import org.hibernate.metamodel.binding.AttributeBindingContainer;
/*   9:    */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*  10:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  11:    */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*  12:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  13:    */ import org.hibernate.metamodel.binding.PluralAttributeBinding;
/*  14:    */ import org.hibernate.type.VersionType;
/*  15:    */ 
/*  16:    */ public class CacheDataDescriptionImpl
/*  17:    */   implements CacheDataDescription
/*  18:    */ {
/*  19:    */   private final boolean mutable;
/*  20:    */   private final boolean versioned;
/*  21:    */   private final Comparator versionComparator;
/*  22:    */   
/*  23:    */   public CacheDataDescriptionImpl(boolean mutable, boolean versioned, Comparator versionComparator)
/*  24:    */   {
/*  25: 44 */     this.mutable = mutable;
/*  26: 45 */     this.versioned = versioned;
/*  27: 46 */     this.versionComparator = versionComparator;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean isMutable()
/*  31:    */   {
/*  32: 50 */     return this.mutable;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isVersioned()
/*  36:    */   {
/*  37: 54 */     return this.versioned;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Comparator getVersionComparator()
/*  41:    */   {
/*  42: 58 */     return this.versionComparator;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static CacheDataDescriptionImpl decode(PersistentClass model)
/*  46:    */   {
/*  47: 62 */     return new CacheDataDescriptionImpl(model.isMutable(), model.isVersioned(), model.isVersioned() ? ((VersionType)model.getVersion().getType()).getComparator() : null);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static CacheDataDescriptionImpl decode(EntityBinding model)
/*  51:    */   {
/*  52: 70 */     return new CacheDataDescriptionImpl(model.isMutable(), model.isVersioned(), getVersionComparator(model));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static CacheDataDescriptionImpl decode(Collection model)
/*  56:    */   {
/*  57: 78 */     return new CacheDataDescriptionImpl(model.isMutable(), model.getOwner().isVersioned(), model.getOwner().isVersioned() ? ((VersionType)model.getOwner().getVersion().getType()).getComparator() : null);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static CacheDataDescriptionImpl decode(PluralAttributeBinding model)
/*  61:    */   {
/*  62: 86 */     return new CacheDataDescriptionImpl(model.isMutable(), model.getContainer().seekEntityBinding().isVersioned(), getVersionComparator(model.getContainer().seekEntityBinding()));
/*  63:    */   }
/*  64:    */   
/*  65:    */   private static Comparator getVersionComparator(EntityBinding model)
/*  66:    */   {
/*  67: 94 */     Comparator versionComparator = null;
/*  68: 95 */     if (model.isVersioned()) {
/*  69: 96 */       versionComparator = ((VersionType)model.getHierarchyDetails().getVersioningAttributeBinding().getHibernateTypeDescriptor().getResolvedTypeMapping()).getComparator();
/*  70:    */     }
/*  71:103 */     return versionComparator;
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.internal.CacheDataDescriptionImpl
 * JD-Core Version:    0.7.0.1
 */