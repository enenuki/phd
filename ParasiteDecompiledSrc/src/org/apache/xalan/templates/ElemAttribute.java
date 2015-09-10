/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   5:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   6:    */ import org.apache.xml.serializer.NamespaceMappings;
/*   7:    */ import org.apache.xml.serializer.SerializationHandler;
/*   8:    */ import org.apache.xml.utils.QName;
/*   9:    */ import org.apache.xml.utils.XML11Char;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ 
/*  12:    */ public class ElemAttribute
/*  13:    */   extends ElemElement
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 8817220961566919187L;
/*  16:    */   
/*  17:    */   public int getXSLToken()
/*  18:    */   {
/*  19: 59 */     return 48;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String getNodeName()
/*  23:    */   {
/*  24: 69 */     return "attribute";
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected String resolvePrefix(SerializationHandler rhandler, String prefix, String nodeNamespace)
/*  28:    */     throws TransformerException
/*  29:    */   {
/*  30:129 */     if ((null != prefix) && ((prefix.length() == 0) || (prefix.equals("xmlns"))))
/*  31:    */     {
/*  32:133 */       prefix = rhandler.getPrefix(nodeNamespace);
/*  33:136 */       if ((null == prefix) || (prefix.length() == 0) || (prefix.equals("xmlns"))) {
/*  34:138 */         if (nodeNamespace.length() > 0)
/*  35:    */         {
/*  36:140 */           NamespaceMappings prefixMapping = rhandler.getNamespaceMappings();
/*  37:141 */           prefix = prefixMapping.generateNextPrefix();
/*  38:    */         }
/*  39:    */         else
/*  40:    */         {
/*  41:144 */           prefix = "";
/*  42:    */         }
/*  43:    */       }
/*  44:    */     }
/*  45:147 */     return prefix;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected boolean validateNodeName(String nodeName)
/*  49:    */   {
/*  50:159 */     if (null == nodeName) {
/*  51:160 */       return false;
/*  52:    */     }
/*  53:161 */     if (nodeName.equals("xmlns")) {
/*  54:162 */       return false;
/*  55:    */     }
/*  56:163 */     return XML11Char.isXML11ValidQName(nodeName);
/*  57:    */   }
/*  58:    */   
/*  59:    */   void constructNode(String nodeName, String prefix, String nodeNamespace, TransformerImpl transformer)
/*  60:    */     throws TransformerException
/*  61:    */   {
/*  62:184 */     if ((null != nodeName) && (nodeName.length() > 0))
/*  63:    */     {
/*  64:186 */       SerializationHandler rhandler = transformer.getSerializationHandler();
/*  65:    */       
/*  66:    */ 
/*  67:189 */       String val = transformer.transformToString(this);
/*  68:    */       try
/*  69:    */       {
/*  70:193 */         String localName = QName.getLocalPart(nodeName);
/*  71:194 */         if ((prefix != null) && (prefix.length() > 0)) {
/*  72:195 */           rhandler.addAttribute(nodeNamespace, localName, nodeName, "CDATA", val, true);
/*  73:    */         } else {
/*  74:197 */           rhandler.addAttribute("", localName, nodeName, "CDATA", val, true);
/*  75:    */         }
/*  76:    */       }
/*  77:    */       catch (SAXException e) {}
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  82:    */   {
/*  83:225 */     int type = newChild.getXSLToken();
/*  84:227 */     switch (type)
/*  85:    */     {
/*  86:    */     case 9: 
/*  87:    */     case 17: 
/*  88:    */     case 28: 
/*  89:    */     case 30: 
/*  90:    */     case 35: 
/*  91:    */     case 36: 
/*  92:    */     case 37: 
/*  93:    */     case 42: 
/*  94:    */     case 50: 
/*  95:    */     case 72: 
/*  96:    */     case 73: 
/*  97:    */     case 74: 
/*  98:    */     case 75: 
/*  99:    */     case 78: 
/* 100:    */       break;
/* 101:    */     default: 
/* 102:253 */       error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/* 103:    */     }
/* 104:260 */     return super.appendChild(newChild);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setName(AVT v)
/* 108:    */   {
/* 109:266 */     if (v.isSimple()) {
/* 110:268 */       if (v.getSimpleString().equals("xmlns")) {
/* 111:270 */         throw new IllegalArgumentException();
/* 112:    */       }
/* 113:    */     }
/* 114:273 */     super.setName(v);
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemAttribute
 * JD-Core Version:    0.7.0.1
 */