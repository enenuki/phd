/*   1:    */ package org.apache.xpath.jaxp;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.namespace.QName;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import javax.xml.xpath.XPathFunction;
/*   8:    */ import javax.xml.xpath.XPathFunctionException;
/*   9:    */ import javax.xml.xpath.XPathFunctionResolver;
/*  10:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  11:    */ import org.apache.xpath.ExtensionsProvider;
/*  12:    */ import org.apache.xpath.functions.FuncExtFunction;
/*  13:    */ import org.apache.xpath.objects.XNodeSet;
/*  14:    */ import org.apache.xpath.objects.XObject;
/*  15:    */ import org.apache.xpath.res.XPATHMessages;
/*  16:    */ 
/*  17:    */ public class JAXPExtensionsProvider
/*  18:    */   implements ExtensionsProvider
/*  19:    */ {
/*  20:    */   private final XPathFunctionResolver resolver;
/*  21: 47 */   private boolean extensionInvocationDisabled = false;
/*  22:    */   
/*  23:    */   public JAXPExtensionsProvider(XPathFunctionResolver resolver)
/*  24:    */   {
/*  25: 50 */     this.resolver = resolver;
/*  26: 51 */     this.extensionInvocationDisabled = false;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JAXPExtensionsProvider(XPathFunctionResolver resolver, boolean featureSecureProcessing)
/*  30:    */   {
/*  31: 56 */     this.resolver = resolver;
/*  32: 57 */     this.extensionInvocationDisabled = featureSecureProcessing;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean functionAvailable(String ns, String funcName)
/*  36:    */     throws TransformerException
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40: 67 */       if (funcName == null)
/*  41:    */       {
/*  42: 68 */         String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "Function Name" });
/*  43:    */         
/*  44:    */ 
/*  45: 71 */         throw new NullPointerException(fmsg);
/*  46:    */       }
/*  47: 74 */       QName myQName = new QName(ns, funcName);
/*  48: 75 */       XPathFunction xpathFunction = this.resolver.resolveFunction(myQName, 0);
/*  49: 77 */       if (xpathFunction == null) {
/*  50: 78 */         return false;
/*  51:    */       }
/*  52: 80 */       return true;
/*  53:    */     }
/*  54:    */     catch (Exception e) {}
/*  55: 82 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean elementAvailable(String ns, String elemName)
/*  59:    */     throws TransformerException
/*  60:    */   {
/*  61: 94 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object extFunction(String ns, String funcName, Vector argVec, Object methodKey)
/*  65:    */     throws TransformerException
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:104 */       if (funcName == null)
/*  70:    */       {
/*  71:105 */         String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "Function Name" });
/*  72:    */         
/*  73:    */ 
/*  74:108 */         throw new NullPointerException(fmsg);
/*  75:    */       }
/*  76:111 */       QName myQName = new QName(ns, funcName);
/*  77:116 */       if (this.extensionInvocationDisabled)
/*  78:    */       {
/*  79:117 */         String fmsg = XPATHMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[] { myQName.toString() });
/*  80:    */         
/*  81:    */ 
/*  82:120 */         throw new XPathFunctionException(fmsg);
/*  83:    */       }
/*  84:125 */       int arity = argVec.size();
/*  85:    */       
/*  86:127 */       XPathFunction xpathFunction = this.resolver.resolveFunction(myQName, arity);
/*  87:    */       
/*  88:    */ 
/*  89:    */ 
/*  90:131 */       ArrayList argList = new ArrayList(arity);
/*  91:132 */       for (int i = 0; i < arity; i++)
/*  92:    */       {
/*  93:133 */         Object argument = argVec.elementAt(i);
/*  94:136 */         if ((argument instanceof XNodeSet))
/*  95:    */         {
/*  96:137 */           argList.add(i, ((XNodeSet)argument).nodelist());
/*  97:    */         }
/*  98:138 */         else if ((argument instanceof XObject))
/*  99:    */         {
/* 100:139 */           Object passedArgument = ((XObject)argument).object();
/* 101:140 */           argList.add(i, passedArgument);
/* 102:    */         }
/* 103:    */         else
/* 104:    */         {
/* 105:142 */           argList.add(i, argument);
/* 106:    */         }
/* 107:    */       }
/* 108:146 */       return xpathFunction.evaluate(argList);
/* 109:    */     }
/* 110:    */     catch (XPathFunctionException xfe)
/* 111:    */     {
/* 112:150 */       throw new WrappedRuntimeException(xfe);
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:152 */       throw new TransformerException(e);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Object extFunction(FuncExtFunction extFunction, Vector argVec)
/* 121:    */     throws TransformerException
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:164 */       String namespace = extFunction.getNamespace();
/* 126:165 */       String functionName = extFunction.getFunctionName();
/* 127:166 */       int arity = extFunction.getArgCount();
/* 128:167 */       QName myQName = new QName(namespace, functionName);
/* 129:173 */       if (this.extensionInvocationDisabled)
/* 130:    */       {
/* 131:174 */         String fmsg = XPATHMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[] { myQName.toString() });
/* 132:    */         
/* 133:176 */         throw new XPathFunctionException(fmsg);
/* 134:    */       }
/* 135:179 */       XPathFunction xpathFunction = this.resolver.resolveFunction(myQName, arity);
/* 136:    */       
/* 137:    */ 
/* 138:182 */       ArrayList argList = new ArrayList(arity);
/* 139:183 */       for (int i = 0; i < arity; i++)
/* 140:    */       {
/* 141:184 */         Object argument = argVec.elementAt(i);
/* 142:187 */         if ((argument instanceof XNodeSet))
/* 143:    */         {
/* 144:188 */           argList.add(i, ((XNodeSet)argument).nodelist());
/* 145:    */         }
/* 146:189 */         else if ((argument instanceof XObject))
/* 147:    */         {
/* 148:190 */           Object passedArgument = ((XObject)argument).object();
/* 149:191 */           argList.add(i, passedArgument);
/* 150:    */         }
/* 151:    */         else
/* 152:    */         {
/* 153:193 */           argList.add(i, argument);
/* 154:    */         }
/* 155:    */       }
/* 156:197 */       return xpathFunction.evaluate(argList);
/* 157:    */     }
/* 158:    */     catch (XPathFunctionException xfe)
/* 159:    */     {
/* 160:202 */       throw new WrappedRuntimeException(xfe);
/* 161:    */     }
/* 162:    */     catch (Exception e)
/* 163:    */     {
/* 164:204 */       throw new TransformerException(e);
/* 165:    */     }
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.jaxp.JAXPExtensionsProvider
 * JD-Core Version:    0.7.0.1
 */