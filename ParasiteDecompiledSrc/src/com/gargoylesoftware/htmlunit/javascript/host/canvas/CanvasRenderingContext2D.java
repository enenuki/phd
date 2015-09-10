/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.canvas;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   7:    */ 
/*   8:    */ public class CanvasRenderingContext2D
/*   9:    */   extends SimpleScriptable
/*  10:    */ {
/*  11:    */   public Object jsxGet_fillStyle()
/*  12:    */   {
/*  13: 36 */     return null;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void jsxFunction_translate(Object x, Object y) {}
/*  17:    */   
/*  18:    */   public void jsxFunction_scale(Object x, Object y) {}
/*  19:    */   
/*  20:    */   public void jsxSet_fillStyle(Object fillStyle) {}
/*  21:    */   
/*  22:    */   public Object jsxGet_strokeStyle()
/*  23:    */   {
/*  24: 70 */     return null;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void jsxSet_strokeStyle(Object strokeStyle) {}
/*  28:    */   
/*  29:    */   public double jsxGet_lineWidth()
/*  30:    */   {
/*  31: 86 */     return 0.0D;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void jsxSet_lineWidth(Object lineWidth) {}
/*  35:    */   
/*  36:    */   public double jsxGet_globalAlpha()
/*  37:    */   {
/*  38:102 */     return 0.0D;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void jsxSet_globalAlpha(Object globalAlpha) {}
/*  42:    */   
/*  43:    */   public void jsxFunction_clearRect(double x, double y, double w, double h) {}
/*  44:    */   
/*  45:    */   public void jsxFunction_fillRect(double x, double y, double w, double h) {}
/*  46:    */   
/*  47:    */   public void jsxFunction_strokeRect(double x, double y, double w, double h) {}
/*  48:    */   
/*  49:    */   public void jsxFunction_beginPath() {}
/*  50:    */   
/*  51:    */   public void jsxFunction_closePath() {}
/*  52:    */   
/*  53:    */   public void jsxFunction_moveTo(double x, double y) {}
/*  54:    */   
/*  55:    */   public void jsxFunction_lineTo(double x, double y) {}
/*  56:    */   
/*  57:    */   public void jsxFunction_save() {}
/*  58:    */   
/*  59:    */   public void jsxFunction_restore() {}
/*  60:    */   
/*  61:    */   public void jsxFunction_createLinearGradient(double x0, double y0, double r0, double x1, Object y1, Object r1) {}
/*  62:    */   
/*  63:    */   public void jsxFunction_arc(double x, double y, double radius, double startAngle, double endAngle, boolean anticlockwise) {}
/*  64:    */   
/*  65:    */   public void jsxFunction_arcTo(double x1, double y1, double x2, double y2, double radius) {}
/*  66:    */   
/*  67:    */   public void jsxFunction_bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {}
/*  68:    */   
/*  69:    */   public void jsxFunction_fill() {}
/*  70:    */   
/*  71:    */   public void jsxFunction_stroke() {}
/*  72:    */   
/*  73:    */   public void jsxFunction_clip() {}
/*  74:    */   
/*  75:    */   public static void jsxFunction_drawImage(Context context, Scriptable thisObj, Object[] args, Function function) {}
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.canvas.CanvasRenderingContext2D
 * JD-Core Version:    0.7.0.1
 */