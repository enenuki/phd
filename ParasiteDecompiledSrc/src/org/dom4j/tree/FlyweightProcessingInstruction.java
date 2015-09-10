/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.dom4j.Element;
/*   6:    */ import org.dom4j.Node;
/*   7:    */ 
/*   8:    */ public class FlyweightProcessingInstruction
/*   9:    */   extends AbstractProcessingInstruction
/*  10:    */ {
/*  11:    */   protected String target;
/*  12:    */   protected String text;
/*  13:    */   protected Map values;
/*  14:    */   
/*  15:    */   public FlyweightProcessingInstruction() {}
/*  16:    */   
/*  17:    */   public FlyweightProcessingInstruction(String target, Map values)
/*  18:    */   {
/*  19: 58 */     this.target = target;
/*  20: 59 */     this.values = values;
/*  21: 60 */     this.text = toString(values);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public FlyweightProcessingInstruction(String target, String text)
/*  25:    */   {
/*  26: 74 */     this.target = target;
/*  27: 75 */     this.text = text;
/*  28: 76 */     this.values = parseValues(text);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getTarget()
/*  32:    */   {
/*  33: 80 */     return this.target;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setTarget(String target)
/*  37:    */   {
/*  38: 84 */     throw new UnsupportedOperationException("This PI is read-only and cannot be modified");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getText()
/*  42:    */   {
/*  43: 89 */     return this.text;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getValue(String name)
/*  47:    */   {
/*  48: 93 */     String answer = (String)this.values.get(name);
/*  49: 95 */     if (answer == null) {
/*  50: 96 */       return "";
/*  51:    */     }
/*  52: 99 */     return answer;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Map getValues()
/*  56:    */   {
/*  57:103 */     return Collections.unmodifiableMap(this.values);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected Node createXPathResult(Element parent)
/*  61:    */   {
/*  62:107 */     return new DefaultProcessingInstruction(parent, getTarget(), getText());
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.FlyweightProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */