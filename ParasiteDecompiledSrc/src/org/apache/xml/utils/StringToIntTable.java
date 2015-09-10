/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class StringToIntTable
/*   4:    */ {
/*   5:    */   public static final int INVALID_KEY = -10000;
/*   6:    */   private int m_blocksize;
/*   7:    */   private String[] m_map;
/*   8:    */   private int[] m_values;
/*   9: 45 */   private int m_firstFree = 0;
/*  10:    */   private int m_mapSize;
/*  11:    */   
/*  12:    */   public StringToIntTable()
/*  13:    */   {
/*  14: 57 */     this.m_blocksize = 8;
/*  15: 58 */     this.m_mapSize = this.m_blocksize;
/*  16: 59 */     this.m_map = new String[this.m_blocksize];
/*  17: 60 */     this.m_values = new int[this.m_blocksize];
/*  18:    */   }
/*  19:    */   
/*  20:    */   public StringToIntTable(int blocksize)
/*  21:    */   {
/*  22: 71 */     this.m_blocksize = blocksize;
/*  23: 72 */     this.m_mapSize = blocksize;
/*  24: 73 */     this.m_map = new String[blocksize];
/*  25: 74 */     this.m_values = new int[this.m_blocksize];
/*  26:    */   }
/*  27:    */   
/*  28:    */   public final int getLength()
/*  29:    */   {
/*  30: 84 */     return this.m_firstFree;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public final void put(String key, int value)
/*  34:    */   {
/*  35: 96 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  36:    */     {
/*  37: 98 */       this.m_mapSize += this.m_blocksize;
/*  38:    */       
/*  39:100 */       String[] newMap = new String[this.m_mapSize];
/*  40:    */       
/*  41:102 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  42:    */       
/*  43:104 */       this.m_map = newMap;
/*  44:    */       
/*  45:106 */       int[] newValues = new int[this.m_mapSize];
/*  46:    */       
/*  47:108 */       System.arraycopy(this.m_values, 0, newValues, 0, this.m_firstFree + 1);
/*  48:    */       
/*  49:110 */       this.m_values = newValues;
/*  50:    */     }
/*  51:113 */     this.m_map[this.m_firstFree] = key;
/*  52:114 */     this.m_values[this.m_firstFree] = value;
/*  53:    */     
/*  54:116 */     this.m_firstFree += 1;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final int get(String key)
/*  58:    */   {
/*  59:130 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  60:132 */       if (this.m_map[i].equals(key)) {
/*  61:133 */         return this.m_values[i];
/*  62:    */       }
/*  63:    */     }
/*  64:136 */     return -10000;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final int getIgnoreCase(String key)
/*  68:    */   {
/*  69:149 */     if (null == key) {
/*  70:150 */       return -10000;
/*  71:    */     }
/*  72:152 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  73:154 */       if (this.m_map[i].equalsIgnoreCase(key)) {
/*  74:155 */         return this.m_values[i];
/*  75:    */       }
/*  76:    */     }
/*  77:158 */     return -10000;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final boolean contains(String key)
/*  81:    */   {
/*  82:171 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  83:173 */       if (this.m_map[i].equals(key)) {
/*  84:174 */         return true;
/*  85:    */       }
/*  86:    */     }
/*  87:177 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final String[] keys()
/*  91:    */   {
/*  92:187 */     String[] keysArr = new String[this.m_firstFree];
/*  93:189 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  94:191 */       keysArr[i] = this.m_map[i];
/*  95:    */     }
/*  96:194 */     return keysArr;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StringToIntTable
 * JD-Core Version:    0.7.0.1
 */