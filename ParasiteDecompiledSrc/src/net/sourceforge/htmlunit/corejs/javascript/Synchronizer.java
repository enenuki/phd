/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public class Synchronizer
/*  4:   */   extends Delegator
/*  5:   */ {
/*  6:   */   private Object syncObject;
/*  7:   */   
/*  8:   */   public Synchronizer(Scriptable obj)
/*  9:   */   {
/* 10:70 */     super(obj);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Synchronizer(Scriptable obj, Object syncObject)
/* 14:   */   {
/* 15:81 */     super(obj);
/* 16:82 */     this.syncObject = syncObject;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 20:   */   {
/* 21:92 */     Object sync = this.syncObject != null ? this.syncObject : thisObj;
/* 22:93 */     synchronized ((sync instanceof Wrapper) ? ((Wrapper)sync).unwrap() : sync)
/* 23:   */     {
/* 24:94 */       return ((Function)this.obj).call(cx, scope, thisObj, args);
/* 25:   */     }
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Synchronizer
 * JD-Core Version:    0.7.0.1
 */