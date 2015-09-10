/*   1:    */ package antlr.preprocessor;
/*   2:    */ 
/*   3:    */ import antlr.ANTLRException;
/*   4:    */ import antlr.TokenStreamException;
/*   5:    */ import antlr.Tool;
/*   6:    */ import antlr.collections.impl.IndexedVector;
/*   7:    */ import java.io.BufferedReader;
/*   8:    */ import java.io.FileNotFoundException;
/*   9:    */ import java.io.FileReader;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.Hashtable;
/*  12:    */ 
/*  13:    */ public class Hierarchy
/*  14:    */ {
/*  15: 20 */   protected Grammar LexerRoot = null;
/*  16: 21 */   protected Grammar ParserRoot = null;
/*  17: 22 */   protected Grammar TreeParserRoot = null;
/*  18:    */   protected Hashtable symbols;
/*  19:    */   protected Hashtable files;
/*  20:    */   protected Tool antlrTool;
/*  21:    */   
/*  22:    */   public Hierarchy(Tool paramTool)
/*  23:    */   {
/*  24: 28 */     this.antlrTool = paramTool;
/*  25: 29 */     this.LexerRoot = new Grammar(paramTool, "Lexer", null, null);
/*  26: 30 */     this.ParserRoot = new Grammar(paramTool, "Parser", null, null);
/*  27: 31 */     this.TreeParserRoot = new Grammar(paramTool, "TreeParser", null, null);
/*  28: 32 */     this.symbols = new Hashtable(10);
/*  29: 33 */     this.files = new Hashtable(10);
/*  30:    */     
/*  31: 35 */     this.LexerRoot.setPredefined(true);
/*  32: 36 */     this.ParserRoot.setPredefined(true);
/*  33: 37 */     this.TreeParserRoot.setPredefined(true);
/*  34:    */     
/*  35: 39 */     this.symbols.put(this.LexerRoot.getName(), this.LexerRoot);
/*  36: 40 */     this.symbols.put(this.ParserRoot.getName(), this.ParserRoot);
/*  37: 41 */     this.symbols.put(this.TreeParserRoot.getName(), this.TreeParserRoot);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void addGrammar(Grammar paramGrammar)
/*  41:    */   {
/*  42: 45 */     paramGrammar.setHierarchy(this);
/*  43:    */     
/*  44: 47 */     this.symbols.put(paramGrammar.getName(), paramGrammar);
/*  45:    */     
/*  46: 49 */     GrammarFile localGrammarFile = getFile(paramGrammar.getFileName());
/*  47: 50 */     localGrammarFile.addGrammar(paramGrammar);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void addGrammarFile(GrammarFile paramGrammarFile)
/*  51:    */   {
/*  52: 54 */     this.files.put(paramGrammarFile.getName(), paramGrammarFile);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void expandGrammarsInFile(String paramString)
/*  56:    */   {
/*  57: 58 */     GrammarFile localGrammarFile = getFile(paramString);
/*  58: 59 */     for (Enumeration localEnumeration = localGrammarFile.getGrammars().elements(); localEnumeration.hasMoreElements();)
/*  59:    */     {
/*  60: 60 */       Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*  61: 61 */       localGrammar.expandInPlace();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Grammar findRoot(Grammar paramGrammar)
/*  66:    */   {
/*  67: 66 */     if (paramGrammar.getSuperGrammarName() == null) {
/*  68: 67 */       return paramGrammar;
/*  69:    */     }
/*  70: 70 */     Grammar localGrammar = paramGrammar.getSuperGrammar();
/*  71: 71 */     if (localGrammar == null) {
/*  72: 71 */       return paramGrammar;
/*  73:    */     }
/*  74: 72 */     return findRoot(localGrammar);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public GrammarFile getFile(String paramString)
/*  78:    */   {
/*  79: 76 */     return (GrammarFile)this.files.get(paramString);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Grammar getGrammar(String paramString)
/*  83:    */   {
/*  84: 80 */     return (Grammar)this.symbols.get(paramString);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String optionsToString(IndexedVector paramIndexedVector)
/*  88:    */   {
/*  89: 84 */     String str = "options {" + System.getProperty("line.separator");
/*  90: 85 */     for (Enumeration localEnumeration = paramIndexedVector.elements(); localEnumeration.hasMoreElements();) {
/*  91: 86 */       str = str + (Option)localEnumeration.nextElement() + System.getProperty("line.separator");
/*  92:    */     }
/*  93: 88 */     str = str + "}" + System.getProperty("line.separator") + System.getProperty("line.separator");
/*  94:    */     
/*  95:    */ 
/*  96: 91 */     return str;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void readGrammarFile(String paramString)
/* 100:    */     throws FileNotFoundException
/* 101:    */   {
/* 102: 95 */     BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/* 103: 96 */     addGrammarFile(new GrammarFile(this.antlrTool, paramString));
/* 104:    */     
/* 105:    */ 
/* 106: 99 */     PreprocessorLexer localPreprocessorLexer = new PreprocessorLexer(localBufferedReader);
/* 107:100 */     localPreprocessorLexer.setFilename(paramString);
/* 108:101 */     Preprocessor localPreprocessor = new Preprocessor(localPreprocessorLexer);
/* 109:102 */     localPreprocessor.setTool(this.antlrTool);
/* 110:103 */     localPreprocessor.setFilename(paramString);
/* 111:    */     try
/* 112:    */     {
/* 113:107 */       localPreprocessor.grammarFile(this, paramString);
/* 114:    */     }
/* 115:    */     catch (TokenStreamException localTokenStreamException)
/* 116:    */     {
/* 117:110 */       this.antlrTool.toolError("Token stream error reading grammar(s):\n" + localTokenStreamException);
/* 118:    */     }
/* 119:    */     catch (ANTLRException localANTLRException)
/* 120:    */     {
/* 121:113 */       this.antlrTool.toolError("error reading grammar(s):\n" + localANTLRException);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean verifyThatHierarchyIsComplete()
/* 126:    */   {
/* 127:119 */     int i = 1;
/* 128:121 */     for (Enumeration localEnumeration = this.symbols.elements(); localEnumeration.hasMoreElements();)
/* 129:    */     {
/* 130:122 */       localGrammar1 = (Grammar)localEnumeration.nextElement();
/* 131:123 */       if (localGrammar1.getSuperGrammarName() != null)
/* 132:    */       {
/* 133:126 */         Grammar localGrammar2 = localGrammar1.getSuperGrammar();
/* 134:127 */         if (localGrammar2 == null)
/* 135:    */         {
/* 136:128 */           this.antlrTool.toolError("grammar " + localGrammar1.getSuperGrammarName() + " not defined");
/* 137:129 */           i = 0;
/* 138:130 */           this.symbols.remove(localGrammar1.getName());
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:    */     Grammar localGrammar1;
/* 143:134 */     if (i == 0) {
/* 144:134 */       return false;
/* 145:    */     }
/* 146:139 */     for (localEnumeration = this.symbols.elements(); localEnumeration.hasMoreElements();)
/* 147:    */     {
/* 148:140 */       localGrammar1 = (Grammar)localEnumeration.nextElement();
/* 149:141 */       if (localGrammar1.getSuperGrammarName() != null) {
/* 150:144 */         localGrammar1.setType(findRoot(localGrammar1).getName());
/* 151:    */       }
/* 152:    */     }
/* 153:147 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Tool getTool()
/* 157:    */   {
/* 158:151 */     return this.antlrTool;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setTool(Tool paramTool)
/* 162:    */   {
/* 163:155 */     this.antlrTool = paramTool;
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.Hierarchy
 * JD-Core Version:    0.7.0.1
 */