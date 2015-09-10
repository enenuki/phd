/*   1:    */ package org.hibernate.hql.internal.ast;
/*   2:    */ 
/*   3:    */ import antlr.ASTFactory;
/*   4:    */ import antlr.Token;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import java.lang.reflect.Constructor;
/*   7:    */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*   8:    */ import org.hibernate.hql.internal.ast.tree.AggregateNode;
/*   9:    */ import org.hibernate.hql.internal.ast.tree.BetweenOperatorNode;
/*  10:    */ import org.hibernate.hql.internal.ast.tree.BinaryArithmeticOperatorNode;
/*  11:    */ import org.hibernate.hql.internal.ast.tree.BinaryLogicOperatorNode;
/*  12:    */ import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
/*  13:    */ import org.hibernate.hql.internal.ast.tree.Case2Node;
/*  14:    */ import org.hibernate.hql.internal.ast.tree.CaseNode;
/*  15:    */ import org.hibernate.hql.internal.ast.tree.CollectionFunction;
/*  16:    */ import org.hibernate.hql.internal.ast.tree.ConstructorNode;
/*  17:    */ import org.hibernate.hql.internal.ast.tree.CountNode;
/*  18:    */ import org.hibernate.hql.internal.ast.tree.DeleteStatement;
/*  19:    */ import org.hibernate.hql.internal.ast.tree.DotNode;
/*  20:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  21:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  22:    */ import org.hibernate.hql.internal.ast.tree.IdentNode;
/*  23:    */ import org.hibernate.hql.internal.ast.tree.ImpliedFromElement;
/*  24:    */ import org.hibernate.hql.internal.ast.tree.InLogicOperatorNode;
/*  25:    */ import org.hibernate.hql.internal.ast.tree.IndexNode;
/*  26:    */ import org.hibernate.hql.internal.ast.tree.InitializeableNode;
/*  27:    */ import org.hibernate.hql.internal.ast.tree.InsertStatement;
/*  28:    */ import org.hibernate.hql.internal.ast.tree.IntoClause;
/*  29:    */ import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
/*  30:    */ import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
/*  31:    */ import org.hibernate.hql.internal.ast.tree.JavaConstantNode;
/*  32:    */ import org.hibernate.hql.internal.ast.tree.LiteralNode;
/*  33:    */ import org.hibernate.hql.internal.ast.tree.MapEntryNode;
/*  34:    */ import org.hibernate.hql.internal.ast.tree.MapKeyNode;
/*  35:    */ import org.hibernate.hql.internal.ast.tree.MapValueNode;
/*  36:    */ import org.hibernate.hql.internal.ast.tree.MethodNode;
/*  37:    */ import org.hibernate.hql.internal.ast.tree.OrderByClause;
/*  38:    */ import org.hibernate.hql.internal.ast.tree.ParameterNode;
/*  39:    */ import org.hibernate.hql.internal.ast.tree.QueryNode;
/*  40:    */ import org.hibernate.hql.internal.ast.tree.ResultVariableRefNode;
/*  41:    */ import org.hibernate.hql.internal.ast.tree.SelectClause;
/*  42:    */ import org.hibernate.hql.internal.ast.tree.SelectExpressionImpl;
/*  43:    */ import org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode;
/*  44:    */ import org.hibernate.hql.internal.ast.tree.SqlFragment;
/*  45:    */ import org.hibernate.hql.internal.ast.tree.SqlNode;
/*  46:    */ import org.hibernate.hql.internal.ast.tree.UnaryArithmeticNode;
/*  47:    */ import org.hibernate.hql.internal.ast.tree.UnaryLogicOperatorNode;
/*  48:    */ import org.hibernate.hql.internal.ast.tree.UpdateStatement;
/*  49:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  50:    */ 
/*  51:    */ public class SqlASTFactory
/*  52:    */   extends ASTFactory
/*  53:    */   implements HqlSqlTokenTypes
/*  54:    */ {
/*  55:    */   private HqlSqlWalker walker;
/*  56:    */   
/*  57:    */   public SqlASTFactory(HqlSqlWalker walker)
/*  58:    */   {
/*  59: 92 */     this.walker = walker;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Class getASTNodeType(int tokenType)
/*  63:    */   {
/*  64:102 */     switch (tokenType)
/*  65:    */     {
/*  66:    */     case 45: 
/*  67:    */     case 86: 
/*  68:105 */       return QueryNode.class;
/*  69:    */     case 51: 
/*  70:107 */       return UpdateStatement.class;
/*  71:    */     case 13: 
/*  72:109 */       return DeleteStatement.class;
/*  73:    */     case 29: 
/*  74:111 */       return InsertStatement.class;
/*  75:    */     case 30: 
/*  76:113 */       return IntoClause.class;
/*  77:    */     case 22: 
/*  78:115 */       return FromClause.class;
/*  79:    */     case 134: 
/*  80:117 */       return FromElement.class;
/*  81:    */     case 135: 
/*  82:119 */       return ImpliedFromElement.class;
/*  83:    */     case 15: 
/*  84:121 */       return DotNode.class;
/*  85:    */     case 78: 
/*  86:123 */       return IndexNode.class;
/*  87:    */     case 126: 
/*  88:    */     case 140: 
/*  89:127 */       return IdentNode.class;
/*  90:    */     case 150: 
/*  91:129 */       return ResultVariableRefNode.class;
/*  92:    */     case 142: 
/*  93:131 */       return SqlFragment.class;
/*  94:    */     case 81: 
/*  95:133 */       return MethodNode.class;
/*  96:    */     case 17: 
/*  97:    */     case 27: 
/*  98:136 */       return CollectionFunction.class;
/*  99:    */     case 137: 
/* 100:138 */       return SelectClause.class;
/* 101:    */     case 144: 
/* 102:140 */       return SelectExpressionImpl.class;
/* 103:    */     case 71: 
/* 104:142 */       return AggregateNode.class;
/* 105:    */     case 12: 
/* 106:144 */       return CountNode.class;
/* 107:    */     case 73: 
/* 108:146 */       return ConstructorNode.class;
/* 109:    */     case 95: 
/* 110:    */     case 96: 
/* 111:    */     case 97: 
/* 112:    */     case 98: 
/* 113:    */     case 99: 
/* 114:    */     case 124: 
/* 115:    */     case 125: 
/* 116:154 */       return LiteralNode.class;
/* 117:    */     case 20: 
/* 118:    */     case 49: 
/* 119:157 */       return BooleanLiteralNode.class;
/* 120:    */     case 100: 
/* 121:159 */       return JavaConstantNode.class;
/* 122:    */     case 41: 
/* 123:161 */       return OrderByClause.class;
/* 124:    */     case 115: 
/* 125:    */     case 116: 
/* 126:    */     case 117: 
/* 127:    */     case 118: 
/* 128:    */     case 119: 
/* 129:167 */       return BinaryArithmeticOperatorNode.class;
/* 130:    */     case 90: 
/* 131:    */     case 91: 
/* 132:170 */       return UnaryArithmeticNode.class;
/* 133:    */     case 74: 
/* 134:172 */       return Case2Node.class;
/* 135:    */     case 54: 
/* 136:174 */       return CaseNode.class;
/* 137:    */     case 123: 
/* 138:    */     case 148: 
/* 139:177 */       return ParameterNode.class;
/* 140:    */     case 34: 
/* 141:    */     case 84: 
/* 142:    */     case 102: 
/* 143:    */     case 108: 
/* 144:    */     case 110: 
/* 145:    */     case 111: 
/* 146:    */     case 112: 
/* 147:    */     case 113: 
/* 148:186 */       return BinaryLogicOperatorNode.class;
/* 149:    */     case 26: 
/* 150:    */     case 83: 
/* 151:189 */       return InLogicOperatorNode.class;
/* 152:    */     case 10: 
/* 153:    */     case 82: 
/* 154:192 */       return BetweenOperatorNode.class;
/* 155:    */     case 80: 
/* 156:194 */       return IsNullLogicOperatorNode.class;
/* 157:    */     case 79: 
/* 158:196 */       return IsNotNullLogicOperatorNode.class;
/* 159:    */     case 19: 
/* 160:198 */       return UnaryLogicOperatorNode.class;
/* 161:    */     case 68: 
/* 162:200 */       return MapKeyNode.class;
/* 163:    */     case 69: 
/* 164:203 */       return MapValueNode.class;
/* 165:    */     case 70: 
/* 166:206 */       return MapEntryNode.class;
/* 167:    */     }
/* 168:209 */     return SqlNode.class;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected AST createUsingCtor(Token token, String className)
/* 172:    */   {
/* 173:    */     AST t;
/* 174:    */     try
/* 175:    */     {
/* 176:217 */       Class c = Class.forName(className);
/* 177:218 */       Class[] tokenArgType = { Token.class };
/* 178:219 */       Constructor ctor = c.getConstructor(tokenArgType);
/* 179:220 */       if (ctor != null)
/* 180:    */       {
/* 181:221 */         AST t = (AST)ctor.newInstance(new Object[] { token });
/* 182:222 */         initializeSqlNode(t);
/* 183:    */       }
/* 184:    */       else
/* 185:    */       {
/* 186:227 */         t = create(c);
/* 187:    */       }
/* 188:    */     }
/* 189:    */     catch (Exception e)
/* 190:    */     {
/* 191:231 */       throw new IllegalArgumentException("Invalid class or can't make instance, " + className);
/* 192:    */     }
/* 193:233 */     return t;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void initializeSqlNode(AST t)
/* 197:    */   {
/* 198:238 */     if ((t instanceof InitializeableNode))
/* 199:    */     {
/* 200:239 */       InitializeableNode initializeableNode = (InitializeableNode)t;
/* 201:240 */       initializeableNode.initialize(this.walker);
/* 202:    */     }
/* 203:242 */     if ((t instanceof SessionFactoryAwareNode)) {
/* 204:243 */       ((SessionFactoryAwareNode)t).setSessionFactory(this.walker.getSessionFactoryHelper().getFactory());
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected AST create(Class c)
/* 209:    */   {
/* 210:    */     AST t;
/* 211:    */     try
/* 212:    */     {
/* 213:256 */       t = (AST)c.newInstance();
/* 214:257 */       initializeSqlNode(t);
/* 215:    */     }
/* 216:    */     catch (Exception e)
/* 217:    */     {
/* 218:260 */       error("Can't create AST Node " + c.getName());
/* 219:261 */       return null;
/* 220:    */     }
/* 221:263 */     return t;
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.SqlASTFactory
 * JD-Core Version:    0.7.0.1
 */