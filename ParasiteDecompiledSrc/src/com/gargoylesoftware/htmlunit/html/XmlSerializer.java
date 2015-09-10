/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.Page;
/*   4:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   7:    */ import com.gargoylesoftware.htmlunit.util.MimeType;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.FileOutputStream;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStream;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import java.util.regex.Matcher;
/*  17:    */ import java.util.regex.Pattern;
/*  18:    */ import org.apache.commons.io.FileUtils;
/*  19:    */ import org.apache.commons.io.IOUtils;
/*  20:    */ 
/*  21:    */ class XmlSerializer
/*  22:    */ {
/*  23:    */   private static final String FILE_SEPARATOR = "/";
/*  24: 44 */   private static final Pattern CREATE_FILE_PATTERN = Pattern.compile(".*/");
/*  25: 46 */   private final StringBuilder buffer_ = new StringBuilder();
/*  26: 47 */   private final StringBuilder indent_ = new StringBuilder();
/*  27:    */   private File outputDir_;
/*  28:    */   
/*  29:    */   public void save(HtmlPage page, File file)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 51 */     String fileName = file.getName();
/*  33: 52 */     if ((!fileName.endsWith(".htm")) && (!fileName.endsWith(".html"))) {
/*  34: 53 */       fileName = fileName + ".html";
/*  35:    */     }
/*  36: 55 */     File outputFile = new File(file.getParentFile(), fileName);
/*  37: 56 */     if (outputFile.exists()) {
/*  38: 57 */       throw new IOException("File already exists: " + outputFile);
/*  39:    */     }
/*  40: 59 */     fileName = fileName.substring(0, fileName.lastIndexOf('.'));
/*  41: 60 */     this.outputDir_ = new File(file.getParentFile(), fileName);
/*  42: 61 */     FileUtils.writeStringToFile(outputFile, asXml(page.getDocumentElement()));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String asXml(HtmlElement node)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48: 71 */     this.buffer_.setLength(0);
/*  49: 72 */     this.indent_.setLength(0);
/*  50: 73 */     String charsetName = null;
/*  51: 74 */     if ((node.getPage() instanceof HtmlPage)) {
/*  52: 75 */       charsetName = node.getPage().getPageEncoding();
/*  53:    */     }
/*  54: 77 */     if ((charsetName != null) && ((node instanceof HtmlHtml))) {
/*  55: 78 */       this.buffer_.append("<?xml version=\"1.0\" encoding=\"").append(charsetName).append("\"?>").append('\n');
/*  56:    */     }
/*  57: 80 */     printXml(node);
/*  58: 81 */     String response = this.buffer_.toString();
/*  59: 82 */     this.buffer_.setLength(0);
/*  60: 83 */     return response;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void printXml(DomElement node)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66: 87 */     if (!isExcluded(node))
/*  67:    */     {
/*  68: 88 */       boolean hasChildren = node.getFirstChild() != null;
/*  69: 89 */       this.buffer_.append(this.indent_).append('<');
/*  70: 90 */       printOpeningTag(node);
/*  71: 92 */       if ((!hasChildren) && (!node.isEmptyXmlTagExpanded()))
/*  72:    */       {
/*  73: 93 */         this.buffer_.append("/>").append('\n');
/*  74:    */       }
/*  75:    */       else
/*  76:    */       {
/*  77: 96 */         this.buffer_.append(">").append('\n');
/*  78: 97 */         for (DomNode child = node.getFirstChild(); child != null; child = child.getNextSibling())
/*  79:    */         {
/*  80: 98 */           this.indent_.append("  ");
/*  81: 99 */           if ((child instanceof DomElement)) {
/*  82:100 */             printXml((DomElement)child);
/*  83:    */           } else {
/*  84:103 */             this.buffer_.append(child);
/*  85:    */           }
/*  86:105 */           this.indent_.setLength(this.indent_.length() - 2);
/*  87:    */         }
/*  88:107 */         this.buffer_.append(this.indent_).append("</").append(node.getTagName()).append('>').append('\n');
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected void printOpeningTag(DomElement node)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:119 */     this.buffer_.append(node.getTagName());
/*  97:120 */     Map<String, DomAttr> attributes = readAttributes(node);
/*  98:122 */     for (Map.Entry<String, DomAttr> entry : attributes.entrySet())
/*  99:    */     {
/* 100:123 */       this.buffer_.append(" ");
/* 101:124 */       this.buffer_.append((String)entry.getKey());
/* 102:125 */       this.buffer_.append("=\"");
/* 103:126 */       String value = ((DomAttr)entry.getValue()).getNodeValue();
/* 104:127 */       this.buffer_.append(com.gargoylesoftware.htmlunit.util.StringUtils.escapeXmlAttributeValue(value));
/* 105:128 */       this.buffer_.append('"');
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   private Map<String, DomAttr> readAttributes(DomElement node)
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:133 */     if ((node instanceof HtmlImage)) {
/* 113:134 */       return getAttributesFor((HtmlImage)node);
/* 114:    */     }
/* 115:136 */     if ((node instanceof HtmlLink)) {
/* 116:137 */       return getAttributesFor((HtmlLink)node);
/* 117:    */     }
/* 118:139 */     if ((node instanceof BaseFrame)) {
/* 119:140 */       return getAttributesFor((BaseFrame)node);
/* 120:    */     }
/* 121:143 */     Map<String, DomAttr> attributes = node.getAttributesMap();
/* 122:144 */     if ((node instanceof HtmlOption))
/* 123:    */     {
/* 124:145 */       attributes = new HashMap(attributes);
/* 125:146 */       HtmlOption option = (HtmlOption)node;
/* 126:147 */       if (option.isSelected())
/* 127:    */       {
/* 128:148 */         if (!attributes.containsKey("selected")) {
/* 129:149 */           attributes.put("selected", new DomAttr(node.getPage(), null, "selected", "selected", false));
/* 130:    */         }
/* 131:    */       }
/* 132:    */       else {
/* 133:153 */         attributes.remove("selected");
/* 134:    */       }
/* 135:    */     }
/* 136:156 */     return attributes;
/* 137:    */   }
/* 138:    */   
/* 139:    */   private Map<String, DomAttr> getAttributesFor(BaseFrame frame)
/* 140:    */     throws IOException
/* 141:    */   {
/* 142:160 */     Map<String, DomAttr> map = createAttributesCopyWithClonedAttribute(frame, "src");
/* 143:161 */     DomAttr srcAttr = (DomAttr)map.get("src");
/* 144:162 */     if (srcAttr == null) {
/* 145:163 */       return map;
/* 146:    */     }
/* 147:166 */     Page enclosedPage = frame.getEnclosedPage();
/* 148:167 */     String suffix = getFileExtension(enclosedPage);
/* 149:168 */     File file = createFile(srcAttr.getValue(), "." + suffix);
/* 150:170 */     if ((enclosedPage instanceof HtmlPage))
/* 151:    */     {
/* 152:171 */       file.delete();
/* 153:    */       
/* 154:173 */       ((HtmlPage)enclosedPage).save(file);
/* 155:    */     }
/* 156:    */     else
/* 157:    */     {
/* 158:176 */       InputStream is = enclosedPage.getWebResponse().getContentAsStream();
/* 159:177 */       FileOutputStream fos = new FileOutputStream(file);
/* 160:178 */       IOUtils.copyLarge(is, fos);
/* 161:179 */       IOUtils.closeQuietly(is);
/* 162:180 */       IOUtils.closeQuietly(fos);
/* 163:    */     }
/* 164:183 */     srcAttr.setValue(file.getParentFile().getName() + "/" + file.getName());
/* 165:184 */     return map;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private String getFileExtension(Page enclosedPage)
/* 169:    */   {
/* 170:188 */     if ((enclosedPage instanceof HtmlPage)) {
/* 171:189 */       return "html";
/* 172:    */     }
/* 173:192 */     URL url = enclosedPage.getUrl();
/* 174:193 */     if (url.getPath().contains(".")) {
/* 175:194 */       return org.apache.commons.lang.StringUtils.substringAfterLast(url.getPath(), ".");
/* 176:    */     }
/* 177:197 */     return ".unknown";
/* 178:    */   }
/* 179:    */   
/* 180:    */   protected Map<String, DomAttr> getAttributesFor(HtmlLink link)
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:201 */     Map<String, DomAttr> map = createAttributesCopyWithClonedAttribute(link, "href");
/* 184:202 */     DomAttr hrefAttr = (DomAttr)map.get("href");
/* 185:203 */     if ((null != hrefAttr) && (org.apache.commons.lang.StringUtils.isNotBlank(hrefAttr.getValue())))
/* 186:    */     {
/* 187:204 */       File file = createFile(hrefAttr.getValue(), ".css");
/* 188:205 */       FileUtils.writeStringToFile(file, link.getWebResponse(true).getContentAsString());
/* 189:206 */       hrefAttr.setValue(this.outputDir_.getName() + "/" + file.getName());
/* 190:    */     }
/* 191:209 */     return map;
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected Map<String, DomAttr> getAttributesFor(HtmlImage image)
/* 195:    */     throws IOException
/* 196:    */   {
/* 197:213 */     Map<String, DomAttr> map = createAttributesCopyWithClonedAttribute(image, "src");
/* 198:214 */     DomAttr srcAttr = (DomAttr)map.get("src");
/* 199:215 */     if ((null != srcAttr) && (org.apache.commons.lang.StringUtils.isNotBlank(srcAttr.getValue())))
/* 200:    */     {
/* 201:216 */       WebResponse response = image.getWebResponse(true);
/* 202:    */       
/* 203:218 */       File file = createFile(srcAttr.getValue(), "." + getSuffix(response));
/* 204:219 */       FileUtils.copyInputStreamToFile(response.getContentAsStream(), file);
/* 205:220 */       String valueOnFileSystem = this.outputDir_.getName() + "/" + file.getName();
/* 206:221 */       srcAttr.setValue(valueOnFileSystem);
/* 207:    */     }
/* 208:224 */     return map;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private String getSuffix(WebResponse response)
/* 212:    */   {
/* 213:229 */     String url = response.getWebRequest().getUrl().toString();
/* 214:230 */     String fileName = org.apache.commons.lang.StringUtils.substringAfterLast(org.apache.commons.lang.StringUtils.substringBefore(url, "?"), "/");
/* 215:    */     
/* 216:232 */     String suffix = org.apache.commons.lang.StringUtils.substringAfterLast(fileName, ".");
/* 217:233 */     if ((suffix.length() > 1) && (suffix.length() < 5)) {
/* 218:234 */       return suffix;
/* 219:    */     }
/* 220:238 */     return MimeType.getFileExtension(response.getContentType());
/* 221:    */   }
/* 222:    */   
/* 223:    */   private Map<String, DomAttr> createAttributesCopyWithClonedAttribute(HtmlElement elt, String attrName)
/* 224:    */   {
/* 225:242 */     Map<String, DomAttr> newMap = new HashMap(elt.getAttributesMap());
/* 226:    */     
/* 227:    */ 
/* 228:245 */     DomAttr attr = (DomAttr)newMap.get(attrName);
/* 229:246 */     if (null == attr) {
/* 230:247 */       return newMap;
/* 231:    */     }
/* 232:250 */     DomAttr clonedAttr = new DomAttr(attr.getPage(), attr.getNamespaceURI(), attr.getQualifiedName(), attr.getValue(), attr.getSpecified());
/* 233:    */     
/* 234:    */ 
/* 235:253 */     newMap.put(attrName, clonedAttr);
/* 236:    */     
/* 237:255 */     return newMap;
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected boolean isExcluded(DomElement element)
/* 241:    */   {
/* 242:259 */     return element instanceof HtmlScript;
/* 243:    */   }
/* 244:    */   
/* 245:    */   private File createFile(String url, String extension)
/* 246:    */     throws IOException
/* 247:    */   {
/* 248:270 */     String name = url.replaceFirst("/$", "");
/* 249:271 */     name = CREATE_FILE_PATTERN.matcher(name).replaceAll("");
/* 250:272 */     name = org.apache.commons.lang.StringUtils.substringBefore(name, "?");
/* 251:273 */     name = org.apache.commons.lang.StringUtils.substringBefore(name, ";");
/* 252:274 */     if (!name.endsWith(extension)) {
/* 253:275 */       name = name + extension;
/* 254:    */     }
/* 255:277 */     int counter = 0;
/* 256:    */     for (;;)
/* 257:    */     {
/* 258:    */       String fileName;
/* 259:    */       String fileName;
/* 260:280 */       if (counter != 0) {
/* 261:281 */         fileName = org.apache.commons.lang.StringUtils.substringBeforeLast(name, ".") + "_" + counter + "." + org.apache.commons.lang.StringUtils.substringAfterLast(name, ".");
/* 262:    */       } else {
/* 263:285 */         fileName = name;
/* 264:    */       }
/* 265:287 */       this.outputDir_.mkdirs();
/* 266:288 */       File f = new File(this.outputDir_, fileName);
/* 267:289 */       if (f.createNewFile()) {
/* 268:290 */         return f;
/* 269:    */       }
/* 270:292 */       counter++;
/* 271:    */     }
/* 272:    */   }
/* 273:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.XmlSerializer
 * JD-Core Version:    0.7.0.1
 */