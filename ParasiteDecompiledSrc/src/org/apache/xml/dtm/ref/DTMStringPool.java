/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.xml.utils.IntVector;
/*   6:    */ 
/*   7:    */ public class DTMStringPool
/*   8:    */ {
/*   9:    */   Vector m_intToString;
/*  10:    */   static final int HASHPRIME = 101;
/*  11: 61 */   int[] m_hashStart = new int[101];
/*  12:    */   IntVector m_hashChain;
/*  13:    */   public static final int NULL = -1;
/*  14:    */   
/*  15:    */   public DTMStringPool(int chainSize)
/*  16:    */   {
/*  17: 72 */     this.m_intToString = new Vector();
/*  18: 73 */     this.m_hashChain = new IntVector(chainSize);
/*  19: 74 */     removeAllElements();
/*  20:    */     
/*  21:    */ 
/*  22: 77 */     stringToIndex("");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public DTMStringPool()
/*  26:    */   {
/*  27: 82 */     this(512);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void removeAllElements()
/*  31:    */   {
/*  32: 87 */     this.m_intToString.removeAllElements();
/*  33: 88 */     for (int i = 0; i < 101; i++) {
/*  34: 89 */       this.m_hashStart[i] = -1;
/*  35:    */     }
/*  36: 90 */     this.m_hashChain.removeAllElements();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String indexToString(int i)
/*  40:    */     throws ArrayIndexOutOfBoundsException
/*  41:    */   {
/*  42:100 */     if (i == -1) {
/*  43:100 */       return null;
/*  44:    */     }
/*  45:101 */     return (String)this.m_intToString.elementAt(i);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int stringToIndex(String s)
/*  49:    */   {
/*  50:107 */     if (s == null) {
/*  51:107 */       return -1;
/*  52:    */     }
/*  53:109 */     int hashslot = s.hashCode() % 101;
/*  54:110 */     if (hashslot < 0) {
/*  55:110 */       hashslot = -hashslot;
/*  56:    */     }
/*  57:113 */     int hashlast = this.m_hashStart[hashslot];
/*  58:114 */     int hashcandidate = hashlast;
/*  59:115 */     while (hashcandidate != -1)
/*  60:    */     {
/*  61:117 */       if (this.m_intToString.elementAt(hashcandidate).equals(s)) {
/*  62:118 */         return hashcandidate;
/*  63:    */       }
/*  64:120 */       hashlast = hashcandidate;
/*  65:121 */       hashcandidate = this.m_hashChain.elementAt(hashcandidate);
/*  66:    */     }
/*  67:125 */     int newIndex = this.m_intToString.size();
/*  68:126 */     this.m_intToString.addElement(s);
/*  69:    */     
/*  70:128 */     this.m_hashChain.addElement(-1);
/*  71:129 */     if (hashlast == -1) {
/*  72:130 */       this.m_hashStart[hashslot] = newIndex;
/*  73:    */     } else {
/*  74:132 */       this.m_hashChain.setElementAt(newIndex, hashlast);
/*  75:    */     }
/*  76:134 */     return newIndex;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void main(String[] args)
/*  80:    */   {
/*  81:143 */     String[] word = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five", "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty", "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five", "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine" };
/*  82:    */     
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:154 */     DTMStringPool pool = new DTMStringPool();
/*  93:    */     
/*  94:156 */     System.out.println("If no complaints are printed below, we passed initial test.");
/*  95:158 */     for (int pass = 0; pass <= 1; pass++)
/*  96:    */     {
/*  97:162 */       for (int i = 0; i < word.length; i++)
/*  98:    */       {
/*  99:164 */         int j = pool.stringToIndex(word[i]);
/* 100:165 */         if (j != i) {
/* 101:166 */           System.out.println("\tMismatch populating pool: assigned " + j + " for create " + i);
/* 102:    */         }
/* 103:    */       }
/* 104:170 */       for (i = 0; i < word.length; i++)
/* 105:    */       {
/* 106:172 */         int j = pool.stringToIndex(word[i]);
/* 107:173 */         if (j != i) {
/* 108:174 */           System.out.println("\tMismatch in stringToIndex: returned " + j + " for lookup " + i);
/* 109:    */         }
/* 110:    */       }
/* 111:178 */       for (i = 0; i < word.length; i++)
/* 112:    */       {
/* 113:180 */         String w = pool.indexToString(i);
/* 114:181 */         if (!word[i].equals(w)) {
/* 115:182 */           System.out.println("\tMismatch in indexToString: returned" + w + " for lookup " + i);
/* 116:    */         }
/* 117:    */       }
/* 118:186 */       pool.removeAllElements();
/* 119:    */       
/* 120:188 */       System.out.println("\nPass " + pass + " complete\n");
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMStringPool
 * JD-Core Version:    0.7.0.1
 */