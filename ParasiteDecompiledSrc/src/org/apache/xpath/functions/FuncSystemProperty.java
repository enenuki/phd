/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.Properties;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xml.utils.PrefixResolver;
/*   8:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   9:    */ import org.apache.xpath.Expression;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XObject;
/*  12:    */ import org.apache.xpath.objects.XString;
/*  13:    */ 
/*  14:    */ public class FuncSystemProperty
/*  15:    */   extends FunctionOneArg
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = 3694874980992204867L;
/*  18:    */   static final String XSLT_PROPERTIES = "org/apache/xalan/res/XSLTInfo.properties";
/*  19:    */   
/*  20:    */   public XObject execute(XPathContext xctxt)
/*  21:    */     throws TransformerException
/*  22:    */   {
/*  23: 59 */     String fullName = this.m_arg0.execute(xctxt).str();
/*  24: 60 */     int indexOfNSSep = fullName.indexOf(':');
/*  25:    */     
/*  26: 62 */     String propName = "";
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30: 66 */     Properties xsltInfo = new Properties();
/*  31:    */     
/*  32: 68 */     loadPropertyFile("org/apache/xalan/res/XSLTInfo.properties", xsltInfo);
/*  33:    */     String result;
/*  34: 70 */     if (indexOfNSSep > 0)
/*  35:    */     {
/*  36: 72 */       String prefix = indexOfNSSep >= 0 ? fullName.substring(0, indexOfNSSep) : "";
/*  37:    */       
/*  38:    */ 
/*  39:    */ 
/*  40: 76 */       String namespace = xctxt.getNamespaceContext().getNamespaceForPrefix(prefix);
/*  41: 77 */       propName = indexOfNSSep < 0 ? fullName : fullName.substring(indexOfNSSep + 1);
/*  42: 80 */       if ((namespace.startsWith("http://www.w3.org/XSL/Transform")) || (namespace.equals("http://www.w3.org/1999/XSL/Transform")))
/*  43:    */       {
/*  44: 83 */         result = xsltInfo.getProperty(propName);
/*  45: 85 */         if (null == result)
/*  46:    */         {
/*  47: 87 */           warn(xctxt, "WG_PROPERTY_NOT_SUPPORTED", new Object[] { fullName });
/*  48:    */           
/*  49:    */ 
/*  50: 90 */           return XString.EMPTYSTRING;
/*  51:    */         }
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55: 95 */         warn(xctxt, "WG_DONT_DO_ANYTHING_WITH_NS", new Object[] { namespace, fullName });
/*  56:    */         try
/*  57:    */         {
/*  58:101 */           result = System.getProperty(propName);
/*  59:103 */           if (null == result) {
/*  60:107 */             return XString.EMPTYSTRING;
/*  61:    */           }
/*  62:    */         }
/*  63:    */         catch (SecurityException se)
/*  64:    */         {
/*  65:112 */           warn(xctxt, "WG_SECURITY_EXCEPTION", new Object[] { fullName });
/*  66:    */           
/*  67:    */ 
/*  68:115 */           return XString.EMPTYSTRING;
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74:    */       try
/*  75:    */       {
/*  76:123 */         result = System.getProperty(fullName);
/*  77:125 */         if (null == result) {
/*  78:129 */           return XString.EMPTYSTRING;
/*  79:    */         }
/*  80:    */       }
/*  81:    */       catch (SecurityException se)
/*  82:    */       {
/*  83:134 */         warn(xctxt, "WG_SECURITY_EXCEPTION", new Object[] { fullName });
/*  84:    */         
/*  85:    */ 
/*  86:137 */         return XString.EMPTYSTRING;
/*  87:    */       }
/*  88:    */     }
/*  89:141 */     if ((propName.equals("version")) && (result.length() > 0)) {
/*  90:    */       try
/*  91:    */       {
/*  92:146 */         return new XString("1.0");
/*  93:    */       }
/*  94:    */       catch (Exception ex)
/*  95:    */       {
/*  96:150 */         return new XString(result);
/*  97:    */       }
/*  98:    */     }
/*  99:154 */     return new XString(result);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void loadPropertyFile(String file, Properties target)
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:169 */       SecuritySupport ss = SecuritySupport.getInstance();
/* 107:    */       
/* 108:171 */       InputStream is = ss.getResourceAsStream(ObjectFactory.findClassLoader(), file);
/* 109:    */       
/* 110:    */ 
/* 111:    */ 
/* 112:175 */       BufferedInputStream bis = new BufferedInputStream(is);
/* 113:    */       
/* 114:177 */       target.load(bis);
/* 115:178 */       bis.close();
/* 116:    */     }
/* 117:    */     catch (Exception ex)
/* 118:    */     {
/* 119:183 */       throw new WrappedRuntimeException(ex);
/* 120:    */     }
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncSystemProperty
 * JD-Core Version:    0.7.0.1
 */