/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.host.FormField;
/*   8:    */ import java.util.regex.Matcher;
/*   9:    */ import java.util.regex.Pattern;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ 
/*  12:    */ public class HTMLTextAreaElement
/*  13:    */   extends FormField
/*  14:    */ {
/*  15: 38 */   private static final Pattern NORMALIZE_VALUE_PATTERN = Pattern.compile("([^\\r])\\n");
/*  16:    */   
/*  17:    */   public String jsxGet_type()
/*  18:    */   {
/*  19: 53 */     return "textarea";
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String jsxGet_value()
/*  23:    */   {
/*  24: 62 */     String value = ((HtmlTextArea)getDomNodeOrDie()).getText();
/*  25: 63 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.TEXTAREA_CRNL)) {
/*  26: 64 */       value = NORMALIZE_VALUE_PATTERN.matcher(value).replaceAll("$1\r\n");
/*  27:    */     }
/*  28: 66 */     return value;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void jsxSet_value(String value)
/*  32:    */   {
/*  33: 75 */     ((HtmlTextArea)getDomNodeOrDie()).setText(value);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int jsxGet_cols()
/*  37:    */   {
/*  38:    */     int cols;
/*  39:    */     try
/*  40:    */     {
/*  41: 85 */       String s = getDomNodeOrDie().getAttribute("cols");
/*  42: 86 */       cols = Integer.parseInt(s);
/*  43:    */     }
/*  44:    */     catch (NumberFormatException e)
/*  45:    */     {
/*  46:    */       int cols;
/*  47: 89 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_173)) {
/*  48: 90 */         cols = -1;
/*  49:    */       } else {
/*  50: 93 */         cols = 20;
/*  51:    */       }
/*  52:    */     }
/*  53: 96 */     return cols;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void jsxSet_cols(String cols)
/*  57:    */   {
/*  58:    */     int i;
/*  59:    */     try
/*  60:    */     {
/*  61:106 */       i = new Float(cols).intValue();
/*  62:107 */       if (i < 0) {
/*  63:108 */         throw new NumberFormatException();
/*  64:    */       }
/*  65:    */     }
/*  66:    */     catch (NumberFormatException e)
/*  67:    */     {
/*  68:112 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_110)) {
/*  69:113 */         throw Context.throwAsScriptRuntimeEx(e);
/*  70:    */       }
/*  71:115 */       i = 0;
/*  72:    */     }
/*  73:117 */     getDomNodeOrDie().setAttribute("cols", Integer.toString(i));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int jsxGet_rows()
/*  77:    */   {
/*  78:    */     int rows;
/*  79:    */     try
/*  80:    */     {
/*  81:127 */       String s = getDomNodeOrDie().getAttribute("rows");
/*  82:128 */       rows = Integer.parseInt(s);
/*  83:    */     }
/*  84:    */     catch (NumberFormatException e)
/*  85:    */     {
/*  86:    */       int rows;
/*  87:131 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_174)) {
/*  88:132 */         rows = -1;
/*  89:    */       } else {
/*  90:135 */         rows = 2;
/*  91:    */       }
/*  92:    */     }
/*  93:138 */     return rows;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void jsxSet_rows(String rows)
/*  97:    */   {
/*  98:    */     int i;
/*  99:    */     try
/* 100:    */     {
/* 101:148 */       i = new Float(rows).intValue();
/* 102:149 */       if (i < 0) {
/* 103:150 */         throw new NumberFormatException();
/* 104:    */       }
/* 105:    */     }
/* 106:    */     catch (NumberFormatException e)
/* 107:    */     {
/* 108:154 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_111)) {
/* 109:155 */         throw Context.throwAsScriptRuntimeEx(e);
/* 110:    */       }
/* 111:157 */       i = 0;
/* 112:    */     }
/* 113:159 */     getDomNodeOrDie().setAttribute("rows", Integer.toString(i));
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String jsxGet_defaultValue()
/* 117:    */   {
/* 118:168 */     String value = ((HtmlTextArea)getDomNodeOrDie()).getDefaultValue();
/* 119:169 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.TEXTAREA_CRNL)) {
/* 120:170 */       value = NORMALIZE_VALUE_PATTERN.matcher(value).replaceAll("$1\r\n");
/* 121:    */     }
/* 122:172 */     return value;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void jsxSet_defaultValue(String defaultValue)
/* 126:    */   {
/* 127:181 */     ((HtmlTextArea)getDomNodeOrDie()).setDefaultValue(defaultValue);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int jsxGet_textLength()
/* 131:    */   {
/* 132:189 */     return jsxGet_value().length();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int jsxGet_selectionStart()
/* 136:    */   {
/* 137:197 */     return ((HtmlTextArea)getDomNodeOrDie()).getSelectionStart();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void jsxSet_selectionStart(int start)
/* 141:    */   {
/* 142:205 */     ((HtmlTextArea)getDomNodeOrDie()).setSelectionStart(start);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int jsxGet_selectionEnd()
/* 146:    */   {
/* 147:213 */     return ((HtmlTextArea)getDomNodeOrDie()).getSelectionEnd();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void jsxSet_selectionEnd(int end)
/* 151:    */   {
/* 152:221 */     ((HtmlTextArea)getDomNodeOrDie()).setSelectionEnd(end);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void jsxFunction_setSelectionRange(int start, int end)
/* 156:    */   {
/* 157:230 */     jsxSet_selectionStart(start);
/* 158:231 */     jsxSet_selectionEnd(end);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void jsxFunction_select()
/* 162:    */   {
/* 163:238 */     ((HtmlTextArea)getDomNodeOrDie()).select();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean jsxGet_readOnly()
/* 167:    */   {
/* 168:246 */     return ((HtmlTextArea)getDomNodeOrDie()).isReadOnly();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void jsxSet_readOnly(boolean readOnly)
/* 172:    */   {
/* 173:254 */     ((HtmlTextArea)getDomNodeOrDie()).setReadOnly(readOnly);
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTextAreaElement
 * JD-Core Version:    0.7.0.1
 */