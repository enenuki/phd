/*  1:   */ package org.apache.xml.dtm.ref;
/*  2:   */ 
/*  3:   */ import java.util.Hashtable;
/*  4:   */ import java.util.Vector;
/*  5:   */ 
/*  6:   */ public class CustomStringPool
/*  7:   */   extends DTMStringPool
/*  8:   */ {
/*  9:52 */   final Hashtable m_stringToInt = new Hashtable();
/* 10:   */   public static final int NULL = -1;
/* 11:   */   
/* 12:   */   public void removeAllElements()
/* 13:   */   {
/* 14:67 */     this.m_intToString.removeAllElements();
/* 15:68 */     if (this.m_stringToInt != null) {
/* 16:69 */       this.m_stringToInt.clear();
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String indexToString(int i)
/* 21:   */     throws ArrayIndexOutOfBoundsException
/* 22:   */   {
/* 23:79 */     return (String)this.m_intToString.elementAt(i);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int stringToIndex(String s)
/* 27:   */   {
/* 28:85 */     if (s == null) {
/* 29:85 */       return -1;
/* 30:   */     }
/* 31:86 */     Integer iobj = (Integer)this.m_stringToInt.get(s);
/* 32:87 */     if (iobj == null)
/* 33:   */     {
/* 34:88 */       this.m_intToString.addElement(s);
/* 35:89 */       iobj = new Integer(this.m_intToString.size());
/* 36:90 */       this.m_stringToInt.put(s, iobj);
/* 37:   */     }
/* 38:92 */     return iobj.intValue();
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.CustomStringPool
 * JD-Core Version:    0.7.0.1
 */