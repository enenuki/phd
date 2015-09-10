/*   1:    */ package org.apache.james.mime4j.util;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.NoSuchElementException;
/*  13:    */ 
/*  14:    */ public class StringArrayMap
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -5833051164281786907L;
/*  18: 48 */   private final Map<String, Object> map = new HashMap();
/*  19:    */   
/*  20:    */   public static String asString(Object pValue)
/*  21:    */   {
/*  22: 59 */     if (pValue == null) {
/*  23: 60 */       return null;
/*  24:    */     }
/*  25: 62 */     if ((pValue instanceof String)) {
/*  26: 63 */       return (String)pValue;
/*  27:    */     }
/*  28: 65 */     if ((pValue instanceof String[])) {
/*  29: 66 */       return ((String[])(String[])pValue)[0];
/*  30:    */     }
/*  31: 68 */     if ((pValue instanceof List)) {
/*  32: 69 */       return (String)((List)pValue).get(0);
/*  33:    */     }
/*  34: 71 */     throw new IllegalStateException("Invalid parameter class: " + pValue.getClass().getName());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static String[] asStringArray(Object pValue)
/*  38:    */   {
/*  39: 83 */     if (pValue == null) {
/*  40: 84 */       return null;
/*  41:    */     }
/*  42: 86 */     if ((pValue instanceof String)) {
/*  43: 87 */       return new String[] { (String)pValue };
/*  44:    */     }
/*  45: 89 */     if ((pValue instanceof String[])) {
/*  46: 90 */       return (String[])pValue;
/*  47:    */     }
/*  48: 92 */     if ((pValue instanceof List))
/*  49:    */     {
/*  50: 93 */       List<?> l = (List)pValue;
/*  51: 94 */       return (String[])l.toArray(new String[l.size()]);
/*  52:    */     }
/*  53: 96 */     throw new IllegalStateException("Invalid parameter class: " + pValue.getClass().getName());
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static Enumeration<String> asStringEnum(Object pValue)
/*  57:    */   {
/*  58:108 */     if (pValue == null) {
/*  59:109 */       return null;
/*  60:    */     }
/*  61:111 */     if ((pValue instanceof String)) {
/*  62:112 */       new Enumeration()
/*  63:    */       {
/*  64:113 */         private Object value = this.val$pValue;
/*  65:    */         
/*  66:    */         public boolean hasMoreElements()
/*  67:    */         {
/*  68:115 */           return this.value != null;
/*  69:    */         }
/*  70:    */         
/*  71:    */         public String nextElement()
/*  72:    */         {
/*  73:118 */           if (this.value == null) {
/*  74:119 */             throw new NoSuchElementException();
/*  75:    */           }
/*  76:121 */           String s = (String)this.value;
/*  77:122 */           this.value = null;
/*  78:123 */           return s;
/*  79:    */         }
/*  80:    */       };
/*  81:    */     }
/*  82:127 */     if ((pValue instanceof String[]))
/*  83:    */     {
/*  84:128 */       String[] values = (String[])pValue;
/*  85:129 */       new Enumeration()
/*  86:    */       {
/*  87:    */         private int offset;
/*  88:    */         
/*  89:    */         public boolean hasMoreElements()
/*  90:    */         {
/*  91:132 */           return this.offset < this.val$values.length;
/*  92:    */         }
/*  93:    */         
/*  94:    */         public String nextElement()
/*  95:    */         {
/*  96:135 */           if (this.offset >= this.val$values.length) {
/*  97:136 */             throw new NoSuchElementException();
/*  98:    */           }
/*  99:138 */           return this.val$values[(this.offset++)];
/* 100:    */         }
/* 101:    */       };
/* 102:    */     }
/* 103:142 */     if ((pValue instanceof List))
/* 104:    */     {
/* 105:144 */       List<String> stringList = (List)pValue;
/* 106:145 */       return Collections.enumeration(stringList);
/* 107:    */     }
/* 108:147 */     throw new IllegalStateException("Invalid parameter class: " + pValue.getClass().getName());
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static Map<String, String[]> asMap(Map<String, Object> pMap)
/* 112:    */   {
/* 113:155 */     Map<String, String[]> result = new HashMap(pMap.size());
/* 114:156 */     for (Map.Entry<String, Object> entry : pMap.entrySet())
/* 115:    */     {
/* 116:157 */       String[] value = asStringArray(entry.getValue());
/* 117:158 */       result.put(entry.getKey(), value);
/* 118:    */     }
/* 119:160 */     return Collections.unmodifiableMap(result);
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected void addMapValue(Map<String, Object> pMap, String pName, String pValue)
/* 123:    */   {
/* 124:167 */     Object o = pMap.get(pName);
/* 125:168 */     if (o == null)
/* 126:    */     {
/* 127:169 */       o = pValue;
/* 128:    */     }
/* 129:170 */     else if ((o instanceof String))
/* 130:    */     {
/* 131:171 */       List<Object> list = new ArrayList();
/* 132:172 */       list.add(o);
/* 133:173 */       list.add(pValue);
/* 134:174 */       o = list;
/* 135:    */     }
/* 136:175 */     else if ((o instanceof List))
/* 137:    */     {
/* 138:177 */       List<String> stringList = (List)o;
/* 139:178 */       stringList.add(pValue);
/* 140:    */     }
/* 141:179 */     else if ((o instanceof String[]))
/* 142:    */     {
/* 143:180 */       List<String> list = new ArrayList();
/* 144:181 */       String[] arr = (String[])o;
/* 145:182 */       for (String str : arr) {
/* 146:183 */         list.add(str);
/* 147:    */       }
/* 148:185 */       list.add(pValue);
/* 149:186 */       o = list;
/* 150:    */     }
/* 151:    */     else
/* 152:    */     {
/* 153:188 */       throw new IllegalStateException("Invalid object type: " + o.getClass().getName());
/* 154:    */     }
/* 155:190 */     pMap.put(pName, o);
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected String convertName(String pName)
/* 159:    */   {
/* 160:197 */     return pName.toLowerCase();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getValue(String pName)
/* 164:    */   {
/* 165:204 */     return asString(this.map.get(convertName(pName)));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String[] getValues(String pName)
/* 169:    */   {
/* 170:211 */     return asStringArray(this.map.get(convertName(pName)));
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Enumeration<String> getValueEnum(String pName)
/* 174:    */   {
/* 175:218 */     return asStringEnum(this.map.get(convertName(pName)));
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Enumeration<String> getNames()
/* 179:    */   {
/* 180:226 */     return Collections.enumeration(this.map.keySet());
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Map<String, String[]> getMap()
/* 184:    */   {
/* 185:235 */     return asMap(this.map);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void addValue(String pName, String pValue)
/* 189:    */   {
/* 190:242 */     addMapValue(this.map, convertName(pName), pValue);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String[] getNameArray()
/* 194:    */   {
/* 195:250 */     Collection<String> c = this.map.keySet();
/* 196:251 */     return (String[])c.toArray(new String[c.size()]);
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.StringArrayMap
 * JD-Core Version:    0.7.0.1
 */