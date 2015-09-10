/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.Namespace;
/*   5:    */ import org.dom4j.QName;
/*   6:    */ 
/*   7:    */ public class DefaultAttribute
/*   8:    */   extends FlyweightAttribute
/*   9:    */ {
/*  10:    */   private Element parent;
/*  11:    */   
/*  12:    */   public DefaultAttribute(QName qname)
/*  13:    */   {
/*  14: 28 */     super(qname);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public DefaultAttribute(QName qname, String value)
/*  18:    */   {
/*  19: 32 */     super(qname, value);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DefaultAttribute(Element parent, QName qname, String value)
/*  23:    */   {
/*  24: 36 */     super(qname, value);
/*  25: 37 */     this.parent = parent;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public DefaultAttribute(String name, String value)
/*  29:    */   {
/*  30: 50 */     super(name, value);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public DefaultAttribute(String name, String value, Namespace namespace)
/*  34:    */   {
/*  35: 65 */     super(name, value, namespace);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public DefaultAttribute(Element parent, String name, String value, Namespace namespace)
/*  39:    */   {
/*  40: 83 */     super(name, value, namespace);
/*  41: 84 */     this.parent = parent;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setValue(String value)
/*  45:    */   {
/*  46: 88 */     this.value = value;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Element getParent()
/*  50:    */   {
/*  51: 92 */     return this.parent;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setParent(Element parent)
/*  55:    */   {
/*  56: 96 */     this.parent = parent;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean supportsParent()
/*  60:    */   {
/*  61:100 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isReadOnly()
/*  65:    */   {
/*  66:104 */     return false;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultAttribute
 * JD-Core Version:    0.7.0.1
 */