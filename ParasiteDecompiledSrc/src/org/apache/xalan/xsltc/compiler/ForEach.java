/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Vector;
/*   5:    */ import org.apache.bcel.generic.BranchHandle;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFGT;
/*  10:    */ import org.apache.bcel.generic.InstructionConstants;
/*  11:    */ import org.apache.bcel.generic.InstructionHandle;
/*  12:    */ import org.apache.bcel.generic.InstructionList;
/*  13:    */ import org.apache.bcel.generic.MethodGen;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  24:    */ 
/*  25:    */ final class ForEach
/*  26:    */   extends Instruction
/*  27:    */ {
/*  28:    */   private Expression _select;
/*  29:    */   private Type _type;
/*  30:    */   
/*  31:    */   public void display(int indent)
/*  32:    */   {
/*  33: 55 */     indent(indent);
/*  34: 56 */     Util.println("ForEach");
/*  35: 57 */     indent(indent + 4);
/*  36: 58 */     Util.println("select " + this._select.toString());
/*  37: 59 */     displayContents(indent + 4);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void parseContents(Parser parser)
/*  41:    */   {
/*  42: 63 */     this._select = parser.parseExpression(this, "select", null);
/*  43:    */     
/*  44: 65 */     parseChildren(parser);
/*  45: 68 */     if (this._select.isDummy()) {
/*  46: 69 */       reportError(this, parser, "REQUIRED_ATTR_ERR", "select");
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Type typeCheck(SymbolTable stable)
/*  51:    */     throws TypeCheckError
/*  52:    */   {
/*  53: 74 */     this._type = this._select.typeCheck(stable);
/*  54: 76 */     if (((this._type instanceof ReferenceType)) || ((this._type instanceof NodeType)))
/*  55:    */     {
/*  56: 77 */       this._select = new CastExpr(this._select, Type.NodeSet);
/*  57: 78 */       typeCheckContents(stable);
/*  58: 79 */       return Type.Void;
/*  59:    */     }
/*  60: 81 */     if (((this._type instanceof NodeSetType)) || ((this._type instanceof ResultTreeType)))
/*  61:    */     {
/*  62: 82 */       typeCheckContents(stable);
/*  63: 83 */       return Type.Void;
/*  64:    */     }
/*  65: 85 */     throw new TypeCheckError(this);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  69:    */   {
/*  70: 89 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  71: 90 */     InstructionList il = methodGen.getInstructionList();
/*  72:    */     
/*  73:    */ 
/*  74: 93 */     il.append(methodGen.loadCurrentNode());
/*  75: 94 */     il.append(methodGen.loadIterator());
/*  76:    */     
/*  77:    */ 
/*  78: 97 */     Vector sortObjects = new Vector();
/*  79: 98 */     Enumeration children = elements();
/*  80: 99 */     while (children.hasMoreElements())
/*  81:    */     {
/*  82:100 */       Object child = children.nextElement();
/*  83:101 */       if ((child instanceof Sort)) {
/*  84:102 */         sortObjects.addElement(child);
/*  85:    */       }
/*  86:    */     }
/*  87:106 */     if ((this._type != null) && ((this._type instanceof ResultTreeType)))
/*  88:    */     {
/*  89:108 */       il.append(methodGen.loadDOM());
/*  90:111 */       if (sortObjects.size() > 0)
/*  91:    */       {
/*  92:112 */         ErrorMsg msg = new ErrorMsg("RESULT_TREE_SORT_ERR", this);
/*  93:113 */         getParser().reportError(4, msg);
/*  94:    */       }
/*  95:117 */       this._select.translate(classGen, methodGen);
/*  96:    */       
/*  97:119 */       this._type.translateTo(classGen, methodGen, Type.NodeSet);
/*  98:    */       
/*  99:121 */       il.append(InstructionConstants.SWAP);
/* 100:122 */       il.append(methodGen.storeDOM());
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:126 */       if (sortObjects.size() > 0) {
/* 105:127 */         Sort.translateSortIterator(classGen, methodGen, this._select, sortObjects);
/* 106:    */       } else {
/* 107:131 */         this._select.translate(classGen, methodGen);
/* 108:    */       }
/* 109:134 */       if (!(this._type instanceof ReferenceType))
/* 110:    */       {
/* 111:135 */         il.append(methodGen.loadContextNode());
/* 112:136 */         il.append(methodGen.setStartNode());
/* 113:    */       }
/* 114:    */     }
/* 115:142 */     il.append(methodGen.storeIterator());
/* 116:    */     
/* 117:    */ 
/* 118:145 */     initializeVariables(classGen, methodGen);
/* 119:    */     
/* 120:147 */     BranchHandle nextNode = il.append(new GOTO(null));
/* 121:148 */     InstructionHandle loop = il.append(InstructionConstants.NOP);
/* 122:    */     
/* 123:150 */     translateContents(classGen, methodGen);
/* 124:    */     
/* 125:152 */     nextNode.setTarget(il.append(methodGen.loadIterator()));
/* 126:153 */     il.append(methodGen.nextNode());
/* 127:154 */     il.append(InstructionConstants.DUP);
/* 128:155 */     il.append(methodGen.storeCurrentNode());
/* 129:156 */     il.append(new IFGT(loop));
/* 130:159 */     if ((this._type != null) && ((this._type instanceof ResultTreeType))) {
/* 131:160 */       il.append(methodGen.storeDOM());
/* 132:    */     }
/* 133:164 */     il.append(methodGen.storeIterator());
/* 134:165 */     il.append(methodGen.storeCurrentNode());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void initializeVariables(ClassGenerator classGen, MethodGenerator methodGen)
/* 138:    */   {
/* 139:186 */     int n = elementCount();
/* 140:187 */     for (int i = 0; i < n; i++)
/* 141:    */     {
/* 142:188 */       Object child = getContents().elementAt(i);
/* 143:189 */       if ((child instanceof Variable))
/* 144:    */       {
/* 145:190 */         Variable var = (Variable)child;
/* 146:191 */         var.initialize(classGen, methodGen);
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ForEach
 * JD-Core Version:    0.7.0.1
 */