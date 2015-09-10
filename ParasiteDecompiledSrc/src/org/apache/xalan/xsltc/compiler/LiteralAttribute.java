/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ClassGen;
/*   4:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   5:    */ import org.apache.bcel.generic.InstructionList;
/*   6:    */ import org.apache.bcel.generic.MethodGen;
/*   7:    */ import org.apache.bcel.generic.PUSH;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  13:    */ import org.apache.xml.serializer.ElemDesc;
/*  14:    */ 
/*  15:    */ final class LiteralAttribute
/*  16:    */   extends Instruction
/*  17:    */ {
/*  18:    */   private final String _name;
/*  19:    */   private final AttributeValue _value;
/*  20:    */   
/*  21:    */   public LiteralAttribute(String name, String value, Parser parser, SyntaxTreeNode parent)
/*  22:    */   {
/*  23: 55 */     this._name = name;
/*  24: 56 */     setParent(parent);
/*  25: 57 */     this._value = AttributeValue.create(this, value, parser);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void display(int indent)
/*  29:    */   {
/*  30: 61 */     indent(indent);
/*  31: 62 */     Util.println("LiteralAttribute name=" + this._name + " value=" + this._value);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Type typeCheck(SymbolTable stable)
/*  35:    */     throws TypeCheckError
/*  36:    */   {
/*  37: 66 */     this._value.typeCheck(stable);
/*  38: 67 */     typeCheckContents(stable);
/*  39: 68 */     return Type.Void;
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected boolean contextDependent()
/*  43:    */   {
/*  44: 72 */     return this._value.contextDependent();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  48:    */   {
/*  49: 76 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  50: 77 */     InstructionList il = methodGen.getInstructionList();
/*  51:    */     
/*  52:    */ 
/*  53: 80 */     il.append(methodGen.loadHandler());
/*  54:    */     
/*  55: 82 */     il.append(new PUSH(cpg, this._name));
/*  56:    */     
/*  57: 84 */     this._value.translate(classGen, methodGen);
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61: 88 */     SyntaxTreeNode parent = getParent();
/*  62: 89 */     if (((parent instanceof LiteralElement)) && (((LiteralElement)parent).allAttributesUnique()))
/*  63:    */     {
/*  64: 92 */       int flags = 0;
/*  65: 93 */       boolean isHTMLAttrEmpty = false;
/*  66: 94 */       ElemDesc elemDesc = ((LiteralElement)parent).getElemDesc();
/*  67: 97 */       if (elemDesc != null) {
/*  68: 98 */         if (elemDesc.isAttrFlagSet(this._name, 4))
/*  69:    */         {
/*  70: 99 */           flags |= 0x2;
/*  71:100 */           isHTMLAttrEmpty = true;
/*  72:    */         }
/*  73:102 */         else if (elemDesc.isAttrFlagSet(this._name, 2))
/*  74:    */         {
/*  75:103 */           flags |= 0x4;
/*  76:    */         }
/*  77:    */       }
/*  78:107 */       if ((this._value instanceof SimpleAttributeValue))
/*  79:    */       {
/*  80:108 */         String attrValue = ((SimpleAttributeValue)this._value).toString();
/*  81:110 */         if ((!hasBadChars(attrValue)) && (!isHTMLAttrEmpty)) {
/*  82:111 */           flags |= 0x1;
/*  83:    */         }
/*  84:    */       }
/*  85:115 */       il.append(new PUSH(cpg, flags));
/*  86:116 */       il.append(methodGen.uniqueAttribute());
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90:120 */       il.append(methodGen.attribute());
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private boolean hasBadChars(String value)
/*  95:    */   {
/*  96:133 */     char[] chars = value.toCharArray();
/*  97:134 */     int size = chars.length;
/*  98:135 */     for (int i = 0; i < size; i++)
/*  99:    */     {
/* 100:136 */       char ch = chars[i];
/* 101:137 */       if ((ch < ' ') || ('~' < ch) || (ch == '<') || (ch == '>') || (ch == '&') || (ch == '"')) {
/* 102:138 */         return true;
/* 103:    */       }
/* 104:    */     }
/* 105:140 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getName()
/* 109:    */   {
/* 110:147 */     return this._name;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public AttributeValue getValue()
/* 114:    */   {
/* 115:154 */     return this._value;
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LiteralAttribute
 * JD-Core Version:    0.7.0.1
 */