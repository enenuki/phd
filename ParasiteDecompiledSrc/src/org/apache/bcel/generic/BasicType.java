/*  1:   */ package org.apache.bcel.generic;
/*  2:   */ 
/*  3:   */ public final class BasicType
/*  4:   */   extends Type
/*  5:   */ {
/*  6:   */   BasicType(byte type)
/*  7:   */   {
/*  8:72 */     super(type, org.apache.bcel.Constants.SHORT_TYPE_NAMES[type]);
/*  9:74 */     if ((type < 4) || (type > 12)) {
/* 10:75 */       throw new ClassGenException("Invalid type: " + type);
/* 11:   */     }
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static final BasicType getType(byte type)
/* 15:   */   {
/* 16:79 */     switch (type)
/* 17:   */     {
/* 18:   */     case 12: 
/* 19:80 */       return Type.VOID;
/* 20:   */     case 4: 
/* 21:81 */       return Type.BOOLEAN;
/* 22:   */     case 8: 
/* 23:82 */       return Type.BYTE;
/* 24:   */     case 9: 
/* 25:83 */       return Type.SHORT;
/* 26:   */     case 5: 
/* 27:84 */       return Type.CHAR;
/* 28:   */     case 10: 
/* 29:85 */       return Type.INT;
/* 30:   */     case 11: 
/* 31:86 */       return Type.LONG;
/* 32:   */     case 7: 
/* 33:87 */       return Type.DOUBLE;
/* 34:   */     case 6: 
/* 35:88 */       return Type.FLOAT;
/* 36:   */     }
/* 37:91 */     throw new ClassGenException("Invalid type: " + type);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean equals(Object type)
/* 41:   */   {
/* 42:98 */     return ((BasicType)type).type == this.type;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.BasicType
 * JD-Core Version:    0.7.0.1
 */