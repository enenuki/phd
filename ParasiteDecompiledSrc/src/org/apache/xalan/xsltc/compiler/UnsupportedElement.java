/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.bcel.generic.PUSH;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  16:    */ 
/*  17:    */ final class UnsupportedElement
/*  18:    */   extends SyntaxTreeNode
/*  19:    */ {
/*  20: 43 */   private Vector _fallbacks = null;
/*  21: 44 */   private ErrorMsg _message = null;
/*  22: 45 */   private boolean _isExtension = false;
/*  23:    */   
/*  24:    */   public UnsupportedElement(String uri, String prefix, String local, boolean isExtension)
/*  25:    */   {
/*  26: 51 */     super(uri, prefix, local);
/*  27: 52 */     this._isExtension = isExtension;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setErrorMessage(ErrorMsg message)
/*  31:    */   {
/*  32: 65 */     this._message = message;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void display(int indent)
/*  36:    */   {
/*  37: 72 */     indent(indent);
/*  38: 73 */     Util.println("Unsupported element = " + this._qname.getNamespace() + ":" + this._qname.getLocalPart());
/*  39:    */     
/*  40: 75 */     displayContents(indent + 4);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void processFallbacks(Parser parser)
/*  44:    */   {
/*  45: 84 */     Vector children = getContents();
/*  46: 85 */     if (children != null)
/*  47:    */     {
/*  48: 86 */       int count = children.size();
/*  49: 87 */       for (int i = 0; i < count; i++)
/*  50:    */       {
/*  51: 88 */         SyntaxTreeNode child = (SyntaxTreeNode)children.elementAt(i);
/*  52: 89 */         if ((child instanceof Fallback))
/*  53:    */         {
/*  54: 90 */           Fallback fallback = (Fallback)child;
/*  55: 91 */           fallback.activate();
/*  56: 92 */           fallback.parseContents(parser);
/*  57: 93 */           if (this._fallbacks == null) {
/*  58: 94 */             this._fallbacks = new Vector();
/*  59:    */           }
/*  60: 96 */           this._fallbacks.addElement(child);
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void parseContents(Parser parser)
/*  67:    */   {
/*  68:106 */     processFallbacks(parser);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Type typeCheck(SymbolTable stable)
/*  72:    */     throws TypeCheckError
/*  73:    */   {
/*  74:113 */     if (this._fallbacks != null)
/*  75:    */     {
/*  76:114 */       int count = this._fallbacks.size();
/*  77:115 */       for (int i = 0; i < count; i++)
/*  78:    */       {
/*  79:116 */         Fallback fallback = (Fallback)this._fallbacks.elementAt(i);
/*  80:117 */         fallback.typeCheck(stable);
/*  81:    */       }
/*  82:    */     }
/*  83:120 */     return Type.Void;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  87:    */   {
/*  88:127 */     if (this._fallbacks != null)
/*  89:    */     {
/*  90:128 */       int count = this._fallbacks.size();
/*  91:129 */       for (int i = 0; i < count; i++)
/*  92:    */       {
/*  93:130 */         Fallback fallback = (Fallback)this._fallbacks.elementAt(i);
/*  94:131 */         fallback.translate(classGen, methodGen);
/*  95:    */       }
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:140 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 100:141 */       InstructionList il = methodGen.getInstructionList();
/* 101:    */       
/* 102:143 */       int unsupportedElem = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "unsupported_ElementF", "(Ljava/lang/String;Z)V");
/* 103:    */       
/* 104:145 */       il.append(new PUSH(cpg, getQName().toString()));
/* 105:146 */       il.append(new PUSH(cpg, this._isExtension));
/* 106:147 */       il.append(new INVOKESTATIC(unsupportedElem));
/* 107:    */     }
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.UnsupportedElement
 * JD-Core Version:    0.7.0.1
 */