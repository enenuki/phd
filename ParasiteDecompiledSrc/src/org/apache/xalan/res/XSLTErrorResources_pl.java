/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_pl
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
/*  263:     */   public static final String ERROR_STRING = "nr błędu";
/*  264:     */   public static final String ERROR_HEADER = "Błąd: ";
/*  265:     */   public static final String WARNING_HEADER = "Ostrzeżenie: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "WZORZEC ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Błąd: Wewnątrz wyrażenia nie może być znaku '{'" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} ma niedozwolony atrybut {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode jest puste w xsl:apply-imports!" }, { "ER_CANNOT_ADD", "Nie można dodać {0} do {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode jest puste w handleApplyTemplatesInstruction!" }, { "ER_NO_NAME_ATTRIB", "{0} musi mieć atrybut name." }, { "ER_TEMPLATE_NOT_FOUND", "Nie można znaleźć szablonu o nazwie {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Nie można przetłumaczyć AVT nazwy na xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "{0} wymaga atrybutu: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} musi mieć atrybut ''test''." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Błędna wartość w atrybucie level: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Nazwą instrukcji przetwarzania nie może być 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Nazwa instrukcji przetwarzania musi być poprawną nazwą NCName {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} musi mieć atrybut match, jeśli ma mode." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} wymaga albo atrybutu name, albo match." }, { "ER_CANT_RESOLVE_NSPREFIX", "Nie można rozstrzygnąć przedrostka przestrzeni nazw {0}" }, { "ER_ILLEGAL_VALUE", "xml:space ma niepoprawną wartość {0}" }, { "ER_NO_OWNERDOC", "Bezpośredni węzeł potomny nie ma dokumentu właściciela!" }, { "ER_ELEMTEMPLATEELEM_ERR", "Błąd ElemTemplateElement: {0}" }, { "ER_NULL_CHILD", "Próba dodania pustego bezpośredniego elementu potomnego!" }, { "ER_NEED_SELECT_ATTRIB", "{0} wymaga atrybutu select." }, { "ER_NEED_TEST_ATTRIB", "xsl:when musi mieć atrybut 'test'." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param musi mieć atrybut 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "Kontekst nie ma dokumentu właściciela!" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Nie można utworzyć połączenia XML TransformerFactory: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Proces Xalan nie wykonał się pomyślnie." }, { "ER_NOT_SUCCESSFUL", "Xalan nie wykonał się pomyślnie." }, { "ER_ENCODING_NOT_SUPPORTED", "Nieobsługiwane kodowanie {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Nie można utworzyć TraceListener: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key wymaga atrybutu 'name'." }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key wymaga atrybutu 'match'." }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key wymaga atrybutu 'use'." }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} wymaga atrybutu ''elements''!" }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0} brakuje atrybutu ''prefix''" }, { "ER_BAD_STYLESHEET_URL", "Adres URL arkusza stylów jest błędny {0}" }, { "ER_FILE_NOT_FOUND", "Nie znaleziono pliku arkusza stylów {0}" }, { "ER_IOEXCEPTION", "Wystąpił wyjątek we/wy w pliku arkusza stylów {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Nie można znaleźć atrybutu href dla {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} zawiera siebie bezpośrednio lub pośrednio!" }, { "ER_PROCESSINCLUDE_ERROR", "Błąd StylesheetHandler.processInclude {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0} brakuje atrybutu ''lang''" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) źle umieszczony element {0}?? Brakuje elementu kontenera ''component''" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Można wyprowadzać dane tylko do Element, DocumentFragment, Document lub PrintWriter." }, { "ER_PROCESS_ERROR", "Błąd StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Błąd UnImplNode: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Błąd! Nie znaleziono wyrażenia wyboru xpath (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Nie można szeregować XSLProcessor!" }, { "ER_NO_INPUT_STYLESHEET", "Nie podano danych wejściowych do arkusza stylów!" }, { "ER_FAILED_PROCESS_STYLESHEET", "Nie powiodło się przetworzenie arkusza stylów!" }, { "ER_COULDNT_PARSE_DOC", "Nie można zanalizować dokumentu {0}!" }, { "ER_COULDNT_FIND_FRAGMENT", "Nie można znaleźć fragmentu {0}" }, { "ER_NODE_NOT_ELEMENT", "Węzeł wskazywany przez identyfikator fragmentu nie był elementem {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each musi mieć albo atrybut match, albo name" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "templates musi mieć albo atrybut match, albo name" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Brak klonu fragmentu dokumentu!" }, { "ER_CANT_CREATE_ITEM", "Nie można utworzyć elementu w wynikowym drzewie {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space w źródłowym pliku XML ma niepoprawną wartość {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Nie ma deklaracji xsl:key dla {0}!" }, { "ER_CANT_CREATE_URL", "Błąd! Nie można utworzyć adresu url dla {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions jest nieobsługiwane" }, { "ER_PROCESSOR_ERROR", "Błąd XSLT TransformerFactory" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} jest niedozwolone wewnątrz arkusza stylów!" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns nie jest już obsługiwane!  Użyj zamiast tego xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space nie jest już obsługiwane!  Użyj zamiast tego xsl:strip-space lub xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result nie jest już obsługiwane!  Użyj zamiast tego xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} ma niedozwolony atrybut {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Nieznany element XSL {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort może być używane tylko z xsl:apply-templates lub xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) błędnie umieszczone xsl:when!" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when bez nadrzędnego xsl:choose!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) błędnie umieszczone xsl:otherwise!" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise bez nadrzędnego xsl:choose!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} jest niedozwolone wewnątrz szablonu!" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) Nieznany przedrostek {1} rozszerzenia {0} przestrzeni nazw" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Importy mogą wystąpić tylko jako pierwsze elementy w arkuszu stylów!" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} importuje siebie bezpośrednio lub pośrednio!" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space ma niedozwoloną wartość: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet było niepomyślne!" }, { "ER_SAX_EXCEPTION", "Wyjątek SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Nieobsługiwana funkcja!" }, { "ER_XSLT_ERROR", "Błąd XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "Znak waluty jest niedozwolony w ciągu znaków wzorca formatu" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "Funkcja Document nie jest obsługiwana w arkuszu stylów DOM!" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Nie można rozstrzygnąć przedrostka przelicznika bez przedrostka!" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Rozszerzenie Redirect: Nie można pobrać nazwy pliku - atrybut file lub select musi zwrócić poprawny ciąg znaków." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Nie można zbudować FormatterListener w rozszerzeniu Redirect!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Przedrostek w exclude-result-prefixes jest niepoprawny: {0}" }, { "ER_MISSING_NS_URI", "Nieobecny identyfikator URI przestrzeni nazw w podanym przedrostku" }, { "ER_MISSING_ARG_FOR_OPTION", "Nieobecny argument opcji {0}" }, { "ER_INVALID_OPTION", "Niepoprawna opcja {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Zniekształcony ciąg znaków formatu {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet wymaga atrybutu 'version'!" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "Atrybut {0} ma niepoprawną wartość {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose wymaga xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports jest niedozwolone w xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Nie można użyć DTMLiaison w wyjściowym węźle DOM... przekaż zamiast tego org.apache.xpath.DOM2Helper!" }, { "ER_CANT_USE_DTM_FOR_INPUT", "Nie można użyć DTMLiaison w wejściowym węźle DOM... przekaż zamiast tego org.apache.xpath.DOM2Helper!" }, { "ER_CALL_TO_EXT_FAILED", "Wywołanie elementu rozszerzenia nie powiodło się: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "Przedrostek musi dać się przetłumaczyć na przestrzeń nazw: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Wykryto niepoprawny odpowiednik UTF-16: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} użyło siebie, co wywoła nieskończoną pętlę." }, { "ER_CANNOT_MIX_XERCESDOM", "Nie można mieszać wejścia innego niż Xerces-DOM z wyjściem Xerces-DOM!" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "W ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Znaleziono więcej niż jeden szablon o nazwie {0}" }, { "ER_INVALID_KEY_CALL", "Niepoprawne wywołanie funkcji: Rekurencyjne wywołania key() są niedozwolone" }, { "ER_REFERENCING_ITSELF", "Zmienna {0} odwołuje się do siebie bezpośrednio lub pośrednio!" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "Węzeł wejściowy nie może być pusty dla DOMSource dla newTemplates!" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Nie znaleziono pliku klasy dla opcji {0}" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Nie znaleziono wymaganego elementu {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream nie może być pusty" }, { "ER_URI_CANNOT_BE_NULL", "Identyfikator URI nie może być pusty" }, { "ER_FILE_CANNOT_BE_NULL", "File nie może być pusty" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource nie może być pusty" }, { "ER_CANNOT_INIT_BSFMGR", "Nie można zainicjować menedżera BSF" }, { "ER_CANNOT_CMPL_EXTENSN", "Nie można skompilować rozszerzenia" }, { "ER_CANNOT_CREATE_EXTENSN", "Nie można utworzyć rozszerzenia {0} z powodu  {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "Wywołanie metody Instance do metody {0} wymaga instancji Object jako pierwszego argumentu" }, { "ER_INVALID_ELEMENT_NAME", "Podano niepoprawną nazwę elementu {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "Metoda nazwy elementu musi być statyczna {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "Funkcja rozszerzenia {0} : {1} jest nieznana" }, { "ER_MORE_MATCH_CONSTRUCTOR", "Więcej niż jedno najlepsze dopasowanie dla konstruktora {0}" }, { "ER_MORE_MATCH_METHOD", "Więcej niż jedno najlepsze dopasowanie dla metody {0}" }, { "ER_MORE_MATCH_ELEMENT", "Więcej niż jedno najlepsze dopasowanie dla metody elementu {0}" }, { "ER_INVALID_CONTEXT_PASSED", "Przekazano niepoprawny kontekst do wyliczenia {0}" }, { "ER_POOL_EXISTS", "Pula już istnieje" }, { "ER_NO_DRIVER_NAME", "Nie podano nazwy sterownika" }, { "ER_NO_URL", "Nie podano adresu URL" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "Wielkość puli jest mniejsza od jedności!" }, { "ER_INVALID_DRIVER", "Podano niepoprawną nazwę sterownika!" }, { "ER_NO_STYLESHEETROOT", "Nie znaleziono elementu głównego arkusza stylów!" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Niedozwolona wartość xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode nie powiodło się" }, { "ER_RESOURCE_COULD_NOT_LOAD", "Zasób [ {0} ] nie mógł załadować: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Wielkość buforu <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Nieznany błąd podczas wywoływania rozszerzenia" }, { "ER_NO_NAMESPACE_DECL", "Przedrostek {0} nie ma odpowiadającej mu deklaracji przestrzeni nazw" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Zawartość elementu niedozwolona dla lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Arkusz stylów zarządził zakończenie" }, { "ER_ONE_OR_TWO", "1 lub 2" }, { "ER_TWO_OR_THREE", "2 lub 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Nie można załadować {0} (sprawdź CLASSPATH), używane są teraz wartości domyślne" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Nie można zainicjować domyślnych szablonów" }, { "ER_RESULT_NULL", "Rezultat nie powinien być pusty" }, { "ER_RESULT_COULD_NOT_BE_SET", "Nie można ustawić rezultatu" }, { "ER_NO_OUTPUT_SPECIFIED", "Nie podano wyjścia" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Nie można przekształcić do rezultatu o typie {0}" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Nie można przekształcić źródła o typie {0}" }, { "ER_NULL_CONTENT_HANDLER", "Pusta procedura obsługi zawartości" }, { "ER_NULL_ERROR_HANDLER", "Pusta procedura obsługi błędu" }, { "ER_CANNOT_CALL_PARSE", "Nie można wywołać parse, jeśli nie ustawiono ContentHandler" }, { "ER_NO_PARENT_FOR_FILTER", "Brak elementu nadrzędnego dla filtru" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Nie znaleziono arkusza stylów w {0}, nośnik= {1}" }, { "ER_NO_STYLESHEET_PI", "Nie znaleziono instrukcji przetwarzania xml-stylesheet w {0}" }, { "ER_NOT_SUPPORTED", "Nieobsługiwane: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "Wartość właściwości {0} powinna być instancją typu Boolean" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Nie można się dostać do zewnętrznego skryptu w {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "Nie można znaleźć zasobu [ {0} ].\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Nierozpoznana właściwość wyjściowa {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Nie powiodło się utworzenie instancji ElemLiteralResult" }, { "ER_VALUE_SHOULD_BE_NUMBER", "Wartość {0} powinna zawierać liczbę możliwą do zanalizowania" }, { "ER_VALUE_SHOULD_EQUAL", "Wartością {0} powinno być yes lub no" }, { "ER_FAILED_CALLING_METHOD", "Niepowodzenie wywołania metody {0}" }, { "ER_FAILED_CREATING_ELEMTMPL", "Nie powiodło się utworzenie instancji ElemTemplateElement" }, { "ER_CHARS_NOT_ALLOWED", "W tym miejscu dokumentu znaki są niedozwolone" }, { "ER_ATTR_NOT_ALLOWED", "Atrybut \"{0}\" nie jest dozwolony w elemencie {1}!" }, { "ER_BAD_VALUE", "Błędna wartość {0} {1}" }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Nie znaleziono wartości atrybutu {0}" }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Nie rozpoznano wartości atrybutu {0}" }, { "ER_NULL_URI_NAMESPACE", "Próba wygenerowania przedrostka przestrzeni nazw z pustym identyfikatorem URI" }, { "ER_NUMBER_TOO_BIG", "Próba sformatowania liczby większej niż największa liczba typu long integer" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Nie można znaleźć klasy sterownika SAX1 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "Znaleziono klasę sterownika SAX1 {0}, ale nie można jej załadować" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "Klasa sterownika SAX1 {0} została załadowana, ale nie można utworzyć jej instancji" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "Klasa sterownika SAX1 {0} nie implementuje org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Właściwość systemowa org.xml.sax.parser nie została podana" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "Argument analizatora nie może być pusty" }, { "ER_FEATURE", "Opcja: {0}" }, { "ER_PROPERTY", "Właściwość: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Pusty przelicznik encji" }, { "ER_NULL_DTD_HANDLER", "Pusta procedura obsługi DTD" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Nie podano nazwy sterownika!" }, { "ER_NO_URL_SPECIFIED", "Nie podano adresu URL!" }, { "ER_POOLSIZE_LESS_THAN_ONE", "Wielkość puli jest mniejsza od 1!" }, { "ER_INVALID_DRIVER_NAME", "Podano niepoprawną nazwę sterownika!" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Błąd programisty! Wyrażenie nie ma elementu nadrzędnego ElemTemplateElement!" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Asercja programisty w RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} jest niedozwolone na tej pozycji w arkuszu stylów!" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Tekst złożony ze znaków innych niż odstępy jest niedozwolony na tej pozycji w arkuszu stylów!" }, { "INVALID_TCHAR", "Niedozwolona wartość {1} użyta w atrybucie CHAR {0}.  Atrybut typu CHAR musi być pojedynczym znakiem!" }, { "INVALID_QNAME", "Niedozwolona wartość {1} użyta w atrybucie QNAME {0}" }, { "INVALID_ENUM", "Niedozwolona wartość {1} użyta w atrybucie ENUM {0}.  Poprawne wartości to: {2}." }, { "INVALID_NMTOKEN", "Niedozwolona wartość {1} użyta w atrybucie NMTOKEN {0}" }, { "INVALID_NCNAME", "Niedozwolona wartość {1} użyta w atrybucie NCNAME {0}" }, { "INVALID_BOOLEAN", "Niedozwolona wartość {1} użyta w atrybucie logicznym {0}" }, { "INVALID_NUMBER", "Niedozwolona wartość {1} użyta w atrybucie liczbowym {0}" }, { "ER_ARG_LITERAL", "Argument opcji {0} we wzorcu uzgadniania musi być literałem." }, { "ER_DUPLICATE_GLOBAL_VAR", "Zduplikowana deklaracja zmiennej globalnej." }, { "ER_DUPLICATE_VAR", "Zduplikowana deklaracja zmiennej." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template musi mieć atrybut name lub match (lub obydwa)" }, { "ER_INVALID_PREFIX", "Przedrostek w exclude-result-prefixes jest niepoprawny: {0}" }, { "ER_NO_ATTRIB_SET", "Zbiór atrybutów o nazwie {0} nie istnieje" }, { "ER_FUNCTION_NOT_FOUND", "Funkcja o nazwie {0} nie istnieje" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "Element {0} nie może mieć jednocześnie zawartości i atrybutu select." }, { "ER_INVALID_SET_PARAM_VALUE", "Wartością parametru {0} musi być poprawny obiekt języka Java." }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "Atrybut result-prefix elementu xsl:namespace-alias ma wartość '#default', ale nie ma deklaracji domyślnej przestrzeni nazw w zasięgu tego elementu." }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "Atrybut result-prefix elementu xsl:namespace-alias ma wartość ''{0}'', ale nie ma deklaracji przestrzeni nazw dla przedrostka ''{0}'' w zasięgu tego elementu." }, { "ER_SET_FEATURE_NULL_NAME", "Nazwa opcji nie może mieć wartości null w TransformerFactory.setFeature(String nazwa, boolean wartość)." }, { "ER_GET_FEATURE_NULL_NAME", "Nazwa opcji nie może mieć wartości null w TransformerFactory.getFeature(String nazwa)." }, { "ER_UNSUPPORTED_FEATURE", "Nie można ustawić opcji ''{0}'' w tej klasie TransformerFactory." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "Użycie elementu rozszerzenia ''{0}'' jest niedozwolone, gdy opcja przetwarzania bezpiecznego jest ustawiona na wartość true." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Nie można pobrać przedrostka dla pustego identyfikatora uri przestrzeni nazw." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Nie można pobrać identyfikatora uri przestrzeni nazw dla pustego przedrostka." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "Nazwa funkcji nie może być pusta (null)." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "Liczba parametrów nie może być ujemna." }, { "WG_FOUND_CURLYBRACE", "Znaleziono znak '}', ale nie jest otwarty żaden szablon atrybutów!" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Ostrzeżenie: Atrybut count nie jest zgodny ze swym przodkiem w xsl:number! Cel = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Stara składnia: Nazwa atrybutu 'expr' została zmieniona na 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan nie obsługuje jeszcze nazwy ustawień narodowych w funkcji format-number." }, { "WG_LOCALE_NOT_FOUND", "Ostrzeżenie: Nie można znaleźć ustawień narodowych dla xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "Nie można utworzyć adresu URL z {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Nie można załadować żądanego dokumentu {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Nie można znaleźć procesu sortującego (Collator) dla <sort xml:lang={0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Stara składnia: Instrukcja functions powinna używać adresu url {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "Kodowanie nieobsługiwane: {0}, używane jest UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "Kodowanie nieobsługiwane: {0}, używane jest Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "Znaleziono konflikty specyfiki {0}, używany będzie ostatni znaleziony w arkuszu stylów." }, { "WG_PARSING_AND_PREPARING", "========= Analizowanie i przygotowywanie {0} ==========" }, { "WG_ATTR_TEMPLATE", "Szablon atrybutu {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Konflikt zgodności pomiędzy xsl:strip-space oraz xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan nie obsługuje jeszcze atrybutu {0}!" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Nie znaleziono deklaracji formatu dziesiętnego {0}" }, { "WG_OLD_XSLT_NS", "Nieobecna lub niepoprawna przestrzeń nazw XSLT." }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Dozwolona jest tylko jedna domyślna deklaracja xsl:decimal-format." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Nazwy xsl:decimal-format muszą być unikalne. Nazwa \"{0}\" została zduplikowana." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} ma niedozwolony atrybut {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Nie można przetłumaczyć przedrostka przestrzeni nazw {0}. Węzeł zostanie zignorowany." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet wymaga atrybutu 'version'!" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Niedozwolona nazwa atrybutu {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Niedozwolona wartość atrybutu {0}: {1}" }, { "WG_EMPTY_SECOND_ARG", "Wynikający z drugiego argumentu funkcji document zestaw węzłów jest pusty. Zwracany jest pusty zestaw węzłów." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Wartością atrybutu 'name' nazwy xsl:processing-instruction nie może być 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Wartością atrybutu ''name'' xsl:processing-instruction musi być poprawna nazwa NCName: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Nie można dodać atrybutu {0} po węzłach potomnych ani przed wyprodukowaniem elementu.  Atrybut zostanie zignorowany." }, { "NO_MODIFICATION_ALLOWED_ERR", "Usiłowano zmodyfikować obiekt, tam gdzie modyfikacje są niedozwolone." }, { "ui_language", "pl" }, { "help_language", "pl" }, { "language", "pl" }, { "BAD_CODE", "Parametr createMessage był spoza zakresu" }, { "FORMAT_FAILED", "Podczas wywołania messageFormat zgłoszony został wyjątek" }, { "version", ">>>>>>> Wersja Xalan " }, { "version2", "<<<<<<<" }, { "yes", "tak" }, { "line", "Nr wiersza: " }, { "column", "Nr kolumny: " }, { "xsldone", "XSLProcessor: gotowe" }, { "xslProc_option", "Opcje wiersza komend klasy Process Xalan-J:" }, { "xslProc_option", "Opcje wiersza komend klasy Process Xalan-J:" }, { "xslProc_invalid_xsltc_option", "Opcja {0} jest nieobsługiwana w trybie XSLTC." }, { "xslProc_invalid_xalan_option", "Opcji {0} można używać tylko razem z -XSLTC." }, { "xslProc_no_input", "Błąd: Nie podano arkusza stylów lub wejściowego pliku xml. Wykonaj tę komendę bez żadnych opcji, aby zapoznać się z informacjami o składni." }, { "xslProc_common_options", "-Wspólne opcje-" }, { "xslProc_xalan_options", "-Opcje dla Xalan-" }, { "xslProc_xsltc_options", "-Opcje dla XSLTC-" }, { "xslProc_return_to_continue", "(naciśnij klawisz <enter>, aby kontynuować)" }, { "optionXSLTC", "[-XSLTC (użycie XSLTC do transformacji)]" }, { "optionIN", "[-IN wejściowyXMLURL]" }, { "optionXSL", "[-XSL URLTransformacjiXSL]" }, { "optionOUT", "[-OUT NazwaPlikuWyjściowego]" }, { "optionLXCIN", "[-LXCIN NazwaWejściowegoPlikuSkompilowanegoArkuszaStylów]" }, { "optionLXCOUT", "[-LXCOUT NazwaWyjściowegoPlikuSkompilowanegoArkuszaStylów]" }, { "optionPARSER", "[-PARSER pełna nazwa klasy połączenia analizatora]" }, { "optionE", "[-E (bez rozwijania odwołań do encji)]" }, { "optionV", "[-E (bez rozwijania odwołań do encji)]" }, { "optionQC", "[-QC (ciche ostrzeżenia o konfliktach wzorców)]" }, { "optionQ", "[-Q  (tryb cichy)]" }, { "optionLF", "[-LF (użycie tylko znaków wysuwu wiersza na wyjściu {domyślnie CR/LF})]" }, { "optionCR", "[-LF (użycie tylko znaków powrotu karetki na wyjściu {domyślnie CR/LF})]" }, { "optionESCAPE", "[-ESCAPE (znaki o zmienionym znaczeniu {domyślne <>&\"'\\r\\n}]" }, { "optionINDENT", "[-INDENT (liczba znaków wcięcia {domyślnie 0})]" }, { "optionTT", "[-TT (śledzenie szablonów podczas ich wywoływania)]" }, { "optionTG", "[-TG (śledzenie każdego zdarzenia generowania)]" }, { "optionTS", "[-TS (śledzenie każdego zdarzenia wyboru)]" }, { "optionTTC", "[-TTC (śledzenie szablonów potomnych podczas ich przetwarzania)]" }, { "optionTCLASS", "[-TCLASS (klasa TraceListener dla rozszerzeń śledzenia)]" }, { "optionVALIDATE", "[-VALIDATE (włączenie sprawdzania poprawności - domyślnie jest wyłączona)]" }, { "optionEDUMP", "[-EDUMP {opcjonalna nazwa pliku} (wykonywanie zrzutu stosu w przypadku wystąpienia błędu)]" }, { "optionXML", "[-XML (użycie formatera XML i dodanie nagłówka XML)]" }, { "optionTEXT", "[-TEXT (użycie prostego formatera tekstu)]" }, { "optionHTML", "[-HTML (użycie formatera HTML)]" }, { "optionPARAM", "[-PARAM nazwa wyrażenie (ustawienie parametru arkusza stylów)]" }, { "noParsermsg1", "Proces XSL nie wykonał się pomyślnie." }, { "noParsermsg2", "** Nie można znaleźć analizatora **" }, { "noParsermsg3", "Sprawdź classpath." }, { "noParsermsg4", "Jeśli nie masz analizatora XML dla języka Java firmy IBM, możesz go pobrać" }, { "noParsermsg5", "z serwisu AlphaWorks firmy IBM: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER pełna nazwa klasy (URIResolver używany do tłumaczenia URI)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER pełna nazwa klasy (EntityResolver używany do tłumaczenia encji)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER pełna nazwa klasy (ContentHandler używany do szeregowania wyjścia)]" }, { "optionLINENUMBERS", "    [-L użycie numerów wierszy w dokumentach źródłowych]" }, { "optionSECUREPROCESSING", "   [-SECURE (ustawienie opcji przetwarzania bezpiecznego na wartość true.)]" }, { "optionMEDIA", "   [-MEDIA typ_nośnika (używaj atrybutu media w celu znalezienia arkusza stylów związanego z dokumentem)]" }, { "optionFLAVOR", "   [-FLAVOR nazwa_posmaku (używaj jawnie s2s=SAX lub d2d=DOM w celu wykonania transformacji)]" }, { "optionDIAG", "   [-DIAG (wyświetlenie całkowitego czasu trwania transformacji)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (żądanie przyrostowego budowania DTM poprzez ustawienie http://xml.apache.org/xalan/features/incremental true.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (żądanie braku optymalizowania arkuszy stylów poprzez ustawienie http://xml.apache.org/xalan/features/optimize false.)]" }, { "optionRL", "   [-RL limit_rekurencji (określenie liczbowego limitu głębokości rekurencji w arkuszach stylów)]" }, { "optionXO", "[-XO [NazwaTransletu] (przypisanie nazwy wygenerowanemu transletowi)]" }, { "optionXD", "[-XD KatalogDocelowy (określenie katalogu docelowego dla transletu)]" }, { "optionXJ", "[-XJ plik_jar (pakowanie klas transletu do pliku jar o nazwie <plik_jar>)]" }, { "optionXP", "[-XP pakiet (określenie przedrostka nazwy pakietu dla wszystkich wygenerowanych klas transletu)]" }, { "optionXN", "[-XN (włączenie wstawiania szablonów)]" }, { "optionXX", "[-XX (włączenie dodatkowych diagnostycznych komunikatów wyjściowych)]" }, { "optionXT", "[-XT (użycie transletu do transformacji, jeśli to możliwe)]" }, { "diagTiming", "--------- Transformacja {0} przez {1} zajęła {2} ms" }, { "recursionTooDeep", "Zbyt głębokie zagnieżdżenie szablonów. zagnieżdżenie= {0}, szablon {1} {2}" }, { "nameIs", "nazwą jest" }, { "matchPatternIs", "wzorcem uzgadniania jest" } };
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
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("pl", "PL"));
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_pl
 * JD-Core Version:    0.7.0.1
 */