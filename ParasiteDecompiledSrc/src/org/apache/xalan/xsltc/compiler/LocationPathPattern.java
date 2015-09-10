/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   4:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   5:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*   6:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*   7:    */ 
/*   8:    */ public abstract class LocationPathPattern
/*   9:    */   extends Pattern
/*  10:    */ {
/*  11:    */   private Template _template;
/*  12:    */   private int _importPrecedence;
/*  13: 38 */   private double _priority = (0.0D / 0.0D);
/*  14: 39 */   private int _position = 0;
/*  15:    */   
/*  16:    */   public Type typeCheck(SymbolTable stable)
/*  17:    */     throws TypeCheckError
/*  18:    */   {
/*  19: 42 */     return Type.Void;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {}
/*  23:    */   
/*  24:    */   public void setTemplate(Template template)
/*  25:    */   {
/*  26: 50 */     this._template = template;
/*  27: 51 */     this._priority = template.getPriority();
/*  28: 52 */     this._importPrecedence = template.getImportPrecedence();
/*  29: 53 */     this._position = template.getPosition();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Template getTemplate()
/*  33:    */   {
/*  34: 57 */     return this._template;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final double getPriority()
/*  38:    */   {
/*  39: 61 */     return Double.isNaN(this._priority) ? getDefaultPriority() : this._priority;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public double getDefaultPriority()
/*  43:    */   {
/*  44: 65 */     return 0.5D;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean noSmallerThan(LocationPathPattern other)
/*  48:    */   {
/*  49: 77 */     if (this._importPrecedence > other._importPrecedence) {
/*  50: 78 */       return true;
/*  51:    */     }
/*  52: 80 */     if (this._importPrecedence == other._importPrecedence)
/*  53:    */     {
/*  54: 81 */       if (this._priority > other._priority) {
/*  55: 82 */         return true;
/*  56:    */       }
/*  57: 84 */       if ((this._priority == other._priority) && 
/*  58: 85 */         (this._position > other._position)) {
/*  59: 86 */         return true;
/*  60:    */       }
/*  61:    */     }
/*  62: 90 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public abstract StepPattern getKernelPattern();
/*  66:    */   
/*  67:    */   public abstract void reduceKernelPattern();
/*  68:    */   
/*  69:    */   public abstract boolean isWildcard();
/*  70:    */   
/*  71:    */   public int getAxis()
/*  72:    */   {
/*  73:100 */     StepPattern sp = getKernelPattern();
/*  74:101 */     return sp != null ? sp.getAxis() : 3;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:105 */     return "root()";
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LocationPathPattern
 * JD-Core Version:    0.7.0.1
 */