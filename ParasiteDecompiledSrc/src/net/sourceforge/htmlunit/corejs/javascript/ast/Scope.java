/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.LinkedHashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*  10:    */ 
/*  11:    */ public class Scope
/*  12:    */   extends Jump
/*  13:    */ {
/*  14:    */   protected Map<String, Symbol> symbolTable;
/*  15:    */   protected Scope parentScope;
/*  16:    */   protected ScriptNode top;
/*  17:    */   private List<Scope> childScopes;
/*  18:    */   
/*  19:    */   public Scope()
/*  20:    */   {
/*  21: 67 */     this.type = 129;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Scope(int pos)
/*  25:    */   {
/*  26: 67 */     this.type = 129;
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33: 74 */     this.position = pos;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Scope(int pos, int len)
/*  37:    */   {
/*  38: 78 */     this(pos);
/*  39: 79 */     this.length = len;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Scope getParentScope()
/*  43:    */   {
/*  44: 83 */     return this.parentScope;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setParentScope(Scope parentScope)
/*  48:    */   {
/*  49: 90 */     this.parentScope = parentScope;
/*  50: 91 */     this.top = (parentScope == null ? (ScriptNode)this : parentScope.top);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void clearParentScope()
/*  54:    */   {
/*  55: 98 */     this.parentScope = null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Scope> getChildScopes()
/*  59:    */   {
/*  60:106 */     return this.childScopes;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void addChildScope(Scope child)
/*  64:    */   {
/*  65:116 */     if (this.childScopes == null) {
/*  66:117 */       this.childScopes = new ArrayList();
/*  67:    */     }
/*  68:119 */     this.childScopes.add(child);
/*  69:120 */     child.setParentScope(this);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void replaceWith(Scope newScope)
/*  73:    */   {
/*  74:133 */     if (this.childScopes != null)
/*  75:    */     {
/*  76:134 */       for (Scope kid : this.childScopes) {
/*  77:135 */         newScope.addChildScope(kid);
/*  78:    */       }
/*  79:137 */       this.childScopes.clear();
/*  80:138 */       this.childScopes = null;
/*  81:    */     }
/*  82:140 */     if ((this.symbolTable != null) && (!this.symbolTable.isEmpty())) {
/*  83:141 */       joinScopes(this, newScope);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ScriptNode getTop()
/*  88:    */   {
/*  89:149 */     return this.top;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setTop(ScriptNode top)
/*  93:    */   {
/*  94:156 */     this.top = top;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static Scope splitScope(Scope scope)
/*  98:    */   {
/*  99:166 */     Scope result = new Scope(scope.getType());
/* 100:167 */     result.symbolTable = scope.symbolTable;
/* 101:168 */     scope.symbolTable = null;
/* 102:169 */     result.parent = scope.parent;
/* 103:170 */     result.setParentScope(scope.getParentScope());
/* 104:171 */     result.setParentScope(result);
/* 105:172 */     scope.parent = result;
/* 106:173 */     result.top = scope.top;
/* 107:174 */     return result;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static void joinScopes(Scope source, Scope dest)
/* 111:    */   {
/* 112:181 */     Map<String, Symbol> src = source.ensureSymbolTable();
/* 113:182 */     Map<String, Symbol> dst = dest.ensureSymbolTable();
/* 114:183 */     if (!Collections.disjoint(src.keySet(), dst.keySet())) {
/* 115:184 */       codeBug();
/* 116:    */     }
/* 117:186 */     for (Map.Entry<String, Symbol> entry : src.entrySet())
/* 118:    */     {
/* 119:187 */       Symbol sym = (Symbol)entry.getValue();
/* 120:188 */       sym.setContainingTable(dest);
/* 121:189 */       dst.put(entry.getKey(), sym);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Scope getDefiningScope(String name)
/* 126:    */   {
/* 127:200 */     for (Scope s = this; s != null; s = s.parentScope)
/* 128:    */     {
/* 129:201 */       Map<String, Symbol> symbolTable = s.getSymbolTable();
/* 130:202 */       if ((symbolTable != null) && (symbolTable.containsKey(name))) {
/* 131:203 */         return s;
/* 132:    */       }
/* 133:    */     }
/* 134:206 */     return null;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Symbol getSymbol(String name)
/* 138:    */   {
/* 139:215 */     return this.symbolTable == null ? null : (Symbol)this.symbolTable.get(name);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void putSymbol(Symbol symbol)
/* 143:    */   {
/* 144:222 */     if (symbol.getName() == null) {
/* 145:223 */       throw new IllegalArgumentException("null symbol name");
/* 146:    */     }
/* 147:224 */     ensureSymbolTable();
/* 148:225 */     this.symbolTable.put(symbol.getName(), symbol);
/* 149:226 */     symbol.setContainingTable(this);
/* 150:227 */     this.top.addSymbol(symbol);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Map<String, Symbol> getSymbolTable()
/* 154:    */   {
/* 155:235 */     return this.symbolTable;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setSymbolTable(Map<String, Symbol> table)
/* 159:    */   {
/* 160:242 */     this.symbolTable = table;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private Map<String, Symbol> ensureSymbolTable()
/* 164:    */   {
/* 165:246 */     if (this.symbolTable == null) {
/* 166:247 */       this.symbolTable = new LinkedHashMap(5);
/* 167:    */     }
/* 168:249 */     return this.symbolTable;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<AstNode> getStatements()
/* 172:    */   {
/* 173:260 */     List<AstNode> stmts = new ArrayList();
/* 174:261 */     Node n = getFirstChild();
/* 175:262 */     while (n != null)
/* 176:    */     {
/* 177:263 */       stmts.add((AstNode)n);
/* 178:264 */       n = n.getNext();
/* 179:    */     }
/* 180:266 */     return stmts;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String toSource(int depth)
/* 184:    */   {
/* 185:271 */     StringBuilder sb = new StringBuilder();
/* 186:272 */     sb.append(makeIndent(depth));
/* 187:273 */     sb.append("{\n");
/* 188:274 */     for (Node kid : this) {
/* 189:275 */       sb.append(((AstNode)kid).toSource(depth + 1));
/* 190:    */     }
/* 191:277 */     sb.append(makeIndent(depth));
/* 192:278 */     sb.append("}\n");
/* 193:279 */     return sb.toString();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void visit(NodeVisitor v)
/* 197:    */   {
/* 198:284 */     if (v.visit(this)) {
/* 199:285 */       for (Node kid : this) {
/* 200:286 */         ((AstNode)kid).visit(v);
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Scope
 * JD-Core Version:    0.7.0.1
 */