/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.Node;
/*   5:    */ 
/*   6:    */ public class FlyweightEntity
/*   7:    */   extends AbstractEntity
/*   8:    */ {
/*   9:    */   protected String name;
/*  10:    */   protected String text;
/*  11:    */   
/*  12:    */   protected FlyweightEntity() {}
/*  13:    */   
/*  14:    */   public FlyweightEntity(String name)
/*  15:    */   {
/*  16: 53 */     this.name = name;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public FlyweightEntity(String name, String text)
/*  20:    */   {
/*  21: 65 */     this.name = name;
/*  22: 66 */     this.text = text;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getName()
/*  26:    */   {
/*  27: 75 */     return this.name;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getText()
/*  31:    */   {
/*  32: 84 */     return this.text;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setText(String text)
/*  36:    */   {
/*  37: 99 */     if (this.text != null) {
/*  38:100 */       this.text = text;
/*  39:    */     } else {
/*  40:102 */       throw new UnsupportedOperationException("This Entity is read-only. It cannot be modified");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected Node createXPathResult(Element parent)
/*  45:    */   {
/*  46:108 */     return new DefaultEntity(parent, getName(), getText());
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.FlyweightEntity
 * JD-Core Version:    0.7.0.1
 */