/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileReader;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.io.Reader;
/*  10:    */ import java.net.URL;
/*  11:    */ import org.dom4j.Document;
/*  12:    */ import org.dom4j.DocumentException;
/*  13:    */ import org.dom4j.DocumentFactory;
/*  14:    */ import org.dom4j.Element;
/*  15:    */ import org.dom4j.ElementHandler;
/*  16:    */ import org.dom4j.xpp.ProxyXmlStartTag;
/*  17:    */ import org.gjt.xpp.XmlEndTag;
/*  18:    */ import org.gjt.xpp.XmlPullParser;
/*  19:    */ import org.gjt.xpp.XmlPullParserException;
/*  20:    */ import org.gjt.xpp.XmlPullParserFactory;
/*  21:    */ 
/*  22:    */ public class XPPReader
/*  23:    */ {
/*  24:    */   private DocumentFactory factory;
/*  25:    */   private XmlPullParser xppParser;
/*  26:    */   private XmlPullParserFactory xppFactory;
/*  27:    */   private DispatchHandler dispatchHandler;
/*  28:    */   
/*  29:    */   public XPPReader() {}
/*  30:    */   
/*  31:    */   public XPPReader(DocumentFactory factory)
/*  32:    */   {
/*  33: 59 */     this.factory = factory;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Document read(File file)
/*  37:    */     throws DocumentException, IOException, XmlPullParserException
/*  38:    */   {
/*  39: 81 */     String systemID = file.getAbsolutePath();
/*  40:    */     
/*  41: 83 */     return read(new BufferedReader(new FileReader(file)), systemID);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Document read(URL url)
/*  45:    */     throws DocumentException, IOException, XmlPullParserException
/*  46:    */   {
/*  47:105 */     String systemID = url.toExternalForm();
/*  48:    */     
/*  49:107 */     return read(createReader(url.openStream()), systemID);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Document read(String systemID)
/*  53:    */     throws DocumentException, IOException, XmlPullParserException
/*  54:    */   {
/*  55:137 */     if (systemID.indexOf(':') >= 0) {
/*  56:139 */       return read(new URL(systemID));
/*  57:    */     }
/*  58:142 */     return read(new File(systemID));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Document read(InputStream in)
/*  62:    */     throws DocumentException, IOException, XmlPullParserException
/*  63:    */   {
/*  64:165 */     return read(createReader(in));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Document read(Reader reader)
/*  68:    */     throws DocumentException, IOException, XmlPullParserException
/*  69:    */   {
/*  70:187 */     getXPPParser().setInput(reader);
/*  71:    */     
/*  72:189 */     return parseDocument();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Document read(char[] text)
/*  76:    */     throws DocumentException, IOException, XmlPullParserException
/*  77:    */   {
/*  78:211 */     getXPPParser().setInput(text);
/*  79:    */     
/*  80:213 */     return parseDocument();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Document read(InputStream in, String systemID)
/*  84:    */     throws DocumentException, IOException, XmlPullParserException
/*  85:    */   {
/*  86:237 */     return read(createReader(in), systemID);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Document read(Reader reader, String systemID)
/*  90:    */     throws DocumentException, IOException, XmlPullParserException
/*  91:    */   {
/*  92:261 */     Document document = read(reader);
/*  93:262 */     document.setName(systemID);
/*  94:    */     
/*  95:264 */     return document;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public XmlPullParser getXPPParser()
/*  99:    */     throws XmlPullParserException
/* 100:    */   {
/* 101:270 */     if (this.xppParser == null) {
/* 102:271 */       this.xppParser = getXPPFactory().newPullParser();
/* 103:    */     }
/* 104:274 */     return this.xppParser;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public XmlPullParserFactory getXPPFactory()
/* 108:    */     throws XmlPullParserException
/* 109:    */   {
/* 110:278 */     if (this.xppFactory == null) {
/* 111:279 */       this.xppFactory = XmlPullParserFactory.newInstance();
/* 112:    */     }
/* 113:282 */     return this.xppFactory;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setXPPFactory(XmlPullParserFactory xPPFactory)
/* 117:    */   {
/* 118:286 */     this.xppFactory = xPPFactory;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public DocumentFactory getDocumentFactory()
/* 122:    */   {
/* 123:296 */     if (this.factory == null) {
/* 124:297 */       this.factory = DocumentFactory.getInstance();
/* 125:    */     }
/* 126:300 */     return this.factory;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/* 130:    */   {
/* 131:315 */     this.factory = documentFactory;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void addHandler(String path, ElementHandler handler)
/* 135:    */   {
/* 136:329 */     getDispatchHandler().addHandler(path, handler);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void removeHandler(String path)
/* 140:    */   {
/* 141:340 */     getDispatchHandler().removeHandler(path);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setDefaultHandler(ElementHandler handler)
/* 145:    */   {
/* 146:353 */     getDispatchHandler().setDefaultHandler(handler);
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected Document parseDocument()
/* 150:    */     throws DocumentException, IOException, XmlPullParserException
/* 151:    */   {
/* 152:360 */     Document document = getDocumentFactory().createDocument();
/* 153:361 */     Element parent = null;
/* 154:362 */     XmlPullParser parser = getXPPParser();
/* 155:363 */     parser.setNamespaceAware(true);
/* 156:    */     
/* 157:365 */     ProxyXmlStartTag startTag = new ProxyXmlStartTag();
/* 158:366 */     XmlEndTag endTag = this.xppFactory.newEndTag();
/* 159:    */     for (;;)
/* 160:    */     {
/* 161:369 */       int type = parser.next();
/* 162:371 */       switch (type)
/* 163:    */       {
/* 164:    */       case 1: 
/* 165:373 */         return document;
/* 166:    */       case 2: 
/* 167:376 */         parser.readStartTag(startTag);
/* 168:    */         
/* 169:378 */         Element newElement = startTag.getElement();
/* 170:380 */         if (parent != null) {
/* 171:381 */           parent.add(newElement);
/* 172:    */         } else {
/* 173:383 */           document.add(newElement);
/* 174:    */         }
/* 175:386 */         parent = newElement;
/* 176:    */         
/* 177:388 */         break;
/* 178:    */       case 3: 
/* 179:392 */         parser.readEndTag(endTag);
/* 180:394 */         if (parent != null) {
/* 181:395 */           parent = parent.getParent();
/* 182:    */         }
/* 183:    */         break;
/* 184:    */       case 4: 
/* 185:402 */         String text = parser.readContent();
/* 186:404 */         if (parent != null)
/* 187:    */         {
/* 188:405 */           parent.addText(text);
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:407 */           String msg = "Cannot have text content outside of the root document";
/* 193:    */           
/* 194:409 */           throw new DocumentException(msg);
/* 195:    */         }
/* 196:    */         break;
/* 197:    */       default: 
/* 198:416 */         throw new DocumentException("Error: unknown type: " + type);
/* 199:    */       }
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected DispatchHandler getDispatchHandler()
/* 204:    */   {
/* 205:422 */     if (this.dispatchHandler == null) {
/* 206:423 */       this.dispatchHandler = new DispatchHandler();
/* 207:    */     }
/* 208:426 */     return this.dispatchHandler;
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected void setDispatchHandler(DispatchHandler dispatchHandler)
/* 212:    */   {
/* 213:430 */     this.dispatchHandler = dispatchHandler;
/* 214:    */   }
/* 215:    */   
/* 216:    */   protected Reader createReader(InputStream in)
/* 217:    */     throws IOException
/* 218:    */   {
/* 219:445 */     return new BufferedReader(new InputStreamReader(in));
/* 220:    */   }
/* 221:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.XPPReader
 * JD-Core Version:    0.7.0.1
 */