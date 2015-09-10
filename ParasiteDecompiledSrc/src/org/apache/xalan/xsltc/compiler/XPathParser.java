/*    1:     */ package org.apache.xalan.xsltc.compiler;
/*    2:     */ 
/*    3:     */ import java.util.Stack;
/*    4:     */ import java.util.Vector;
/*    5:     */ import java_cup.runtime.Scanner;
/*    6:     */ import java_cup.runtime.Symbol;
/*    7:     */ import java_cup.runtime.lr_parser;
/*    8:     */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*    9:     */ 
/*   10:     */ public class XPathParser
/*   11:     */   extends lr_parser
/*   12:     */ {
/*   13:     */   public XPathParser() {}
/*   14:     */   
/*   15:     */   public XPathParser(Scanner s)
/*   16:     */   {
/*   17:  28 */     super(s);
/*   18:     */   }
/*   19:     */   
/*   20:  31 */   protected static final short[][] _production_table = lr_parser.unpackFromStrings(new String[] { "" });
/*   21:     */   
/*   22:     */   public short[][] production_table()
/*   23:     */   {
/*   24:  79 */     return _production_table;
/*   25:     */   }
/*   26:     */   
/*   27:  82 */   protected static final short[][] _action_table = lr_parser.unpackFromStrings(new String[] { "" });
/*   28:     */   
/*   29:     */   public short[][] action_table()
/*   30:     */   {
/*   31: 662 */     return _action_table;
/*   32:     */   }
/*   33:     */   
/*   34: 665 */   protected static final short[][] _reduce_table = lr_parser.unpackFromStrings(new String[] { "" });
/*   35:     */   protected CUP.XPathParser.actions action_obj;
/*   36:     */   
/*   37:     */   public short[][] reduce_table()
/*   38:     */   {
/*   39: 823 */     return _reduce_table;
/*   40:     */   }
/*   41:     */   
/*   42:     */   protected void init_actions()
/*   43:     */   {
/*   44: 831 */     this.action_obj = new CUP.XPathParser.actions(this);
/*   45:     */   }
/*   46:     */   
/*   47:     */   public Symbol do_action(int act_num, lr_parser parser, Stack stack, int top)
/*   48:     */     throws Exception
/*   49:     */   {
/*   50: 843 */     return this.action_obj.CUP$XPathParser$do_action(act_num, parser, stack, top);
/*   51:     */   }
/*   52:     */   
/*   53:     */   public int start_state()
/*   54:     */   {
/*   55: 847 */     return 0;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public int start_production()
/*   59:     */   {
/*   60: 849 */     return 0;
/*   61:     */   }
/*   62:     */   
/*   63:     */   public int EOF_sym()
/*   64:     */   {
/*   65: 852 */     return 0;
/*   66:     */   }
/*   67:     */   
/*   68:     */   public int error_sym()
/*   69:     */   {
/*   70: 855 */     return 1;
/*   71:     */   }
/*   72:     */   
/*   73: 862 */   public static final Vector EmptyArgs = new Vector(0);
/*   74: 867 */   public static final VariableRef DummyVarRef = null;
/*   75:     */   private Parser _parser;
/*   76:     */   private XSLTC _xsltc;
/*   77:     */   private String _expression;
/*   78: 883 */   private int _lineNumber = 0;
/*   79:     */   public SymbolTable _symbolTable;
/*   80:     */   
/*   81:     */   public XPathParser(Parser parser)
/*   82:     */   {
/*   83: 891 */     this._parser = parser;
/*   84: 892 */     this._xsltc = parser.getXSLTC();
/*   85: 893 */     this._symbolTable = parser.getSymbolTable();
/*   86:     */   }
/*   87:     */   
/*   88:     */   public int getLineNumber()
/*   89:     */   {
/*   90: 897 */     return this._lineNumber;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public QName getQNameIgnoreDefaultNs(String name)
/*   94:     */   {
/*   95: 901 */     return this._parser.getQNameIgnoreDefaultNs(name);
/*   96:     */   }
/*   97:     */   
/*   98:     */   public QName getQName(String namespace, String prefix, String localname)
/*   99:     */   {
/*  100: 905 */     return this._parser.getQName(namespace, prefix, localname);
/*  101:     */   }
/*  102:     */   
/*  103:     */   public void setMultiDocument(boolean flag)
/*  104:     */   {
/*  105: 909 */     this._xsltc.setMultiDocument(flag);
/*  106:     */   }
/*  107:     */   
/*  108:     */   public void setCallsNodeset(boolean flag)
/*  109:     */   {
/*  110: 913 */     this._xsltc.setCallsNodeset(flag);
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void setHasIdCall(boolean flag)
/*  114:     */   {
/*  115: 917 */     this._xsltc.setHasIdCall(flag);
/*  116:     */   }
/*  117:     */   
/*  118:     */   public StepPattern createStepPattern(int axis, Object test, Vector predicates)
/*  119:     */   {
/*  120:     */     int nodeType;
/*  121: 934 */     if (test == null)
/*  122:     */     {
/*  123: 935 */       nodeType = axis == 9 ? -1 : axis == 2 ? 2 : 1;
/*  124:     */       
/*  125:     */ 
/*  126: 938 */       return new StepPattern(axis, nodeType, predicates);
/*  127:     */     }
/*  128: 940 */     if ((test instanceof Integer))
/*  129:     */     {
/*  130: 941 */       nodeType = ((Integer)test).intValue();
/*  131:     */       
/*  132: 943 */       return new StepPattern(axis, nodeType, predicates);
/*  133:     */     }
/*  134: 946 */     QName name = (QName)test;
/*  135: 947 */     boolean setPriority = false;
/*  136: 949 */     if (axis == 9)
/*  137:     */     {
/*  138: 950 */       nodeType = name.toString().equals("*") ? -1 : this._xsltc.registerNamespacePrefix(name);
/*  139:     */     }
/*  140:     */     else
/*  141:     */     {
/*  142: 954 */       String uri = name.getNamespace();
/*  143: 955 */       String local = name.getLocalPart();
/*  144: 956 */       QName namespace_uri = this._parser.getQNameIgnoreDefaultNs("namespace-uri");
/*  145: 960 */       if ((uri != null) && ((local.equals("*")) || (local.equals("@*"))))
/*  146:     */       {
/*  147: 961 */         if (predicates == null) {
/*  148: 962 */           predicates = new Vector(2);
/*  149:     */         }
/*  150: 966 */         setPriority = predicates.size() == 0;
/*  151:     */         
/*  152: 968 */         predicates.add(new Predicate(new EqualityExpr(0, new NamespaceUriCall(namespace_uri), new LiteralExpr(uri))));
/*  153:     */       }
/*  154: 975 */       if (local.equals("*")) {
/*  155: 976 */         nodeType = axis == 2 ? 2 : 1;
/*  156: 979 */       } else if (local.equals("@*")) {
/*  157: 980 */         nodeType = 2;
/*  158:     */       } else {
/*  159: 983 */         nodeType = axis == 2 ? this._xsltc.registerAttribute(name) : this._xsltc.registerElement(name);
/*  160:     */       }
/*  161:     */     }
/*  162: 988 */     StepPattern result = new StepPattern(axis, nodeType, predicates);
/*  163: 991 */     if (setPriority) {
/*  164: 992 */       result.setPriority(-0.25D);
/*  165:     */     }
/*  166: 995 */     return result;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public int findNodeType(int axis, Object test)
/*  170:     */   {
/*  171:1000 */     if (test == null) {
/*  172:1001 */       return axis == 9 ? -1 : axis == 2 ? 2 : 1;
/*  173:     */     }
/*  174:1005 */     if ((test instanceof Integer)) {
/*  175:1006 */       return ((Integer)test).intValue();
/*  176:     */     }
/*  177:1009 */     QName name = (QName)test;
/*  178:1011 */     if (axis == 9) {
/*  179:1012 */       return name.toString().equals("*") ? -1 : this._xsltc.registerNamespacePrefix(name);
/*  180:     */     }
/*  181:1016 */     if (name.getNamespace() == null)
/*  182:     */     {
/*  183:1017 */       String local = name.getLocalPart();
/*  184:1019 */       if (local.equals("*")) {
/*  185:1020 */         return axis == 2 ? 2 : 1;
/*  186:     */       }
/*  187:1023 */       if (local.equals("@*")) {
/*  188:1024 */         return 2;
/*  189:     */       }
/*  190:     */     }
/*  191:1028 */     return axis == 2 ? this._xsltc.registerAttribute(name) : this._xsltc.registerElement(name);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public Symbol parse(String expression, int lineNumber)
/*  195:     */     throws Exception
/*  196:     */   {
/*  197:     */     try
/*  198:     */     {
/*  199:1046 */       this._expression = expression;
/*  200:1047 */       this._lineNumber = lineNumber;
/*  201:1048 */       return super.parse();
/*  202:     */     }
/*  203:     */     catch (IllegalCharException e)
/*  204:     */     {
/*  205:1051 */       ErrorMsg err = new ErrorMsg("ILLEGAL_CHAR_ERR", lineNumber, e.getMessage());
/*  206:     */       
/*  207:1053 */       this._parser.reportError(2, err);
/*  208:     */     }
/*  209:1055 */     return null;
/*  210:     */   }
/*  211:     */   
/*  212:     */   final SyntaxTreeNode lookupName(QName name)
/*  213:     */   {
/*  214:1065 */     SyntaxTreeNode result = this._parser.lookupVariable(name);
/*  215:1066 */     if (result != null) {
/*  216:1067 */       return result;
/*  217:     */     }
/*  218:1069 */     return this._symbolTable.lookupName(name);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public final void addError(ErrorMsg error)
/*  222:     */   {
/*  223:1073 */     this._parser.reportError(3, error);
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void report_error(String message, Object info)
/*  227:     */   {
/*  228:1077 */     ErrorMsg err = new ErrorMsg("SYNTAX_ERR", this._lineNumber, this._expression);
/*  229:     */     
/*  230:1079 */     this._parser.reportError(2, err);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public void report_fatal_error(String message, Object info) {}
/*  234:     */   
/*  235:     */   public RelativeLocationPath insertStep(Step step, RelativeLocationPath rlp)
/*  236:     */   {
/*  237:1087 */     if ((rlp instanceof Step)) {
/*  238:1088 */       return new ParentLocationPath(step, (Step)rlp);
/*  239:     */     }
/*  240:1090 */     if ((rlp instanceof ParentLocationPath))
/*  241:     */     {
/*  242:1091 */       ParentLocationPath plp = (ParentLocationPath)rlp;
/*  243:1092 */       RelativeLocationPath newrlp = insertStep(step, plp.getPath());
/*  244:1093 */       return new ParentLocationPath(newrlp, plp.getStep());
/*  245:     */     }
/*  246:1096 */     addError(new ErrorMsg("INTERNAL_ERR", "XPathParser.insertStep"));
/*  247:1097 */     return rlp;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public boolean isElementAxis(int axis)
/*  251:     */   {
/*  252:1107 */     return (axis == 3) || (axis == 2) || (axis == 9) || (axis == 4);
/*  253:     */   }
/*  254:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.XPathParser
 * JD-Core Version:    0.7.0.1
 */