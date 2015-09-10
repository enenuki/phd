/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class StringToStringTable
/*   4:    */ {
/*   5:    */   private int m_blocksize;
/*   6:    */   private String[] m_map;
/*   7: 38 */   private int m_firstFree = 0;
/*   8:    */   private int m_mapSize;
/*   9:    */   
/*  10:    */   public StringToStringTable()
/*  11:    */   {
/*  12: 50 */     this.m_blocksize = 16;
/*  13: 51 */     this.m_mapSize = this.m_blocksize;
/*  14: 52 */     this.m_map = new String[this.m_blocksize];
/*  15:    */   }
/*  16:    */   
/*  17:    */   public StringToStringTable(int blocksize)
/*  18:    */   {
/*  19: 63 */     this.m_blocksize = blocksize;
/*  20: 64 */     this.m_mapSize = blocksize;
/*  21: 65 */     this.m_map = new String[blocksize];
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final int getLength()
/*  25:    */   {
/*  26: 75 */     return this.m_firstFree;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final void put(String key, String value)
/*  30:    */   {
/*  31: 89 */     if (this.m_firstFree + 2 >= this.m_mapSize)
/*  32:    */     {
/*  33: 91 */       this.m_mapSize += this.m_blocksize;
/*  34:    */       
/*  35: 93 */       String[] newMap = new String[this.m_mapSize];
/*  36:    */       
/*  37: 95 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  38:    */       
/*  39: 97 */       this.m_map = newMap;
/*  40:    */     }
/*  41:100 */     this.m_map[this.m_firstFree] = key;
/*  42:    */     
/*  43:102 */     this.m_firstFree += 1;
/*  44:    */     
/*  45:104 */     this.m_map[this.m_firstFree] = value;
/*  46:    */     
/*  47:106 */     this.m_firstFree += 1;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final String get(String key)
/*  51:    */   {
/*  52:119 */     for (int i = 0; i < this.m_firstFree; i += 2) {
/*  53:121 */       if (this.m_map[i].equals(key)) {
/*  54:122 */         return this.m_map[(i + 1)];
/*  55:    */       }
/*  56:    */     }
/*  57:125 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final void remove(String key)
/*  61:    */   {
/*  62:136 */     for (int i = 0; i < this.m_firstFree; i += 2) {
/*  63:138 */       if (this.m_map[i].equals(key))
/*  64:    */       {
/*  65:140 */         if (i + 2 < this.m_firstFree) {
/*  66:141 */           System.arraycopy(this.m_map, i + 2, this.m_map, i, this.m_firstFree - (i + 2));
/*  67:    */         }
/*  68:143 */         this.m_firstFree -= 2;
/*  69:144 */         this.m_map[this.m_firstFree] = null;
/*  70:145 */         this.m_map[(this.m_firstFree + 1)] = null;
/*  71:    */         
/*  72:147 */         break;
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final String getIgnoreCase(String key)
/*  78:    */   {
/*  79:162 */     if (null == key) {
/*  80:163 */       return null;
/*  81:    */     }
/*  82:165 */     for (int i = 0; i < this.m_firstFree; i += 2) {
/*  83:167 */       if (this.m_map[i].equalsIgnoreCase(key)) {
/*  84:168 */         return this.m_map[(i + 1)];
/*  85:    */       }
/*  86:    */     }
/*  87:171 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final String getByValue(String val)
/*  91:    */   {
/*  92:184 */     for (int i = 1; i < this.m_firstFree; i += 2) {
/*  93:186 */       if (this.m_map[i].equals(val)) {
/*  94:187 */         return this.m_map[(i - 1)];
/*  95:    */       }
/*  96:    */     }
/*  97:190 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final String elementAt(int i)
/* 101:    */   {
/* 102:202 */     return this.m_map[i];
/* 103:    */   }
/* 104:    */   
/* 105:    */   public final boolean contains(String key)
/* 106:    */   {
/* 107:215 */     for (int i = 0; i < this.m_firstFree; i += 2) {
/* 108:217 */       if (this.m_map[i].equals(key)) {
/* 109:218 */         return true;
/* 110:    */       }
/* 111:    */     }
/* 112:221 */     return false;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public final boolean containsValue(String val)
/* 116:    */   {
/* 117:234 */     for (int i = 1; i < this.m_firstFree; i += 2) {
/* 118:236 */       if (this.m_map[i].equals(val)) {
/* 119:237 */         return true;
/* 120:    */       }
/* 121:    */     }
/* 122:240 */     return false;
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StringToStringTable
 * JD-Core Version:    0.7.0.1
 */