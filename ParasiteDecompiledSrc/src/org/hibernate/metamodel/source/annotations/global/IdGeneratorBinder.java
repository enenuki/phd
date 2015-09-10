/*   1:    */ package org.hibernate.metamodel.source.annotations.global;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ import org.hibernate.metamodel.Metadata.Options;
/*  10:    */ import org.hibernate.metamodel.binding.IdGenerator;
/*  11:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  12:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  13:    */ import org.hibernate.metamodel.source.annotations.EnumConversionHelper;
/*  14:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  15:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  16:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  17:    */ import org.jboss.jandex.AnnotationInstance;
/*  18:    */ import org.jboss.jandex.Index;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class IdGeneratorBinder
/*  22:    */ {
/*  23: 62 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IdGeneratorBinder.class.getName());
/*  24:    */   
/*  25:    */   public static void bind(AnnotationBindingContext bindingContext)
/*  26:    */   {
/*  27: 77 */     List<AnnotationInstance> annotations = bindingContext.getIndex().getAnnotations(JPADotNames.SEQUENCE_GENERATOR);
/*  28: 79 */     for (AnnotationInstance generator : annotations) {
/*  29: 80 */       bindSequenceGenerator(bindingContext.getMetadataImplementor(), generator);
/*  30:    */     }
/*  31: 83 */     annotations = bindingContext.getIndex().getAnnotations(JPADotNames.TABLE_GENERATOR);
/*  32: 84 */     for (AnnotationInstance generator : annotations) {
/*  33: 85 */       bindTableGenerator(bindingContext.getMetadataImplementor(), generator);
/*  34:    */     }
/*  35: 88 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.GENERIC_GENERATOR);
/*  36: 89 */     for (AnnotationInstance generator : annotations) {
/*  37: 90 */       bindGenericGenerator(bindingContext.getMetadataImplementor(), generator);
/*  38:    */     }
/*  39: 93 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.GENERIC_GENERATORS);
/*  40: 94 */     for (AnnotationInstance generators : annotations) {
/*  41: 95 */       for (AnnotationInstance generator : (AnnotationInstance[])JandexHelper.getValue(generators, "value", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  42:100 */         bindGenericGenerator(bindingContext.getMetadataImplementor(), generator);
/*  43:    */       }
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   private static void addStringParameter(AnnotationInstance annotation, String element, Map<String, String> parameters, String parameter)
/*  48:    */   {
/*  49:109 */     String string = (String)JandexHelper.getValue(annotation, element, String.class);
/*  50:110 */     if (StringHelper.isNotEmpty(string)) {
/*  51:111 */       parameters.put(parameter, string);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static void bindGenericGenerator(MetadataImplementor metadata, AnnotationInstance generator)
/*  56:    */   {
/*  57:116 */     String name = (String)JandexHelper.getValue(generator, "name", String.class);
/*  58:117 */     Map<String, String> parameterMap = new HashMap();
/*  59:118 */     AnnotationInstance[] parameterAnnotations = (AnnotationInstance[])JandexHelper.getValue(generator, "parameters", [Lorg.jboss.jandex.AnnotationInstance.class);
/*  60:123 */     for (AnnotationInstance parameterAnnotation : parameterAnnotations) {
/*  61:124 */       parameterMap.put(JandexHelper.getValue(parameterAnnotation, "name", String.class), JandexHelper.getValue(parameterAnnotation, "value", String.class));
/*  62:    */     }
/*  63:129 */     metadata.addIdGenerator(new IdGenerator(name, (String)JandexHelper.getValue(generator, "strategy", String.class), parameterMap));
/*  64:    */     
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:136 */     LOG.tracef("Add generic generator with name: %s", name);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static void bindSequenceGenerator(MetadataImplementor metadata, AnnotationInstance generator)
/*  74:    */   {
/*  75:140 */     String name = (String)JandexHelper.getValue(generator, "name", String.class);
/*  76:141 */     Map<String, String> parameterMap = new HashMap();
/*  77:142 */     addStringParameter(generator, "sequenceName", parameterMap, "sequence_name");
/*  78:143 */     boolean useNewIdentifierGenerators = metadata.getOptions().useNewIdentifierGenerators();
/*  79:144 */     String strategy = EnumConversionHelper.generationTypeToGeneratorStrategyName(GenerationType.SEQUENCE, useNewIdentifierGenerators);
/*  80:148 */     if (useNewIdentifierGenerators)
/*  81:    */     {
/*  82:149 */       addStringParameter(generator, "catalog", parameterMap, "catalog");
/*  83:150 */       addStringParameter(generator, "schema", parameterMap, "schema");
/*  84:151 */       parameterMap.put("increment_size", String.valueOf(JandexHelper.getValue(generator, "allocationSize", Integer.class)));
/*  85:    */       
/*  86:    */ 
/*  87:    */ 
/*  88:155 */       parameterMap.put("initial_value", String.valueOf(JandexHelper.getValue(generator, "initialValue", Integer.class)));
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:161 */       if (((Integer)JandexHelper.getValue(generator, "initialValue", Integer.class)).intValue() != 1) {
/*  93:162 */         LOG.unsupportedInitialValue("hibernate.id.new_generator_mappings");
/*  94:    */       }
/*  95:164 */       parameterMap.put("max_lo", String.valueOf(((Integer)JandexHelper.getValue(generator, "allocationSize", Integer.class)).intValue() - 1));
/*  96:    */     }
/*  97:169 */     metadata.addIdGenerator(new IdGenerator(name, strategy, parameterMap));
/*  98:170 */     LOG.tracef("Add sequence generator with name: %s", name);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static void bindTableGenerator(MetadataImplementor metadata, AnnotationInstance generator)
/* 102:    */   {
/* 103:174 */     String name = (String)JandexHelper.getValue(generator, "name", String.class);
/* 104:175 */     Map<String, String> parameterMap = new HashMap();
/* 105:176 */     addStringParameter(generator, "catalog", parameterMap, "catalog");
/* 106:177 */     addStringParameter(generator, "schema", parameterMap, "schema");
/* 107:178 */     boolean useNewIdentifierGenerators = metadata.getOptions().useNewIdentifierGenerators();
/* 108:179 */     String strategy = EnumConversionHelper.generationTypeToGeneratorStrategyName(GenerationType.TABLE, useNewIdentifierGenerators);
/* 109:183 */     if (useNewIdentifierGenerators)
/* 110:    */     {
/* 111:184 */       parameterMap.put("prefer_entity_table_as_segment_value", "true");
/* 112:185 */       addStringParameter(generator, "table", parameterMap, "table_name");
/* 113:186 */       addStringParameter(generator, "pkColumnName", parameterMap, "segment_column_name");
/* 114:187 */       addStringParameter(generator, "pkColumnValue", parameterMap, "segment_value");
/* 115:188 */       addStringParameter(generator, "valueColumnName", parameterMap, "value_column_name");
/* 116:189 */       parameterMap.put("increment_size", String.valueOf(JandexHelper.getValue(generator, "allocationSize", String.class)));
/* 117:    */       
/* 118:    */ 
/* 119:    */ 
/* 120:193 */       parameterMap.put("initial_value", String.valueOf((String)JandexHelper.getValue(generator, "initialValue", String.class) + 1));
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:199 */       addStringParameter(generator, "table", parameterMap, "table");
/* 125:200 */       addStringParameter(generator, "pkColumnName", parameterMap, "primary_key_column");
/* 126:201 */       addStringParameter(generator, "pkColumnValue", parameterMap, "primary_key_value");
/* 127:202 */       addStringParameter(generator, "valueColumnName", parameterMap, "value_column");
/* 128:203 */       parameterMap.put("max_lo", String.valueOf(((Integer)JandexHelper.getValue(generator, "allocationSize", Integer.class)).intValue() - 1));
/* 129:    */     }
/* 130:208 */     if (((AnnotationInstance[])JandexHelper.getValue(generator, "uniqueConstraints", [Lorg.jboss.jandex.AnnotationInstance.class)).length > 0) {
/* 131:209 */       LOG.ignoringTableGeneratorConstraints(name);
/* 132:    */     }
/* 133:211 */     metadata.addIdGenerator(new IdGenerator(name, strategy, parameterMap));
/* 134:212 */     LOG.tracef("Add table generator with name: %s", name);
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.global.IdGeneratorBinder
 * JD-Core Version:    0.7.0.1
 */