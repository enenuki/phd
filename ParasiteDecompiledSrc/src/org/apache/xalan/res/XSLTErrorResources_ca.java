/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_ca
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
/*  264:     */   public static final String ERROR_HEADER = "Error: ";
/*  265:     */   public static final String WARNING_HEADER = "Avís: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "PATTERN ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 490 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Error: no hi pot haver un caràcter '{' dins l'expressió" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} té un atribut no permès: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode és nul en xsl:apply-imports." }, { "ER_CANNOT_ADD", "No es pot afegir {0} a {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode és nul en handleApplyTemplatesInstruction." }, { "ER_NO_NAME_ATTRIB", "{0} ha de tenir un atribut de nom." }, { "ER_TEMPLATE_NOT_FOUND", "No s''ha trobat la plantilla anomenada: {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "No s'ha pogut resoldre l'AVT de noms a xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "{0} necessita l''atribut: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} ha de tenir un atribut ''test''. " }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Valor incorrecte a l''atribut de nivell: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "El nom processing-instruction no pot ser 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "El nom processing-instruction ha de ser un NCName vàlid: {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} ha de tenir un atribut que hi coincideixi si té una modalitat." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} necessita un nom o un atribut que hi coincideixi." }, { "ER_CANT_RESOLVE_NSPREFIX", "No s''ha pogut resoldre el prefix d''espai de noms: {0}" }, { "ER_ILLEGAL_VALUE", "xml:space té un valor no vàlid: {0}" }, { "ER_NO_OWNERDOC", "El node subordinat no té un document de propietari." }, { "ER_ELEMTEMPLATEELEM_ERR", "Error d''ElemTemplateElement: {0}" }, { "ER_NULL_CHILD", "S'està intentant afegir un subordinat nul." }, { "ER_NEED_SELECT_ATTRIB", "{0} necessita un atribut de selecció." }, { "ER_NEED_TEST_ATTRIB", "xsl:when ha de tenir un atribut 'test'." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param ha de tenir un atribut 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "El context no té un document de propietari." }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "No s''ha pogut crear la relació XML TransformerFactory: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: el procés no ha estat correcte." }, { "ER_NOT_SUCCESSFUL", "Xalan no ha estat correcte." }, { "ER_ENCODING_NOT_SUPPORTED", "La codificació no té suport: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "No s''ha pogut crear TraceListener: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key necessita un atribut 'name'." }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key necessita un atribut 'match'." }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key necessita un atribut 'use'." }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} necessita un atribut ''elements''. " }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) falta l''atribut ''prefix'' {0}. " }, { "ER_BAD_STYLESHEET_URL", "La URL del full d''estils és incorrecta: {0}" }, { "ER_FILE_NOT_FOUND", "No s''ha trobat el fitxer del full d''estils: {0}" }, { "ER_IOEXCEPTION", "S''ha produït una excepció d''E/S amb el fitxer de full d''estils: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) No s''ha trobat l''atribut href de {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} s''està incloent a ell mateix directament o indirecta." }, { "ER_PROCESSINCLUDE_ERROR", "Error de StylesheetHandler.processInclude, {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) falta l''atribut ''lang'' {0}. " }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) L''element {0} és fora de lloc? Falta l''element de contenidor ''component''" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "La sortida només pot ser cap a un Element, Fragment de document, Document o Transcriptor de documents." }, { "ER_PROCESS_ERROR", "Error de StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Error d''UnImplNode: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Error. No s'ha trobat l'expressió select d'xpath (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "No es pot serialitzar un XSLProcessor." }, { "ER_NO_INPUT_STYLESHEET", "No s'ha especificat l'entrada del full d'estils." }, { "ER_FAILED_PROCESS_STYLESHEET", "No s'ha pogut processar el full d'estils." }, { "ER_COULDNT_PARSE_DOC", "No s''ha pogut analitzar el document {0}." }, { "ER_COULDNT_FIND_FRAGMENT", "No s''ha pogut trobar el fragment: {0}" }, { "ER_NODE_NOT_ELEMENT", "El node al qual apuntava l''identificador de fragments no era un element: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each ha de tenir o bé una coincidència o bé un atribut de nom." }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "Les plantilles han de tenir o bé una coincidència o bé un atribut de nom." }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "No hi ha cap clonatge d'un fragment de document." }, { "ER_CANT_CREATE_ITEM", "No es pot crear un element a l''arbre de resultats: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space de l''XML d''origen té un valor no permès: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "No hi ha cap declaració d''xls:key per a {0}." }, { "ER_CANT_CREATE_URL", "Error. No es pot crear la URL de: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions no té suport." }, { "ER_PROCESSOR_ERROR", "Error d'XSLT TransformerFactory" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} no està permès dins d''un full d''estils." }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns ja no té suport. En comptes d'això, feu servir xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space ja no té suport. En comptes d'això, feu servir xsl:strip-space o xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result ja no té suport. En comptes d'això, feu servir xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} té un atribut no permès: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Element XSL desconegut: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort només es pot utilitzar amb xsl:apply-templates o xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when està mal col·locat." }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when no ha estat analitzat per xsl:choose." }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise està mal col·locat." }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise no té com a superior xsl:choose." }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} no està permès dins d''una plantilla." }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0} prefix d''espai de noms d''extensió {1} desconegut" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Les importacions només es poden produir com els primers elements del full d'estils." }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} s''està important a ell mateix directament o indirecta." }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space té un valor no permès: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet no ha estat correcte." }, { "ER_SAX_EXCEPTION", "Excepció SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Aquesta funció no té suport." }, { "ER_XSLT_ERROR", "Error d'XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "El signe de moneda no està permès en una cadena de patró de format." }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "La funció document no té suport al DOM de full d'estils." }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "No es pot resoldre el prefix del solucionador sense prefix." }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Extensió de redirecció: No s'ha pogut obtenir el nom del fitxer - els atributs file o select han de retornar una cadena vàlida." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "No es pot crear build FormatterListener en l'extensió de redirecció." }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "El prefix d''exclude-result-prefixes no és vàlid: {0}" }, { "ER_MISSING_NS_URI", "Falta l'URI d'espai de noms del prefix especificat." }, { "ER_MISSING_ARG_FOR_OPTION", "Falta un argument de l''opció: {0}" }, { "ER_INVALID_OPTION", "Opció no vàlida: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Cadena de format mal formada: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet necessita un atribut 'version'." }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "L''atribut {0} té un valor no permès {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose necessita un xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports no es permeten en un xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "No es pot utilitzar una DTMLiaison per a un node DOM de sortida. En lloc d'això, utilitzeu org.apache.xpath.DOM2Helper." }, { "ER_CANT_USE_DTM_FOR_INPUT", "No es pot utilitzar una DTMLiaison per a un node DOM d'entrada. En lloc d'això, utilitzeu org.apache.xpath.DOM2Helper." }, { "ER_CALL_TO_EXT_FAILED", "S''ha produït un error en la crida de l''element d''extensió {0}" }, { "ER_PREFIX_MUST_RESOLVE", "El prefix s''ha de resoldre en un espai de noms: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "S''ha detectat un suplent UTF-16 no vàlid: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} s''ha utilitzat a ell mateix; això crearà un bucle infinit." }, { "ER_CANNOT_MIX_XERCESDOM", "No es pot barrejar entrada no Xerces-DOM amb sortida Xerces-DOM." }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "En ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "S''ha trobat més d''una plantilla anomenada {0}" }, { "ER_INVALID_KEY_CALL", "Crida de funció no vàlida: les crides key() recursives no estan permeses." }, { "ER_REFERENCING_ITSELF", "La variable {0} està fent referència a ella mateixa directa o indirectament." }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "El node d'entrada no pot ser nul per a DOMSource de newTemplates." }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "No s''ha trobat el fitxer de classe per a l''opció {0}" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "L''element necessari no s''ha trobat: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream no pot ser nul." }, { "ER_URI_CANNOT_BE_NULL", "L'URI no pot ser nul." }, { "ER_FILE_CANNOT_BE_NULL", "El fitxer no pot ser nul." }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource no pot ser nul." }, { "ER_CANNOT_INIT_BSFMGR", "No s'ha pogut inicialitzar BSF Manager" }, { "ER_CANNOT_CMPL_EXTENSN", "No s'ha pogut compilar l'extensió" }, { "ER_CANNOT_CREATE_EXTENSN", "No s''ha pogut crear l''extensió {0} a causa de {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "La crida del mètode d''instància {0} necessita una instància d''objecte com a primer argument" }, { "ER_INVALID_ELEMENT_NAME", "S''ha especificat un nom d''element no vàlid {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "El mètode del nom de l''element ha de ser estàtic {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "No es coneix la funció d''extensió {0} : {1}." }, { "ER_MORE_MATCH_CONSTRUCTOR", "Hi ha més d''una millor coincidència per al constructor de {0}" }, { "ER_MORE_MATCH_METHOD", "Hi ha més d''una millor coincidència per al mètode {0}" }, { "ER_MORE_MATCH_ELEMENT", "Hi ha més d''una millor coincidència per al mètode d''element {0}" }, { "ER_INVALID_CONTEXT_PASSED", "S''ha donat un context no vàlid per avaluar {0}" }, { "ER_POOL_EXISTS", "L'agrupació ja existeix" }, { "ER_NO_DRIVER_NAME", "No s'ha especificat cap nom de controlador" }, { "ER_NO_URL", "No s'ha especificat cap URL" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "La grandària de l'agrupació és inferior a u" }, { "ER_INVALID_DRIVER", "S'ha especificat un nom de controlador no vàlid" }, { "ER_NO_STYLESHEETROOT", "No s'ha trobat l'arrel del full d'estils" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Valor no permès per a xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "S'ha produït un error a processFromNode" }, { "ER_RESOURCE_COULD_NOT_LOAD", "No s''ha pogut carregar el recurs [ {0} ]: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Grandària del buffer <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "S'ha produït un error desconegut en cridar l'extensió" }, { "ER_NO_NAMESPACE_DECL", "El prefix {0} no té una declaració d''espai de noms corresponent" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "El contingut de l''element no està permès per a lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "El full d'estils ha ordenat l'acabament" }, { "ER_ONE_OR_TWO", "1 o 2" }, { "ER_TWO_OR_THREE", "2 o 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "No s''ha pogut carregar {0} (comproveu la CLASSPATH); ara s''estan fent servir els valors per defecte." }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "No es poden inicialitzar les plantilles per defecte" }, { "ER_RESULT_NULL", "El resultat no ha de ser nul" }, { "ER_RESULT_COULD_NOT_BE_SET", "No s'ha pogut establir el resultat" }, { "ER_NO_OUTPUT_SPECIFIED", "No s'ha especificat cap sortida" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "No s''ha pogut transformar en un resultat del tipus {0}" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "No s''ha pogut transformar en un origen del tipus {0}" }, { "ER_NULL_CONTENT_HANDLER", "Manejador de contingut nul" }, { "ER_NULL_ERROR_HANDLER", "Manejador d'error nul" }, { "ER_CANNOT_CALL_PARSE", "L'anàlisi no es pot cridar si no s'ha establert ContentHandler" }, { "ER_NO_PARENT_FOR_FILTER", "El filtre no té superior" }, { "ER_NO_STYLESHEET_IN_MEDIA", "No s''ha trobat cap full d''estils a {0}, suport= {1}" }, { "ER_NO_STYLESHEET_PI", "No s''ha trobat cap PI d''xml-stylesheet a {0}" }, { "ER_NOT_SUPPORTED", "No té suport: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "El valor de la propietat {0} ha de ser una instància booleana" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "No s''ha pogut arribar a l''script extern a {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "No s''ha trobat el recurs [ {0} ].\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "La propietat de sortida no es reconeix: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "S'ha produït un error en crear la instància ElemLiteralResult" }, { "ER_VALUE_SHOULD_BE_NUMBER", "El valor de {0} ha de contenir un número que es pugui analitzar" }, { "ER_VALUE_SHOULD_EQUAL", "El valor de {0} ha de ser igual a yes o no" }, { "ER_FAILED_CALLING_METHOD", "No s''ha pogut cridar el mètode {0}" }, { "ER_FAILED_CREATING_ELEMTMPL", "No s'ha pogut crear la instància ElemTemplateElement" }, { "ER_CHARS_NOT_ALLOWED", "En aquest punt del document no es permeten els caràcters" }, { "ER_ATTR_NOT_ALLOWED", "L''atribut \"{0}\" no es permet en l''element {1}" }, { "ER_BAD_VALUE", "{0} valor erroni {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "No s''ha trobat el valor de l''atribut {0} " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "No es reconeix el valor de l''atribut {0} " }, { "ER_NULL_URI_NAMESPACE", "S'intenta generar un prefix d'espai de noms amb un URI nul" }, { "ER_NUMBER_TOO_BIG", "S'intenta formatar un número més gran que l'enter llarg més gran" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "No es pot trobar la classe de controlador SAX1 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "S''ha trobat la classe de controlador SAX1 {0} però no es pot carregar" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "S''ha carregat la classe de controlador SAX1 {0} però no es pot particularitzar" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "La classe de controlador SAX1 {0} no implementa org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "No s'ha identificat la propietat del sistema org.xml.sax.parser" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "L'argument d'analitzador ha de ser nul" }, { "ER_FEATURE", "Característica: {0}" }, { "ER_PROPERTY", "Propietat: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Solucionador d'entitat nul" }, { "ER_NULL_DTD_HANDLER", "Manejador de DTD nul" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "No s'ha especificat cap nom de controlador" }, { "ER_NO_URL_SPECIFIED", "No s'ha especificat cap URL" }, { "ER_POOLSIZE_LESS_THAN_ONE", "La grandària de l'agrupació és inferior a 1" }, { "ER_INVALID_DRIVER_NAME", "S'ha especificat un nom de controlador no vàlid" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Error del programador. L'expressió no té cap superior ElemTemplateElement " }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "L''afirmació del programador a RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} no es permet en aquesta posició del full d''estil" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "No es permet text sense espais en blanc en aquesta posició del full d'estil" }, { "INVALID_TCHAR", "S''ha utilitzat un valor no permès {1} per a l''atribut CHAR {0}. Un atribut de tipus CHAR només ha de contenir un caràcter." }, { "INVALID_QNAME", "S''ha utilitzat un valor no permès {1} per a l''atribut QNAME {0}" }, { "INVALID_ENUM", "S''ha utilitzat un valor no permès {1} per a l''atribut ENUM {0}. Els valors vàlids són {2}." }, { "INVALID_NMTOKEN", "S''ha utilitzat un valor no permès {1} per a l''atribut NMTOKEN {0} " }, { "INVALID_NCNAME", "S''ha utilitzat un valor no permès {1} per a l''atribut NCNAME {0} " }, { "INVALID_BOOLEAN", "S''ha utilitzat un valor no permès {1} per a l''atribut boolean {0} " }, { "INVALID_NUMBER", "S''ha utilitzat un valor no permès {1} per a l''atribut number {0} " }, { "ER_ARG_LITERAL", "L''argument de {0} del patró de coincidència ha de ser un literal." }, { "ER_DUPLICATE_GLOBAL_VAR", "La declaració de variable global està duplicada." }, { "ER_DUPLICATE_VAR", "La declaració de variable està duplicada." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template ha de tenir un nom o un atribut de coincidència (o tots dos)" }, { "ER_INVALID_PREFIX", "El prefix d''exclude-result-prefixes no és vàlid: {0}" }, { "ER_NO_ATTRIB_SET", "attribute-set anomenat {0} no existeix" }, { "ER_FUNCTION_NOT_FOUND", "La funció anomenada {0} no existeix" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "L''element {0} no ha de tenir ni l''atribut content ni el select. " }, { "ER_INVALID_SET_PARAM_VALUE", "El valor del paràmetre {0} ha de ser un objecte Java vàlid " }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "L'atribut result-prefix d'un element xsl:namespace-alias té el valor '#default', però no hi ha cap declaració de l'espai de noms per defecte en l'àmbit de l'element " }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "L''atribut result-prefix d''un element xsl:namespace-alias té el valor ''{0}'', però no hi ha cap declaració d''espai de noms per al prefix ''{0}'' en l''àmbit de l''element. " }, { "ER_SET_FEATURE_NULL_NAME", "El nom de la característica no pot ser nul a TransformerFactory.setFeature(nom de la cadena, valor booleà). " }, { "ER_GET_FEATURE_NULL_NAME", "El nom de la característica no pot ser nul a TransformerFactory.getFeature(nom de cadena). " }, { "ER_UNSUPPORTED_FEATURE", "No es pot establir la característica ''{0}'' en aquesta TransformerFactory." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "L''ús de l''element d''extensió ''{0}'' no està permès, si la característica de procés segur s''ha establert en true." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "No es pot obtenir el prefix per a un URI de nom d'espais nul. " }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "No es pot obtenir l'URI del nom d'espais per a un prefix nul. " }, { "ER_XPATH_RESOLVER_NULL_QNAME", "El nom de la funció no pot ser nul. " }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "L'aritat no pot ser negativa." }, { "WG_FOUND_CURLYBRACE", "S'ha trobat '}' però no hi ha cap plantilla d'atribut oberta" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Avís: l''atribut de recompte no coincideix amb un antecessor d''xsl:number. Destinació = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Sintaxi antiga: El nom de l'atribut 'expr' s'ha canviat per 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan encara no pot gestionar el nom de l'entorn nacional a la funció format-number." }, { "WG_LOCALE_NOT_FOUND", "Avís: no s''ha trobat l''entorn nacional d''xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "No es pot crear la URL de: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "No es pot carregar el document sol·licitat: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "No s''ha trobat el classificador de <sort xml:lang={0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Sintaxi antiga: la instrucció de funcions ha d''utilitzar una URL de {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "Codificació sense suport: {0}, s''utilitza UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "Codificació sense suport: {0}, s''utilitza Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "S''han trobat conflictes d''especificitat: {0} S''utilitzarà el darrer trobat al full d''estils." }, { "WG_PARSING_AND_PREPARING", "========= S''està analitzant i preparant {0} ==========" }, { "WG_ATTR_TEMPLATE", "Plantilla Attr, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "S'ha produït un conflicte de coincidència entre xsl:strip-space i xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan encara no pot gestionar l''atribut {0}" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "No s''ha trobat cap declaració per al format decimal: {0}" }, { "WG_OLD_XSLT_NS", "Falta l'espai de noms XSLT o és incorrecte. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Només es permet una declaració xsl:decimal-format per defecte." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Els noms d''xsl:decimal-format han de ser exclusius. El nom \"{0}\" està duplicat." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} té un atribut no permès: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "No s''ha pogut resoldre el prefix d''espai de noms: {0}. Es passarà per alt el node." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet necessita un atribut 'version'." }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "El nom d''atribut no és permès: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "S''ha utilitzat un valor no permès a l''atribut {0}: {1}" }, { "WG_EMPTY_SECOND_ARG", "El conjunt de nodes resultant del segon argument de la funció document està buit. Torna un conjunt de nodes buit." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "El valor de l'atribut 'name' del nom xsl:processing-instruction no ha de ser 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "El valor de l''atribut ''name'' de xsl:processing-instruction ha de ser un NCName vàlid: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "No es pot afegir l''atribut {0} després dels nodes subordinats o abans que es produeixi un element. Es passarà per alt l''atribut." }, { "NO_MODIFICATION_ALLOWED_ERR", "S'ha intentat modificar un objecte on no es permeten modificacions. " }, { "ui_language", "ca" }, { "help_language", "ca" }, { "language", "ca" }, { "BAD_CODE", "El paràmetre de createMessage estava fora dels límits." }, { "FORMAT_FAILED", "S'ha generat una excepció durant la crida messageFormat." }, { "version", ">>>>>>> Versió Xalan " }, { "version2", "<<<<<<<" }, { "yes", "sí" }, { "line", "Línia núm." }, { "column", "Columna núm." }, { "xsldone", "XSLProcessor: fet" }, { "xslProc_option", "Opcions de classe del procés de línia d'ordres de Xalan-J:" }, { "xslProc_option", "Opcions de classe del procés de línia d'ordres de Xalan-J:" }, { "xslProc_invalid_xsltc_option", "L''opció {0} no té suport en modalitat XSLTC." }, { "xslProc_invalid_xalan_option", "L''opció {0} només es pot fer servir amb -XSLTC." }, { "xslProc_no_input", "Error: no s'ha especificat cap full d'estils o xml d'entrada. Per obtenir les instruccions d'ús, executeu aquesta ordre sense opcions." }, { "xslProc_common_options", "-Opcions comuns-" }, { "xslProc_xalan_options", "-Opcions per a Xalan-" }, { "xslProc_xsltc_options", "-Opcions per a XSLTC-" }, { "xslProc_return_to_continue", "(premeu <retorn> per continuar)" }, { "optionXSLTC", "   [-XSLTC (Utilitza XSLTC per a la transformació)]" }, { "optionIN", "   [-IN URL_XML_entrada]" }, { "optionXSL", "   [-XSL URL_transformació_XSL]" }, { "optionOUT", "   [-OUT nom_fitxer_sortida]" }, { "optionLXCIN", "   [-LXCIN entrada_nom_fitxer_full_estil_compilat]" }, { "optionLXCOUT", "   [-LXCOUT sortida_nom_fitxer_full_estil_compilat]" }, { "optionPARSER", "   [-PARSER nom de classe completament qualificat de la relació de l'analitzador]" }, { "optionE", "   [-E (No amplia les referències d'entitat)]" }, { "optionV", "   [-E (No amplia les referències d'entitat)]" }, { "optionQC", "   [-QC (Avisos de conflictes de patró reduït)]" }, { "optionQ", "   [-Q  (Modalitat reduïda)]" }, { "optionLF", "   [-LF (Utilitza salts de línia només a la sortida {el valor per defecte és CR/LF})]" }, { "optionCR", "   [-CR (Utilitza retorns de carro només a la sortida {el valor per defecte és CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (Caràcters per aplicar un escapament {el valor per defecte és <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (Controla quants espais tindrà el sagnat {el valor per defecte és 0})]" }, { "optionTT", "   [-TT (Fa un rastreig de les plantilles a mesura que es criden.)]" }, { "optionTG", "   [-TG (Fa un rastreig de cada un dels esdeveniments de generació.)]" }, { "optionTS", "   [-TS (Fa un rastreig de cada un dels esdeveniments de selecció.)]" }, { "optionTTC", "   [-TTC (Fa un rastreig dels subordinats de plantilla a mesura que es processen.)]" }, { "optionTCLASS", "   [-TCLASS (Classe TraceListener per a extensions de rastreig.)]" }, { "optionVALIDATE", "   [-VALIDATE (Estableix si es produeix la validació. Per defecte no està activada.)]" }, { "optionEDUMP", "   [-EDUMP {nom de fitxer opcional} (Fer el buidatge de la pila si es produeix un error.)]" }, { "optionXML", "   [-XML (Utilitza el formatador XML i afegeix la capçalera XML.)]" }, { "optionTEXT", "   [-TEXT (Utilitza el formatador de text simple.)]" }, { "optionHTML", "   [-HTML (Utilitza el formatador HTML.)]" }, { "optionPARAM", "   [-PARAM expressió del nom (Estableix un paràmetre de full d'estils)]" }, { "noParsermsg1", "El procés XSL no ha estat correcte." }, { "noParsermsg2", "** No s'ha trobat l'analitzador **" }, { "noParsermsg3", "Comproveu la vostra classpath." }, { "noParsermsg4", "Si no teniu XML Parser for Java d'IBM, el podeu baixar de l'indret web" }, { "noParsermsg5", "AlphaWorks d'IBM: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER nom de classe complet (URIResolver que s'ha d'utilitzar per resoldre URI)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER nom de classe complet (EntityResolver que s'ha d'utilitzar per resoldre entitats)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER nom de classe complet (ContentHandler que s'ha d'utilitzar per serialitzar la sortida)]" }, { "optionLINENUMBERS", "   [-L utilitza els números de línia del document origen]" }, { "optionSECUREPROCESSING", "   [-SECURE (estableix la característica de procés segur en true.)]" }, { "optionMEDIA", "   [-MEDIA mediaType (utilitza l'atribut media per trobar un full d'estils relacionat amb un document.)]" }, { "optionFLAVOR", "   [-FLAVOR nom_flavor (utilitza explícitament s2s=SAX o d2d=DOM per fer una transformació.)] " }, { "optionDIAG", "   [-DIAG (Imprimex els mil·lisegons en total que ha trigat la transformació.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (sol·licita la construcció de DTM incremental establint http://xml.apache.org/xalan/features/incremental en true.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (sol·licita que no es processi l'optimització de full d'estils establint http://xml.apache.org/xalan/features/optimize en false.)]" }, { "optionRL", "   [-RL recursionlimit (confirma el límit numèric de la profunditat de recursivitat del full d'estils.)]" }, { "optionXO", "   [-XO [nom_translet] (assigna el nom al translet generat)]" }, { "optionXD", "   [-XD directori_destinació (especifica un directori de destinació per al translet)]" }, { "optionXJ", "   [-XJ fitxer_jar (empaqueta les classes de translet en un fitxer jar amb el nom <fitxer_jar>)]" }, { "optionXP", "   [-XP paquet (especifica un prefix de nom de paquet per a totes les classes de translet generades)]" }, { "optionXN", "   [-XN (habilita l'inlining de plantilles)]" }, { "optionXX", "   [-XX (activa la sortida de missatges de depuració addicionals)]" }, { "optionXT", "   [-XT (utilitza el translet per a la transformació si és possible)]" }, { "diagTiming", " --------- La transformació de {0} mitjançant {1} ha trigat {2} ms" }, { "recursionTooDeep", "La imbricació de plantilles té massa nivells. Imbricació = {0}, plantilla{1} {2}" }, { "nameIs", "el nom és" }, { "matchPatternIs", "el patró de coincidència és" } };
/*  276:     */   }
/*  277:     */   
/*  278:     */   public static final XSLTErrorResources loadResourceBundle(String className)
/*  279:     */     throws MissingResourceException
/*  280:     */   {
/*  281:1477 */     Locale locale = Locale.getDefault();
/*  282:1478 */     String suffix = getResourceSuffix(locale);
/*  283:     */     try
/*  284:     */     {
/*  285:1484 */       return (XSLTErrorResources)ResourceBundle.getBundle(className + suffix, locale);
/*  286:     */     }
/*  287:     */     catch (MissingResourceException e)
/*  288:     */     {
/*  289:     */       try
/*  290:     */       {
/*  291:1494 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("ca", "ES"));
/*  292:     */       }
/*  293:     */       catch (MissingResourceException e2)
/*  294:     */       {
/*  295:1502 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  296:     */       }
/*  297:     */     }
/*  298:     */   }
/*  299:     */   
/*  300:     */   private static final String getResourceSuffix(Locale locale)
/*  301:     */   {
/*  302:1519 */     String suffix = "_" + locale.getLanguage();
/*  303:1520 */     String country = locale.getCountry();
/*  304:1522 */     if (country.equals("TW")) {
/*  305:1523 */       suffix = suffix + "_" + country;
/*  306:     */     }
/*  307:1525 */     return suffix;
/*  308:     */   }
/*  309:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_ca
 * JD-Core Version:    0.7.0.1
 */