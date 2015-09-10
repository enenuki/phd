/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.trace.TraceManager;
/*   5:    */ import org.apache.xalan.transformer.MsgMgr;
/*   6:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   7:    */ import org.apache.xpath.XPath;
/*   8:    */ import org.apache.xpath.XPathContext;
/*   9:    */ import org.apache.xpath.objects.XObject;
/*  10:    */ 
/*  11:    */ public class ElemChoose
/*  12:    */   extends ElemTemplateElement
/*  13:    */ {
/*  14:    */   static final long serialVersionUID = -3070117361903102033L;
/*  15:    */   
/*  16:    */   public int getXSLToken()
/*  17:    */   {
/*  18: 51 */     return 37;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getNodeName()
/*  22:    */   {
/*  23: 61 */     return "choose";
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void execute(TransformerImpl transformer)
/*  27:    */     throws TransformerException
/*  28:    */   {
/*  29: 81 */     if (transformer.getDebug()) {
/*  30: 82 */       transformer.getTraceManager().fireTraceEvent(this);
/*  31:    */     }
/*  32: 84 */     boolean found = false;
/*  33: 86 */     for (ElemTemplateElement childElem = getFirstChildElem(); childElem != null; childElem = childElem.getNextSiblingElem())
/*  34:    */     {
/*  35: 89 */       int type = childElem.getXSLToken();
/*  36: 91 */       if (38 == type)
/*  37:    */       {
/*  38: 93 */         found = true;
/*  39:    */         
/*  40: 95 */         ElemWhen when = (ElemWhen)childElem;
/*  41:    */         
/*  42:    */ 
/*  43: 98 */         XPathContext xctxt = transformer.getXPathContext();
/*  44: 99 */         int sourceNode = xctxt.getCurrentNode();
/*  45:106 */         if (transformer.getDebug())
/*  46:    */         {
/*  47:108 */           XObject test = when.getTest().execute(xctxt, sourceNode, when);
/*  48:110 */           if (transformer.getDebug()) {
/*  49:111 */             transformer.getTraceManager().fireSelectedEvent(sourceNode, when, "test", when.getTest(), test);
/*  50:    */           }
/*  51:114 */           if (test.bool())
/*  52:    */           {
/*  53:116 */             transformer.getTraceManager().fireTraceEvent(when);
/*  54:    */             
/*  55:118 */             transformer.executeChildTemplates(when, true);
/*  56:    */             
/*  57:120 */             transformer.getTraceManager().fireTraceEndEvent(when);
/*  58:    */             
/*  59:122 */             return;
/*  60:    */           }
/*  61:    */         }
/*  62:126 */         else if (when.getTest().bool(xctxt, sourceNode, when))
/*  63:    */         {
/*  64:128 */           transformer.executeChildTemplates(when, true);
/*  65:    */           
/*  66:130 */           return;
/*  67:    */         }
/*  68:    */       }
/*  69:133 */       else if (39 == type)
/*  70:    */       {
/*  71:135 */         found = true;
/*  72:137 */         if (transformer.getDebug()) {
/*  73:138 */           transformer.getTraceManager().fireTraceEvent(childElem);
/*  74:    */         }
/*  75:141 */         transformer.executeChildTemplates(childElem, true);
/*  76:143 */         if (transformer.getDebug()) {
/*  77:144 */           transformer.getTraceManager().fireTraceEndEvent(childElem);
/*  78:    */         }
/*  79:145 */         return;
/*  80:    */       }
/*  81:    */     }
/*  82:149 */     if (!found) {
/*  83:150 */       transformer.getMsgMgr().error(this, "ER_CHOOSE_REQUIRES_WHEN");
/*  84:    */     }
/*  85:153 */     if (transformer.getDebug()) {
/*  86:154 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  91:    */   {
/*  92:169 */     int type = newChild.getXSLToken();
/*  93:171 */     switch (type)
/*  94:    */     {
/*  95:    */     case 38: 
/*  96:    */     case 39: 
/*  97:    */       break;
/*  98:    */     default: 
/*  99:179 */       error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/* 100:    */     }
/* 101:186 */     return super.appendChild(newChild);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean canAcceptVariables()
/* 105:    */   {
/* 106:195 */     return false;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemChoose
 * JD-Core Version:    0.7.0.1
 */