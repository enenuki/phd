/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.dom4j.Element;
/*   5:    */ 
/*   6:    */ public class DefaultProcessingInstruction
/*   7:    */   extends FlyweightProcessingInstruction
/*   8:    */ {
/*   9:    */   private Element parent;
/*  10:    */   
/*  11:    */   public DefaultProcessingInstruction(String target, Map values)
/*  12:    */   {
/*  13: 40 */     super(target, values);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public DefaultProcessingInstruction(String target, String values)
/*  17:    */   {
/*  18: 54 */     super(target, values);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DefaultProcessingInstruction(Element parent, String target, String values)
/*  22:    */   {
/*  23: 71 */     super(target, values);
/*  24: 72 */     this.parent = parent;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setTarget(String target)
/*  28:    */   {
/*  29: 76 */     this.target = target;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setText(String text)
/*  33:    */   {
/*  34: 80 */     this.text = text;
/*  35: 81 */     this.values = parseValues(text);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setValues(Map values)
/*  39:    */   {
/*  40: 85 */     this.values = values;
/*  41: 86 */     this.text = toString(values);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setValue(String name, String value)
/*  45:    */   {
/*  46: 90 */     this.values.put(name, value);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Element getParent()
/*  50:    */   {
/*  51: 94 */     return this.parent;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setParent(Element parent)
/*  55:    */   {
/*  56: 98 */     this.parent = parent;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean supportsParent()
/*  60:    */   {
/*  61:102 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isReadOnly()
/*  65:    */   {
/*  66:106 */     return false;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */