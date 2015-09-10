/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ 
/*   6:    */ public class REDebugCompiler
/*   7:    */   extends RECompiler
/*   8:    */ {
/*   9: 75 */   static Hashtable hashOpcode = new Hashtable();
/*  10:    */   
/*  11:    */   static
/*  12:    */   {
/*  13: 78 */     hashOpcode.put(new Integer(56), "OP_RELUCTANTSTAR");
/*  14: 79 */     hashOpcode.put(new Integer(61), "OP_RELUCTANTPLUS");
/*  15: 80 */     hashOpcode.put(new Integer(47), "OP_RELUCTANTMAYBE");
/*  16: 81 */     hashOpcode.put(new Integer(69), "OP_END");
/*  17: 82 */     hashOpcode.put(new Integer(94), "OP_BOL");
/*  18: 83 */     hashOpcode.put(new Integer(36), "OP_EOL");
/*  19: 84 */     hashOpcode.put(new Integer(46), "OP_ANY");
/*  20: 85 */     hashOpcode.put(new Integer(91), "OP_ANYOF");
/*  21: 86 */     hashOpcode.put(new Integer(124), "OP_BRANCH");
/*  22: 87 */     hashOpcode.put(new Integer(65), "OP_ATOM");
/*  23: 88 */     hashOpcode.put(new Integer(42), "OP_STAR");
/*  24: 89 */     hashOpcode.put(new Integer(43), "OP_PLUS");
/*  25: 90 */     hashOpcode.put(new Integer(63), "OP_MAYBE");
/*  26: 91 */     hashOpcode.put(new Integer(78), "OP_NOTHING");
/*  27: 92 */     hashOpcode.put(new Integer(71), "OP_GOTO");
/*  28: 93 */     hashOpcode.put(new Integer(92), "OP_ESCAPE");
/*  29: 94 */     hashOpcode.put(new Integer(40), "OP_OPEN");
/*  30: 95 */     hashOpcode.put(new Integer(41), "OP_CLOSE");
/*  31: 96 */     hashOpcode.put(new Integer(35), "OP_BACKREF");
/*  32: 97 */     hashOpcode.put(new Integer(80), "OP_POSIXCLASS");
/*  33:    */   }
/*  34:    */   
/*  35:    */   String opcodeToString(char paramChar)
/*  36:    */   {
/*  37:108 */     String str = (String)hashOpcode.get(new Integer(paramChar));
/*  38:111 */     if (str == null) {
/*  39:113 */       str = "OP_????";
/*  40:    */     }
/*  41:115 */     return str;
/*  42:    */   }
/*  43:    */   
/*  44:    */   String charToString(char paramChar)
/*  45:    */   {
/*  46:126 */     if ((paramChar < ' ') || (paramChar > '')) {
/*  47:128 */       return "\\" + paramChar;
/*  48:    */     }
/*  49:132 */     return String.valueOf(paramChar);
/*  50:    */   }
/*  51:    */   
/*  52:    */   String nodeToString(int paramInt)
/*  53:    */   {
/*  54:143 */     char c = this.instruction[paramInt];
/*  55:144 */     int i = this.instruction[(paramInt + 1)];
/*  56:    */     
/*  57:    */ 
/*  58:147 */     return opcodeToString(c) + ", opdata = " + i;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void dumpProgram(PrintWriter paramPrintWriter)
/*  62:    */   {
/*  63:157 */     for (int i = 0; i < this.lenInstruction;)
/*  64:    */     {
/*  65:160 */       int j = this.instruction[i];
/*  66:161 */       int k = this.instruction[(i + 1)];
/*  67:162 */       int m = (short)this.instruction[(i + 2)];
/*  68:    */       
/*  69:    */ 
/*  70:165 */       paramPrintWriter.print(i + ". " + nodeToString(i) + ", next = ");
/*  71:168 */       if (m == 0) {
/*  72:170 */         paramPrintWriter.print("none");
/*  73:    */       } else {
/*  74:174 */         paramPrintWriter.print(i + m);
/*  75:    */       }
/*  76:178 */       i += 3;
/*  77:    */       int n;
/*  78:181 */       if (j == 91)
/*  79:    */       {
/*  80:184 */         paramPrintWriter.print(", [");
/*  81:    */         
/*  82:    */ 
/*  83:187 */         n = k;
/*  84:188 */         for (int i1 = 0; i1 < n; i1++)
/*  85:    */         {
/*  86:191 */           char c1 = this.instruction[(i++)];
/*  87:192 */           char c2 = this.instruction[(i++)];
/*  88:195 */           if (c1 == c2) {
/*  89:197 */             paramPrintWriter.print(charToString(c1));
/*  90:    */           } else {
/*  91:201 */             paramPrintWriter.print(charToString(c1) + "-" + charToString(c2));
/*  92:    */           }
/*  93:    */         }
/*  94:206 */         paramPrintWriter.print("]");
/*  95:    */       }
/*  96:210 */       if (j == 65)
/*  97:    */       {
/*  98:213 */         paramPrintWriter.print(", \"");
/*  99:216 */         for (n = k; n-- != 0;) {
/* 100:218 */           paramPrintWriter.print(charToString(this.instruction[(i++)]));
/* 101:    */         }
/* 102:222 */         paramPrintWriter.print("\"");
/* 103:    */       }
/* 104:226 */       paramPrintWriter.println("");
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.REDebugCompiler
 * JD-Core Version:    0.7.0.1
 */