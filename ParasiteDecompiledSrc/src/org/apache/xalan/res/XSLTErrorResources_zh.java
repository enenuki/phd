/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_zh
/*    9:     */   extends ListResourceBundle
/*   10:     */ {
/*   11:     */   public static final int MAX_CODE = 201;
/*   12:     */   public static final int MAX_WARNING = 29;
/*   13:     */   public static final int MAX_OTHERS = 55;
/*   14:     */   public static final int MAX_MESSAGES = 231;
/*   15:     */   public static final String ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX = "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX";
/*   16:     */   public static final String ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT = "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT";
/*   17:     */   public static final String ER_NO_CURLYBRACE = "ER_NO_CURLYBRACE";
/*   18:     */   public static final String ER_FUNCTION_NOT_SUPPORTED = "ER_FUNCTION_NOT_SUPPORTED";
/*   19:     */   public static final String ER_ILLEGAL_ATTRIBUTE = "ER_ILLEGAL_ATTRIBUTE";
/*   20:     */   public static final String ER_NULL_SOURCENODE_APPLYIMPORTS = "ER_NULL_SOURCENODE_APPLYIMPORTS";
/*   21:     */   public static final String ER_CANNOT_ADD = "ER_CANNOT_ADD";
/*   22:     */   public static final String ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES = "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES";
/*   23:     */   public static final String ER_NO_NAME_ATTRIB = "ER_NO_NAME_ATTRIB";
/*   24:     */   public static final String ER_TEMPLATE_NOT_FOUND = "ER_TEMPLATE_NOT_FOUND";
/*   25:     */   public static final String ER_CANT_RESOLVE_NAME_AVT = "ER_CANT_RESOLVE_NAME_AVT";
/*   26:     */   public static final String ER_REQUIRES_ATTRIB = "ER_REQUIRES_ATTRIB";
/*   27:     */   public static final String ER_MUST_HAVE_TEST_ATTRIB = "ER_MUST_HAVE_TEST_ATTRIB";
/*   28:     */   public static final String ER_BAD_VAL_ON_LEVEL_ATTRIB = "ER_BAD_VAL_ON_LEVEL_ATTRIB";
/*   29:     */   public static final String ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML = "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML";
/*   30:     */   public static final String ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME = "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME";
/*   31:     */   public static final String ER_NEED_MATCH_ATTRIB = "ER_NEED_MATCH_ATTRIB";
/*   32:     */   public static final String ER_NEED_NAME_OR_MATCH_ATTRIB = "ER_NEED_NAME_OR_MATCH_ATTRIB";
/*   33:     */   public static final String ER_CANT_RESOLVE_NSPREFIX = "ER_CANT_RESOLVE_NSPREFIX";
/*   34:     */   public static final String ER_ILLEGAL_VALUE = "ER_ILLEGAL_VALUE";
/*   35:     */   public static final String ER_NO_OWNERDOC = "ER_NO_OWNERDOC";
/*   36:     */   public static final String ER_ELEMTEMPLATEELEM_ERR = "ER_ELEMTEMPLATEELEM_ERR";
/*   37:     */   public static final String ER_NULL_CHILD = "ER_NULL_CHILD";
/*   38:     */   public static final String ER_NEED_SELECT_ATTRIB = "ER_NEED_SELECT_ATTRIB";
/*   39:     */   public static final String ER_NEED_TEST_ATTRIB = "ER_NEED_TEST_ATTRIB";
/*   40:     */   public static final String ER_NEED_NAME_ATTRIB = "ER_NEED_NAME_ATTRIB";
/*   41:     */   public static final String ER_NO_CONTEXT_OWNERDOC = "ER_NO_CONTEXT_OWNERDOC";
/*   42:     */   public static final String ER_COULD_NOT_CREATE_XML_PROC_LIAISON = "ER_COULD_NOT_CREATE_XML_PROC_LIAISON";
/*   43:     */   public static final String ER_PROCESS_NOT_SUCCESSFUL = "ER_PROCESS_NOT_SUCCESSFUL";
/*   44:     */   public static final String ER_NOT_SUCCESSFUL = "ER_NOT_SUCCESSFUL";
/*   45:     */   public static final String ER_ENCODING_NOT_SUPPORTED = "ER_ENCODING_NOT_SUPPORTED";
/*   46:     */   public static final String ER_COULD_NOT_CREATE_TRACELISTENER = "ER_COULD_NOT_CREATE_TRACELISTENER";
/*   47:     */   public static final String ER_KEY_REQUIRES_NAME_ATTRIB = "ER_KEY_REQUIRES_NAME_ATTRIB";
/*   48:     */   public static final String ER_KEY_REQUIRES_MATCH_ATTRIB = "ER_KEY_REQUIRES_MATCH_ATTRIB";
/*   49:     */   public static final String ER_KEY_REQUIRES_USE_ATTRIB = "ER_KEY_REQUIRES_USE_ATTRIB";
/*   50:     */   public static final String ER_REQUIRES_ELEMENTS_ATTRIB = "ER_REQUIRES_ELEMENTS_ATTRIB";
/*   51:     */   public static final String ER_MISSING_PREFIX_ATTRIB = "ER_MISSING_PREFIX_ATTRIB";
/*   52:     */   public static final String ER_BAD_STYLESHEET_URL = "ER_BAD_STYLESHEET_URL";
/*   53:     */   public static final String ER_FILE_NOT_FOUND = "ER_FILE_NOT_FOUND";
/*   54:     */   public static final String ER_IOEXCEPTION = "ER_IOEXCEPTION";
/*   55:     */   public static final String ER_NO_HREF_ATTRIB = "ER_NO_HREF_ATTRIB";
/*   56:     */   public static final String ER_STYLESHEET_INCLUDES_ITSELF = "ER_STYLESHEET_INCLUDES_ITSELF";
/*   57:     */   public static final String ER_PROCESSINCLUDE_ERROR = "ER_PROCESSINCLUDE_ERROR";
/*   58:     */   public static final String ER_MISSING_LANG_ATTRIB = "ER_MISSING_LANG_ATTRIB";
/*   59:     */   public static final String ER_MISSING_CONTAINER_ELEMENT_COMPONENT = "ER_MISSING_CONTAINER_ELEMENT_COMPONENT";
/*   60:     */   public static final String ER_CAN_ONLY_OUTPUT_TO_ELEMENT = "ER_CAN_ONLY_OUTPUT_TO_ELEMENT";
/*   61:     */   public static final String ER_PROCESS_ERROR = "ER_PROCESS_ERROR";
/*   62:     */   public static final String ER_UNIMPLNODE_ERROR = "ER_UNIMPLNODE_ERROR";
/*   63:     */   public static final String ER_NO_SELECT_EXPRESSION = "ER_NO_SELECT_EXPRESSION";
/*   64:     */   public static final String ER_CANNOT_SERIALIZE_XSLPROCESSOR = "ER_CANNOT_SERIALIZE_XSLPROCESSOR";
/*   65:     */   public static final String ER_NO_INPUT_STYLESHEET = "ER_NO_INPUT_STYLESHEET";
/*   66:     */   public static final String ER_FAILED_PROCESS_STYLESHEET = "ER_FAILED_PROCESS_STYLESHEET";
/*   67:     */   public static final String ER_COULDNT_PARSE_DOC = "ER_COULDNT_PARSE_DOC";
/*   68:     */   public static final String ER_COULDNT_FIND_FRAGMENT = "ER_COULDNT_FIND_FRAGMENT";
/*   69:     */   public static final String ER_NODE_NOT_ELEMENT = "ER_NODE_NOT_ELEMENT";
/*   70:     */   public static final String ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB = "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB";
/*   71:     */   public static final String ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB = "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB";
/*   72:     */   public static final String ER_NO_CLONE_OF_DOCUMENT_FRAG = "ER_NO_CLONE_OF_DOCUMENT_FRAG";
/*   73:     */   public static final String ER_CANT_CREATE_ITEM = "ER_CANT_CREATE_ITEM";
/*   74:     */   public static final String ER_XMLSPACE_ILLEGAL_VALUE = "ER_XMLSPACE_ILLEGAL_VALUE";
/*   75:     */   public static final String ER_NO_XSLKEY_DECLARATION = "ER_NO_XSLKEY_DECLARATION";
/*   76:     */   public static final String ER_CANT_CREATE_URL = "ER_CANT_CREATE_URL";
/*   77:     */   public static final String ER_XSLFUNCTIONS_UNSUPPORTED = "ER_XSLFUNCTIONS_UNSUPPORTED";
/*   78:     */   public static final String ER_PROCESSOR_ERROR = "ER_PROCESSOR_ERROR";
/*   79:     */   public static final String ER_NOT_ALLOWED_INSIDE_STYLESHEET = "ER_NOT_ALLOWED_INSIDE_STYLESHEET";
/*   80:     */   public static final String ER_RESULTNS_NOT_SUPPORTED = "ER_RESULTNS_NOT_SUPPORTED";
/*   81:     */   public static final String ER_DEFAULTSPACE_NOT_SUPPORTED = "ER_DEFAULTSPACE_NOT_SUPPORTED";
/*   82:     */   public static final String ER_INDENTRESULT_NOT_SUPPORTED = "ER_INDENTRESULT_NOT_SUPPORTED";
/*   83:     */   public static final String ER_ILLEGAL_ATTRIB = "ER_ILLEGAL_ATTRIB";
/*   84:     */   public static final String ER_UNKNOWN_XSL_ELEM = "ER_UNKNOWN_XSL_ELEM";
/*   85:     */   public static final String ER_BAD_XSLSORT_USE = "ER_BAD_XSLSORT_USE";
/*   86:     */   public static final String ER_MISPLACED_XSLWHEN = "ER_MISPLACED_XSLWHEN";
/*   87:     */   public static final String ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE = "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE";
/*   88:     */   public static final String ER_MISPLACED_XSLOTHERWISE = "ER_MISPLACED_XSLOTHERWISE";
/*   89:     */   public static final String ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE = "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE";
/*   90:     */   public static final String ER_NOT_ALLOWED_INSIDE_TEMPLATE = "ER_NOT_ALLOWED_INSIDE_TEMPLATE";
/*   91:     */   public static final String ER_UNKNOWN_EXT_NS_PREFIX = "ER_UNKNOWN_EXT_NS_PREFIX";
/*   92:     */   public static final String ER_IMPORTS_AS_FIRST_ELEM = "ER_IMPORTS_AS_FIRST_ELEM";
/*   93:     */   public static final String ER_IMPORTING_ITSELF = "ER_IMPORTING_ITSELF";
/*   94:     */   public static final String ER_XMLSPACE_ILLEGAL_VAL = "ER_XMLSPACE_ILLEGAL_VAL";
/*   95:     */   public static final String ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL = "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL";
/*   96:     */   public static final String ER_SAX_EXCEPTION = "ER_SAX_EXCEPTION";
/*   97:     */   public static final String ER_XSLT_ERROR = "ER_XSLT_ERROR";
/*   98:     */   public static final String ER_CURRENCY_SIGN_ILLEGAL = "ER_CURRENCY_SIGN_ILLEGAL";
/*   99:     */   public static final String ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM = "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM";
/*  100:     */   public static final String ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER = "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER";
/*  101:     */   public static final String ER_REDIRECT_COULDNT_GET_FILENAME = "ER_REDIRECT_COULDNT_GET_FILENAME";
/*  102:     */   public static final String ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT = "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT";
/*  103:     */   public static final String ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX = "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX";
/*  104:     */   public static final String ER_MISSING_NS_URI = "ER_MISSING_NS_URI";
/*  105:     */   public static final String ER_MISSING_ARG_FOR_OPTION = "ER_MISSING_ARG_FOR_OPTION";
/*  106:     */   public static final String ER_INVALID_OPTION = "ER_INVALID_OPTION";
/*  107:     */   public static final String ER_MALFORMED_FORMAT_STRING = "ER_MALFORMED_FORMAT_STRING";
/*  108:     */   public static final String ER_STYLESHEET_REQUIRES_VERSION_ATTRIB = "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB";
/*  109:     */   public static final String ER_ILLEGAL_ATTRIBUTE_VALUE = "ER_ILLEGAL_ATTRIBUTE_VALUE";
/*  110:     */   public static final String ER_CHOOSE_REQUIRES_WHEN = "ER_CHOOSE_REQUIRES_WHEN";
/*  111:     */   public static final String ER_NO_APPLY_IMPORT_IN_FOR_EACH = "ER_NO_APPLY_IMPORT_IN_FOR_EACH";
/*  112:     */   public static final String ER_CANT_USE_DTM_FOR_OUTPUT = "ER_CANT_USE_DTM_FOR_OUTPUT";
/*  113:     */   public static final String ER_CANT_USE_DTM_FOR_INPUT = "ER_CANT_USE_DTM_FOR_INPUT";
/*  114:     */   public static final String ER_CALL_TO_EXT_FAILED = "ER_CALL_TO_EXT_FAILED";
/*  115:     */   public static final String ER_PREFIX_MUST_RESOLVE = "ER_PREFIX_MUST_RESOLVE";
/*  116:     */   public static final String ER_INVALID_UTF16_SURROGATE = "ER_INVALID_UTF16_SURROGATE";
/*  117:     */   public static final String ER_XSLATTRSET_USED_ITSELF = "ER_XSLATTRSET_USED_ITSELF";
/*  118:     */   public static final String ER_CANNOT_MIX_XERCESDOM = "ER_CANNOT_MIX_XERCESDOM";
/*  119:     */   public static final String ER_TOO_MANY_LISTENERS = "ER_TOO_MANY_LISTENERS";
/*  120:     */   public static final String ER_IN_ELEMTEMPLATEELEM_READOBJECT = "ER_IN_ELEMTEMPLATEELEM_READOBJECT";
/*  121:     */   public static final String ER_DUPLICATE_NAMED_TEMPLATE = "ER_DUPLICATE_NAMED_TEMPLATE";
/*  122:     */   public static final String ER_INVALID_KEY_CALL = "ER_INVALID_KEY_CALL";
/*  123:     */   public static final String ER_REFERENCING_ITSELF = "ER_REFERENCING_ITSELF";
/*  124:     */   public static final String ER_ILLEGAL_DOMSOURCE_INPUT = "ER_ILLEGAL_DOMSOURCE_INPUT";
/*  125:     */   public static final String ER_CLASS_NOT_FOUND_FOR_OPTION = "ER_CLASS_NOT_FOUND_FOR_OPTION";
/*  126:     */   public static final String ER_REQUIRED_ELEM_NOT_FOUND = "ER_REQUIRED_ELEM_NOT_FOUND";
/*  127:     */   public static final String ER_INPUT_CANNOT_BE_NULL = "ER_INPUT_CANNOT_BE_NULL";
/*  128:     */   public static final String ER_URI_CANNOT_BE_NULL = "ER_URI_CANNOT_BE_NULL";
/*  129:     */   public static final String ER_FILE_CANNOT_BE_NULL = "ER_FILE_CANNOT_BE_NULL";
/*  130:     */   public static final String ER_SOURCE_CANNOT_BE_NULL = "ER_SOURCE_CANNOT_BE_NULL";
/*  131:     */   public static final String ER_CANNOT_INIT_BSFMGR = "ER_CANNOT_INIT_BSFMGR";
/*  132:     */   public static final String ER_CANNOT_CMPL_EXTENSN = "ER_CANNOT_CMPL_EXTENSN";
/*  133:     */   public static final String ER_CANNOT_CREATE_EXTENSN = "ER_CANNOT_CREATE_EXTENSN";
/*  134:     */   public static final String ER_INSTANCE_MTHD_CALL_REQUIRES = "ER_INSTANCE_MTHD_CALL_REQUIRES";
/*  135:     */   public static final String ER_INVALID_ELEMENT_NAME = "ER_INVALID_ELEMENT_NAME";
/*  136:     */   public static final String ER_ELEMENT_NAME_METHOD_STATIC = "ER_ELEMENT_NAME_METHOD_STATIC";
/*  137:     */   public static final String ER_EXTENSION_FUNC_UNKNOWN = "ER_EXTENSION_FUNC_UNKNOWN";
/*  138:     */   public static final String ER_MORE_MATCH_CONSTRUCTOR = "ER_MORE_MATCH_CONSTRUCTOR";
/*  139:     */   public static final String ER_MORE_MATCH_METHOD = "ER_MORE_MATCH_METHOD";
/*  140:     */   public static final String ER_MORE_MATCH_ELEMENT = "ER_MORE_MATCH_ELEMENT";
/*  141:     */   public static final String ER_INVALID_CONTEXT_PASSED = "ER_INVALID_CONTEXT_PASSED";
/*  142:     */   public static final String ER_POOL_EXISTS = "ER_POOL_EXISTS";
/*  143:     */   public static final String ER_NO_DRIVER_NAME = "ER_NO_DRIVER_NAME";
/*  144:     */   public static final String ER_NO_URL = "ER_NO_URL";
/*  145:     */   public static final String ER_POOL_SIZE_LESSTHAN_ONE = "ER_POOL_SIZE_LESSTHAN_ONE";
/*  146:     */   public static final String ER_INVALID_DRIVER = "ER_INVALID_DRIVER";
/*  147:     */   public static final String ER_NO_STYLESHEETROOT = "ER_NO_STYLESHEETROOT";
/*  148:     */   public static final String ER_ILLEGAL_XMLSPACE_VALUE = "ER_ILLEGAL_XMLSPACE_VALUE";
/*  149:     */   public static final String ER_PROCESSFROMNODE_FAILED = "ER_PROCESSFROMNODE_FAILED";
/*  150:     */   public static final String ER_RESOURCE_COULD_NOT_LOAD = "ER_RESOURCE_COULD_NOT_LOAD";
/*  151:     */   public static final String ER_BUFFER_SIZE_LESSTHAN_ZERO = "ER_BUFFER_SIZE_LESSTHAN_ZERO";
/*  152:     */   public static final String ER_UNKNOWN_ERROR_CALLING_EXTENSION = "ER_UNKNOWN_ERROR_CALLING_EXTENSION";
/*  153:     */   public static final String ER_NO_NAMESPACE_DECL = "ER_NO_NAMESPACE_DECL";
/*  154:     */   public static final String ER_ELEM_CONTENT_NOT_ALLOWED = "ER_ELEM_CONTENT_NOT_ALLOWED";
/*  155:     */   public static final String ER_STYLESHEET_DIRECTED_TERMINATION = "ER_STYLESHEET_DIRECTED_TERMINATION";
/*  156:     */   public static final String ER_ONE_OR_TWO = "ER_ONE_OR_TWO";
/*  157:     */   public static final String ER_TWO_OR_THREE = "ER_TWO_OR_THREE";
/*  158:     */   public static final String ER_COULD_NOT_LOAD_RESOURCE = "ER_COULD_NOT_LOAD_RESOURCE";
/*  159:     */   public static final String ER_CANNOT_INIT_DEFAULT_TEMPLATES = "ER_CANNOT_INIT_DEFAULT_TEMPLATES";
/*  160:     */   public static final String ER_RESULT_NULL = "ER_RESULT_NULL";
/*  161:     */   public static final String ER_RESULT_COULD_NOT_BE_SET = "ER_RESULT_COULD_NOT_BE_SET";
/*  162:     */   public static final String ER_NO_OUTPUT_SPECIFIED = "ER_NO_OUTPUT_SPECIFIED";
/*  163:     */   public static final String ER_CANNOT_TRANSFORM_TO_RESULT_TYPE = "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE";
/*  164:     */   public static final String ER_CANNOT_TRANSFORM_SOURCE_TYPE = "ER_CANNOT_TRANSFORM_SOURCE_TYPE";
/*  165:     */   public static final String ER_NULL_CONTENT_HANDLER = "ER_NULL_CONTENT_HANDLER";
/*  166:     */   public static final String ER_NULL_ERROR_HANDLER = "ER_NULL_ERROR_HANDLER";
/*  167:     */   public static final String ER_CANNOT_CALL_PARSE = "ER_CANNOT_CALL_PARSE";
/*  168:     */   public static final String ER_NO_PARENT_FOR_FILTER = "ER_NO_PARENT_FOR_FILTER";
/*  169:     */   public static final String ER_NO_STYLESHEET_IN_MEDIA = "ER_NO_STYLESHEET_IN_MEDIA";
/*  170:     */   public static final String ER_NO_STYLESHEET_PI = "ER_NO_STYLESHEET_PI";
/*  171:     */   public static final String ER_NOT_SUPPORTED = "ER_NOT_SUPPORTED";
/*  172:     */   public static final String ER_PROPERTY_VALUE_BOOLEAN = "ER_PROPERTY_VALUE_BOOLEAN";
/*  173:     */   public static final String ER_COULD_NOT_FIND_EXTERN_SCRIPT = "ER_COULD_NOT_FIND_EXTERN_SCRIPT";
/*  174:     */   public static final String ER_RESOURCE_COULD_NOT_FIND = "ER_RESOURCE_COULD_NOT_FIND";
/*  175:     */   public static final String ER_OUTPUT_PROPERTY_NOT_RECOGNIZED = "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED";
/*  176:     */   public static final String ER_FAILED_CREATING_ELEMLITRSLT = "ER_FAILED_CREATING_ELEMLITRSLT";
/*  177:     */   public static final String ER_VALUE_SHOULD_BE_NUMBER = "ER_VALUE_SHOULD_BE_NUMBER";
/*  178:     */   public static final String ER_VALUE_SHOULD_EQUAL = "ER_VALUE_SHOULD_EQUAL";
/*  179:     */   public static final String ER_FAILED_CALLING_METHOD = "ER_FAILED_CALLING_METHOD";
/*  180:     */   public static final String ER_FAILED_CREATING_ELEMTMPL = "ER_FAILED_CREATING_ELEMTMPL";
/*  181:     */   public static final String ER_CHARS_NOT_ALLOWED = "ER_CHARS_NOT_ALLOWED";
/*  182:     */   public static final String ER_ATTR_NOT_ALLOWED = "ER_ATTR_NOT_ALLOWED";
/*  183:     */   public static final String ER_BAD_VALUE = "ER_BAD_VALUE";
/*  184:     */   public static final String ER_ATTRIB_VALUE_NOT_FOUND = "ER_ATTRIB_VALUE_NOT_FOUND";
/*  185:     */   public static final String ER_ATTRIB_VALUE_NOT_RECOGNIZED = "ER_ATTRIB_VALUE_NOT_RECOGNIZED";
/*  186:     */   public static final String ER_NULL_URI_NAMESPACE = "ER_NULL_URI_NAMESPACE";
/*  187:     */   public static final String ER_NUMBER_TOO_BIG = "ER_NUMBER_TOO_BIG";
/*  188:     */   public static final String ER_CANNOT_FIND_SAX1_DRIVER = "ER_CANNOT_FIND_SAX1_DRIVER";
/*  189:     */   public static final String ER_SAX1_DRIVER_NOT_LOADED = "ER_SAX1_DRIVER_NOT_LOADED";
/*  190:     */   public static final String ER_SAX1_DRIVER_NOT_INSTANTIATED = "ER_SAX1_DRIVER_NOT_INSTANTIATED";
/*  191:     */   public static final String ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER = "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER";
/*  192:     */   public static final String ER_PARSER_PROPERTY_NOT_SPECIFIED = "ER_PARSER_PROPERTY_NOT_SPECIFIED";
/*  193:     */   public static final String ER_PARSER_ARG_CANNOT_BE_NULL = "ER_PARSER_ARG_CANNOT_BE_NULL";
/*  194:     */   public static final String ER_FEATURE = "ER_FEATURE";
/*  195:     */   public static final String ER_PROPERTY = "ER_PROPERTY";
/*  196:     */   public static final String ER_NULL_ENTITY_RESOLVER = "ER_NULL_ENTITY_RESOLVER";
/*  197:     */   public static final String ER_NULL_DTD_HANDLER = "ER_NULL_DTD_HANDLER";
/*  198:     */   public static final String ER_NO_DRIVER_NAME_SPECIFIED = "ER_NO_DRIVER_NAME_SPECIFIED";
/*  199:     */   public static final String ER_NO_URL_SPECIFIED = "ER_NO_URL_SPECIFIED";
/*  200:     */   public static final String ER_POOLSIZE_LESS_THAN_ONE = "ER_POOLSIZE_LESS_THAN_ONE";
/*  201:     */   public static final String ER_INVALID_DRIVER_NAME = "ER_INVALID_DRIVER_NAME";
/*  202:     */   public static final String ER_ERRORLISTENER = "ER_ERRORLISTENER";
/*  203:     */   public static final String ER_ASSERT_NO_TEMPLATE_PARENT = "ER_ASSERT_NO_TEMPLATE_PARENT";
/*  204:     */   public static final String ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR = "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR";
/*  205:     */   public static final String ER_NOT_ALLOWED_IN_POSITION = "ER_NOT_ALLOWED_IN_POSITION";
/*  206:     */   public static final String ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION = "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION";
/*  207:     */   public static final String ER_NAMESPACE_CONTEXT_NULL_NAMESPACE = "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE";
/*  208:     */   public static final String ER_NAMESPACE_CONTEXT_NULL_PREFIX = "ER_NAMESPACE_CONTEXT_NULL_PREFIX";
/*  209:     */   public static final String ER_XPATH_RESOLVER_NULL_QNAME = "ER_XPATH_RESOLVER_NULL_QNAME";
/*  210:     */   public static final String ER_XPATH_RESOLVER_NEGATIVE_ARITY = "ER_XPATH_RESOLVER_NEGATIVE_ARITY";
/*  211:     */   public static final String INVALID_TCHAR = "INVALID_TCHAR";
/*  212:     */   public static final String INVALID_QNAME = "INVALID_QNAME";
/*  213:     */   public static final String INVALID_ENUM = "INVALID_ENUM";
/*  214:     */   public static final String INVALID_NMTOKEN = "INVALID_NMTOKEN";
/*  215:     */   public static final String INVALID_NCNAME = "INVALID_NCNAME";
/*  216:     */   public static final String INVALID_BOOLEAN = "INVALID_BOOLEAN";
/*  217:     */   public static final String INVALID_NUMBER = "INVALID_NUMBER";
/*  218:     */   public static final String ER_ARG_LITERAL = "ER_ARG_LITERAL";
/*  219:     */   public static final String ER_DUPLICATE_GLOBAL_VAR = "ER_DUPLICATE_GLOBAL_VAR";
/*  220:     */   public static final String ER_DUPLICATE_VAR = "ER_DUPLICATE_VAR";
/*  221:     */   public static final String ER_TEMPLATE_NAME_MATCH = "ER_TEMPLATE_NAME_MATCH";
/*  222:     */   public static final String ER_INVALID_PREFIX = "ER_INVALID_PREFIX";
/*  223:     */   public static final String ER_NO_ATTRIB_SET = "ER_NO_ATTRIB_SET";
/*  224:     */   public static final String ER_FUNCTION_NOT_FOUND = "ER_FUNCTION_NOT_FOUND";
/*  225:     */   public static final String ER_CANT_HAVE_CONTENT_AND_SELECT = "ER_CANT_HAVE_CONTENT_AND_SELECT";
/*  226:     */   public static final String ER_INVALID_SET_PARAM_VALUE = "ER_INVALID_SET_PARAM_VALUE";
/*  227:     */   public static final String ER_SET_FEATURE_NULL_NAME = "ER_SET_FEATURE_NULL_NAME";
/*  228:     */   public static final String ER_GET_FEATURE_NULL_NAME = "ER_GET_FEATURE_NULL_NAME";
/*  229:     */   public static final String ER_UNSUPPORTED_FEATURE = "ER_UNSUPPORTED_FEATURE";
/*  230:     */   public static final String ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING = "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING";
/*  231:     */   public static final String WG_FOUND_CURLYBRACE = "WG_FOUND_CURLYBRACE";
/*  232:     */   public static final String WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR = "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR";
/*  233:     */   public static final String WG_EXPR_ATTRIB_CHANGED_TO_SELECT = "WG_EXPR_ATTRIB_CHANGED_TO_SELECT";
/*  234:     */   public static final String WG_NO_LOCALE_IN_FORMATNUMBER = "WG_NO_LOCALE_IN_FORMATNUMBER";
/*  235:     */   public static final String WG_LOCALE_NOT_FOUND = "WG_LOCALE_NOT_FOUND";
/*  236:     */   public static final String WG_CANNOT_MAKE_URL_FROM = "WG_CANNOT_MAKE_URL_FROM";
/*  237:     */   public static final String WG_CANNOT_LOAD_REQUESTED_DOC = "WG_CANNOT_LOAD_REQUESTED_DOC";
/*  238:     */   public static final String WG_CANNOT_FIND_COLLATOR = "WG_CANNOT_FIND_COLLATOR";
/*  239:     */   public static final String WG_FUNCTIONS_SHOULD_USE_URL = "WG_FUNCTIONS_SHOULD_USE_URL";
/*  240:     */   public static final String WG_ENCODING_NOT_SUPPORTED_USING_UTF8 = "WG_ENCODING_NOT_SUPPORTED_USING_UTF8";
/*  241:     */   public static final String WG_ENCODING_NOT_SUPPORTED_USING_JAVA = "WG_ENCODING_NOT_SUPPORTED_USING_JAVA";
/*  242:     */   public static final String WG_SPECIFICITY_CONFLICTS = "WG_SPECIFICITY_CONFLICTS";
/*  243:     */   public static final String WG_PARSING_AND_PREPARING = "WG_PARSING_AND_PREPARING";
/*  244:     */   public static final String WG_ATTR_TEMPLATE = "WG_ATTR_TEMPLATE";
/*  245:     */   public static final String WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESPACE = "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP";
/*  246:     */   public static final String WG_ATTRIB_NOT_HANDLED = "WG_ATTRIB_NOT_HANDLED";
/*  247:     */   public static final String WG_NO_DECIMALFORMAT_DECLARATION = "WG_NO_DECIMALFORMAT_DECLARATION";
/*  248:     */   public static final String WG_OLD_XSLT_NS = "WG_OLD_XSLT_NS";
/*  249:     */   public static final String WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED = "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED";
/*  250:     */   public static final String WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE = "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE";
/*  251:     */   public static final String WG_ILLEGAL_ATTRIBUTE = "WG_ILLEGAL_ATTRIBUTE";
/*  252:     */   public static final String WG_COULD_NOT_RESOLVE_PREFIX = "WG_COULD_NOT_RESOLVE_PREFIX";
/*  253:     */   public static final String WG_STYLESHEET_REQUIRES_VERSION_ATTRIB = "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB";
/*  254:     */   public static final String WG_ILLEGAL_ATTRIBUTE_NAME = "WG_ILLEGAL_ATTRIBUTE_NAME";
/*  255:     */   public static final String WG_ILLEGAL_ATTRIBUTE_VALUE = "WG_ILLEGAL_ATTRIBUTE_VALUE";
/*  256:     */   public static final String WG_EMPTY_SECOND_ARG = "WG_EMPTY_SECOND_ARG";
/*  257:     */   public static final String WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML = "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML";
/*  258:     */   public static final String WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME = "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME";
/*  259:     */   public static final String WG_ILLEGAL_ATTRIBUTE_POSITION = "WG_ILLEGAL_ATTRIBUTE_POSITION";
/*  260:     */   public static final String NO_MODIFICATION_ALLOWED_ERR = "NO_MODIFICATION_ALLOWED_ERR";
/*  261:     */   public static final String BAD_CODE = "BAD_CODE";
/*  262:     */   public static final String FORMAT_FAILED = "FORMAT_FAILED";
/*  263:     */   public static final String ERROR_STRING = "#错误";
/*  264:     */   public static final String ERROR_HEADER = "错误：";
/*  265:     */   public static final String WARNING_HEADER = "警告：";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "PATTERN ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "错误：表达式中不能有“{”" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} 有一个非法属性：{1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode 在 xsl:apply-imports 中为空！" }, { "ER_CANNOT_ADD", "无法将“{0}”添加到“{1}”" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode 在 handleApplyTemplatesInstruction 中为空！" }, { "ER_NO_NAME_ATTRIB", "“{0}”必须有 name 属性。" }, { "ER_TEMPLATE_NOT_FOUND", "找不到以下名称的模板：{0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "无法解析 xsl:call-template 中的名称 AVT。" }, { "ER_REQUIRES_ATTRIB", "“{0}”需要属性：{1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "“{0}”必须有“test”属性。" }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "级别属性上出现错误值：{0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "processing-instruction 名称不能是“xml”" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "processing-instruction 名称必须是有效的 NCName：{0}" }, { "ER_NEED_MATCH_ATTRIB", "“{0}”如果有某种方式，就必须有 match 属性。" }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "“{0}”需要 name 属性或 match 属性。" }, { "ER_CANT_RESOLVE_NSPREFIX", "无法解析名称空间前缀：{0}" }, { "ER_ILLEGAL_VALUE", "xml:space 有非法的值：{0}" }, { "ER_NO_OWNERDOC", "子节点没有所有者文档！" }, { "ER_ELEMTEMPLATEELEM_ERR", "ElemTemplateElement 错误：{0}" }, { "ER_NULL_CHILD", "正在尝试添加空的子代！" }, { "ER_NEED_SELECT_ATTRIB", "{0} 需要 select 属性。" }, { "ER_NEED_TEST_ATTRIB", "xsl:when 必须有“test”属性。" }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param 必须有“name”属性。" }, { "ER_NO_CONTEXT_OWNERDOC", "上下文没有所有者文档！" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "无法创建 XML TransformerFactory 联系：{0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: Process 不成功。" }, { "ER_NOT_SUCCESSFUL", "Xalan: 不成功。" }, { "ER_ENCODING_NOT_SUPPORTED", "不支持编码：{0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "无法创建 TraceListener：{0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key 需要“name”属性！" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key 需要“match”属性！" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key 需要“use”属性！" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} 需要“elements”属性！" }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0} 属性“prefix”丢失" }, { "ER_BAD_STYLESHEET_URL", "样式表 URL 错误：{0}" }, { "ER_FILE_NOT_FOUND", "找不到样式表文件：{0}" }, { "ER_IOEXCEPTION", "样式表文件发生 IO 异常：{0}" }, { "ER_NO_HREF_ATTRIB", "（StylesheetHandler）无法为 {0} 找到 href 属性" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "（StylesheetHandler）{0} 正在直接或间接地包含它自身！" }, { "ER_PROCESSINCLUDE_ERROR", "StylesheetHandler.processInclude 错误，{0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0} 属性“lang”丢失" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "（StylesheetHandler）是否错放了 {0} 元素？？缺少容器元素“component”" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "只能输出到 Element、DocumentFragment、Document 或 PrintWriter。" }, { "ER_PROCESS_ERROR", "StylesheetRoot.process 错误" }, { "ER_UNIMPLNODE_ERROR", "UnImplNode 错误：{0}" }, { "ER_NO_SELECT_EXPRESSION", "错误！找不到 xpath 选择表达式（-select）。" }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "无法序列化 XSLProcessor！" }, { "ER_NO_INPUT_STYLESHEET", "没有指定样式表输入！" }, { "ER_FAILED_PROCESS_STYLESHEET", "无法处理样式表！" }, { "ER_COULDNT_PARSE_DOC", "无法解析 {0} 文档！" }, { "ER_COULDNT_FIND_FRAGMENT", "找不到片段：{0}" }, { "ER_NODE_NOT_ELEMENT", "片段标识指向的节点不是元素：{0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each 必须有 match 或 name 属性" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "templates 必须有 match 或 name 属性" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "无文档片段的克隆！" }, { "ER_CANT_CREATE_ITEM", "无法在结果树中创建项：{0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "源 XML 中的 xml:space 有非法值：{0}" }, { "ER_NO_XSLKEY_DECLARATION", "{0} 没有 xsl:key 声明！" }, { "ER_CANT_CREATE_URL", "错误！无法为 {0} 创建 URL" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "不支持 xsl:functions" }, { "ER_PROCESSOR_ERROR", "XSLT TransformerFactory 错误" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "（StylesheetHandler）样式表内不允许 {0}！" }, { "ER_RESULTNS_NOT_SUPPORTED", "不再支持 result-ns！请改为使用 xsl:output。" }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "不再支持 default-space！请改为使用 xsl:strip-space 或 xsl:preserve-space。" }, { "ER_INDENTRESULT_NOT_SUPPORTED", "不再支持 indent-result！请改为使用 xsl:output。" }, { "ER_ILLEGAL_ATTRIB", "（StylesheetHandler）{0} 有非法属性：{1}" }, { "ER_UNKNOWN_XSL_ELEM", "未知 XSL 元素：{0}" }, { "ER_BAD_XSLSORT_USE", "（StylesheetHandler）xsl:sort 只能与 xsl:apply-templates 或 xsl:for-each 一起使用。" }, { "ER_MISPLACED_XSLWHEN", "（StylesheetHandler）错放了 xsl:when！" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "（StylesheetHandler）xsl:choose 不是 xsl:when 的父代！" }, { "ER_MISPLACED_XSLOTHERWISE", "（StylesheetHandler）错放了 xsl:otherwise！" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "（StylesheetHandler）xsl:choose 不是 xsl:otherwise 的父代！" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "（StylesheetHandler）模板内不允许出现 {0}！" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "（StylesheetHandler）{0} 扩展名称空间前缀 {1} 未知" }, { "ER_IMPORTS_AS_FIRST_ELEM", "（StylesheetHandler）导入只能作为样式表中最前面的元素发生！" }, { "ER_IMPORTING_ITSELF", "（StylesheetHandler）{0} 正在直接或间接地导入它自身！" }, { "ER_XMLSPACE_ILLEGAL_VAL", "（StylesheetHandler）xml:space 有非法值：{0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet 不成功！" }, { "ER_SAX_EXCEPTION", "SAX 异常" }, { "ER_FUNCTION_NOT_SUPPORTED", "函数不受支持！" }, { "ER_XSLT_ERROR", "XSLT 错误" }, { "ER_CURRENCY_SIGN_ILLEGAL", "格式模式字符串中不允许存在货币符号" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "样式表 DOM 中不支持文档函数！" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "无法解析非前缀解析器的前缀！" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "重定向扩展：无法获取文件名 － file 或 select 属性必须返回有效字符串。" }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "无法在重定向扩展中构建 FormatterListener！" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "exclude-result-prefixes 中的前缀无效：{0}" }, { "ER_MISSING_NS_URI", "指定的前缀缺少名称空间 URI" }, { "ER_MISSING_ARG_FOR_OPTION", "选项 {0} 缺少自变量" }, { "ER_INVALID_OPTION", "选项 {0} 无效" }, { "ER_MALFORMED_FORMAT_STRING", "格式字符串 {0} 的格式错误" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet 需要“version”属性！" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "属性：{0} 有非法的值：{1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose 需要 xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:for-each 中不允许有 xsl:apply-imports" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "无法将 DTMLiaison 用于输出 DOM 节点... 改为传递 org.apache.xpath.DOM2Helper！" }, { "ER_CANT_USE_DTM_FOR_INPUT", "无法将 DTMLiaison 用于输入 DOM 节点... 改为传递 org.apache.xpath.DOM2Helper！" }, { "ER_CALL_TO_EXT_FAILED", "调用扩展元素失败：{0}" }, { "ER_PREFIX_MUST_RESOLVE", "前缀必须解析为名称空间：{0}" }, { "ER_INVALID_UTF16_SURROGATE", "检测到无效的 UTF-16 超大字符集：{0}？" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} 使用了自身，这将导致无限循环。" }, { "ER_CANNOT_MIX_XERCESDOM", "无法将非 Xerces-DOM 输入与 Xerces-DOM 输出混合！" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet ― TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "在 ElemTemplateElement.readObject 中：{0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "找到多个名为 {0} 的模板" }, { "ER_INVALID_KEY_CALL", "无效的函数调用：不允许递归 key() 调用" }, { "ER_REFERENCING_ITSELF", "变量 {0} 正在直接或间接地引用它自身！" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "输入节点对于 newTemplates 的 DOMSource 不能为空！" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "找不到选项 {0} 的类文件" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "找不到必需的元素：{0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream 不能为空" }, { "ER_URI_CANNOT_BE_NULL", "URI 不能为空" }, { "ER_FILE_CANNOT_BE_NULL", "File 不能为空" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource 不能为空" }, { "ER_CANNOT_INIT_BSFMGR", "无法初始化 BSF 管理器" }, { "ER_CANNOT_CMPL_EXTENSN", "无法编译扩展" }, { "ER_CANNOT_CREATE_EXTENSN", "由于 {1}，无法创建扩展 {0}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "对方法 {0} 的实例方法调用要求以对象实例作为第一参数" }, { "ER_INVALID_ELEMENT_NAME", "指定了无效的元素名称 {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "元素名称方法必须是 static {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "扩展函数 {0} : {1} 未知" }, { "ER_MORE_MATCH_CONSTRUCTOR", "对于 {0}，构造函数有多个最佳匹配" }, { "ER_MORE_MATCH_METHOD", "方法 {0} 有多个最佳匹配" }, { "ER_MORE_MATCH_ELEMENT", "element 方法 {0} 有多个最佳匹配" }, { "ER_INVALID_CONTEXT_PASSED", "评估 {0} 时传递了无效的上下文" }, { "ER_POOL_EXISTS", "池已经存在" }, { "ER_NO_DRIVER_NAME", "未指定驱动程序名称" }, { "ER_NO_URL", "未指定 URL" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "池大小小于 1！" }, { "ER_INVALID_DRIVER", "指定了无效的驱动程序名称！" }, { "ER_NO_STYLESHEETROOT", "找不到样式表根目录！" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "xml:space 的值非法" }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode 失败" }, { "ER_RESOURCE_COULD_NOT_LOAD", "资源 [ {0} ] 无法装入：{1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "缓冲区大小 <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "调用扩展时发生未知错误" }, { "ER_NO_NAMESPACE_DECL", "前缀 {0} 没有相应的名称空间声明" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "lang=javaclass {0} 不允许出现元素内容" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "样式表定向的终止" }, { "ER_ONE_OR_TWO", "1 或 2" }, { "ER_TWO_OR_THREE", "2 或 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "无法装入 {0}（检查 CLASSPATH），现在只使用缺省值" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "无法初始化缺省模板" }, { "ER_RESULT_NULL", "结果不应为空" }, { "ER_RESULT_COULD_NOT_BE_SET", "无法设置结果" }, { "ER_NO_OUTPUT_SPECIFIED", "未指定输出" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "无法转换成类型为 {0} 的结果" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "无法转换类型为 {0} 的源" }, { "ER_NULL_CONTENT_HANDLER", "内容处理程序为空" }, { "ER_NULL_ERROR_HANDLER", "错误处理程序为空" }, { "ER_CANNOT_CALL_PARSE", "如果没有设置 ContentHandler，则无法调用解析" }, { "ER_NO_PARENT_FOR_FILTER", "过滤器无父代" }, { "ER_NO_STYLESHEET_IN_MEDIA", "在 {0} 中找不到样式表，介质 = {1}" }, { "ER_NO_STYLESHEET_PI", "在 {0} 中找不到 xml-stylesheet PI" }, { "ER_NOT_SUPPORTED", "不支持：{0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "特性 {0} 的值应当是布尔实例" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "找不到 {0} 上的外部脚本" }, { "ER_RESOURCE_COULD_NOT_FIND", "找不到资源 [ {0} ]。\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "未识别出输出属性：{0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "创建 ElemLiteralResult 实例失败" }, { "ER_VALUE_SHOULD_BE_NUMBER", "{0} 的值应当包含可进行解析的数字" }, { "ER_VALUE_SHOULD_EQUAL", "{0} 的值应当等于 yes 或 no" }, { "ER_FAILED_CALLING_METHOD", "调用 {0} 方法失败" }, { "ER_FAILED_CREATING_ELEMTMPL", "创建 ElemTemplateElement 实例失败" }, { "ER_CHARS_NOT_ALLOWED", "此时文档中不允许有字符" }, { "ER_ATTR_NOT_ALLOWED", "{1} 元素上不允许使用“{0}”属性！" }, { "ER_BAD_VALUE", "{0} 错误值 {1}" }, { "ER_ATTRIB_VALUE_NOT_FOUND", "找不到 {0} 属性值" }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "未识别出 {0} 属性值" }, { "ER_NULL_URI_NAMESPACE", "正在试图以空的 URI 生成名称空间前缀" }, { "ER_NUMBER_TOO_BIG", "正在试图格式化大于最大长整数的数字" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "找不到 SAX1 驱动程序类 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "找到了 SAX1 驱动程序类 {0}，但无法装入它" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "装入了 SAX1 驱动程序类 {0}，但无法将它实例化" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "SAX1 驱动程序类 {0} 不实现 org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "没有指定系统属性 org.xml.sax.parser" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "解析器参数不得为空" }, { "ER_FEATURE", "特征：{0}" }, { "ER_PROPERTY", "属性：{0}" }, { "ER_NULL_ENTITY_RESOLVER", "实体解析器为空" }, { "ER_NULL_DTD_HANDLER", "DTD 处理程序为空" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "未指定驱动程序名称！" }, { "ER_NO_URL_SPECIFIED", "未指定 URL！" }, { "ER_POOLSIZE_LESS_THAN_ONE", "池大小小于 1！" }, { "ER_INVALID_DRIVER_NAME", "指定了无效的驱动程序名称！" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "程序员的错误！表达式没有 ElemTemplateElement 父代！" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "程序员在 RedundentExprEliminator 中的断言：{0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "样式表的此位置中不允许有 {0}！" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "样式表的此位置中不允许有非空格的文本！" }, { "INVALID_TCHAR", "用于 CHAR 属性 {0} 的值 {1} 非法。类型 CHAR 的属性必须只有 1 个字符！" }, { "INVALID_QNAME", "用于 QNAME 属性 {0} 的值 {1} 非法" }, { "INVALID_ENUM", "用于 ENUM 属性 {0} 的值 {1} 非法。有效的值是：{2}。" }, { "INVALID_NMTOKEN", "用于 NMTOKEN 属性 {0} 的值 {1} 非法" }, { "INVALID_NCNAME", "用于 NCNAME 属性 {0} 的值 {1} 非法" }, { "INVALID_BOOLEAN", "用于 boolean 属性 {0} 的值 {1} 非法" }, { "INVALID_NUMBER", "用于 number 属性 {0} 的值 {1} 非法" }, { "ER_ARG_LITERAL", "匹配模式中 {0} 的自变量必须是文字。" }, { "ER_DUPLICATE_GLOBAL_VAR", "全局变量的声明重复。" }, { "ER_DUPLICATE_VAR", "变量声明重复。" }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template 必须有一个 name 或 match 属性（或两者兼有）" }, { "ER_INVALID_PREFIX", "exclude-result-prefixes 中的前缀无效：{0}" }, { "ER_NO_ATTRIB_SET", "名为 {0} 的属性集不存在" }, { "ER_FUNCTION_NOT_FOUND", "名为 {0} 的函数不存在" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "{0} 元素不得同时具有内容和 select 属性。" }, { "ER_INVALID_SET_PARAM_VALUE", "参数 {0} 的值必须为有效的 Java 对象" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "xsl:namespace-alias 元素的 result-prefix 属性含有“#default”值，但在该元素的作用域中没有缺省名称空间的声明。" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "xsl:namespace-alias 元素的 result-prefix 属性含有“{0}”值，但是在该元素的作用域中没有前缀“{0}”的名称空间声明。" }, { "ER_SET_FEATURE_NULL_NAME", "在 TransformerFactory.setFeature(String name, boolean value) 中特征名不能为空。" }, { "ER_GET_FEATURE_NULL_NAME", "在 TransformerFactory.getFeature(String name) 中特征名不能为空。" }, { "ER_UNSUPPORTED_FEATURE", "无法对此 TransformerFactory 设置特征“{0}”。" }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "当安全处理功能设置为 true 时，不允许使用扩展元素“{0}”。" }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "无法为空名称空间 uri 获取前缀。" }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "无法为空前缀获取名称空间 uri。" }, { "ER_XPATH_RESOLVER_NULL_QNAME", "函数名不能为空。" }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "数量不能为负。" }, { "WG_FOUND_CURLYBRACE", "找到“}”，但没有打开属性模板！" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "警告：count 属性与 xsl:number 中的上级不匹配！目标 = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "旧语法：“expr”属性的名称已经更改为“select”。" }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan 在 format-number 函数中尚未处理语言环境名。" }, { "WG_LOCALE_NOT_FOUND", "警告：找不到 xml:lang={0} 的语言环境" }, { "WG_CANNOT_MAKE_URL_FROM", "无法从 {0} 生成 URL" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "无法装入请求的文档：{0}" }, { "WG_CANNOT_FIND_COLLATOR", "找不到 <sort xml:lang={0} 的整理器" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "旧语法：函数指令应当使用 {0} 的 URL" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "不支持编码：{0}，正在使用 UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "不支持编码：{0}，正在使用 Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "发现特性冲突：将使用样式表中最后找到的 {0}。" }, { "WG_PARSING_AND_PREPARING", "========= 解析和准备 {0} ==========" }, { "WG_ATTR_TEMPLATE", "属性模板，{0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "xsl:strip-space 和 xsl:preserve-space 之间的匹配冲突" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan 尚未处理 {0} 属性！" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "找不到十进制格式的声明：{0}" }, { "WG_OLD_XSLT_NS", "XSLT 名称空间丢失或不正确。" }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "只允许一个缺省的 xsl:decimal-format 声明。" }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "xsl:decimal-format 名称必须是唯一的。名称“{0}”有重复。" }, { "WG_ILLEGAL_ATTRIBUTE", "{0} 有一个非法属性：{1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "无法解析名称空间前缀：{0}。将忽略该节点。" }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet 需要“version”属性！" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "非法属性名称：{0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "用于属性 {0} 的值非法：{1}" }, { "WG_EMPTY_SECOND_ARG", "从文档函数的第二参数产生的节点集是空的。返回一个空节点集。" }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "xsl:processing-instruction 名称的“name”属性的值不得为“xml”" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "xsl:processing-instruction 的“name”属性的值必须是有效的 NCName：{0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "在生成子节点之后或在生成元素之前无法添加属性 {0}。将忽略属性。" }, { "NO_MODIFICATION_ALLOWED_ERR", "试图修改不允许修改的对象。" }, { "ui_language", "zh" }, { "help_language", "zh" }, { "language", "zh" }, { "BAD_CODE", "createMessage 的参数超出范围" }, { "FORMAT_FAILED", "在 messageFormat 调用过程中抛出了异常" }, { "version", ">>>>>>> Xalan 版本" }, { "version2", "<<<<<<<" }, { "yes", "是" }, { "line", "行号" }, { "column", "列号" }, { "xsldone", "XSLProcessor：完成" }, { "xslProc_option", "Xalan-J 命令行 Process 类选项：" }, { "xslProc_option", "Xalan-J 命令行 Process 类选项：" }, { "xslProc_invalid_xsltc_option", "在 XSLTC 方式中，不支持选项 {0}。" }, { "xslProc_invalid_xalan_option", "选项 {0} 只能与 -XSLTC 一起使用。" }, { "xslProc_no_input", "错误：没有指定样式表或输入 xml。不带任何选项运行此命令，以了解使用说明。" }, { "xslProc_common_options", "－ 常用选项 －" }, { "xslProc_xalan_options", "― Xalan 选项 ―" }, { "xslProc_xsltc_options", "― XSLTC 选项 ―" }, { "xslProc_return_to_continue", "（请按 <return> 键继续）" }, { "optionXSLTC", "   [-XSLTC （使用 XSLTC 转换）]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "[-XSL XSLTransformationURL]" }, { "optionOUT", "[-OUT outputFileName]" }, { "optionLXCIN", "[-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "[-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "   [-PARSER fully qualified class name of parser liaison]" }, { "optionE", "[-E （不要展开实体引用）]" }, { "optionV", "[-E （不要展开实体引用）]" }, { "optionQC", "[-QC （静默模式冲突警告）]" }, { "optionQ", "[-Q （静默方式）]" }, { "optionLF", "[-LF （仅在输出时使用换行 {缺省值是 CR/LF}）]" }, { "optionCR", "[-CR （仅在输出时使用回车符 {缺省值是 CR/LF}）]" }, { "optionESCAPE", "[-ESCAPE （设置转义字符 {缺省值是 <>&\"'\\r\\n}）]" }, { "optionINDENT", "[-INDENT （控制缩进多少空格 {缺省值是 0}）]" }, { "optionTT", "[-TT （在模板被调用时跟踪模板。）]" }, { "optionTG", "[-TG （跟踪每一个生成事件。）]" }, { "optionTS", "[-TS （跟踪每一个选择事件。）]" }, { "optionTTC", "[-TTC （在子模板被处理时对其进行跟踪。）]" }, { "optionTCLASS", "[-TCLASS （跟踪扩展的 TraceListener 类。）]" }, { "optionVALIDATE", "[-VALIDATE （设置是否进行验证。缺省时验证是关闭的。）]" }, { "optionEDUMP", "[-EDUMP {可选文件名} （发生错误时堆栈转储。）]" }, { "optionXML", "[-XML （使用 XML 格式化程序并添加 XML 头。）]" }, { "optionTEXT", "[-TEXT （使用简单文本格式化程序。）]" }, { "optionHTML", "[-HTML （使用 HTML 格式化程序）]" }, { "optionPARAM", "[-PARAM name expression （设置样识表参数）]" }, { "noParsermsg1", "XSL 处理不成功。" }, { "noParsermsg2", "** 找不到解析器 **" }, { "noParsermsg3", "请检查您的类路径。" }, { "noParsermsg4", "如果没有 IBM 的 XML Parser for Java，您可以从以下位置下载它：" }, { "noParsermsg5", "IBM 的 AlphaWorks：http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "[-URIRESOLVER full class name （使用 URIResolver 解析 URI）]" }, { "optionENTITYRESOLVER", "[-ENTITYRESOLVER full class name （使用 EntityResolver 解析实体）]" }, { "optionCONTENTHANDLER", "[-CONTENTHANDLER full class name （使用 ContentHandler 串行化输出）]" }, { "optionLINENUMBERS", "[-L use line numbers for source document]" }, { "optionSECUREPROCESSING", "   [-SECURE （将安全处理功能设置为 true。）]" }, { "optionMEDIA", "   [-MEDIA mediaType （使用 media 属性查找与文档关联的样式表。）]" }, { "optionFLAVOR", "   [-FLAVOR flavorName （显式使用 s2s=SAX 或 d2d=DOM 进行转换。）]" }, { "optionDIAG", "[-DIAG （打印全部毫秒转换标记。）]" }, { "optionINCREMENTAL", "   [-INCREMENTAL （通过将 http://xml.apache.org/xalan/features/incremental 设置为 true 请求增量 DTM 构造。）]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE （通过将 http://xml.apache.org/xalan/features/optimize 设置为 false 请求无样式表的优化处理。）]" }, { "optionRL", "   [-RL recursionlimit （断言样式表递归深度的数字极限。）]" }, { "optionXO", "[-XO [transletName] （断言生成的 translet 的名称）]" }, { "optionXD", "[-XD destinationDirectory （指定 translet 的目标目录）]" }, { "optionXJ", "[-XJ jarfile （将 translet 类打包成名称为 <jarfile> 的 jar 文件）]" }, { "optionXP", "[-XP package （指出所有生成的 translet 类的软件包名称前缀）]" }, { "optionXN", "[-XN （启用模板代码嵌入）]" }, { "optionXX", "[-XX （打开附加调试消息输出）]" }, { "optionXT", "[-XT （可能的话使用 translet 进行转换）]" }, { "diagTiming", "--------- {0} 通过 {1} 的转换耗时 {2} 毫秒" }, { "recursionTooDeep", "模板嵌套太深。嵌套 = {0}，模板 {1} {2}" }, { "nameIs", "名称为" }, { "matchPatternIs", "匹配模式为" } };
/*  276:     */   }
/*  277:     */   
/*  278:     */   public static final XSLTErrorResources loadResourceBundle(String className)
/*  279:     */     throws MissingResourceException
/*  280:     */   {
/*  281:1478 */     Locale locale = Locale.getDefault();
/*  282:1479 */     String suffix = getResourceSuffix(locale);
/*  283:     */     try
/*  284:     */     {
/*  285:1485 */       return (XSLTErrorResources)ResourceBundle.getBundle(className + suffix, locale);
/*  286:     */     }
/*  287:     */     catch (MissingResourceException e)
/*  288:     */     {
/*  289:     */       try
/*  290:     */       {
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("zh", "CN"));
/*  292:     */       }
/*  293:     */       catch (MissingResourceException e2)
/*  294:     */       {
/*  295:1503 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  296:     */       }
/*  297:     */     }
/*  298:     */   }
/*  299:     */   
/*  300:     */   private static final String getResourceSuffix(Locale locale)
/*  301:     */   {
/*  302:1520 */     String suffix = "_" + locale.getLanguage();
/*  303:1521 */     String country = locale.getCountry();
/*  304:1523 */     if (country.equals("TW")) {
/*  305:1524 */       suffix = suffix + "_" + country;
/*  306:     */     }
/*  307:1526 */     return suffix;
/*  308:     */   }
/*  309:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_zh
 * JD-Core Version:    0.7.0.1
 */