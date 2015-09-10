/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_hu
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
/*  264:     */   public static final String ERROR_HEADER = "Hiba: ";
/*  265:     */   public static final String WARNING_HEADER = "Figyelmeztetés: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "MINTA ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Hiba: Nem lehet '{' a kifejezéseken belül" }, { "ER_ILLEGAL_ATTRIBUTE", "A(z) {0}-nak érvénytelen attribútuma van: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "A sourceNode értéke null az xsl:apply-imports metódusban." }, { "ER_CANNOT_ADD", "Nem lehet a(z) {0}-t felvenni a(z) {1}-ba" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "A sourceNode null a handleApplyTemplatesInstruction-ban!" }, { "ER_NO_NAME_ATTRIB", "A(z) {0}-nak kell legyen name attribútuma." }, { "ER_TEMPLATE_NOT_FOUND", "Nem található {0} nevű sablon" }, { "ER_CANT_RESOLVE_NAME_AVT", "Nem lehet feloldani a név AVT-t az xsl:call-template-ben." }, { "ER_REQUIRES_ATTRIB", "{0}-nek attribútum szükséges: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "A(z) {0} -nak kell legyen ''test'' attribútuma. " }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Rossz érték a level attribútumban: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "A feldolgozási utasítás neve nem lehet 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "A feldolgozási utasítás neve érvényes NCName kell legyen: {0}" }, { "ER_NEED_MATCH_ATTRIB", "A(z) {0}-nek kell legyen illeszkedési attribútuma, ha van módja." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "A(z) {0}-nak kell vagy név vagy illeszkedési attribútum." }, { "ER_CANT_RESOLVE_NSPREFIX", "Nem lehet feloldani a névtér előtagot: {0}" }, { "ER_ILLEGAL_VALUE", "Az xml:space értéke érvénytelen: {0}" }, { "ER_NO_OWNERDOC", "A leszármazott csomópontnak nincs tulajdonos dokumentuma!" }, { "ER_ELEMTEMPLATEELEM_ERR", "ElemTemplateElement hiba: {0}" }, { "ER_NULL_CHILD", "Kísérlet null leszármazott felvételére!" }, { "ER_NEED_SELECT_ATTRIB", "A(z) {0}-nak kell kiválasztási attribútum." }, { "ER_NEED_TEST_ATTRIB", "Az xsl:when-nek kell legyen 'test' attribútuma." }, { "ER_NEED_NAME_ATTRIB", "Az xsl:param-nak kell legyen 'name' attribútuma." }, { "ER_NO_CONTEXT_OWNERDOC", "A környezetnek nincs tulajdonos dokumentuma!" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Nem lehet XML TransformerFactory Liaison-t létrehozni: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "A Xalan folyamat sikertelen volt." }, { "ER_NOT_SUCCESSFUL", "Xalan: sikertelen volt." }, { "ER_ENCODING_NOT_SUPPORTED", "A kódolás nem támogatott: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Nem lehet TraceListener-t létrehozni: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "Az xsl:key-nek kell legyen 'name' attribútuma!" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "Az xsl:key-nek kell legyen 'match' attribútuma!" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "Az xsl:key-nek kell legyen 'use' attribútuma!" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) A(z) {0}-nak kell legyen ''elements'' attribútuma! " }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) A(z) {0}-nak hiányzik a ''prefix'' attribútuma" }, { "ER_BAD_STYLESHEET_URL", "A stíluslap URL rossz: {0}" }, { "ER_FILE_NOT_FOUND", "A stíluslap fájl nem található: {0}" }, { "ER_IOEXCEPTION", "IO kivétel történt a stíluslap fájlnál: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) A(z) {0} href attribútuma nem található" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) A(z) {0} közvetlenül vagy közvetetten tartalmazza saját magát!" }, { "ER_PROCESSINCLUDE_ERROR", "StylesheetHandler.processInclude hiba, {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) A(z) {0}-nak hiányzik a ''lang'' attribútuma " }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) Rosszul elhelyezett {0} elem?? Hiányzik a ''component'' tárolóelem" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Csak egy Element-be, DocumentFragment-be, Document-be vagy PrintWriter-be lehet kimenetet küldeni." }, { "ER_PROCESS_ERROR", "StylesheetRoot.process hiba" }, { "ER_UNIMPLNODE_ERROR", "UnImplNode hiba: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Hiba! Az xpath kiválasztási kifejezés nem található (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Nem lehet sorbarakni az XSLProcessor-t!" }, { "ER_NO_INPUT_STYLESHEET", "Nem adott meg stíluslap bemenetet!" }, { "ER_FAILED_PROCESS_STYLESHEET", "Nem sikerült feldolgozni a stíluslapot!" }, { "ER_COULDNT_PARSE_DOC", "Nem lehet elemezni a(z) {0} dokumentumot!" }, { "ER_COULDNT_FIND_FRAGMENT", "Nem található a darab: {0}" }, { "ER_NODE_NOT_ELEMENT", "A darab azonosító által mutatott csomópont nem elem: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "A for-each-nek legalább egy match vagy egy name attribútuma kell legyen" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "A sablonoknak vagy match vagy name attribútumuk kell legyen" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Nincs klónja egy dokumentumdarabnak!" }, { "ER_CANT_CREATE_ITEM", "Nem lehet elemet létrehozni az eredményfában: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "Az xml:space-nek a forrás XML-ben tiltott értéke van: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Nincs xsl:key deklaráció a(z) {0}-hoz!" }, { "ER_CANT_CREATE_URL", "Hiba! Nem lehet URL-t létrehozni ehhez: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "Az xsl:functions nem támogatott" }, { "ER_PROCESSOR_ERROR", "XSLT TransformerFactory hiba" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) A(z) {0} nem megengedett a stíluslapon belül!" }, { "ER_RESULTNS_NOT_SUPPORTED", "A result-ns többé már nem támogatott!  Használja inkább az xsl:output-ot." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "A default-space többé már nem támogatott!  Használja inkább az xsl:strip-space-t vagy az  xsl:preserve-space-t." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "Az indent-result többé már nem támogatott!  Használja inkább az xsl:output-ot." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) A(z) {0}-nak tiltott attribútuma van: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Ismeretlen XSL elem: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) Az xsl:sort csak az xsl:apply-templates-szel vagy xsl:for-each-el együtt használható." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) Rosszul elhelyezett xsl:when!" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) Az xsl:when szülője nem xsl:choose!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) Rosszul elhelyezett xsl:otherwise!" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) Az xsl:otherwise szülője nem xsl:choose!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) A(z) {0} nem megengedett sablonok belsejében!" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) A(z) {0} kiterjesztés névtér előtag {1} ismeretlen" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Az importálások csak a stíluslap első elemei lehetnek!" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) A(z) {0} közvetlenül vagy közvetve tartalmazza saját magát!" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space értéke nem megengedett: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "A processStylesheet sikertelen volt!" }, { "ER_SAX_EXCEPTION", "SAX kivétel" }, { "ER_FUNCTION_NOT_SUPPORTED", "A függvény nem támogatott!" }, { "ER_XSLT_ERROR", "XSLT hiba" }, { "ER_CURRENCY_SIGN_ILLEGAL", "A pénzjel nem megengedett a formátum minta karakterláncban" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "A document funkció nem támogatott a Stylesheet DOM-ban!" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Nem lehet feloldani az előtagot egy nem-előtag feloldónak!" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Átirányítás kiterjesztés: Nem lehet megkapni a fájlnevet - a file vagy select attribútumnak egy érvényes karakterláncot kell visszaadnia." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Nem lehet FormatterListener-t építeni az átirányítás kiterjesztésben!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Az előtag az exclude-result-prefixes-ben nem érvényes: {0}" }, { "ER_MISSING_NS_URI", "Hiányzik a megadott előtag névtér URI-ja" }, { "ER_MISSING_ARG_FOR_OPTION", "Hiányzik az opció argumentuma: {0}" }, { "ER_INVALID_OPTION", "Érvénytelen opció: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Rossz formátumú karakterlánc: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "Az xsl:stylesheet-nek kell legyen 'version' attribútuma!" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "A(z) {0} attibútum értéke érvénytelen: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "Az xsl:choose-hoz egy xsl:when szükséges" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "Az xsl:apply-imports nem megengedett xsl:for-each-en belül" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Nem használhat DTMLiaison-t kimeneti DOM csomópontként... adjon át inkább egy org.apache.xpath.DOM2Helper-t!" }, { "ER_CANT_USE_DTM_FOR_INPUT", "Nem használhat DTMLiaison-t bemeneti DOM csomópontként... adjon át inkább egy org.apache.xpath.DOM2Helper-t!" }, { "ER_CALL_TO_EXT_FAILED", "A kiterjesztés-elem meghívása sikertelen volt: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "Az előtagnak egy névtérre kell feloldódnia: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Érvénytelen UTF-16 helyettesítés: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "A(z) {0} xsl:attribute-set-et saját magával használta, ami végtelen ciklust eredményez." }, { "ER_CANNOT_MIX_XERCESDOM", "Nem keverheti a nem Xerces-DOM bemenetet a Xerces-DOM kimenettel!" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "Az ElemTemplateElement.readObject metódusban: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Egynél több ''{0}'' nevű sablont találtam" }, { "ER_INVALID_KEY_CALL", "Érvénytelen függvényhívás: rekurzív key() hívások nem megengedettek" }, { "ER_REFERENCING_ITSELF", "A(z) {0} változó közvetlenül vagy közvetve önmagára hivatkozik!" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "A bemeneti csomópont nem lehet null egy DOMSource-ban a newTemplates-hez!" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Az osztály fájl nem található a(z) {0} opcióhoz" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "A szükséges elem nem található: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "Az InputStream nem lehet null" }, { "ER_URI_CANNOT_BE_NULL", "Az URI nem lehet null" }, { "ER_FILE_CANNOT_BE_NULL", "A fájl nem lehet null" }, { "ER_SOURCE_CANNOT_BE_NULL", "Az InputSource nem lehet null" }, { "ER_CANNOT_INIT_BSFMGR", "Nem lehet inicializálni a BSF kezelőt" }, { "ER_CANNOT_CMPL_EXTENSN", "Nem lehet lefordítani a kiterjesztést" }, { "ER_CANNOT_CREATE_EXTENSN", "Nem lehet létrehozni a kiterjesztést ({0}) {1} miatt" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "Az {0} metódus példány metódushívásához szükség van egy objektumpéldányra első argumentumként" }, { "ER_INVALID_ELEMENT_NAME", "Érvénytelen elemnevet adott meg {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "Az elemnév metódus statikus {0} kell legyen" }, { "ER_EXTENSION_FUNC_UNKNOWN", "{0} kiterjesztés funkció : A(z) {1} ismeretlen" }, { "ER_MORE_MATCH_CONSTRUCTOR", "Több legjobb illeszkedés a(z) {0} konstruktorára" }, { "ER_MORE_MATCH_METHOD", "Több legjobb illeszkedés a(z) {0} metódusra" }, { "ER_MORE_MATCH_ELEMENT", "Több legjobb illeszkedés a(z) {0} elem metódusra" }, { "ER_INVALID_CONTEXT_PASSED", "Érvénytelen környzetet adott át a(z) {0} kiértékeléséhez" }, { "ER_POOL_EXISTS", "A tároló már létezik" }, { "ER_NO_DRIVER_NAME", "Nem adott meg meghajtónevet" }, { "ER_NO_URL", "Nem adott meg URL-t" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "A tároló mérete egynél kisebb!" }, { "ER_INVALID_DRIVER", "Érvénytelen meghajtónevet adott meg!" }, { "ER_NO_STYLESHEETROOT", "Nem található a stíluslap gyökere!" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Tiltott érték az xml:space-hez" }, { "ER_PROCESSFROMNODE_FAILED", "A processFromNode nem sikerült" }, { "ER_RESOURCE_COULD_NOT_LOAD", "Az erőforrást [ {0} ] nem lehet betölteni: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Pufferméret <= 0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Ismeretlen hiba a kiterjesztés hívásánál" }, { "ER_NO_NAMESPACE_DECL", "A(z) {0} előtaghoz nem tartozik névtér deklaráció" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Elem tartalom nem megengedett a(z) {0} lang=javaclass-hoz" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Stíluslap által irányított leállás" }, { "ER_ONE_OR_TWO", "1 vagy 2" }, { "ER_TWO_OR_THREE", "2 vagy 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Nem lehet betölteni a(z) {0}-t (ellenőrizze a CLASSPATH-t), most csak az alapértelmezéseket használjuk" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Nem lehet inicializálni az alapértelmezett sablonokat" }, { "ER_RESULT_NULL", "Az eredmény nem lehet null" }, { "ER_RESULT_COULD_NOT_BE_SET", "Nem lehet beállítani az eredményt" }, { "ER_NO_OUTPUT_SPECIFIED", "Nem adott meg kimenetet" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Nem alakítható át {0} típusú eredménnyé" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "A(z) {0} típusú forrás nem alakítható át " }, { "ER_NULL_CONTENT_HANDLER", "Null tartalomkezelő" }, { "ER_NULL_ERROR_HANDLER", "Null hibakezelő" }, { "ER_CANNOT_CALL_PARSE", "A parse nem hívható meg, ha a ContentHandler-t nem állította be" }, { "ER_NO_PARENT_FOR_FILTER", "A szűrőnek nincs szülője" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Nincs stíluslap ebben: {0}, adathordozó: {1}" }, { "ER_NO_STYLESHEET_PI", "Nem található xml-stylesheet PI itt: {0}" }, { "ER_NOT_SUPPORTED", "Nem támogatott: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "A(z) {0} tulajdonság értéke Boolean példány kell legyen" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Nem lehet eljutni a külső parancsfájlhoz a(z) {0}-n" }, { "ER_RESOURCE_COULD_NOT_FIND", "A(z) [ {0} ] erőforrás nem található.\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "A kimeneti tulajdonság nem felismerhető: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Nem sikerült ElemLiteralResult példányt létrehozni" }, { "ER_VALUE_SHOULD_BE_NUMBER", "A(z) {0} tulajdonság értéke értelmezhető szám kell legyen" }, { "ER_VALUE_SHOULD_EQUAL", "A(z) {0} értéke igen vagy nem kell legyen" }, { "ER_FAILED_CALLING_METHOD", "Nem sikerült meghívni a(z) {0} metódust" }, { "ER_FAILED_CREATING_ELEMTMPL", "Nem sikerült ElemTemplateElement példányt létrehozni" }, { "ER_CHARS_NOT_ALLOWED", "Karakterek nem megengedettek a dokumentumnak ezen a pontján" }, { "ER_ATTR_NOT_ALLOWED", "A(z) \"{0}\" attribútum nem megengedett a(z) {1} elemhez!" }, { "ER_BAD_VALUE", "{0} rossz érték {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "{0} attribútum érték nem található " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "{0} attribútum érték ismeretlen " }, { "ER_NULL_URI_NAMESPACE", "Kísérlet egy névtér előtag létrehozására null URI-val" }, { "ER_NUMBER_TOO_BIG", "Kísérlet egy szám megformázására, ami nagyobb, mint a legnagyobb Long egész" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Nem található a(z) {0} SAX1 meghajtóosztály" }, { "ER_SAX1_DRIVER_NOT_LOADED", "A(z) {0} SAX1 meghajtóosztály megvan, de nem tölthető be" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "A(z) {0} SAX1 meghajtóosztály betöltve, de nem lehet példányt létrehozni belőle" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "A(z) {0} SAX1 meghajtóosztály nem implementálja az org.xml.sax.Parser-t" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Nem adta meg az org.xml.sax.parser rendszertulajdonságot" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "Az értelmező argumentuma nem lehet null" }, { "ER_FEATURE", "Képesség: {0}" }, { "ER_PROPERTY", "Tulajdonság: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Null entitás feloldó" }, { "ER_NULL_DTD_HANDLER", "Null DTD kezelő" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Nem adott meg meghajtónevet!" }, { "ER_NO_URL_SPECIFIED", "Nem adott meg URL-t!" }, { "ER_POOLSIZE_LESS_THAN_ONE", "A tároló mérete 1-nél kisebb!" }, { "ER_INVALID_DRIVER_NAME", "Érvénytelen meghajtónevet adott meg!" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Programozói hiba! A kifejezésnek nincs ElemTemplateElement szülője!" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Programozói értesítés a RedundentExprEliminator hívásban: {0} " }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} nem engedélyezett a stíluslap ezen helyén!" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Nem-szeparátor szöveg nem megengedett a stíluslap ezen helyén!" }, { "INVALID_TCHAR", "Tiltott értéket használt a(z) {0} attribútumhoz: {1}.  A CHAR típusú attribútum csak 1 karakter lehet!" }, { "INVALID_QNAME", "Tiltott értéket használt a(z) {0} CHAR attribútumhoz: {1}." }, { "INVALID_ENUM", "Tiltott értéket használt a(z) {0} ENUM attribútumhoz: {1}.  Az érvényes értékek: {2}." }, { "INVALID_NMTOKEN", "Tiltott értéket használt a(z) {0} NMTOKEN attribútumhoz: {1}. " }, { "INVALID_NCNAME", "Tiltott értéket használt a(z) {0} NCNAME attribútumhoz: {1}. " }, { "INVALID_BOOLEAN", "Tiltott értéket használt a(z) {0} logikai attribútumhoz: {1}. " }, { "INVALID_NUMBER", "Tiltott értéket használt a(z) {0} szám attribútumhoz: {1}. " }, { "ER_ARG_LITERAL", "A(z) {0} argumentuma az illeszkedési mintában egy literál kell legyen." }, { "ER_DUPLICATE_GLOBAL_VAR", "Kétszer szerepel a globális változó-deklaráció." }, { "ER_DUPLICATE_VAR", "Kétszer szerepel a változó-deklaráció." }, { "ER_TEMPLATE_NAME_MATCH", "Az xsl:template-nek kell legyen neve vagy illeszkedési attribútuma (vagy mindkettő)" }, { "ER_INVALID_PREFIX", "Az előtag az exclude-result-prefixes-ben nem érvényes: {0}" }, { "ER_NO_ATTRIB_SET", "A(z) {0} nevű attribute-set nem létezik" }, { "ER_FUNCTION_NOT_FOUND", "A(z) {0} nevű funkció nem létezik" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "A(z) {0} elemnek nem lehet egyszerre content és select attribútuma." }, { "ER_INVALID_SET_PARAM_VALUE", "A(z) {0} paraméter értéke egy érvényes Jáva objektum kell legyen" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "Az xsl:namespace-alias elem result-prefix részének értéke '#default', de nincs meghatározva alapértelmezett névtér az elem hatókörében. " }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "Egy xsl:namespace-alias elem result-prefix attribútumának értéke ''{0}'', de nincs névtér deklaráció a(z) ''{0}'' előtaghoz az elem hatókörében. " }, { "ER_SET_FEATURE_NULL_NAME", "A szolgáltatás neve nem lehet null a TransformerFactory.setFeature(String name, boolean value) metódusban." }, { "ER_GET_FEATURE_NULL_NAME", "A szolgáltatás neve nem lehet null a TransformerFactory.getFeature(String name) metódusban." }, { "ER_UNSUPPORTED_FEATURE", "A(z) ''{0}'' szolgáltatás nem állítható be ehhez a TransformerFactory osztályhoz." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "A(z) ''{0}'' kiterjesztési elem használata nem megengedett, ha biztonságos feldolgozás be van kapcsolva. " }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Nem lehet beolvasni az előtagot null névtér URI esetén. " }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Nem olvasható be a névtér null előtag miatt. " }, { "ER_XPATH_RESOLVER_NULL_QNAME", "A függvény neve nem lehet null." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "Az aritás nem lehet negatív." }, { "WG_FOUND_CURLYBRACE", "'}'-t találtunk, de nincs attribútumsablon megnyitva!" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Figyelmeztetés: A count attribútum nem felel meg a egy felmenőnek az xsl:number-ben! Cél = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Régi szintaktika: Az 'expr' attribútum neve 'select'-re változott." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Az Xalan még nem kezeli a locale nevet a format-number függvényben." }, { "WG_LOCALE_NOT_FOUND", "Figyelmeztetés: Nem található az xml:lang={0} értékhez tartozó locale" }, { "WG_CANNOT_MAKE_URL_FROM", "Nem készíthető URL ebből: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "A kér dokumentum nem tölthető be: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Nem található Collator a <sort xml:lang={0}-hez" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Régi szintaktika: a functions utasítás {0} URL-t kell használjon" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "a kódolás nem támogatott: {0}, UTF-8-at használunk" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "a kódolás nem támogatott: {0}, Java {1}-t használunk" }, { "WG_SPECIFICITY_CONFLICTS", "Specifikussági konfliktust találtunk: {0} A stíluslapon legutoljára megtaláltat használjuk." }, { "WG_PARSING_AND_PREPARING", "========= {0} elemzése és előkészítése ==========" }, { "WG_ATTR_TEMPLATE", "Attr sablon, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Illesztési konfliktus az xsl:strip-space és az xsl:preserve-space között" }, { "WG_ATTRIB_NOT_HANDLED", "A Xalan még nem kezeli a(z) {0} attribútumot!" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Nem találtuk meg a deklarációt a decimális formátumhoz: {0}" }, { "WG_OLD_XSLT_NS", "Hiányzó vagy helytelen XSLT névtér. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Csak az alapértelmezett xsl:decimal-format deklaráció megengedett." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Az xsl:decimal-format neveknek egyedieknek kell lenniük. A(z) \"{0}\" név meg lett ismételve." }, { "WG_ILLEGAL_ATTRIBUTE", "A(z) {0}-nak érvénytelen attribútuma van: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Nem lehet feloldani a névtér előtagot: {0}. A csomópont figyelmen kívül marad." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "Az xsl:stylesheet-nek kell legyen 'version' attribútuma!" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Nem megengedett attribútumnév: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Tiltott értéket használt a(z) {0} attribútumhoz: {1}" }, { "WG_EMPTY_SECOND_ARG", "A document függvény második argumentumából előálló csomóponthalmaz üres. Üres node-készletetet adok vissza." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "A(z) xsl:processing-instruction  név 'name' attribútuma nem lehet 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "A(z) xsl:processing-instruction  név ''name'' attribútuma érvényes NCName kell legyen: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Nem lehet {0} attribútumat felvenni a gyermek node-ok után vagy mielőtt egy elem létrejönne.  Az attribútum figyelmen kívül marad." }, { "NO_MODIFICATION_ALLOWED_ERR", "Kísérlet történt egy objektum módosítására, ahol a módosítások nem megengedettek. " }, { "ui_language", "hu" }, { "help_language", "hu" }, { "language", "hu" }, { "BAD_CODE", "A createMessage paramétere nincs a megfelelő tartományban" }, { "FORMAT_FAILED", "Kivétel történt a messageFormat hívás alatt" }, { "version", ">>>>>>> Xalan verzió " }, { "version2", "<<<<<<<" }, { "yes", "igen" }, { "line", "Sor #" }, { "column", "Oszlop #" }, { "xsldone", "XSLProcessor: kész" }, { "xslProc_option", "Xalan-J parancssori Process osztály opciók:" }, { "xslProc_option", "Xalan-J parancssori Process osztály opciók:" }, { "xslProc_invalid_xsltc_option", "A(z) {0} opció nem támogatott XSLTC módban." }, { "xslProc_invalid_xalan_option", "A(z) {0} opció csak -XSLTC-vel együtt használható." }, { "xslProc_no_input", "Hiba: Nem adott meg stíluslapot vagy bemeneti xml-t. Futtassa ezt a parancsot kapcsolók nélkül a használati utasítások megjelenítésére." }, { "xslProc_common_options", "-Általános opciók-" }, { "xslProc_xalan_options", "-Xalan opciók-" }, { "xslProc_xsltc_options", "-XSLTC opciók-" }, { "xslProc_return_to_continue", "(nyomja la a <return> gombot a folytatáshoz)" }, { "optionXSLTC", "   [-XSLTC (XSLTC-t használ a transzformáláshoz)]" }, { "optionIN", "   [-IN bemenetiXMLURL]" }, { "optionXSL", "   [-XSL XSLTranszformációsURL]" }, { "optionOUT", "   [-OUT kimenetiFájlnév]" }, { "optionLXCIN", "   [-LXCIN lefordítottstíluslapFájlnévBe]" }, { "optionLXCOUT", "   [-LXCOUT lefordítottStíluslapFájlnévKi]" }, { "optionPARSER", "   [-PARSER az értelmezőkapcsolat teljesen meghatározott osztályneve]" }, { "optionE", "   [-E (Nem bontja ki az entitás hivatkozásokat)]" }, { "optionV", "   [-E (Nem bontja ki az entitás hivatkozásokat)]" }, { "optionQC", "   [-QC (Csendes mintakonfliktus figyelmeztetések)]" }, { "optionQ", "   [-Q  (Csendes mód)]" }, { "optionLF", "   [-LF (A soremeléseket csak kimenet esetén használja {alapértelmezés: CR/LF})]" }, { "optionCR", "   [-CR (A kocsivissza karaktert csak kimenet esetén használja {alapértelmezés: CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (Mely karaktereket kell escape-elni {alapértelmezés: <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (Meghatározza, hogy hány szóközzel kell beljebb kezdeni {alapértelmezés: 0})]" }, { "optionTT", "   [-TT (Nyomköveti a sablonokat, ahogy azokat meghívják.)]" }, { "optionTG", "   [-TG (Nyomköveti az összes generálási eseményt.)]" }, { "optionTS", "   [-TS (Nyomköveti az összes kiválasztási eseményt.)]" }, { "optionTTC", "   [-TTC (Nyomköveti a sablon-leszármazottakat, ahogy azokat feldolgozzák.)]" }, { "optionTCLASS", "   [-TCLASS (TraceListener osztály a nyomkövetési kiterjesztésekhez.)]" }, { "optionVALIDATE", "   [-VALIDATE (Beállítja, hogy legyen-e érvényességvizsgálat.  Alapértelmezésben nincs érvényességvizsgálat.)]" }, { "optionEDUMP", "   [-EDUMP {opcionális fájlnév} (Hibánál stackdump-ot hajt végre.)]" }, { "optionXML", "   [-XML (XML formázó használata és XML fejléc hozzáadása.)]" }, { "optionTEXT", "   [-TEXT (Egyszerű szövegformázó használata.)]" }, { "optionHTML", "   [-HTML (HTML formázó használata.)]" }, { "optionPARAM", "   [-PARAM név kifejezés (Beállít egy stíluslap paramétert)]" }, { "noParsermsg1", "Az XSL folyamat sikertelen volt." }, { "noParsermsg2", "** Az értelmező nem található **" }, { "noParsermsg3", "Kérem, ellenőrizze az osztály elérési utat." }, { "noParsermsg4", "Ha önnek nincs meg az IBM Java XML értelmezője, akkor letöltheti az" }, { "noParsermsg5", "az IBM AlphaWorks weblapról: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER teljes osztálynév (az URIResolver fogja feloldani az URI-kat)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER teljes osztálynév (az EntityResolver fogja feloldani az entitásokat)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER teljes osztálynév (a ContentHandler fogja sorosítani a kimenetet)]" }, { "optionLINENUMBERS", "   [-L sorszámokat használ a forrásdokumentumhoz]" }, { "optionSECUREPROCESSING", "   [-SECURE (biztonságos feldolgozás szolgáltatás igazra állítása.)]" }, { "optionMEDIA", "   [-MEDIA adathordozóTípus (a media attribútum segítségével megkeresi a dokumentumhoz tartozó stíluslapot.)]" }, { "optionFLAVOR", "   [-FLAVOR ízlésNév (Explicit használja az s2s=SAX-ot vagy d2d=DOM-ot a transzformációhoz.)] " }, { "optionDIAG", "   [-DIAG (Kiírja, hogy összesen hány ezredmásodpercig tartott a transzformáció.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (növekményes DTM létrehozást igényel a http://xml.apache.org/xalan/features/incremental igazra állításával.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (nem igényel stíluslap optimizálást a http://xml.apache.org/xalan/features/optimize hamisra állítását.)]" }, { "optionRL", "   [-RL rekurziókorlát (numerikusan korlátozza a stíluslap rekurzió mélységét.)]" }, { "optionXO", "   [-XO [transletNeve] (a nevet rendeli a generált translethez)]" }, { "optionXD", "   [-XD célAlkönyvtár (a translet cél-alkönyvtára)]" }, { "optionXJ", "   [-XJ jarfájl (a translet osztályokat a megadott <jarfájl>-ba csomagolja)]" }, { "optionXP", "   [-XP csomag (megadja a generált translet osztályok név-prefixét)]" }, { "optionXN", "   [-XN (engedélyezi a template inlining optimalizálást)]" }, { "optionXX", "   [-XX (bekapcsolja a további hibakeresési kimenetet)]" }, { "optionXT", "   [-XT (translet-et használt az átalakításhoz, ha lehet)]" }, { "diagTiming", " --------- A(z) {0} tarnszformációa a(z) {1}-el {2} ms-ig tartott" }, { "recursionTooDeep", "A sablonon egymásba ágyazása túl mély. Beágyazás = {0}, sablon: {1} {2}" }, { "nameIs", "A név:" }, { "matchPatternIs", "Az illeszkedési minta:" } };
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
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("hu", "HU"));
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_hu
 * JD-Core Version:    0.7.0.1
 */