/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.DOM;
/*  4:   */ import org.xml.sax.AttributeList;
/*  5:   */ 
/*  6:   */ public final class Attributes
/*  7:   */   implements AttributeList
/*  8:   */ {
/*  9:   */   private int _element;
/* 10:   */   private DOM _document;
/* 11:   */   
/* 12:   */   public Attributes(DOM document, int element)
/* 13:   */   {
/* 14:36 */     this._element = element;
/* 15:37 */     this._document = document;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getLength()
/* 19:   */   {
/* 20:41 */     return 0;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName(int i)
/* 24:   */   {
/* 25:45 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getType(int i)
/* 29:   */   {
/* 30:49 */     return null;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getType(String name)
/* 34:   */   {
/* 35:53 */     return null;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getValue(int i)
/* 39:   */   {
/* 40:57 */     return null;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getValue(String name)
/* 44:   */   {
/* 45:61 */     return null;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.Attributes
 * JD-Core Version:    0.7.0.1
 */