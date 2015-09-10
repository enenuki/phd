/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLObjectElement;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class HtmlObject
/*   8:    */   extends HtmlElement
/*   9:    */ {
/*  10:    */   public static final String TAG_NAME = "object";
/*  11:    */   
/*  12:    */   HtmlObject(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  13:    */   {
/*  14: 46 */     super(namespaceURI, qualifiedName, page, attributes);
/*  15: 47 */     if (attributes != null)
/*  16:    */     {
/*  17: 48 */       DomAttr classid = (DomAttr)attributes.get("classid");
/*  18: 49 */       if (classid != null) {
/*  19: 50 */         ((HTMLObjectElement)getScriptObject()).jsxSet_classid(classid.getValue());
/*  20:    */       }
/*  21:    */     }
/*  22:    */   }
/*  23:    */   
/*  24:    */   public final String getDeclareAttribute()
/*  25:    */   {
/*  26: 64 */     return getAttribute("declare");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final String getClassIdAttribute()
/*  30:    */   {
/*  31: 76 */     return getAttribute("classid");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final String getCodebaseAttribute()
/*  35:    */   {
/*  36: 88 */     return getAttribute("codebase");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final String getDataAttribute()
/*  40:    */   {
/*  41:100 */     return getAttribute("data");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final String getTypeAttribute()
/*  45:    */   {
/*  46:112 */     return getAttribute("type");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final String getCodeTypeAttribute()
/*  50:    */   {
/*  51:124 */     return getAttribute("codetype");
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final String getArchiveAttribute()
/*  55:    */   {
/*  56:136 */     return getAttribute("archive");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final String getStandbyAttribute()
/*  60:    */   {
/*  61:148 */     return getAttribute("standby");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final String getHeightAttribute()
/*  65:    */   {
/*  66:160 */     return getAttribute("height");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final String getWidthAttribute()
/*  70:    */   {
/*  71:172 */     return getAttribute("width");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final String getUseMapAttribute()
/*  75:    */   {
/*  76:184 */     return getAttribute("usemap");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final String getNameAttribute()
/*  80:    */   {
/*  81:196 */     return getAttribute("name");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public final String getTabIndexAttribute()
/*  85:    */   {
/*  86:208 */     return getAttribute("tabindex");
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final String getAlignAttribute()
/*  90:    */   {
/*  91:220 */     return getAttribute("align");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final String getBorderAttribute()
/*  95:    */   {
/*  96:232 */     return getAttribute("border");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final String getHspaceAttribute()
/* 100:    */   {
/* 101:244 */     return getAttribute("hspace");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final String getVspaceAttribute()
/* 105:    */   {
/* 106:256 */     return getAttribute("vspace");
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlObject
 * JD-Core Version:    0.7.0.1
 */