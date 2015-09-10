/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import org.dom4j.QName;
/*  4:   */ import org.dom4j.tree.DefaultAttribute;
/*  5:   */ 
/*  6:   */ public class UserDataAttribute
/*  7:   */   extends DefaultAttribute
/*  8:   */ {
/*  9:   */   private Object data;
/* 10:   */   
/* 11:   */   public UserDataAttribute(QName qname)
/* 12:   */   {
/* 13:30 */     super(qname);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public UserDataAttribute(QName qname, String text)
/* 17:   */   {
/* 18:34 */     super(qname, text);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object getData()
/* 22:   */   {
/* 23:38 */     return this.data;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setData(Object data)
/* 27:   */   {
/* 28:42 */     this.data = data;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.UserDataAttribute
 * JD-Core Version:    0.7.0.1
 */