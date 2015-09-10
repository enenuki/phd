/*   1:    */ package org.hibernate.bytecode.instrumentation.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Set;
/*   5:    */ import org.hibernate.bytecode.instrumentation.spi.AbstractFieldInterceptor;
/*   6:    */ import org.hibernate.bytecode.internal.javassist.FieldHandler;
/*   7:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   8:    */ import org.hibernate.proxy.HibernateProxy;
/*   9:    */ import org.hibernate.proxy.LazyInitializer;
/*  10:    */ 
/*  11:    */ public final class FieldInterceptorImpl
/*  12:    */   extends AbstractFieldInterceptor
/*  13:    */   implements FieldHandler, Serializable
/*  14:    */ {
/*  15:    */   FieldInterceptorImpl(SessionImplementor session, Set uninitializedFields, String entityName)
/*  16:    */   {
/*  17: 53 */     super(session, uninitializedFields, entityName);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean readBoolean(Object target, String name, boolean oldValue)
/*  21:    */   {
/*  22: 60 */     return ((Boolean)intercept(target, name, Boolean.valueOf(oldValue))).booleanValue();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public byte readByte(Object target, String name, byte oldValue)
/*  26:    */   {
/*  27: 65 */     return ((Byte)intercept(target, name, Byte.valueOf(oldValue))).byteValue();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public char readChar(Object target, String name, char oldValue)
/*  31:    */   {
/*  32: 69 */     return ((Character)intercept(target, name, Character.valueOf(oldValue))).charValue();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public double readDouble(Object target, String name, double oldValue)
/*  36:    */   {
/*  37: 74 */     return ((Double)intercept(target, name, Double.valueOf(oldValue))).doubleValue();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public float readFloat(Object target, String name, float oldValue)
/*  41:    */   {
/*  42: 79 */     return ((Float)intercept(target, name, Float.valueOf(oldValue))).floatValue();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int readInt(Object target, String name, int oldValue)
/*  46:    */   {
/*  47: 84 */     return ((Integer)intercept(target, name, Integer.valueOf(oldValue))).intValue();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public long readLong(Object target, String name, long oldValue)
/*  51:    */   {
/*  52: 89 */     return ((Long)intercept(target, name, Long.valueOf(oldValue))).longValue();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public short readShort(Object target, String name, short oldValue)
/*  56:    */   {
/*  57: 93 */     return ((Short)intercept(target, name, Short.valueOf(oldValue))).shortValue();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Object readObject(Object target, String name, Object oldValue)
/*  61:    */   {
/*  62: 98 */     Object value = intercept(target, name, oldValue);
/*  63: 99 */     if ((value instanceof HibernateProxy))
/*  64:    */     {
/*  65:100 */       LazyInitializer li = ((HibernateProxy)value).getHibernateLazyInitializer();
/*  66:101 */       if (li.isUnwrap()) {
/*  67:102 */         value = li.getImplementation();
/*  68:    */       }
/*  69:    */     }
/*  70:105 */     return value;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean writeBoolean(Object target, String name, boolean oldValue, boolean newValue)
/*  74:    */   {
/*  75:109 */     dirty();
/*  76:110 */     intercept(target, name, Boolean.valueOf(oldValue));
/*  77:111 */     return newValue;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public byte writeByte(Object target, String name, byte oldValue, byte newValue)
/*  81:    */   {
/*  82:115 */     dirty();
/*  83:116 */     intercept(target, name, Byte.valueOf(oldValue));
/*  84:117 */     return newValue;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public char writeChar(Object target, String name, char oldValue, char newValue)
/*  88:    */   {
/*  89:121 */     dirty();
/*  90:122 */     intercept(target, name, Character.valueOf(oldValue));
/*  91:123 */     return newValue;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public double writeDouble(Object target, String name, double oldValue, double newValue)
/*  95:    */   {
/*  96:127 */     dirty();
/*  97:128 */     intercept(target, name, Double.valueOf(oldValue));
/*  98:129 */     return newValue;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public float writeFloat(Object target, String name, float oldValue, float newValue)
/* 102:    */   {
/* 103:133 */     dirty();
/* 104:134 */     intercept(target, name, Float.valueOf(oldValue));
/* 105:135 */     return newValue;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int writeInt(Object target, String name, int oldValue, int newValue)
/* 109:    */   {
/* 110:139 */     dirty();
/* 111:140 */     intercept(target, name, Integer.valueOf(oldValue));
/* 112:141 */     return newValue;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public long writeLong(Object target, String name, long oldValue, long newValue)
/* 116:    */   {
/* 117:145 */     dirty();
/* 118:146 */     intercept(target, name, Long.valueOf(oldValue));
/* 119:147 */     return newValue;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public short writeShort(Object target, String name, short oldValue, short newValue)
/* 123:    */   {
/* 124:151 */     dirty();
/* 125:152 */     intercept(target, name, Short.valueOf(oldValue));
/* 126:153 */     return newValue;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Object writeObject(Object target, String name, Object oldValue, Object newValue)
/* 130:    */   {
/* 131:157 */     dirty();
/* 132:158 */     intercept(target, name, oldValue);
/* 133:159 */     return newValue;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String toString()
/* 137:    */   {
/* 138:163 */     return "FieldInterceptorImpl(entityName=" + getEntityName() + ",dirty=" + isDirty() + ",uninitializedFields=" + getUninitializedFields() + ')';
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.instrumentation.internal.javassist.FieldInterceptorImpl
 * JD-Core Version:    0.7.0.1
 */