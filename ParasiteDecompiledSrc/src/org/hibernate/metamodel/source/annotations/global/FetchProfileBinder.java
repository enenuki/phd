/*  1:   */ package org.hibernate.metamodel.source.annotations.global;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.hibernate.MappingException;
/*  7:   */ import org.hibernate.annotations.FetchMode;
/*  8:   */ import org.hibernate.metamodel.binding.FetchProfile;
/*  9:   */ import org.hibernate.metamodel.binding.FetchProfile.Fetch;
/* 10:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/* 11:   */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/* 12:   */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/* 13:   */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/* 14:   */ import org.jboss.jandex.AnnotationInstance;
/* 15:   */ import org.jboss.jandex.Index;
/* 16:   */ 
/* 17:   */ public class FetchProfileBinder
/* 18:   */ {
/* 19:   */   public static void bind(AnnotationBindingContext bindingContext)
/* 20:   */   {
/* 21:60 */     List<AnnotationInstance> annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.FETCH_PROFILE);
/* 22:62 */     for (AnnotationInstance fetchProfile : annotations) {
/* 23:63 */       bind(bindingContext.getMetadataImplementor(), fetchProfile);
/* 24:   */     }
/* 25:66 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.FETCH_PROFILES);
/* 26:67 */     for (AnnotationInstance fetchProfiles : annotations)
/* 27:   */     {
/* 28:68 */       AnnotationInstance[] fetchProfileAnnotations = (AnnotationInstance[])JandexHelper.getValue(fetchProfiles, "value", [Lorg.jboss.jandex.AnnotationInstance.class);
/* 29:73 */       for (AnnotationInstance fetchProfile : fetchProfileAnnotations) {
/* 30:74 */         bind(bindingContext.getMetadataImplementor(), fetchProfile);
/* 31:   */       }
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   private static void bind(MetadataImplementor metadata, AnnotationInstance fetchProfile)
/* 36:   */   {
/* 37:80 */     String name = (String)JandexHelper.getValue(fetchProfile, "name", String.class);
/* 38:81 */     Set<FetchProfile.Fetch> fetches = new HashSet();
/* 39:82 */     AnnotationInstance[] overrideAnnotations = (AnnotationInstance[])JandexHelper.getValue(fetchProfile, "fetchOverrides", [Lorg.jboss.jandex.AnnotationInstance.class);
/* 40:87 */     for (AnnotationInstance override : overrideAnnotations)
/* 41:   */     {
/* 42:88 */       FetchMode fetchMode = (FetchMode)JandexHelper.getEnumValue(override, "mode", FetchMode.class);
/* 43:89 */       if (!fetchMode.equals(FetchMode.JOIN)) {
/* 44:90 */         throw new MappingException("Only FetchMode.JOIN is currently supported");
/* 45:   */       }
/* 46:92 */       String entityName = (String)JandexHelper.getValue(override, "entity", String.class);
/* 47:93 */       String associationName = (String)JandexHelper.getValue(override, "association", String.class);
/* 48:94 */       fetches.add(new FetchProfile.Fetch(entityName, associationName, fetchMode.toString().toLowerCase()));
/* 49:   */     }
/* 50:96 */     metadata.addFetchProfile(new FetchProfile(name, fetches));
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.global.FetchProfileBinder
 * JD-Core Version:    0.7.0.1
 */