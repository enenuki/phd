/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.Namespace;
/*   5:    */ 
/*   6:    */ public class DefaultNamespace
/*   7:    */   extends Namespace
/*   8:    */ {
/*   9:    */   private Element parent;
/*  10:    */   
/*  11:    */   public DefaultNamespace(String prefix, String uri)
/*  12:    */   {
/*  13: 36 */     super(prefix, uri);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public DefaultNamespace(Element parent, String prefix, String uri)
/*  17:    */   {
/*  18: 50 */     super(prefix, uri);
/*  19: 51 */     this.parent = parent;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected int createHashCode()
/*  23:    */   {
/*  24: 61 */     int hashCode = super.createHashCode();
/*  25: 63 */     if (this.parent != null) {
/*  26: 64 */       hashCode ^= this.parent.hashCode();
/*  27:    */     }
/*  28: 67 */     return hashCode;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean equals(Object object)
/*  32:    */   {
/*  33: 80 */     if ((object instanceof DefaultNamespace))
/*  34:    */     {
/*  35: 81 */       DefaultNamespace that = (DefaultNamespace)object;
/*  36: 83 */       if (that.parent == this.parent) {
/*  37: 84 */         return super.equals(object);
/*  38:    */       }
/*  39:    */     }
/*  40: 88 */     return false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int hashCode()
/*  44:    */   {
/*  45: 92 */     return super.hashCode();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Element getParent()
/*  49:    */   {
/*  50: 96 */     return this.parent;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setParent(Element parent)
/*  54:    */   {
/*  55:100 */     this.parent = parent;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean supportsParent()
/*  59:    */   {
/*  60:104 */     return true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isReadOnly()
/*  64:    */   {
/*  65:108 */     return false;
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultNamespace
 * JD-Core Version:    0.7.0.1
 */