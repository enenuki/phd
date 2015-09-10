/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import org.apache.xml.utils.QName;
/*   4:    */ import org.apache.xpath.objects.XObject;
/*   5:    */ 
/*   6:    */ public class Arg
/*   7:    */ {
/*   8:    */   private QName m_qname;
/*   9:    */   private XObject m_val;
/*  10:    */   private String m_expression;
/*  11:    */   private boolean m_isFromWithParam;
/*  12:    */   private boolean m_isVisible;
/*  13:    */   
/*  14:    */   public final QName getQName()
/*  15:    */   {
/*  16: 49 */     return this.m_qname;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public final void setQName(QName name)
/*  20:    */   {
/*  21: 59 */     this.m_qname = name;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final XObject getVal()
/*  25:    */   {
/*  26: 76 */     return this.m_val;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final void setVal(XObject val)
/*  30:    */   {
/*  31: 87 */     this.m_val = val;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void detach()
/*  35:    */   {
/*  36: 96 */     if (null != this.m_val)
/*  37:    */     {
/*  38: 98 */       this.m_val.allowDetachToRelease(true);
/*  39: 99 */       this.m_val.detach();
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getExpression()
/*  44:    */   {
/*  45:119 */     return this.m_expression;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setExpression(String expr)
/*  49:    */   {
/*  50:131 */     this.m_expression = expr;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isFromWithParam()
/*  54:    */   {
/*  55:146 */     return this.m_isFromWithParam;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean isVisible()
/*  59:    */   {
/*  60:163 */     return this.m_isVisible;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIsVisible(boolean b)
/*  64:    */   {
/*  65:171 */     this.m_isVisible = b;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Arg()
/*  69:    */   {
/*  70:182 */     this.m_qname = new QName("");
/*  71:    */     
/*  72:184 */     this.m_val = null;
/*  73:185 */     this.m_expression = null;
/*  74:186 */     this.m_isVisible = true;
/*  75:187 */     this.m_isFromWithParam = false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Arg(QName qname, String expression, boolean isFromWithParam)
/*  79:    */   {
/*  80:200 */     this.m_qname = qname;
/*  81:201 */     this.m_val = null;
/*  82:202 */     this.m_expression = expression;
/*  83:203 */     this.m_isFromWithParam = isFromWithParam;
/*  84:204 */     this.m_isVisible = (!isFromWithParam);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Arg(QName qname, XObject val)
/*  88:    */   {
/*  89:217 */     this.m_qname = qname;
/*  90:218 */     this.m_val = val;
/*  91:219 */     this.m_isVisible = true;
/*  92:220 */     this.m_isFromWithParam = false;
/*  93:221 */     this.m_expression = null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean equals(Object obj)
/*  97:    */   {
/*  98:234 */     if ((obj instanceof QName)) {
/*  99:236 */       return this.m_qname.equals(obj);
/* 100:    */     }
/* 101:239 */     return super.equals(obj);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Arg(QName qname, XObject val, boolean isFromWithParam)
/* 105:    */   {
/* 106:252 */     this.m_qname = qname;
/* 107:253 */     this.m_val = val;
/* 108:254 */     this.m_isFromWithParam = isFromWithParam;
/* 109:255 */     this.m_isVisible = (!isFromWithParam);
/* 110:256 */     this.m_expression = null;
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.Arg
 * JD-Core Version:    0.7.0.1
 */