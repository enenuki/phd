/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*   4:    */ 
/*   5:    */ public class BooleanTypeDescriptor
/*   6:    */   extends AbstractTypeDescriptor<Boolean>
/*   7:    */ {
/*   8: 37 */   public static final BooleanTypeDescriptor INSTANCE = new BooleanTypeDescriptor();
/*   9:    */   private final char characterValueTrue;
/*  10:    */   private final char characterValueFalse;
/*  11:    */   private final char characterValueTrueLC;
/*  12:    */   private final String stringValueTrue;
/*  13:    */   private final String stringValueFalse;
/*  14:    */   
/*  15:    */   public BooleanTypeDescriptor()
/*  16:    */   {
/*  17: 48 */     this('Y', 'N');
/*  18:    */   }
/*  19:    */   
/*  20:    */   public BooleanTypeDescriptor(char characterValueTrue, char characterValueFalse)
/*  21:    */   {
/*  22: 52 */     super(Boolean.class);
/*  23: 53 */     this.characterValueTrue = Character.toUpperCase(characterValueTrue);
/*  24: 54 */     this.characterValueFalse = Character.toUpperCase(characterValueFalse);
/*  25:    */     
/*  26: 56 */     this.characterValueTrueLC = Character.toLowerCase(characterValueTrue);
/*  27:    */     
/*  28: 58 */     this.stringValueTrue = String.valueOf(characterValueTrue);
/*  29: 59 */     this.stringValueFalse = String.valueOf(characterValueFalse);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String toString(Boolean value)
/*  33:    */   {
/*  34: 63 */     return value == null ? null : value.toString();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Boolean fromString(String string)
/*  38:    */   {
/*  39: 67 */     return Boolean.valueOf(string);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public <X> X unwrap(Boolean value, Class<X> type, WrapperOptions options)
/*  43:    */   {
/*  44: 72 */     if (value == null) {
/*  45: 73 */       return null;
/*  46:    */     }
/*  47: 75 */     if (Boolean.class.isAssignableFrom(type)) {
/*  48: 76 */       return value;
/*  49:    */     }
/*  50: 78 */     if (Byte.class.isAssignableFrom(type)) {
/*  51: 79 */       return toByte(value);
/*  52:    */     }
/*  53: 81 */     if (Short.class.isAssignableFrom(type)) {
/*  54: 82 */       return toShort(value);
/*  55:    */     }
/*  56: 84 */     if (Integer.class.isAssignableFrom(type)) {
/*  57: 85 */       return toInteger(value);
/*  58:    */     }
/*  59: 87 */     if (Long.class.isAssignableFrom(type)) {
/*  60: 88 */       return toInteger(value);
/*  61:    */     }
/*  62: 90 */     if (Character.class.isAssignableFrom(type)) {
/*  63: 91 */       return Character.valueOf(value.booleanValue() ? this.characterValueTrue : this.characterValueFalse);
/*  64:    */     }
/*  65: 93 */     if (String.class.isAssignableFrom(type)) {
/*  66: 94 */       return value.booleanValue() ? this.stringValueTrue : this.stringValueFalse;
/*  67:    */     }
/*  68: 96 */     throw unknownUnwrap(type);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public <X> Boolean wrap(X value, WrapperOptions options)
/*  72:    */   {
/*  73:101 */     if (value == null) {
/*  74:102 */       return null;
/*  75:    */     }
/*  76:104 */     if (Boolean.class.isInstance(value)) {
/*  77:105 */       return (Boolean)value;
/*  78:    */     }
/*  79:107 */     if (Number.class.isInstance(value))
/*  80:    */     {
/*  81:108 */       int intValue = ((Number)value).intValue();
/*  82:109 */       return intValue == 0 ? Boolean.FALSE : Boolean.TRUE;
/*  83:    */     }
/*  84:111 */     if (Character.class.isInstance(value)) {
/*  85:112 */       return isTrue(((Character)value).charValue()) ? Boolean.TRUE : Boolean.FALSE;
/*  86:    */     }
/*  87:114 */     if (String.class.isInstance(value)) {
/*  88:115 */       return isTrue(((String)value).charAt(0)) ? Boolean.TRUE : Boolean.FALSE;
/*  89:    */     }
/*  90:117 */     throw unknownWrap(value.getClass());
/*  91:    */   }
/*  92:    */   
/*  93:    */   private boolean isTrue(char charValue)
/*  94:    */   {
/*  95:121 */     return (charValue == this.characterValueTrue) || (charValue == this.characterValueTrueLC);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int toInt(Boolean value)
/*  99:    */   {
/* 100:125 */     return value.booleanValue() ? 1 : 0;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Byte toByte(Boolean value)
/* 104:    */   {
/* 105:130 */     return Byte.valueOf((byte)toInt(value));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Short toShort(Boolean value)
/* 109:    */   {
/* 110:135 */     return Short.valueOf((short)toInt(value));
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Integer toInteger(Boolean value)
/* 114:    */   {
/* 115:140 */     return Integer.valueOf(toInt(value));
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Long toLong(Boolean value)
/* 119:    */   {
/* 120:145 */     return Long.valueOf(toInt(value));
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.BooleanTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */