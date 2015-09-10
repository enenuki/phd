/*   1:    */ package org.apache.commons.collections.comparators;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.BitSet;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ 
/*  10:    */ public class ComparatorChain
/*  11:    */   implements Comparator, Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -721644942746081630L;
/*  14: 63 */   protected List comparatorChain = null;
/*  15: 65 */   protected BitSet orderingBits = null;
/*  16: 67 */   protected boolean isLocked = false;
/*  17:    */   
/*  18:    */   public ComparatorChain()
/*  19:    */   {
/*  20: 77 */     this(new ArrayList(), new BitSet());
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ComparatorChain(Comparator comparator)
/*  24:    */   {
/*  25: 87 */     this(comparator, false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ComparatorChain(Comparator comparator, boolean reverse)
/*  29:    */   {
/*  30: 98 */     this.comparatorChain = new ArrayList();
/*  31: 99 */     this.comparatorChain.add(comparator);
/*  32:100 */     this.orderingBits = new BitSet(1);
/*  33:101 */     if (reverse == true) {
/*  34:102 */       this.orderingBits.set(0);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ComparatorChain(List list)
/*  39:    */   {
/*  40:115 */     this(list, new BitSet(list.size()));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ComparatorChain(List list, BitSet bits)
/*  44:    */   {
/*  45:134 */     this.comparatorChain = list;
/*  46:135 */     this.orderingBits = bits;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void addComparator(Comparator comparator)
/*  50:    */   {
/*  51:146 */     addComparator(comparator, false);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addComparator(Comparator comparator, boolean reverse)
/*  55:    */   {
/*  56:157 */     checkLocked();
/*  57:    */     
/*  58:159 */     this.comparatorChain.add(comparator);
/*  59:160 */     if (reverse == true) {
/*  60:161 */       this.orderingBits.set(this.comparatorChain.size() - 1);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setComparator(int index, Comparator comparator)
/*  65:    */     throws IndexOutOfBoundsException
/*  66:    */   {
/*  67:176 */     setComparator(index, comparator, false);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setComparator(int index, Comparator comparator, boolean reverse)
/*  71:    */   {
/*  72:188 */     checkLocked();
/*  73:    */     
/*  74:190 */     this.comparatorChain.set(index, comparator);
/*  75:191 */     if (reverse == true) {
/*  76:192 */       this.orderingBits.set(index);
/*  77:    */     } else {
/*  78:194 */       this.orderingBits.clear(index);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setForwardSort(int index)
/*  83:    */   {
/*  84:206 */     checkLocked();
/*  85:207 */     this.orderingBits.clear(index);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setReverseSort(int index)
/*  89:    */   {
/*  90:217 */     checkLocked();
/*  91:218 */     this.orderingBits.set(index);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int size()
/*  95:    */   {
/*  96:227 */     return this.comparatorChain.size();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isLocked()
/* 100:    */   {
/* 101:239 */     return this.isLocked;
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void checkLocked()
/* 105:    */   {
/* 106:244 */     if (this.isLocked == true) {
/* 107:245 */       throw new UnsupportedOperationException("Comparator ordering cannot be changed after the first comparison is performed");
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void checkChainIntegrity()
/* 112:    */   {
/* 113:250 */     if (this.comparatorChain.size() == 0) {
/* 114:251 */       throw new UnsupportedOperationException("ComparatorChains must contain at least one Comparator");
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int compare(Object o1, Object o2)
/* 119:    */     throws UnsupportedOperationException
/* 120:    */   {
/* 121:268 */     if (!this.isLocked)
/* 122:    */     {
/* 123:269 */       checkChainIntegrity();
/* 124:270 */       this.isLocked = true;
/* 125:    */     }
/* 126:274 */     Iterator comparators = this.comparatorChain.iterator();
/* 127:275 */     for (int comparatorIndex = 0; comparators.hasNext(); comparatorIndex++)
/* 128:    */     {
/* 129:277 */       Comparator comparator = (Comparator)comparators.next();
/* 130:278 */       int retval = comparator.compare(o1, o2);
/* 131:279 */       if (retval != 0)
/* 132:    */       {
/* 133:281 */         if (this.orderingBits.get(comparatorIndex) == true) {
/* 134:282 */           if (-2147483648 == retval) {
/* 135:283 */             retval = 2147483647;
/* 136:    */           } else {
/* 137:285 */             retval *= -1;
/* 138:    */           }
/* 139:    */         }
/* 140:289 */         return retval;
/* 141:    */       }
/* 142:    */     }
/* 143:295 */     return 0;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int hashCode()
/* 147:    */   {
/* 148:307 */     int hash = 0;
/* 149:308 */     if (null != this.comparatorChain) {
/* 150:309 */       hash ^= this.comparatorChain.hashCode();
/* 151:    */     }
/* 152:311 */     if (null != this.orderingBits) {
/* 153:312 */       hash ^= this.orderingBits.hashCode();
/* 154:    */     }
/* 155:314 */     return hash;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean equals(Object object)
/* 159:    */   {
/* 160:334 */     if (this == object) {
/* 161:335 */       return true;
/* 162:    */     }
/* 163:336 */     if (null == object) {
/* 164:337 */       return false;
/* 165:    */     }
/* 166:338 */     if (object.getClass().equals(getClass()))
/* 167:    */     {
/* 168:339 */       ComparatorChain chain = (ComparatorChain)object;
/* 169:340 */       return (null == this.orderingBits ? null == chain.orderingBits : this.orderingBits.equals(chain.orderingBits)) && (null == this.comparatorChain ? null == chain.comparatorChain : this.comparatorChain.equals(chain.comparatorChain));
/* 170:    */     }
/* 171:343 */     return false;
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.ComparatorChain
 * JD-Core Version:    0.7.0.1
 */