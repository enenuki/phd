/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ final class FieldOrMethodRef
/*    4:     */ {
/*    5:     */   private String className;
/*    6:     */   private String name;
/*    7:     */   private String type;
/*    8:     */   
/*    9:     */   FieldOrMethodRef(String className, String name, String type)
/*   10:     */   {
/*   11:4739 */     this.className = className;
/*   12:4740 */     this.name = name;
/*   13:4741 */     this.type = type;
/*   14:     */   }
/*   15:     */   
/*   16:     */   public String getClassName()
/*   17:     */   {
/*   18:4746 */     return this.className;
/*   19:     */   }
/*   20:     */   
/*   21:     */   public String getName()
/*   22:     */   {
/*   23:4751 */     return this.name;
/*   24:     */   }
/*   25:     */   
/*   26:     */   public String getType()
/*   27:     */   {
/*   28:4756 */     return this.type;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public boolean equals(Object obj)
/*   32:     */   {
/*   33:4762 */     if (!(obj instanceof FieldOrMethodRef)) {
/*   34:4762 */       return false;
/*   35:     */     }
/*   36:4763 */     FieldOrMethodRef x = (FieldOrMethodRef)obj;
/*   37:4764 */     return (this.className.equals(x.className)) && (this.name.equals(x.name)) && (this.type.equals(x.type));
/*   38:     */   }
/*   39:     */   
/*   40:     */   public int hashCode()
/*   41:     */   {
/*   42:4772 */     if (this.hashCode == -1)
/*   43:     */     {
/*   44:4773 */       int h1 = this.className.hashCode();
/*   45:4774 */       int h2 = this.name.hashCode();
/*   46:4775 */       int h3 = this.type.hashCode();
/*   47:4776 */       this.hashCode = (h1 ^ h2 ^ h3);
/*   48:     */     }
/*   49:4778 */     return this.hashCode;
/*   50:     */   }
/*   51:     */   
/*   52:4784 */   private int hashCode = -1;
/*   53:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.FieldOrMethodRef
 * JD-Core Version:    0.7.0.1
 */