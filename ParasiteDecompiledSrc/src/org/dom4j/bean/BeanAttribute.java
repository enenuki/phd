/*  1:   */ package org.dom4j.bean;
/*  2:   */ 
/*  3:   */ import org.dom4j.Element;
/*  4:   */ import org.dom4j.QName;
/*  5:   */ import org.dom4j.tree.AbstractAttribute;
/*  6:   */ 
/*  7:   */ public class BeanAttribute
/*  8:   */   extends AbstractAttribute
/*  9:   */ {
/* 10:   */   private final BeanAttributeList beanList;
/* 11:   */   private final int index;
/* 12:   */   
/* 13:   */   public BeanAttribute(BeanAttributeList beanList, int index)
/* 14:   */   {
/* 15:31 */     this.beanList = beanList;
/* 16:32 */     this.index = index;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public QName getQName()
/* 20:   */   {
/* 21:36 */     return this.beanList.getQName(this.index);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Element getParent()
/* 25:   */   {
/* 26:40 */     return this.beanList.getParent();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getValue()
/* 30:   */   {
/* 31:44 */     Object data = getData();
/* 32:   */     
/* 33:46 */     return data != null ? data.toString() : null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setValue(String data)
/* 37:   */   {
/* 38:50 */     this.beanList.setData(this.index, data);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Object getData()
/* 42:   */   {
/* 43:54 */     return this.beanList.getData(this.index);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setData(Object data)
/* 47:   */   {
/* 48:58 */     this.beanList.setData(this.index, data);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.bean.BeanAttribute
 * JD-Core Version:    0.7.0.1
 */