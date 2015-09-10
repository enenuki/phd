/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import org.apache.bcel.Repository;
/*   9:    */ import org.apache.bcel.classfile.JavaClass;
/*  10:    */ import org.apache.bcel.verifier.statics.Pass1Verifier;
/*  11:    */ import org.apache.bcel.verifier.statics.Pass2Verifier;
/*  12:    */ import org.apache.bcel.verifier.statics.Pass3aVerifier;
/*  13:    */ import org.apache.bcel.verifier.structurals.Pass3bVerifier;
/*  14:    */ 
/*  15:    */ public class Verifier
/*  16:    */ {
/*  17:    */   private final String classname;
/*  18:    */   private Pass1Verifier p1v;
/*  19:    */   private Pass2Verifier p2v;
/*  20: 94 */   private HashMap p3avs = new HashMap();
/*  21: 96 */   private HashMap p3bvs = new HashMap();
/*  22:    */   
/*  23:    */   public VerificationResult doPass1()
/*  24:    */   {
/*  25:100 */     if (this.p1v == null) {
/*  26:101 */       this.p1v = new Pass1Verifier(this);
/*  27:    */     }
/*  28:103 */     return this.p1v.verify();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public VerificationResult doPass2()
/*  32:    */   {
/*  33:108 */     if (this.p2v == null) {
/*  34:109 */       this.p2v = new Pass2Verifier(this);
/*  35:    */     }
/*  36:111 */     return this.p2v.verify();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public VerificationResult doPass3a(int method_no)
/*  40:    */   {
/*  41:116 */     String key = Integer.toString(method_no);
/*  42:    */     
/*  43:118 */     Pass3aVerifier p3av = (Pass3aVerifier)this.p3avs.get(key);
/*  44:119 */     if (this.p3avs.get(key) == null)
/*  45:    */     {
/*  46:120 */       p3av = new Pass3aVerifier(this, method_no);
/*  47:121 */       this.p3avs.put(key, p3av);
/*  48:    */     }
/*  49:123 */     return p3av.verify();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public VerificationResult doPass3b(int method_no)
/*  53:    */   {
/*  54:128 */     String key = Integer.toString(method_no);
/*  55:    */     
/*  56:130 */     Pass3bVerifier p3bv = (Pass3bVerifier)this.p3bvs.get(key);
/*  57:131 */     if (this.p3bvs.get(key) == null)
/*  58:    */     {
/*  59:132 */       p3bv = new Pass3bVerifier(this, method_no);
/*  60:133 */       this.p3bvs.put(key, p3bv);
/*  61:    */     }
/*  62:135 */     return p3bv.verify();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private Verifier()
/*  66:    */   {
/*  67:142 */     this.classname = "";
/*  68:    */   }
/*  69:    */   
/*  70:    */   Verifier(String fully_qualified_classname)
/*  71:    */   {
/*  72:151 */     this.classname = fully_qualified_classname;
/*  73:152 */     flush();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public final String getClassName()
/*  77:    */   {
/*  78:163 */     return this.classname;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void flush()
/*  82:    */   {
/*  83:173 */     this.p1v = null;
/*  84:174 */     this.p2v = null;
/*  85:175 */     this.p3avs.clear();
/*  86:176 */     this.p3bvs.clear();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String[] getMessages()
/*  90:    */   {
/*  91:184 */     ArrayList messages = new ArrayList();
/*  92:186 */     if (this.p1v != null)
/*  93:    */     {
/*  94:187 */       String[] p1m = this.p1v.getMessages();
/*  95:188 */       for (int i = 0; i < p1m.length; i++) {
/*  96:189 */         messages.add("Pass 1: " + p1m[i]);
/*  97:    */       }
/*  98:    */     }
/*  99:192 */     if (this.p2v != null)
/* 100:    */     {
/* 101:193 */       String[] p2m = this.p2v.getMessages();
/* 102:194 */       for (int i = 0; i < p2m.length; i++) {
/* 103:195 */         messages.add("Pass 2: " + p2m[i]);
/* 104:    */       }
/* 105:    */     }
/* 106:198 */     Iterator p3as = this.p3avs.values().iterator();
/* 107:    */     String[] p3am;
/* 108:    */     int i;
/* 109:199 */     for (; p3as.hasNext(); i < p3am.length)
/* 110:    */     {
/* 111:200 */       Pass3aVerifier pv = (Pass3aVerifier)p3as.next();
/* 112:201 */       p3am = pv.getMessages();
/* 113:202 */       int meth = pv.getMethodNo();
/* 114:203 */       i = 0; continue;
/* 115:204 */       messages.add("Pass 3a, method " + meth + " ('" + Repository.lookupClass(this.classname).getMethods()[meth] + "'): " + p3am[i]);i++;
/* 116:    */     }
/* 117:207 */     Iterator p3bs = this.p3bvs.values().iterator();
/* 118:    */     String[] p3bm;
/* 119:    */     int i;
/* 120:208 */     for (; p3bs.hasNext(); i < p3bm.length)
/* 121:    */     {
/* 122:209 */       Pass3bVerifier pv = (Pass3bVerifier)p3bs.next();
/* 123:210 */       p3bm = pv.getMessages();
/* 124:211 */       int meth = pv.getMethodNo();
/* 125:212 */       i = 0; continue;
/* 126:213 */       messages.add("Pass 3b, method " + meth + " ('" + Repository.lookupClass(this.classname).getMethods()[meth] + "'): " + p3bm[i]);i++;
/* 127:    */     }
/* 128:217 */     String[] ret = new String[messages.size()];
/* 129:218 */     for (int i = 0; i < messages.size(); i++) {
/* 130:219 */       ret[i] = ((String)messages.get(i));
/* 131:    */     }
/* 132:222 */     return ret;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static void main(String[] args)
/* 136:    */   {
/* 137:236 */     System.out.println("JustIce by Enver Haase, (C) 2001. http://bcel.sourceforge.net\n");
/* 138:237 */     for (int k = 0; k < args.length; k++)
/* 139:    */     {
/* 140:239 */       if (args[k].endsWith(".class"))
/* 141:    */       {
/* 142:240 */         int dotclasspos = args[k].lastIndexOf(".class");
/* 143:241 */         if (dotclasspos != -1) {
/* 144:241 */           args[k] = args[k].substring(0, dotclasspos);
/* 145:    */         }
/* 146:    */       }
/* 147:244 */       args[k] = args[k].replace('/', '.');
/* 148:    */       
/* 149:246 */       System.out.println("Now verifiying: " + args[k] + "\n");
/* 150:    */       
/* 151:248 */       Verifier v = VerifierFactory.getVerifier(args[k]);
/* 152:    */       
/* 153:    */ 
/* 154:251 */       VerificationResult vr = v.doPass1();
/* 155:252 */       System.out.println("Pass 1:\n" + vr);
/* 156:    */       
/* 157:254 */       vr = v.doPass2();
/* 158:255 */       System.out.println("Pass 2:\n" + vr);
/* 159:257 */       if (vr == VerificationResult.VR_OK)
/* 160:    */       {
/* 161:258 */         JavaClass jc = Repository.lookupClass(args[k]);
/* 162:259 */         for (int i = 0; i < jc.getMethods().length; i++)
/* 163:    */         {
/* 164:260 */           vr = v.doPass3a(i);
/* 165:261 */           System.out.println("Pass 3a, method " + i + " ['" + jc.getMethods()[i] + "']:\n" + vr);
/* 166:    */           
/* 167:263 */           vr = v.doPass3b(i);
/* 168:264 */           System.out.println("Pass 3b, method number " + i + " ['" + jc.getMethods()[i] + "']:\n" + vr);
/* 169:    */         }
/* 170:    */       }
/* 171:268 */       System.out.println("Warnings:");
/* 172:269 */       String[] warnings = v.getMessages();
/* 173:270 */       if (warnings.length == 0) {
/* 174:270 */         System.out.println("<none>");
/* 175:    */       }
/* 176:271 */       for (int j = 0; j < warnings.length; j++) {
/* 177:272 */         System.out.println(warnings[j]);
/* 178:    */       }
/* 179:275 */       System.out.println("\n");
/* 180:    */       
/* 181:    */ 
/* 182:278 */       v.flush();
/* 183:279 */       Repository.clearCache();
/* 184:280 */       System.gc();
/* 185:    */     }
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.Verifier
 * JD-Core Version:    0.7.0.1
 */