/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ public final class ExtendedType
/*   4:    */ {
/*   5:    */   private int nodetype;
/*   6:    */   private String namespace;
/*   7:    */   private String localName;
/*   8:    */   private int hash;
/*   9:    */   
/*  10:    */   public ExtendedType(int nodetype, String namespace, String localName)
/*  11:    */   {
/*  12: 44 */     this.nodetype = nodetype;
/*  13: 45 */     this.namespace = namespace;
/*  14: 46 */     this.localName = localName;
/*  15: 47 */     this.hash = (nodetype + namespace.hashCode() + localName.hashCode());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ExtendedType(int nodetype, String namespace, String localName, int hash)
/*  19:    */   {
/*  20: 61 */     this.nodetype = nodetype;
/*  21: 62 */     this.namespace = namespace;
/*  22: 63 */     this.localName = localName;
/*  23: 64 */     this.hash = hash;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected void redefine(int nodetype, String namespace, String localName)
/*  27:    */   {
/*  28: 74 */     this.nodetype = nodetype;
/*  29: 75 */     this.namespace = namespace;
/*  30: 76 */     this.localName = localName;
/*  31: 77 */     this.hash = (nodetype + namespace.hashCode() + localName.hashCode());
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected void redefine(int nodetype, String namespace, String localName, int hash)
/*  35:    */   {
/*  36: 87 */     this.nodetype = nodetype;
/*  37: 88 */     this.namespace = namespace;
/*  38: 89 */     this.localName = localName;
/*  39: 90 */     this.hash = hash;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int hashCode()
/*  43:    */   {
/*  44: 98 */     return this.hash;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean equals(ExtendedType other)
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51:111 */       return (other.nodetype == this.nodetype) && (other.localName.equals(this.localName)) && (other.namespace.equals(this.namespace));
/*  52:    */     }
/*  53:    */     catch (NullPointerException e) {}
/*  54:117 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getNodeType()
/*  58:    */   {
/*  59:126 */     return this.nodetype;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getLocalName()
/*  63:    */   {
/*  64:134 */     return this.localName;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getNamespace()
/*  68:    */   {
/*  69:142 */     return this.namespace;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.ExtendedType
 * JD-Core Version:    0.7.0.1
 */