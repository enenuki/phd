/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.ScriptableWithFallbackGetter;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   9:    */ 
/*  10:    */ public class SimpleArray
/*  11:    */   extends SimpleScriptable
/*  12:    */   implements ScriptableWithFallbackGetter
/*  13:    */ {
/*  14: 35 */   private final List<Object> elements_ = new ArrayList();
/*  15:    */   
/*  16:    */   public Object jsxFunction_item(int index)
/*  17:    */   {
/*  18: 50 */     return this.elements_.get(index);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Object getWithFallback(String name)
/*  22:    */   {
/*  23: 57 */     Object response = jsxFunction_namedItem(name);
/*  24: 58 */     if (response != null) {
/*  25: 59 */       return response;
/*  26:    */     }
/*  27: 61 */     return Context.getUndefinedValue();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final Object get(int index, Scriptable start)
/*  31:    */   {
/*  32: 71 */     SimpleArray array = (SimpleArray)start;
/*  33: 72 */     List<Object> elements = array.elements_;
/*  34: 74 */     if ((index >= 0) && (index < elements.size())) {
/*  35: 75 */       return elements.get(index);
/*  36:    */     }
/*  37: 77 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object jsxFunction_namedItem(String name)
/*  41:    */   {
/*  42: 86 */     for (Object element : this.elements_) {
/*  43: 87 */       if (name.equals(getItemName(element))) {
/*  44: 88 */         return element;
/*  45:    */       }
/*  46:    */     }
/*  47: 91 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String getItemName(Object element)
/*  51:    */   {
/*  52:101 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int jsxGet_length()
/*  56:    */   {
/*  57:109 */     return this.elements_.size();
/*  58:    */   }
/*  59:    */   
/*  60:    */   void add(Object element)
/*  61:    */   {
/*  62:117 */     this.elements_.add(element);
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.SimpleArray
 * JD-Core Version:    0.7.0.1
 */