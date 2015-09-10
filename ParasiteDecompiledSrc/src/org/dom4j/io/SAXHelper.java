/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.xml.sax.SAXException;
/*   5:    */ import org.xml.sax.SAXNotRecognizedException;
/*   6:    */ import org.xml.sax.SAXNotSupportedException;
/*   7:    */ import org.xml.sax.XMLReader;
/*   8:    */ import org.xml.sax.helpers.XMLReaderFactory;
/*   9:    */ 
/*  10:    */ class SAXHelper
/*  11:    */ {
/*  12: 26 */   private static boolean loggedWarning = true;
/*  13:    */   
/*  14:    */   public static boolean setParserProperty(XMLReader reader, String propertyName, Object value)
/*  15:    */   {
/*  16:    */     try
/*  17:    */     {
/*  18: 34 */       reader.setProperty(propertyName, value);
/*  19:    */       
/*  20: 36 */       return true;
/*  21:    */     }
/*  22:    */     catch (SAXNotSupportedException e) {}catch (SAXNotRecognizedException e) {}
/*  23: 43 */     return false;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static boolean setParserFeature(XMLReader reader, String featureName, boolean value)
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 49 */       reader.setFeature(featureName, value);
/*  31:    */       
/*  32: 51 */       return true;
/*  33:    */     }
/*  34:    */     catch (SAXNotSupportedException e) {}catch (SAXNotRecognizedException e) {}
/*  35: 58 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static XMLReader createXMLReader(boolean validating)
/*  39:    */     throws SAXException
/*  40:    */   {
/*  41: 75 */     XMLReader reader = null;
/*  42: 77 */     if (reader == null) {
/*  43: 78 */       reader = createXMLReaderViaJAXP(validating, true);
/*  44:    */     }
/*  45: 81 */     if (reader == null) {
/*  46:    */       try
/*  47:    */       {
/*  48: 83 */         reader = XMLReaderFactory.createXMLReader();
/*  49:    */       }
/*  50:    */       catch (Exception e)
/*  51:    */       {
/*  52: 85 */         if (isVerboseErrorReporting())
/*  53:    */         {
/*  54: 88 */           System.out.println("Warning: Caught exception attempting to use SAX to load a SAX XMLReader ");
/*  55:    */           
/*  56: 90 */           System.out.println("Warning: Exception was: " + e);
/*  57: 91 */           System.out.println("Warning: I will print the stack trace then carry on using the default SAX parser");
/*  58:    */           
/*  59:    */ 
/*  60:    */ 
/*  61: 95 */           e.printStackTrace();
/*  62:    */         }
/*  63: 98 */         throw new SAXException(e);
/*  64:    */       }
/*  65:    */     }
/*  66:102 */     if (reader == null) {
/*  67:103 */       throw new SAXException("Couldn't create SAX reader");
/*  68:    */     }
/*  69:106 */     return reader;
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected static XMLReader createXMLReaderViaJAXP(boolean validating, boolean namespaceAware)
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:125 */       return JAXPHelper.createXMLReader(validating, namespaceAware);
/*  77:    */     }
/*  78:    */     catch (Throwable e)
/*  79:    */     {
/*  80:127 */       if (!loggedWarning)
/*  81:    */       {
/*  82:128 */         loggedWarning = true;
/*  83:130 */         if (isVerboseErrorReporting())
/*  84:    */         {
/*  85:133 */           System.out.println("Warning: Caught exception attempting to use JAXP to load a SAX XMLReader");
/*  86:    */           
/*  87:135 */           System.out.println("Warning: Exception was: " + e);
/*  88:136 */           e.printStackTrace();
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:141 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected static boolean isVerboseErrorReporting()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:146 */       String flag = System.getProperty("org.dom4j.verbose");
/* 100:148 */       if ((flag != null) && (flag.equalsIgnoreCase("true"))) {
/* 101:149 */         return true;
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (Exception e) {}
/* 105:156 */     return true;
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXHelper
 * JD-Core Version:    0.7.0.1
 */