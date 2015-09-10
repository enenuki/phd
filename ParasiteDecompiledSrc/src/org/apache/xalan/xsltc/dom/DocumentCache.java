/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.net.URLConnection;
/*   7:    */ import java.net.URLDecoder;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import javax.xml.parsers.ParserConfigurationException;
/*  11:    */ import javax.xml.parsers.SAXParser;
/*  12:    */ import javax.xml.parsers.SAXParserFactory;
/*  13:    */ import javax.xml.transform.TransformerException;
/*  14:    */ import javax.xml.transform.sax.SAXSource;
/*  15:    */ import org.apache.xalan.xsltc.DOM;
/*  16:    */ import org.apache.xalan.xsltc.DOMCache;
/*  17:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*  18:    */ import org.apache.xalan.xsltc.Translet;
/*  19:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*  20:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*  21:    */ import org.apache.xml.utils.SystemIDResolver;
/*  22:    */ import org.xml.sax.InputSource;
/*  23:    */ import org.xml.sax.SAXException;
/*  24:    */ import org.xml.sax.XMLReader;
/*  25:    */ 
/*  26:    */ public final class DocumentCache
/*  27:    */   implements DOMCache
/*  28:    */ {
/*  29:    */   private int _size;
/*  30:    */   private Hashtable _references;
/*  31:    */   private String[] _URIs;
/*  32:    */   private int _count;
/*  33:    */   private int _current;
/*  34:    */   private SAXParser _parser;
/*  35:    */   private XMLReader _reader;
/*  36:    */   private XSLTCDTMManager _dtmManager;
/*  37:    */   private static final int REFRESH_INTERVAL = 1000;
/*  38:    */   
/*  39:    */   public final class CachedDocument
/*  40:    */   {
/*  41:    */     private long _firstReferenced;
/*  42:    */     private long _lastReferenced;
/*  43:    */     private long _accessCount;
/*  44:    */     private long _lastModified;
/*  45:    */     private long _lastChecked;
/*  46:    */     private long _buildTime;
/*  47: 81 */     private DOMEnhancedForDTM _dom = null;
/*  48:    */     
/*  49:    */     public CachedDocument(String uri)
/*  50:    */     {
/*  51: 88 */       long stamp = System.currentTimeMillis();
/*  52: 89 */       this._firstReferenced = stamp;
/*  53: 90 */       this._lastReferenced = stamp;
/*  54: 91 */       this._accessCount = 0L;
/*  55: 92 */       loadDocument(uri);
/*  56:    */       
/*  57: 94 */       this._buildTime = (System.currentTimeMillis() - stamp);
/*  58:    */     }
/*  59:    */     
/*  60:    */     public void loadDocument(String uri)
/*  61:    */     {
/*  62:    */       try
/*  63:    */       {
/*  64:103 */         long stamp = System.currentTimeMillis();
/*  65:104 */         this._dom = ((DOMEnhancedForDTM)DocumentCache.this._dtmManager.getDTM(new SAXSource(DocumentCache.this._reader, new InputSource(uri)), false, null, true, false));
/*  66:    */         
/*  67:    */ 
/*  68:107 */         this._dom.setDocumentURI(uri);
/*  69:    */         
/*  70:    */ 
/*  71:    */ 
/*  72:111 */         long thisTime = System.currentTimeMillis() - stamp;
/*  73:112 */         if (this._buildTime > 0L) {
/*  74:113 */           this._buildTime = (this._buildTime + thisTime >>> 1);
/*  75:    */         } else {
/*  76:115 */           this._buildTime = thisTime;
/*  77:    */         }
/*  78:    */       }
/*  79:    */       catch (Exception e)
/*  80:    */       {
/*  81:118 */         this._dom = null;
/*  82:    */       }
/*  83:    */     }
/*  84:    */     
/*  85:    */     public DOM getDocument()
/*  86:    */     {
/*  87:122 */       return this._dom;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public long getFirstReferenced()
/*  91:    */     {
/*  92:124 */       return this._firstReferenced;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public long getLastReferenced()
/*  96:    */     {
/*  97:126 */       return this._lastReferenced;
/*  98:    */     }
/*  99:    */     
/* 100:    */     public long getAccessCount()
/* 101:    */     {
/* 102:128 */       return this._accessCount;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public void incAccessCount()
/* 106:    */     {
/* 107:130 */       this._accessCount += 1L;
/* 108:    */     }
/* 109:    */     
/* 110:    */     public long getLastModified()
/* 111:    */     {
/* 112:132 */       return this._lastModified;
/* 113:    */     }
/* 114:    */     
/* 115:    */     public void setLastModified(long t)
/* 116:    */     {
/* 117:134 */       this._lastModified = t;
/* 118:    */     }
/* 119:    */     
/* 120:    */     public long getLatency()
/* 121:    */     {
/* 122:136 */       return this._buildTime;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public long getLastChecked()
/* 126:    */     {
/* 127:138 */       return this._lastChecked;
/* 128:    */     }
/* 129:    */     
/* 130:    */     public void setLastChecked(long t)
/* 131:    */     {
/* 132:140 */       this._lastChecked = t;
/* 133:    */     }
/* 134:    */     
/* 135:    */     public long getEstimatedSize()
/* 136:    */     {
/* 137:143 */       if (this._dom != null) {
/* 138:144 */         return this._dom.getSize() << 5;
/* 139:    */       }
/* 140:146 */       return 0L;
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public DocumentCache(int size)
/* 145:    */     throws SAXException
/* 146:    */   {
/* 147:155 */     this(size, null);
/* 148:    */     try
/* 149:    */     {
/* 150:157 */       this._dtmManager = ((XSLTCDTMManager)XSLTCDTMManager.getDTMManagerClass().newInstance());
/* 151:    */     }
/* 152:    */     catch (Exception e)
/* 153:    */     {
/* 154:160 */       throw new SAXException(e);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public DocumentCache(int size, XSLTCDTMManager dtmManager)
/* 159:    */     throws SAXException
/* 160:    */   {
/* 161:168 */     this._dtmManager = dtmManager;
/* 162:169 */     this._count = 0;
/* 163:170 */     this._current = 0;
/* 164:171 */     this._size = size;
/* 165:172 */     this._references = new Hashtable(this._size + 2);
/* 166:173 */     this._URIs = new String[this._size];
/* 167:    */     try
/* 168:    */     {
/* 169:177 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/* 170:    */       try
/* 171:    */       {
/* 172:179 */         factory.setFeature("http://xml.org/sax/features/namespaces", true);
/* 173:    */       }
/* 174:    */       catch (Exception e)
/* 175:    */       {
/* 176:182 */         factory.setNamespaceAware(true);
/* 177:    */       }
/* 178:184 */       this._parser = factory.newSAXParser();
/* 179:185 */       this._reader = this._parser.getXMLReader();
/* 180:    */     }
/* 181:    */     catch (ParserConfigurationException e)
/* 182:    */     {
/* 183:188 */       BasisLibrary.runTimeError("NAMESPACES_SUPPORT_ERR");
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private final long getLastModified(String uri)
/* 188:    */   {
/* 189:    */     try
/* 190:    */     {
/* 191:197 */       URL url = new URL(uri);
/* 192:198 */       URLConnection connection = url.openConnection();
/* 193:199 */       long timestamp = connection.getLastModified();
/* 194:    */       File localfile;
/* 195:201 */       if ((timestamp == 0L) && 
/* 196:202 */         ("file".equals(url.getProtocol()))) {
/* 197:203 */         localfile = new File(URLDecoder.decode(url.getFile()));
/* 198:    */       }
/* 199:204 */       return localfile.lastModified();
/* 200:    */     }
/* 201:    */     catch (Exception e) {}
/* 202:211 */     return System.currentTimeMillis();
/* 203:    */   }
/* 204:    */   
/* 205:    */   private CachedDocument lookupDocument(String uri)
/* 206:    */   {
/* 207:219 */     return (CachedDocument)this._references.get(uri);
/* 208:    */   }
/* 209:    */   
/* 210:    */   private synchronized void insertDocument(String uri, CachedDocument doc)
/* 211:    */   {
/* 212:226 */     if (this._count < this._size)
/* 213:    */     {
/* 214:228 */       this._URIs[(this._count++)] = uri;
/* 215:229 */       this._current = 0;
/* 216:    */     }
/* 217:    */     else
/* 218:    */     {
/* 219:233 */       this._references.remove(this._URIs[this._current]);
/* 220:    */       
/* 221:235 */       this._URIs[this._current] = uri;
/* 222:236 */       if (++this._current >= this._size) {
/* 223:236 */         this._current = 0;
/* 224:    */       }
/* 225:    */     }
/* 226:238 */     this._references.put(uri, doc);
/* 227:    */   }
/* 228:    */   
/* 229:    */   private synchronized void replaceDocument(String uri, CachedDocument doc)
/* 230:    */   {
/* 231:245 */     CachedDocument old = (CachedDocument)this._references.get(uri);
/* 232:246 */     if (doc == null) {
/* 233:247 */       insertDocument(uri, doc);
/* 234:    */     } else {
/* 235:249 */       this._references.put(uri, doc);
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public DOM retrieveDocument(String baseURI, String href, Translet trs)
/* 240:    */   {
/* 241:259 */     String uri = href;
/* 242:260 */     if ((baseURI != null) && (!baseURI.equals(""))) {
/* 243:    */       try
/* 244:    */       {
/* 245:262 */         uri = SystemIDResolver.getAbsoluteURI(uri, baseURI);
/* 246:    */       }
/* 247:    */       catch (TransformerException te) {}
/* 248:    */     }
/* 249:    */     CachedDocument doc;
/* 250:269 */     if ((doc = lookupDocument(uri)) == null)
/* 251:    */     {
/* 252:270 */       doc = new CachedDocument(uri);
/* 253:271 */       if (doc == null) {
/* 254:271 */         return null;
/* 255:    */       }
/* 256:272 */       doc.setLastModified(getLastModified(uri));
/* 257:273 */       insertDocument(uri, doc);
/* 258:    */     }
/* 259:    */     else
/* 260:    */     {
/* 261:277 */       long now = System.currentTimeMillis();
/* 262:278 */       long chk = doc.getLastChecked();
/* 263:279 */       doc.setLastChecked(now);
/* 264:281 */       if (now > chk + 1000L)
/* 265:    */       {
/* 266:282 */         doc.setLastChecked(now);
/* 267:283 */         long last = getLastModified(uri);
/* 268:285 */         if (last > doc.getLastModified())
/* 269:    */         {
/* 270:286 */           doc = new CachedDocument(uri);
/* 271:287 */           if (doc == null) {
/* 272:287 */             return null;
/* 273:    */           }
/* 274:288 */           doc.setLastModified(getLastModified(uri));
/* 275:289 */           replaceDocument(uri, doc);
/* 276:    */         }
/* 277:    */       }
/* 278:    */     }
/* 279:296 */     DOM dom = doc.getDocument();
/* 280:300 */     if (dom == null) {
/* 281:300 */       return null;
/* 282:    */     }
/* 283:302 */     doc.incAccessCount();
/* 284:    */     
/* 285:304 */     AbstractTranslet translet = (AbstractTranslet)trs;
/* 286:    */     
/* 287:    */ 
/* 288:    */ 
/* 289:308 */     translet.prepassDocument(dom);
/* 290:    */     
/* 291:310 */     return doc.getDocument();
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void getStatistics(PrintWriter out)
/* 295:    */   {
/* 296:317 */     out.println("<h2>DOM cache statistics</h2><center><table border=\"2\"><tr><td><b>Document URI</b></td><td><center><b>Build time</b></center></td><td><center><b>Access count</b></center></td><td><center><b>Last accessed</b></center></td><td><center><b>Last modified</b></center></td></tr>");
/* 297:324 */     for (int i = 0; i < this._count; i++)
/* 298:    */     {
/* 299:325 */       CachedDocument doc = (CachedDocument)this._references.get(this._URIs[i]);
/* 300:326 */       out.print("<tr><td><a href=\"" + this._URIs[i] + "\">" + "<font size=-1>" + this._URIs[i] + "</font></a></td>");
/* 301:    */       
/* 302:328 */       out.print("<td><center>" + doc.getLatency() + "ms</center></td>");
/* 303:329 */       out.print("<td><center>" + doc.getAccessCount() + "</center></td>");
/* 304:330 */       out.print("<td><center>" + new Date(doc.getLastReferenced()) + "</center></td>");
/* 305:    */       
/* 306:332 */       out.print("<td><center>" + new Date(doc.getLastModified()) + "</center></td>");
/* 307:    */       
/* 308:334 */       out.println("</tr>");
/* 309:    */     }
/* 310:337 */     out.println("</table></center>");
/* 311:    */   }
/* 312:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.DocumentCache
 * JD-Core Version:    0.7.0.1
 */