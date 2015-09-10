/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.Source;
/*   5:    */ import javax.xml.transform.Templates;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import javax.xml.transform.URIResolver;
/*   8:    */ import javax.xml.transform.sax.TemplatesHandler;
/*   9:    */ import org.apache.xalan.xsltc.compiler.CompilerException;
/*  10:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  11:    */ import org.apache.xalan.xsltc.compiler.SourceLoader;
/*  12:    */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*  13:    */ import org.apache.xalan.xsltc.compiler.SyntaxTreeNode;
/*  14:    */ import org.apache.xalan.xsltc.compiler.XSLTC;
/*  15:    */ import org.xml.sax.Attributes;
/*  16:    */ import org.xml.sax.ContentHandler;
/*  17:    */ import org.xml.sax.InputSource;
/*  18:    */ import org.xml.sax.Locator;
/*  19:    */ import org.xml.sax.SAXException;
/*  20:    */ 
/*  21:    */ public class TemplatesHandlerImpl
/*  22:    */   implements ContentHandler, TemplatesHandler, SourceLoader
/*  23:    */ {
/*  24:    */   private String _systemId;
/*  25:    */   private int _indentNumber;
/*  26: 68 */   private URIResolver _uriResolver = null;
/*  27: 74 */   private TransformerFactoryImpl _tfactory = null;
/*  28: 79 */   private Parser _parser = null;
/*  29: 84 */   private TemplatesImpl _templates = null;
/*  30:    */   
/*  31:    */   protected TemplatesHandlerImpl(int indentNumber, TransformerFactoryImpl tfactory)
/*  32:    */   {
/*  33: 92 */     this._indentNumber = indentNumber;
/*  34: 93 */     this._tfactory = tfactory;
/*  35:    */     
/*  36:    */ 
/*  37: 96 */     XSLTC xsltc = new XSLTC();
/*  38: 97 */     if (tfactory.getFeature("http://javax.xml.XMLConstants/feature/secure-processing")) {
/*  39: 98 */       xsltc.setSecureProcessing(true);
/*  40:    */     }
/*  41:100 */     if ("true".equals(tfactory.getAttribute("enable-inlining"))) {
/*  42:101 */       xsltc.setTemplateInlining(true);
/*  43:    */     } else {
/*  44:103 */       xsltc.setTemplateInlining(false);
/*  45:    */     }
/*  46:105 */     this._parser = xsltc.getParser();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getSystemId()
/*  50:    */   {
/*  51:115 */     return this._systemId;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setSystemId(String id)
/*  55:    */   {
/*  56:125 */     this._systemId = id;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setURIResolver(URIResolver resolver)
/*  60:    */   {
/*  61:132 */     this._uriResolver = resolver;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Templates getTemplates()
/*  65:    */   {
/*  66:145 */     return this._templates;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public InputSource loadSource(String href, String context, XSLTC xsltc)
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:160 */       Source source = this._uriResolver.resolve(href, context);
/*  74:161 */       if (source != null) {
/*  75:162 */         return Util.getInputSource(xsltc, source);
/*  76:    */       }
/*  77:    */     }
/*  78:    */     catch (TransformerException e) {}
/*  79:168 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void startDocument()
/*  83:    */   {
/*  84:177 */     XSLTC xsltc = this._parser.getXSLTC();
/*  85:178 */     xsltc.init();
/*  86:179 */     xsltc.setOutputType(2);
/*  87:180 */     this._parser.startDocument();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void endDocument()
/*  91:    */     throws SAXException
/*  92:    */   {
/*  93:187 */     this._parser.endDocument();
/*  94:    */     try
/*  95:    */     {
/*  96:191 */       XSLTC xsltc = this._parser.getXSLTC();
/*  97:195 */       if (this._systemId != null) {
/*  98:196 */         transletName = Util.baseName(this._systemId);
/*  99:    */       } else {
/* 100:199 */         transletName = (String)this._tfactory.getAttribute("translet-name");
/* 101:    */       }
/* 102:201 */       xsltc.setClassName(transletName);
/* 103:    */       
/* 104:    */ 
/* 105:204 */       String transletName = xsltc.getClassName();
/* 106:    */       
/* 107:206 */       Stylesheet stylesheet = null;
/* 108:207 */       SyntaxTreeNode root = this._parser.getDocumentRoot();
/* 109:210 */       if ((!this._parser.errorsFound()) && (root != null))
/* 110:    */       {
/* 111:212 */         stylesheet = this._parser.makeStylesheet(root);
/* 112:213 */         stylesheet.setSystemId(this._systemId);
/* 113:214 */         stylesheet.setParentStylesheet(null);
/* 114:216 */         if (xsltc.getTemplateInlining()) {
/* 115:217 */           stylesheet.setTemplateInlining(true);
/* 116:    */         } else {
/* 117:219 */           stylesheet.setTemplateInlining(false);
/* 118:    */         }
/* 119:222 */         if (this._uriResolver != null) {
/* 120:223 */           stylesheet.setSourceLoader(this);
/* 121:    */         }
/* 122:226 */         this._parser.setCurrentStylesheet(stylesheet);
/* 123:    */         
/* 124:    */ 
/* 125:229 */         xsltc.setStylesheet(stylesheet);
/* 126:    */         
/* 127:    */ 
/* 128:232 */         this._parser.createAST(stylesheet);
/* 129:    */       }
/* 130:236 */       if ((!this._parser.errorsFound()) && (stylesheet != null))
/* 131:    */       {
/* 132:237 */         stylesheet.setMultiDocument(xsltc.isMultiDocument());
/* 133:238 */         stylesheet.setHasIdCall(xsltc.hasIdCall());
/* 134:241 */         synchronized (xsltc.getClass())
/* 135:    */         {
/* 136:242 */           stylesheet.translate();
/* 137:    */         }
/* 138:    */       }
/* 139:246 */       if (!this._parser.errorsFound())
/* 140:    */       {
/* 141:248 */         byte[][] bytecodes = xsltc.getBytecodes();
/* 142:249 */         if (bytecodes != null)
/* 143:    */         {
/* 144:250 */           this._templates = new TemplatesImpl(xsltc.getBytecodes(), transletName, this._parser.getOutputProperties(), this._indentNumber, this._tfactory);
/* 145:255 */           if (this._uriResolver != null) {
/* 146:256 */             this._templates.setURIResolver(this._uriResolver);
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:261 */         StringBuffer errorMessage = new StringBuffer();
/* 153:262 */         Vector errors = this._parser.getErrors();
/* 154:263 */         int count = errors.size();
/* 155:264 */         for (int i = 0; i < count; i++)
/* 156:    */         {
/* 157:265 */           if (errorMessage.length() > 0) {
/* 158:266 */             errorMessage.append('\n');
/* 159:    */           }
/* 160:267 */           errorMessage.append(errors.elementAt(i).toString());
/* 161:    */         }
/* 162:269 */         throw new SAXException("JAXP_COMPILE_ERR", new TransformerException(errorMessage.toString()));
/* 163:    */       }
/* 164:    */     }
/* 165:    */     catch (CompilerException e)
/* 166:    */     {
/* 167:273 */       throw new SAXException("JAXP_COMPILE_ERR", e);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void startPrefixMapping(String prefix, String uri)
/* 172:    */   {
/* 173:281 */     this._parser.startPrefixMapping(prefix, uri);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void endPrefixMapping(String prefix)
/* 177:    */   {
/* 178:288 */     this._parser.endPrefixMapping(prefix);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void startElement(String uri, String localname, String qname, Attributes attributes)
/* 182:    */     throws SAXException
/* 183:    */   {
/* 184:297 */     this._parser.startElement(uri, localname, qname, attributes);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void endElement(String uri, String localname, String qname)
/* 188:    */   {
/* 189:304 */     this._parser.endElement(uri, localname, qname);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void characters(char[] ch, int start, int length)
/* 193:    */   {
/* 194:311 */     this._parser.characters(ch, start, length);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void processingInstruction(String name, String value)
/* 198:    */   {
/* 199:318 */     this._parser.processingInstruction(name, value);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 203:    */   {
/* 204:325 */     this._parser.ignorableWhitespace(ch, start, length);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void skippedEntity(String name)
/* 208:    */   {
/* 209:332 */     this._parser.skippedEntity(name);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setDocumentLocator(Locator locator)
/* 213:    */   {
/* 214:339 */     setSystemId(locator.getSystemId());
/* 215:340 */     this._parser.setDocumentLocator(locator);
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.TemplatesHandlerImpl
 * JD-Core Version:    0.7.0.1
 */