/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class StringToStringTableVector
/*   4:    */ {
/*   5:    */   private int m_blocksize;
/*   6:    */   private StringToStringTable[] m_map;
/*   7: 38 */   private int m_firstFree = 0;
/*   8:    */   private int m_mapSize;
/*   9:    */   
/*  10:    */   public StringToStringTableVector()
/*  11:    */   {
/*  12: 50 */     this.m_blocksize = 8;
/*  13: 51 */     this.m_mapSize = this.m_blocksize;
/*  14: 52 */     this.m_map = new StringToStringTable[this.m_blocksize];
/*  15:    */   }
/*  16:    */   
/*  17:    */   public StringToStringTableVector(int blocksize)
/*  18:    */   {
/*  19: 63 */     this.m_blocksize = blocksize;
/*  20: 64 */     this.m_mapSize = blocksize;
/*  21: 65 */     this.m_map = new StringToStringTable[blocksize];
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final int getLength()
/*  25:    */   {
/*  26: 75 */     return this.m_firstFree;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final int size()
/*  30:    */   {
/*  31: 85 */     return this.m_firstFree;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final void addElement(StringToStringTable value)
/*  35:    */   {
/*  36: 96 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  37:    */     {
/*  38: 98 */       this.m_mapSize += this.m_blocksize;
/*  39:    */       
/*  40:100 */       StringToStringTable[] newMap = new StringToStringTable[this.m_mapSize];
/*  41:    */       
/*  42:102 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  43:    */       
/*  44:104 */       this.m_map = newMap;
/*  45:    */     }
/*  46:107 */     this.m_map[this.m_firstFree] = value;
/*  47:    */     
/*  48:109 */     this.m_firstFree += 1;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final String get(String key)
/*  52:    */   {
/*  53:124 */     for (int i = this.m_firstFree - 1; i >= 0; i--)
/*  54:    */     {
/*  55:126 */       String nsuri = this.m_map[i].get(key);
/*  56:128 */       if (nsuri != null) {
/*  57:129 */         return nsuri;
/*  58:    */       }
/*  59:    */     }
/*  60:132 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final boolean containsKey(String key)
/*  64:    */   {
/*  65:146 */     for (int i = this.m_firstFree - 1; i >= 0; i--) {
/*  66:148 */       if (this.m_map[i].get(key) != null) {
/*  67:149 */         return true;
/*  68:    */       }
/*  69:    */     }
/*  70:152 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final void removeLastElem()
/*  74:    */   {
/*  75:161 */     if (this.m_firstFree > 0)
/*  76:    */     {
/*  77:163 */       this.m_map[this.m_firstFree] = null;
/*  78:    */       
/*  79:165 */       this.m_firstFree -= 1;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final StringToStringTable elementAt(int i)
/*  84:    */   {
/*  85:178 */     return this.m_map[i];
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final boolean contains(StringToStringTable s)
/*  89:    */   {
/*  90:191 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  91:193 */       if (this.m_map[i].equals(s)) {
/*  92:194 */         return true;
/*  93:    */       }
/*  94:    */     }
/*  95:197 */     return false;
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StringToStringTableVector
 * JD-Core Version:    0.7.0.1
 */