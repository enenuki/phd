/*   1:    */ package com.fasterxml.classmate.util;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import java.util.LinkedHashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ 
/*   8:    */ public class ResolvedTypeCache
/*   9:    */ {
/*  10:    */   protected final CacheMap _map;
/*  11:    */   
/*  12:    */   public ResolvedTypeCache(int maxEntries)
/*  13:    */   {
/*  14: 19 */     this._map = new CacheMap(maxEntries);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Key key(Class<?> simpleType)
/*  18:    */   {
/*  19: 26 */     return new Key(simpleType);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Key key(Class<?> simpleType, ResolvedType[] tp)
/*  23:    */   {
/*  24: 33 */     return new Key(simpleType, tp);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public synchronized ResolvedType find(Key key)
/*  28:    */   {
/*  29: 37 */     return (ResolvedType)this._map.get(key);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public synchronized int size()
/*  33:    */   {
/*  34: 41 */     return this._map.size();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public synchronized void put(Key key, ResolvedType type)
/*  38:    */   {
/*  39: 45 */     this._map.put(key, type);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void add(ResolvedType type)
/*  43:    */   {
/*  44: 56 */     List<ResolvedType> tp = type.getTypeParameters();
/*  45: 57 */     ResolvedType[] tpa = (ResolvedType[])tp.toArray(new ResolvedType[tp.size()]);
/*  46: 58 */     put(key(type.getErasedType(), tpa), type);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static class Key
/*  50:    */   {
/*  51:    */     private final Class<?> _erasedType;
/*  52:    */     private final ResolvedType[] _typeParameters;
/*  53:    */     private final int _hashCode;
/*  54:    */     
/*  55:    */     public Key(Class<?> simpleType)
/*  56:    */     {
/*  57: 79 */       this(simpleType, null);
/*  58:    */     }
/*  59:    */     
/*  60:    */     public Key(Class<?> erasedType, ResolvedType[] tp)
/*  61:    */     {
/*  62: 85 */       if ((tp != null) && (tp.length == 0)) {
/*  63: 86 */         tp = null;
/*  64:    */       }
/*  65: 88 */       this._erasedType = erasedType;
/*  66: 89 */       this._typeParameters = tp;
/*  67: 90 */       int h = erasedType.getName().hashCode();
/*  68: 91 */       if (tp != null) {
/*  69: 92 */         h += tp.length;
/*  70:    */       }
/*  71: 94 */       this._hashCode = h;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public int hashCode()
/*  75:    */     {
/*  76: 98 */       return this._hashCode;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public boolean equals(Object o)
/*  80:    */     {
/*  81:103 */       if (o == this) {
/*  82:103 */         return true;
/*  83:    */       }
/*  84:104 */       if ((o == null) || (o.getClass() != getClass())) {
/*  85:104 */         return false;
/*  86:    */       }
/*  87:105 */       Key other = (Key)o;
/*  88:106 */       if (other._erasedType != this._erasedType) {
/*  89:106 */         return false;
/*  90:    */       }
/*  91:107 */       ResolvedType[] otherTP = other._typeParameters;
/*  92:108 */       if (this._typeParameters == null) {
/*  93:109 */         return otherTP == null;
/*  94:    */       }
/*  95:111 */       if ((otherTP == null) || (otherTP.length != this._typeParameters.length)) {
/*  96:112 */         return false;
/*  97:    */       }
/*  98:114 */       int i = 0;
/*  99:114 */       for (int len = this._typeParameters.length; i < len; i++) {
/* 100:115 */         if (!this._typeParameters[i].equals(otherTP[i])) {
/* 101:116 */           return false;
/* 102:    */         }
/* 103:    */       }
/* 104:119 */       return true;
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private static final class CacheMap
/* 109:    */     extends LinkedHashMap<ResolvedTypeCache.Key, ResolvedType>
/* 110:    */   {
/* 111:    */     protected final int _maxEntries;
/* 112:    */     
/* 113:    */     public CacheMap(int maxEntries)
/* 114:    */     {
/* 115:132 */       this._maxEntries = maxEntries;
/* 116:    */     }
/* 117:    */     
/* 118:    */     protected boolean removeEldestEntry(Map.Entry<ResolvedTypeCache.Key, ResolvedType> eldest)
/* 119:    */     {
/* 120:137 */       return size() > this._maxEntries;
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.util.ResolvedTypeCache
 * JD-Core Version:    0.7.0.1
 */