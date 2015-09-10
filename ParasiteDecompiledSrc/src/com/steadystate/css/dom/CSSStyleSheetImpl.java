/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import com.steadystate.css.parser.SACParserCSS2;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.io.StringReader;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.net.URISyntaxException;
/*  10:    */ import org.w3c.css.sac.CSSException;
/*  11:    */ import org.w3c.css.sac.InputSource;
/*  12:    */ import org.w3c.css.sac.SACMediaList;
/*  13:    */ import org.w3c.dom.DOMException;
/*  14:    */ import org.w3c.dom.Node;
/*  15:    */ import org.w3c.dom.css.CSSImportRule;
/*  16:    */ import org.w3c.dom.css.CSSRule;
/*  17:    */ import org.w3c.dom.css.CSSRuleList;
/*  18:    */ import org.w3c.dom.css.CSSStyleSheet;
/*  19:    */ import org.w3c.dom.stylesheets.MediaList;
/*  20:    */ import org.w3c.dom.stylesheets.StyleSheet;
/*  21:    */ 
/*  22:    */ public class CSSStyleSheetImpl
/*  23:    */   implements CSSStyleSheet, Serializable
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -2300541300646796363L;
/*  26: 62 */   private boolean disabled = false;
/*  27: 63 */   private Node ownerNode = null;
/*  28: 64 */   private StyleSheet parentStyleSheet = null;
/*  29: 65 */   private String href = null;
/*  30: 66 */   private String title = null;
/*  31: 67 */   private MediaList media = null;
/*  32: 68 */   private CSSRule ownerRule = null;
/*  33: 69 */   private boolean readOnly = false;
/*  34: 70 */   private CSSRuleList cssRules = null;
/*  35:    */   private String baseUri;
/*  36:    */   
/*  37:    */   public void setMedia(MediaList media)
/*  38:    */   {
/*  39: 75 */     this.media = media;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private String getBaseUri()
/*  43:    */   {
/*  44: 80 */     return this.baseUri;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setBaseUri(String baseUri)
/*  48:    */   {
/*  49: 85 */     this.baseUri = baseUri;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getType()
/*  53:    */   {
/*  54: 93 */     return "text/css";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean getDisabled()
/*  58:    */   {
/*  59: 97 */     return this.disabled;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setDisabled(boolean disabled)
/*  63:    */   {
/*  64:105 */     this.disabled = disabled;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Node getOwnerNode()
/*  68:    */   {
/*  69:109 */     return this.ownerNode;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public StyleSheet getParentStyleSheet()
/*  73:    */   {
/*  74:113 */     return this.parentStyleSheet;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getHref()
/*  78:    */   {
/*  79:117 */     return this.href;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getTitle()
/*  83:    */   {
/*  84:121 */     return this.title;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public MediaList getMedia()
/*  88:    */   {
/*  89:125 */     return this.media;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public CSSRule getOwnerRule()
/*  93:    */   {
/*  94:129 */     return this.ownerRule;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public CSSRuleList getCssRules()
/*  98:    */   {
/*  99:133 */     if (this.cssRules == null) {
/* 100:135 */       this.cssRules = new CSSRuleListImpl();
/* 101:    */     }
/* 102:137 */     return this.cssRules;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int insertRule(String rule, int index)
/* 106:    */     throws DOMException
/* 107:    */   {
/* 108:141 */     if (this.readOnly) {
/* 109:142 */       throw new DOMExceptionImpl((short)7, 2);
/* 110:    */     }
/* 111:    */     try
/* 112:    */     {
/* 113:148 */       InputSource is = new InputSource(new StringReader(rule));
/* 114:149 */       CSSOMParser parser = new CSSOMParser();
/* 115:150 */       parser.setParentStyleSheet(this);
/* 116:151 */       CSSRule r = parser.parseRule(is);
/* 117:153 */       if (getCssRules().getLength() > 0)
/* 118:    */       {
/* 119:157 */         int msg = -1;
/* 120:158 */         if (r.getType() == 2)
/* 121:    */         {
/* 122:161 */           if (index != 0) {
/* 123:162 */             msg = 15;
/* 124:163 */           } else if (getCssRules().item(0).getType() == 2) {
/* 125:165 */             msg = 16;
/* 126:    */           }
/* 127:    */         }
/* 128:167 */         else if (r.getType() == 3) {
/* 129:171 */           if (index <= getCssRules().getLength()) {
/* 130:172 */             for (int i = 0; i < index; i++)
/* 131:    */             {
/* 132:173 */               int rt = getCssRules().item(i).getType();
/* 133:174 */               if ((rt != 2) || (rt != 3))
/* 134:    */               {
/* 135:176 */                 msg = 17;
/* 136:177 */                 break;
/* 137:    */               }
/* 138:    */             }
/* 139:    */           }
/* 140:    */         }
/* 141:183 */         if (msg > -1) {
/* 142:184 */           throw new DOMExceptionImpl((short)3, msg);
/* 143:    */         }
/* 144:    */       }
/* 145:191 */       ((CSSRuleListImpl)getCssRules()).insert(r, index);
/* 146:    */     }
/* 147:    */     catch (ArrayIndexOutOfBoundsException e)
/* 148:    */     {
/* 149:194 */       throw new DOMExceptionImpl((short)1, 1, e.getMessage());
/* 150:    */     }
/* 151:    */     catch (CSSException e)
/* 152:    */     {
/* 153:199 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 154:    */     }
/* 155:    */     catch (IOException e)
/* 156:    */     {
/* 157:204 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 158:    */     }
/* 159:209 */     return index;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void deleteRule(int index)
/* 163:    */     throws DOMException
/* 164:    */   {
/* 165:213 */     if (this.readOnly) {
/* 166:214 */       throw new DOMExceptionImpl((short)7, 2);
/* 167:    */     }
/* 168:    */     try
/* 169:    */     {
/* 170:220 */       ((CSSRuleListImpl)getCssRules()).delete(index);
/* 171:    */     }
/* 172:    */     catch (ArrayIndexOutOfBoundsException e)
/* 173:    */     {
/* 174:222 */       throw new DOMExceptionImpl((short)1, 1, e.getMessage());
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isReadOnly()
/* 179:    */   {
/* 180:230 */     return this.readOnly;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setReadOnly(boolean b)
/* 184:    */   {
/* 185:234 */     this.readOnly = b;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setOwnerNode(Node ownerNode)
/* 189:    */   {
/* 190:238 */     this.ownerNode = ownerNode;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setParentStyleSheet(StyleSheet parentStyleSheet)
/* 194:    */   {
/* 195:242 */     this.parentStyleSheet = parentStyleSheet;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setHref(String href)
/* 199:    */   {
/* 200:246 */     this.href = href;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setTitle(String title)
/* 204:    */   {
/* 205:250 */     this.title = title;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setMediaText(String mediaText)
/* 209:    */   {
/* 210:254 */     InputSource source = new InputSource(new StringReader(mediaText));
/* 211:    */     try
/* 212:    */     {
/* 213:258 */       SACMediaList sml = new SACParserCSS2().parseMedia(source);
/* 214:259 */       this.media = new MediaListImpl(sml);
/* 215:    */     }
/* 216:    */     catch (IOException e) {}
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setOwnerRule(CSSRule ownerRule)
/* 220:    */   {
/* 221:268 */     this.ownerRule = ownerRule;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setCssRules(CSSRuleList rules)
/* 225:    */   {
/* 226:272 */     this.cssRules = rules;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String toString()
/* 230:    */   {
/* 231:276 */     return getCssRules().toString();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void importImports(boolean recursive)
/* 235:    */     throws DOMException
/* 236:    */   {
/* 237:288 */     for (int i = 0; i < getCssRules().getLength(); i++)
/* 238:    */     {
/* 239:290 */       CSSRule cssRule = getCssRules().item(i);
/* 240:291 */       if (cssRule.getType() == 3)
/* 241:    */       {
/* 242:293 */         CSSImportRule cssImportRule = (CSSImportRule)cssRule;
/* 243:    */         try
/* 244:    */         {
/* 245:296 */           URI importURI = new URI(getBaseUri()).resolve(cssImportRule.getHref());
/* 246:    */           
/* 247:298 */           CSSStyleSheetImpl importedCSS = (CSSStyleSheetImpl)new CSSOMParser().parseStyleSheet(new InputSource(importURI.toString()), null, importURI.toString());
/* 248:301 */           if (recursive) {
/* 249:303 */             importedCSS.importImports(recursive);
/* 250:    */           }
/* 251:305 */           MediaList mediaList = cssImportRule.getMedia();
/* 252:306 */           if (mediaList.getLength() == 0) {
/* 253:308 */             mediaList.appendMedium("all");
/* 254:    */           }
/* 255:310 */           CSSMediaRuleImpl cssMediaRule = new CSSMediaRuleImpl(this, null, mediaList);
/* 256:    */           
/* 257:312 */           cssMediaRule.setRuleList((CSSRuleListImpl)importedCSS.getCssRules());
/* 258:    */           
/* 259:314 */           deleteRule(i);
/* 260:315 */           ((CSSRuleListImpl)getCssRules()).insert(cssMediaRule, i);
/* 261:    */         }
/* 262:    */         catch (URISyntaxException e)
/* 263:    */         {
/* 264:320 */           throw new DOMException((short)12, e.getLocalizedMessage());
/* 265:    */         }
/* 266:    */         catch (IOException e) {}
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSStyleSheetImpl
 * JD-Core Version:    0.7.0.1
 */