/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public class NativeJavaConstructor
/*  4:   */   extends BaseFunction
/*  5:   */ {
/*  6:   */   static final long serialVersionUID = -8149253217482668463L;
/*  7:   */   MemberBox ctor;
/*  8:   */   
/*  9:   */   public NativeJavaConstructor(MemberBox ctor)
/* 10:   */   {
/* 11:65 */     this.ctor = ctor;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 15:   */   {
/* 16:72 */     return NativeJavaClass.constructSpecific(cx, scope, args, this.ctor);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getFunctionName()
/* 20:   */   {
/* 21:78 */     String sig = JavaMembers.liveConnectSignature(this.ctor.argTypes);
/* 22:79 */     return "<init>".concat(sig);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:85 */     return "[JavaConstructor " + this.ctor.getName() + "]";
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaConstructor
 * JD-Core Version:    0.7.0.1
 */