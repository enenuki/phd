/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   7:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*   8:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*   9:    */ import org.apache.bcel.generic.InstructionConstants;
/*  10:    */ import org.apache.bcel.generic.InstructionList;
/*  11:    */ import org.apache.bcel.generic.MethodGen;
/*  12:    */ import org.apache.bcel.generic.NEW;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ import org.apache.xml.dtm.Axis;
/*  18:    */ 
/*  19:    */ final class UnionPathExpr
/*  20:    */   extends Expression
/*  21:    */ {
/*  22:    */   private final Expression _pathExpr;
/*  23:    */   private final Expression _rest;
/*  24: 47 */   private boolean _reverse = false;
/*  25:    */   private Expression[] _components;
/*  26:    */   
/*  27:    */   public UnionPathExpr(Expression pathExpr, Expression rest)
/*  28:    */   {
/*  29: 53 */     this._pathExpr = pathExpr;
/*  30: 54 */     this._rest = rest;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setParser(Parser parser)
/*  34:    */   {
/*  35: 58 */     super.setParser(parser);
/*  36:    */     
/*  37: 60 */     Vector components = new Vector();
/*  38: 61 */     flatten(components);
/*  39: 62 */     int size = components.size();
/*  40: 63 */     this._components = ((Expression[])components.toArray(new Expression[size]));
/*  41: 64 */     for (int i = 0; i < size; i++)
/*  42:    */     {
/*  43: 65 */       this._components[i].setParser(parser);
/*  44: 66 */       this._components[i].setParent(this);
/*  45: 67 */       if ((this._components[i] instanceof Step))
/*  46:    */       {
/*  47: 68 */         Step step = (Step)this._components[i];
/*  48: 69 */         int axis = step.getAxis();
/*  49: 70 */         int type = step.getNodeType();
/*  50: 72 */         if ((axis == 2) || (type == 2))
/*  51:    */         {
/*  52: 73 */           this._components[i] = this._components[0];
/*  53: 74 */           this._components[0] = step;
/*  54:    */         }
/*  55: 77 */         if (Axis.isReverse(axis)) {
/*  56: 77 */           this._reverse = true;
/*  57:    */         }
/*  58:    */       }
/*  59:    */     }
/*  60: 81 */     if ((getParent() instanceof Expression)) {
/*  61: 81 */       this._reverse = false;
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Type typeCheck(SymbolTable stable)
/*  66:    */     throws TypeCheckError
/*  67:    */   {
/*  68: 85 */     int length = this._components.length;
/*  69: 86 */     for (int i = 0; i < length; i++) {
/*  70: 87 */       if (this._components[i].typeCheck(stable) != Type.NodeSet) {
/*  71: 88 */         this._components[i] = new CastExpr(this._components[i], Type.NodeSet);
/*  72:    */       }
/*  73:    */     }
/*  74: 91 */     return this._type = Type.NodeSet;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79: 95 */     return "union(" + this._pathExpr + ", " + this._rest + ')';
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void flatten(Vector components)
/*  83:    */   {
/*  84: 99 */     components.addElement(this._pathExpr);
/*  85:100 */     if (this._rest != null) {
/*  86:101 */       if ((this._rest instanceof UnionPathExpr)) {
/*  87:102 */         ((UnionPathExpr)this._rest).flatten(components);
/*  88:    */       } else {
/*  89:105 */         components.addElement(this._rest);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  95:    */   {
/*  96:111 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  97:112 */     InstructionList il = methodGen.getInstructionList();
/*  98:    */     
/*  99:114 */     int init = cpg.addMethodref("org.apache.xalan.xsltc.dom.UnionIterator", "<init>", "(Lorg/apache/xalan/xsltc/DOM;)V");
/* 100:    */     
/* 101:    */ 
/* 102:117 */     int iter = cpg.addMethodref("org.apache.xalan.xsltc.dom.UnionIterator", "addIterator", "(Lorg/apache/xml/dtm/DTMAxisIterator;)Lorg/apache/xalan/xsltc/dom/UnionIterator;");
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:122 */     il.append(new NEW(cpg.addClass("org.apache.xalan.xsltc.dom.UnionIterator")));
/* 108:123 */     il.append(InstructionConstants.DUP);
/* 109:124 */     il.append(methodGen.loadDOM());
/* 110:125 */     il.append(new INVOKESPECIAL(init));
/* 111:    */     
/* 112:    */ 
/* 113:128 */     int length = this._components.length;
/* 114:129 */     for (int i = 0; i < length; i++)
/* 115:    */     {
/* 116:130 */       this._components[i].translate(classGen, methodGen);
/* 117:131 */       il.append(new INVOKEVIRTUAL(iter));
/* 118:    */     }
/* 119:135 */     if (this._reverse)
/* 120:    */     {
/* 121:136 */       int order = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "orderNodes", "(Lorg/apache/xml/dtm/DTMAxisIterator;I)Lorg/apache/xml/dtm/DTMAxisIterator;");
/* 122:    */       
/* 123:    */ 
/* 124:139 */       il.append(methodGen.loadDOM());
/* 125:140 */       il.append(InstructionConstants.SWAP);
/* 126:141 */       il.append(methodGen.loadContextNode());
/* 127:142 */       il.append(new INVOKEINTERFACE(order, 3));
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.UnionPathExpr
 * JD-Core Version:    0.7.0.1
 */