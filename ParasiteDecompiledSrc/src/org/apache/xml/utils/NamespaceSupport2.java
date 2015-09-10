/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.EmptyStackException;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import org.xml.sax.helpers.NamespaceSupport;
/*   6:    */ 
/*   7:    */ public class NamespaceSupport2
/*   8:    */   extends NamespaceSupport
/*   9:    */ {
/*  10:    */   private Context2 currentContext;
/*  11:    */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/*  12:    */   
/*  13:    */   public NamespaceSupport2()
/*  14:    */   {
/*  15: 79 */     reset();
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void reset()
/*  19:    */   {
/*  20: 99 */     this.currentContext = new Context2(null);
/*  21:100 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void pushContext()
/*  25:    */   {
/*  26:125 */     Context2 parentContext = this.currentContext;
/*  27:126 */     this.currentContext = parentContext.getChild();
/*  28:127 */     if (this.currentContext == null) {
/*  29:128 */       this.currentContext = new Context2(parentContext);
/*  30:    */     } else {
/*  31:133 */       this.currentContext.setParent(parentContext);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void popContext()
/*  36:    */   {
/*  37:153 */     Context2 parentContext = this.currentContext.getParent();
/*  38:154 */     if (parentContext == null) {
/*  39:155 */       throw new EmptyStackException();
/*  40:    */     }
/*  41:157 */     this.currentContext = parentContext;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean declarePrefix(String prefix, String uri)
/*  45:    */   {
/*  46:197 */     if ((prefix.equals("xml")) || (prefix.equals("xmlns"))) {
/*  47:198 */       return false;
/*  48:    */     }
/*  49:200 */     this.currentContext.declarePrefix(prefix, uri);
/*  50:201 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String[] processName(String qName, String[] parts, boolean isAttribute)
/*  54:    */   {
/*  55:247 */     String[] name = this.currentContext.processName(qName, isAttribute);
/*  56:248 */     if (name == null) {
/*  57:249 */       return null;
/*  58:    */     }
/*  59:253 */     System.arraycopy(name, 0, parts, 0, 3);
/*  60:254 */     return parts;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getURI(String prefix)
/*  64:    */   {
/*  65:272 */     return this.currentContext.getURI(prefix);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Enumeration getPrefixes()
/*  69:    */   {
/*  70:291 */     return this.currentContext.getPrefixes();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getPrefix(String uri)
/*  74:    */   {
/*  75:315 */     return this.currentContext.getPrefix(uri);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Enumeration getPrefixes(String uri)
/*  79:    */   {
/*  80:351 */     return new PrefixForUriEnumerator(this, uri, getPrefixes());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Enumeration getDeclaredPrefixes()
/*  84:    */   {
/*  85:369 */     return this.currentContext.getDeclaredPrefixes();
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.NamespaceSupport2
 * JD-Core Version:    0.7.0.1
 */