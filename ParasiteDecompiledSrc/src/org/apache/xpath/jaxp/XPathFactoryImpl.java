/*   1:    */ package org.apache.xpath.jaxp;
/*   2:    */ 
/*   3:    */ import javax.xml.xpath.XPath;
/*   4:    */ import javax.xml.xpath.XPathFactory;
/*   5:    */ import javax.xml.xpath.XPathFactoryConfigurationException;
/*   6:    */ import javax.xml.xpath.XPathFunctionResolver;
/*   7:    */ import javax.xml.xpath.XPathVariableResolver;
/*   8:    */ import org.apache.xpath.res.XPATHMessages;
/*   9:    */ 
/*  10:    */ public class XPathFactoryImpl
/*  11:    */   extends XPathFactory
/*  12:    */ {
/*  13:    */   private static final String CLASS_NAME = "XPathFactoryImpl";
/*  14: 47 */   private XPathFunctionResolver xPathFunctionResolver = null;
/*  15: 52 */   private XPathVariableResolver xPathVariableResolver = null;
/*  16: 57 */   private boolean featureSecureProcessing = false;
/*  17:    */   
/*  18:    */   public boolean isObjectModelSupported(String objectModel)
/*  19:    */   {
/*  20: 74 */     if (objectModel == null)
/*  21:    */     {
/*  22: 75 */       String fmsg = XPATHMessages.createXPATHMessage("ER_OBJECT_MODEL_NULL", new Object[] { getClass().getName() });
/*  23:    */       
/*  24:    */ 
/*  25:    */ 
/*  26: 79 */       throw new NullPointerException(fmsg);
/*  27:    */     }
/*  28: 82 */     if (objectModel.length() == 0)
/*  29:    */     {
/*  30: 83 */       String fmsg = XPATHMessages.createXPATHMessage("ER_OBJECT_MODEL_EMPTY", new Object[] { getClass().getName() });
/*  31:    */       
/*  32:    */ 
/*  33: 86 */       throw new IllegalArgumentException(fmsg);
/*  34:    */     }
/*  35: 90 */     if (objectModel.equals("http://java.sun.com/jaxp/xpath/dom")) {
/*  36: 91 */       return true;
/*  37:    */     }
/*  38: 95 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public XPath newXPath()
/*  42:    */   {
/*  43:105 */     return new XPathImpl(this.xPathVariableResolver, this.xPathFunctionResolver, this.featureSecureProcessing);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setFeature(String name, boolean value)
/*  47:    */     throws XPathFactoryConfigurationException
/*  48:    */   {
/*  49:140 */     if (name == null)
/*  50:    */     {
/*  51:141 */       String fmsg = XPATHMessages.createXPATHMessage("ER_FEATURE_NAME_NULL", new Object[] { "XPathFactoryImpl", new Boolean(value) });
/*  52:    */       
/*  53:    */ 
/*  54:144 */       throw new NullPointerException(fmsg);
/*  55:    */     }
/*  56:148 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
/*  57:    */     {
/*  58:150 */       this.featureSecureProcessing = value;
/*  59:    */       
/*  60:    */ 
/*  61:153 */       return;
/*  62:    */     }
/*  63:157 */     String fmsg = XPATHMessages.createXPATHMessage("ER_FEATURE_UNKNOWN", new Object[] { name, "XPathFactoryImpl", new Boolean(value) });
/*  64:    */     
/*  65:    */ 
/*  66:160 */     throw new XPathFactoryConfigurationException(fmsg);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean getFeature(String name)
/*  70:    */     throws XPathFactoryConfigurationException
/*  71:    */   {
/*  72:190 */     if (name == null)
/*  73:    */     {
/*  74:191 */       String fmsg = XPATHMessages.createXPATHMessage("ER_GETTING_NULL_FEATURE", new Object[] { "XPathFactoryImpl" });
/*  75:    */       
/*  76:    */ 
/*  77:194 */       throw new NullPointerException(fmsg);
/*  78:    */     }
/*  79:198 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  80:199 */       return this.featureSecureProcessing;
/*  81:    */     }
/*  82:203 */     String fmsg = XPATHMessages.createXPATHMessage("ER_GETTING_UNKNOWN_FEATURE", new Object[] { name, "XPathFactoryImpl" });
/*  83:    */     
/*  84:    */ 
/*  85:    */ 
/*  86:207 */     throw new XPathFactoryConfigurationException(fmsg);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setXPathFunctionResolver(XPathFunctionResolver resolver)
/*  90:    */   {
/*  91:227 */     if (resolver == null)
/*  92:    */     {
/*  93:228 */       String fmsg = XPATHMessages.createXPATHMessage("ER_NULL_XPATH_FUNCTION_RESOLVER", new Object[] { "XPathFactoryImpl" });
/*  94:    */       
/*  95:    */ 
/*  96:231 */       throw new NullPointerException(fmsg);
/*  97:    */     }
/*  98:234 */     this.xPathFunctionResolver = resolver;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setXPathVariableResolver(XPathVariableResolver resolver)
/* 102:    */   {
/* 103:253 */     if (resolver == null)
/* 104:    */     {
/* 105:254 */       String fmsg = XPATHMessages.createXPATHMessage("ER_NULL_XPATH_VARIABLE_RESOLVER", new Object[] { "XPathFactoryImpl" });
/* 106:    */       
/* 107:    */ 
/* 108:257 */       throw new NullPointerException(fmsg);
/* 109:    */     }
/* 110:260 */     this.xPathVariableResolver = resolver;
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.jaxp.XPathFactoryImpl
 * JD-Core Version:    0.7.0.1
 */