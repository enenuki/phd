/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   4:    */ import java.io.BufferedReader;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ import java.io.StringReader;
/*   9:    */ import java.io.StringWriter;
/*  10:    */ import java.util.StringTokenizer;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.EcmaError;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.RhinoException;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.WrappedException;
/*  15:    */ 
/*  16:    */ public class ScriptException
/*  17:    */   extends RuntimeException
/*  18:    */ {
/*  19:    */   private final String scriptSourceCode_;
/*  20:    */   private final HtmlPage page_;
/*  21:    */   
/*  22:    */   public ScriptException(HtmlPage page, Throwable throwable, String scriptSourceCode)
/*  23:    */   {
/*  24: 55 */     super(getMessageFrom(throwable), throwable);
/*  25: 56 */     this.scriptSourceCode_ = scriptSourceCode;
/*  26: 57 */     this.page_ = page;
/*  27:    */   }
/*  28:    */   
/*  29:    */   private static String getMessageFrom(Throwable throwable)
/*  30:    */   {
/*  31: 61 */     if (throwable == null) {
/*  32: 62 */       return "null";
/*  33:    */     }
/*  34: 64 */     return throwable.getMessage();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ScriptException(HtmlPage page, Throwable throwable)
/*  38:    */   {
/*  39: 73 */     this(page, throwable, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void printStackTrace()
/*  43:    */   {
/*  44: 82 */     printStackTrace(System.out);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void printStackTrace(PrintWriter writer)
/*  48:    */   {
/*  49: 93 */     writer.write(createPrintableStackTrace());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void printStackTrace(PrintStream stream)
/*  53:    */   {
/*  54:104 */     stream.print(createPrintableStackTrace());
/*  55:    */   }
/*  56:    */   
/*  57:    */   private String createPrintableStackTrace()
/*  58:    */   {
/*  59:108 */     StringWriter stringWriter = new StringWriter();
/*  60:109 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/*  61:    */     
/*  62:111 */     printWriter.println("======= EXCEPTION START ========");
/*  63:113 */     if (getCause() != null) {
/*  64:114 */       if ((getCause() instanceof EcmaError))
/*  65:    */       {
/*  66:115 */         EcmaError ecmaError = (EcmaError)getCause();
/*  67:116 */         printWriter.print("EcmaError: ");
/*  68:117 */         printWriter.print("lineNumber=[");
/*  69:118 */         printWriter.print(ecmaError.lineNumber());
/*  70:119 */         printWriter.print("] column=[");
/*  71:120 */         printWriter.print(ecmaError.columnNumber());
/*  72:121 */         printWriter.print("] lineSource=[");
/*  73:122 */         printWriter.print(getFailingLine());
/*  74:123 */         printWriter.print("] name=[");
/*  75:124 */         printWriter.print(ecmaError.getName());
/*  76:125 */         printWriter.print("] sourceName=[");
/*  77:126 */         printWriter.print(ecmaError.sourceName());
/*  78:127 */         printWriter.print("] message=[");
/*  79:128 */         printWriter.print(ecmaError.getMessage());
/*  80:129 */         printWriter.print("]");
/*  81:130 */         printWriter.println();
/*  82:    */       }
/*  83:    */       else
/*  84:    */       {
/*  85:133 */         printWriter.println("Exception class=[" + getCause().getClass().getName() + "]");
/*  86:    */       }
/*  87:    */     }
/*  88:137 */     super.printStackTrace(printWriter);
/*  89:138 */     if ((getCause() != null) && ((getCause() instanceof JavaScriptException)))
/*  90:    */     {
/*  91:139 */       Object value = ((JavaScriptException)getCause()).getValue();
/*  92:    */       
/*  93:141 */       printWriter.print("JavaScriptException value = ");
/*  94:142 */       if ((value instanceof Throwable)) {
/*  95:143 */         ((Throwable)value).printStackTrace(printWriter);
/*  96:    */       } else {
/*  97:146 */         printWriter.println(value);
/*  98:    */       }
/*  99:    */     }
/* 100:149 */     else if ((getCause() != null) && ((getCause() instanceof WrappedException)))
/* 101:    */     {
/* 102:150 */       WrappedException wrappedException = (WrappedException)getCause();
/* 103:151 */       printWriter.print("WrappedException: ");
/* 104:152 */       wrappedException.printStackTrace(printWriter);
/* 105:    */       
/* 106:154 */       Throwable innerException = wrappedException.getWrappedException();
/* 107:155 */       if (innerException == null)
/* 108:    */       {
/* 109:156 */         printWriter.println("Inside wrapped exception: null");
/* 110:    */       }
/* 111:    */       else
/* 112:    */       {
/* 113:159 */         printWriter.println("Inside wrapped exception:");
/* 114:160 */         innerException.printStackTrace(printWriter);
/* 115:    */       }
/* 116:    */     }
/* 117:163 */     else if (getCause() != null)
/* 118:    */     {
/* 119:164 */       printWriter.println("Enclosed exception: ");
/* 120:165 */       getCause().printStackTrace(printWriter);
/* 121:    */     }
/* 122:168 */     if ((this.scriptSourceCode_ != null) && (this.scriptSourceCode_.length() > 0))
/* 123:    */     {
/* 124:169 */       printWriter.println("== CALLING JAVASCRIPT ==");
/* 125:170 */       printWriter.println(this.scriptSourceCode_);
/* 126:    */     }
/* 127:172 */     printWriter.println("======= EXCEPTION END ========");
/* 128:    */     
/* 129:174 */     return stringWriter.toString();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getScriptSourceCode()
/* 133:    */   {
/* 134:182 */     return this.scriptSourceCode_;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getFailingLine()
/* 138:    */   {
/* 139:193 */     int lineNumber = getFailingLineNumber();
/* 140:194 */     if ((lineNumber == -1) || (this.scriptSourceCode_ == null)) {
/* 141:195 */       return "<no source>";
/* 142:    */     }
/* 143:    */     try
/* 144:    */     {
/* 145:199 */       BufferedReader reader = new BufferedReader(new StringReader(this.scriptSourceCode_));
/* 146:200 */       for (int i = 0; i < lineNumber - 1; i++) {
/* 147:201 */         reader.readLine();
/* 148:    */       }
/* 149:203 */       String result = reader.readLine();
/* 150:204 */       reader.close();
/* 151:205 */       return result;
/* 152:    */     }
/* 153:    */     catch (IOException e)
/* 154:    */     {
/* 155:209 */       e.printStackTrace();
/* 156:    */     }
/* 157:211 */     return "";
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int getFailingLineNumber()
/* 161:    */   {
/* 162:221 */     if ((getCause() instanceof RhinoException))
/* 163:    */     {
/* 164:222 */       RhinoException cause = (RhinoException)getCause();
/* 165:223 */       return cause.lineNumber();
/* 166:    */     }
/* 167:226 */     return -1;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public HtmlPage getPage()
/* 171:    */   {
/* 172:236 */     return this.page_;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void printScriptStackTrace(PrintWriter writer)
/* 176:    */   {
/* 177:245 */     StringWriter stringWriter = new StringWriter();
/* 178:246 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/* 179:    */     
/* 180:248 */     getCause().printStackTrace(printWriter);
/* 181:    */     
/* 182:250 */     writer.print(getCause().getMessage());
/* 183:251 */     StringTokenizer st = new StringTokenizer(stringWriter.toString(), "\r\n");
/* 184:252 */     while (st.hasMoreTokens())
/* 185:    */     {
/* 186:253 */       String line = st.nextToken();
/* 187:254 */       if (line.contains("at script"))
/* 188:    */       {
/* 189:255 */         writer.println();
/* 190:256 */         writer.print(line.replaceFirst("at script\\.?", "at "));
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ScriptException
 * JD-Core Version:    0.7.0.1
 */