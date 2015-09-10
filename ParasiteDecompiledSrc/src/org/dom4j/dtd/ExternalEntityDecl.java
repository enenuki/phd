/*   1:    */ package org.dom4j.dtd;
/*   2:    */ 
/*   3:    */ public class ExternalEntityDecl
/*   4:    */ {
/*   5:    */   private String name;
/*   6:    */   private String publicID;
/*   7:    */   private String systemID;
/*   8:    */   
/*   9:    */   public ExternalEntityDecl() {}
/*  10:    */   
/*  11:    */   public ExternalEntityDecl(String name, String publicID, String systemID)
/*  12:    */   {
/*  13: 33 */     this.name = name;
/*  14: 34 */     this.publicID = publicID;
/*  15: 35 */     this.systemID = systemID;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getName()
/*  19:    */   {
/*  20: 44 */     return this.name;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setName(String name)
/*  24:    */   {
/*  25: 54 */     this.name = name;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getPublicID()
/*  29:    */   {
/*  30: 63 */     return this.publicID;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setPublicID(String publicID)
/*  34:    */   {
/*  35: 73 */     this.publicID = publicID;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getSystemID()
/*  39:    */   {
/*  40: 82 */     return this.systemID;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setSystemID(String systemID)
/*  44:    */   {
/*  45: 92 */     this.systemID = systemID;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String toString()
/*  49:    */   {
/*  50: 96 */     StringBuffer buffer = new StringBuffer("<!ENTITY ");
/*  51: 98 */     if (this.name.startsWith("%"))
/*  52:    */     {
/*  53: 99 */       buffer.append("% ");
/*  54:100 */       buffer.append(this.name.substring(1));
/*  55:    */     }
/*  56:    */     else
/*  57:    */     {
/*  58:102 */       buffer.append(this.name);
/*  59:    */     }
/*  60:105 */     if (this.publicID != null)
/*  61:    */     {
/*  62:106 */       buffer.append(" PUBLIC \"");
/*  63:107 */       buffer.append(this.publicID);
/*  64:108 */       buffer.append("\" ");
/*  65:110 */       if (this.systemID != null)
/*  66:    */       {
/*  67:111 */         buffer.append("\"");
/*  68:112 */         buffer.append(this.systemID);
/*  69:113 */         buffer.append("\" ");
/*  70:    */       }
/*  71:    */     }
/*  72:115 */     else if (this.systemID != null)
/*  73:    */     {
/*  74:116 */       buffer.append(" SYSTEM \"");
/*  75:117 */       buffer.append(this.systemID);
/*  76:118 */       buffer.append("\" ");
/*  77:    */     }
/*  78:121 */     buffer.append(">");
/*  79:    */     
/*  80:123 */     return buffer.toString();
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dtd.ExternalEntityDecl
 * JD-Core Version:    0.7.0.1
 */