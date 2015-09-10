/*  1:   */ package javax.persistence;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.Set;
/*  7:   */ import javax.persistence.spi.LoadState;
/*  8:   */ import javax.persistence.spi.PersistenceProvider;
/*  9:   */ import javax.persistence.spi.PersistenceProviderResolver;
/* 10:   */ import javax.persistence.spi.PersistenceProviderResolverHolder;
/* 11:   */ import javax.persistence.spi.ProviderUtil;
/* 12:   */ 
/* 13:   */ public class Persistence
/* 14:   */ {
/* 15:   */   @Deprecated
/* 16:   */   public static final String PERSISTENCE_PROVIDER = "javax.persistence.spi.PeristenceProvider";
/* 17:   */   @Deprecated
/* 18:37 */   protected static final Set<PersistenceProvider> providers = new HashSet();
/* 19:   */   
/* 20:   */   public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName)
/* 21:   */   {
/* 22:47 */     return createEntityManagerFactory(persistenceUnitName, null);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties)
/* 26:   */   {
/* 27:60 */     EntityManagerFactory emf = null;
/* 28:61 */     List<PersistenceProvider> providers = getProviders();
/* 29:62 */     for (PersistenceProvider provider : providers)
/* 30:   */     {
/* 31:63 */       emf = provider.createEntityManagerFactory(persistenceUnitName, properties);
/* 32:64 */       if (emf != null) {
/* 33:   */         break;
/* 34:   */       }
/* 35:   */     }
/* 36:68 */     if (emf == null) {
/* 37:69 */       throw new PersistenceException("No Persistence provider for EntityManager named " + persistenceUnitName);
/* 38:   */     }
/* 39:71 */     return emf;
/* 40:   */   }
/* 41:   */   
/* 42:   */   private static List<PersistenceProvider> getProviders()
/* 43:   */   {
/* 44:75 */     return PersistenceProviderResolverHolder.getPersistenceProviderResolver().getPersistenceProviders();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static PersistenceUtil getPersistenceUtil()
/* 48:   */   {
/* 49:84 */     return util;
/* 50:   */   }
/* 51:   */   
/* 52:87 */   private static PersistenceUtil util = new PersistenceUtil()
/* 53:   */   {
/* 54:   */     public boolean isLoaded(Object entity, String attributeName)
/* 55:   */     {
/* 56:91 */       List<PersistenceProvider> providers = Persistence.access$000();
/* 57:92 */       for (PersistenceProvider provider : providers)
/* 58:   */       {
/* 59:93 */         LoadState state = provider.getProviderUtil().isLoadedWithoutReference(entity, attributeName);
/* 60:94 */         if (state != LoadState.UNKNOWN) {
/* 61:95 */           return state == LoadState.LOADED;
/* 62:   */         }
/* 63:   */       }
/* 64:97 */       for (PersistenceProvider provider : providers)
/* 65:   */       {
/* 66:98 */         LoadState state = provider.getProviderUtil().isLoadedWithReference(entity, attributeName);
/* 67:99 */         if (state != LoadState.UNKNOWN) {
/* 68::0 */           return state == LoadState.LOADED;
/* 69:   */         }
/* 70:   */       }
/* 71::2 */       return true;
/* 72:   */     }
/* 73:   */     
/* 74:   */     public boolean isLoaded(Object object)
/* 75:   */     {
/* 76::6 */       List<PersistenceProvider> providers = Persistence.access$000();
/* 77::7 */       for (PersistenceProvider provider : providers)
/* 78:   */       {
/* 79::8 */         LoadState state = provider.getProviderUtil().isLoaded(object);
/* 80::9 */         if (state != LoadState.UNKNOWN) {
/* 81:;0 */           return state == LoadState.LOADED;
/* 82:   */         }
/* 83:   */       }
/* 84:;2 */       return true;
/* 85:   */     }
/* 86:   */   };
/* 87:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.Persistence
 * JD-Core Version:    0.7.0.1
 */