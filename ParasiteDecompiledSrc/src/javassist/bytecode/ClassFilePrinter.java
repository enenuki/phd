/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.util.List;
/*   5:    */ import javassist.Modifier;
/*   6:    */ 
/*   7:    */ public class ClassFilePrinter
/*   8:    */ {
/*   9:    */   public static void print(ClassFile cf)
/*  10:    */   {
/*  11: 32 */     print(cf, new PrintWriter(System.out, true));
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static void print(ClassFile cf, PrintWriter out)
/*  15:    */   {
/*  16: 45 */     int mod = AccessFlag.toModifier(cf.getAccessFlags() & 0xFFFFFFDF);
/*  17:    */     
/*  18:    */ 
/*  19: 48 */     out.println("major: " + cf.major + ", minor: " + cf.minor + " modifiers: " + Integer.toHexString(cf.getAccessFlags()));
/*  20:    */     
/*  21: 50 */     out.println(Modifier.toString(mod) + " class " + cf.getName() + " extends " + cf.getSuperclass());
/*  22:    */     
/*  23:    */ 
/*  24: 53 */     String[] infs = cf.getInterfaces();
/*  25: 54 */     if ((infs != null) && (infs.length > 0))
/*  26:    */     {
/*  27: 55 */       out.print("    implements ");
/*  28: 56 */       out.print(infs[0]);
/*  29: 57 */       for (int i = 1; i < infs.length; i++) {
/*  30: 58 */         out.print(", " + infs[i]);
/*  31:    */       }
/*  32: 60 */       out.println();
/*  33:    */     }
/*  34: 63 */     out.println();
/*  35: 64 */     List list = cf.getFields();
/*  36: 65 */     int n = list.size();
/*  37: 66 */     for (int i = 0; i < n; i++)
/*  38:    */     {
/*  39: 67 */       FieldInfo finfo = (FieldInfo)list.get(i);
/*  40: 68 */       int acc = finfo.getAccessFlags();
/*  41: 69 */       out.println(Modifier.toString(AccessFlag.toModifier(acc)) + " " + finfo.getName() + "\t" + finfo.getDescriptor());
/*  42:    */       
/*  43:    */ 
/*  44: 72 */       printAttributes(finfo.getAttributes(), out, 'f');
/*  45:    */     }
/*  46: 75 */     out.println();
/*  47: 76 */     list = cf.getMethods();
/*  48: 77 */     n = list.size();
/*  49: 78 */     for (int i = 0; i < n; i++)
/*  50:    */     {
/*  51: 79 */       MethodInfo minfo = (MethodInfo)list.get(i);
/*  52: 80 */       int acc = minfo.getAccessFlags();
/*  53: 81 */       out.println(Modifier.toString(AccessFlag.toModifier(acc)) + " " + minfo.getName() + "\t" + minfo.getDescriptor());
/*  54:    */       
/*  55:    */ 
/*  56: 84 */       printAttributes(minfo.getAttributes(), out, 'm');
/*  57: 85 */       out.println();
/*  58:    */     }
/*  59: 88 */     out.println();
/*  60: 89 */     printAttributes(cf.getAttributes(), out, 'c');
/*  61:    */   }
/*  62:    */   
/*  63:    */   static void printAttributes(List list, PrintWriter out, char kind)
/*  64:    */   {
/*  65: 93 */     if (list == null) {
/*  66: 94 */       return;
/*  67:    */     }
/*  68: 96 */     int n = list.size();
/*  69: 97 */     for (int i = 0; i < n; i++)
/*  70:    */     {
/*  71: 98 */       AttributeInfo ai = (AttributeInfo)list.get(i);
/*  72: 99 */       if ((ai instanceof CodeAttribute))
/*  73:    */       {
/*  74:100 */         CodeAttribute ca = (CodeAttribute)ai;
/*  75:101 */         out.println("attribute: " + ai.getName() + ": " + ai.getClass().getName());
/*  76:    */         
/*  77:103 */         out.println("max stack " + ca.getMaxStack() + ", max locals " + ca.getMaxLocals() + ", " + ca.getExceptionTable().size() + " catch blocks");
/*  78:    */         
/*  79:    */ 
/*  80:    */ 
/*  81:107 */         out.println("<code attribute begin>");
/*  82:108 */         printAttributes(ca.getAttributes(), out, kind);
/*  83:109 */         out.println("<code attribute end>");
/*  84:    */       }
/*  85:111 */       else if ((ai instanceof StackMapTable))
/*  86:    */       {
/*  87:112 */         out.println("<stack map table begin>");
/*  88:113 */         StackMapTable.Printer.print((StackMapTable)ai, out);
/*  89:114 */         out.println("<stack map table end>");
/*  90:    */       }
/*  91:116 */       else if ((ai instanceof StackMap))
/*  92:    */       {
/*  93:117 */         out.println("<stack map begin>");
/*  94:118 */         ((StackMap)ai).print(out);
/*  95:119 */         out.println("<stack map end>");
/*  96:    */       }
/*  97:121 */       else if ((ai instanceof SignatureAttribute))
/*  98:    */       {
/*  99:122 */         SignatureAttribute sa = (SignatureAttribute)ai;
/* 100:123 */         String sig = sa.getSignature();
/* 101:124 */         out.println("signature: " + sig);
/* 102:    */         try
/* 103:    */         {
/* 104:    */           String s;
/* 105:    */           String s;
/* 106:127 */           if (kind == 'c')
/* 107:    */           {
/* 108:128 */             s = SignatureAttribute.toClassSignature(sig).toString();
/* 109:    */           }
/* 110:    */           else
/* 111:    */           {
/* 112:    */             String s;
/* 113:129 */             if (kind == 'm') {
/* 114:130 */               s = SignatureAttribute.toMethodSignature(sig).toString();
/* 115:    */             } else {
/* 116:132 */               s = SignatureAttribute.toFieldSignature(sig).toString();
/* 117:    */             }
/* 118:    */           }
/* 119:134 */           out.println("           " + s);
/* 120:    */         }
/* 121:    */         catch (BadBytecode e)
/* 122:    */         {
/* 123:137 */           out.println("           syntax error");
/* 124:    */         }
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:141 */         out.println("attribute: " + ai.getName() + " (" + ai.get().length + " byte): " + ai.getClass().getName());
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ClassFilePrinter
 * JD-Core Version:    0.7.0.1
 */