/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.annotations.FetchMode;
/*  6:   */ import org.hibernate.annotations.FetchProfile.FetchOverride;
/*  7:   */ import org.hibernate.mapping.FetchProfile;
/*  8:   */ import org.hibernate.mapping.MetadataSource;
/*  9:   */ import org.hibernate.mapping.PersistentClass;
/* 10:   */ 
/* 11:   */ public class VerifyFetchProfileReferenceSecondPass
/* 12:   */   implements SecondPass
/* 13:   */ {
/* 14:   */   private String fetchProfileName;
/* 15:   */   private FetchProfile.FetchOverride fetch;
/* 16:   */   private Mappings mappings;
/* 17:   */   
/* 18:   */   public VerifyFetchProfileReferenceSecondPass(String fetchProfileName, FetchProfile.FetchOverride fetch, Mappings mappings)
/* 19:   */   {
/* 20:44 */     this.fetchProfileName = fetchProfileName;
/* 21:45 */     this.fetch = fetch;
/* 22:46 */     this.mappings = mappings;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void doSecondPass(Map persistentClasses)
/* 26:   */     throws MappingException
/* 27:   */   {
/* 28:50 */     FetchProfile profile = this.mappings.findOrCreateFetchProfile(this.fetchProfileName, MetadataSource.ANNOTATIONS);
/* 29:54 */     if (MetadataSource.ANNOTATIONS != profile.getSource()) {
/* 30:55 */       return;
/* 31:   */     }
/* 32:58 */     PersistentClass clazz = this.mappings.getClass(this.fetch.entity().getName());
/* 33:   */     
/* 34:60 */     clazz.getProperty(this.fetch.association());
/* 35:   */     
/* 36:62 */     profile.addFetch(this.fetch.entity().getName(), this.fetch.association(), this.fetch.mode().toString().toLowerCase());
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.VerifyFetchProfileReferenceSecondPass
 * JD-Core Version:    0.7.0.1
 */