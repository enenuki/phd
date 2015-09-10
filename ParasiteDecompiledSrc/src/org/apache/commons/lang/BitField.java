/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ public class BitField
/*   4:    */ {
/*   5:    */   private final int _mask;
/*   6:    */   private final int _shift_count;
/*   7:    */   
/*   8:    */   public BitField(int mask)
/*   9:    */   {
/*  10: 45 */     this._mask = mask;
/*  11: 46 */     int count = 0;
/*  12: 47 */     int bit_pattern = mask;
/*  13: 49 */     if (bit_pattern != 0) {
/*  14: 50 */       while ((bit_pattern & 0x1) == 0)
/*  15:    */       {
/*  16: 51 */         count++;
/*  17: 52 */         bit_pattern >>= 1;
/*  18:    */       }
/*  19:    */     }
/*  20: 55 */     this._shift_count = count;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int getValue(int holder)
/*  24:    */   {
/*  25: 73 */     return getRawValue(holder) >> this._shift_count;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public short getShortValue(short holder)
/*  29:    */   {
/*  30: 91 */     return (short)getValue(holder);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getRawValue(int holder)
/*  34:    */   {
/*  35:102 */     return holder & this._mask;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public short getShortRawValue(short holder)
/*  39:    */   {
/*  40:113 */     return (short)getRawValue(holder);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isSet(int holder)
/*  44:    */   {
/*  45:130 */     return (holder & this._mask) != 0;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean isAllSet(int holder)
/*  49:    */   {
/*  50:146 */     return (holder & this._mask) == this._mask;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int setValue(int holder, int value)
/*  54:    */   {
/*  55:160 */     return holder & (this._mask ^ 0xFFFFFFFF) | value << this._shift_count & this._mask;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public short setShortValue(short holder, short value)
/*  59:    */   {
/*  60:174 */     return (short)setValue(holder, value);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int clear(int holder)
/*  64:    */   {
/*  65:186 */     return holder & (this._mask ^ 0xFFFFFFFF);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public short clearShort(short holder)
/*  69:    */   {
/*  70:198 */     return (short)clear(holder);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public byte clearByte(byte holder)
/*  74:    */   {
/*  75:211 */     return (byte)clear(holder);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int set(int holder)
/*  79:    */   {
/*  80:223 */     return holder | this._mask;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public short setShort(short holder)
/*  84:    */   {
/*  85:235 */     return (short)set(holder);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public byte setByte(byte holder)
/*  89:    */   {
/*  90:248 */     return (byte)set(holder);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int setBoolean(int holder, boolean flag)
/*  94:    */   {
/*  95:261 */     return flag ? set(holder) : clear(holder);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public short setShortBoolean(short holder, boolean flag)
/*  99:    */   {
/* 100:274 */     return flag ? setShort(holder) : clearShort(holder);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public byte setByteBoolean(byte holder, boolean flag)
/* 104:    */   {
/* 105:287 */     return flag ? setByte(holder) : clearByte(holder);
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.BitField
 * JD-Core Version:    0.7.0.1
 */