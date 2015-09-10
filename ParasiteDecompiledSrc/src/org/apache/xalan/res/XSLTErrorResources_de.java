/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_de
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
/*  261:     */   public static final String BAD_CODE = "FEHLERHAFTER_CODE";
/*  262:     */   public static final String FORMAT_FAILED = "FORMAT_FEHLGESCHLAGEN";
/*  263:     */   public static final String ERROR_STRING = "#Fehler";
/*  264:     */   public static final String ERROR_HEADER = "Fehler: ";
/*  265:     */   public static final String WARNING_HEADER = "Achtung: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "MUSTER ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Fehler: '{' darf nicht innerhalb des Ausdrucks stehen." }, { "ER_ILLEGAL_ATTRIBUTE", "{0} hat ein unzulässiges Attribut {1}." }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode ist Null in xsl:apply-imports!" }, { "ER_CANNOT_ADD", "{0} kann nicht {1} hinzugefügt werden." }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode ist Null in handleApplyTemplatesInstruction!" }, { "ER_NO_NAME_ATTRIB", "{0} muss ein Namensattribut haben." }, { "ER_TEMPLATE_NOT_FOUND", "Vorlage konnte nicht gefunden werden: {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Namensvorlage für den Attributwert in xsl:call-template konnte nicht aufgelöst werden." }, { "ER_REQUIRES_ATTRIB", "{0} erfordert das Attribut {1}." }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} muss über ein Attribut ''test'' verfügen." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Falscher Wert für Ebenenattribut: {0}." }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Name der Verarbeitungsanweisung darf nicht 'xml' sein." }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Name der Verarbeitungsanweisung muss ein gültiges NCName-Format haben: {0}." }, { "ER_NEED_MATCH_ATTRIB", "{0} muss über ein entsprechendes Attribut verfügen, wenn ein Modus vorhanden ist." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} erfordert einen Namen oder ein Übereinstimmungsattribut." }, { "ER_CANT_RESOLVE_NSPREFIX", "Präfix des Namensbereichs kann nicht aufgelöst werden: {0}." }, { "ER_ILLEGAL_VALUE", "xml:space weist einen ungültigen Wert auf: {0}" }, { "ER_NO_OWNERDOC", "Der Kindknoten hat kein Eignerdokument!" }, { "ER_ELEMTEMPLATEELEM_ERR", "ElemTemplateElement-Fehler: {0}" }, { "ER_NULL_CHILD", "Es wird versucht, ein leeres Kind hinzuzufügen!" }, { "ER_NEED_SELECT_ATTRIB", "{0} erfordert ein Attribut ''''select''''." }, { "ER_NEED_TEST_ATTRIB", "xsl:when muss über ein Attribut 'test' verfügen." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param muss über ein Attribut 'name' verfügen." }, { "ER_NO_CONTEXT_OWNERDOC", "Der Kontextknoten verfügt nicht über ein Eignerdokument!" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "XML-TransformerFactory-Liaison konnte nicht erstellt werden: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan:-Prozess konnte nicht erfolgreich durchgeführt werden." }, { "ER_NOT_SUCCESSFUL", "Xalan: war nicht erfolgreich." }, { "ER_ENCODING_NOT_SUPPORTED", "Verschlüsselung wird nicht unterstützt: {0}." }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "TraceListener konnte nicht erstellt werden: {0}." }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key erfordert ein Attribut 'name'!" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key erfordert ein Attribut 'match'!" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key erfordert ein Attribut 'use'!" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} erfordert ein Attribut ''elements''!" }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) {0}: Das Attribut ''prefix'' fehlt." }, { "ER_BAD_STYLESHEET_URL", "Formatvorlagen-URL-Adresse ist ungültig: {0}." }, { "ER_FILE_NOT_FOUND", "Formatvorlagendatei konnte nicht gefunden werden: {0}." }, { "ER_IOEXCEPTION", "Bei folgender Formatvorlagendatei ist eine E/A-Ausnahmebedingung aufgetreten: {0}." }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Attribut ''href'' für {0} konnte nicht gefunden werden." }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} schließt sich selbst direkt oder indirekt mit ein!" }, { "ER_PROCESSINCLUDE_ERROR", "Fehler in StylesheetHandler.processInclude, {0}." }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) {0}: Das Attribut ''lang'' fehlt." }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) Element {0} an falscher Position? Fehlendes Containerelement ''component''." }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Ausgabe kann nur an ein Element, Dokumentfragment, Dokument oder Druckausgabeprogramm erfolgen." }, { "ER_PROCESS_ERROR", "Fehler in StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "UnImplNode-Fehler: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Fehler! xpath-Auswahlausdruck (-select) konnte nicht gefunden werden." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "XSLProcessor kann nicht serialisiert werden!" }, { "ER_NO_INPUT_STYLESHEET", "Formatvorlageneingabe wurde nicht angegeben!" }, { "ER_FAILED_PROCESS_STYLESHEET", "Verarbeitung der Formatvorlage fehlgeschlagen!" }, { "ER_COULDNT_PARSE_DOC", "Dokument {0} konnte nicht syntaktisch analysiert werden!" }, { "ER_COULDNT_FIND_FRAGMENT", "Fragment konnte nicht gefunden werden: {0}." }, { "ER_NODE_NOT_ELEMENT", "Der Knoten, auf den von einer Fragment-ID verwiesen wurde, war kein Element: {0}." }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "'for-each' muss entweder ein Attribut 'match' oder 'name' haben." }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "Vorlagen müssen entweder ein Attribut 'match' oder 'name' haben." }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Kein Klon eines Dokumentfragments!" }, { "ER_CANT_CREATE_ITEM", "Im Ergebnisbaum kann kein Eintrag erzeugt werden: {0}." }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space in der Quellen-XML hat einen ungültigen Wert: {0}." }, { "ER_NO_XSLKEY_DECLARATION", "Keine Deklaration xsl:key für {0} vorhanden!" }, { "ER_CANT_CREATE_URL", "Fehler! URL kann nicht erstellt werden für: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions wird nicht unterstützt." }, { "ER_PROCESSOR_ERROR", "XSLT-TransformerFactory-Fehler" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} nicht zulässig innerhalb einer Formatvorlage!" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns wird nicht mehr unterstützt!  Verwenden Sie stattdessen xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space wird nicht mehr unterstützt!  Verwenden Sie stattdessen xsl:strip-space oder xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result wird nicht mehr unterstützt!  Verwenden Sie stattdessen xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} hat ein ungültiges Attribut: {1}." }, { "ER_UNKNOWN_XSL_ELEM", "Unbekanntes XSL-Element: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort kann nur mit xsl:apply-templates oder xsl:for-each verwendet werden." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when steht an der falschen Position!" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) Für xsl:when ist xsl:choose nicht als Elter definiert!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise steht an der falschen Position!" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) Für xsl:otherwise ist xsl:choose nicht als Elter definiert!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} ist innerhalb einer Vorlage nicht zulässig!" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0}: Erweiterung des Namensbereichspräfixes {1} ist unbekannt" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Importe können nur als erste Elemente in der Formatvorlage auftreten!" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} importiert sich direkt oder indirekt selbst!" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space hat einen ungültigen Wert: {0}." }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet nicht erfolgreich!" }, { "ER_SAX_EXCEPTION", "SAX-Ausnahmebedingung" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funktion nicht unterstützt!" }, { "ER_XSLT_ERROR", "XSLT-Fehler" }, { "ER_CURRENCY_SIGN_ILLEGAL", "Ein Währungssymbol ist in der Formatmusterzeichenfolge nicht zulässig." }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "Eine Dokumentfunktion wird in der Dokumentobjektmodell-Formatvorlage nicht unterstützt!" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Präfix einer Auflösung ohne Präfix kann nicht aufgelöst werden!" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Umleitungserweiterung: Dateiname konnte nicht abgerufen werden - Datei oder Attribut 'select' muss eine gültige Zeichenfolge zurückgeben." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "FormatterListener kann in Umleitungserweiterung nicht erstellt werden!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Präfix in exclude-result-prefixes ist nicht gültig: {0}." }, { "ER_MISSING_NS_URI", "Fehlende Namensbereichs-URI für angegebenes Präfix." }, { "ER_MISSING_ARG_FOR_OPTION", "Fehlendes Argument für Option: {0}." }, { "ER_INVALID_OPTION", "Ungültige Option: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Syntaktisch falsche Formatzeichenfolge: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet erfordert ein Attribut 'version'!" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "Attribut {0} weist einen ungültigen Wert auf: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose erfordert xsl:when." }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports ist in xsl:for-each nicht zulässig." }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "DTMLiaison kann nicht für einen Ausgabe-Dokumentobjektmodellknoten verwendet werden... Übergeben Sie stattdessen org.apache.xpath.DOM2Helper!" }, { "ER_CANT_USE_DTM_FOR_INPUT", "DTMLiaison kann nicht für einen Eingabe-Dokumentobjektmodellknoten verwendet werden... Übergeben Sie stattdessen org.apache.xpath.DOM2Helper!" }, { "ER_CALL_TO_EXT_FAILED", "Aufruf an Erweiterungselement fehlgeschlagen: {0}." }, { "ER_PREFIX_MUST_RESOLVE", "Das Präfix muss in einen Namensbereich aufgelöst werden: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Ungültige UTF-16-Ersetzung festgestellt: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} verwendet sich selbst, wodurch eine Endlosschleife verursacht wird." }, { "ER_CANNOT_MIX_XERCESDOM", "Nicht-Xerces-Dokumentobjektmodelleingabe kann nicht mit Xerces-Dokumentobjektmodellausgabe gemischt werden!" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "In ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Mehrere Vorlagen mit folgendem Namen gefunden: {0}." }, { "ER_INVALID_KEY_CALL", "Ungültiger Funktionsaufruf: rekursive Aufrufe 'key()'sind nicht zulässig." }, { "ER_REFERENCING_ITSELF", "Variable {0} verweist direkt oder indirekt auf sich selbst!" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "Der Eingabeknoten kann für DOMSource für newTemplates nicht Null sein!" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Klassendatei für Option {0} wurde nicht gefunden." }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Erforderliches Element nicht gefunden: {0}." }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream kann nicht Null sein." }, { "ER_URI_CANNOT_BE_NULL", "URI kann nicht Null sein." }, { "ER_FILE_CANNOT_BE_NULL", "Eine Datei kann nicht Null sein." }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource kann nicht Null sein." }, { "ER_CANNOT_INIT_BSFMGR", "BSF Manager kann nicht initialisiert werden." }, { "ER_CANNOT_CMPL_EXTENSN", "Erweiterung konnte nicht kompiliert werden." }, { "ER_CANNOT_CREATE_EXTENSN", "Erweiterung {0} konnte nicht erstellt werden. Ursache: {1}." }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "Der Aufruf einer Instanzdefinitionsmethode von Methode {0} erfordert eine Objektinstanz als erstes Argument." }, { "ER_INVALID_ELEMENT_NAME", "Ungültiger Elementname angegeben {0}." }, { "ER_ELEMENT_NAME_METHOD_STATIC", "Elementnamenmethode muss statisch sein: {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "Erweiterungsfunktion {0} : {1} ist unbekannt." }, { "ER_MORE_MATCH_CONSTRUCTOR", "Mehrere passende Entsprechungen für Konstruktor für {0}." }, { "ER_MORE_MATCH_METHOD", "Mehrere passende Entsprechungen für Methode {0}." }, { "ER_MORE_MATCH_ELEMENT", "Mehrere passende Entsprechungen für Elementmethode {0}." }, { "ER_INVALID_CONTEXT_PASSED", "Ungültiger Kontext zur Auswertung von {0} übergeben." }, { "ER_POOL_EXISTS", "Pool ist bereits vorhanden." }, { "ER_NO_DRIVER_NAME", "Kein Treibername angegeben." }, { "ER_NO_URL", "Keine URL-Adresse angegeben." }, { "ER_POOL_SIZE_LESSTHAN_ONE", "Poolgröße ist kleiner als Eins!" }, { "ER_INVALID_DRIVER", "Ungültiger Treibername angegeben!" }, { "ER_NO_STYLESHEETROOT", "Root der Formatvorlage konnte nicht gefunden werden!" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Ungültiger Wert für xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode ist fehlgeschlagen." }, { "ER_RESOURCE_COULD_NOT_LOAD", "Die Ressource [ {0} ] konnte nicht geladen werden: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Puffergröße <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Unbekannter Fehler beim Aufrufen der Erweiterung." }, { "ER_NO_NAMESPACE_DECL", "Präfix {0} hat keine entsprechende Namensbereichsdeklaration." }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Elementinhalt nicht zulässig für lang=javaclass {0}." }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Formatvorlage hat die Beendigung übertragen." }, { "ER_ONE_OR_TWO", "1 oder 2" }, { "ER_TWO_OR_THREE", "2 oder 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "{0} (CLASSPATH prüfen) konnte nicht geladen werden; es werden die Standardwerte verwendet." }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Standardvorlagen können nicht initialisiert werden." }, { "ER_RESULT_NULL", "Das Ergebnis darf nicht Null sein." }, { "ER_RESULT_COULD_NOT_BE_SET", "Das Ergebnis konnte nicht festgelegt werden." }, { "ER_NO_OUTPUT_SPECIFIED", "Keine Ausgabe angegeben." }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Umsetzen in ein Ergebnis des Typs {0} ist nicht möglich" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Umsetzen einer Quelle des Typs {0} ist nicht möglich" }, { "ER_NULL_CONTENT_HANDLER", "Es ist keine Inhaltssteuerroutine vorhanden." }, { "ER_NULL_ERROR_HANDLER", "Kein Fehlerbehandlungsprogramm vorhanden" }, { "ER_CANNOT_CALL_PARSE", "Die Syntaxanalyse kann nicht aufgerufen werden, wenn ContentHandler nicht festgelegt wurde." }, { "ER_NO_PARENT_FOR_FILTER", "Kein Elter für Filter vorhanden" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Keine Formatvorlage gefunden in: {0}, Datenträger= {1}." }, { "ER_NO_STYLESHEET_PI", "Keine Verarbeitungsanweisung für xml-stylesheet gefunden in {0}." }, { "ER_NOT_SUPPORTED", "Nicht unterstützt: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "Der Wert für Merkmal {0} sollte eine Boolesche Instanz sein." }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Externes Script bei {0} konnte nicht erreicht werden." }, { "ER_RESOURCE_COULD_NOT_FIND", "Die Ressource [ {0} ] konnte nicht gefunden werden.\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Ausgabemerkmal nicht erkannt: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Das Erstellen der Instanz ElemLiteralResult ist fehlgeschlagen." }, { "ER_VALUE_SHOULD_BE_NUMBER", "Der Wert für {0} sollte eine syntaktisch analysierbare Zahl sein." }, { "ER_VALUE_SHOULD_EQUAL", "Der Wert für {0} sollte ''''yes'''' oder ''''no'''' entsprechen." }, { "ER_FAILED_CALLING_METHOD", "Aufruf von Methode {0} ist fehlgeschlagen" }, { "ER_FAILED_CREATING_ELEMTMPL", "Das Erstellen der Instanz ElemTemplateElement ist fehlgeschlagen." }, { "ER_CHARS_NOT_ALLOWED", "Zeichen sind an dieser Stelle im Dokument nicht zulässig." }, { "ER_ATTR_NOT_ALLOWED", "Das Attribut \"{0}\" ist im Element {1} nicht zulässig!" }, { "ER_BAD_VALUE", "{0} ungültiger Wert {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Attributwert {0} wurde nicht gefunden " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Attributwert {0} wurde nicht erkannt " }, { "ER_NULL_URI_NAMESPACE", "Es wird versucht, ein Namensbereichspräfix mit einer Null-URI zu erzeugen." }, { "ER_NUMBER_TOO_BIG", "Es wird versucht, eine größere Zahl als die größte erweiterte Ganzzahl zu formatieren." }, { "ER_CANNOT_FIND_SAX1_DRIVER", "SAX1-Treiberklasse {0} konnte nicht gefunden werden." }, { "ER_SAX1_DRIVER_NOT_LOADED", "SAX1-Treiberklasse {0} gefunden, kann aber nicht geladen werden." }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "SAX1-Treiberklasse {0} geladen, kann aber nicht instanziert werden." }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "SAX1-Treiberklasse {0} implementiert nicht org.xml.sax.Parser." }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Systemmerkmal org.xml.sax.parser ist nicht angegeben." }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "Parserargument darf nicht Null sein." }, { "ER_FEATURE", "Feature: {0}" }, { "ER_PROPERTY", "Merkmal: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Es ist keine Entitätenauflösungsroutine vorhanden." }, { "ER_NULL_DTD_HANDLER", "Es ist keine Steuerroutine für Dokumenttypbeschreibungen vorhanden." }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Kein Treibername angegeben!" }, { "ER_NO_URL_SPECIFIED", "Keine URL-Adresse angegeben!" }, { "ER_POOLSIZE_LESS_THAN_ONE", "Poolgröße ist kleiner als 1!" }, { "ER_INVALID_DRIVER_NAME", "Ungültiger Treibername angegeben!" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Programmierfehler! Der Ausdruck hat kein übergeordnetes Element ElemTemplateElement!" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Programmiererfestlegung in RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} ist an dieser Position in der Formatvorlage nicht zulässig!" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Anderer Text als Leerzeichen ist an dieser Position in der Formatvorlage nicht zulässig!" }, { "INVALID_TCHAR", "Unzulässiger Wert {1} für CHAR-Attribut verwendet: {0}. Ein Attribut des Typs CHAR darf nur ein Zeichen umfassen!" }, { "INVALID_QNAME", "Unzulässiger Wert {1} für QNAME-Attribut verwendet: {0}" }, { "INVALID_ENUM", "Unzulässiger Wert {1} für ENUM-Attribut verwendet: {0}. Folgende Werte sind gültig: {2}." }, { "INVALID_NMTOKEN", "Unzulässiger Wert {1} für NMTOKEN-Attribut verwendet: {0}. " }, { "INVALID_NCNAME", "Unzulässiger Wert {1} für NCNAME-Attribut verwendet: {0}. " }, { "INVALID_BOOLEAN", "Unzulässiger Wert {1} für BOOLEAN-Attribut verwendet: {0}. " }, { "INVALID_NUMBER", "Unzulässiger Wert {1} für NUMBER-Attribut verwendet: {0}. " }, { "ER_ARG_LITERAL", "Argument von {0} in Suchmuster muss ein Literal sein." }, { "ER_DUPLICATE_GLOBAL_VAR", "Doppelte Deklaration einer globalen Variablen." }, { "ER_DUPLICATE_VAR", "Doppelte Deklaration einer Variablen." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template muss ein Attribut 'name' und/oder 'match' haben." }, { "ER_INVALID_PREFIX", "Präfix in exclude-result-prefixes ist nicht gültig: {0}." }, { "ER_NO_ATTRIB_SET", "Die Attributgruppe {0} ist nicht vorhanden." }, { "ER_FUNCTION_NOT_FOUND", "Die Funktion {0} ist nicht vorhanden." }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "Das Element {0} darf nicht über ein Attribut ''''content'''' und zusätzlich über ein Attribut ''''select'''' verfügen." }, { "ER_INVALID_SET_PARAM_VALUE", "Der Wert von Parameter {0} muss ein gültiges Java-Objekt sein." }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "Das Attribut result-prefix eines Elements xsl:namespace-alias hat den Wert '#default', es ist jedoch keine Deklaration des Standardnamensbereichs vorhanden, die für dieses Element gültig ist." }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "Das Attribut result-prefix eines Elements xsl:namespace-alias hat den Wert ''{0}'', es ist jedoch keine Namensbereichsdeklaration für das Präfix ''{0}'' vorhanden, die für dieses Element gültig ist." }, { "ER_SET_FEATURE_NULL_NAME", "Der Funktionsname darf in TransformerFactory.setFeature(Name der Zeichenfolge, Boolescher Wert) nicht den Wert Null haben." }, { "ER_GET_FEATURE_NULL_NAME", "Der Funktionsname darf in TransformerFactory.getFeature(Name der Zeichenfolge) nicht den Wert Null haben." }, { "ER_UNSUPPORTED_FEATURE", "Die Funktion ''{0}'' kann in dieser TransformerFactory nicht festgelegt werden." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "Die Verwendung des Erweiterungselements ''{0}'' ist nicht zulässig, wenn für die Funktion zur sicheren Verarbeitung der Wert ''true'' festgelegt wurde." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Das Präfix für einen Namensbereich-URI mit dem Wert Null kann nicht abgerufen werden." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Der Namensbereich-URI für ein Präfix mit dem Wert Null kann nicht abgerufen werden." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "Es muss ein Funktionsname angegeben werden." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "Die Stelligkeit darf nicht negativ sein." }, { "WG_FOUND_CURLYBRACE", "'}' gefunden, es ist aber keine Attributvorlage geöffnet!" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Warnung: Attribut ''count'' entspricht keinem übergeordneten Fensterobjekt in xsl:number! Ziel = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Veraltete Syntax: Der Name des Attributs 'expr' wurde in 'select' geändert." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan bearbeitet noch nicht den Ländereinstellungsnamen in der Funktion 'format-number'." }, { "WG_LOCALE_NOT_FOUND", "Warnung: Ländereinstellung für xml:lang={0} konnte nicht gefunden werden." }, { "WG_CANNOT_MAKE_URL_FROM", "URL konnte nicht erstellt werden aus: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Angeforderte Dokumentation kann nicht geladen werden: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Collator für <sort xml:lang={0} konnte nicht gefunden werden." }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Veraltete Syntax: Die Funktionsanweisung sollte eine URL-Adresse {0} verwenden." }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "Verschlüsselung nicht unterstützt: {0}, UTF-8 wird verwendet." }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "Verschlüsselung nicht unterstützt: {0}, Java {1} wird verwendet." }, { "WG_SPECIFICITY_CONFLICTS", "Genauigkeitskonflikte gefunden: {0}. Die letzte Angabe in der Formatvorlage wird verwendet." }, { "WG_PARSING_AND_PREPARING", "========= Syntaxanalyse und Vorbereitung von {0} ==========" }, { "WG_ATTR_TEMPLATE", "Attributvorlage, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Übereinstimmungskonflikt zwischen xsl:strip-space und xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan bearbeitet noch nicht das Attribut {0}!" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Keine Deklaration für Dezimalformat gefunden: {0}" }, { "WG_OLD_XSLT_NS", "Fehlender oder ungültiger XSLT-Namensbereich " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Nur eine Standarddeklaration xsl:decimal-format ist zulässig." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Namen in xsl:decimal-format müssen eindeutig sein. Name \"{0}\" wurde dupliziert." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} hat ein unzulässiges Attribut {1}." }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Namensbereichspräfix konnte nicht aufgelöst werden: {0}. Der Knoten wird ignoriert." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet erfordert ein Attribut 'version'!" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Unzulässiger Attributname: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Unzulässiger Wert für Attribut {0} verwendet: {1}" }, { "WG_EMPTY_SECOND_ARG", "Die Ergebnisknoteneinstellung des zweiten Arguments der Dokumentfunktion ist leer. Geben Sie eine leere Knotengruppe zurück." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Der Wert des Attributs 'name' von xsl:processing-instruction darf nicht 'xml' sein." }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Der Wert des Attributs ''name'' von xsl:processing-instruction muss ein gültiger NCName sein: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Attribut {0} kann nicht nach Kindknoten oder vor dem Erstellen eines Elements hinzugefügt werden. Das Attribut wird ignoriert." }, { "NO_MODIFICATION_ALLOWED_ERR", "Es wurde versucht, ein Objekt an einer nicht zulässigen Stelle zu ändern." }, { "ui_language", "de" }, { "help_language", "de" }, { "language", "de" }, { "BAD_CODE", "Der Parameter für createMessage lag außerhalb des gültigen Bereichs" }, { "FORMAT_FAILED", "Während des Aufrufs von messageFormat wurde eine Ausnahmebedingung ausgelöst" }, { "version", ">>>>>>> Xalan-Version " }, { "version2", "<<<<<<<" }, { "yes", "ja" }, { "line", "Zeilennummer" }, { "column", "Spaltennummer" }, { "xsldone", "XSLProcessor: fertig" }, { "xslProc_option", "Optionen für Verarbeitungsklassen in der Xalan-J-Befehlszeile:" }, { "xslProc_option", "Optionen für Verarbeitungsklassen in der Xalan-J-Befehlszeile:" }, { "xslProc_invalid_xsltc_option", "Die Option {0} wird im XSLTC-Modus nicht unterstützt." }, { "xslProc_invalid_xalan_option", "Die Option {0} kann nur mit -XSLTC verwendet werden." }, { "xslProc_no_input", "Fehler: Es wurde keine Formatvorlagen- oder Eingabe-XML angegeben. Führen Sie diesen Befehl ohne Optionen für Syntaxanweisungen aus." }, { "xslProc_common_options", "-Allgemeine Optionen-" }, { "xslProc_xalan_options", "-Optionen für Xalan-" }, { "xslProc_xsltc_options", "-Optionen für XSLTC-" }, { "xslProc_return_to_continue", "(Drücken Sie die Eingabetaste, um fortzufahren.)" }, { "optionXSLTC", "[-XSLTC (XSLTC für Umsetzung verwenden)]" }, { "optionIN", "[-IN EingabeXMLURL]" }, { "optionXSL", "[-XSL XSLUmsetzungsURL]" }, { "optionOUT", "[-OUT AusgabeDateiName]" }, { "optionLXCIN", "[-LXCIN kompilierteDateivorlageDateiNameEin]" }, { "optionLXCOUT", "[-LXCOUT kompilierteDateivorlageDateiNameAus]" }, { "optionPARSER", "[-PARSER vollständig qualifizierter Klassenname der Parser-Liaison]" }, { "optionE", "[-E (Entitätenverweise nicht erweitern)]" }, { "optionV", "[-E (Entitätenverweise nicht erweitern)]" }, { "optionQC", "[-QC (Unterdrückte Musterkonfliktwarnungen)]" }, { "optionQ", "[-Q  (Unterdrückter Modus)]" }, { "optionLF", "[-LF (Nur Zeilenvorschubzeichen bei Ausgabe verwenden {Standardeinstellung ist CR/LF})]" }, { "optionCR", "[-CR (Nur Zeilenschaltung bei Ausgabe verwenden {Standardeinstellung ist CR/LF})]" }, { "optionESCAPE", "[-ESCAPE (Zeichen, die mit einem Escapezeichen angegeben werden müssen {Standardeinstellung ist <>&\"'\\r\\n}]" }, { "optionINDENT", "[-INDENT (Steuerung, um wie viele Leerzeichen eingerückt werden soll {Standardeinstellung ist 0})]" }, { "optionTT", "[-TT (Trace für Vorlagen ausführen, wenn sie aufgerufen werden.)]" }, { "optionTG", "[-TG (Trace für jedes Generierungsereignis durchführen.)]" }, { "optionTS", "[-TS (Trace für jedes Auswahlereignis durchführen.)]" }, { "optionTTC", "[-TTC (Trace für die untergeordneten Vorlagen ausführen, wenn sie verarbeitet werden.)]" }, { "optionTCLASS", "[-TCLASS (TraceListener-Klasse für Trace-Erweiterungen.)]" }, { "optionVALIDATE", "[-VALIDATE (Festlegen, ob eine Gültigkeitsprüfung erfolgen soll. Die Gültigkeitsprüfung ist standardmäßig ausgeschaltet.)]" }, { "optionEDUMP", "[-EDUMP {optionaler Dateiname} (Bei Fehler Stapelspeicherauszug erstellen.)]" }, { "optionXML", "[-XML (XML-Formatierungsprogramm verwenden und XML-Header hinzufügen.)]" }, { "optionTEXT", "[-TEXT (Einfaches Textformatierungsprogramm verwenden.)]" }, { "optionHTML", "[-HTML (HTML-Formatierungsprogramm verwenden.)]" }, { "optionPARAM", "[-PARAM Name Ausdruck (Festlegen eines Formatvorlagenparameters)]" }, { "noParsermsg1", "XSL-Prozess konnte nicht erfolgreich durchgeführt werden." }, { "noParsermsg2", "** Parser konnte nicht gefunden werden **" }, { "noParsermsg3", "Bitte überprüfen Sie den Klassenpfad." }, { "noParsermsg4", "Wenn Sie nicht über einen IBM XML-Parser für Java verfügen, können Sie ihn herunterladen:" }, { "noParsermsg5", "IBM AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "[-URIRESOLVER vollständiger Klassenname (URIResolver wird zum Auflösen von URIs verwendet)]" }, { "optionENTITYRESOLVER", "[-ENTITYRESOLVER vollständiger Klassenname (EntityResolver wird zum Auflösen von Entitäten verwendet)]" }, { "optionCONTENTHANDLER", "[-CONTENTHANDLER vollständiger Klassenname (ContentHandler wird zum Serialisieren der Ausgabe verwendet)]" }, { "optionLINENUMBERS", "[-L Zeilennummern für das Quellendokument verwenden]" }, { "optionSECUREPROCESSING", "   [-SECURE (Funktion zur sicheren Verarbeitung auf 'True' setzen)]" }, { "optionMEDIA", "[-MEDIA DatenträgerTyp (Datenträgerattribut verwenden, um die einem Dokument zugeordnete Formatvorlage zu suchen.)]" }, { "optionFLAVOR", "[-FLAVOR WunschName (Explizit s2s=SAX oder d2d=DOM verwenden, um die Umsetzung auszuführen.)] " }, { "optionDIAG", "[-DIAG (Gesamtanzahl Millisekunden für die Umsetzung ausgeben.)]" }, { "optionINCREMENTAL", "[-INCREMENTAL (Inkrementelle DTM-Erstellung mit der Einstellung 'true' für http://xml.apache.org/xalan/features/incremental anfordern.)]" }, { "optionNOOPTIMIMIZE", "[-NOOPTIMIMIZE (Mit der Einstellung 'false' für 'http://xml.apache.org/xalan/features/optimize' anfordern, dass keine Formatvorlagenoptimierung ausgeführt wird.)]" }, { "optionRL", "[-RL Verschachtelungsbegrenzung (Numerische Begrenzung für Verschachtelungstiefe der Formatvorlage festlegen.)]" }, { "optionXO", "[-XO [transletName] (Namen dem generierten Translet zuordnen)]" }, { "optionXD", "[-XD ZielVerzeichnis (Ein Zielverzeichnis für Translet angeben)]" }, { "optionXJ", "[-XJ jardatei (Translet-Klassen in eine jar-Datei mit dem Namen <jardatei> packen)]" }, { "optionXP", "[-XP paket (Ein Paketnamenpräfix für alle generierten Translet-Klassen angeben)]" }, { "optionXN", "[-XN (Inline-Anordnung für Vorlagen aktivieren)]" }, { "optionXX", "[-XX (Zusätzliche Debugnachrichtenausgabe aktivieren)]" }, { "optionXT", "[-XT (Translet für Umsetzung verwenden, wenn möglich)]" }, { "diagTiming", "--------- Umsetzung von {0} über {1} betrug {2} Millisekunden" }, { "recursionTooDeep", "Vorlagenverschachtelung ist zu stark. Verschachtelung = {0}, Vorlage {1} {2}" }, { "nameIs", "Der Name ist" }, { "matchPatternIs", "Das Suchmuster ist" } };
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_de
 * JD-Core Version:    0.7.0.1
 */