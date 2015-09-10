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
/*  13:    */ final class Include
/*  14:    */   extends TopLevelElement
/*  15:    */ {
/*  16: 48 */   private Stylesheet _included = null;
/*  17:    */   
/*  18:    */   public Stylesheet getIncludedStylesheet()
/*  19:    */   {
/*  20: 51 */     return this._included;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void parseContents(Parser parser)
/*  24:    */   {
/*  25: 55 */     XSLTC xsltc = parser.getXSLTC();
/*  26: 56 */     Stylesheet context = parser.getCurrentStylesheet();
/*  27:    */     
/*  28: 58 */     String docToLoad = getAttribute("href");
/*  29:    */     try
/*  30:    */     {
/*  31: 60 */       if (context.checkForLoop(docToLoad))
/*  32:    */       {
/*  33: 61 */         ErrorMsg msg = new ErrorMsg("CIRCULAR_INCLUDE_ERR", docToLoad, this);
/*  34:    */         
/*  35: 63 */         parser.reportError(2, msg);
/*  36: 64 */         return;
/*  37:    */       }
/*  38: 67 */       InputSource input = null;
/*  39: 68 */       XMLReader reader = null;
/*  40: 69 */       String currLoadedDoc = context.getSystemId();
/*  41: 70 */       SourceLoader loader = context.getSourceLoader();
/*  42: 73 */       if (loader != null)
/*  43:    */       {
/*  44: 74 */         input = loader.loadSource(docToLoad, currLoadedDoc, xsltc);
/*  45: 75 */         if (input != null)
/*  46:    */         {
/*  47: 76 */           docToLoad = input.getSystemId();
/*  48: 77 */           reader = xsltc.getXMLReader();
/*  49:    */         }
/*  50:    */       }
/*  51: 82 */       if (input == null)
/*  52:    */       {
/*  53: 83 */         docToLoad = SystemIDResolver.getAbsoluteURI(docToLoad, currLoadedDoc);
/*  54: 84 */         input = new InputSource(docToLoad);
/*  55:    */       }
/*  56: 88 */       if (input == null)
/*  57:    */       {
/*  58: 89 */         ErrorMsg msg = new ErrorMsg("FILE_NOT_FOUND_ERR", docToLoad, this);
/*  59:    */         
/*  60: 91 */         parser.reportError(2, msg); return;
/*  61:    */       }
/*  62:    */       SyntaxTreeNode root;
/*  63: 96 */       if (reader != null) {
/*  64: 97 */         root = parser.parse(reader, input);
/*  65:    */       } else {
/*  66:100 */         root = parser.parse(input);
/*  67:    */       }
/*  68:103 */       if (root == null) {
/*  69:103 */         return;
/*  70:    */       }
/*  71:104 */       this._included = parser.makeStylesheet(root);
/*  72:105 */       if (this._included == null) {
/*  73:105 */         return;
/*  74:    */       }
/*  75:107 */       this._included.setSourceLoader(loader);
/*  76:108 */       this._included.setSystemId(docToLoad);
/*  77:109 */       this._included.setParentStylesheet(context);
/*  78:110 */       this._included.setIncludingStylesheet(context);
/*  79:111 */       this._included.setTemplateInlining(context.getTemplateInlining());
/*  80:    */       
/*  81:    */ 
/*  82:    */ 
/*  83:115 */       int precedence = context.getImportPrecedence();
/*  84:116 */       this._included.setImportPrecedence(precedence);
/*  85:117 */       parser.setCurrentStylesheet(this._included);
/*  86:118 */       this._included.parseContents(parser);
/*  87:    */       
/*  88:120 */       Enumeration elements = this._included.elements();
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
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Include
 * JD-Core Version:    0.7.0.1
 */