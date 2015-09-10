/*  1:   */ package org.hibernate.metamodel.source.annotations.global;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.engine.spi.FilterDefinition;
/*  7:   */ import org.hibernate.internal.CoreMessageLogger;
/*  8:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  9:   */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/* 10:   */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/* 11:   */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/* 12:   */ import org.hibernate.type.Type;
/* 13:   */ import org.hibernate.type.TypeResolver;
/* 14:   */ import org.jboss.jandex.AnnotationInstance;
/* 15:   */ import org.jboss.jandex.Index;
/* 16:   */ import org.jboss.logging.Logger;
/* 17:   */ 
/* 18:   */ public class FilterDefBinder
/* 19:   */ {
/* 20:50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FilterDefBinder.class.getName());
/* 21:   */   
/* 22:   */   public static void bind(AnnotationBindingContext bindingContext)
/* 23:   */   {
/* 24:61 */     List<AnnotationInstance> annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.FILTER_DEF);
/* 25:62 */     for (AnnotationInstance filterDef : annotations) {
/* 26:63 */       bind(bindingContext.getMetadataImplementor(), filterDef);
/* 27:   */     }
/* 28:66 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.FILTER_DEFS);
/* 29:67 */     for (AnnotationInstance filterDefs : annotations)
/* 30:   */     {
/* 31:68 */       AnnotationInstance[] filterDefAnnotations = (AnnotationInstance[])JandexHelper.getValue(filterDefs, "value", [Lorg.jboss.jandex.AnnotationInstance.class);
/* 32:73 */       for (AnnotationInstance filterDef : filterDefAnnotations) {
/* 33:74 */         bind(bindingContext.getMetadataImplementor(), filterDef);
/* 34:   */       }
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   private static void bind(MetadataImplementor metadata, AnnotationInstance filterDef)
/* 39:   */   {
/* 40:80 */     String name = (String)JandexHelper.getValue(filterDef, "name", String.class);
/* 41:81 */     Map<String, Type> prms = new HashMap();
/* 42:82 */     for (AnnotationInstance prm : (AnnotationInstance[])JandexHelper.getValue(filterDef, "parameters", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/* 43:83 */       prms.put(JandexHelper.getValue(prm, "name", String.class), metadata.getTypeResolver().heuristicType((String)JandexHelper.getValue(prm, "type", String.class)));
/* 44:   */     }
/* 45:88 */     metadata.addFilterDefinition(new FilterDefinition(name, (String)JandexHelper.getValue(filterDef, "defaultCondition", String.class), prms));
/* 46:   */     
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:95 */     LOG.debugf("Binding filter definition: %s", name);
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.global.FilterDefBinder
 * JD-Core Version:    0.7.0.1
 */