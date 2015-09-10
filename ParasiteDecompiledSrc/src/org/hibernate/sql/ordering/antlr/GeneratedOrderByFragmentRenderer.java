/*   1:    */ package org.hibernate.sql.ordering.antlr;
/*   2:    */ 
/*   3:    */ import antlr.NoViableAltException;
/*   4:    */ import antlr.RecognitionException;
/*   5:    */ import antlr.TreeParser;
/*   6:    */ import antlr.collections.AST;
/*   7:    */ 
/*   8:    */ public class GeneratedOrderByFragmentRenderer
/*   9:    */   extends TreeParser
/*  10:    */   implements GeneratedOrderByFragmentRendererTokenTypes
/*  11:    */ {
/*  12: 51 */   private StringBuffer buffer = new StringBuffer();
/*  13:    */   
/*  14:    */   protected void out(String text)
/*  15:    */   {
/*  16: 54 */     this.buffer.append(text);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected void out(AST ast)
/*  20:    */   {
/*  21: 58 */     this.buffer.append(ast.getText());
/*  22:    */   }
/*  23:    */   
/*  24:    */   String getRenderedFragment()
/*  25:    */   {
/*  26: 62 */     return this.buffer.toString();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public GeneratedOrderByFragmentRenderer()
/*  30:    */   {
/*  31: 65 */     this.tokenNames = _tokenNames;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final void orderByFragment(AST _t)
/*  35:    */     throws RecognitionException
/*  36:    */   {
/*  37: 70 */     AST orderByFragment_AST_in = _t == ASTNULL ? null : _t;
/*  38:    */     try
/*  39:    */     {
/*  40: 73 */       AST __t2 = _t;
/*  41: 74 */       AST tmp1_AST_in = _t;
/*  42: 75 */       match(_t, 4);
/*  43: 76 */       _t = _t.getFirstChild();
/*  44: 77 */       sortSpecification(_t);
/*  45: 78 */       _t = this._retTree;
/*  46:    */       for (;;)
/*  47:    */       {
/*  48: 82 */         if (_t == null) {
/*  49: 82 */           _t = ASTNULL;
/*  50:    */         }
/*  51: 83 */         if (_t.getType() != 5) {
/*  52:    */           break;
/*  53:    */         }
/*  54: 84 */         out(", ");
/*  55: 85 */         sortSpecification(_t);
/*  56: 86 */         _t = this._retTree;
/*  57:    */       }
/*  58: 94 */       _t = __t2;
/*  59: 95 */       _t = _t.getNextSibling();
/*  60:    */     }
/*  61:    */     catch (RecognitionException ex)
/*  62:    */     {
/*  63: 98 */       reportError(ex);
/*  64: 99 */       if (_t != null) {
/*  65: 99 */         _t = _t.getNextSibling();
/*  66:    */       }
/*  67:    */     }
/*  68:101 */     this._retTree = _t;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public final void sortSpecification(AST _t)
/*  72:    */     throws RecognitionException
/*  73:    */   {
/*  74:106 */     AST sortSpecification_AST_in = _t == ASTNULL ? null : _t;
/*  75:    */     try
/*  76:    */     {
/*  77:109 */       AST __t6 = _t;
/*  78:110 */       AST tmp2_AST_in = _t;
/*  79:111 */       match(_t, 5);
/*  80:112 */       _t = _t.getFirstChild();
/*  81:113 */       sortKeySpecification(_t);
/*  82:114 */       _t = this._retTree;
/*  83:116 */       if (_t == null) {
/*  84:116 */         _t = ASTNULL;
/*  85:    */       }
/*  86:117 */       switch (_t.getType())
/*  87:    */       {
/*  88:    */       case 12: 
/*  89:120 */         collationSpecification(_t);
/*  90:121 */         _t = this._retTree;
/*  91:122 */         break;
/*  92:    */       case 3: 
/*  93:    */       case 6: 
/*  94:    */         break;
/*  95:    */       default: 
/*  96:131 */         throw new NoViableAltException(_t);
/*  97:    */       }
/*  98:136 */       if (_t == null) {
/*  99:136 */         _t = ASTNULL;
/* 100:    */       }
/* 101:137 */       switch (_t.getType())
/* 102:    */       {
/* 103:    */       case 6: 
/* 104:140 */         orderingSpecification(_t);
/* 105:141 */         _t = this._retTree;
/* 106:142 */         break;
/* 107:    */       case 3: 
/* 108:    */         break;
/* 109:    */       default: 
/* 110:150 */         throw new NoViableAltException(_t);
/* 111:    */       }
/* 112:154 */       _t = __t6;
/* 113:155 */       _t = _t.getNextSibling();
/* 114:    */     }
/* 115:    */     catch (RecognitionException ex)
/* 116:    */     {
/* 117:158 */       reportError(ex);
/* 118:159 */       if (_t != null) {
/* 119:159 */         _t = _t.getNextSibling();
/* 120:    */       }
/* 121:    */     }
/* 122:161 */     this._retTree = _t;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public final void sortKeySpecification(AST _t)
/* 126:    */     throws RecognitionException
/* 127:    */   {
/* 128:166 */     AST sortKeySpecification_AST_in = _t == ASTNULL ? null : _t;
/* 129:    */     try
/* 130:    */     {
/* 131:169 */       AST __t10 = _t;
/* 132:170 */       AST tmp3_AST_in = _t;
/* 133:171 */       match(_t, 7);
/* 134:172 */       _t = _t.getFirstChild();
/* 135:173 */       sortKey(_t);
/* 136:174 */       _t = this._retTree;
/* 137:175 */       _t = __t10;
/* 138:176 */       _t = _t.getNextSibling();
/* 139:    */     }
/* 140:    */     catch (RecognitionException ex)
/* 141:    */     {
/* 142:179 */       reportError(ex);
/* 143:180 */       if (_t != null) {
/* 144:180 */         _t = _t.getNextSibling();
/* 145:    */       }
/* 146:    */     }
/* 147:182 */     this._retTree = _t;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final void collationSpecification(AST _t)
/* 151:    */     throws RecognitionException
/* 152:    */   {
/* 153:187 */     AST collationSpecification_AST_in = _t == ASTNULL ? null : _t;
/* 154:188 */     AST c = null;
/* 155:    */     try
/* 156:    */     {
/* 157:191 */       c = _t;
/* 158:192 */       match(_t, 12);
/* 159:193 */       _t = _t.getNextSibling();
/* 160:    */       
/* 161:195 */       out(" collate ");
/* 162:196 */       out(c);
/* 163:    */     }
/* 164:    */     catch (RecognitionException ex)
/* 165:    */     {
/* 166:200 */       reportError(ex);
/* 167:201 */       if (_t != null) {
/* 168:201 */         _t = _t.getNextSibling();
/* 169:    */       }
/* 170:    */     }
/* 171:203 */     this._retTree = _t;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public final void orderingSpecification(AST _t)
/* 175:    */     throws RecognitionException
/* 176:    */   {
/* 177:208 */     AST orderingSpecification_AST_in = _t == ASTNULL ? null : _t;
/* 178:209 */     AST o = null;
/* 179:    */     try
/* 180:    */     {
/* 181:212 */       o = _t;
/* 182:213 */       match(_t, 6);
/* 183:214 */       _t = _t.getNextSibling();
/* 184:    */       
/* 185:216 */       out(" ");
/* 186:217 */       out(o);
/* 187:    */     }
/* 188:    */     catch (RecognitionException ex)
/* 189:    */     {
/* 190:221 */       reportError(ex);
/* 191:222 */       if (_t != null) {
/* 192:222 */         _t = _t.getNextSibling();
/* 193:    */       }
/* 194:    */     }
/* 195:224 */     this._retTree = _t;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public final void sortKey(AST _t)
/* 199:    */     throws RecognitionException
/* 200:    */   {
/* 201:229 */     AST sortKey_AST_in = _t == ASTNULL ? null : _t;
/* 202:230 */     AST i = null;
/* 203:    */     try
/* 204:    */     {
/* 205:233 */       i = _t;
/* 206:234 */       match(_t, 17);
/* 207:235 */       _t = _t.getNextSibling();
/* 208:    */       
/* 209:237 */       out(i);
/* 210:    */     }
/* 211:    */     catch (RecognitionException ex)
/* 212:    */     {
/* 213:241 */       reportError(ex);
/* 214:242 */       if (_t != null) {
/* 215:242 */         _t = _t.getNextSibling();
/* 216:    */       }
/* 217:    */     }
/* 218:244 */     this._retTree = _t;
/* 219:    */   }
/* 220:    */   
/* 221:248 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "ORDER_BY", "SORT_SPEC", "ORDER_SPEC", "SORT_KEY", "EXPR_LIST", "DOT", "IDENT_LIST", "COLUMN_REF", "\"collate\"", "\"asc\"", "\"desc\"", "COMMA", "HARD_QUOTE", "IDENT", "OPEN_PAREN", "CLOSE_PAREN", "NUM_DOUBLE", "NUM_FLOAT", "NUM_INT", "NUM_LONG", "QUOTED_STRING", "\"ascending\"", "\"descending\"", "ID_START_LETTER", "ID_LETTER", "ESCqs", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX", "WS" };
/* 222:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentRenderer
 * JD-Core Version:    0.7.0.1
 */