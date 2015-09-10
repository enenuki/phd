/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import java.text.MessageFormat;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.ResourceBundle;
/*   6:    */ import org.apache.xalan.xsltc.compiler.Stylesheet;
/*   7:    */ import org.apache.xalan.xsltc.compiler.SyntaxTreeNode;
/*   8:    */ 
/*   9:    */ public final class ErrorMsg
/*  10:    */ {
/*  11:    */   private String _code;
/*  12:    */   private int _line;
/*  13: 42 */   private String _message = null;
/*  14: 43 */   private String _url = null;
/*  15: 44 */   Object[] _params = null;
/*  16:    */   private boolean _isWarningError;
/*  17:    */   public static final String MULTIPLE_STYLESHEET_ERR = "MULTIPLE_STYLESHEET_ERR";
/*  18:    */   public static final String TEMPLATE_REDEF_ERR = "TEMPLATE_REDEF_ERR";
/*  19:    */   public static final String TEMPLATE_UNDEF_ERR = "TEMPLATE_UNDEF_ERR";
/*  20:    */   public static final String VARIABLE_REDEF_ERR = "VARIABLE_REDEF_ERR";
/*  21:    */   public static final String VARIABLE_UNDEF_ERR = "VARIABLE_UNDEF_ERR";
/*  22:    */   public static final String CLASS_NOT_FOUND_ERR = "CLASS_NOT_FOUND_ERR";
/*  23:    */   public static final String METHOD_NOT_FOUND_ERR = "METHOD_NOT_FOUND_ERR";
/*  24:    */   public static final String ARGUMENT_CONVERSION_ERR = "ARGUMENT_CONVERSION_ERR";
/*  25:    */   public static final String FILE_NOT_FOUND_ERR = "FILE_NOT_FOUND_ERR";
/*  26:    */   public static final String INVALID_URI_ERR = "INVALID_URI_ERR";
/*  27:    */   public static final String FILE_ACCESS_ERR = "FILE_ACCESS_ERR";
/*  28:    */   public static final String MISSING_ROOT_ERR = "MISSING_ROOT_ERR";
/*  29:    */   public static final String NAMESPACE_UNDEF_ERR = "NAMESPACE_UNDEF_ERR";
/*  30:    */   public static final String FUNCTION_RESOLVE_ERR = "FUNCTION_RESOLVE_ERR";
/*  31:    */   public static final String NEED_LITERAL_ERR = "NEED_LITERAL_ERR";
/*  32:    */   public static final String XPATH_PARSER_ERR = "XPATH_PARSER_ERR";
/*  33:    */   public static final String REQUIRED_ATTR_ERR = "REQUIRED_ATTR_ERR";
/*  34:    */   public static final String ILLEGAL_CHAR_ERR = "ILLEGAL_CHAR_ERR";
/*  35:    */   public static final String ILLEGAL_PI_ERR = "ILLEGAL_PI_ERR";
/*  36:    */   public static final String STRAY_ATTRIBUTE_ERR = "STRAY_ATTRIBUTE_ERR";
/*  37:    */   public static final String ILLEGAL_ATTRIBUTE_ERR = "ILLEGAL_ATTRIBUTE_ERR";
/*  38:    */   public static final String CIRCULAR_INCLUDE_ERR = "CIRCULAR_INCLUDE_ERR";
/*  39:    */   public static final String RESULT_TREE_SORT_ERR = "RESULT_TREE_SORT_ERR";
/*  40:    */   public static final String SYMBOLS_REDEF_ERR = "SYMBOLS_REDEF_ERR";
/*  41:    */   public static final String XSL_VERSION_ERR = "XSL_VERSION_ERR";
/*  42:    */   public static final String CIRCULAR_VARIABLE_ERR = "CIRCULAR_VARIABLE_ERR";
/*  43:    */   public static final String ILLEGAL_BINARY_OP_ERR = "ILLEGAL_BINARY_OP_ERR";
/*  44:    */   public static final String ILLEGAL_ARG_ERR = "ILLEGAL_ARG_ERR";
/*  45:    */   public static final String DOCUMENT_ARG_ERR = "DOCUMENT_ARG_ERR";
/*  46:    */   public static final String MISSING_WHEN_ERR = "MISSING_WHEN_ERR";
/*  47:    */   public static final String MULTIPLE_OTHERWISE_ERR = "MULTIPLE_OTHERWISE_ERR";
/*  48:    */   public static final String STRAY_OTHERWISE_ERR = "STRAY_OTHERWISE_ERR";
/*  49:    */   public static final String STRAY_WHEN_ERR = "STRAY_WHEN_ERR";
/*  50:    */   public static final String WHEN_ELEMENT_ERR = "WHEN_ELEMENT_ERR";
/*  51:    */   public static final String UNNAMED_ATTRIBSET_ERR = "UNNAMED_ATTRIBSET_ERR";
/*  52:    */   public static final String ILLEGAL_CHILD_ERR = "ILLEGAL_CHILD_ERR";
/*  53:    */   public static final String ILLEGAL_ELEM_NAME_ERR = "ILLEGAL_ELEM_NAME_ERR";
/*  54:    */   public static final String ILLEGAL_ATTR_NAME_ERR = "ILLEGAL_ATTR_NAME_ERR";
/*  55:    */   public static final String ILLEGAL_TEXT_NODE_ERR = "ILLEGAL_TEXT_NODE_ERR";
/*  56:    */   public static final String SAX_PARSER_CONFIG_ERR = "SAX_PARSER_CONFIG_ERR";
/*  57:    */   public static final String INTERNAL_ERR = "INTERNAL_ERR";
/*  58:    */   public static final String UNSUPPORTED_XSL_ERR = "UNSUPPORTED_XSL_ERR";
/*  59:    */   public static final String UNSUPPORTED_EXT_ERR = "UNSUPPORTED_EXT_ERR";
/*  60:    */   public static final String MISSING_XSLT_URI_ERR = "MISSING_XSLT_URI_ERR";
/*  61:    */   public static final String MISSING_XSLT_TARGET_ERR = "MISSING_XSLT_TARGET_ERR";
/*  62:    */   public static final String NOT_IMPLEMENTED_ERR = "NOT_IMPLEMENTED_ERR";
/*  63:    */   public static final String NOT_STYLESHEET_ERR = "NOT_STYLESHEET_ERR";
/*  64:    */   public static final String ELEMENT_PARSE_ERR = "ELEMENT_PARSE_ERR";
/*  65:    */   public static final String KEY_USE_ATTR_ERR = "KEY_USE_ATTR_ERR";
/*  66:    */   public static final String OUTPUT_VERSION_ERR = "OUTPUT_VERSION_ERR";
/*  67:    */   public static final String ILLEGAL_RELAT_OP_ERR = "ILLEGAL_RELAT_OP_ERR";
/*  68:    */   public static final String ATTRIBSET_UNDEF_ERR = "ATTRIBSET_UNDEF_ERR";
/*  69:    */   public static final String ATTR_VAL_TEMPLATE_ERR = "ATTR_VAL_TEMPLATE_ERR";
/*  70:    */   public static final String UNKNOWN_SIG_TYPE_ERR = "UNKNOWN_SIG_TYPE_ERR";
/*  71:    */   public static final String DATA_CONVERSION_ERR = "DATA_CONVERSION_ERR";
/*  72:    */   public static final String NO_TRANSLET_CLASS_ERR = "NO_TRANSLET_CLASS_ERR";
/*  73:    */   public static final String NO_MAIN_TRANSLET_ERR = "NO_MAIN_TRANSLET_ERR";
/*  74:    */   public static final String TRANSLET_CLASS_ERR = "TRANSLET_CLASS_ERR";
/*  75:    */   public static final String TRANSLET_OBJECT_ERR = "TRANSLET_OBJECT_ERR";
/*  76:    */   public static final String ERROR_LISTENER_NULL_ERR = "ERROR_LISTENER_NULL_ERR";
/*  77:    */   public static final String JAXP_UNKNOWN_SOURCE_ERR = "JAXP_UNKNOWN_SOURCE_ERR";
/*  78:    */   public static final String JAXP_NO_SOURCE_ERR = "JAXP_NO_SOURCE_ERR";
/*  79:    */   public static final String JAXP_COMPILE_ERR = "JAXP_COMPILE_ERR";
/*  80:    */   public static final String JAXP_INVALID_ATTR_ERR = "JAXP_INVALID_ATTR_ERR";
/*  81:    */   public static final String JAXP_SET_RESULT_ERR = "JAXP_SET_RESULT_ERR";
/*  82:    */   public static final String JAXP_NO_TRANSLET_ERR = "JAXP_NO_TRANSLET_ERR";
/*  83:    */   public static final String JAXP_NO_HANDLER_ERR = "JAXP_NO_HANDLER_ERR";
/*  84:    */   public static final String JAXP_NO_RESULT_ERR = "JAXP_NO_RESULT_ERR";
/*  85:    */   public static final String JAXP_UNKNOWN_PROP_ERR = "JAXP_UNKNOWN_PROP_ERR";
/*  86:    */   public static final String SAX2DOM_ADAPTER_ERR = "SAX2DOM_ADAPTER_ERR";
/*  87:    */   public static final String XSLTC_SOURCE_ERR = "XSLTC_SOURCE_ERR";
/*  88:    */   public static final String ER_RESULT_NULL = "ER_RESULT_NULL";
/*  89:    */   public static final String JAXP_INVALID_SET_PARAM_VALUE = "JAXP_INVALID_SET_PARAM_VALUE";
/*  90:    */   public static final String JAXP_SET_FEATURE_NULL_NAME = "JAXP_SET_FEATURE_NULL_NAME";
/*  91:    */   public static final String JAXP_GET_FEATURE_NULL_NAME = "JAXP_GET_FEATURE_NULL_NAME";
/*  92:    */   public static final String JAXP_UNSUPPORTED_FEATURE = "JAXP_UNSUPPORTED_FEATURE";
/*  93:    */   public static final String COMPILE_STDIN_ERR = "COMPILE_STDIN_ERR";
/*  94:    */   public static final String COMPILE_USAGE_STR = "COMPILE_USAGE_STR";
/*  95:    */   public static final String TRANSFORM_USAGE_STR = "TRANSFORM_USAGE_STR";
/*  96:    */   public static final String STRAY_SORT_ERR = "STRAY_SORT_ERR";
/*  97:    */   public static final String UNSUPPORTED_ENCODING = "UNSUPPORTED_ENCODING";
/*  98:    */   public static final String SYNTAX_ERR = "SYNTAX_ERR";
/*  99:    */   public static final String CONSTRUCTOR_NOT_FOUND = "CONSTRUCTOR_NOT_FOUND";
/* 100:    */   public static final String NO_JAVA_FUNCT_THIS_REF = "NO_JAVA_FUNCT_THIS_REF";
/* 101:    */   public static final String TYPE_CHECK_ERR = "TYPE_CHECK_ERR";
/* 102:    */   public static final String TYPE_CHECK_UNK_LOC_ERR = "TYPE_CHECK_UNK_LOC_ERR";
/* 103:    */   public static final String ILLEGAL_CMDLINE_OPTION_ERR = "ILLEGAL_CMDLINE_OPTION_ERR";
/* 104:    */   public static final String CMDLINE_OPT_MISSING_ARG_ERR = "CMDLINE_OPT_MISSING_ARG_ERR";
/* 105:    */   public static final String WARNING_PLUS_WRAPPED_MSG = "WARNING_PLUS_WRAPPED_MSG";
/* 106:    */   public static final String WARNING_MSG = "WARNING_MSG";
/* 107:    */   public static final String FATAL_ERR_PLUS_WRAPPED_MSG = "FATAL_ERR_PLUS_WRAPPED_MSG";
/* 108:    */   public static final String FATAL_ERR_MSG = "FATAL_ERR_MSG";
/* 109:    */   public static final String ERROR_PLUS_WRAPPED_MSG = "ERROR_PLUS_WRAPPED_MSG";
/* 110:    */   public static final String ERROR_MSG = "ERROR_MSG";
/* 111:    */   public static final String TRANSFORM_WITH_TRANSLET_STR = "TRANSFORM_WITH_TRANSLET_STR";
/* 112:    */   public static final String TRANSFORM_WITH_JAR_STR = "TRANSFORM_WITH_JAR_STR";
/* 113:    */   public static final String COULD_NOT_CREATE_TRANS_FACT = "COULD_NOT_CREATE_TRANS_FACT";
/* 114:    */   public static final String TRANSLET_NAME_JAVA_CONFLICT = "TRANSLET_NAME_JAVA_CONFLICT";
/* 115:    */   public static final String INVALID_QNAME_ERR = "INVALID_QNAME_ERR";
/* 116:    */   public static final String INVALID_NCNAME_ERR = "INVALID_NCNAME_ERR";
/* 117:    */   public static final String INVALID_METHOD_IN_OUTPUT = "INVALID_METHOD_IN_OUTPUT";
/* 118:    */   public static final String OUTLINE_ERR_TRY_CATCH = "OUTLINE_ERR_TRY_CATCH";
/* 119:    */   public static final String OUTLINE_ERR_UNBALANCED_MARKERS = "OUTLINE_ERR_UNBALANCED_MARKERS";
/* 120:    */   public static final String OUTLINE_ERR_DELETED_TARGET = "OUTLINE_ERR_DELETED_TARGET";
/* 121:    */   public static final String OUTLINE_ERR_METHOD_TOO_BIG = "OUTLINE_ERR_METHOD_TOO_BIG";
/* 122:175 */   private static ResourceBundle _bundle = ResourceBundle.getBundle("org.apache.xalan.xsltc.compiler.util.ErrorMessages", Locale.getDefault());
/* 123:    */   public static final String ERROR_MESSAGES_KEY = "ERROR_MESSAGES_KEY";
/* 124:    */   public static final String COMPILER_ERROR_KEY = "COMPILER_ERROR_KEY";
/* 125:    */   public static final String COMPILER_WARNING_KEY = "COMPILER_WARNING_KEY";
/* 126:    */   public static final String RUNTIME_ERROR_KEY = "RUNTIME_ERROR_KEY";
/* 127:    */   
/* 128:    */   public ErrorMsg(String code)
/* 129:    */   {
/* 130:181 */     this._code = code;
/* 131:182 */     this._line = 0;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public ErrorMsg(Throwable e)
/* 135:    */   {
/* 136:186 */     this._code = null;
/* 137:187 */     this._message = e.getMessage();
/* 138:188 */     this._line = 0;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public ErrorMsg(String message, int line)
/* 142:    */   {
/* 143:192 */     this._code = null;
/* 144:193 */     this._message = message;
/* 145:194 */     this._line = line;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ErrorMsg(String code, int line, Object param)
/* 149:    */   {
/* 150:198 */     this._code = code;
/* 151:199 */     this._line = line;
/* 152:200 */     this._params = new Object[] { param };
/* 153:    */   }
/* 154:    */   
/* 155:    */   public ErrorMsg(String code, Object param)
/* 156:    */   {
/* 157:204 */     this(code);
/* 158:205 */     this._params = new Object[1];
/* 159:206 */     this._params[0] = param;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public ErrorMsg(String code, Object param1, Object param2)
/* 163:    */   {
/* 164:210 */     this(code);
/* 165:211 */     this._params = new Object[2];
/* 166:212 */     this._params[0] = param1;
/* 167:213 */     this._params[1] = param2;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public ErrorMsg(String code, SyntaxTreeNode node)
/* 171:    */   {
/* 172:217 */     this._code = code;
/* 173:218 */     this._url = getFileName(node);
/* 174:219 */     this._line = node.getLineNumber();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public ErrorMsg(String code, Object param1, SyntaxTreeNode node)
/* 178:    */   {
/* 179:223 */     this._code = code;
/* 180:224 */     this._url = getFileName(node);
/* 181:225 */     this._line = node.getLineNumber();
/* 182:226 */     this._params = new Object[1];
/* 183:227 */     this._params[0] = param1;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public ErrorMsg(String code, Object param1, Object param2, SyntaxTreeNode node)
/* 187:    */   {
/* 188:232 */     this._code = code;
/* 189:233 */     this._url = getFileName(node);
/* 190:234 */     this._line = node.getLineNumber();
/* 191:235 */     this._params = new Object[2];
/* 192:236 */     this._params[0] = param1;
/* 193:237 */     this._params[1] = param2;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private String getFileName(SyntaxTreeNode node)
/* 197:    */   {
/* 198:241 */     Stylesheet stylesheet = node.getStylesheet();
/* 199:242 */     if (stylesheet != null) {
/* 200:243 */       return stylesheet.getSystemId();
/* 201:    */     }
/* 202:245 */     return null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private String formatLine()
/* 206:    */   {
/* 207:249 */     StringBuffer result = new StringBuffer();
/* 208:250 */     if (this._url != null)
/* 209:    */     {
/* 210:251 */       result.append(this._url);
/* 211:252 */       result.append(": ");
/* 212:    */     }
/* 213:254 */     if (this._line > 0)
/* 214:    */     {
/* 215:255 */       result.append("line ");
/* 216:256 */       result.append(Integer.toString(this._line));
/* 217:257 */       result.append(": ");
/* 218:    */     }
/* 219:259 */     return result.toString();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String toString()
/* 223:    */   {
/* 224:268 */     String suffix = this._params == null ? this._message : null != this._code ? getErrorMessage() : MessageFormat.format(getErrorMessage(), this._params);
/* 225:    */     
/* 226:    */ 
/* 227:271 */     return formatLine() + suffix;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String toString(Object obj)
/* 231:    */   {
/* 232:275 */     Object[] params = new Object[1];
/* 233:276 */     params[0] = obj.toString();
/* 234:277 */     String suffix = MessageFormat.format(getErrorMessage(), params);
/* 235:278 */     return formatLine() + suffix;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String toString(Object obj0, Object obj1)
/* 239:    */   {
/* 240:282 */     Object[] params = new Object[2];
/* 241:283 */     params[0] = obj0.toString();
/* 242:284 */     params[1] = obj1.toString();
/* 243:285 */     String suffix = MessageFormat.format(getErrorMessage(), params);
/* 244:286 */     return formatLine() + suffix;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private String getErrorMessage()
/* 248:    */   {
/* 249:297 */     return _bundle.getString(this._code);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setWarningError(boolean flag)
/* 253:    */   {
/* 254:305 */     this._isWarningError = flag;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean isWarningError()
/* 258:    */   {
/* 259:309 */     return this._isWarningError;
/* 260:    */   }
/* 261:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.ErrorMsg
 * JD-Core Version:    0.7.0.1
 */