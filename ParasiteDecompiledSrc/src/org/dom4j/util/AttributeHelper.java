/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import org.dom4j.Attribute;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.dom4j.QName;
/*  6:   */ 
/*  7:   */ public class AttributeHelper
/*  8:   */ {
/*  9:   */   public static boolean booleanValue(Element element, String attributeName)
/* 10:   */   {
/* 11:28 */     return booleanValue(element.attribute(attributeName));
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static boolean booleanValue(Element element, QName attributeQName)
/* 15:   */   {
/* 16:32 */     return booleanValue(element.attribute(attributeQName));
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected static boolean booleanValue(Attribute attribute)
/* 20:   */   {
/* 21:36 */     if (attribute == null) {
/* 22:37 */       return false;
/* 23:   */     }
/* 24:40 */     Object value = attribute.getData();
/* 25:42 */     if (value == null) {
/* 26:43 */       return false;
/* 27:   */     }
/* 28:44 */     if ((value instanceof Boolean))
/* 29:   */     {
/* 30:45 */       Boolean b = (Boolean)value;
/* 31:   */       
/* 32:47 */       return b.booleanValue();
/* 33:   */     }
/* 34:49 */     return "true".equalsIgnoreCase(value.toString());
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.AttributeHelper
 * JD-Core Version:    0.7.0.1
 */