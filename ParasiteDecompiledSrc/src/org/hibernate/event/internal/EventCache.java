/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.IdentityHashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ 
/*  11:    */ class EventCache
/*  12:    */   implements Map
/*  13:    */ {
/*  14: 55 */   private Map entityToCopyMap = new IdentityHashMap(10);
/*  15: 59 */   private Map entityToOperatedOnFlagMap = new IdentityHashMap(10);
/*  16:    */   
/*  17:    */   public void clear()
/*  18:    */   {
/*  19: 67 */     this.entityToCopyMap.clear();
/*  20: 68 */     this.entityToOperatedOnFlagMap.clear();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean containsKey(Object entity)
/*  24:    */   {
/*  25: 78 */     if (entity == null) {
/*  26: 79 */       throw new NullPointerException("null entities are not supported by " + getClass().getName());
/*  27:    */     }
/*  28: 81 */     return this.entityToCopyMap.containsKey(entity);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean containsValue(Object copy)
/*  32:    */   {
/*  33: 91 */     if (copy == null) {
/*  34: 92 */       throw new NullPointerException("null copies are not supported by " + getClass().getName());
/*  35:    */     }
/*  36: 94 */     return this.entityToCopyMap.containsValue(copy);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Set entrySet()
/*  40:    */   {
/*  41:102 */     return this.entityToCopyMap.entrySet();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object get(Object entity)
/*  45:    */   {
/*  46:112 */     if (entity == null) {
/*  47:113 */       throw new NullPointerException("null entities are not supported by " + getClass().getName());
/*  48:    */     }
/*  49:115 */     return this.entityToCopyMap.get(entity);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isEmpty()
/*  53:    */   {
/*  54:123 */     return this.entityToCopyMap.isEmpty();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Set keySet()
/*  58:    */   {
/*  59:131 */     return this.entityToCopyMap.keySet();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object put(Object entity, Object copy)
/*  63:    */   {
/*  64:143 */     if ((entity == null) || (copy == null)) {
/*  65:144 */       throw new NullPointerException("null entities and copies are not supported by " + getClass().getName());
/*  66:    */     }
/*  67:146 */     this.entityToOperatedOnFlagMap.put(entity, Boolean.FALSE);
/*  68:147 */     return this.entityToCopyMap.put(entity, copy);
/*  69:    */   }
/*  70:    */   
/*  71:    */   Object put(Object entity, Object copy, boolean isOperatedOn)
/*  72:    */   {
/*  73:161 */     if ((entity == null) || (copy == null)) {
/*  74:162 */       throw new NullPointerException("null entities and copies are not supported by " + getClass().getName());
/*  75:    */     }
/*  76:164 */     this.entityToOperatedOnFlagMap.put(entity, Boolean.valueOf(isOperatedOn));
/*  77:165 */     return this.entityToCopyMap.put(entity, copy);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void putAll(Map map)
/*  81:    */   {
/*  82:174 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/*  83:    */     {
/*  84:175 */       Map.Entry entry = (Map.Entry)it.next();
/*  85:176 */       if ((entry.getKey() == null) || (entry.getValue() == null)) {
/*  86:177 */         throw new NullPointerException("null entities and copies are not supported by " + getClass().getName());
/*  87:    */       }
/*  88:179 */       this.entityToCopyMap.put(entry.getKey(), entry.getValue());
/*  89:180 */       this.entityToOperatedOnFlagMap.put(entry.getKey(), Boolean.FALSE);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object remove(Object entity)
/*  94:    */   {
/*  95:191 */     if (entity == null) {
/*  96:192 */       throw new NullPointerException("null entities are not supported by " + getClass().getName());
/*  97:    */     }
/*  98:194 */     this.entityToOperatedOnFlagMap.remove(entity);
/*  99:195 */     return this.entityToCopyMap.remove(entity);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int size()
/* 103:    */   {
/* 104:203 */     return this.entityToCopyMap.size();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Collection values()
/* 108:    */   {
/* 109:211 */     return this.entityToCopyMap.values();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isOperatedOn(Object entity)
/* 113:    */   {
/* 114:221 */     if (entity == null) {
/* 115:222 */       throw new NullPointerException("null entities are not supported by " + getClass().getName());
/* 116:    */     }
/* 117:224 */     return ((Boolean)this.entityToOperatedOnFlagMap.get(entity)).booleanValue();
/* 118:    */   }
/* 119:    */   
/* 120:    */   void setOperatedOn(Object entity, boolean isOperatedOn)
/* 121:    */   {
/* 122:235 */     if (entity == null) {
/* 123:236 */       throw new NullPointerException("null entities are not supported by " + getClass().getName());
/* 124:    */     }
/* 125:238 */     if ((!this.entityToOperatedOnFlagMap.containsKey(entity)) || (!this.entityToCopyMap.containsKey(entity))) {
/* 126:240 */       throw new AssertionFailure("called EventCache.setOperatedOn() for entity not found in EventCache");
/* 127:    */     }
/* 128:242 */     this.entityToOperatedOnFlagMap.put(entity, Boolean.valueOf(isOperatedOn));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Map invertMap()
/* 132:    */   {
/* 133:250 */     Map result = new IdentityHashMap(this.entityToCopyMap.size());
/* 134:251 */     for (Map.Entry entry : this.entityToCopyMap.entrySet()) {
/* 135:252 */       result.put(entry.getValue(), entry.getKey());
/* 136:    */     }
/* 137:254 */     return result;
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.EventCache
 * JD-Core Version:    0.7.0.1
 */