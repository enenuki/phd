/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ 
/*   5:    */ class ParserGrammar
/*   6:    */   extends Grammar
/*   7:    */ {
/*   8:    */   ParserGrammar(String paramString1, Tool paramTool, String paramString2)
/*   9:    */   {
/*  10: 23 */     super(paramString1, paramTool, paramString2);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public void generate()
/*  14:    */     throws IOException
/*  15:    */   {
/*  16: 28 */     this.generator.gen(this);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected String getSuperClass()
/*  20:    */   {
/*  21: 34 */     if (this.debuggingOutput) {
/*  22: 35 */       return "debug.LLkDebuggingParser";
/*  23:    */     }
/*  24: 36 */     return "LLkParser";
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void processArguments(String[] paramArrayOfString)
/*  28:    */   {
/*  29: 45 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  30: 46 */       if (paramArrayOfString[i].equals("-trace"))
/*  31:    */       {
/*  32: 47 */         this.traceRules = true;
/*  33: 48 */         this.antlrTool.setArgOK(i);
/*  34:    */       }
/*  35: 50 */       else if (paramArrayOfString[i].equals("-traceParser"))
/*  36:    */       {
/*  37: 51 */         this.traceRules = true;
/*  38: 52 */         this.antlrTool.setArgOK(i);
/*  39:    */       }
/*  40: 54 */       else if (paramArrayOfString[i].equals("-debug"))
/*  41:    */       {
/*  42: 55 */         this.debuggingOutput = true;
/*  43: 56 */         this.antlrTool.setArgOK(i);
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean setOption(String paramString, Token paramToken)
/*  49:    */   {
/*  50: 64 */     String str = paramToken.getText();
/*  51: 65 */     if (paramString.equals("buildAST"))
/*  52:    */     {
/*  53: 66 */       if (str.equals("true")) {
/*  54: 67 */         this.buildAST = true;
/*  55: 69 */       } else if (str.equals("false")) {
/*  56: 70 */         this.buildAST = false;
/*  57:    */       } else {
/*  58: 73 */         this.antlrTool.error("buildAST option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  59:    */       }
/*  60: 75 */       return true;
/*  61:    */     }
/*  62: 77 */     if (paramString.equals("interactive"))
/*  63:    */     {
/*  64: 78 */       if (str.equals("true")) {
/*  65: 79 */         this.interactive = true;
/*  66: 81 */       } else if (str.equals("false")) {
/*  67: 82 */         this.interactive = false;
/*  68:    */       } else {
/*  69: 85 */         this.antlrTool.error("interactive option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  70:    */       }
/*  71: 87 */       return true;
/*  72:    */     }
/*  73: 89 */     if (paramString.equals("ASTLabelType"))
/*  74:    */     {
/*  75: 90 */       super.setOption(paramString, paramToken);
/*  76: 91 */       return true;
/*  77:    */     }
/*  78: 93 */     if (paramString.equals("className"))
/*  79:    */     {
/*  80: 94 */       super.setOption(paramString, paramToken);
/*  81: 95 */       return true;
/*  82:    */     }
/*  83: 97 */     if (super.setOption(paramString, paramToken)) {
/*  84: 98 */       return true;
/*  85:    */     }
/*  86:100 */     this.antlrTool.error("Invalid option: " + paramString, getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  87:101 */     return false;
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ParserGrammar
 * JD-Core Version:    0.7.0.1
 */