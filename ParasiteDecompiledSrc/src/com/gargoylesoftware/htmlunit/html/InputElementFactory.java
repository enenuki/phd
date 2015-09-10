/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.xml.sax.Attributes;
/*   9:    */ 
/*  10:    */ public final class InputElementFactory
/*  11:    */   implements IElementFactory
/*  12:    */ {
/*  13: 39 */   private static final Log LOG = LogFactory.getLog(InputElementFactory.class);
/*  14: 42 */   public static final InputElementFactory instance = new InputElementFactory();
/*  15:    */   
/*  16:    */   public HtmlElement createElement(SgmlPage page, String tagName, Attributes attributes)
/*  17:    */   {
/*  18: 61 */     return createElementNS(page, null, tagName, attributes);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public HtmlElement createElementNS(SgmlPage page, String namespaceURI, String qualifiedName, Attributes attributes)
/*  22:    */   {
/*  23: 70 */     Map<String, DomAttr> attributeMap = DefaultElementFactory.setAttributes(page, attributes);
/*  24: 71 */     if (attributeMap == null) {
/*  25: 72 */       attributeMap = new HashMap();
/*  26:    */     }
/*  27: 75 */     String type = null;
/*  28: 76 */     if (attributes != null) {
/*  29: 77 */       type = attributes.getValue("type");
/*  30:    */     }
/*  31: 79 */     if (type == null)
/*  32:    */     {
/*  33: 80 */       type = "";
/*  34:    */     }
/*  35:    */     else
/*  36:    */     {
/*  37: 83 */       type = type.toLowerCase();
/*  38: 84 */       ((DomAttr)attributeMap.get("type")).setValue(type);
/*  39:    */     }
/*  40:    */     HtmlInput result;
/*  41:    */     HtmlInput result;
/*  42: 88 */     if (type.length() == 0)
/*  43:    */     {
/*  44: 92 */       HtmlElement.addAttributeToMap(page, attributeMap, null, "type", "text");
/*  45: 93 */       result = new HtmlTextInput(namespaceURI, qualifiedName, page, attributeMap);
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49:    */       HtmlInput result;
/*  50: 95 */       if ("submit".equals(type))
/*  51:    */       {
/*  52: 96 */         result = new HtmlSubmitInput(namespaceURI, qualifiedName, page, attributeMap);
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56:    */         HtmlInput result;
/*  57: 98 */         if ("checkbox".equals(type))
/*  58:    */         {
/*  59: 99 */           result = new HtmlCheckBoxInput(namespaceURI, qualifiedName, page, attributeMap);
/*  60:    */         }
/*  61:    */         else
/*  62:    */         {
/*  63:    */           HtmlInput result;
/*  64:101 */           if ("radio".equals(type))
/*  65:    */           {
/*  66:102 */             result = new HtmlRadioButtonInput(namespaceURI, qualifiedName, page, attributeMap);
/*  67:    */           }
/*  68:    */           else
/*  69:    */           {
/*  70:    */             HtmlInput result;
/*  71:104 */             if ("text".equals(type))
/*  72:    */             {
/*  73:105 */               result = new HtmlTextInput(namespaceURI, qualifiedName, page, attributeMap);
/*  74:    */             }
/*  75:    */             else
/*  76:    */             {
/*  77:    */               HtmlInput result;
/*  78:107 */               if ("hidden".equals(type))
/*  79:    */               {
/*  80:108 */                 result = new HtmlHiddenInput(namespaceURI, qualifiedName, page, attributeMap);
/*  81:    */               }
/*  82:    */               else
/*  83:    */               {
/*  84:    */                 HtmlInput result;
/*  85:110 */                 if ("password".equals(type))
/*  86:    */                 {
/*  87:111 */                   result = new HtmlPasswordInput(namespaceURI, qualifiedName, page, attributeMap);
/*  88:    */                 }
/*  89:    */                 else
/*  90:    */                 {
/*  91:    */                   HtmlInput result;
/*  92:113 */                   if ("image".equals(type))
/*  93:    */                   {
/*  94:114 */                     result = new HtmlImageInput(namespaceURI, qualifiedName, page, attributeMap);
/*  95:    */                   }
/*  96:    */                   else
/*  97:    */                   {
/*  98:    */                     HtmlInput result;
/*  99:116 */                     if ("reset".equals(type))
/* 100:    */                     {
/* 101:117 */                       result = new HtmlResetInput(namespaceURI, qualifiedName, page, attributeMap);
/* 102:    */                     }
/* 103:    */                     else
/* 104:    */                     {
/* 105:    */                       HtmlInput result;
/* 106:119 */                       if ("button".equals(type))
/* 107:    */                       {
/* 108:120 */                         result = new HtmlButtonInput(namespaceURI, qualifiedName, page, attributeMap);
/* 109:    */                       }
/* 110:    */                       else
/* 111:    */                       {
/* 112:    */                         HtmlInput result;
/* 113:122 */                         if ("file".equals(type))
/* 114:    */                         {
/* 115:123 */                           result = new HtmlFileInput(namespaceURI, qualifiedName, page, attributeMap);
/* 116:    */                         }
/* 117:    */                         else
/* 118:    */                         {
/* 119:126 */                           LOG.info("Bad input type: \"" + type + "\", creating a text input");
/* 120:127 */                           result = new HtmlTextInput(namespaceURI, qualifiedName, page, attributeMap);
/* 121:    */                         }
/* 122:    */                       }
/* 123:    */                     }
/* 124:    */                   }
/* 125:    */                 }
/* 126:    */               }
/* 127:    */             }
/* 128:    */           }
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:129 */     return result;
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.InputElementFactory
 * JD-Core Version:    0.7.0.1
 */