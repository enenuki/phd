/*  1:   */ package javassist.compiler;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import javassist.compiler.ast.Declarator;
/*  5:   */ 
/*  6:   */ public final class SymbolTable
/*  7:   */   extends HashMap
/*  8:   */ {
/*  9:   */   private SymbolTable parent;
/* 10:   */   
/* 11:   */   public SymbolTable()
/* 12:   */   {
/* 13:24 */     this(null);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public SymbolTable(SymbolTable p)
/* 17:   */   {
/* 18:28 */     this.parent = p;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SymbolTable getParent()
/* 22:   */   {
/* 23:31 */     return this.parent;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Declarator lookup(String name)
/* 27:   */   {
/* 28:34 */     Declarator found = (Declarator)get(name);
/* 29:35 */     if ((found == null) && (this.parent != null)) {
/* 30:36 */       return this.parent.lookup(name);
/* 31:   */     }
/* 32:38 */     return found;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void append(String name, Declarator value)
/* 36:   */   {
/* 37:42 */     put(name, value);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.SymbolTable
 * JD-Core Version:    0.7.0.1
 */