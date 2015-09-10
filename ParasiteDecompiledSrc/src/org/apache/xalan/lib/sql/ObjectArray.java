/*   1:    */ package org.apache.xalan.lib.sql;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class ObjectArray
/*   7:    */ {
/*   8: 36 */   private int m_minArraySize = 10;
/*   9: 40 */   private Vector m_Arrays = new Vector(200);
/*  10:    */   private _ObjectArray m_currentArray;
/*  11:    */   private int m_nextSlot;
/*  12:    */   
/*  13:    */   public ObjectArray()
/*  14:    */   {
/*  15: 62 */     init(10);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ObjectArray(int minArraySize)
/*  19:    */   {
/*  20: 70 */     init(minArraySize);
/*  21:    */   }
/*  22:    */   
/*  23:    */   private void init(int size)
/*  24:    */   {
/*  25: 79 */     this.m_minArraySize = size;
/*  26: 80 */     this.m_currentArray = new _ObjectArray(this.m_minArraySize);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Object getAt(int idx)
/*  30:    */   {
/*  31: 89 */     int arrayIndx = idx / this.m_minArraySize;
/*  32: 90 */     int arrayOffset = idx - arrayIndx * this.m_minArraySize;
/*  33: 95 */     if (arrayIndx < this.m_Arrays.size())
/*  34:    */     {
/*  35: 97 */       _ObjectArray a = (_ObjectArray)this.m_Arrays.elementAt(arrayIndx);
/*  36: 98 */       return a.objects[arrayOffset];
/*  37:    */     }
/*  38:107 */     return this.m_currentArray.objects[arrayOffset];
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setAt(int idx, Object obj)
/*  42:    */   {
/*  43:118 */     int arrayIndx = idx / this.m_minArraySize;
/*  44:119 */     int arrayOffset = idx - arrayIndx * this.m_minArraySize;
/*  45:124 */     if (arrayIndx < this.m_Arrays.size())
/*  46:    */     {
/*  47:126 */       _ObjectArray a = (_ObjectArray)this.m_Arrays.elementAt(arrayIndx);
/*  48:127 */       a.objects[arrayOffset] = obj;
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52:136 */       this.m_currentArray.objects[arrayOffset] = obj;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int append(Object o)
/*  57:    */   {
/*  58:148 */     if (this.m_nextSlot >= this.m_minArraySize)
/*  59:    */     {
/*  60:150 */       this.m_Arrays.addElement(this.m_currentArray);
/*  61:151 */       this.m_nextSlot = 0;
/*  62:152 */       this.m_currentArray = new _ObjectArray(this.m_minArraySize);
/*  63:    */     }
/*  64:155 */     this.m_currentArray.objects[this.m_nextSlot] = o;
/*  65:    */     
/*  66:157 */     int pos = this.m_Arrays.size() * this.m_minArraySize + this.m_nextSlot;
/*  67:    */     
/*  68:159 */     this.m_nextSlot += 1;
/*  69:    */     
/*  70:161 */     return pos;
/*  71:    */   }
/*  72:    */   
/*  73:    */   class _ObjectArray
/*  74:    */   {
/*  75:    */     public Object[] objects;
/*  76:    */     
/*  77:    */     public _ObjectArray(int size)
/*  78:    */     {
/*  79:177 */       this.objects = new Object[size];
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void main(String[] args)
/*  84:    */   {
/*  85:187 */     String[] word = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five", "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty", "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five", "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine" };
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:198 */     ObjectArray m_ObjectArray = new ObjectArray();
/*  97:200 */     for (int x = 0; x < word.length; x++) {
/*  98:202 */       System.out.print(" - " + m_ObjectArray.append(word[x]));
/*  99:    */     }
/* 100:205 */     System.out.println("\n");
/* 101:207 */     for (int x = 0; x < word.length; x++)
/* 102:    */     {
/* 103:209 */       String s = (String)m_ObjectArray.getAt(x);
/* 104:210 */       System.out.println(s);
/* 105:    */     }
/* 106:214 */     System.out.println((String)m_ObjectArray.getAt(5));
/* 107:215 */     System.out.println((String)m_ObjectArray.getAt(10));
/* 108:216 */     System.out.println((String)m_ObjectArray.getAt(20));
/* 109:217 */     System.out.println((String)m_ObjectArray.getAt(2));
/* 110:218 */     System.out.println((String)m_ObjectArray.getAt(15));
/* 111:219 */     System.out.println((String)m_ObjectArray.getAt(30));
/* 112:220 */     System.out.println((String)m_ObjectArray.getAt(6));
/* 113:221 */     System.out.println((String)m_ObjectArray.getAt(8));
/* 114:    */     
/* 115:    */ 
/* 116:224 */     System.out.println((String)m_ObjectArray.getAt(40));
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.ObjectArray
 * JD-Core Version:    0.7.0.1
 */