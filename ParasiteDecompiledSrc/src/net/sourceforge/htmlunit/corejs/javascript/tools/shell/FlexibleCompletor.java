/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationHandler;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.List;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*   9:    */ 
/*  10:    */ class FlexibleCompletor
/*  11:    */   implements InvocationHandler
/*  12:    */ {
/*  13:    */   private Method completeMethod;
/*  14:    */   private Scriptable global;
/*  15:    */   
/*  16:    */   FlexibleCompletor(Class<?> completorClass, Scriptable global)
/*  17:    */     throws NoSuchMethodException
/*  18:    */   {
/*  19:122 */     this.global = global;
/*  20:123 */     this.completeMethod = completorClass.getMethod("complete", new Class[] { String.class, Integer.TYPE, List.class });
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  24:    */   {
/*  25:129 */     if (method.equals(this.completeMethod))
/*  26:    */     {
/*  27:130 */       int result = complete((String)args[0], ((Integer)args[1]).intValue(), (List)args[2]);
/*  28:    */       
/*  29:132 */       return Integer.valueOf(result);
/*  30:    */     }
/*  31:134 */     throw new NoSuchMethodError(method.toString());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int complete(String buffer, int cursor, List<String> candidates)
/*  35:    */   {
/*  36:144 */     int m = cursor - 1;
/*  37:145 */     while (m >= 0)
/*  38:    */     {
/*  39:146 */       char c = buffer.charAt(m);
/*  40:147 */       if ((!Character.isJavaIdentifierPart(c)) && (c != '.')) {
/*  41:    */         break;
/*  42:    */       }
/*  43:149 */       m--;
/*  44:    */     }
/*  45:151 */     String namesAndDots = buffer.substring(m + 1, cursor);
/*  46:152 */     String[] names = namesAndDots.split("\\.", -1);
/*  47:153 */     Scriptable obj = this.global;
/*  48:154 */     for (int i = 0; i < names.length - 1; i++)
/*  49:    */     {
/*  50:155 */       Object val = obj.get(names[i], this.global);
/*  51:156 */       if ((val instanceof Scriptable)) {
/*  52:157 */         obj = (Scriptable)val;
/*  53:    */       } else {
/*  54:159 */         return buffer.length();
/*  55:    */       }
/*  56:    */     }
/*  57:162 */     Object[] ids = (obj instanceof ScriptableObject) ? ((ScriptableObject)obj).getAllIds() : obj.getIds();
/*  58:    */     
/*  59:    */ 
/*  60:165 */     String lastPart = names[(names.length - 1)];
/*  61:166 */     for (int i = 0; i < ids.length; i++) {
/*  62:167 */       if ((ids[i] instanceof String))
/*  63:    */       {
/*  64:169 */         String id = (String)ids[i];
/*  65:170 */         if (id.startsWith(lastPart))
/*  66:    */         {
/*  67:171 */           if ((obj.get(id, obj) instanceof Function)) {
/*  68:172 */             id = id + "(";
/*  69:    */           }
/*  70:173 */           candidates.add(id);
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:176 */     return buffer.length() - lastPart.length();
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.FlexibleCompletor
 * JD-Core Version:    0.7.0.1
 */