/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   5:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   6:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   7:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*   9:    */ import org.apache.xml.utils.SystemIDResolver;
/*  10:    */ import org.xml.sax.InputSource;
/*  11:    */ import org.xml.sax.XMLReader;
/*  12:    */ 
/*  13:    */ final class Import
/*  14:    */   extends TopLevelElement
/*  15:    */ {
/*  16: 47 */   private Stylesheet _imported = null;
/*  17:    */   
/*  18:    */   public Stylesheet getImportedStylesheet()
/*  19:    */   {
/*  20: 50 */     return this._imported;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void parseContents(Parser parser)
/*  24:    */   {
/*  25: 54 */     XSLTC xsltc = parser.getXSLTC();
/*  26: 55 */     Stylesheet context = parser.getCurrentStylesheet();
/*  27:    */     try
/*  28:    */     {
/*  29: 58 */       String docToLoad = getAttribute("href");
/*  30: 59 */       if (context.checkForLoop(docToLoad))
/*  31:    */       {
/*  32: 60 */         ErrorMsg msg = new ErrorMsg("CIRCULAR_INCLUDE_ERR", docToLoad, this);
/*  33:    */         
/*  34: 62 */         parser.reportError(2, msg);
/*  35: 63 */         return;
/*  36:    */       }
/*  37: 66 */       InputSource input = null;
/*  38: 67 */       XMLReader reader = null;
/*  39: 68 */       String currLoadedDoc = context.getSystemId();
/*  40: 69 */       SourceLoader loader = context.getSourceLoader();
/*  41: 72 */       if (loader != null)
/*  42:    */       {
/*  43: 73 */         input = loader.loadSource(docToLoad, currLoadedDoc, xsltc);
/*  44: 74 */         if (input != null)
/*  45:    */         {
/*  46: 75 */           docToLoad = input.getSystemId();
/*  47: 76 */           reader = xsltc.getXMLReader();
/*  48:    */         }
/*  49:    */       }
/*  50: 81 */       if (input == null)
/*  51:    */       {
/*  52: 82 */         docToLoad = SystemIDResolver.getAbsoluteURI(docToLoad, currLoadedDoc);
/*  53: 83 */         input = new InputSource(docToLoad);
/*  54:    */       }
/*  55: 87 */       if (input == null)
/*  56:    */       {
/*  57: 88 */         ErrorMsg msg = new ErrorMsg("FILE_NOT_FOUND_ERR", docToLoad, this);
/*  58:    */         
/*  59: 90 */         parser.reportError(2, msg); return;
/*  60:    */       }
/*  61:    */       SyntaxTreeNode root;
/*  62: 95 */       if (reader != null) {
/*  63: 96 */         root = parser.parse(reader, input);
/*  64:    */       } else {
/*  65: 99 */         root = parser.parse(input);
/*  66:    */       }
/*  67:102 */       if (root == null) {
/*  68:102 */         return;
/*  69:    */       }
/*  70:103 */       this._imported = parser.makeStylesheet(root);
/*  71:104 */       if (this._imported == null) {
/*  72:104 */         return;
/*  73:    */       }
/*  74:106 */       this._imported.setSourceLoader(loader);
/*  75:107 */       this._imported.setSystemId(docToLoad);
/*  76:108 */       this._imported.setParentStylesheet(context);
/*  77:109 */       this._imported.setImportingStylesheet(context);
/*  78:110 */       this._imported.setTemplateInlining(context.getTemplateInlining());
/*  79:    */       
/*  80:    */ 
/*  81:113 */       int currPrecedence = parser.getCurrentImportPrecedence();
/*  82:114 */       int nextPrecedence = parser.getNextImportPrecedence();
/*  83:115 */       this._imported.setImportPrecedence(currPrecedence);
/*  84:116 */       context.setImportPrecedence(nextPrecedence);
/*  85:117 */       parser.setCurrentStylesheet(this._imported);
/*  86:118 */       this._imported.parseContents(parser);
/*  87:    */       
/*  88:120 */       Enumeration elements = this._imported.elements();
/*  89:121 */       Stylesheet topStylesheet = parser.getTopLevelStylesheet();
/*  90:122 */       while (elements.hasMoreElements())
/*  91:    */       {
/*  92:123 */         Object element = elements.nextElement();
/*  93:124 */         if ((element instanceof TopLevelElement)) {
/*  94:125 */           if ((element instanceof Variable)) {
/*  95:126 */             topStylesheet.addVariable((Variable)element);
/*  96:128 */           } else if ((element instanceof Param)) {
/*  97:129 */             topStylesheet.addParam((Param)element);
/*  98:    */           } else {
/*  99:132 */             topStylesheet.addElement((TopLevelElement)element);
/* 100:    */           }
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (Exception e)
/* 105:    */     {
/* 106:138 */       e.printStackTrace();
/* 107:    */     }
/* 108:    */     finally
/* 109:    */     {
/* 110:141 */       parser.setCurrentStylesheet(context);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Type typeCheck(SymbolTable stable)
/* 115:    */     throws TypeCheckError
/* 116:    */   {
/* 117:146 */     return Type.Void;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Import
 * JD-Core Version:    0.7.0.1
 */