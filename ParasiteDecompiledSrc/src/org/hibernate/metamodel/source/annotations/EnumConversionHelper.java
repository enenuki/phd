/*   1:    */ package org.hibernate.metamodel.source.annotations;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import javax.persistence.CascadeType;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  10:    */ import org.hibernate.id.MultipleHiLoPerTableGenerator;
/*  11:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  12:    */ 
/*  13:    */ public class EnumConversionHelper
/*  14:    */ {
/*  15:    */   public static String generationTypeToGeneratorStrategyName(GenerationType generatorEnum, boolean useNewGeneratorMappings)
/*  16:    */   {
/*  17: 48 */     switch (1.$SwitchMap$javax$persistence$GenerationType[generatorEnum.ordinal()])
/*  18:    */     {
/*  19:    */     case 1: 
/*  20: 50 */       return "identity";
/*  21:    */     case 2: 
/*  22: 52 */       return useNewGeneratorMappings ? "enhanced-sequence" : "native";
/*  23:    */     case 3: 
/*  24: 56 */       return useNewGeneratorMappings ? "enhanced-table" : MultipleHiLoPerTableGenerator.class.getName();
/*  25:    */     case 4: 
/*  26: 60 */       return useNewGeneratorMappings ? "enhanced-sequence" : "seqhilo";
/*  27:    */     }
/*  28: 64 */     throw new AssertionFailure("Unknown GeneratorType: " + generatorEnum);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static CascadeStyle cascadeTypeToCascadeStyle(CascadeType cascadeType)
/*  32:    */   {
/*  33: 68 */     switch (1.$SwitchMap$javax$persistence$CascadeType[cascadeType.ordinal()])
/*  34:    */     {
/*  35:    */     case 1: 
/*  36: 70 */       return CascadeStyle.ALL;
/*  37:    */     case 2: 
/*  38: 73 */       return CascadeStyle.PERSIST;
/*  39:    */     case 3: 
/*  40: 76 */       return CascadeStyle.MERGE;
/*  41:    */     case 4: 
/*  42: 79 */       return CascadeStyle.DELETE;
/*  43:    */     case 5: 
/*  44: 82 */       return CascadeStyle.REFRESH;
/*  45:    */     case 6: 
/*  46: 85 */       return CascadeStyle.EVICT;
/*  47:    */     }
/*  48: 88 */     throw new AssertionFailure("Unknown cascade type");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static org.hibernate.FetchMode annotationFetchModeToHibernateFetchMode(org.hibernate.annotations.FetchMode annotationFetchMode)
/*  52:    */   {
/*  53: 94 */     switch (1.$SwitchMap$org$hibernate$annotations$FetchMode[annotationFetchMode.ordinal()])
/*  54:    */     {
/*  55:    */     case 1: 
/*  56: 96 */       return org.hibernate.FetchMode.JOIN;
/*  57:    */     case 2: 
/*  58: 99 */       return org.hibernate.FetchMode.SELECT;
/*  59:    */     case 3: 
/*  60:104 */       return org.hibernate.FetchMode.SELECT;
/*  61:    */     }
/*  62:107 */     throw new AssertionFailure("Unknown fetch mode");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Set<CascadeStyle> cascadeTypeToCascadeStyleSet(Set<CascadeType> cascadeTypes)
/*  66:    */   {
/*  67:113 */     if (CollectionHelper.isEmpty(cascadeTypes)) {
/*  68:114 */       return Collections.emptySet();
/*  69:    */     }
/*  70:116 */     Set<CascadeStyle> cascadeStyleSet = new HashSet();
/*  71:117 */     for (CascadeType cascadeType : cascadeTypes) {
/*  72:118 */       cascadeStyleSet.add(cascadeTypeToCascadeStyle(cascadeType));
/*  73:    */     }
/*  74:120 */     return cascadeStyleSet;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.EnumConversionHelper
 * JD-Core Version:    0.7.0.1
 */