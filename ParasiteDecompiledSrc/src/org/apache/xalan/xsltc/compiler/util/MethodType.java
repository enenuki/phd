/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ 
/*   5:    */ public final class MethodType
/*   6:    */   extends Type
/*   7:    */ {
/*   8:    */   private final Type _resultType;
/*   9:    */   private final Vector _argsType;
/*  10:    */   
/*  11:    */   public MethodType(Type resultType)
/*  12:    */   {
/*  13: 35 */     this._argsType = null;
/*  14: 36 */     this._resultType = resultType;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public MethodType(Type resultType, Type arg1)
/*  18:    */   {
/*  19: 40 */     if (arg1 != Type.Void)
/*  20:    */     {
/*  21: 41 */       this._argsType = new Vector();
/*  22: 42 */       this._argsType.addElement(arg1);
/*  23:    */     }
/*  24:    */     else
/*  25:    */     {
/*  26: 45 */       this._argsType = null;
/*  27:    */     }
/*  28: 47 */     this._resultType = resultType;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public MethodType(Type resultType, Type arg1, Type arg2)
/*  32:    */   {
/*  33: 51 */     this._argsType = new Vector(2);
/*  34: 52 */     this._argsType.addElement(arg1);
/*  35: 53 */     this._argsType.addElement(arg2);
/*  36: 54 */     this._resultType = resultType;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public MethodType(Type resultType, Type arg1, Type arg2, Type arg3)
/*  40:    */   {
/*  41: 58 */     this._argsType = new Vector(3);
/*  42: 59 */     this._argsType.addElement(arg1);
/*  43: 60 */     this._argsType.addElement(arg2);
/*  44: 61 */     this._argsType.addElement(arg3);
/*  45: 62 */     this._resultType = resultType;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public MethodType(Type resultType, Vector argsType)
/*  49:    */   {
/*  50: 66 */     this._resultType = resultType;
/*  51: 67 */     this._argsType = (argsType.size() > 0 ? argsType : null);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String toString()
/*  55:    */   {
/*  56: 71 */     StringBuffer result = new StringBuffer("method{");
/*  57: 72 */     if (this._argsType != null)
/*  58:    */     {
/*  59: 73 */       int count = this._argsType.size();
/*  60: 74 */       for (int i = 0; i < count; i++)
/*  61:    */       {
/*  62: 75 */         result.append(this._argsType.elementAt(i));
/*  63: 76 */         if (i != count - 1) {
/*  64: 76 */           result.append(',');
/*  65:    */         }
/*  66:    */       }
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70: 80 */       result.append("void");
/*  71:    */     }
/*  72: 82 */     result.append('}');
/*  73: 83 */     return result.toString();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toSignature()
/*  77:    */   {
/*  78: 87 */     return toSignature("");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String toSignature(String lastArgSig)
/*  82:    */   {
/*  83: 95 */     StringBuffer buffer = new StringBuffer();
/*  84: 96 */     buffer.append('(');
/*  85: 97 */     if (this._argsType != null)
/*  86:    */     {
/*  87: 98 */       int n = this._argsType.size();
/*  88: 99 */       for (int i = 0; i < n; i++) {
/*  89:100 */         buffer.append(((Type)this._argsType.elementAt(i)).toSignature());
/*  90:    */       }
/*  91:    */     }
/*  92:103 */     return lastArgSig + ')' + this._resultType.toSignature();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public org.apache.bcel.generic.Type toJCType()
/*  96:    */   {
/*  97:111 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean identicalTo(Type other)
/* 101:    */   {
/* 102:115 */     boolean result = false;
/* 103:116 */     if ((other instanceof MethodType))
/* 104:    */     {
/* 105:117 */       MethodType temp = (MethodType)other;
/* 106:118 */       if (this._resultType.identicalTo(temp._resultType))
/* 107:    */       {
/* 108:119 */         int len = argsCount();
/* 109:120 */         result = len == temp.argsCount();
/* 110:121 */         for (int i = 0; (i < len) && (result); i++)
/* 111:    */         {
/* 112:122 */           Type arg1 = (Type)this._argsType.elementAt(i);
/* 113:123 */           Type arg2 = (Type)temp._argsType.elementAt(i);
/* 114:124 */           result = arg1.identicalTo(arg2);
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:128 */     return result;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int distanceTo(Type other)
/* 122:    */   {
/* 123:132 */     int result = 2147483647;
/* 124:133 */     if ((other instanceof MethodType))
/* 125:    */     {
/* 126:134 */       MethodType mtype = (MethodType)other;
/* 127:135 */       if (this._argsType != null)
/* 128:    */       {
/* 129:136 */         int len = this._argsType.size();
/* 130:137 */         if (len == mtype._argsType.size())
/* 131:    */         {
/* 132:138 */           result = 0;
/* 133:139 */           for (int i = 0; i < len; i++)
/* 134:    */           {
/* 135:140 */             Type arg1 = (Type)this._argsType.elementAt(i);
/* 136:141 */             Type arg2 = (Type)mtype._argsType.elementAt(i);
/* 137:142 */             int temp = arg1.distanceTo(arg2);
/* 138:143 */             if (temp == 2147483647)
/* 139:    */             {
/* 140:144 */               result = temp;
/* 141:145 */               break;
/* 142:    */             }
/* 143:148 */             result += arg1.distanceTo(arg2);
/* 144:    */           }
/* 145:    */         }
/* 146:    */       }
/* 147:153 */       else if (mtype._argsType == null)
/* 148:    */       {
/* 149:154 */         result = 0;
/* 150:    */       }
/* 151:    */     }
/* 152:157 */     return result;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Type resultType()
/* 156:    */   {
/* 157:161 */     return this._resultType;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Vector argsType()
/* 161:    */   {
/* 162:165 */     return this._argsType;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int argsCount()
/* 166:    */   {
/* 167:169 */     return this._argsType == null ? 0 : this._argsType.size();
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.MethodType
 * JD-Core Version:    0.7.0.1
 */