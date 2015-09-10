/*  1:   */ package org.apache.xml.serializer.dom3;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.w3c.dom.DOMStringList;
/*  5:   */ 
/*  6:   */ final class DOMStringListImpl
/*  7:   */   implements DOMStringList
/*  8:   */ {
/*  9:   */   private Vector fStrings;
/* 10:   */   
/* 11:   */   DOMStringListImpl()
/* 12:   */   {
/* 13:42 */     this.fStrings = new Vector();
/* 14:   */   }
/* 15:   */   
/* 16:   */   DOMStringListImpl(Vector params)
/* 17:   */   {
/* 18:49 */     this.fStrings = params;
/* 19:   */   }
/* 20:   */   
/* 21:   */   DOMStringListImpl(String[] params)
/* 22:   */   {
/* 23:56 */     this.fStrings = new Vector();
/* 24:57 */     if (params != null) {
/* 25:58 */       for (int i = 0; i < params.length; i++) {
/* 26:59 */         this.fStrings.add(params[i]);
/* 27:   */       }
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String item(int index)
/* 32:   */   {
/* 33:   */     try
/* 34:   */     {
/* 35:69 */       return (String)this.fStrings.elementAt(index);
/* 36:   */     }
/* 37:   */     catch (ArrayIndexOutOfBoundsException e) {}
/* 38:71 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getLength()
/* 42:   */   {
/* 43:79 */     return this.fStrings.size();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean contains(String param)
/* 47:   */   {
/* 48:86 */     return this.fStrings.contains(param);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void add(String param)
/* 52:   */   {
/* 53:96 */     this.fStrings.add(param);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOMStringListImpl
 * JD-Core Version:    0.7.0.1
 */