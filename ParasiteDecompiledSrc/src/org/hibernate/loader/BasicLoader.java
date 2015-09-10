/*   1:    */ package org.hibernate.loader;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   6:    */ import org.hibernate.persister.collection.CollectionPersister;
/*   7:    */ import org.hibernate.persister.entity.Loadable;
/*   8:    */ import org.hibernate.type.BagType;
/*   9:    */ 
/*  10:    */ public abstract class BasicLoader
/*  11:    */   extends Loader
/*  12:    */ {
/*  13: 43 */   protected static final String[] NO_SUFFIX = { "" };
/*  14:    */   private EntityAliases[] descriptors;
/*  15:    */   private CollectionAliases[] collectionDescriptors;
/*  16:    */   
/*  17:    */   public BasicLoader(SessionFactoryImplementor factory)
/*  18:    */   {
/*  19: 49 */     super(factory);
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected final EntityAliases[] getEntityAliases()
/*  23:    */   {
/*  24: 53 */     return this.descriptors;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected final CollectionAliases[] getCollectionAliases()
/*  28:    */   {
/*  29: 57 */     return this.collectionDescriptors;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected abstract String[] getSuffixes();
/*  33:    */   
/*  34:    */   protected abstract String[] getCollectionSuffixes();
/*  35:    */   
/*  36:    */   protected void postInstantiate()
/*  37:    */   {
/*  38: 64 */     Loadable[] persisters = getEntityPersisters();
/*  39: 65 */     String[] suffixes = getSuffixes();
/*  40: 66 */     this.descriptors = new EntityAliases[persisters.length];
/*  41: 67 */     for (int i = 0; i < this.descriptors.length; i++) {
/*  42: 68 */       this.descriptors[i] = new DefaultEntityAliases(persisters[i], suffixes[i]);
/*  43:    */     }
/*  44: 71 */     CollectionPersister[] collectionPersisters = getCollectionPersisters();
/*  45: 72 */     List bagRoles = null;
/*  46: 73 */     if (collectionPersisters != null)
/*  47:    */     {
/*  48: 74 */       String[] collectionSuffixes = getCollectionSuffixes();
/*  49: 75 */       this.collectionDescriptors = new CollectionAliases[collectionPersisters.length];
/*  50: 76 */       for (int i = 0; i < collectionPersisters.length; i++)
/*  51:    */       {
/*  52: 77 */         if (isBag(collectionPersisters[i]))
/*  53:    */         {
/*  54: 78 */           if (bagRoles == null) {
/*  55: 79 */             bagRoles = new ArrayList();
/*  56:    */           }
/*  57: 81 */           bagRoles.add(collectionPersisters[i].getRole());
/*  58:    */         }
/*  59: 83 */         this.collectionDescriptors[i] = new GeneratedCollectionAliases(collectionPersisters[i], collectionSuffixes[i]);
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 90 */       this.collectionDescriptors = null;
/*  65:    */     }
/*  66: 92 */     if ((bagRoles != null) && (bagRoles.size() > 1)) {
/*  67: 93 */       throw new MultipleBagFetchException(bagRoles);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   private boolean isBag(CollectionPersister collectionPersister)
/*  72:    */   {
/*  73: 98 */     return collectionPersister.getCollectionType().getClass().isAssignableFrom(BagType.class);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String[] generateSuffixes(int length)
/*  77:    */   {
/*  78:111 */     return generateSuffixes(0, length);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static String[] generateSuffixes(int seed, int length)
/*  82:    */   {
/*  83:115 */     if (length == 0) {
/*  84:115 */       return NO_SUFFIX;
/*  85:    */     }
/*  86:117 */     String[] suffixes = new String[length];
/*  87:118 */     for (int i = 0; i < length; i++) {
/*  88:119 */       suffixes[i] = (Integer.toString(i + seed) + "_");
/*  89:    */     }
/*  90:121 */     return suffixes;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.BasicLoader
 * JD-Core Version:    0.7.0.1
 */