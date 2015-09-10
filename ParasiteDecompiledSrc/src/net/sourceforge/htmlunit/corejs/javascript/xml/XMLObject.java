/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.xml;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.IdScriptableObject;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeWith;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Ref;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   8:    */ 
/*   9:    */ public abstract class XMLObject
/*  10:    */   extends IdScriptableObject
/*  11:    */ {
/*  12:    */   public XMLObject() {}
/*  13:    */   
/*  14:    */   public XMLObject(Scriptable scope, Scriptable prototype)
/*  15:    */   {
/*  16: 58 */     super(scope, prototype);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public abstract boolean ecmaHas(Context paramContext, Object paramObject);
/*  20:    */   
/*  21:    */   public abstract Object ecmaGet(Context paramContext, Object paramObject);
/*  22:    */   
/*  23:    */   public abstract void ecmaPut(Context paramContext, Object paramObject1, Object paramObject2);
/*  24:    */   
/*  25:    */   public abstract boolean ecmaDelete(Context paramContext, Object paramObject);
/*  26:    */   
/*  27:    */   public abstract Scriptable getExtraMethodSource(Context paramContext);
/*  28:    */   
/*  29:    */   public abstract Ref memberRef(Context paramContext, Object paramObject, int paramInt);
/*  30:    */   
/*  31:    */   public abstract Ref memberRef(Context paramContext, Object paramObject1, Object paramObject2, int paramInt);
/*  32:    */   
/*  33:    */   public abstract NativeWith enterWith(Scriptable paramScriptable);
/*  34:    */   
/*  35:    */   public abstract NativeWith enterDotQuery(Scriptable paramScriptable);
/*  36:    */   
/*  37:    */   public Object addValues(Context cx, boolean thisIsLeft, Object value)
/*  38:    */   {
/*  39:125 */     return Scriptable.NOT_FOUND;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getTypeOf()
/*  43:    */   {
/*  44:136 */     return avoidObjectDetection() ? "undefined" : "xml";
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.xml.XMLObject
 * JD-Core Version:    0.7.0.1
 */