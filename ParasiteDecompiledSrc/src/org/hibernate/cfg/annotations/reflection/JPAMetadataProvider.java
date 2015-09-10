/*   1:    */ package org.hibernate.cfg.annotations.reflection;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.lang.reflect.AnnotatedElement;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.persistence.EntityListeners;
/*  12:    */ import javax.persistence.NamedNativeQuery;
/*  13:    */ import javax.persistence.NamedQuery;
/*  14:    */ import javax.persistence.SequenceGenerator;
/*  15:    */ import javax.persistence.SqlResultSetMapping;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import org.dom4j.Element;
/*  18:    */ import org.hibernate.annotations.common.reflection.AnnotationReader;
/*  19:    */ import org.hibernate.annotations.common.reflection.MetadataProvider;
/*  20:    */ import org.hibernate.annotations.common.reflection.java.JavaMetadataProvider;
/*  21:    */ import org.hibernate.internal.util.ReflectHelper;
/*  22:    */ 
/*  23:    */ public class JPAMetadataProvider
/*  24:    */   implements MetadataProvider, Serializable
/*  25:    */ {
/*  26: 53 */   private transient MetadataProvider delegate = new JavaMetadataProvider();
/*  27:    */   private transient Map<Object, Object> defaults;
/*  28: 55 */   private transient Map<AnnotatedElement, AnnotationReader> cache = new HashMap(100);
/*  29: 58 */   private XMLContext xmlContext = new XMLContext();
/*  30:    */   
/*  31:    */   private void readObject(ObjectInputStream ois)
/*  32:    */     throws IOException, ClassNotFoundException
/*  33:    */   {
/*  34: 61 */     ois.defaultReadObject();
/*  35: 62 */     this.delegate = new JavaMetadataProvider();
/*  36: 63 */     this.cache = new HashMap(100);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public AnnotationReader getAnnotationReader(AnnotatedElement annotatedElement)
/*  40:    */   {
/*  41: 67 */     AnnotationReader reader = (AnnotationReader)this.cache.get(annotatedElement);
/*  42: 68 */     if (reader == null)
/*  43:    */     {
/*  44: 69 */       if (this.xmlContext.hasContext()) {
/*  45: 70 */         reader = new JPAOverridenAnnotationReader(annotatedElement, this.xmlContext);
/*  46:    */       } else {
/*  47: 73 */         reader = this.delegate.getAnnotationReader(annotatedElement);
/*  48:    */       }
/*  49: 75 */       this.cache.put(annotatedElement, reader);
/*  50:    */     }
/*  51: 77 */     return reader;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Map<Object, Object> getDefaults()
/*  55:    */   {
/*  56:    */     XMLContext.Default xmlDefaults;
/*  57: 81 */     if (this.defaults == null)
/*  58:    */     {
/*  59: 82 */       this.defaults = new HashMap();
/*  60: 83 */       xmlDefaults = this.xmlContext.getDefault(null);
/*  61:    */       
/*  62: 85 */       this.defaults.put("schema", xmlDefaults.getSchema());
/*  63: 86 */       this.defaults.put("catalog", xmlDefaults.getCatalog());
/*  64: 87 */       this.defaults.put("delimited-identifier", xmlDefaults.getDelimitedIdentifier());
/*  65: 88 */       List<Class> entityListeners = new ArrayList();
/*  66: 89 */       for (String className : this.xmlContext.getDefaultEntityListeners()) {
/*  67:    */         try
/*  68:    */         {
/*  69: 91 */           entityListeners.add(ReflectHelper.classForName(className, getClass()));
/*  70:    */         }
/*  71:    */         catch (ClassNotFoundException e)
/*  72:    */         {
/*  73: 94 */           throw new IllegalStateException("Default entity listener class not found: " + className);
/*  74:    */         }
/*  75:    */       }
/*  76: 97 */       this.defaults.put(EntityListeners.class, entityListeners);
/*  77: 98 */       for (Element element : this.xmlContext.getAllDocuments())
/*  78:    */       {
/*  79:100 */         List<Element> elements = element.elements("sequence-generator");
/*  80:101 */         List<SequenceGenerator> sequenceGenerators = (List)this.defaults.get(SequenceGenerator.class);
/*  81:102 */         if (sequenceGenerators == null)
/*  82:    */         {
/*  83:103 */           sequenceGenerators = new ArrayList();
/*  84:104 */           this.defaults.put(SequenceGenerator.class, sequenceGenerators);
/*  85:    */         }
/*  86:106 */         for (Element subelement : elements) {
/*  87:107 */           sequenceGenerators.add(JPAOverridenAnnotationReader.buildSequenceGeneratorAnnotation(subelement));
/*  88:    */         }
/*  89:110 */         elements = element.elements("table-generator");
/*  90:111 */         List<TableGenerator> tableGenerators = (List)this.defaults.get(TableGenerator.class);
/*  91:112 */         if (tableGenerators == null)
/*  92:    */         {
/*  93:113 */           tableGenerators = new ArrayList();
/*  94:114 */           this.defaults.put(TableGenerator.class, tableGenerators);
/*  95:    */         }
/*  96:116 */         for (Element subelement : elements) {
/*  97:117 */           tableGenerators.add(JPAOverridenAnnotationReader.buildTableGeneratorAnnotation(subelement, xmlDefaults));
/*  98:    */         }
/*  99:124 */         List<NamedQuery> namedQueries = (List)this.defaults.get(NamedQuery.class);
/* 100:125 */         if (namedQueries == null)
/* 101:    */         {
/* 102:126 */           namedQueries = new ArrayList();
/* 103:127 */           this.defaults.put(NamedQuery.class, namedQueries);
/* 104:    */         }
/* 105:129 */         List<NamedQuery> currentNamedQueries = JPAOverridenAnnotationReader.buildNamedQueries(element, false, xmlDefaults);
/* 106:    */         
/* 107:    */ 
/* 108:132 */         namedQueries.addAll(currentNamedQueries);
/* 109:    */         
/* 110:134 */         List<NamedNativeQuery> namedNativeQueries = (List)this.defaults.get(NamedNativeQuery.class);
/* 111:135 */         if (namedNativeQueries == null)
/* 112:    */         {
/* 113:136 */           namedNativeQueries = new ArrayList();
/* 114:137 */           this.defaults.put(NamedNativeQuery.class, namedNativeQueries);
/* 115:    */         }
/* 116:139 */         List<NamedNativeQuery> currentNamedNativeQueries = JPAOverridenAnnotationReader.buildNamedQueries(element, true, xmlDefaults);
/* 117:    */         
/* 118:    */ 
/* 119:142 */         namedNativeQueries.addAll(currentNamedNativeQueries);
/* 120:    */         
/* 121:144 */         List<SqlResultSetMapping> sqlResultSetMappings = (List)this.defaults.get(SqlResultSetMapping.class);
/* 122:147 */         if (sqlResultSetMappings == null)
/* 123:    */         {
/* 124:148 */           sqlResultSetMappings = new ArrayList();
/* 125:149 */           this.defaults.put(SqlResultSetMapping.class, sqlResultSetMappings);
/* 126:    */         }
/* 127:151 */         List<SqlResultSetMapping> currentSqlResultSetMappings = JPAOverridenAnnotationReader.buildSqlResultsetMappings(element, xmlDefaults);
/* 128:    */         
/* 129:    */ 
/* 130:154 */         sqlResultSetMappings.addAll(currentSqlResultSetMappings);
/* 131:    */       }
/* 132:    */     }
/* 133:157 */     return this.defaults;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public XMLContext getXMLContext()
/* 137:    */   {
/* 138:161 */     return this.xmlContext;
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.reflection.JPAMetadataProvider
 * JD-Core Version:    0.7.0.1
 */