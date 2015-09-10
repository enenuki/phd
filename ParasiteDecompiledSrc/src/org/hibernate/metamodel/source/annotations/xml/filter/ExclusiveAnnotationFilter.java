/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.filter;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.metamodel.source.annotations.xml.mocker.MockHelper;
/*  11:    */ import org.jboss.jandex.AnnotationInstance;
/*  12:    */ import org.jboss.jandex.AnnotationTarget;
/*  13:    */ import org.jboss.jandex.DotName;
/*  14:    */ 
/*  15:    */ class ExclusiveAnnotationFilter
/*  16:    */   extends AbstractAnnotationFilter
/*  17:    */ {
/*  18: 45 */   public static ExclusiveAnnotationFilter INSTANCE = new ExclusiveAnnotationFilter();
/*  19:    */   private DotName[] targetNames;
/*  20:    */   private List<ExclusiveGroup> exclusiveGroupList;
/*  21:    */   
/*  22:    */   private ExclusiveAnnotationFilter()
/*  23:    */   {
/*  24: 50 */     this.exclusiveGroupList = getExclusiveGroupList();
/*  25: 51 */     Set<DotName> names = new HashSet();
/*  26: 52 */     for (ExclusiveGroup group : this.exclusiveGroupList) {
/*  27: 53 */       names.addAll(group.getNames());
/*  28:    */     }
/*  29: 55 */     this.targetNames = ((DotName[])names.toArray(new DotName[names.size()]));
/*  30:    */   }
/*  31:    */   
/*  32:    */   private List<ExclusiveGroup> getExclusiveGroupList()
/*  33:    */   {
/*  34: 59 */     if (this.exclusiveGroupList == null)
/*  35:    */     {
/*  36: 60 */       this.exclusiveGroupList = new ArrayList();
/*  37: 61 */       ExclusiveGroup group = new ExclusiveGroup(null);
/*  38: 62 */       group.add(ENTITY);
/*  39: 63 */       group.add(MAPPED_SUPERCLASS);
/*  40: 64 */       group.add(EMBEDDABLE);
/*  41: 65 */       group.scope = Scope.TYPE;
/*  42: 66 */       this.exclusiveGroupList.add(group);
/*  43:    */       
/*  44: 68 */       group = new ExclusiveGroup(null);
/*  45: 69 */       group.add(SECONDARY_TABLES);
/*  46: 70 */       group.add(SECONDARY_TABLE);
/*  47: 71 */       group.scope = Scope.TYPE;
/*  48: 72 */       this.exclusiveGroupList.add(group);
/*  49:    */       
/*  50: 74 */       group = new ExclusiveGroup(null);
/*  51: 75 */       group.add(PRIMARY_KEY_JOIN_COLUMNS);
/*  52: 76 */       group.add(PRIMARY_KEY_JOIN_COLUMN);
/*  53: 77 */       group.scope = Scope.ATTRIBUTE;
/*  54: 78 */       this.exclusiveGroupList.add(group);
/*  55:    */       
/*  56: 80 */       group = new ExclusiveGroup(null);
/*  57: 81 */       group.add(SQL_RESULT_SET_MAPPING);
/*  58: 82 */       group.add(SQL_RESULT_SET_MAPPINGS);
/*  59: 83 */       group.scope = Scope.TYPE;
/*  60: 84 */       this.exclusiveGroupList.add(group);
/*  61:    */       
/*  62: 86 */       group = new ExclusiveGroup(null);
/*  63: 87 */       group.add(NAMED_NATIVE_QUERY);
/*  64: 88 */       group.add(NAMED_NATIVE_QUERIES);
/*  65: 89 */       group.scope = Scope.TYPE;
/*  66: 90 */       this.exclusiveGroupList.add(group);
/*  67:    */       
/*  68: 92 */       group = new ExclusiveGroup(null);
/*  69: 93 */       group.add(NAMED_QUERY);
/*  70: 94 */       group.add(NAMED_QUERIES);
/*  71: 95 */       group.scope = Scope.TYPE;
/*  72: 96 */       this.exclusiveGroupList.add(group);
/*  73:    */       
/*  74: 98 */       group = new ExclusiveGroup(null);
/*  75: 99 */       group.add(ATTRIBUTE_OVERRIDES);
/*  76:100 */       group.add(ATTRIBUTE_OVERRIDE);
/*  77:101 */       group.scope = Scope.ATTRIBUTE;
/*  78:102 */       this.exclusiveGroupList.add(group);
/*  79:    */       
/*  80:104 */       group = new ExclusiveGroup(null);
/*  81:105 */       group.add(ASSOCIATION_OVERRIDE);
/*  82:106 */       group.add(ASSOCIATION_OVERRIDES);
/*  83:107 */       group.scope = Scope.ATTRIBUTE;
/*  84:108 */       this.exclusiveGroupList.add(group);
/*  85:    */       
/*  86:110 */       group = new ExclusiveGroup(null);
/*  87:111 */       group.add(MAP_KEY_JOIN_COLUMN);
/*  88:112 */       group.add(MAP_KEY_JOIN_COLUMNS);
/*  89:113 */       group.scope = Scope.ATTRIBUTE;
/*  90:114 */       this.exclusiveGroupList.add(group);
/*  91:    */     }
/*  92:117 */     return this.exclusiveGroupList;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void overrideIndexedAnnotationMap(DotName annName, AnnotationInstance annotationInstance, Map<DotName, List<AnnotationInstance>> map)
/*  96:    */   {
/*  97:122 */     ExclusiveGroup group = getExclusiveGroup(annName);
/*  98:123 */     if (group == null) {
/*  99:124 */       return;
/* 100:    */     }
/* 101:126 */     AnnotationTarget target = annotationInstance.target();
/* 102:127 */     for (DotName entityAnnName : group) {
/* 103:128 */       if (map.containsKey(entityAnnName)) {
/* 104:131 */         switch (1.$SwitchMap$org$hibernate$metamodel$source$annotations$xml$filter$ExclusiveAnnotationFilter$Scope[group.scope.ordinal()])
/* 105:    */         {
/* 106:    */         case 1: 
/* 107:133 */           map.put(entityAnnName, Collections.emptyList());
/* 108:134 */           break;
/* 109:    */         case 2: 
/* 110:136 */           List<AnnotationInstance> indexedAnnotationInstanceList = (List)map.get(entityAnnName);
/* 111:137 */           Iterator<AnnotationInstance> iter = indexedAnnotationInstanceList.iterator();
/* 112:138 */           while (iter.hasNext())
/* 113:    */           {
/* 114:139 */             AnnotationInstance ann = (AnnotationInstance)iter.next();
/* 115:140 */             if (MockHelper.targetEquals(target, ann.target())) {
/* 116:141 */               iter.remove();
/* 117:    */             }
/* 118:    */           }
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected DotName[] targetAnnotation()
/* 125:    */   {
/* 126:151 */     return this.targetNames;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private ExclusiveGroup getExclusiveGroup(DotName annName)
/* 130:    */   {
/* 131:155 */     for (ExclusiveGroup group : this.exclusiveGroupList) {
/* 132:156 */       if (group.contains(annName)) {
/* 133:157 */         return group;
/* 134:    */       }
/* 135:    */     }
/* 136:160 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   static enum Scope
/* 140:    */   {
/* 141:163 */     TYPE,  ATTRIBUTE;
/* 142:    */     
/* 143:    */     private Scope() {}
/* 144:    */   }
/* 145:    */   
/* 146:    */   private class ExclusiveGroup
/* 147:    */     implements Iterable<DotName>
/* 148:    */   {
/* 149:    */     private ExclusiveGroup() {}
/* 150:    */     
/* 151:    */     public Set<DotName> getNames()
/* 152:    */     {
/* 153:167 */       return this.names;
/* 154:    */     }
/* 155:    */     
/* 156:170 */     private Set<DotName> names = new HashSet();
/* 157:171 */     ExclusiveAnnotationFilter.Scope scope = ExclusiveAnnotationFilter.Scope.ATTRIBUTE;
/* 158:    */     
/* 159:    */     public Iterator iterator()
/* 160:    */     {
/* 161:175 */       return this.names.iterator();
/* 162:    */     }
/* 163:    */     
/* 164:    */     boolean contains(DotName name)
/* 165:    */     {
/* 166:179 */       return this.names.contains(name);
/* 167:    */     }
/* 168:    */     
/* 169:    */     void add(DotName name)
/* 170:    */     {
/* 171:183 */       this.names.add(name);
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.filter.ExclusiveAnnotationFilter
 * JD-Core Version:    0.7.0.1
 */