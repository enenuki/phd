/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.IF_ICMPNE;
/*   6:    */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.bcel.generic.SIPUSH;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MultiHashtable;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  19:    */ 
/*  20:    */ final class CastExpr
/*  21:    */   extends Expression
/*  22:    */ {
/*  23:    */   private final Expression _left;
/*  24: 52 */   private static MultiHashtable InternalTypeMap = new MultiHashtable();
/*  25:    */   
/*  26:    */   static
/*  27:    */   {
/*  28: 56 */     InternalTypeMap.put(Type.Boolean, Type.Boolean);
/*  29: 57 */     InternalTypeMap.put(Type.Boolean, Type.Real);
/*  30: 58 */     InternalTypeMap.put(Type.Boolean, Type.String);
/*  31: 59 */     InternalTypeMap.put(Type.Boolean, Type.Reference);
/*  32: 60 */     InternalTypeMap.put(Type.Boolean, Type.Object);
/*  33:    */     
/*  34: 62 */     InternalTypeMap.put(Type.Real, Type.Real);
/*  35: 63 */     InternalTypeMap.put(Type.Real, Type.Int);
/*  36: 64 */     InternalTypeMap.put(Type.Real, Type.Boolean);
/*  37: 65 */     InternalTypeMap.put(Type.Real, Type.String);
/*  38: 66 */     InternalTypeMap.put(Type.Real, Type.Reference);
/*  39: 67 */     InternalTypeMap.put(Type.Real, Type.Object);
/*  40:    */     
/*  41: 69 */     InternalTypeMap.put(Type.Int, Type.Int);
/*  42: 70 */     InternalTypeMap.put(Type.Int, Type.Real);
/*  43: 71 */     InternalTypeMap.put(Type.Int, Type.Boolean);
/*  44: 72 */     InternalTypeMap.put(Type.Int, Type.String);
/*  45: 73 */     InternalTypeMap.put(Type.Int, Type.Reference);
/*  46: 74 */     InternalTypeMap.put(Type.Int, Type.Object);
/*  47:    */     
/*  48: 76 */     InternalTypeMap.put(Type.String, Type.String);
/*  49: 77 */     InternalTypeMap.put(Type.String, Type.Boolean);
/*  50: 78 */     InternalTypeMap.put(Type.String, Type.Real);
/*  51: 79 */     InternalTypeMap.put(Type.String, Type.Reference);
/*  52: 80 */     InternalTypeMap.put(Type.String, Type.Object);
/*  53:    */     
/*  54: 82 */     InternalTypeMap.put(Type.NodeSet, Type.NodeSet);
/*  55: 83 */     InternalTypeMap.put(Type.NodeSet, Type.Boolean);
/*  56: 84 */     InternalTypeMap.put(Type.NodeSet, Type.Real);
/*  57: 85 */     InternalTypeMap.put(Type.NodeSet, Type.String);
/*  58: 86 */     InternalTypeMap.put(Type.NodeSet, Type.Node);
/*  59: 87 */     InternalTypeMap.put(Type.NodeSet, Type.Reference);
/*  60: 88 */     InternalTypeMap.put(Type.NodeSet, Type.Object);
/*  61:    */     
/*  62: 90 */     InternalTypeMap.put(Type.Node, Type.Node);
/*  63: 91 */     InternalTypeMap.put(Type.Node, Type.Boolean);
/*  64: 92 */     InternalTypeMap.put(Type.Node, Type.Real);
/*  65: 93 */     InternalTypeMap.put(Type.Node, Type.String);
/*  66: 94 */     InternalTypeMap.put(Type.Node, Type.NodeSet);
/*  67: 95 */     InternalTypeMap.put(Type.Node, Type.Reference);
/*  68: 96 */     InternalTypeMap.put(Type.Node, Type.Object);
/*  69:    */     
/*  70: 98 */     InternalTypeMap.put(Type.ResultTree, Type.ResultTree);
/*  71: 99 */     InternalTypeMap.put(Type.ResultTree, Type.Boolean);
/*  72:100 */     InternalTypeMap.put(Type.ResultTree, Type.Real);
/*  73:101 */     InternalTypeMap.put(Type.ResultTree, Type.String);
/*  74:102 */     InternalTypeMap.put(Type.ResultTree, Type.NodeSet);
/*  75:103 */     InternalTypeMap.put(Type.ResultTree, Type.Reference);
/*  76:104 */     InternalTypeMap.put(Type.ResultTree, Type.Object);
/*  77:    */     
/*  78:106 */     InternalTypeMap.put(Type.Reference, Type.Reference);
/*  79:107 */     InternalTypeMap.put(Type.Reference, Type.Boolean);
/*  80:108 */     InternalTypeMap.put(Type.Reference, Type.Int);
/*  81:109 */     InternalTypeMap.put(Type.Reference, Type.Real);
/*  82:110 */     InternalTypeMap.put(Type.Reference, Type.String);
/*  83:111 */     InternalTypeMap.put(Type.Reference, Type.Node);
/*  84:112 */     InternalTypeMap.put(Type.Reference, Type.NodeSet);
/*  85:113 */     InternalTypeMap.put(Type.Reference, Type.ResultTree);
/*  86:114 */     InternalTypeMap.put(Type.Reference, Type.Object);
/*  87:    */     
/*  88:116 */     InternalTypeMap.put(Type.Object, Type.String);
/*  89:    */     
/*  90:118 */     InternalTypeMap.put(Type.Void, Type.String);
/*  91:    */   }
/*  92:    */   
/*  93:121 */   private boolean _typeTest = false;
/*  94:    */   
/*  95:    */   public CastExpr(Expression left, Type type)
/*  96:    */     throws TypeCheckError
/*  97:    */   {
/*  98:128 */     this._left = left;
/*  99:129 */     this._type = type;
/* 100:131 */     if (((this._left instanceof Step)) && (this._type == Type.Boolean))
/* 101:    */     {
/* 102:132 */       Step step = (Step)this._left;
/* 103:133 */       if ((step.getAxis() == 13) && (step.getNodeType() != -1)) {
/* 104:134 */         this._typeTest = true;
/* 105:    */       }
/* 106:    */     }
/* 107:138 */     setParser(left.getParser());
/* 108:139 */     setParent(left.getParent());
/* 109:140 */     left.setParent(this);
/* 110:141 */     typeCheck(left.getParser().getSymbolTable());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Expression getExpr()
/* 114:    */   {
/* 115:145 */     return this._left;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean hasPositionCall()
/* 119:    */   {
/* 120:153 */     return this._left.hasPositionCall();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean hasLastCall()
/* 124:    */   {
/* 125:157 */     return this._left.hasLastCall();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String toString()
/* 129:    */   {
/* 130:161 */     return "cast(" + this._left + ", " + this._type + ")";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Type typeCheck(SymbolTable stable)
/* 134:    */     throws TypeCheckError
/* 135:    */   {
/* 136:171 */     Type tleft = this._left.getType();
/* 137:172 */     if (tleft == null) {
/* 138:173 */       tleft = this._left.typeCheck(stable);
/* 139:    */     }
/* 140:175 */     if ((tleft instanceof NodeType)) {
/* 141:176 */       tleft = Type.Node;
/* 142:178 */     } else if ((tleft instanceof ResultTreeType)) {
/* 143:179 */       tleft = Type.ResultTree;
/* 144:    */     }
/* 145:181 */     if (InternalTypeMap.maps(tleft, this._type) != null) {
/* 146:182 */       return this._type;
/* 147:    */     }
/* 148:185 */     throw new TypeCheckError(new ErrorMsg("DATA_CONVERSION_ERR", tleft.toString(), this._type.toString()));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/* 152:    */   {
/* 153:192 */     Type ltype = this._left.getType();
/* 154:197 */     if (this._typeTest)
/* 155:    */     {
/* 156:198 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 157:199 */       InstructionList il = methodGen.getInstructionList();
/* 158:    */       
/* 159:201 */       int idx = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getExpandedTypeID", "(I)I");
/* 160:    */       
/* 161:    */ 
/* 162:204 */       il.append(new SIPUSH((short)((Step)this._left).getNodeType()));
/* 163:205 */       il.append(methodGen.loadDOM());
/* 164:206 */       il.append(methodGen.loadContextNode());
/* 165:207 */       il.append(new INVOKEINTERFACE(idx, 2));
/* 166:208 */       this._falseList.add(il.append(new IF_ICMPNE(null)));
/* 167:    */     }
/* 168:    */     else
/* 169:    */     {
/* 170:212 */       this._left.translate(classGen, methodGen);
/* 171:213 */       if (this._type != ltype)
/* 172:    */       {
/* 173:214 */         this._left.startIterator(classGen, methodGen);
/* 174:215 */         if ((this._type instanceof BooleanType))
/* 175:    */         {
/* 176:216 */           FlowList fl = ltype.translateToDesynthesized(classGen, methodGen, this._type);
/* 177:218 */           if (fl != null) {
/* 178:219 */             this._falseList.append(fl);
/* 179:    */           }
/* 180:    */         }
/* 181:    */         else
/* 182:    */         {
/* 183:223 */           ltype.translateTo(classGen, methodGen, this._type);
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 190:    */   {
/* 191:230 */     Type ltype = this._left.getType();
/* 192:231 */     this._left.translate(classGen, methodGen);
/* 193:232 */     if (!this._type.identicalTo(ltype))
/* 194:    */     {
/* 195:233 */       this._left.startIterator(classGen, methodGen);
/* 196:234 */       ltype.translateTo(classGen, methodGen, this._type);
/* 197:    */     }
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CastExpr
 * JD-Core Version:    0.7.0.1
 */