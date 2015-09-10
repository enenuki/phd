/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.util.BitSet;
/*   4:    */ import org.apache.xml.res.XMLMessages;
/*   5:    */ 
/*   6:    */ public class CoroutineManager
/*   7:    */ {
/*   8:113 */   BitSet m_activeIDs = new BitSet();
/*   9:    */   static final int m_unreasonableId = 1024;
/*  10:149 */   Object m_yield = null;
/*  11:    */   static final int NOBODY = -1;
/*  12:    */   static final int ANYBODY = -1;
/*  13:160 */   int m_nextCoroutine = -1;
/*  14:    */   
/*  15:    */   public synchronized int co_joinCoroutineSet(int coroutineID)
/*  16:    */   {
/*  17:186 */     if (coroutineID >= 0)
/*  18:    */     {
/*  19:188 */       if ((coroutineID >= 1024) || (this.m_activeIDs.get(coroutineID))) {
/*  20:189 */         return -1;
/*  21:    */       }
/*  22:    */     }
/*  23:    */     else
/*  24:    */     {
/*  25:195 */       coroutineID = 0;
/*  26:196 */       while (coroutineID < 1024)
/*  27:    */       {
/*  28:198 */         if (!this.m_activeIDs.get(coroutineID)) {
/*  29:    */           break;
/*  30:    */         }
/*  31:199 */         coroutineID++;
/*  32:    */       }
/*  33:203 */       if (coroutineID >= 1024) {
/*  34:204 */         return -1;
/*  35:    */       }
/*  36:    */     }
/*  37:207 */     this.m_activeIDs.set(coroutineID);
/*  38:208 */     return coroutineID;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public synchronized Object co_entry_pause(int thisCoroutine)
/*  42:    */     throws NoSuchMethodException
/*  43:    */   {
/*  44:228 */     if (!this.m_activeIDs.get(thisCoroutine)) {
/*  45:229 */       throw new NoSuchMethodException();
/*  46:    */     }
/*  47:231 */     while (this.m_nextCoroutine != thisCoroutine) {
/*  48:    */       try
/*  49:    */       {
/*  50:235 */         wait();
/*  51:    */       }
/*  52:    */       catch (InterruptedException e) {}
/*  53:    */     }
/*  54:244 */     return this.m_yield;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public synchronized Object co_resume(Object arg_object, int thisCoroutine, int toCoroutine)
/*  58:    */     throws NoSuchMethodException
/*  59:    */   {
/*  60:263 */     if (!this.m_activeIDs.get(toCoroutine)) {
/*  61:264 */       throw new NoSuchMethodException(XMLMessages.createXMLMessage("ER_COROUTINE_NOT_AVAIL", new Object[] { Integer.toString(toCoroutine) }));
/*  62:    */     }
/*  63:268 */     this.m_yield = arg_object;
/*  64:269 */     this.m_nextCoroutine = toCoroutine;
/*  65:    */     
/*  66:271 */     notify();
/*  67:272 */     while ((this.m_nextCoroutine != thisCoroutine) || (this.m_nextCoroutine == -1) || (this.m_nextCoroutine == -1)) {
/*  68:    */       try
/*  69:    */       {
/*  70:277 */         wait();
/*  71:    */       }
/*  72:    */       catch (InterruptedException e) {}
/*  73:    */     }
/*  74:286 */     if (this.m_nextCoroutine == -1)
/*  75:    */     {
/*  76:289 */       co_exit(thisCoroutine);
/*  77:    */       
/*  78:    */ 
/*  79:292 */       throw new NoSuchMethodException(XMLMessages.createXMLMessage("ER_COROUTINE_CO_EXIT", null));
/*  80:    */     }
/*  81:295 */     return this.m_yield;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public synchronized void co_exit(int thisCoroutine)
/*  85:    */   {
/*  86:313 */     this.m_activeIDs.clear(thisCoroutine);
/*  87:314 */     this.m_nextCoroutine = -1;
/*  88:315 */     notify();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public synchronized void co_exit_to(Object arg_object, int thisCoroutine, int toCoroutine)
/*  92:    */     throws NoSuchMethodException
/*  93:    */   {
/*  94:332 */     if (!this.m_activeIDs.get(toCoroutine)) {
/*  95:333 */       throw new NoSuchMethodException(XMLMessages.createXMLMessage("ER_COROUTINE_NOT_AVAIL", new Object[] { Integer.toString(toCoroutine) }));
/*  96:    */     }
/*  97:337 */     this.m_yield = arg_object;
/*  98:338 */     this.m_nextCoroutine = toCoroutine;
/*  99:    */     
/* 100:340 */     this.m_activeIDs.clear(thisCoroutine);
/* 101:    */     
/* 102:342 */     notify();
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.CoroutineManager
 * JD-Core Version:    0.7.0.1
 */