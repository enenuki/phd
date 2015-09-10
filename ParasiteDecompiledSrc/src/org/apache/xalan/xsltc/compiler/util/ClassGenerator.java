/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.classfile.Method;
/*   4:    */ import org.apache.bcel.generic.ALOAD;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.Instruction;
/*   7:    */ import org.apache.xalan.xsltc.compiler.Constants;
/*   8:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*   9:    */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*  10:    */ import org.apache.xalan.xsltc.compiler.SyntaxTreeNode;
/*  11:    */ 
/*  12:    */ public class ClassGenerator
/*  13:    */   extends ClassGen
/*  14:    */ {
/*  15:    */   protected static final int TRANSLET_INDEX = 0;
/*  16:    */   private Stylesheet _stylesheet;
/*  17:    */   private final Parser _parser;
/*  18:    */   private final Instruction _aloadTranslet;
/*  19:    */   private final String _domClass;
/*  20:    */   private final String _domClassSig;
/*  21:    */   private final String _applyTemplatesSig;
/*  22:    */   private final String _applyTemplatesSigForImport;
/*  23:    */   
/*  24:    */   public ClassGenerator(String class_name, String super_class_name, String file_name, int access_flags, String[] interfaces, Stylesheet stylesheet)
/*  25:    */   {
/*  26: 64 */     super(class_name, super_class_name, file_name, access_flags, interfaces);
/*  27:    */     
/*  28: 66 */     this._stylesheet = stylesheet;
/*  29: 67 */     this._parser = stylesheet.getParser();
/*  30: 68 */     this._aloadTranslet = new ALOAD(0);
/*  31: 70 */     if (stylesheet.isMultiDocument())
/*  32:    */     {
/*  33: 71 */       this._domClass = "org.apache.xalan.xsltc.dom.MultiDOM";
/*  34: 72 */       this._domClassSig = "Lorg/apache/xalan/xsltc/dom/MultiDOM;";
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 75 */       this._domClass = "org.apache.xalan.xsltc.dom.DOMAdapter";
/*  39: 76 */       this._domClassSig = "Lorg/apache/xalan/xsltc/dom/DOMAdapter;";
/*  40:    */     }
/*  41: 78 */     this._applyTemplatesSig = ("(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + ")V");
/*  42:    */     
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 84 */     this._applyTemplatesSigForImport = ("(Lorg/apache/xalan/xsltc/DOM;Lorg/apache/xml/dtm/DTMAxisIterator;" + Constants.TRANSLET_OUTPUT_SIG + "I" + ")V");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final Parser getParser()
/*  51:    */   {
/*  52: 93 */     return this._parser;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final Stylesheet getStylesheet()
/*  56:    */   {
/*  57: 97 */     return this._stylesheet;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final String getClassName()
/*  61:    */   {
/*  62:105 */     return this._stylesheet.getClassName();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Instruction loadTranslet()
/*  66:    */   {
/*  67:109 */     return this._aloadTranslet;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final String getDOMClass()
/*  71:    */   {
/*  72:113 */     return this._domClass;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final String getDOMClassSig()
/*  76:    */   {
/*  77:117 */     return this._domClassSig;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final String getApplyTemplatesSig()
/*  81:    */   {
/*  82:121 */     return this._applyTemplatesSig;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final String getApplyTemplatesSigForImport()
/*  86:    */   {
/*  87:125 */     return this._applyTemplatesSigForImport;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isExternal()
/*  91:    */   {
/*  92:133 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void addMethod(MethodGenerator methodGen)
/*  96:    */   {
/*  97:137 */     Method[] methodsToAdd = methodGen.getGeneratedMethods(this);
/*  98:138 */     for (int i = 0; i < methodsToAdd.length; i++) {
/*  99:139 */       addMethod(methodsToAdd[i]);
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.ClassGenerator
 * JD-Core Version:    0.7.0.1
 */