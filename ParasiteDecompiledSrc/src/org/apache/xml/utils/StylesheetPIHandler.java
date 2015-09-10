/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.Source;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import javax.xml.transform.URIResolver;
/*   8:    */ import javax.xml.transform.sax.SAXSource;
/*   9:    */ import org.xml.sax.Attributes;
/*  10:    */ import org.xml.sax.InputSource;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ import org.xml.sax.helpers.DefaultHandler;
/*  13:    */ 
/*  14:    */ public class StylesheetPIHandler
/*  15:    */   extends DefaultHandler
/*  16:    */ {
/*  17:    */   String m_baseID;
/*  18:    */   String m_media;
/*  19:    */   String m_title;
/*  20:    */   String m_charset;
/*  21: 56 */   Vector m_stylesheets = new Vector();
/*  22:    */   URIResolver m_uriResolver;
/*  23:    */   
/*  24:    */   public void setURIResolver(URIResolver resolver)
/*  25:    */   {
/*  26: 75 */     this.m_uriResolver = resolver;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public URIResolver getURIResolver()
/*  30:    */   {
/*  31: 86 */     return this.m_uriResolver;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public StylesheetPIHandler(String baseID, String media, String title, String charset)
/*  35:    */   {
/*  36:103 */     this.m_baseID = baseID;
/*  37:104 */     this.m_media = media;
/*  38:105 */     this.m_title = title;
/*  39:106 */     this.m_charset = charset;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Source getAssociatedStylesheet()
/*  43:    */   {
/*  44:118 */     int sz = this.m_stylesheets.size();
/*  45:120 */     if (sz > 0)
/*  46:    */     {
/*  47:122 */       Source source = (Source)this.m_stylesheets.elementAt(sz - 1);
/*  48:123 */       return source;
/*  49:    */     }
/*  50:126 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void processingInstruction(String target, String data)
/*  54:    */     throws SAXException
/*  55:    */   {
/*  56:144 */     if (target.equals("xml-stylesheet"))
/*  57:    */     {
/*  58:146 */       String href = null;
/*  59:147 */       String type = null;
/*  60:148 */       String title = null;
/*  61:149 */       String media = null;
/*  62:150 */       String charset = null;
/*  63:151 */       boolean alternate = false;
/*  64:152 */       StringTokenizer tokenizer = new StringTokenizer(data, " \t=\n", true);
/*  65:153 */       boolean lookedAhead = false;
/*  66:154 */       Source source = null;
/*  67:    */       
/*  68:156 */       String token = "";
/*  69:157 */       while (tokenizer.hasMoreTokens())
/*  70:    */       {
/*  71:159 */         if (!lookedAhead) {
/*  72:160 */           token = tokenizer.nextToken();
/*  73:    */         } else {
/*  74:162 */           lookedAhead = false;
/*  75:    */         }
/*  76:163 */         if ((!tokenizer.hasMoreTokens()) || ((!token.equals(" ")) && (!token.equals("\t")) && (!token.equals("="))))
/*  77:    */         {
/*  78:167 */           String name = token;
/*  79:168 */           if (name.equals("type"))
/*  80:    */           {
/*  81:170 */             token = tokenizer.nextToken();
/*  82:171 */             while ((tokenizer.hasMoreTokens()) && ((token.equals(" ")) || (token.equals("\t")) || (token.equals("=")))) {
/*  83:173 */               token = tokenizer.nextToken();
/*  84:    */             }
/*  85:174 */             type = token.substring(1, token.length() - 1);
/*  86:    */           }
/*  87:177 */           else if (name.equals("href"))
/*  88:    */           {
/*  89:179 */             token = tokenizer.nextToken();
/*  90:180 */             while ((tokenizer.hasMoreTokens()) && ((token.equals(" ")) || (token.equals("\t")) || (token.equals("=")))) {
/*  91:182 */               token = tokenizer.nextToken();
/*  92:    */             }
/*  93:183 */             href = token;
/*  94:184 */             if (tokenizer.hasMoreTokens())
/*  95:    */             {
/*  96:186 */               token = tokenizer.nextToken();
/*  97:195 */               while ((token.equals("=")) && (tokenizer.hasMoreTokens()))
/*  98:    */               {
/*  99:197 */                 href = href + token + tokenizer.nextToken();
/* 100:198 */                 if (!tokenizer.hasMoreTokens()) {
/* 101:    */                   break;
/* 102:    */                 }
/* 103:200 */                 token = tokenizer.nextToken();
/* 104:201 */                 lookedAhead = true;
/* 105:    */               }
/* 106:    */             }
/* 107:209 */             href = href.substring(1, href.length() - 1);
/* 108:    */             try
/* 109:    */             {
/* 110:213 */               if (this.m_uriResolver != null)
/* 111:    */               {
/* 112:215 */                 source = this.m_uriResolver.resolve(href, this.m_baseID);
/* 113:    */               }
/* 114:    */               else
/* 115:    */               {
/* 116:219 */                 href = SystemIDResolver.getAbsoluteURI(href, this.m_baseID);
/* 117:220 */                 source = new SAXSource(new InputSource(href));
/* 118:    */               }
/* 119:    */             }
/* 120:    */             catch (TransformerException te)
/* 121:    */             {
/* 122:225 */               throw new SAXException(te);
/* 123:    */             }
/* 124:    */           }
/* 125:228 */           else if (name.equals("title"))
/* 126:    */           {
/* 127:230 */             token = tokenizer.nextToken();
/* 128:231 */             while ((tokenizer.hasMoreTokens()) && ((token.equals(" ")) || (token.equals("\t")) || (token.equals("=")))) {
/* 129:233 */               token = tokenizer.nextToken();
/* 130:    */             }
/* 131:234 */             title = token.substring(1, token.length() - 1);
/* 132:    */           }
/* 133:236 */           else if (name.equals("media"))
/* 134:    */           {
/* 135:238 */             token = tokenizer.nextToken();
/* 136:239 */             while ((tokenizer.hasMoreTokens()) && ((token.equals(" ")) || (token.equals("\t")) || (token.equals("=")))) {
/* 137:241 */               token = tokenizer.nextToken();
/* 138:    */             }
/* 139:242 */             media = token.substring(1, token.length() - 1);
/* 140:    */           }
/* 141:244 */           else if (name.equals("charset"))
/* 142:    */           {
/* 143:246 */             token = tokenizer.nextToken();
/* 144:247 */             while ((tokenizer.hasMoreTokens()) && ((token.equals(" ")) || (token.equals("\t")) || (token.equals("=")))) {
/* 145:249 */               token = tokenizer.nextToken();
/* 146:    */             }
/* 147:250 */             charset = token.substring(1, token.length() - 1);
/* 148:    */           }
/* 149:252 */           else if (name.equals("alternate"))
/* 150:    */           {
/* 151:254 */             token = tokenizer.nextToken();
/* 152:255 */             while ((tokenizer.hasMoreTokens()) && ((token.equals(" ")) || (token.equals("\t")) || (token.equals("=")))) {
/* 153:257 */               token = tokenizer.nextToken();
/* 154:    */             }
/* 155:258 */             alternate = token.substring(1, token.length() - 1).equals("yes");
/* 156:    */           }
/* 157:    */         }
/* 158:    */       }
/* 159:264 */       if ((null != type) && ((type.equals("text/xsl")) || (type.equals("text/xml")) || (type.equals("application/xml+xslt"))) && (null != href))
/* 160:    */       {
/* 161:268 */         if (null != this.m_media) {
/* 162:270 */           if (null != media)
/* 163:    */           {
/* 164:272 */             if (media.equals(this.m_media)) {}
/* 165:    */           }
/* 166:    */           else {
/* 167:276 */             return;
/* 168:    */           }
/* 169:    */         }
/* 170:279 */         if (null != this.m_charset) {
/* 171:281 */           if (null != charset)
/* 172:    */           {
/* 173:283 */             if (charset.equals(this.m_charset)) {}
/* 174:    */           }
/* 175:    */           else {
/* 176:287 */             return;
/* 177:    */           }
/* 178:    */         }
/* 179:290 */         if (null != this.m_title) {
/* 180:292 */           if (null != title)
/* 181:    */           {
/* 182:294 */             if (title.equals(this.m_title)) {}
/* 183:    */           }
/* 184:    */           else {
/* 185:298 */             return;
/* 186:    */           }
/* 187:    */         }
/* 188:301 */         this.m_stylesheets.addElement(source);
/* 189:    */       }
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
/* 194:    */     throws SAXException
/* 195:    */   {
/* 196:324 */     throw new StopParseException();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setBaseId(String baseId)
/* 200:    */   {
/* 201:333 */     this.m_baseID = baseId;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public String getBaseId()
/* 205:    */   {
/* 206:337 */     return this.m_baseID;
/* 207:    */   }
/* 208:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StylesheetPIHandler
 * JD-Core Version:    0.7.0.1
 */