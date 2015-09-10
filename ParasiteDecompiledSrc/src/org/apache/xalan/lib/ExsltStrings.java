/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import javax.xml.parsers.DocumentBuilder;
/*   5:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   6:    */ import javax.xml.parsers.ParserConfigurationException;
/*   7:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   8:    */ import org.apache.xpath.NodeSet;
/*   9:    */ import org.w3c.dom.Document;
/*  10:    */ import org.w3c.dom.Element;
/*  11:    */ import org.w3c.dom.Node;
/*  12:    */ import org.w3c.dom.NodeList;
/*  13:    */ import org.w3c.dom.Text;
/*  14:    */ 
/*  15:    */ public class ExsltStrings
/*  16:    */   extends ExsltBase
/*  17:    */ {
/*  18:    */   public static String align(String targetStr, String paddingStr, String type)
/*  19:    */   {
/*  20: 84 */     if (targetStr.length() >= paddingStr.length()) {
/*  21: 85 */       return targetStr.substring(0, paddingStr.length());
/*  22:    */     }
/*  23: 87 */     if (type.equals("right")) {
/*  24: 89 */       return paddingStr.substring(0, paddingStr.length() - targetStr.length()) + targetStr;
/*  25:    */     }
/*  26: 91 */     if (type.equals("center"))
/*  27:    */     {
/*  28: 93 */       int startIndex = (paddingStr.length() - targetStr.length()) / 2;
/*  29: 94 */       return paddingStr.substring(0, startIndex) + targetStr + paddingStr.substring(startIndex + targetStr.length());
/*  30:    */     }
/*  31: 99 */     return targetStr + paddingStr.substring(targetStr.length());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static String align(String targetStr, String paddingStr)
/*  35:    */   {
/*  36:108 */     return align(targetStr, paddingStr, "left");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static String concat(NodeList nl)
/*  40:    */   {
/*  41:121 */     StringBuffer sb = new StringBuffer();
/*  42:122 */     for (int i = 0; i < nl.getLength(); i++)
/*  43:    */     {
/*  44:124 */       Node node = nl.item(i);
/*  45:125 */       String value = ExsltBase.toString(node);
/*  46:127 */       if ((value != null) && (value.length() > 0)) {
/*  47:128 */         sb.append(value);
/*  48:    */       }
/*  49:    */     }
/*  50:131 */     return sb.toString();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String padding(double length, String pattern)
/*  54:    */   {
/*  55:151 */     if ((pattern == null) || (pattern.length() == 0)) {
/*  56:152 */       return "";
/*  57:    */     }
/*  58:154 */     StringBuffer sb = new StringBuffer();
/*  59:155 */     int len = (int)length;
/*  60:156 */     int numAdded = 0;
/*  61:157 */     int index = 0;
/*  62:158 */     while (numAdded < len)
/*  63:    */     {
/*  64:160 */       if (index == pattern.length()) {
/*  65:161 */         index = 0;
/*  66:    */       }
/*  67:163 */       sb.append(pattern.charAt(index));
/*  68:164 */       index++;
/*  69:165 */       numAdded++;
/*  70:    */     }
/*  71:168 */     return sb.toString();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static String padding(double length)
/*  75:    */   {
/*  76:176 */     return padding(length, " ");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static NodeList split(String str, String pattern)
/*  80:    */   {
/*  81:204 */     NodeSet resultSet = new NodeSet();
/*  82:205 */     resultSet.setShouldCacheNodes(true);
/*  83:    */     
/*  84:207 */     boolean done = false;
/*  85:208 */     int fromIndex = 0;
/*  86:209 */     int matchIndex = 0;
/*  87:210 */     String token = null;
/*  88:212 */     while ((!done) && (fromIndex < str.length()))
/*  89:    */     {
/*  90:214 */       matchIndex = str.indexOf(pattern, fromIndex);
/*  91:215 */       if (matchIndex >= 0)
/*  92:    */       {
/*  93:217 */         token = str.substring(fromIndex, matchIndex);
/*  94:218 */         fromIndex = matchIndex + pattern.length();
/*  95:    */       }
/*  96:    */       else
/*  97:    */       {
/*  98:222 */         done = true;
/*  99:223 */         token = str.substring(fromIndex);
/* 100:    */       }
/* 101:226 */       Document doc = DocumentHolder.m_doc;
/* 102:227 */       synchronized (doc)
/* 103:    */       {
/* 104:229 */         Element element = doc.createElement("token");
/* 105:230 */         Text text = doc.createTextNode(token);
/* 106:231 */         element.appendChild(text);
/* 107:232 */         resultSet.addNode(element);
/* 108:    */       }
/* 109:    */     }
/* 110:236 */     return resultSet;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static NodeList split(String str)
/* 114:    */   {
/* 115:244 */     return split(str, " ");
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static NodeList tokenize(String toTokenize, String delims)
/* 119:    */   {
/* 120:284 */     NodeSet resultSet = new NodeSet();
/* 121:286 */     if ((delims != null) && (delims.length() > 0))
/* 122:    */     {
/* 123:288 */       StringTokenizer lTokenizer = new StringTokenizer(toTokenize, delims);
/* 124:    */       
/* 125:290 */       ??? = DocumentHolder.m_doc;
/* 126:291 */       synchronized (???)
/* 127:    */       {
/* 128:293 */         while (lTokenizer.hasMoreTokens())
/* 129:    */         {
/* 130:295 */           Element element = ???.createElement("token");
/* 131:296 */           element.appendChild(???.createTextNode(lTokenizer.nextToken()));
/* 132:297 */           resultSet.addNode(element);
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:306 */       Document doc = DocumentHolder.m_doc;
/* 139:307 */       synchronized (doc)
/* 140:    */       {
/* 141:309 */         for (int i = 0; i < toTokenize.length(); i++)
/* 142:    */         {
/* 143:311 */           Element element = doc.createElement("token");
/* 144:312 */           element.appendChild(doc.createTextNode(toTokenize.substring(i, i + 1)));
/* 145:313 */           resultSet.addNode(element);
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:318 */     return resultSet;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static NodeList tokenize(String toTokenize)
/* 153:    */   {
/* 154:326 */     return tokenize(toTokenize, " \t\n\r");
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static class DocumentHolder
/* 158:    */   {
/* 159:    */     private static final Document m_doc;
/* 160:    */     
/* 161:    */     static
/* 162:    */     {
/* 163:    */       try
/* 164:    */       {
/* 165:344 */         m_doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/* 166:    */       }
/* 167:    */       catch (ParserConfigurationException pce)
/* 168:    */       {
/* 169:349 */         throw new WrappedRuntimeException(pce);
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltStrings
 * JD-Core Version:    0.7.0.1
 */