/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_it
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
/*  264:     */   public static final String ERROR_HEADER = "Errore: ";
/*  265:     */   public static final String WARNING_HEADER = "Avvertenza: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "MODELLO ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Errore: '{' non può essere contenuto in un'espressione" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} ha un attributo non valido: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode nullo in xsl:apply-imports!" }, { "ER_CANNOT_ADD", "Impossibile aggiungere {0} a {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode nullo in handleApplyTemplatesInstruction." }, { "ER_NO_NAME_ATTRIB", "{0} deve avere un attributo name." }, { "ER_TEMPLATE_NOT_FOUND", "Impossibile trovare la maschera: {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Impossibile risolvere il nome AVT in xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "{0} richiede l''''attributo: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} deve avere un attributo ''test''." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Valore errato nell''''attributo livello: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Il nome dell'istruzione di elaborazione non può essere 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "il nome dell''''istruzione di elaborazione deve essere un NCName valido: {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} deve avere un attributo match nel caso abbia un modo." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} richiede un attributo match o name." }, { "ER_CANT_RESOLVE_NSPREFIX", "Impossibile risolvere il prefisso dello namespace: {0}" }, { "ER_ILLEGAL_VALUE", "xml:space ha un valore non valido: {0}" }, { "ER_NO_OWNERDOC", "Il nodo secondario non ha un documento proprietario." }, { "ER_ELEMTEMPLATEELEM_ERR", "Errore ElemTemplateElement: {0}" }, { "ER_NULL_CHILD", "È stato effettuato un tentativo di aggiungere un secondario nullo." }, { "ER_NEED_SELECT_ATTRIB", "{0} richiede un attributo select." }, { "ER_NEED_TEST_ATTRIB", "xsl:when deve avere un attributo 'test'." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param deve avere un attributo 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "il contesto non ha un documento proprietario." }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Impossibile creare XML TransformerFactory Liaison: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: Processo non eseguito correttamente." }, { "ER_NOT_SUCCESSFUL", "Xalan: non eseguito correttamente." }, { "ER_ENCODING_NOT_SUPPORTED", "Codifica non supportata: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Impossibile creare TraceListener: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key richiede un attributo 'name'." }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key richiede un attributo 'match'." }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key richiede un attributo 'use'." }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} richiede un attributo ''elements''." }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0} attributo ''prefix'' mancante" }, { "ER_BAD_STYLESHEET_URL", "URL del foglio di lavoro errato: {0}" }, { "ER_FILE_NOT_FOUND", "File del foglio di lavoro non trovato: {0}" }, { "ER_IOEXCEPTION", "Eccezione IO nel file del foglio di lavoro: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Impossibile trovare l''''attributo href per {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} sta direttamente o indirettamente includendo se stesso." }, { "ER_PROCESSINCLUDE_ERROR", "Errore StylesheetHandler.processInclude, {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0} attributo ''lang'' mancante" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) elemento {0} non ubicato correttamente. Elemento contenitore ''component'' mancante " }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "L'emissione è consentita solo in un elemento, frammento di documento, documento o stampante." }, { "ER_PROCESS_ERROR", "Errore StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Errore UnImplNode: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Errore! Impossibile trovare espressione selezione xpath (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Impossibile serializzare XSLProcessor!" }, { "ER_NO_INPUT_STYLESHEET", "Input del foglio di lavoro non specificato." }, { "ER_FAILED_PROCESS_STYLESHEET", "Impossibile elaborare il foglio di lavoro." }, { "ER_COULDNT_PARSE_DOC", "Impossibile analizzare il documento {0}." }, { "ER_COULDNT_FIND_FRAGMENT", "Impossibile trovare il frammento: {0}" }, { "ER_NODE_NOT_ELEMENT", "Il nodo a cui fa riferimento l''''identificativo del frammento non è un elemento: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each deve avere un attributo match o name" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "le maschere devono avere un attributo match o name" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Non è possibile avere un clone di un frammento di documento." }, { "ER_CANT_CREATE_ITEM", "Impossibile creare la voce nella struttura dei risultati: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space in XML di origine ha un valore non valido: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Nessuna dichiarazione xsl:key per {0}!" }, { "ER_CANT_CREATE_URL", "Errore! Impossibile creare url per: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions non supportato" }, { "ER_PROCESSOR_ERROR", "Errore XSLT TransformerFactory" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} non consentito nel foglio di lavoro." }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns non è più supportato.  Utilizzare xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space non è più supportato.  Utilizzare xsl:strip-space oppure xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result non è più supportato.  Utilizzare xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} ha un attributo non valido: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Elemento XSL sconosciuto: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort può essere utilizzato solo con xsl:apply-templates oppure xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when posizionato in modo non corretto." }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when non reso principale da xsl:choose!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise posizionato in modo non corretto." }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise non reso principale da xsl:choose!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} non è consentito in una maschera." }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0} prefisso namespace estensione {1} sconosciuto" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Le importazioni possono verificarsi solo come primi elementi nel foglio di lavoro." }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} sta direttamente o indirettamente importando se stesso." }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space ha un valore non valido: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet con esito negativo." }, { "ER_SAX_EXCEPTION", "Eccezione SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funzione non supportata." }, { "ER_XSLT_ERROR", "Errore XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "il simbolo della valuta non è consentito nella stringa modello formato." }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "La funzione documento non è supportata nel DOM del foglio di lavoro." }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Impossibile risolvere il prefisso di un resolver non di prefisso." }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Redirect extension: Impossibile richiamare il nome file - l'attributo file o select deve restituire una stringa valida." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Impossibile creare FormatterListener in Redirect extension!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Prefisso in exclude-result-prefixes non valido: {0}" }, { "ER_MISSING_NS_URI", "URI spazio nome mancante per il prefisso specificato" }, { "ER_MISSING_ARG_FOR_OPTION", "Argomento mancante per l''''opzione: {0}" }, { "ER_INVALID_OPTION", "Opzione non valida: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Stringa di formato errato: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet richiede un attributo 'version'." }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "L''attributo: {0} ha un valore non valido: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose richiede xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports non consentito in xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Impossibile utilizzare DTMLiaison per un nodo DOM di output... utilizzare invece org.apache.xpath.DOM2Helper." }, { "ER_CANT_USE_DTM_FOR_INPUT", "Impossibile utilizzare DTMLiaison per un nodo DON di input... utilizzare invece org.apache.xpath.DOM2Helper." }, { "ER_CALL_TO_EXT_FAILED", "Chiamata all''''elemento estensione non riuscita: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "Il prefisso deve risolvere in uno namespace: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Rilevato surrogato UTF-16 non valido: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} sta utilizzando se stesso, determinando un loop infinito." }, { "ER_CANNOT_MIX_XERCESDOM", "Impossibile unire input non Xerces-DOM con output Xerces-DOM." }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "In ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Sono state rilevate più maschere denominate: {0}" }, { "ER_INVALID_KEY_CALL", "Chiamata funzione non valida: le chiamate key() ricorsive non sono consentite" }, { "ER_REFERENCING_ITSELF", "La variabile {0} sta direttamente o indirettamente facendo riferimento a se stessa." }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "Il nodo di input non può essere nullo per DOMSource per newTemplates." }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "File di classe non trovato per l''opzione {0}" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Elemento richiesto non trovato: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream non può essere nullo" }, { "ER_URI_CANNOT_BE_NULL", "URI non può essere nullo" }, { "ER_FILE_CANNOT_BE_NULL", "File non può essere nullo" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource non può essere nullo" }, { "ER_CANNOT_INIT_BSFMGR", "Impossibile inizializzare BSF Manager" }, { "ER_CANNOT_CMPL_EXTENSN", "Impossibile compilare l'estensione" }, { "ER_CANNOT_CREATE_EXTENSN", "Impossibile creare l''''estensione: {0} a causa di: {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "La chiamata metodo istanza al metodo {0} richiede un''istanza Object come primo argomento" }, { "ER_INVALID_ELEMENT_NAME", "Specificato nome elemento non valido{0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "Il metodo nome elemento deve essere statico {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "Funzione estensione {0} : {1} sconosciuta" }, { "ER_MORE_MATCH_CONSTRUCTOR", "È stata trovata più di una corrispondenza migliore per il costruttore per {0}" }, { "ER_MORE_MATCH_METHOD", "È stata trovata più di una corrispondenza migliore per il metodo {0}" }, { "ER_MORE_MATCH_ELEMENT", "È stata trovata più di una corrispondenza migliore per il metodo elemento {0}" }, { "ER_INVALID_CONTEXT_PASSED", "Specificato contesto non valido per valutare {0}" }, { "ER_POOL_EXISTS", "Pool già esistente" }, { "ER_NO_DRIVER_NAME", "Non è stato specificato alcun Nome driver" }, { "ER_NO_URL", "Non è stata specificata alcuna URL" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "La dimensione del pool è inferiore a uno." }, { "ER_INVALID_DRIVER", "Specificato nome driver non valido." }, { "ER_NO_STYLESHEETROOT", "Impossibile trovare la root del foglio di lavoro." }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Valore non valido per xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode non riuscito" }, { "ER_RESOURCE_COULD_NOT_LOAD", "Impossibile caricare la risorsa [ {0} ]: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Dimensione buffer <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Errore sconosciuto durante la chiamata all'estensione" }, { "ER_NO_NAMESPACE_DECL", "Il prefisso {0} non ha una dichiarazione namaspace corrispondente" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Contenuto elemento non consentito per lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Il foglio di lavoro ha indirizzato l'interruzione" }, { "ER_ONE_OR_TWO", "1 o 2" }, { "ER_TWO_OR_THREE", "2 o 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Impossibile caricare {0} (controllare CLASSPATH), verranno utilizzati i valori predefiniti." }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Impossibile inizializzare le maschere predefinite" }, { "ER_RESULT_NULL", "Il risultato non può essere nullo" }, { "ER_RESULT_COULD_NOT_BE_SET", "Impossibile impostare il risultato" }, { "ER_NO_OUTPUT_SPECIFIED", "Non è stato specificato alcun output" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Impossibile trasformare in un risultato di tipo {0}" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Impossibile trasformare in un''origine di tipo {0}" }, { "ER_NULL_CONTENT_HANDLER", "Handler contenuto nullo" }, { "ER_NULL_ERROR_HANDLER", "Handler errori nullo" }, { "ER_CANNOT_CALL_PARSE", "non è possibile richiamare l'analisi se ContentHandler non è stato impostato" }, { "ER_NO_PARENT_FOR_FILTER", "Nessun principale per il filtro" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Nessun foglio di lavoro trovato in: {0}, supporto= {1}" }, { "ER_NO_STYLESHEET_PI", "Nessun PI xml-stylesheet trovato in: {0}" }, { "ER_NOT_SUPPORTED", "Non supportato: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "Il valore della proprietà {0} deve essere una istanza booleana" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Impossibile richiamare lo script esterno in {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "Risorsa [ {0} ] non trovata.\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Proprietà Output non riconosciuta: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Creazione dell'istanza ElemLiteralResult non riuscita" }, { "ER_VALUE_SHOULD_BE_NUMBER", "Il valore di {0} deve contenere un numero analizzabile" }, { "ER_VALUE_SHOULD_EQUAL", "Il valore di {0} deve essere uguale a yes o no" }, { "ER_FAILED_CALLING_METHOD", "Chiamata al metodo {0} non riuscita" }, { "ER_FAILED_CREATING_ELEMTMPL", "Creazione dell'istanza ElemTemplateElement non riuscita" }, { "ER_CHARS_NOT_ALLOWED", "I caratteri non sono consentiti in questo punto del documento" }, { "ER_ATTR_NOT_ALLOWED", "L''''attributo \"{0}\" non è consentito nell''''elemento {1}." }, { "ER_BAD_VALUE", "{0} valore errato {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Valore attributo {0} non trovato " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Valore attributo {0} non riconosciuto " }, { "ER_NULL_URI_NAMESPACE", "È stato effettuato un tentativo di generare un prefisso spazio nome con un URI nullo" }, { "ER_NUMBER_TOO_BIG", "Si sta effettuando un tentativo di formattare un numero superiore all'intero Long più grande" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Impossibile trovare la classe driver SAX1 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "La classe driver SAX1 {0} è stata trovata ma non è stato possibile caricarla" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "La classe driver SAX1 {0} è stata caricata ma non è stato possibile istanziarla" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "La classe driver SAX1 {0} non implementa org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Proprietà di sistema org.xml.sax.parser non specificata" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "L'argomento Parser non può essere nullo" }, { "ER_FEATURE", "Funzione: {0}" }, { "ER_PROPERTY", "Proprietà: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Resolver entità nullo" }, { "ER_NULL_DTD_HANDLER", "Handler DTD nullo" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Non è stato specificato alcun nome driver." }, { "ER_NO_URL_SPECIFIED", "Non è stato specificato alcun URL." }, { "ER_POOLSIZE_LESS_THAN_ONE", "La dimensione del pool è inferiore a 1." }, { "ER_INVALID_DRIVER_NAME", "Specificato nome driver non valido." }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Errore di programmazione. Espressione senza ElemTemplateElement principale" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Asserzione del programmatore in RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0}non è consentito in questa posizione in stylesheet" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Testo Non-whitespace non consentito in questa posizione in stylesheet" }, { "INVALID_TCHAR", "Valore non valido: {1} utilizzato per l''''attributo CHAR: {0}.  Un attributo di tipo CHAR deve essere di 1 solo carattere." }, { "INVALID_QNAME", "Valore non valido: {1} utilizzato per l''''attributo QNAME: {0}" }, { "INVALID_ENUM", "Valore non valido: {1} utilizzato per l''''attributo ENUM: {0}.  I valori validi sono: {2}." }, { "INVALID_NMTOKEN", "Valore non valido: {1} utilizzato per l''''attributo NMTOKEN: {0} " }, { "INVALID_NCNAME", "Valore non valido: {1} utilizzato per l''''attributo NCNAME: {0} " }, { "INVALID_BOOLEAN", "Valore non valido: {1} utilizzato per l''''attributo boolean: {0} " }, { "INVALID_NUMBER", "Valore non valido: {1} utilizzato per l''''attributo number: {0} " }, { "ER_ARG_LITERAL", "L''''argomento di {0} nel modello di corrispondenza deve essere letterale." }, { "ER_DUPLICATE_GLOBAL_VAR", "Dichiarazione di variabile globale duplicata." }, { "ER_DUPLICATE_VAR", "Dichiarazione di variabile duplicata." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template deve avere un attributo name oppure match (o entrambi)" }, { "ER_INVALID_PREFIX", "Prefisso in exclude-result-prefixes non valido: {0}" }, { "ER_NO_ATTRIB_SET", "attribute-set denominato {0} non esiste" }, { "ER_FUNCTION_NOT_FOUND", "La funzione {0} indicata non esiste" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "L''''elemento {0} non deve avere sia un attributo content o selection." }, { "ER_INVALID_SET_PARAM_VALUE", "Il valore del parametro {0} deve essere un oggetto Java valido" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "L'attributo result-prefix si un elemento xsl:namespace-alias ha il valore '#default', ma non c'è dichiarazione dello spazio nome predefinito nell'ambito per l'elemento" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "L''attributo result-prefix di un elemento xsl:namespace-alias ha il valore ''{0}'', ma non c''è dichiarazione dello spazio per il prefisso ''{0}'' nell''ambito per l''elemento." }, { "ER_SET_FEATURE_NULL_NAME", "Il nome della funzione non può essere nullo in TransformerFactory.setFeature(Nome stringa, valore booleano)." }, { "ER_GET_FEATURE_NULL_NAME", "Il nome della funzione non può essere nullo in TransformerFactory.getFeature(Nome stringa)." }, { "ER_UNSUPPORTED_FEATURE", "Impossibile impostare la funzione ''{0}'' su questo TransformerFactory." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "L''''utilizzo di un elemento di estensione ''{0}'' non è consentito quando la funzione di elaborazione sicura è impostata su true." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Impossibile ottenere il prefisso per un uri dello spazio nome nullo." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Impossibile ottenere l'uri dello spazio nome per il prefisso null." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "Il nome della funzione non può essere null." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "Arity non può essere negativo." }, { "WG_FOUND_CURLYBRACE", "Rilevato '}' senza una maschera attributo aperta." }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Attenzione: l''attributo count non corrisponde ad un predecessore in xsl:number! Destinazione = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Sintassi obsoleta: Il nome dell'attributo 'expr' è stato modificato in 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan non gestisce ancora il nome locale nella funzione formato-numero." }, { "WG_LOCALE_NOT_FOUND", "Attenzione: Impossibile trovare la locale per xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "Impossibile ricavare l''''URL da: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Impossibile caricare il documento richiesto: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Impossibile trovare Collator per <sort xml:lang={0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Sintassi obsoleta: l''istruzione functions deve utilizzare un url di {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "codifica non supportata: {0}, viene utilizzato UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "codifica non supportata: {0}, viene utilizzato Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "Sono stati rilevati conflitti di specificità: {0} Verrà utilizzato l''ultimo trovato nel foglio di lavoro." }, { "WG_PARSING_AND_PREPARING", "========= Analisi e preparazione {0} ==========" }, { "WG_ATTR_TEMPLATE", "Maschera attributo, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Conflitto di corrispondenza tra xsl:strip-space e xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan non può ancora gestire l''''attributo {0}." }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Nessuna dichiarazione trovata per il formato decimale: {0}" }, { "WG_OLD_XSLT_NS", "XSLT Namespace mancante o non corretto. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "È consentita una sola dichiarazione xsl:decimal-format predefinita." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "I nomi xsl:decimal-format devono essere univoci. Il nome \"{0}\" è stato duplicato." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} ha un attributo non valido: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Impossibile risolvere il prefisso dello spazio nome: {0}. Il nodo verrà ignorato." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet richiede un attributo 'version'." }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Nome attributo non valido: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Valore non valido utilizzato per l''''attributo {0}: {1}" }, { "WG_EMPTY_SECOND_ARG", "Il nodeset che risulta dal secondo argomento della funzione documento è vuoto. Restituisce un nodeset vuoto." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Il valore dell'attributo 'name' del nome xsl:processing-instruction non deve essere 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Il valore dell''attributo ''name'' di xsl:processing-instruction deve essere un NCName valido: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Impossibile aggiungere l''''attributo {0} dopo i nodi secondari o prima che sia prodotto un elemento.  L''''attributo verrà ignorato." }, { "NO_MODIFICATION_ALLOWED_ERR", "È stato effettuato un tentativo di modificare un oggetto in un contesto in cui le modifiche non sono supportate." }, { "ui_language", "it" }, { "help_language", "it" }, { "language", "it" }, { "BAD_CODE", "Il parametro per createMessage fuori limite" }, { "FORMAT_FAILED", "Rilevata eccezione durante la chiamata messageFormat" }, { "version", ">>>>>>> Versione Xalan " }, { "version2", "<<<<<<<" }, { "yes", "sì" }, { "line", "Riga #" }, { "column", "Colonna #" }, { "xsldone", "XSLProcessor: eseguito" }, { "xslProc_option", "Opzioni classe Process riga comandi Xalan-J:" }, { "xslProc_option", "Opzioni classe Process riga comandi Xalan-J:" }, { "xslProc_invalid_xsltc_option", "Opzione {0} non supportata in modalità." }, { "xslProc_invalid_xalan_option", "L''''opzione {0} può essere utilizzata solo con -XSLTC." }, { "xslProc_no_input", "Errore: Nessun foglio di lavoro o xml di immissione specificato. Eseguire questo comando senza opzioni per istruzioni sull'utilizzo." }, { "xslProc_common_options", "-Opzioni comuni-" }, { "xslProc_xalan_options", "-Opzioni per Xalan-" }, { "xslProc_xsltc_options", "-Opzioni per XSLTC-" }, { "xslProc_return_to_continue", "(premere <invio> per continuare)" }, { "optionXSLTC", "   [-XSLTC (utilizza XSLTC per la trasformazioni)]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "   [-XSL XSLTransformationURL]" }, { "optionOUT", "   [-OUT outputFileName]" }, { "optionLXCIN", "   [-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "   [-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "   [-PARSER nome classe completo del collegamento parser]" }, { "optionE", "   [-E (non espandere i riferimenti entità)]" }, { "optionV", "   [-E (non espandere i riferimenti entità)]" }, { "optionQC", "   [-QC (Silenziamento avvertenze conflitti modelli)]" }, { "optionQ", "   [-Q  (Modo silenzioso)]" }, { "optionLF", "   [-LF (Utilizza il caricamento riga solo sull'output {valore predefinito: CR/LF})]" }, { "optionCR", "   [-CR (Utilizza il ritorno a capo solo sull'output {valore predefinito: CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (specifica quali caratteri saltare {valore predefinito: <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (Controlla il numero dei rientri {valore predefinito: 0})]" }, { "optionTT", "   [-TT (Traccia le maschere quando vengono richiamate.)]" }, { "optionTG", "   [-TG (Traccia ogni evento di generazione.)]" }, { "optionTS", "   [-TS (Traccia ogni evento di selezione.)]" }, { "optionTTC", "   [-TTC (Traccia il secondario della maschera quando viene elaborato.)]" }, { "optionTCLASS", "   [-TCLASS (classe TraceListener per le estensioni di traccia.)]" }, { "optionVALIDATE", "   [-VALIDATE (Imposta se eseguire la convalida.  Il valore predefinito per la convalida è disattivato.)]" }, { "optionEDUMP", "   [-EDUMP {nome file facoltativo} (Eseguire stackdump in caso di errori.)]" }, { "optionXML", "   [-XML (Utilizza la formattazione XML e aggiunge intestazione XML.)]" }, { "optionTEXT", "   [-TEXT (Utilizza la formattazione Testo semplice.)]" }, { "optionHTML", "   [-HTML (Utilizza la formattazione HTML.)]" }, { "optionPARAM", "   [-PARAM nome espressione (imposta un parametro del foglio di lavoro)]" }, { "noParsermsg1", "Elaborazione XSL non riuscita." }, { "noParsermsg2", "** Impossibile trovare il parser **" }, { "noParsermsg3", "Controllare il classpath." }, { "noParsermsg4", "Se non si possiede IBM XML Parser per Java, è possibile scaricarlo da" }, { "noParsermsg5", "IBM AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER nome classe completo (URIResolver da utilizzare per risolvere gli URI)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER nome classe completo (EntityResolver da utilizzare per risolvere le entità)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER nome classe completo (ContentHandler da utilizzare per serializzare l'output)]" }, { "optionLINENUMBERS", "   [-L utilizza i numeri riga per il documento di origine]" }, { "optionSECUREPROCESSING", "   [-SECURE (imposta la funzione di elaborazione sicura su true.)]" }, { "optionMEDIA", "   [-MEDIA mediaType (utilizza l'attributo media per individuare il foglio di lavoro associato ad un documento.)]" }, { "optionFLAVOR", "   [-FLAVOR flavorName (Utilizza in modo esplicito s2s=SAX oppure d2d=DOM per eseguire la trasformazione.)] " }, { "optionDIAG", "   [-DIAG (Visualizza il tempo impiegato in millisecondi per la trasformazione.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (richiede la costruzione DTM incrementale impostando http://xml.apache.org/xalan/features/incremental true.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (non richiede alcuna elaborazione di ottimizzazione del foglio di lavoro impostando http://xml.apache.org/xalan/features/optimize false.)]" }, { "optionRL", "   [-RL recursionlimit (limite numerico asserzioni nella profondità ricorsiva del foglio di lavoro.)]" }, { "optionXO", "   [-XO [transletName] (assegna il nome al translet generato)]" }, { "optionXD", "   [-XD destinationDirectory (specifica una directory di destinazione per il translet)]" }, { "optionXJ", "   [-XJ jarfile (raggruppa la classi translet in un file jar di nome <jarfile>)]" }, { "optionXP", "   [-XP package (specifica un prefisso di nome pacchetto per tutte le classi translet generate)]" }, { "optionXN", "   [-XN (abilita l'allineamento della maschera)]" }, { "optionXX", "   [-XX (attiva ulteriori emissioni di messaggi di debug)]" }, { "optionXT", "   [-XT (utilizza il translet per la trasformazione, se possibile)]" }, { "diagTiming", " --------- La trasformazione di {0} utilizzando {1} ha impiegato {2} ms" }, { "recursionTooDeep", "Nidificazione della maschera troppo elevata. nesting = {0}, maschera {1} {2}" }, { "nameIs", "il nome è" }, { "matchPatternIs", "il modello di corrispondenza è" } };
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
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("it", "IT"));
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_it
 * JD-Core Version:    0.7.0.1
 */