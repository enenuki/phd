/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   5:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  13:    */ import org.apache.commons.lang.StringUtils;
/*  14:    */ 
/*  15:    */ public class NamespaceCollection
/*  16:    */   extends SimpleScriptable
/*  17:    */   implements Function
/*  18:    */ {
/*  19:    */   private final HTMLDocument doc_;
/*  20:    */   private final List<Namespace> namespaces_;
/*  21:    */   
/*  22:    */   public NamespaceCollection()
/*  23:    */   {
/*  24: 46 */     this.doc_ = null;
/*  25: 47 */     this.namespaces_ = new ArrayList();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NamespaceCollection(HTMLDocument doc)
/*  29:    */   {
/*  30: 55 */     this.doc_ = doc;
/*  31: 56 */     this.namespaces_ = new ArrayList();
/*  32:    */     
/*  33: 58 */     setParentScope(doc);
/*  34: 59 */     setPrototype(getPrototype(getClass()));
/*  35:    */     
/*  36: 61 */     Map<String, String> namespacesMap = this.doc_.getHtmlPage().getNamespaces();
/*  37: 62 */     for (Map.Entry<String, String> entry : namespacesMap.entrySet())
/*  38:    */     {
/*  39: 63 */       String key = (String)entry.getKey();
/*  40: 64 */       if (key.length() != 0) {
/*  41: 65 */         this.namespaces_.add(new Namespace(this.doc_, key, (String)entry.getValue()));
/*  42:    */       }
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final Namespace jsxFunction_add(String namespace, String urn, String url)
/*  47:    */   {
/*  48: 79 */     Namespace n = new Namespace(this.doc_, namespace, urn);
/*  49: 80 */     this.namespaces_.add(n);
/*  50: 81 */     return n;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final int jsxGet_length()
/*  54:    */   {
/*  55: 89 */     return this.namespaces_.size();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final Object jsxFunction_item(Object index)
/*  59:    */   {
/*  60: 98 */     if ((index instanceof Number))
/*  61:    */     {
/*  62: 99 */       Number n = (Number)index;
/*  63:100 */       int i = n.intValue();
/*  64:101 */       return get(i, this);
/*  65:    */     }
/*  66:103 */     String key = String.valueOf(index);
/*  67:104 */     return get(key, this);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object get(int index, Scriptable start)
/*  71:    */   {
/*  72:110 */     if ((index >= 0) && (index < this.namespaces_.size())) {
/*  73:111 */       return this.namespaces_.get(index);
/*  74:    */     }
/*  75:113 */     return super.get(index, start);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object get(String name, Scriptable start)
/*  79:    */   {
/*  80:119 */     for (Namespace n : this.namespaces_) {
/*  81:120 */       if (StringUtils.equals(n.jsxGet_name(), name)) {
/*  82:121 */         return n;
/*  83:    */       }
/*  84:    */     }
/*  85:124 */     return super.get(name, start);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  89:    */   {
/*  90:129 */     if (args.length != 1) {
/*  91:130 */       return NOT_FOUND;
/*  92:    */     }
/*  93:132 */     return jsxFunction_item(args[0]);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  97:    */   {
/*  98:137 */     return null;
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.NamespaceCollection
 * JD-Core Version:    0.7.0.1
 */