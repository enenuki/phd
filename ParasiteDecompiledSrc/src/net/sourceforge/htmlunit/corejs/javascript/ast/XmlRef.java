/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public abstract class XmlRef
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   protected Name namespace;
/*   7: 69 */   protected int atPos = -1;
/*   8: 70 */   protected int colonPos = -1;
/*   9:    */   
/*  10:    */   public XmlRef() {}
/*  11:    */   
/*  12:    */   public XmlRef(int pos)
/*  13:    */   {
/*  14: 76 */     super(pos);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public XmlRef(int pos, int len)
/*  18:    */   {
/*  19: 80 */     super(pos, len);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Name getNamespace()
/*  23:    */   {
/*  24: 87 */     return this.namespace;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setNamespace(Name namespace)
/*  28:    */   {
/*  29: 95 */     this.namespace = namespace;
/*  30: 96 */     if (namespace != null) {
/*  31: 97 */       namespace.setParent(this);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isAttributeAccess()
/*  36:    */   {
/*  37:104 */     return this.atPos >= 0;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getAtPos()
/*  41:    */   {
/*  42:112 */     return this.atPos;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setAtPos(int atPos)
/*  46:    */   {
/*  47:119 */     this.atPos = atPos;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getColonPos()
/*  51:    */   {
/*  52:127 */     return this.colonPos;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setColonPos(int colonPos)
/*  56:    */   {
/*  57:134 */     this.colonPos = colonPos;
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlRef
 * JD-Core Version:    0.7.0.1
 */