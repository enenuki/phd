/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class StringVector
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = 4995234972032919748L;
/*   9:    */   protected int m_blocksize;
/*  10:    */   protected String[] m_map;
/*  11: 39 */   protected int m_firstFree = 0;
/*  12:    */   protected int m_mapSize;
/*  13:    */   
/*  14:    */   public StringVector()
/*  15:    */   {
/*  16: 51 */     this.m_blocksize = 8;
/*  17: 52 */     this.m_mapSize = this.m_blocksize;
/*  18: 53 */     this.m_map = new String[this.m_blocksize];
/*  19:    */   }
/*  20:    */   
/*  21:    */   public StringVector(int blocksize)
/*  22:    */   {
/*  23: 64 */     this.m_blocksize = blocksize;
/*  24: 65 */     this.m_mapSize = blocksize;
/*  25: 66 */     this.m_map = new String[blocksize];
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getLength()
/*  29:    */   {
/*  30: 76 */     return this.m_firstFree;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public final int size()
/*  34:    */   {
/*  35: 86 */     return this.m_firstFree;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final void addElement(String value)
/*  39:    */   {
/*  40: 97 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  41:    */     {
/*  42: 99 */       this.m_mapSize += this.m_blocksize;
/*  43:    */       
/*  44:101 */       String[] newMap = new String[this.m_mapSize];
/*  45:    */       
/*  46:103 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  47:    */       
/*  48:105 */       this.m_map = newMap;
/*  49:    */     }
/*  50:108 */     this.m_map[this.m_firstFree] = value;
/*  51:    */     
/*  52:110 */     this.m_firstFree += 1;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String elementAt(int i)
/*  56:    */   {
/*  57:122 */     return this.m_map[i];
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final boolean contains(String s)
/*  61:    */   {
/*  62:135 */     if (null == s) {
/*  63:136 */       return false;
/*  64:    */     }
/*  65:138 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  66:140 */       if (this.m_map[i].equals(s)) {
/*  67:141 */         return true;
/*  68:    */       }
/*  69:    */     }
/*  70:144 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final boolean containsIgnoreCase(String s)
/*  74:    */   {
/*  75:157 */     if (null == s) {
/*  76:158 */       return false;
/*  77:    */     }
/*  78:160 */     for (int i = 0; i < this.m_firstFree; i++) {
/*  79:162 */       if (this.m_map[i].equalsIgnoreCase(s)) {
/*  80:163 */         return true;
/*  81:    */       }
/*  82:    */     }
/*  83:166 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public final void push(String s)
/*  87:    */   {
/*  88:177 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  89:    */     {
/*  90:179 */       this.m_mapSize += this.m_blocksize;
/*  91:    */       
/*  92:181 */       String[] newMap = new String[this.m_mapSize];
/*  93:    */       
/*  94:183 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  95:    */       
/*  96:185 */       this.m_map = newMap;
/*  97:    */     }
/*  98:188 */     this.m_map[this.m_firstFree] = s;
/*  99:    */     
/* 100:190 */     this.m_firstFree += 1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final String pop()
/* 104:    */   {
/* 105:202 */     if (this.m_firstFree <= 0) {
/* 106:203 */       return null;
/* 107:    */     }
/* 108:205 */     this.m_firstFree -= 1;
/* 109:    */     
/* 110:207 */     String s = this.m_map[this.m_firstFree];
/* 111:    */     
/* 112:209 */     this.m_map[this.m_firstFree] = null;
/* 113:    */     
/* 114:211 */     return s;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final String peek()
/* 118:    */   {
/* 119:221 */     return this.m_firstFree <= 0 ? null : this.m_map[(this.m_firstFree - 1)];
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StringVector
 * JD-Core Version:    0.7.0.1
 */