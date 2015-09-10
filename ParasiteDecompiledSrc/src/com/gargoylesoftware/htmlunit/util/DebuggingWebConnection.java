/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.FormEncodingType;
/*   4:    */ import com.gargoylesoftware.htmlunit.HttpMethod;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebConnection;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebResponseData;
/*   9:    */ import java.io.File;
/*  10:    */ import java.io.FileOutputStream;
/*  11:    */ import java.io.FileWriter;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.io.InputStream;
/*  14:    */ import java.io.OutputStream;
/*  15:    */ import java.net.URL;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.regex.Matcher;
/*  18:    */ import java.util.regex.Pattern;
/*  19:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  20:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*  21:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*  22:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  23:    */ import org.apache.commons.io.FileUtils;
/*  24:    */ import org.apache.commons.io.IOUtils;
/*  25:    */ import org.apache.commons.lang.StringUtils;
/*  26:    */ import org.apache.commons.logging.Log;
/*  27:    */ import org.apache.commons.logging.LogFactory;
/*  28:    */ 
/*  29:    */ public class DebuggingWebConnection
/*  30:    */   extends WebConnectionWrapper
/*  31:    */ {
/*  32: 70 */   private static final Log LOG = LogFactory.getLog(DebuggingWebConnection.class);
/*  33: 72 */   private static final Pattern ESCAPE_QUOTE_PATTERN = Pattern.compile("'");
/*  34:    */   private int counter_;
/*  35:    */   private final WebConnection wrappedWebConnection_;
/*  36:    */   private final File javaScriptFile_;
/*  37:    */   private final File reportFolder_;
/*  38: 78 */   private boolean uncompressJavaScript_ = true;
/*  39:    */   
/*  40:    */   public DebuggingWebConnection(WebConnection webConnection, String dirName)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43: 90 */     super(webConnection);
/*  44:    */     
/*  45: 92 */     this.wrappedWebConnection_ = webConnection;
/*  46: 93 */     File tmpDir = new File(System.getProperty("java.io.tmpdir"));
/*  47: 94 */     this.reportFolder_ = new File(tmpDir, dirName);
/*  48: 95 */     if (this.reportFolder_.exists()) {
/*  49: 96 */       FileUtils.forceDelete(this.reportFolder_);
/*  50:    */     }
/*  51: 98 */     FileUtils.forceMkdir(this.reportFolder_);
/*  52: 99 */     this.javaScriptFile_ = new File(this.reportFolder_, "hu.js");
/*  53:100 */     createOverview();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public WebResponse getResponse(WebRequest request)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:109 */     WebResponse response = this.wrappedWebConnection_.getResponse(request);
/*  60:110 */     if ((isUncompressJavaScript()) && (isJavaScript(response.getContentType()))) {
/*  61:111 */       response = uncompressJavaScript(response);
/*  62:    */     }
/*  63:113 */     saveResponse(response, request);
/*  64:114 */     return response;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected WebResponse uncompressJavaScript(WebResponse response)
/*  68:    */   {
/*  69:123 */     WebRequest request = response.getWebRequest();
/*  70:124 */     final String scriptName = request.getUrl().toString();
/*  71:125 */     final String scriptSource = response.getContentAsString();
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:129 */     ContextFactory factory = new ContextFactory();
/*  76:130 */     ContextAction action = new ContextAction()
/*  77:    */     {
/*  78:    */       public Object run(Context cx)
/*  79:    */       {
/*  80:132 */         cx.setOptimizationLevel(-1);
/*  81:133 */         Script script = cx.compileString(scriptSource, scriptName, 0, null);
/*  82:134 */         return cx.decompileScript(script, 4);
/*  83:    */       }
/*  84:    */     };
/*  85:    */     try
/*  86:    */     {
/*  87:139 */       String decompileScript = (String)factory.call(action);
/*  88:140 */       WebResponseData wrd = new WebResponseData(decompileScript.getBytes(), response.getStatusCode(), response.getStatusMessage(), response.getResponseHeaders());
/*  89:    */       
/*  90:142 */       return new WebResponse(wrd, response.getWebRequest().getUrl(), response.getWebRequest().getHttpMethod(), response.getLoadTime());
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:146 */       LOG.warn("Failed to decompress JavaScript response. Delivering as it.", e);
/*  95:    */     }
/*  96:149 */     return response;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void addMark(String mark)
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:158 */     appendToJSFile("tab[tab.length] = \"" + mark + "\";\n");
/* 103:159 */     LOG.info("--- " + mark + " ---");
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected void saveResponse(WebResponse response, WebRequest request)
/* 107:    */     throws IOException
/* 108:    */   {
/* 109:170 */     this.counter_ += 1;
/* 110:171 */     String extension = chooseExtension(response.getContentType());
/* 111:172 */     File f = createFile(request.getUrl(), extension);
/* 112:173 */     InputStream input = response.getContentAsStream();
/* 113:174 */     OutputStream output = new FileOutputStream(f);
/* 114:    */     try
/* 115:    */     {
/* 116:176 */       IOUtils.copy(response.getContentAsStream(), output);
/* 117:    */     }
/* 118:    */     finally
/* 119:    */     {
/* 120:179 */       IOUtils.closeQuietly(input);
/* 121:180 */       IOUtils.closeQuietly(output);
/* 122:    */     }
/* 123:183 */     URL url = response.getWebRequest().getUrl();
/* 124:184 */     LOG.info("Created file " + f.getAbsolutePath() + " for response " + this.counter_ + ": " + url);
/* 125:    */     
/* 126:186 */     StringBuilder buffer = new StringBuilder();
/* 127:187 */     buffer.append("tab[tab.length] = {code: " + response.getStatusCode() + ", ");
/* 128:188 */     buffer.append("fileName: '" + f.getName() + "', ");
/* 129:189 */     buffer.append("contentType: '" + response.getContentType() + "', ");
/* 130:190 */     buffer.append("method: '" + request.getHttpMethod().name() + "', ");
/* 131:191 */     if ((request.getHttpMethod() == HttpMethod.POST) && (request.getEncodingType() == FormEncodingType.URL_ENCODED)) {
/* 132:192 */       buffer.append("postParameters: " + nameValueListToJsMap(request.getRequestParameters()) + ", ");
/* 133:    */     }
/* 134:194 */     buffer.append("url: '" + escapeJSString(url.toString()) + "', ");
/* 135:195 */     buffer.append("loadTime: " + response.getLoadTime() + ", ");
/* 136:196 */     byte[] bytes = IOUtils.toByteArray(response.getContentAsStream());
/* 137:197 */     buffer.append("responseSize: " + (bytes == null ? 0 : bytes.length) + ", ");
/* 138:198 */     buffer.append("responseHeaders: " + nameValueListToJsMap(response.getResponseHeaders()));
/* 139:199 */     buffer.append("};\n");
/* 140:200 */     appendToJSFile(buffer.toString());
/* 141:    */   }
/* 142:    */   
/* 143:    */   static String escapeJSString(String string)
/* 144:    */   {
/* 145:204 */     return ESCAPE_QUOTE_PATTERN.matcher(string).replaceAll("\\\\'");
/* 146:    */   }
/* 147:    */   
/* 148:    */   static String chooseExtension(String contentType)
/* 149:    */   {
/* 150:208 */     if (isJavaScript(contentType)) {
/* 151:209 */       return ".js";
/* 152:    */     }
/* 153:211 */     if ("text/html".equals(contentType)) {
/* 154:212 */       return ".html";
/* 155:    */     }
/* 156:214 */     if ("text/css".equals(contentType)) {
/* 157:215 */       return ".css";
/* 158:    */     }
/* 159:217 */     if ("text/xml".equals(contentType)) {
/* 160:218 */       return ".xml";
/* 161:    */     }
/* 162:220 */     if ("image/gif".equals(contentType)) {
/* 163:221 */       return ".gif";
/* 164:    */     }
/* 165:223 */     return ".txt";
/* 166:    */   }
/* 167:    */   
/* 168:    */   static boolean isJavaScript(String contentType)
/* 169:    */   {
/* 170:232 */     return (contentType.contains("javascript")) || (contentType.contains("ecmascript")) || ((contentType.startsWith("text/")) && (contentType.endsWith("js")));
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean isUncompressJavaScript()
/* 174:    */   {
/* 175:241 */     return this.uncompressJavaScript_;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setUncompressJavaScript(boolean decompress)
/* 179:    */   {
/* 180:251 */     this.uncompressJavaScript_ = decompress;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void appendToJSFile(String str)
/* 184:    */     throws IOException
/* 185:    */   {
/* 186:255 */     FileWriter jsFileWriter = new FileWriter(this.javaScriptFile_, true);
/* 187:256 */     jsFileWriter.write(str);
/* 188:257 */     jsFileWriter.flush();
/* 189:    */     
/* 190:259 */     jsFileWriter.close();
/* 191:    */   }
/* 192:    */   
/* 193:    */   private File createFile(URL url, String extension)
/* 194:    */     throws IOException
/* 195:    */   {
/* 196:270 */     String name = url.getPath().replaceFirst("/$", "").replaceAll(".*/", "");
/* 197:271 */     name = StringUtils.substringBefore(name, "?");
/* 198:272 */     name = StringUtils.substringBefore(name, ";");
/* 199:273 */     if (!name.endsWith(extension)) {
/* 200:274 */       name = name + extension;
/* 201:    */     }
/* 202:276 */     int counter = 0;
/* 203:    */     for (;;)
/* 204:    */     {
/* 205:    */       String fileName;
/* 206:    */       String fileName;
/* 207:279 */       if (counter != 0) {
/* 208:280 */         fileName = StringUtils.substringBeforeLast(name, ".") + "_" + counter + "." + StringUtils.substringAfterLast(name, ".");
/* 209:    */       } else {
/* 210:284 */         fileName = name;
/* 211:    */       }
/* 212:286 */       File f = new File(this.reportFolder_, fileName);
/* 213:287 */       if (f.createNewFile()) {
/* 214:288 */         return f;
/* 215:    */       }
/* 216:290 */       counter++;
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   static String nameValueListToJsMap(List<NameValuePair> headers)
/* 221:    */   {
/* 222:300 */     if ((headers == null) || (headers.isEmpty())) {
/* 223:301 */       return "{}";
/* 224:    */     }
/* 225:303 */     StringBuilder buffer = new StringBuilder("{");
/* 226:304 */     for (NameValuePair header : headers) {
/* 227:305 */       buffer.append("'" + header.getName() + "': '" + escapeJSString(header.getValue()) + "', ");
/* 228:    */     }
/* 229:307 */     buffer.delete(buffer.length() - 2, buffer.length());
/* 230:308 */     buffer.append("}");
/* 231:309 */     return buffer.toString();
/* 232:    */   }
/* 233:    */   
/* 234:    */   private void createOverview()
/* 235:    */     throws IOException
/* 236:    */   {
/* 237:317 */     FileUtils.writeStringToFile(this.javaScriptFile_, "var tab = [];\n", "ISO-8859-1");
/* 238:    */     
/* 239:319 */     URL indexResource = DebuggingWebConnection.class.getResource("DebuggingWebConnection.index.html");
/* 240:320 */     if (indexResource == null) {
/* 241:321 */       throw new RuntimeException("Missing dependency DebuggingWebConnection.index.html");
/* 242:    */     }
/* 243:323 */     File summary = new File(this.reportFolder_, "index.html");
/* 244:324 */     FileUtils.copyURLToFile(indexResource, summary);
/* 245:    */     
/* 246:326 */     LOG.info("Summary will be in " + summary.getAbsolutePath());
/* 247:    */   }
/* 248:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.DebuggingWebConnection
 * JD-Core Version:    0.7.0.1
 */