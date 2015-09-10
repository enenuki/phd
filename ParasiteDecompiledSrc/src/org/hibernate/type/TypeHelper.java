/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   6:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   7:    */ import org.hibernate.property.BackrefPropertyAccessor;
/*   8:    */ import org.hibernate.tuple.StandardProperty;
/*   9:    */ 
/*  10:    */ public class TypeHelper
/*  11:    */ {
/*  12:    */   public static void deepCopy(Object[] values, Type[] types, boolean[] copy, Object[] target, SessionImplementor session)
/*  13:    */   {
/*  14: 60 */     for (int i = 0; i < types.length; i++) {
/*  15: 61 */       if (copy[i] != 0) {
/*  16: 62 */         if ((values[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY) || (values[i] == BackrefPropertyAccessor.UNKNOWN)) {
/*  17: 64 */           target[i] = values[i];
/*  18:    */         } else {
/*  19: 67 */           target[i] = types[i].deepCopy(values[i], session.getFactory());
/*  20:    */         }
/*  21:    */       }
/*  22:    */     }
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static void beforeAssemble(Serializable[] row, Type[] types, SessionImplementor session)
/*  26:    */   {
/*  27: 85 */     for (int i = 0; i < types.length; i++) {
/*  28: 86 */       if ((row[i] != LazyPropertyInitializer.UNFETCHED_PROPERTY) && (row[i] != BackrefPropertyAccessor.UNKNOWN)) {
/*  29: 88 */         types[i].beforeAssemble(row[i], session);
/*  30:    */       }
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Object[] assemble(Serializable[] row, Type[] types, SessionImplementor session, Object owner)
/*  35:    */   {
/*  36:107 */     Object[] assembled = new Object[row.length];
/*  37:108 */     for (int i = 0; i < types.length; i++) {
/*  38:109 */       if ((row[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY) || (row[i] == BackrefPropertyAccessor.UNKNOWN)) {
/*  39:110 */         assembled[i] = row[i];
/*  40:    */       } else {
/*  41:113 */         assembled[i] = types[i].assemble(row[i], session, owner);
/*  42:    */       }
/*  43:    */     }
/*  44:116 */     return assembled;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static Serializable[] disassemble(Object[] row, Type[] types, boolean[] nonCacheable, SessionImplementor session, Object owner)
/*  48:    */   {
/*  49:136 */     Serializable[] disassembled = new Serializable[row.length];
/*  50:137 */     for (int i = 0; i < row.length; i++) {
/*  51:138 */       if ((nonCacheable != null) && (nonCacheable[i] != 0)) {
/*  52:139 */         disassembled[i] = LazyPropertyInitializer.UNFETCHED_PROPERTY;
/*  53:141 */       } else if ((row[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY) || (row[i] == BackrefPropertyAccessor.UNKNOWN)) {
/*  54:142 */         disassembled[i] = ((Serializable)row[i]);
/*  55:    */       } else {
/*  56:145 */         disassembled[i] = types[i].disassemble(row[i], session, owner);
/*  57:    */       }
/*  58:    */     }
/*  59:148 */     return disassembled;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static Object[] replace(Object[] original, Object[] target, Type[] types, SessionImplementor session, Object owner, Map copyCache)
/*  63:    */   {
/*  64:170 */     Object[] copied = new Object[original.length];
/*  65:171 */     for (int i = 0; i < types.length; i++) {
/*  66:172 */       if ((original[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY) || (original[i] == BackrefPropertyAccessor.UNKNOWN)) {
/*  67:174 */         copied[i] = target[i];
/*  68:    */       } else {
/*  69:177 */         copied[i] = types[i].replace(original[i], target[i], session, owner, copyCache);
/*  70:    */       }
/*  71:    */     }
/*  72:180 */     return copied;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Object[] replace(Object[] original, Object[] target, Type[] types, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection)
/*  76:    */   {
/*  77:204 */     Object[] copied = new Object[original.length];
/*  78:205 */     for (int i = 0; i < types.length; i++) {
/*  79:206 */       if ((original[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY) || (original[i] == BackrefPropertyAccessor.UNKNOWN)) {
/*  80:208 */         copied[i] = target[i];
/*  81:    */       } else {
/*  82:211 */         copied[i] = types[i].replace(original[i], target[i], session, owner, copyCache, foreignKeyDirection);
/*  83:    */       }
/*  84:    */     }
/*  85:214 */     return copied;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static Object[] replaceAssociations(Object[] original, Object[] target, Type[] types, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection)
/*  89:    */   {
/*  90:242 */     Object[] copied = new Object[original.length];
/*  91:243 */     for (int i = 0; i < types.length; i++) {
/*  92:244 */       if ((original[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY) || (original[i] == BackrefPropertyAccessor.UNKNOWN))
/*  93:    */       {
/*  94:246 */         copied[i] = target[i];
/*  95:    */       }
/*  96:248 */       else if (types[i].isComponentType())
/*  97:    */       {
/*  98:250 */         CompositeType componentType = (CompositeType)types[i];
/*  99:251 */         Type[] subtypes = componentType.getSubtypes();
/* 100:252 */         Object[] origComponentValues = original[i] == null ? new Object[subtypes.length] : componentType.getPropertyValues(original[i], session);
/* 101:253 */         Object[] targetComponentValues = target[i] == null ? new Object[subtypes.length] : componentType.getPropertyValues(target[i], session);
/* 102:254 */         replaceAssociations(origComponentValues, targetComponentValues, subtypes, session, null, copyCache, foreignKeyDirection);
/* 103:255 */         copied[i] = target[i];
/* 104:    */       }
/* 105:257 */       else if (!types[i].isAssociationType())
/* 106:    */       {
/* 107:258 */         copied[i] = target[i];
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:261 */         copied[i] = types[i].replace(original[i], target[i], session, owner, copyCache, foreignKeyDirection);
/* 112:    */       }
/* 113:    */     }
/* 114:264 */     return copied;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static int[] findDirty(StandardProperty[] properties, Object[] currentState, Object[] previousState, boolean[][] includeColumns, boolean anyUninitializedProperties, SessionImplementor session)
/* 118:    */   {
/* 119:289 */     int[] results = null;
/* 120:290 */     int count = 0;
/* 121:291 */     int span = properties.length;
/* 122:293 */     for (int i = 0; i < span; i++)
/* 123:    */     {
/* 124:294 */       boolean dirty = (currentState[i] != LazyPropertyInitializer.UNFETCHED_PROPERTY) && (properties[i].isDirtyCheckable(anyUninitializedProperties)) && (properties[i].getType().isDirty(previousState[i], currentState[i], includeColumns[i], session));
/* 125:297 */       if (dirty)
/* 126:    */       {
/* 127:298 */         if (results == null) {
/* 128:299 */           results = new int[span];
/* 129:    */         }
/* 130:301 */         results[(count++)] = i;
/* 131:    */       }
/* 132:    */     }
/* 133:305 */     if (count == 0) {
/* 134:306 */       return null;
/* 135:    */     }
/* 136:309 */     int[] trimmed = new int[count];
/* 137:310 */     System.arraycopy(results, 0, trimmed, 0, count);
/* 138:311 */     return trimmed;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static int[] findModified(StandardProperty[] properties, Object[] currentState, Object[] previousState, boolean[][] includeColumns, boolean anyUninitializedProperties, SessionImplementor session)
/* 142:    */   {
/* 143:337 */     int[] results = null;
/* 144:338 */     int count = 0;
/* 145:339 */     int span = properties.length;
/* 146:341 */     for (int i = 0; i < span; i++)
/* 147:    */     {
/* 148:342 */       boolean modified = (currentState[i] != LazyPropertyInitializer.UNFETCHED_PROPERTY) && (properties[i].isDirtyCheckable(anyUninitializedProperties)) && (properties[i].getType().isModified(previousState[i], currentState[i], includeColumns[i], session));
/* 149:346 */       if (modified)
/* 150:    */       {
/* 151:347 */         if (results == null) {
/* 152:348 */           results = new int[span];
/* 153:    */         }
/* 154:350 */         results[(count++)] = i;
/* 155:    */       }
/* 156:    */     }
/* 157:354 */     if (count == 0) {
/* 158:355 */       return null;
/* 159:    */     }
/* 160:358 */     int[] trimmed = new int[count];
/* 161:359 */     System.arraycopy(results, 0, trimmed, 0, count);
/* 162:360 */     return trimmed;
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TypeHelper
 * JD-Core Version:    0.7.0.1
 */