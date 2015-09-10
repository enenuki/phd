/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import org.dom4j.DocumentFactory;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.dom4j.QName;
/*  6:   */ import org.dom4j.tree.DefaultElement;
/*  7:   */ 
/*  8:   */ public class UserDataElement
/*  9:   */   extends DefaultElement
/* 10:   */ {
/* 11:   */   private Object data;
/* 12:   */   
/* 13:   */   public UserDataElement(String name)
/* 14:   */   {
/* 15:31 */     super(name);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public UserDataElement(QName qname)
/* 19:   */   {
/* 20:35 */     super(qname);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object getData()
/* 24:   */   {
/* 25:39 */     return this.data;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setData(Object data)
/* 29:   */   {
/* 30:43 */     this.data = data;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toString()
/* 34:   */   {
/* 35:47 */     return super.toString() + " userData: " + this.data;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object clone()
/* 39:   */   {
/* 40:51 */     UserDataElement answer = (UserDataElement)super.clone();
/* 41:53 */     if (answer != this) {
/* 42:54 */       answer.data = getCopyOfUserData();
/* 43:   */     }
/* 44:57 */     return answer;
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected Object getCopyOfUserData()
/* 48:   */   {
/* 49:71 */     return this.data;
/* 50:   */   }
/* 51:   */   
/* 52:   */   protected Element createElement(String name)
/* 53:   */   {
/* 54:75 */     Element answer = getDocumentFactory().createElement(name);
/* 55:76 */     answer.setData(getCopyOfUserData());
/* 56:   */     
/* 57:78 */     return answer;
/* 58:   */   }
/* 59:   */   
/* 60:   */   protected Element createElement(QName qName)
/* 61:   */   {
/* 62:82 */     Element answer = getDocumentFactory().createElement(qName);
/* 63:83 */     answer.setData(getCopyOfUserData());
/* 64:   */     
/* 65:85 */     return answer;
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.UserDataElement
 * JD-Core Version:    0.7.0.1
 */