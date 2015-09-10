/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.apache.bcel.classfile.Utility;
/*   5:    */ 
/*   6:    */ public abstract class Type
/*   7:    */ {
/*   8:    */   protected byte type;
/*   9:    */   protected String signature;
/*  10: 75 */   public static final BasicType VOID = new BasicType((byte)12);
/*  11: 76 */   public static final BasicType BOOLEAN = new BasicType((byte)4);
/*  12: 77 */   public static final BasicType INT = new BasicType((byte)10);
/*  13: 78 */   public static final BasicType SHORT = new BasicType((byte)9);
/*  14: 79 */   public static final BasicType BYTE = new BasicType((byte)8);
/*  15: 80 */   public static final BasicType LONG = new BasicType((byte)11);
/*  16: 81 */   public static final BasicType DOUBLE = new BasicType((byte)7);
/*  17: 82 */   public static final BasicType FLOAT = new BasicType((byte)6);
/*  18: 83 */   public static final BasicType CHAR = new BasicType((byte)5);
/*  19: 84 */   public static final ObjectType OBJECT = new ObjectType("java.lang.Object");
/*  20: 85 */   public static final ObjectType STRING = new ObjectType("java.lang.String");
/*  21: 86 */   public static final ObjectType STRINGBUFFER = new ObjectType("java.lang.StringBuffer");
/*  22: 87 */   public static final ObjectType THROWABLE = new ObjectType("java.lang.Throwable");
/*  23: 88 */   public static final Type[] NO_ARGS = new Type[0];
/*  24: 89 */   public static final ReferenceType NULL = new ReferenceType();
/*  25: 90 */   public static final Type UNKNOWN = new Type((byte)15, "<unknown object>") {};
/*  26:    */   
/*  27:    */   protected Type(byte t, String s)
/*  28:    */   {
/*  29: 94 */     this.type = t;
/*  30: 95 */     this.signature = s;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getSignature()
/*  34:    */   {
/*  35:101 */     return this.signature;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public byte getType()
/*  39:    */   {
/*  40:106 */     return this.type;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getSize()
/*  44:    */   {
/*  45:112 */     switch (this.type)
/*  46:    */     {
/*  47:    */     case 7: 
/*  48:    */     case 11: 
/*  49:114 */       return 2;
/*  50:    */     case 12: 
/*  51:115 */       return 0;
/*  52:    */     }
/*  53:116 */     return 1;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toString()
/*  57:    */   {
/*  58:124 */     return (equals(NULL)) || (this.type >= 15) ? this.signature : Utility.signatureToString(this.signature, false);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static String getMethodSignature(Type return_type, Type[] arg_types)
/*  62:    */   {
/*  63:137 */     StringBuffer buf = new StringBuffer("(");
/*  64:138 */     int length = arg_types == null ? 0 : arg_types.length;
/*  65:140 */     for (int i = 0; i < length; i++) {
/*  66:141 */       buf.append(arg_types[i].getSignature());
/*  67:    */     }
/*  68:143 */     buf.append(')');
/*  69:144 */     buf.append(return_type.getSignature());
/*  70:    */     
/*  71:146 */     return buf.toString();
/*  72:    */   }
/*  73:    */   
/*  74:149 */   private static int consumed_chars = 0;
/*  75:    */   
/*  76:    */   public static final Type getType(String signature)
/*  77:    */     throws StringIndexOutOfBoundsException
/*  78:    */   {
/*  79:159 */     byte type = Utility.typeOfSignature(signature);
/*  80:161 */     if (type <= 12)
/*  81:    */     {
/*  82:162 */       consumed_chars = 1;
/*  83:163 */       return BasicType.getType(type);
/*  84:    */     }
/*  85:164 */     if (type == 13)
/*  86:    */     {
/*  87:165 */       int dim = 0;
/*  88:    */       do
/*  89:    */       {
/*  90:167 */         dim++;
/*  91:168 */       } while (signature.charAt(dim) == '[');
/*  92:171 */       Type t = getType(signature.substring(dim));
/*  93:    */       
/*  94:173 */       consumed_chars += dim;
/*  95:    */       
/*  96:175 */       return new ArrayType(t, dim);
/*  97:    */     }
/*  98:177 */     int index = signature.indexOf(';');
/*  99:179 */     if (index < 0) {
/* 100:180 */       throw new ClassFormatError("Invalid signature: " + signature);
/* 101:    */     }
/* 102:182 */     consumed_chars = index + 1;
/* 103:    */     
/* 104:184 */     return new ObjectType(signature.substring(1, index).replace('/', '.'));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static Type getReturnType(String signature)
/* 108:    */   {
/* 109:    */     try
/* 110:    */     {
/* 111:197 */       int index = signature.lastIndexOf(')') + 1;
/* 112:198 */       return getType(signature.substring(index));
/* 113:    */     }
/* 114:    */     catch (StringIndexOutOfBoundsException e)
/* 115:    */     {
/* 116:200 */       throw new ClassFormatError("Invalid method signature: " + signature);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static Type[] getArgumentTypes(String signature)
/* 121:    */   {
/* 122:210 */     ArrayList vec = new ArrayList();
/* 123:    */     try
/* 124:    */     {
/* 125:215 */       if (signature.charAt(0) != '(') {
/* 126:216 */         throw new ClassFormatError("Invalid method signature: " + signature);
/* 127:    */       }
/* 128:218 */       int index = 1;
/* 129:220 */       while (signature.charAt(index) != ')')
/* 130:    */       {
/* 131:221 */         vec.add(getType(signature.substring(index)));
/* 132:222 */         index += consumed_chars;
/* 133:    */       }
/* 134:    */     }
/* 135:    */     catch (StringIndexOutOfBoundsException e)
/* 136:    */     {
/* 137:225 */       throw new ClassFormatError("Invalid method signature: " + signature);
/* 138:    */     }
/* 139:228 */     Type[] types = new Type[vec.size()];
/* 140:229 */     vec.toArray(types);
/* 141:230 */     return types;
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.Type
 * JD-Core Version:    0.7.0.1
 */