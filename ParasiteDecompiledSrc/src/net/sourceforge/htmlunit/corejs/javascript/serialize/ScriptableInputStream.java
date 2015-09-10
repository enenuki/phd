/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.serialize;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.ObjectInputStream;
/*   6:    */ import java.io.ObjectStreamClass;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.UniqueTag;
/*  11:    */ 
/*  12:    */ public class ScriptableInputStream
/*  13:    */   extends ObjectInputStream
/*  14:    */ {
/*  15:    */   private Scriptable scope;
/*  16:    */   private ClassLoader classLoader;
/*  17:    */   
/*  18:    */   public ScriptableInputStream(InputStream in, Scriptable scope)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 69 */     super(in);
/*  22: 70 */     this.scope = scope;
/*  23: 71 */     enableResolveObject(true);
/*  24: 72 */     Context cx = Context.getCurrentContext();
/*  25: 73 */     if (cx != null) {
/*  26: 74 */       this.classLoader = cx.getApplicationClassLoader();
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected Class<?> resolveClass(ObjectStreamClass desc)
/*  31:    */     throws IOException, ClassNotFoundException
/*  32:    */   {
/*  33: 82 */     String name = desc.getName();
/*  34: 83 */     if (this.classLoader != null) {
/*  35:    */       try
/*  36:    */       {
/*  37: 85 */         return this.classLoader.loadClass(name);
/*  38:    */       }
/*  39:    */       catch (ClassNotFoundException ex) {}
/*  40:    */     }
/*  41: 90 */     return super.resolveClass(desc);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected Object resolveObject(Object obj)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 97 */     if ((obj instanceof ScriptableOutputStream.PendingLookup))
/*  48:    */     {
/*  49: 98 */       String name = ((ScriptableOutputStream.PendingLookup)obj).getName();
/*  50: 99 */       obj = ScriptableOutputStream.lookupQualifiedName(this.scope, name);
/*  51:100 */       if (obj == Scriptable.NOT_FOUND) {
/*  52:101 */         throw new IOException("Object " + name + " not found upon " + "deserialization.");
/*  53:    */       }
/*  54:    */     }
/*  55:104 */     else if ((obj instanceof UniqueTag))
/*  56:    */     {
/*  57:105 */       obj = ((UniqueTag)obj).readResolve();
/*  58:    */     }
/*  59:106 */     else if ((obj instanceof Undefined))
/*  60:    */     {
/*  61:107 */       obj = ((Undefined)obj).readResolve();
/*  62:    */     }
/*  63:109 */     return obj;
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.serialize.ScriptableInputStream
 * JD-Core Version:    0.7.0.1
 */