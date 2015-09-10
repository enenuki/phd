/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.text.DecimalFormat;
/*   4:    */ import java.text.DecimalFormatSymbols;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import java.util.Locale;
/*   7:    */ import javax.xml.transform.ErrorListener;
/*   8:    */ import javax.xml.transform.TransformerException;
/*   9:    */ import org.apache.xalan.res.XSLMessages;
/*  10:    */ import org.apache.xml.utils.QName;
/*  11:    */ import org.apache.xml.utils.SAXSourceLocator;
/*  12:    */ import org.apache.xpath.Expression;
/*  13:    */ import org.apache.xpath.XPathContext;
/*  14:    */ import org.apache.xpath.functions.Function2Args;
/*  15:    */ import org.apache.xpath.functions.Function3Args;
/*  16:    */ import org.apache.xpath.functions.FunctionOneArg;
/*  17:    */ import org.apache.xpath.functions.WrongNumberArgsException;
/*  18:    */ import org.apache.xpath.objects.XObject;
/*  19:    */ import org.apache.xpath.objects.XString;
/*  20:    */ 
/*  21:    */ public class FuncFormatNumb
/*  22:    */   extends Function3Args
/*  23:    */ {
/*  24:    */   static final long serialVersionUID = -8869935264870858636L;
/*  25:    */   
/*  26:    */   public XObject execute(XPathContext xctxt)
/*  27:    */     throws TransformerException
/*  28:    */   {
/*  29: 57 */     ElemTemplateElement templElem = (ElemTemplateElement)xctxt.getNamespaceContext();
/*  30:    */     
/*  31: 59 */     StylesheetRoot ss = templElem.getStylesheetRoot();
/*  32: 60 */     DecimalFormat formatter = null;
/*  33: 61 */     DecimalFormatSymbols dfs = null;
/*  34: 62 */     double num = getArg0().execute(xctxt).num();
/*  35: 63 */     String patternStr = getArg1().execute(xctxt).str();
/*  36: 66 */     if (patternStr.indexOf('¤') > 0) {
/*  37: 67 */       ss.error("ER_CURRENCY_SIGN_ILLEGAL");
/*  38:    */     }
/*  39:    */     try
/*  40:    */     {
/*  41: 73 */       Expression arg2Expr = getArg2();
/*  42: 75 */       if (null != arg2Expr)
/*  43:    */       {
/*  44: 77 */         String dfName = arg2Expr.execute(xctxt).str();
/*  45: 78 */         QName qname = new QName(dfName, xctxt.getNamespaceContext());
/*  46:    */         
/*  47: 80 */         dfs = ss.getDecimalFormatComposed(qname);
/*  48: 82 */         if (null == dfs)
/*  49:    */         {
/*  50: 84 */           warn(xctxt, "WG_NO_DECIMALFORMAT_DECLARATION", new Object[] { dfName });
/*  51:    */         }
/*  52:    */         else
/*  53:    */         {
/*  54: 93 */           formatter = new DecimalFormat();
/*  55:    */           
/*  56: 95 */           formatter.setDecimalFormatSymbols(dfs);
/*  57: 96 */           formatter.applyLocalizedPattern(patternStr);
/*  58:    */         }
/*  59:    */       }
/*  60:101 */       if (null == formatter)
/*  61:    */       {
/*  62:105 */         dfs = ss.getDecimalFormatComposed(new QName(""));
/*  63:107 */         if (dfs != null)
/*  64:    */         {
/*  65:109 */           formatter = new DecimalFormat();
/*  66:    */           
/*  67:111 */           formatter.setDecimalFormatSymbols(dfs);
/*  68:112 */           formatter.applyLocalizedPattern(patternStr);
/*  69:    */         }
/*  70:    */         else
/*  71:    */         {
/*  72:116 */           dfs = new DecimalFormatSymbols(Locale.US);
/*  73:    */           
/*  74:118 */           dfs.setInfinity("Infinity");
/*  75:119 */           dfs.setNaN("NaN");
/*  76:    */           
/*  77:121 */           formatter = new DecimalFormat();
/*  78:    */           
/*  79:123 */           formatter.setDecimalFormatSymbols(dfs);
/*  80:125 */           if (null != patternStr) {
/*  81:126 */             formatter.applyLocalizedPattern(patternStr);
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:130 */       return new XString(formatter.format(num));
/*  86:    */     }
/*  87:    */     catch (Exception iae)
/*  88:    */     {
/*  89:134 */       templElem.error("ER_MALFORMED_FORMAT_STRING", new Object[] { patternStr });
/*  90:    */     }
/*  91:137 */     return XString.EMPTYSTRING;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void warn(XPathContext xctxt, String msg, Object[] args)
/*  95:    */     throws TransformerException
/*  96:    */   {
/*  97:158 */     String formattedMsg = XSLMessages.createWarning(msg, args);
/*  98:159 */     ErrorListener errHandler = xctxt.getErrorListener();
/*  99:    */     
/* 100:161 */     errHandler.warning(new TransformerException(formattedMsg, (SAXSourceLocator)xctxt.getSAXLocator()));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void checkNumberArgs(int argNum)
/* 104:    */     throws WrongNumberArgsException
/* 105:    */   {
/* 106:175 */     if ((argNum > 3) || (argNum < 2)) {
/* 107:176 */       reportWrongNumberArgs();
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void reportWrongNumberArgs()
/* 112:    */     throws WrongNumberArgsException
/* 113:    */   {
/* 114:186 */     throw new WrongNumberArgsException(XSLMessages.createMessage("ER_TWO_OR_THREE", null));
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.FuncFormatNumb
 * JD-Core Version:    0.7.0.1
 */