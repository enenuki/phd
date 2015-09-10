/*   1:    */ package org.hibernate.metamodel.source.annotations.global;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.AnnotationException;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ import org.hibernate.metamodel.binding.TypeDef;
/*  10:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  11:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  12:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  13:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  14:    */ import org.jboss.jandex.AnnotationInstance;
/*  15:    */ import org.jboss.jandex.Index;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class TypeDefBinder
/*  19:    */ {
/*  20: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TypeDefBinder.class.getName());
/*  21:    */   
/*  22:    */   public static void bind(AnnotationBindingContext bindingContext)
/*  23:    */   {
/*  24: 61 */     List<AnnotationInstance> annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.TYPE_DEF);
/*  25: 62 */     for (AnnotationInstance typeDef : annotations) {
/*  26: 63 */       bind(bindingContext.getMetadataImplementor(), typeDef);
/*  27:    */     }
/*  28: 66 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.TYPE_DEFS);
/*  29: 67 */     for (AnnotationInstance typeDefs : annotations)
/*  30:    */     {
/*  31: 68 */       AnnotationInstance[] typeDefAnnotations = (AnnotationInstance[])JandexHelper.getValue(typeDefs, "value", [Lorg.jboss.jandex.AnnotationInstance.class);
/*  32: 73 */       for (AnnotationInstance typeDef : typeDefAnnotations) {
/*  33: 74 */         bind(bindingContext.getMetadataImplementor(), typeDef);
/*  34:    */       }
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static void bind(MetadataImplementor metadata, AnnotationInstance typeDefAnnotation)
/*  39:    */   {
/*  40: 80 */     String name = (String)JandexHelper.getValue(typeDefAnnotation, "name", String.class);
/*  41: 81 */     String defaultForType = (String)JandexHelper.getValue(typeDefAnnotation, "defaultForType", String.class);
/*  42: 82 */     String typeClass = (String)JandexHelper.getValue(typeDefAnnotation, "typeClass", String.class);
/*  43:    */     
/*  44: 84 */     boolean noName = StringHelper.isEmpty(name);
/*  45: 85 */     boolean noDefaultForType = (defaultForType == null) || (defaultForType.equals(Void.TYPE.getName()));
/*  46: 87 */     if ((noName) && (noDefaultForType)) {
/*  47: 88 */       throw new AnnotationException("Either name or defaultForType (or both) attribute should be set in TypeDef having typeClass " + typeClass);
/*  48:    */     }
/*  49: 94 */     Map<String, String> parameterMaps = new HashMap();
/*  50: 95 */     AnnotationInstance[] parameterAnnotations = (AnnotationInstance[])JandexHelper.getValue(typeDefAnnotation, "parameters", [Lorg.jboss.jandex.AnnotationInstance.class);
/*  51:100 */     for (AnnotationInstance parameterAnnotation : parameterAnnotations) {
/*  52:101 */       parameterMaps.put(JandexHelper.getValue(parameterAnnotation, "name", String.class), JandexHelper.getValue(parameterAnnotation, "value", String.class));
/*  53:    */     }
/*  54:107 */     if (!noName) {
/*  55:108 */       bind(name, typeClass, parameterMaps, metadata);
/*  56:    */     }
/*  57:110 */     if (!noDefaultForType) {
/*  58:111 */       bind(defaultForType, typeClass, parameterMaps, metadata);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private static void bind(String name, String typeClass, Map<String, String> prms, MetadataImplementor metadata)
/*  63:    */   {
/*  64:120 */     LOG.debugf("Binding type definition: %s", name);
/*  65:121 */     metadata.addTypeDefinition(new TypeDef(name, typeClass, prms));
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.global.TypeDefBinder
 * JD-Core Version:    0.7.0.1
 */