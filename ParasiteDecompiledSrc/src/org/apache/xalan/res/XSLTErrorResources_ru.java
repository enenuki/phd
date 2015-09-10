/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_ru
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
/*  263:     */   public static final String ERROR_STRING = "Ошибка";
/*  264:     */   public static final String ERROR_HEADER = "Ошибка: ";
/*  265:     */   public static final String WARNING_HEADER = "Предупреждение: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "PATTERN ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Ошибка: Скобка '{' недопустима в выражении" }, { "ER_ILLEGAL_ATTRIBUTE", "Для {0} указан недопустимый атрибут: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "Пустой sourceNode в xsl:apply-imports!" }, { "ER_CANNOT_ADD", "Не удалось добавить {0} в {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "Пустой sourceNode в handleApplyTemplatesInstruction!" }, { "ER_NO_NAME_ATTRIB", "У {0} должен быть атрибут name" }, { "ER_TEMPLATE_NOT_FOUND", "Указанный шаблон не найден: {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Не удалось преобразовать имя AVT в xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "Для {0} должен быть указан атрибут: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} должен содержать атрибут ''test''. " }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Неверное значение атрибута уровня: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Имя processing-instruction не может быть равно 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Имя processing-instruction должно быть допустимым именем NCName: {0}" }, { "ER_NEED_MATCH_ATTRIB", "Если для {0} определен режим, то необходим атрибут match." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "У {0} должен быть атрибут name или match." }, { "ER_CANT_RESOLVE_NSPREFIX", "Не удалось преобразовать префикс пространства имен: {0}" }, { "ER_ILLEGAL_VALUE", "В xml:space указано недопустимое значение: {0}" }, { "ER_NO_OWNERDOC", "У дочернего узла нет документа-владельца!" }, { "ER_ELEMTEMPLATEELEM_ERR", "Ошибка ElemTemplateElement: {0}" }, { "ER_NULL_CHILD", "Попытка добавить пустого потомка!" }, { "ER_NEED_SELECT_ATTRIB", "У {0} должен быть атрибут select." }, { "ER_NEED_TEST_ATTRIB", "Для xsl:when должен быть задан атрибут 'test'." }, { "ER_NEED_NAME_ATTRIB", "Для xsl:with-param должен быть задан атрибут 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "В контексте отсутствует документ-владелец!" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Невозможно создать XML TransformerFactory Liaison: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: В процессе обнаружены ошибки." }, { "ER_NOT_SUCCESSFUL", "Xalan: Ошибка." }, { "ER_ENCODING_NOT_SUPPORTED", "Кодировка не поддерживается: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Невозможно создать TraceListener: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "Для xsl:key необходим атрибут 'name'!" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "Для xsl:key необходим атрибут 'match'!" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "Для xsl:key необходим атрибут 'use'!" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "Для (StylesheetHandler) {0} требуется атрибут ''elements''!" }, { "ER_MISSING_PREFIX_ATTRIB", "Для (StylesheetHandler) {0} отсутствует атрибут ''prefix''" }, { "ER_BAD_STYLESHEET_URL", "Неверный URL таблицы стилей: {0}" }, { "ER_FILE_NOT_FOUND", "Не найден файл таблицы стилей: {0}" }, { "ER_IOEXCEPTION", "Исключительная ситуация ввода-вывода при обращении к файлу таблицы стилей: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Не найден атрибут href для {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} непосредственно или косвенно ссылается на себя!" }, { "ER_PROCESSINCLUDE_ERROR", "Ошибка StylesheetHandler.processInclude, {0}" }, { "ER_MISSING_LANG_ATTRIB", "Для (StylesheetHandler) {0} отсутствует атрибут ''lang''" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) Неверное положение элемента {0} ?? Отсутствует элемент-контейнер ''component''" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Вывод возможен только в следующие объекты: Element, DocumentFragment, Document или PrintWriter." }, { "ER_PROCESS_ERROR", "Ошибка StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Ошибка UnImplNode: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Ошибка! Не найдено выражение выбора xpath (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Не удалось сериализовать XSLProcessor!" }, { "ER_NO_INPUT_STYLESHEET", "Не указана исходная таблица стилей!" }, { "ER_FAILED_PROCESS_STYLESHEET", "Ошибка при обработке таблицы стилей!" }, { "ER_COULDNT_PARSE_DOC", "Не удалось проанализировать документ {0} !" }, { "ER_COULDNT_FIND_FRAGMENT", "Не найден фрагмент: {0}" }, { "ER_NODE_NOT_ELEMENT", "Узел, на который ссылается идентификатор фрагмента, не является элементом: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "У for-each должен быть атрибут match или name" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "У templates должен быть атрибут match или name" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Отсутствует копия фрагмента документа!" }, { "ER_CANT_CREATE_ITEM", "Не удалось создать элемент дерева результатов: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "Задано недопустимое значение xml:space в исходном XML: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Отсутствует объявление xsl:key для {0}!" }, { "ER_CANT_CREATE_URL", "Ошибка! Не удалось создать URL для {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions не поддерживается" }, { "ER_PROCESSOR_ERROR", "Ошибка XSLT TransformerFactory" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} недопустим в таблице стилей!" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns больше не поддерживается!  Используйте xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space больше не поддерживается!  Используйте xsl:strip-space или xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result больше не поддерживается!  Используйте xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) Для {0} указан недопустимый атрибут: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Неизвестный элемент XSL: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort может применяться только с xsl:apply-templates или xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) Неверное положение xsl:when!" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when не имеет предшествующего xsl:choose!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) Неверное положение xsl:otherwise!" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise не имеет предшествующего xsl:choose!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} недопустим в шаблоне!" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) Неизвестный префикс {1} пространства имен расширения {0}" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Imports допустим только в качестве первого элемента таблицы стилей!" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} непосредственно или косвенно импортирует себя!" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) Для xml:space указано недопустимое значение: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "Ошибка processStylesheet!" }, { "ER_SAX_EXCEPTION", "Исключительная ситуация SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Функция не поддерживается!" }, { "ER_XSLT_ERROR", "Ошибка XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "Символ денежной единицы недопустим в строке форматирования шаблона" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "Функция документа не поддерживается в DOM таблицы стилей!" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Не удалось преобразовать префикс преобразователя non-Prefix!" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Расширение перенаправления: Не удалось получить имя файла - атрибут file или select должен возвращать допустимую строку." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Не удалось создать FormatterListener в расширении перенаправления!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Недопустимый префикс в exclude-result-prefixes: {0}" }, { "ER_MISSING_NS_URI", "Для указанного префикса отсутствует URI пространства имен" }, { "ER_MISSING_ARG_FOR_OPTION", "Отсутствует аргумент опции: {0}" }, { "ER_INVALID_OPTION", "Недопустимая опция: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Неправильная строка форматирования: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "Для xsl:stylesheet необходим атрибут 'version'!" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "Атрибут: В {0} указано недопустимое значение: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "Для xsl:choose необходим xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports не допускается в xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Нельзя применять DTMLiaison для узла вывода DOM ... Используйте org.apache.xpath.DOM2Helper!" }, { "ER_CANT_USE_DTM_FOR_INPUT", "Нельзя применять DTMLiaison для узла ввода DOM ... Используйте org.apache.xpath.DOM2Helper!" }, { "ER_CALL_TO_EXT_FAILED", "Ошибка при вызове элемента расширения: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "Префикс должен обеспечивать преобразование в пространство имен: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Обнаружено недопустимое значение UTF-16: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} применяет себя, что приведет к образованию бесконечного цикла." }, { "ER_CANNOT_MIX_XERCESDOM", "Нельзя одновременно применять ввод не-Xerces-DOM и вывод Xerces-DOM!" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "В ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Обнаружено несколько шаблонов с заданным именем: {0}" }, { "ER_INVALID_KEY_CALL", "Недопустимый вызов функции: рекурсивные вызовы key() недопустимы" }, { "ER_REFERENCING_ITSELF", "Переменная {0} непосредственно или косвенно ссылается на себя!" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "Для DOMSource в newTemplates узел ввода не может быть пустым!" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Не найден файл класса для опции {0}" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Не найден обязательный элемент: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream не может быть пустым" }, { "ER_URI_CANNOT_BE_NULL", "URI не может быть пустым" }, { "ER_FILE_CANNOT_BE_NULL", "Файл не может быть пустым" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource не может быть пустым" }, { "ER_CANNOT_INIT_BSFMGR", "Не удалось инициализировать администратор BSF" }, { "ER_CANNOT_CMPL_EXTENSN", "Не удалось откомпилировать расширение" }, { "ER_CANNOT_CREATE_EXTENSN", "Не удалось создать расширение: {0}, причина: {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "При вызове в экземпляре метода {0} в первом аргументе должен быть передан экземпляр объекта" }, { "ER_INVALID_ELEMENT_NAME", "Указано недопустимое имя элемента {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "Метод name элемента должен быть статическим {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "Неизвестная функция расширения {0} : {1}" }, { "ER_MORE_MATCH_CONSTRUCTOR", "Несколько наилучших соответствий для конструктора {0}" }, { "ER_MORE_MATCH_METHOD", "Несколько лучших соответствий для метода {0}" }, { "ER_MORE_MATCH_ELEMENT", "Несколько лучших соответствий для метода элемента {0}" }, { "ER_INVALID_CONTEXT_PASSED", "Для вычисления передан недопустимый контекст {0}" }, { "ER_POOL_EXISTS", "Пул уже существует" }, { "ER_NO_DRIVER_NAME", "Не указано имя драйвера" }, { "ER_NO_URL", "Не указан URL" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "Размер пула меньше единицы!" }, { "ER_INVALID_DRIVER", "Указано недопустимое имя драйвера!" }, { "ER_NO_STYLESHEETROOT", "Не найден корневой элемент таблицы стилей!" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Недопустимое значение xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "Ошибка processFromNode" }, { "ER_RESOURCE_COULD_NOT_LOAD", "Не удалось загрузить ресурс [ {0} ]: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Размер буфера <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Неизвестная ошибка при вызове расширения" }, { "ER_NO_NAMESPACE_DECL", "У префикса {0} отсутствует объявление соответствующего пространства имен" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Содержимое элемента недопустимо для lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Прервано в соответствии с таблицей стилей" }, { "ER_ONE_OR_TWO", "1 или 2" }, { "ER_TWO_OR_THREE", "2 или 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Не удалось загрузить {0} (проверьте настройку CLASSPATH), применяются значения по умолчанию" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Не удалось инициализировать шаблоны по умолчанию" }, { "ER_RESULT_NULL", "Результат не должен быть пустым" }, { "ER_RESULT_COULD_NOT_BE_SET", "Невозможно задать результат" }, { "ER_NO_OUTPUT_SPECIFIED", "Не указан вывод" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Невозможно преобразовать в Результат типа {0}" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Невозможно преобразовать в Источник типа {0}" }, { "ER_NULL_CONTENT_HANDLER", "Пустой обработчик содержания" }, { "ER_NULL_ERROR_HANDLER", "Пустой обработчик ошибки" }, { "ER_CANNOT_CALL_PARSE", "Невозможно вызвать анализатор, если не задан ContentHandler" }, { "ER_NO_PARENT_FOR_FILTER", "Не задан родительский объект фильтра" }, { "ER_NO_STYLESHEET_IN_MEDIA", "В {0} не найдена таблица стилей, носитель={1}" }, { "ER_NO_STYLESHEET_PI", "Не найден PI xml-stylesheet в {0}" }, { "ER_NOT_SUPPORTED", "Не поддерживается: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "Значение свойства {0} должно быть экземпляром Boolean" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Невозможно получить внешний сценарий в {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "Ресурс [ {0} ] не найден.\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Свойство вывода не распознано: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Не удалось создать элемент ElemLiteralResult" }, { "ER_VALUE_SHOULD_BE_NUMBER", "Значение {0} должно быть анализируемым числом" }, { "ER_VALUE_SHOULD_EQUAL", "В {0} должно быть указано значение yes или no" }, { "ER_FAILED_CALLING_METHOD", "Ошибка при вызове метода {0}" }, { "ER_FAILED_CREATING_ELEMTMPL", "Ошибка при создании экземпляра ElemTemplateElement" }, { "ER_CHARS_NOT_ALLOWED", "Символы недопустимы в данной позиции документа" }, { "ER_ATTR_NOT_ALLOWED", "Атрибут \"{0}\" недопустим в элементе {1}!" }, { "ER_BAD_VALUE", "{0} неверное значение {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Не найдено значение атрибута {0} " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Значение атрибута {0} не распознано " }, { "ER_NULL_URI_NAMESPACE", "Попытка создать префикс пространства имен с пустым URI" }, { "ER_NUMBER_TOO_BIG", "Попытка отформатировать число больше максимально допустимого LongInteger" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Не удалось найти класс драйвера SAX1 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "Класс драйвера SAX1 {0} обнаружен, но его загрузка невозможна" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "Класс драйвера SAX1 {0} загружен, но его инициализация невозможна" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "В классе драйвера SAX1 {0} не реализован org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Не задано системное свойство org.xml.sax.parser" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "Аргумент анализатора не должен быть пустым" }, { "ER_FEATURE", "Функция: {0}" }, { "ER_PROPERTY", "Свойство: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Пустой преобразователь макроса" }, { "ER_NULL_DTD_HANDLER", "Пустой дескриптор DTD" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Не указано имя драйвера!" }, { "ER_NO_URL_SPECIFIED", "Не указан URL!" }, { "ER_POOLSIZE_LESS_THAN_ONE", "Размер пула меньше 1!" }, { "ER_INVALID_DRIVER_NAME", "Указано недопустимое имя драйвера!" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Программная ошибка! У выражения нет родительского элемента ElemTemplateElement!" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Запись программиста в RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} недопустимо в данной позиции таблицы стилей!" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Текст недопустим в данной позиции таблицы стилей!" }, { "INVALID_TCHAR", "Недопустимое значение {1} атрибута CHAR: {0}.  Атрибут типа CHAR должен содержать только 1 символ!" }, { "INVALID_QNAME", "Недопустимое значение {1} атрибута QNAME: {0}" }, { "INVALID_ENUM", "Недопустимое значение {1} атрибута ENUM: {0}.  Допустимые значения: {2}." }, { "INVALID_NMTOKEN", "Недопустимое значение {1} атрибута NMTOKEN: {0}. " }, { "INVALID_NCNAME", "Недопустимое значение {1} атрибута NCNAME: {0}. " }, { "INVALID_BOOLEAN", "Недопустимое значение {1} атрибута boolean: {0}. " }, { "INVALID_NUMBER", "Недопустимое значение {1} атрибута number: {0}. " }, { "ER_ARG_LITERAL", "Аргумент {0} в шаблоне сравнения должен быть литералом." }, { "ER_DUPLICATE_GLOBAL_VAR", "Повторное объявление глобальной переменной." }, { "ER_DUPLICATE_VAR", "Повторное объявление переменной." }, { "ER_TEMPLATE_NAME_MATCH", "Для xsl:template должен быть задан атрибут name или match, либо оба этих атрибута" }, { "ER_INVALID_PREFIX", "Недопустимый префикс в exclude-result-prefixes: {0}" }, { "ER_NO_ATTRIB_SET", "attribute-set с именем {0} не существует" }, { "ER_FUNCTION_NOT_FOUND", "Функция с именем {0} не существует" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "Для элемента {0} нельзя одновременно указывать атрибуты содержания и выбора. " }, { "ER_INVALID_SET_PARAM_VALUE", "Значение параметра {0} должно быть допустимым объектом Java" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "Атрибут result-prefix элемента xsl:namespace-alias содержит значение '#default', но объявление пространства имен по умолчанию в области для данного элемента не найдено" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "Атрибут result-prefix элемента xsl:namespace-alias содержит значение ''{0}'', но объявление пространства имен для префикса ''{0}'' в области данного элемента не найдено. " }, { "ER_SET_FEATURE_NULL_NAME", "Имя функции не может быть пустым в TransformerFactory.setFeature(Имя строки, булевское значение). " }, { "ER_GET_FEATURE_NULL_NAME", "Имя функции не может быть пустым в TransformerFactory.getFeature(Имя строки). " }, { "ER_UNSUPPORTED_FEATURE", "Невозможно задать функцию ''{0}'' в этом классе TransformerFactory. " }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "Применение элемента расширения ''{0}'' недопустимо, если для функции защищенной обработки задано значение true. " }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Не удалось найти префикс для uri пустого пространства имен. " }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Не удалось найти uri пространства имен для пустого префикса. " }, { "ER_XPATH_RESOLVER_NULL_QNAME", "Имя функции не может быть пустым. " }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "Число аргументов не может быть отрицательным. " }, { "WG_FOUND_CURLYBRACE", "Найдена закрывающая скобка '}', но открытые шаблоны атрибутов отсутствуют!" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Предупреждение: Атрибут count не соответствует родительскому в xsl:number! Целевой = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Старый синтаксис: Имя атрибута 'expr' изменено на 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan еще не может обрабатывать имя локали в функции format-number." }, { "WG_LOCALE_NOT_FOUND", "Предупреждение: Не найдена локаль для xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "Невозможно создать URL из: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Не удалось загрузить запрошенный документ: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Не удалось найти Collator для <sort xml:lang={0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Старый синтаксис: в инструкции functions не следует применять url {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "Кодировка не поддерживается: {0}, применяется UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "Кодировка не поддерживается: {0}, применяется Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "Обнаружен конфликт спецификаций: Будет применяться последний обнаруженный в таблице стилей {0}." }, { "WG_PARSING_AND_PREPARING", "========= Анализ и подготовка {0} ==========" }, { "WG_ATTR_TEMPLATE", "Шаблон атрибута, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Конфликт соответствия xsl:strip-space и xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan еще не может обрабатывать атрибут {0}!" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Не найдено объявление десятичного формата: {0}" }, { "WG_OLD_XSLT_NS", "Отсутствующее или неверное пространство имен XSLT. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Допустимо только одно объявление xsl:decimal-format по умолчанию." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Имена xsl:decimal-format должны быть уникальными. Имя \"{0}\" повторяется." }, { "WG_ILLEGAL_ATTRIBUTE", "Для {0} указан недопустимый атрибут: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Невозможно преобразовать префикс пространства имен: {0}. Узел будет проигнорирован." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "Для xsl:stylesheet необходим атрибут 'version'!" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Недопустимое имя атрибута: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Недопустимое значение атрибута {0}: {1}" }, { "WG_EMPTY_SECOND_ARG", "Результирующий набор узлов из второго аргумента функции document пуст. Возврат пустого node-set." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Значение атрибута 'name' в имени xsl:processing-instruction не должно быть равно 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Значение атрибута ''name'' в xsl:processing-instruction должно быть допустимым именем NCName: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Невозможно добавить атрибут {0} после дочерних узлов или перед созданием элемента.  Атрибут будет проигнорирован. " }, { "NO_MODIFICATION_ALLOWED_ERR", "Попытка изменения объекта, изменение которого запрещено. " }, { "ui_language", "en" }, { "help_language", "en" }, { "language", "en" }, { "BAD_CODE", "Параметр createMessage лежит вне допустимого диапазона" }, { "FORMAT_FAILED", "Исключительная ситуация при вызове messageFormat" }, { "version", ">>>>>>> Версия Xalan " }, { "version2", "<<<<<<<" }, { "yes", "да" }, { "line", "Номер строки " }, { "column", "Номер столбца " }, { "xsldone", "XSLProcessor: выполнено" }, { "xslProc_option", "Опции процесса командной строки Xalan-J:" }, { "xslProc_option", "Опции процесса командной строки Xalan-J:" }, { "xslProc_invalid_xsltc_option", "Опция {0} не поддерживается в режиме XSLTC." }, { "xslProc_invalid_xalan_option", "Опция {0} может применяться только с -XSLTC." }, { "xslProc_no_input", "Ошибка: Не указана таблица стилей или исходный файл xml. Для просмотра справки по использованию команды введите команду без параметров." }, { "xslProc_common_options", "-Общие опции-" }, { "xslProc_xalan_options", "-Опции Xalan-" }, { "xslProc_xsltc_options", "-Опции XSLTC-" }, { "xslProc_return_to_continue", "(Для продолжения нажмите <Enter>)" }, { "optionXSLTC", "   [-XSLTC (для преобразования используйте XSLTC)]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "   [-XSL XSLTransformationURL]" }, { "optionOUT", "   [-OUT outputFileName]" }, { "optionLXCIN", "   [-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "   [-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "   [-PARSER полное имя класса анализатора]" }, { "optionE", "   [-E (Не разворачивать ссылки на макросы entity)]" }, { "optionV", "   [-E (Не разворачивать ссылки на макросы entity)]" }, { "optionQC", "   [-QC (Не показывать предупреждения о конфликтах шаблонов)]" }, { "optionQ", "   [-Q  (Тихий режим)]" }, { "optionLF", "   [-LF (Применять в выводе только LF {по умолчанию - CR/LF})]" }, { "optionCR", "   [-CR (Применять в выводе только CR {по умолчанию - CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (Символы, требующие Esc-последовательностей {по умолчанию - <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (Число пробелов в отступе {по умолчанию - 0})]" }, { "optionTT", "   [-TT (Трассировка вызываемых шаблонов.)]" }, { "optionTG", "   [-TG (Трассировка событий создания.)]" }, { "optionTS", "   [-TS (Трассировка событий выбора.)]" }, { "optionTTC", "   [-TTC (Трассировка обрабатываемых дочерних объектов шаблонов.)]" }, { "optionTCLASS", "   [-TCLASS (Класс TraceListener для трассировка расширений.)]" }, { "optionVALIDATE", "   [-VALIDATE (Включает проверку.  По умолчанию проверка выключена.)]" }, { "optionEDUMP", "   [-EDUMP {необязательное имя файла} (Дамп стека при ошибке.)]" }, { "optionXML", "   [-XML (Применять форматирование XML и добавлять заголовок XML.)]" }, { "optionTEXT", "   [-TEXT (Применять текстовое форматирование.)]" }, { "optionHTML", "   [-HTML (Применять форматирование HTML.)]" }, { "optionPARAM", "   [-PARAM имя выражение (Задать параметр таблицы стилей)]" }, { "noParsermsg1", "В процессе XSL обнаружены ошибки." }, { "noParsermsg2", "** Анализатор не найден **" }, { "noParsermsg3", "Проверьте значение classpath." }, { "noParsermsg4", "Если у вас нет анализатора XML Parser for Java фирмы IBM, вы можете загрузить его с сайта" }, { "noParsermsg5", "IBM AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER имя класса (URIResolver для преобразования URL)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER имя класса (EntityResolver для преобразования макросов)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER имя класса (ContentHandler для сериализации вывода)]" }, { "optionLINENUMBERS", "   [-L применять номера строк исходного документа]" }, { "optionSECUREPROCESSING", "   [-SECURE (задайте для функции защищенной обработки значение true.)]" }, { "optionMEDIA", "   [-MEDIA тип-носит. (Применять атрибут носителя для поиска таблицы стилей.)]" }, { "optionFLAVOR", "   [-FLAVOR имя-преобразования (Явно указать s2s=SAX или d2d=DOM.)] " }, { "optionDIAG", "   [-DIAG (Печать отчета о времени преобразования в миллисекундах.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (Запросить дополняющую модель DTM с помощью значения http://xml.apache.org/xalan/features/incremental true.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (Отключить оптимизацию таблицы стилей с помощью значения http://xml.apache.org/xalan/features/optimize false.)]" }, { "optionRL", "   [-RL ограничение (Числовое ограничение глубины рекурсии таблиц стилей.)]" }, { "optionXO", "   [-XO [имя-процедуры] (Присвоить имя созданной процедуре преобразования)]" }, { "optionXD", "   [-XD целевой-каталог (Задает целевой каталог для процедуры преобразования)]" }, { "optionXJ", "   [-XJ файл-jar (Упаковывает классы процедуры преобразования в <файл-jar>)]" }, { "optionXP", "   [-XP пакет (Префикс имени пакета для всех созданных классов translet)]" }, { "optionXN", "   [-XN (разрешает копирование шаблона)]" }, { "optionXX", "   [-XX (включает вывод дополнительных отладочных сообщений)]" }, { "optionXT", "   [-XT (по возможности применяет процедуру преобразования)]" }, { "diagTiming", " --------- Преобразование {0} с помощью {1} заняло {2} мс" }, { "recursionTooDeep", "Слишком большая вложенность шаблонов. Вложенность = {0}, шаблон {1} {2}" }, { "nameIs", "имя" }, { "matchPatternIs", "шаблон соответствия" } };
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
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("en", "US"));
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_ru
 * JD-Core Version:    0.7.0.1
 */