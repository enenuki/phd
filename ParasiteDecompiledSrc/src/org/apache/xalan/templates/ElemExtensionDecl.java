/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.extensions.ExtensionNamespaceSupport;
/*   5:    */ import org.apache.xalan.extensions.ExtensionNamespacesManager;
/*   6:    */ import org.apache.xalan.res.XSLMessages;
/*   7:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   8:    */ import org.apache.xml.utils.StringVector;
/*   9:    */ 
/*  10:    */ public class ElemExtensionDecl
/*  11:    */   extends ElemTemplateElement
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -4692738885172766789L;
/*  14: 52 */   private String m_prefix = null;
/*  15:    */   
/*  16:    */   public void setPrefix(String v)
/*  17:    */   {
/*  18: 62 */     this.m_prefix = v;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getPrefix()
/*  22:    */   {
/*  23: 73 */     return this.m_prefix;
/*  24:    */   }
/*  25:    */   
/*  26: 78 */   private StringVector m_functions = new StringVector();
/*  27:    */   
/*  28:    */   public void setFunctions(StringVector v)
/*  29:    */   {
/*  30: 88 */     this.m_functions = v;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public StringVector getFunctions()
/*  34:    */   {
/*  35: 99 */     return this.m_functions;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getFunction(int i)
/*  39:    */     throws ArrayIndexOutOfBoundsException
/*  40:    */   {
/*  41:115 */     if (null == this.m_functions) {
/*  42:116 */       throw new ArrayIndexOutOfBoundsException();
/*  43:    */     }
/*  44:118 */     return this.m_functions.elementAt(i);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getFunctionCount()
/*  48:    */   {
/*  49:129 */     return null != this.m_functions ? this.m_functions.size() : 0;
/*  50:    */   }
/*  51:    */   
/*  52:134 */   private StringVector m_elements = null;
/*  53:    */   
/*  54:    */   public void setElements(StringVector v)
/*  55:    */   {
/*  56:144 */     this.m_elements = v;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public StringVector getElements()
/*  60:    */   {
/*  61:155 */     return this.m_elements;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getElement(int i)
/*  65:    */     throws ArrayIndexOutOfBoundsException
/*  66:    */   {
/*  67:171 */     if (null == this.m_elements) {
/*  68:172 */       throw new ArrayIndexOutOfBoundsException();
/*  69:    */     }
/*  70:174 */     return this.m_elements.elementAt(i);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getElementCount()
/*  74:    */   {
/*  75:185 */     return null != this.m_elements ? this.m_elements.size() : 0;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getXSLToken()
/*  79:    */   {
/*  80:196 */     return 85;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void compose(StylesheetRoot sroot)
/*  84:    */     throws TransformerException
/*  85:    */   {
/*  86:201 */     super.compose(sroot);
/*  87:202 */     String prefix = getPrefix();
/*  88:203 */     String declNamespace = getNamespaceForPrefix(prefix);
/*  89:204 */     String lang = null;
/*  90:205 */     String srcURL = null;
/*  91:206 */     String scriptSrc = null;
/*  92:207 */     if (null == declNamespace) {
/*  93:208 */       throw new TransformerException(XSLMessages.createMessage("ER_NO_NAMESPACE_DECL", new Object[] { prefix }));
/*  94:    */     }
/*  95:210 */     for (ElemTemplateElement child = getFirstChildElem(); child != null; child = child.getNextSiblingElem()) {
/*  96:213 */       if (86 == child.getXSLToken())
/*  97:    */       {
/*  98:215 */         ElemExtensionScript sdecl = (ElemExtensionScript)child;
/*  99:216 */         lang = sdecl.getLang();
/* 100:217 */         srcURL = sdecl.getSrc();
/* 101:218 */         ElemTemplateElement childOfSDecl = sdecl.getFirstChildElem();
/* 102:219 */         if (null != childOfSDecl) {
/* 103:221 */           if (78 == childOfSDecl.getXSLToken())
/* 104:    */           {
/* 105:224 */             ElemTextLiteral tl = (ElemTextLiteral)childOfSDecl;
/* 106:225 */             char[] chars = tl.getChars();
/* 107:226 */             scriptSrc = new String(chars);
/* 108:227 */             if (scriptSrc.trim().length() == 0) {
/* 109:228 */               scriptSrc = null;
/* 110:    */             }
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:233 */     if (null == lang) {
/* 116:234 */       lang = "javaclass";
/* 117:    */     }
/* 118:235 */     if ((lang.equals("javaclass")) && (scriptSrc != null)) {
/* 119:236 */       throw new TransformerException(XSLMessages.createMessage("ER_ELEM_CONTENT_NOT_ALLOWED", new Object[] { scriptSrc }));
/* 120:    */     }
/* 121:240 */     ExtensionNamespaceSupport extNsSpt = null;
/* 122:241 */     ExtensionNamespacesManager extNsMgr = sroot.getExtensionNamespacesManager();
/* 123:242 */     if (extNsMgr.namespaceIndex(declNamespace, extNsMgr.getExtensions()) == -1) {
/* 124:245 */       if (lang.equals("javaclass"))
/* 125:    */       {
/* 126:247 */         if (null == srcURL) {
/* 127:249 */           extNsSpt = extNsMgr.defineJavaNamespace(declNamespace);
/* 128:251 */         } else if (extNsMgr.namespaceIndex(srcURL, extNsMgr.getExtensions()) == -1) {
/* 129:254 */           extNsSpt = extNsMgr.defineJavaNamespace(declNamespace, srcURL);
/* 130:    */         }
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:259 */         String handler = "org.apache.xalan.extensions.ExtensionHandlerGeneral";
/* 135:260 */         Object[] args = { declNamespace, this.m_elements, this.m_functions, lang, srcURL, scriptSrc, getSystemId() };
/* 136:    */         
/* 137:262 */         extNsSpt = new ExtensionNamespaceSupport(declNamespace, handler, args);
/* 138:    */       }
/* 139:    */     }
/* 140:265 */     if (extNsSpt != null) {
/* 141:266 */       extNsMgr.registerExtension(extNsSpt);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void runtimeInit(TransformerImpl transformer)
/* 146:    */     throws TransformerException
/* 147:    */   {}
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemExtensionDecl
 * JD-Core Version:    0.7.0.1
 */