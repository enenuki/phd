/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.serialize.SerializerUtils;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xalan.transformer.TreeWalker2Result;
/*   8:    */ import org.apache.xml.dtm.DTM;
/*   9:    */ import org.apache.xml.dtm.DTMIterator;
/*  10:    */ import org.apache.xml.dtm.DTMManager;
/*  11:    */ import org.apache.xml.dtm.ref.DTMTreeWalker;
/*  12:    */ import org.apache.xml.serializer.SerializationHandler;
/*  13:    */ import org.apache.xpath.Expression;
/*  14:    */ import org.apache.xpath.XPath;
/*  15:    */ import org.apache.xpath.XPathContext;
/*  16:    */ import org.apache.xpath.objects.XObject;
/*  17:    */ import org.xml.sax.ContentHandler;
/*  18:    */ import org.xml.sax.SAXException;
/*  19:    */ 
/*  20:    */ public class ElemCopyOf
/*  21:    */   extends ElemTemplateElement
/*  22:    */ {
/*  23:    */   static final long serialVersionUID = -7433828829497411127L;
/*  24: 54 */   public XPath m_selectExpression = null;
/*  25:    */   
/*  26:    */   public void setSelect(XPath expr)
/*  27:    */   {
/*  28: 64 */     this.m_selectExpression = expr;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public XPath getSelect()
/*  32:    */   {
/*  33: 75 */     return this.m_selectExpression;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void compose(StylesheetRoot sroot)
/*  37:    */     throws TransformerException
/*  38:    */   {
/*  39: 86 */     super.compose(sroot);
/*  40:    */     
/*  41: 88 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/*  42: 89 */     this.m_selectExpression.fixupVariables(cstate.getVariableNames(), cstate.getGlobalsSize());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getXSLToken()
/*  46:    */   {
/*  47:100 */     return 74;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getNodeName()
/*  51:    */   {
/*  52:110 */     return "copy-of";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void execute(TransformerImpl transformer)
/*  56:    */     throws TransformerException
/*  57:    */   {
/*  58:127 */     if (transformer.getDebug()) {
/*  59:128 */       transformer.getTraceManager().fireTraceEvent(this);
/*  60:    */     }
/*  61:    */     try
/*  62:    */     {
/*  63:132 */       XPathContext xctxt = transformer.getXPathContext();
/*  64:133 */       int sourceNode = xctxt.getCurrentNode();
/*  65:134 */       XObject value = this.m_selectExpression.execute(xctxt, sourceNode, this);
/*  66:136 */       if (transformer.getDebug()) {
/*  67:137 */         transformer.getTraceManager().fireSelectedEvent(sourceNode, this, "select", this.m_selectExpression, value);
/*  68:    */       }
/*  69:140 */       SerializationHandler handler = transformer.getSerializationHandler();
/*  70:142 */       if (null != value)
/*  71:    */       {
/*  72:144 */         int type = value.getType();
/*  73:    */         String s;
/*  74:147 */         switch (type)
/*  75:    */         {
/*  76:    */         case 1: 
/*  77:    */         case 2: 
/*  78:    */         case 3: 
/*  79:152 */           s = value.str();
/*  80:    */           
/*  81:154 */           handler.characters(s.toCharArray(), 0, s.length());
/*  82:155 */           break;
/*  83:    */         case 4: 
/*  84:159 */           DTMIterator nl = value.iter();
/*  85:    */           
/*  86:    */ 
/*  87:162 */           DTMTreeWalker tw = new TreeWalker2Result(transformer, handler);
/*  88:    */           int pos;
/*  89:165 */           while (-1 != (pos = nl.nextNode()))
/*  90:    */           {
/*  91:    */             int i;
/*  92:167 */             DTM dtm = xctxt.getDTMManager().getDTM(i);
/*  93:168 */             short t = dtm.getNodeType(i);
/*  94:172 */             if (t == 9) {
/*  95:174 */               for (int child = dtm.getFirstChild(i); child != -1; child = dtm.getNextSibling(child)) {
/*  96:177 */                 tw.traverse(child);
/*  97:    */               }
/*  98:180 */             } else if (t == 2) {
/*  99:182 */               SerializerUtils.addAttribute(handler, i);
/* 100:    */             } else {
/* 101:186 */               tw.traverse(i);
/* 102:    */             }
/* 103:    */           }
/* 104:190 */           break;
/* 105:    */         case 5: 
/* 106:192 */           SerializerUtils.outputResultTreeFragment(handler, value, transformer.getXPathContext());
/* 107:    */           
/* 108:194 */           break;
/* 109:    */         default: 
/* 110:197 */           s = value.str();
/* 111:    */           
/* 112:199 */           handler.characters(s.toCharArray(), 0, s.length());
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:    */     catch (SAXException se)
/* 117:    */     {
/* 118:212 */       throw new TransformerException(se);
/* 119:    */     }
/* 120:    */     finally
/* 121:    */     {
/* 122:216 */       if (transformer.getDebug()) {
/* 123:217 */         transformer.getTraceManager().fireTraceEndEvent(this);
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/* 129:    */   {
/* 130:232 */     error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/* 131:    */     
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:237 */     return null;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 139:    */   {
/* 140:246 */     if (callAttrs) {
/* 141:247 */       this.m_selectExpression.getExpression().callVisitors(this.m_selectExpression, visitor);
/* 142:    */     }
/* 143:248 */     super.callChildVisitors(visitor, callAttrs);
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemCopyOf
 * JD-Core Version:    0.7.0.1
 */