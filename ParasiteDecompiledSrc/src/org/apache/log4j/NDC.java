/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Stack;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.log4j.helpers.LogLog;
/*   8:    */ 
/*   9:    */ public class NDC
/*  10:    */ {
/*  11:114 */   static Hashtable ht = new Hashtable();
/*  12:116 */   static int pushCounter = 0;
/*  13:    */   static final int REAP_THRESHOLD = 5;
/*  14:    */   
/*  15:    */   private static Stack getCurrentStack()
/*  16:    */   {
/*  17:134 */     if (ht != null) {
/*  18:135 */       return (Stack)ht.get(Thread.currentThread());
/*  19:    */     }
/*  20:137 */     return null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static void clear()
/*  24:    */   {
/*  25:153 */     Stack stack = getCurrentStack();
/*  26:154 */     if (stack != null) {
/*  27:155 */       stack.setSize(0);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Stack cloneStack()
/*  32:    */   {
/*  33:176 */     Stack stack = getCurrentStack();
/*  34:177 */     if (stack == null) {
/*  35:178 */       return null;
/*  36:    */     }
/*  37:180 */     return (Stack)stack.clone();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static void inherit(Stack stack)
/*  41:    */   {
/*  42:208 */     if (stack != null) {
/*  43:209 */       ht.put(Thread.currentThread(), stack);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String get()
/*  48:    */   {
/*  49:220 */     Stack s = getCurrentStack();
/*  50:221 */     if ((s != null) && (!s.isEmpty())) {
/*  51:222 */       return ((DiagnosticContext)s.peek()).fullMessage;
/*  52:    */     }
/*  53:224 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static int getDepth()
/*  57:    */   {
/*  58:236 */     Stack stack = getCurrentStack();
/*  59:237 */     if (stack == null) {
/*  60:238 */       return 0;
/*  61:    */     }
/*  62:240 */     return stack.size();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private static void lazyRemove()
/*  66:    */   {
/*  67:246 */     if (ht == null) {
/*  68:    */       return;
/*  69:    */     }
/*  70:    */     Vector v;
/*  71:253 */     synchronized (ht)
/*  72:    */     {
/*  73:255 */       if (++pushCounter <= 5) {
/*  74:256 */         return;
/*  75:    */       }
/*  76:258 */       pushCounter = 0;
/*  77:    */       
/*  78:    */ 
/*  79:261 */       int misses = 0;
/*  80:262 */       v = new Vector();
/*  81:263 */       Enumeration enumeration = ht.keys();
/*  82:268 */       while ((enumeration.hasMoreElements()) && (misses <= 4))
/*  83:    */       {
/*  84:269 */         Thread t = (Thread)enumeration.nextElement();
/*  85:270 */         if (t.isAlive())
/*  86:    */         {
/*  87:271 */           misses++;
/*  88:    */         }
/*  89:    */         else
/*  90:    */         {
/*  91:273 */           misses = 0;
/*  92:274 */           v.addElement(t);
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:279 */     int size = v.size();
/*  97:280 */     for (int i = 0; i < size; i++)
/*  98:    */     {
/*  99:281 */       Thread t = (Thread)v.elementAt(i);
/* 100:282 */       LogLog.debug("Lazy NDC removal for thread [" + t.getName() + "] (" + ht.size() + ").");
/* 101:    */       
/* 102:284 */       ht.remove(t);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static String pop()
/* 107:    */   {
/* 108:301 */     Stack stack = getCurrentStack();
/* 109:302 */     if ((stack != null) && (!stack.isEmpty())) {
/* 110:303 */       return ((DiagnosticContext)stack.pop()).message;
/* 111:    */     }
/* 112:305 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static String peek()
/* 116:    */   {
/* 117:321 */     Stack stack = getCurrentStack();
/* 118:322 */     if ((stack != null) && (!stack.isEmpty())) {
/* 119:323 */       return ((DiagnosticContext)stack.peek()).message;
/* 120:    */     }
/* 121:325 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void push(String message)
/* 125:    */   {
/* 126:338 */     Stack stack = getCurrentStack();
/* 127:340 */     if (stack == null)
/* 128:    */     {
/* 129:341 */       DiagnosticContext dc = new DiagnosticContext(message, null);
/* 130:342 */       stack = new Stack();
/* 131:343 */       Thread key = Thread.currentThread();
/* 132:344 */       ht.put(key, stack);
/* 133:345 */       stack.push(dc);
/* 134:    */     }
/* 135:346 */     else if (stack.isEmpty())
/* 136:    */     {
/* 137:347 */       DiagnosticContext dc = new DiagnosticContext(message, null);
/* 138:348 */       stack.push(dc);
/* 139:    */     }
/* 140:    */     else
/* 141:    */     {
/* 142:350 */       DiagnosticContext parent = (DiagnosticContext)stack.peek();
/* 143:351 */       stack.push(new DiagnosticContext(message, parent));
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static void remove()
/* 148:    */   {
/* 149:377 */     if (ht != null)
/* 150:    */     {
/* 151:378 */       ht.remove(Thread.currentThread());
/* 152:    */       
/* 153:    */ 
/* 154:381 */       lazyRemove();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static void setMaxDepth(int maxDepth)
/* 159:    */   {
/* 160:415 */     Stack stack = getCurrentStack();
/* 161:416 */     if ((stack != null) && (maxDepth < stack.size())) {
/* 162:417 */       stack.setSize(maxDepth);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   private static class DiagnosticContext
/* 167:    */   {
/* 168:    */     String fullMessage;
/* 169:    */     String message;
/* 170:    */     
/* 171:    */     DiagnosticContext(String message, DiagnosticContext parent)
/* 172:    */     {
/* 173:427 */       this.message = message;
/* 174:428 */       if (parent != null) {
/* 175:429 */         this.fullMessage = (parent.fullMessage + ' ' + message);
/* 176:    */       } else {
/* 177:431 */         this.fullMessage = message;
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.NDC
 * JD-Core Version:    0.7.0.1
 */