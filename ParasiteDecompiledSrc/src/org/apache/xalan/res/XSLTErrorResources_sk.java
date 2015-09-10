/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_sk
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
/*  263:     */   public static final String ERROR_STRING = "#error";
/*  264:     */   public static final String ERROR_HEADER = "Chyba: ";
/*  265:     */   public static final String WARNING_HEADER = "Upozornenie: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "PATTERN ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Chyba: Nie je možné mať vo výraze '{'" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} má neplatný atribút: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode je v xsl:apply-imports nulový!" }, { "ER_CANNOT_ADD", "Nemôže pridať {0} do {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode je nulový v handleApplyTemplatesInstruction!" }, { "ER_NO_NAME_ATTRIB", "{0} musí mať atribút názvu." }, { "ER_TEMPLATE_NOT_FOUND", "Nebolo možné nájsť vzor s názvom: {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Nebolo možné rozlíšiť názov AVT v xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "{0} vyžaduje atribút: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} musí mať atribút ''test''." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Nesprávna hodnota na atribúte úrovne: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "názov processing-instruction nemôže byť 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "názov inštrukcie spracovania musí byť platným NCName: {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} musí mať porovnávací atribút, ak má režim." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} vyžaduje buď názov, alebo porovnávací atribút." }, { "ER_CANT_RESOLVE_NSPREFIX", "Nie je možné rozlíšiť predponu názvového priestoru: {0}" }, { "ER_ILLEGAL_VALUE", "xml:space má neplatnú hodnotu: {0}" }, { "ER_NO_OWNERDOC", "Potomok uzla nemá dokument vlastníka!" }, { "ER_ELEMTEMPLATEELEM_ERR", "Chyba ElemTemplateElement: {0}" }, { "ER_NULL_CHILD", "Pokus o pridanie nulového potomka!" }, { "ER_NEED_SELECT_ATTRIB", "{0} vyžaduje atribút výberu." }, { "ER_NEED_TEST_ATTRIB", "xsl:when musí mať atribút 'test'." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param musí mať atribút 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "kontext nemá dokument vlastníka!" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Nebolo možné vytvoriť vzťah XML TransformerFactory: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: Proces bol neúspešný." }, { "ER_NOT_SUCCESSFUL", "Xalan: bol neúspešný." }, { "ER_ENCODING_NOT_SUPPORTED", "Kódovanie nie je podporované: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Nebolo možné vytvoriť TraceListener: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key vyžaduje atribút 'name'!" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key vyžaduje atribút 'match'!" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key vyžaduje atribút 'use'!" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} vyžaduje atribút ''elements''!" }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0} chýba atribút ''prefix''" }, { "ER_BAD_STYLESHEET_URL", "URL štýlu dokumentu je nesprávna: {0}" }, { "ER_FILE_NOT_FOUND", "Súbor štýlu dokumentu nebol nájdený: {0}" }, { "ER_IOEXCEPTION", "V súbore štýlu dokumentu bola vstupno-výstupná výnimka: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Nebolo možné nájsť atribút href pre {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} priamo, alebo nepriamo, obsahuje sám seba!" }, { "ER_PROCESSINCLUDE_ERROR", "chyba StylesheetHandler.processInclude, {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0} chýba atribút ''lang''" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) chybne umiestnený {0} element?? Chýba kontajnerový prvok ''component''" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Môže prevádzať výstup len do Element, DocumentFragment, Document, alebo PrintWriter." }, { "ER_PROCESS_ERROR", "chyba StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Chyba UnImplNode: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Chyba! Nenašlo sa vyjadrenie výberu xpath (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Nie je možné serializovať XSLProcessor!" }, { "ER_NO_INPUT_STYLESHEET", "Nebol zadaný vstup štýl dokumentu!" }, { "ER_FAILED_PROCESS_STYLESHEET", "Zlyhalo spracovanie štýlu dokumentu!" }, { "ER_COULDNT_PARSE_DOC", "Nebolo možné analyzovať dokument {0}!" }, { "ER_COULDNT_FIND_FRAGMENT", "Nebolo možné nájsť fragment: {0}" }, { "ER_NODE_NOT_ELEMENT", "Uzol, na ktorý ukazuje identifikátor fragmentu nebol elementom: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each musí mať buď porovnávací atribút, alebo atribút názvu" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "vzory musia mať buď porovnávacie atribúty, alebo atribúty názvov" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Žiadna kópia fragmentu dokumentu!" }, { "ER_CANT_CREATE_ITEM", "Nie je možné vytvoriť položku vo výsledkovom strome: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space v zdrojovom XML má neplatnú hodnotu: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Neexistuje žiadna deklarácia xsl:key pre {0}!" }, { "ER_CANT_CREATE_URL", "Chyba! Nie je možné vytvoriť url pre: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions nie je podporované" }, { "ER_PROCESSOR_ERROR", "Chyba XSLT TransformerFactory" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} nie je povolený vnútri štýlu dokumentu!" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns už viac nie je podporovaný!  Použite namiesto toho xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space už viac nie je podporovaný!  Použite namiesto toho xsl:strip-space alebo xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result už viac nie je podporovaný!  Použite namiesto toho xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} má neplatný atribút: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Neznámy element XSL: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort možno použiť len s xsl:apply-templates alebo xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when na nesprávnom mieste!" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when nemá ako rodiča xsl:choose!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise na nesprávnom mieste!" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise nemá ako rodiča xsl:choose!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} nie je povolený vnútri vzoru!" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0} prefix rozšíreného názvového priestoru {1} nie je známy" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Importy sa môžu vyskytnúť len ako prvé časti štýlu dokumentu!" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} priamo, alebo nepriami, importuje sám seba!" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space má neplatnú hodnotu: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet nebol úspešný!" }, { "ER_SAX_EXCEPTION", "Výnimka SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funkcia nie je podporovaná!" }, { "ER_XSLT_ERROR", "Chyba XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "znak meny nie je povolený vo reťazci formátu vzoru" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "Funckia dokumentu nie je podporovaná v štýle dokumentu DOM!" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Nie je možné určiť prefix bezprefixového rozkladača!" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Rozšírenie presmerovania: Nedal sa získať názov súboru - súbor alebo atribút výberu musí vrátiť platný reťazec." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Nie je možné vytvoriť FormatterListener v rozšírení Redirect!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Predpona v exclude-result-prefixes je neplatná: {0}" }, { "ER_MISSING_NS_URI", "Chýbajúci názvový priestor URI pre zadaný prefix" }, { "ER_MISSING_ARG_FOR_OPTION", "Chýbajúci argument pre voľbu: {0}" }, { "ER_INVALID_OPTION", "Neplatná voľba. {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Znetvorený reťazec formátu: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet si vyžaduje atribút 'version'!" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "Atribút: {0} má neplatnú hodnotu: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose vyžaduje xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports nie je povolený v xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Nemôže použiť DTMLiaison pre výstupný uzol DOM... namiesto neho odošlite org.apache.xpath.DOM2Helper!" }, { "ER_CANT_USE_DTM_FOR_INPUT", "Nemôže použiť DTMLiaison pre vstupný uzol DOM... namiesto neho odošlite org.apache.xpath.DOM2Helper!" }, { "ER_CALL_TO_EXT_FAILED", "Volanie elementu rozšírenia zlyhalo: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "Predpona sa musí rozlíšiť do názvového priestoru: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Bolo zistené neplatné nahradenie UTF-16: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} použil sám seba, čo spôsobí nekonečnú slučku." }, { "ER_CANNOT_MIX_XERCESDOM", "Nie je možné miešať vstup iný, než Xerces-DOM s výstupom Xerces-DOM!" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "V ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Našiel sa viac než jeden vzor s názvom: {0}" }, { "ER_INVALID_KEY_CALL", "Neplatné volanie funkcie: rekurzívne volanie kľúča() nie je povolené" }, { "ER_REFERENCING_ITSELF", "Premenná {0} sa priamo, alebo nepriamo, odkazuje sama na seba!" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "Vstupný uzol nemôže byť pre DOMSource pre newTemplates nulový!" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Súbor triedy nebol pre voľbu {0} nájdený" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Požadovaný element sa nenašiel: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream nemôže byť nulový" }, { "ER_URI_CANNOT_BE_NULL", "URI nemôže byť nulový" }, { "ER_FILE_CANNOT_BE_NULL", "Súbor nemôže byť nulový" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource nemôže byť nulový" }, { "ER_CANNOT_INIT_BSFMGR", "Nebolo možné inicializovať Správcu BSF" }, { "ER_CANNOT_CMPL_EXTENSN", "Nebolo možné skompilovať príponu" }, { "ER_CANNOT_CREATE_EXTENSN", "Nebolo možné vytvoriť rozšírenie: {0} z dôvodu: {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "Volanie metódy metódou inštancie {0} vyžaduje ako prvý argument Inštanciu objektu" }, { "ER_INVALID_ELEMENT_NAME", "Bol zadaný neplatný názov súčasti {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "Metóda názvu súčasti musí byť statická {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "Rozšírenie funkcie {0} : {1} je neznáme" }, { "ER_MORE_MATCH_CONSTRUCTOR", "Bola nájdená viac než jedna najlepšia zhoda s konštruktorom pre {0}" }, { "ER_MORE_MATCH_METHOD", "Bola nájdená viac než jedna najlepšia zhoda pre metódu {0}" }, { "ER_MORE_MATCH_ELEMENT", "Bola nájdená viac než jedna najlepšia zhoda pre metódu súčasti {0}" }, { "ER_INVALID_CONTEXT_PASSED", "Bolo odoslaný neplatný kontext na zhodnotenie {0}" }, { "ER_POOL_EXISTS", "Oblasť už existuje" }, { "ER_NO_DRIVER_NAME", "Nebol zadaný žiaden názov ovládača" }, { "ER_NO_URL", "Nebola zadaná žiadna URL" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "Veľkosť oblasti je menšia než jeden!" }, { "ER_INVALID_DRIVER", "Bol zadaný neplatný názov ovládača!" }, { "ER_NO_STYLESHEETROOT", "Nebol nájdený koreň štýlu dokumentu!" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Neplatná hodnota pre xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "zlyhal processFromNode" }, { "ER_RESOURCE_COULD_NOT_LOAD", "Prostriedok [ {0} ] sa nedal načítať: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Veľkosť vyrovnávacej pamäte <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Neznáma chyba počas volania prípony" }, { "ER_NO_NAMESPACE_DECL", "Prefix {0} nemá zodpovedajúcu deklaráciu názvového priestoru" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Obsah elementu nie je povolený pre lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Ukončenie riadené štýlom dokumentu" }, { "ER_ONE_OR_TWO", "1, alebo 2" }, { "ER_TWO_OR_THREE", "2, alebo 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Nebolo možné zaviesť {0} (check CLASSPATH), teraz sú požité len predvolené štandardy" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Nie je možné inicializovať predvolené vzory" }, { "ER_RESULT_NULL", "Výsledok by nemal byť nulový" }, { "ER_RESULT_COULD_NOT_BE_SET", "Výsledkom nemôže byť množina" }, { "ER_NO_OUTPUT_SPECIFIED", "Nie je zadaný žiaden výstup" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Nedá sa transformovať na výsledok typu {0}" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Nedá sa transformovať zdroj typu {0}" }, { "ER_NULL_CONTENT_HANDLER", "Nulový manipulačný program obsahu" }, { "ER_NULL_ERROR_HANDLER", "Nulový chybový manipulačný program" }, { "ER_CANNOT_CALL_PARSE", "nemôže byť volané analyzovanie, ak nebol nastavený ContentHandler" }, { "ER_NO_PARENT_FOR_FILTER", "Žiaden rodič pre filter" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Nenašiel sa žiadny stylesheet v: {0}, médium= {1}" }, { "ER_NO_STYLESHEET_PI", "Nenašiel sa žiadny xml-stylesheet PI v: {0}" }, { "ER_NOT_SUPPORTED", "Nie je podporované: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "Hodnota vlastnosti {0} by mala byť boolovská inštancia" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Nie je možné dosiahnuť externý skript na {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "Prostriedok [ {0} ] nemohol byť nájdený.\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Výstupné vlastníctvo nebolo rozoznané: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Zlyhalo vytváranie inštancie ElemLiteralResult" }, { "ER_VALUE_SHOULD_BE_NUMBER", "Hodnota pre {0} by mala obsahovať analyzovateľné číslo" }, { "ER_VALUE_SHOULD_EQUAL", "Hodnota {0} by sa mala rovnať áno, alebo nie" }, { "ER_FAILED_CALLING_METHOD", "Zlyhalo volanie metódy {0}" }, { "ER_FAILED_CREATING_ELEMTMPL", "Zlyhalo vytváranie inštancie ElemTemplateElement" }, { "ER_CHARS_NOT_ALLOWED", "V tomto bode dokumentu nie sú znaky povolené" }, { "ER_ATTR_NOT_ALLOWED", "Atribút \"{0}\" nie je povolený na súčasti {1}!" }, { "ER_BAD_VALUE", "{0} zlá hodnota {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Hodnota atribútu {0} nebola nájdená " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Hodnota atribútu {0} nebola rozpoznaná " }, { "ER_NULL_URI_NAMESPACE", "Pokus o vytvorenie prefixu názvového priestoru s nulovým URI" }, { "ER_NUMBER_TOO_BIG", "Pokus o formátovanie čísla väčšieho, než je najdlhší dlhý celočíselný typ" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Nie je možné nájsť triedu ovládača SAX1 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "Trieda ovládača SAX1 {0} bola nájdená, ale nemôže byť zavedená" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "Trieda ovládača SAX1 {0} bola zavedená, ale nemôže byť doložená príkladom" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "Trieda ovládača SAX1 {0} neimplementuje org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Systémová vlastnosť org.xml.sax.parser nie je zadaná" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "Argument syntaktického analyzátora nesmie byť nulový" }, { "ER_FEATURE", "Vlastnosť: {0}" }, { "ER_PROPERTY", "Vlastníctvo: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Rozkladač nulových entít" }, { "ER_NULL_DTD_HANDLER", "Nulový manipulačný program DTD" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Nie je zadaný žiaden názov ovládača!" }, { "ER_NO_URL_SPECIFIED", "Nie je zadaná žiadna URL!" }, { "ER_POOLSIZE_LESS_THAN_ONE", "Veľkosť oblasti je menšia než 1!" }, { "ER_INVALID_DRIVER_NAME", "Je zadaný neplatný názov ovládača!" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Chyba programátora! Výraz nemá rodiča ElemTemplateElement!" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Tvrdenie programátora v RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0}nie je na tejto pozícii predlohy so štýlmi povolené!" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Text bez medzier nie je povolený na tejto pozícii predlohy so štýlmi!" }, { "INVALID_TCHAR", "Neplatná hodnota: {1} používaný pre atribút CHAR: {0}.  Atribút typu CHAR musí byť len 1 znak!" }, { "INVALID_QNAME", "Neplatná hodnota: {1} používaná pre atribút QNAME: {0}" }, { "INVALID_ENUM", "Neplatná hodnota: {1} používaná pre atribút ENUM: {0}.  Platné hodnoty sú: {2}." }, { "INVALID_NMTOKEN", "Neplatná hodnota: {1} používaná pre atribút NMTOKEN:{0} " }, { "INVALID_NCNAME", "Neplatná hodnota: {1} používaná pre atribút NCNAME: {0} " }, { "INVALID_BOOLEAN", "Neplatná hodnota: {1} používaná pre boolovský atribút: {0} " }, { "INVALID_NUMBER", "Neplatná hodnota: {1} používaná pre atribút čísla: {0} " }, { "ER_ARG_LITERAL", "Argument pre {0} v zhodnom vzore musí byť literálom." }, { "ER_DUPLICATE_GLOBAL_VAR", "Duplicitná deklarácia globálnej premennej." }, { "ER_DUPLICATE_VAR", "Duplicitná deklarácia premennej." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template musí mať názov alebo atribút zhody (alebo oboje)" }, { "ER_INVALID_PREFIX", "Predpona v exclude-result-prefixes je neplatná: {0}" }, { "ER_NO_ATTRIB_SET", "pomenovaná sada atribútov {0} neexistuje" }, { "ER_FUNCTION_NOT_FOUND", "Funkcia s názvom {0} neexistuje." }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "Prvok {0} nesmie mať aj atribút content aj atribút select." }, { "ER_INVALID_SET_PARAM_VALUE", "Hodnota parametra {0} musí byť platným objektom jazyka Java." }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "Atribút result-prefix prvku xsl:namespace-alias má hodnotu '#default', ale v rozsahu pre prvok neexistuje žiadna deklarácia štandardného názvového priestoru" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "Atribút result-prefix prvku xsl:namespace-alias má hodnotu ''{0}'', ale v rozsahu pre prvok neexistuje žiadna deklarácia názvového priestoru pre predponu ''{0}''." }, { "ER_SET_FEATURE_NULL_NAME", "V TransformerFactory.setFeature(Názov reťazca, boolovská hodnota)nemôže mať funkcia názov null." }, { "ER_GET_FEATURE_NULL_NAME", "Názov vlastnosti nemôže byť null v TransformerFactory.getFeature(Názov reťazca)." }, { "ER_UNSUPPORTED_FEATURE", "V tomto TransformerFactory sa nedá nastaviť vlastnosť ''{0}''." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "Používanie prvku rozšírenia ''{0}'' nie je povolené, keď je funkcia bezpečného spracovania nastavená na hodnotu true." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Nedá sa získať predpona pre null názvový priestor uri." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Nedá sa získať názvový priestor uri pre predponu null." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "Názov funkcie nemôže byť null." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "Arita nemôže byť záporná." }, { "WG_FOUND_CURLYBRACE", "Bol nájdený znak '}', ale nie otvorený žiaden vzor atribútu!" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Upozornenie: atribút počtu sa nezhoduje s predchodcom v xsl:number! Cieľ = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Stará syntax: Názov atribútu 'expr' bol zmenený na 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan zatiaľ nespracováva názov umiestnenia vo funkcii format-number." }, { "WG_LOCALE_NOT_FOUND", "Upozornenie: Nebolo možné nájsť lokál pre xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "Nie je možné vytvoriť URL z: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Nie je možné zaviesť požadovaný doc: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Nebolo možné nájsť porovnávač pre <sort xml:lang={0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Stará syntax: inštrukcia funkcií by mala používať url {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "Kódovanie nie je podporované: {0}, používa UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "Kódovanie nie je podporované: {0}, používa Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "Boli zistené konflikty špecifickosti: {0} naposledy nájdená v štýle dokumentu bude použitá." }, { "WG_PARSING_AND_PREPARING", "========= Analýza a príprava {0} ==========" }, { "WG_ATTR_TEMPLATE", "Attr vzor, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Konflikt zhodnosti medzi xsl:strip-space a xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan zatiaľ nespracúva atribút {0}!" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Pre desiatkový formát sa nenašla žiadna deklarácia: {0}" }, { "WG_OLD_XSLT_NS", "Chýbajúci, alebo nesprávny názvový priestor XSLT. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Povolená je len jedna štandardná deklarácia xsl:decimal-format." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Názvy xsl:decimal-format musia byť jedinečné. Názov \"{0}\" bol zopakovaný." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} má neplatný atribút: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Nebolo možné rozlíšiť predponu názvového priestoru: {0}. Uzol bude ignorovaný." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet si vyžaduje atribút 'version'!" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Neplatný názov atribútu: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Neplatná hodnota používaná pre atribút {0}: {1}" }, { "WG_EMPTY_SECOND_ARG", "Výsledný nodeset z druhého argumentu funkcie dokumentu je prázdny. Vráťte prázdnu množinu uzlov." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Hodnota atribútu 'name' názvu xsl:processing-instruction nesmie byť 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Hodnota atribútu ''name'' xsl:processing-instruction musí byť platným NCName: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Nie je možné pridať atribút {0} po uzloch potomka alebo pred vytvorením elementu.  Atribút bude ignorovaný." }, { "NO_MODIFICATION_ALLOWED_ERR", "Prebieha pokus o úpravu objektu, pre ktorý nie sú povolené úpravy." }, { "ui_language", "en" }, { "help_language", "en" }, { "language", "en" }, { "BAD_CODE", "Parameter na createMessage bol mimo ohraničenia" }, { "FORMAT_FAILED", "Výnimka počas volania messageFormat" }, { "version", ">>>>>>> Verzia Xalan " }, { "version2", "<<<<<<<" }, { "yes", "áno" }, { "line", "Riadok #" }, { "column", "Stĺpec #" }, { "xsldone", "XSLProcessor: vykonané" }, { "xslProc_option", "Voľby triedy procesu príkazového riadka Xalan-J:" }, { "xslProc_option", "Voľby triedy Process príkazového riadka Xalan-J:" }, { "xslProc_invalid_xsltc_option", "Voľba {0} nie je podporovaná v režime XSLTC." }, { "xslProc_invalid_xalan_option", "Voľbu {0} možno použiť len spolu s -XSLTC." }, { "xslProc_no_input", "Chyba: nie je uvedený žiadny štýl dokumentu, ani vstupný xml. Spustite tento príkaz bez akejkoľvek voľby pre inštrukcie použitia." }, { "xslProc_common_options", "-Bežné voľby-" }, { "xslProc_xalan_options", "-Voľby pre Xalan-" }, { "xslProc_xsltc_options", "-Voľby pre XSLTC-" }, { "xslProc_return_to_continue", "(stlačte <return> a pokračujte)" }, { "optionXSLTC", "   [-XSLTC (použite XSLTC na transformáciu)]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "   [-XSL XSLTransformationURL]" }, { "optionOUT", "   [-OUT outputFileName]" }, { "optionLXCIN", "   [-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "   [-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "   [-PARSER plne kvalifikovaný názov triedy sprostredkovateľa syntaktického analyzátora]" }, { "optionE", "   [-E (Nerozvinie odkazy na entity)]" }, { "optionV", "   [-E (Nerozvinie odkazy na entity)]" }, { "optionQC", "   [-QC (Varovania pri konfliktoch Quiet Pattern)]" }, { "optionQ", "   [-Q  (Tichý režim)]" }, { "optionLF", "   [-LF (Znaky pre posun riadka použiť len vo výstupe {default is CR/LF})]" }, { "optionCR", "   [-CR (Znaky návratu vozíka použiť len vo výstupe {default is CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (Ktoré znaky majú mať zmenený význam {default is <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (Riadi počet medzier odsadenia {default is 0})]" }, { "optionTT", "   [-TT (Sledovanie, ako sú volané vzory.)]" }, { "optionTG", "   [-TG (Sledovanie udalostí každej generácie.)]" }, { "optionTS", "   [-TS (Sledovanie udalostí každého výberu.)]" }, { "optionTTC", "   [-TTC (Sledovanie ako sú vytváraní potomkovia vzorov.)]" }, { "optionTCLASS", "   [-TCLASS (Trieda TraceListener pre prípony sledovania.)]" }, { "optionVALIDATE", "   [-VALIDATE (Určuje, či má dochádzať k overovaniu.  Overovanie je štandardne vypnuté.)]" }, { "optionEDUMP", "   [-EDUMP {optional filename} (Vytvoriť výpis zásobníka pri chybe.)]" }, { "optionXML", "   [-XML (Použije formátor XML a pridá hlavičku XML.)]" }, { "optionTEXT", "   [-TEXT (Jednoduchý textový formátor.)]" }, { "optionHTML", "   [-HTML (Použije formátor HTML.)]" }, { "optionPARAM", "   [-PARAM vyjadrenie názvu (nastaví parameter štýlu dokumentu)]" }, { "noParsermsg1", "Proces XSL nebol úspešný." }, { "noParsermsg2", "** Nebolo možné nájsť syntaktický analyzátor **" }, { "noParsermsg3", "Skontroluje, prosím, svoju classpath." }, { "noParsermsg4", "Ak nemáte Syntaktický analyzátor XML pre jazyk Java od firmy IBM, môžete si ho stiahnuť z" }, { "noParsermsg5", "IBM's AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER plný názov triedy (URIResolver bude použitý na určovanie URI)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER plný názov triedy (EntityResolver bude použitý na určenie entít)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER plný názov triedy (ContentHandler bude použitý na serializáciu výstupu)]" }, { "optionLINENUMBERS", "   [-L použije čísla riadkov pre zdrojový dokument]" }, { "optionSECUREPROCESSING", "   [-SECURE (nastaví funkciu bezpečného spracovania na hodnotu true.)]" }, { "optionMEDIA", "   [-MEDIA mediaType (použiť atribút média na nájdenie štýlu hárka, priradeného k dokumentu.)]" }, { "optionFLAVOR", "   [-FLAVOR flavorName (Explicitne použiť s2s=SAX alebo d2d=DOM na vykonanie transformácie.)] " }, { "optionDIAG", "   [-DIAG (Vytlačiť celkový čas transformácie v milisekundách.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (žiadosť o inkrementálnu konštrukciu DTM nastavením http://xml.apache.org/xalan/features/incremental true.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (požiadavka na nespracúvanie optimalizácie definície štýlov nastavením http://xml.apache.org/xalan/features/optimize na hodnotu false.)]" }, { "optionRL", "   [-RL recursionlimit (nastaviť číselný limit pre hĺbku rekurzie štýlov hárkov.)]" }, { "optionXO", "   [-XO [transletName] (priraďuje názov ku generovanému transletu)]" }, { "optionXD", "   [-XD destinationDirectory (uvádza cieľový adresár pre translet)]" }, { "optionXJ", "   [-XJ jarfile (pakuje triedy transletu do súboru jar s názvom <jarfile>)]" }, { "optionXP", "   [-XP package (uvádza predponu názvu balíka pre všetky generované triedy transletu)]" }, { "optionXN", "   [-XN (povoľuje zoradenie vzorov do riadka)]" }, { "optionXX", "   [-XX (zapína ďalší výstup správ ladenia)]" }, { "optionXT", "   [-XT (ak je to možné, použite translet na transformáciu)]" }, { "diagTiming", " --------- Transformácia z {0} cez {1} trvala {2} ms" }, { "recursionTooDeep", "Vnorenie vzoru je príliš hlboké. vnorenie = {0}, vzor {1} {2}" }, { "nameIs", "názov je" }, { "matchPatternIs", "vzor zhody je" } };
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_sk
 * JD-Core Version:    0.7.0.1
 */