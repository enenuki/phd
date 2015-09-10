/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_fr
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
/*  264:     */   public static final String ERROR_HEADER = "Erreur : ";
/*  265:     */   public static final String WARNING_HEADER = "Avertissement : ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "PATTERN ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Erreur : '{' interdit dans une expression" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} comporte un attribut incorrect : {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode est vide dans xsl:apply-imports !" }, { "ER_CANNOT_ADD", "Impossible d''ajouter {0} à {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode est vide dans handleApplyTemplatesInstruction !" }, { "ER_NO_NAME_ATTRIB", "{0} doit posséder un attribut de nom." }, { "ER_TEMPLATE_NOT_FOUND", "Impossible de trouver le modèle : {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Impossible de convertir l'AVT du nom dans xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "{0} requiert l''attribut : {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} doit avoir un attribut ''test''." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Valeur erronée dans l''attribut de niveau : {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "Le nom de l'instruction de traitement ne peut être 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "Le nom de l''instruction de traitement doit être un NCName valide : {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} doit posséder un attribut de correspondance s''il possède un mode." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} requiert un nom ou un attribut de correspondance." }, { "ER_CANT_RESOLVE_NSPREFIX", "Impossible de résoudre le préfixe de l''espace de noms : {0}" }, { "ER_ILLEGAL_VALUE", "xml:space comporte une valeur non valide : {0}" }, { "ER_NO_OWNERDOC", "Le noeud enfant ne possède pas de document propriétaire !" }, { "ER_ELEMTEMPLATEELEM_ERR", "Erreur de ElemTemplateElement : {0}" }, { "ER_NULL_CHILD", "Tentative d'ajout d'un enfant vide !" }, { "ER_NEED_SELECT_ATTRIB", "{0} requiert un attribut de sélection." }, { "ER_NEED_TEST_ATTRIB", "xsl:when doit posséder un attribut 'test'." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param doit posséder un attribut 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "Le contexte ne possède pas de document propriétaire !" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Impossible de créer XML TransformerFactory Liaison : {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Echec du processus Xalan." }, { "ER_NOT_SUCCESSFUL", "Echec de Xalan." }, { "ER_ENCODING_NOT_SUPPORTED", "Encodage non pris en charge : {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Impossible de créer TraceListener : {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key requiert un attribut 'name' !" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key requiert un attribut 'match' !" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key requiert un attribut 'use' !" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} requiert un attribut ''elements''" }, { "ER_MISSING_PREFIX_ATTRIB", "L''attribut ''prefix'' de (StylesheetHandler) {0} est manquant" }, { "ER_BAD_STYLESHEET_URL", "URL de la feuille de style erroné : {0}" }, { "ER_FILE_NOT_FOUND", "Fichier de la feuille de style introuvable : {0}" }, { "ER_IOEXCEPTION", "Exception d''E-S avec le fichier de la feuille de style : {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Impossible de trouver d''attribut href pour {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} est directement ou indirectement inclus dans lui-même !" }, { "ER_PROCESSINCLUDE_ERROR", "Erreur de StylesheetHandler.processInclude, {0}" }, { "ER_MISSING_LANG_ATTRIB", "L''attribut ''lang'' de (StylesheetHandler) {0} est manquant" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) position de l''élément {0} inadéquate ? Elément ''component'' de conteneur manquant" }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "Seule sortie possible vers Element, DocumentFragment, Document ou PrintWriter." }, { "ER_PROCESS_ERROR", "Erreur de StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Erreur de UnImplNode : {0}" }, { "ER_NO_SELECT_EXPRESSION", "Erreur ! Impossible de trouver l'expression de sélection xpath (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Impossible de sérialiser un XSLProcessor !" }, { "ER_NO_INPUT_STYLESHEET", "Entrée de feuille de style non spécifiée !" }, { "ER_FAILED_PROCESS_STYLESHEET", "Impossible de traiter la feuille de style !" }, { "ER_COULDNT_PARSE_DOC", "Impossible d''analyser le document {0} !" }, { "ER_COULDNT_FIND_FRAGMENT", "Impossible de trouver le fragment : {0}" }, { "ER_NODE_NOT_ELEMENT", "Le noeud désigné par l''identificateur de fragment n''est pas un élément : {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each doit posséder un attribut de correspondance ou de nom" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "Les modèles doivent posséder un attribut de correspondance ou de nom" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Pas de clone dans un fragment de document !" }, { "ER_CANT_CREATE_ITEM", "Impossible de créer l''élément dans l''arborescence de résultats : {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space du source XML possède une valeur incorrecte : {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Aucune déclaration xsl:key pour {0} !" }, { "ER_CANT_CREATE_URL", "Erreur ! Impossible de créer une URL pour : {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions n'est pas pris en charge" }, { "ER_PROCESSOR_ERROR", "Erreur TransformerFactory de XSLT" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} n''est pas pris en charge dans une feuille de style !" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns n'est plus pris en charge !  Préférez xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space n'est plus pris en charge !  Préférez xsl:strip-space ou xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result n'est plus pris en charge !  Préférez xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} comporte un attribut incorrect : {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Elément XSL inconnu : {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort ne peut être utilisé qu'avec xsl:apply-templates ou xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when ne figure pas à la bonne position !" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when sans rapport avec xsl:choose !" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise ne figure pas à la bonne position !" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise sans rapport avec xsl:choose !" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} n''est pas admis dans un modèle !" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) {0} préfixe de l''espace de noms de l''extension {1} inconnu" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Les importations peuvent être effectuées uniquement en tant que premiers éléments de la feuille de style !" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} s''importe lui-même directement ou indirectement !" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space possède une valeur incorrecte : {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "Echec de processStylesheet !" }, { "ER_SAX_EXCEPTION", "Exception SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Fonction non prise en charge !" }, { "ER_XSLT_ERROR", "Erreur XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "Tout symbole monétaire est interdit dans une chaîne de motif de correspondance" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "Fonction de document non prise en charge dans le DOM de la feuille de style !" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Impossible de résoudre le préfixe du solveur !" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Extension de redirection : Impossible d'extraire le nom du fichier - l'attribut de fichier ou de sélection doit retourner une chaîne valide." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Impossible de créer FormatterListener dans une extension Redirect !" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "Préfixe de exclude-result-prefixes non valide : {0}" }, { "ER_MISSING_NS_URI", "URI de l'espace de noms manquant pour le préfixe indiqué" }, { "ER_MISSING_ARG_FOR_OPTION", "Argument manquant pour l''option : {0}" }, { "ER_INVALID_OPTION", "Option incorrecte : {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Chaîne de format mal formée : {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet requiert un attribut 'version' !" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "L''attribut : {0} possède une valeur non valide : {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose requiert xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports interdit dans un xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Impossible d'utiliser DTMLiaison pour un noeud de DOM en sortie... Transmettez org.apache.xpath.DOM2Helper à la place !" }, { "ER_CANT_USE_DTM_FOR_INPUT", "Impossible d'utiliser DTMLiaison pour un noeud de DOM en entrée... Transmettez org.apache.xpath.DOM2Helper à la place !" }, { "ER_CALL_TO_EXT_FAILED", "Echec de l''appel de l''élément d''extension : {0}" }, { "ER_PREFIX_MUST_RESOLVE", "Le préfixe doit se convertir en espace de noms : {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Substitut UTF-16 non valide détecté : {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} s''utilise lui-même, ce qui provoque une boucle infinie." }, { "ER_CANNOT_MIX_XERCESDOM", "Impossible de mélanger une entrée autre que Xerces-DOM avec une sortie Xerces-DOM !" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "Dans ElemTemplateElement.readObject : {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Plusieurs modèles s''appellent : {0}" }, { "ER_INVALID_KEY_CALL", "Appel de fonction non valide : appels de key() récursifs interdits" }, { "ER_REFERENCING_ITSELF", "La variable {0} fait référence à elle-même directement ou indirectement !" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "Le noeud d'entrée ne peut être vide pour un DOMSource de newTemplates !" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Fichier de classe introuvable pour l''option {0}" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Elément requis introuvable : {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream ne doit pas être vide" }, { "ER_URI_CANNOT_BE_NULL", "L'URI ne doit pas être vide" }, { "ER_FILE_CANNOT_BE_NULL", "Le fichier ne doit pas être vide" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource ne doit pas être vide" }, { "ER_CANNOT_INIT_BSFMGR", "Impossible d'initialiser le gestionnaire de BSF" }, { "ER_CANNOT_CMPL_EXTENSN", "Impossible de compiler l'extension" }, { "ER_CANNOT_CREATE_EXTENSN", "Impossible de créer l''extension : {0} en raison de : {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "L''appel de la méthode d''instance de la méthode {0} requiert une instance d''Object comme premier argument" }, { "ER_INVALID_ELEMENT_NAME", "Nom d''élément non valide spécifié {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "La méthode de nom d''élément doit être statique {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "La fonction d''extension {0} : {1} est inconnue" }, { "ER_MORE_MATCH_CONSTRUCTOR", "Plusieurs occurrences proches pour le constructeur de {0}" }, { "ER_MORE_MATCH_METHOD", "Plusieurs occurrences proches pour la méthode {0}" }, { "ER_MORE_MATCH_ELEMENT", "Plusieurs occurrences proches pour la méthode d''élément {0}" }, { "ER_INVALID_CONTEXT_PASSED", "Contexte non valide transmis pour évaluer {0}" }, { "ER_POOL_EXISTS", "Pool déjà existant" }, { "ER_NO_DRIVER_NAME", "Aucun nom de périphérique indiqué" }, { "ER_NO_URL", "Aucune URL spécifiée" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "La taille du pool est inférieure à 1 !" }, { "ER_INVALID_DRIVER", "Nom de pilote non valide spécifié !" }, { "ER_NO_STYLESHEETROOT", "Impossible de trouver la racine de la feuille de style !" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Valeur incorrecte pour xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "Echec de processFromNode" }, { "ER_RESOURCE_COULD_NOT_LOAD", "La ressource [ {0} ] n''a pas pu charger : {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Taille du tampon <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Erreur inconnue lors de l'appel de l'extension" }, { "ER_NO_NAMESPACE_DECL", "Le préfixe {0} ne possède pas de déclaration d''espace de noms correspondante" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Contenu d''élément interdit pour lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "La feuille de style a provoqué l'arrêt" }, { "ER_ONE_OR_TWO", "1 ou 2" }, { "ER_TWO_OR_THREE", "2 ou 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Impossible de charger {0} (vérifier CLASSPATH), les valeurs par défaut sont donc employées" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Impossible d'initialiser les modèles par défaut" }, { "ER_RESULT_NULL", "Le résultat doit être vide" }, { "ER_RESULT_COULD_NOT_BE_SET", "Le résultat ne peut être défini" }, { "ER_NO_OUTPUT_SPECIFIED", "Aucune sortie spécifiée" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Transformation impossible vers un résultat de type {0}" }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Impossible de transformer une source de type {0}" }, { "ER_NULL_CONTENT_HANDLER", "Gestionnaire de contenu vide" }, { "ER_NULL_ERROR_HANDLER", "Gestionnaire d'erreurs vide" }, { "ER_CANNOT_CALL_PARSE", "L'analyse ne peut être appelée si le ContentHandler n'a pas été défini" }, { "ER_NO_PARENT_FOR_FILTER", "Pas de parent pour le filtre" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Aucune feuille de style dans : {0}, support = {1}" }, { "ER_NO_STYLESHEET_PI", "Pas de PI xml-stylesheet dans : {0}" }, { "ER_NOT_SUPPORTED", "Non pris en charge : {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "La valeur de la propriété {0} doit être une instance booléenne" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Impossible d''extraire le script externe à {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "La ressource [ {0} ] est introuvable.\n {1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Propriété de sortie non identifiée : {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Impossible de créer une instance de ElemLiteralResult" }, { "ER_VALUE_SHOULD_BE_NUMBER", "La valeur de {0} doit contenir un nombre analysable" }, { "ER_VALUE_SHOULD_EQUAL", "La valeur de {0} doit être oui ou non" }, { "ER_FAILED_CALLING_METHOD", "Echec de l''appel de la méthode {0}" }, { "ER_FAILED_CREATING_ELEMTMPL", "Echec de la création de l'instance de ElemTemplateElement" }, { "ER_CHARS_NOT_ALLOWED", "La présence de caractères n'est pas admise à cet endroit du document" }, { "ER_ATTR_NOT_ALLOWED", "L''attribut \"{0}\" n''est pas admis sur l''élément {1} !" }, { "ER_BAD_VALUE", "{0} valeur erronée {1} " }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Impossible de trouver la valeur de l''attribut {0} " }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Valeur de l''attribut {0} non identifiée " }, { "ER_NULL_URI_NAMESPACE", "Tentative de création d'un préfixe d'espace de noms avec un URI vide" }, { "ER_NUMBER_TOO_BIG", "Tentative de formatage d'un nombre supérieur à l'entier Long le plus élevé" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Impossible de trouver la classe {0} du pilote SAX1" }, { "ER_SAX1_DRIVER_NOT_LOADED", "Classe {0} du pilote SAX1 trouvée mais non chargée" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "Classe {0} du pilote SAX1 trouvée mais non instanciée" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "La classe {0} du pilote SAX1 n''implémente pas org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Propriété système org.xml.sax.parser non spécifiée" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "L'argument de l'analyseur ne doit pas être vide" }, { "ER_FEATURE", "Fonction : {0}" }, { "ER_PROPERTY", "Propriété : {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Solveur d'entité vide" }, { "ER_NULL_DTD_HANDLER", "Gestionnaire de DT vide" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Aucun nom de pilote spécifié !" }, { "ER_NO_URL_SPECIFIED", "Aucune URL spécifiée !" }, { "ER_POOLSIZE_LESS_THAN_ONE", "La taille du pool est inférieure à 1 !" }, { "ER_INVALID_DRIVER_NAME", "Nom de pilote non valide spécifié !" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Erreur de programme ! L'expression n'a pas de parent ElemTemplateElement !" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Assertion du programmeur dans RedundentExprEliminator : {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} ne peut pas figurer à cette position dans la feuille de style !" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "Seul de l'espace est accepté à cette position dans la feuille de style !" }, { "INVALID_TCHAR", "Valeur incorrecte : {1} utilisée pour l''attribut CHAR : {0}. Un attribut de type CHAR ne peut comporter qu''un seul caractère !" }, { "INVALID_QNAME", "Valeur incorrecte : {1} utilisée pour l''attribut QNAME : {0}" }, { "INVALID_ENUM", "Valeur incorrecte : {1} utilisée pour l''attribut ENUM : {0}. Les valeurs autorisées sont : {2}." }, { "INVALID_NMTOKEN", "Valeur incorrecte : {1} utilisée pour l''attribut NMTOKEN : {0}. " }, { "INVALID_NCNAME", "Valeur incorrecte : {1} utilisée pour l''attribut NCNAME : {0}. " }, { "INVALID_BOOLEAN", "Valeur incorrecte : {1} utilisée pour l''attribut booléen : {0}. " }, { "INVALID_NUMBER", "Valeur incorrecte : {1} utilisée pour l''attribut number : {0}. " }, { "ER_ARG_LITERAL", "L''argument de {0} dans le motif de correspondance doit être un littéral." }, { "ER_DUPLICATE_GLOBAL_VAR", "Déclaration de variable globale en double." }, { "ER_DUPLICATE_VAR", "Déclaration de variable en double." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template doit comporter un attribut name et/ou match" }, { "ER_INVALID_PREFIX", "Préfixe de exclude-result-prefixes non valide : {0}" }, { "ER_NO_ATTRIB_SET", "attribute-set {0} n''existe pas" }, { "ER_FUNCTION_NOT_FOUND", "La fonction {0} n''existe pas" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "L''élément {0} ne peut posséder un attribut content et un attribut select." }, { "ER_INVALID_SET_PARAM_VALUE", "La valeur du paramètre {0} doit être un objet Java valide" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "L'attribut result-prefix d'un élément xsl:namespace-alias a la valeur '#default', mais il n'existe aucune déclaration de l'espace de noms par défaut dans la portée de l'élément" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "L''attribut result-prefix d''un élément xsl:namespace-alias a la valeur ''{0}'', mais il n''existe aucune déclaration d''espace de noms pour le préfixe ''{0}'' dans la portée de l''élément." }, { "ER_SET_FEATURE_NULL_NAME", "Le nom de la fonction ne peut pas avoir une valeur null dans TransformerFactory.setFeature (nom chaîne, valeur boolénne)." }, { "ER_GET_FEATURE_NULL_NAME", "Le nom de la fonction ne peut pas avoir une valeur null dans TransformerFactory.getFeature (nom chaîne)." }, { "ER_UNSUPPORTED_FEATURE", "Impossible de définir la fonction ''{0}'' sur cet élément TransformerFactory." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "L''utilisation de l''élément d''extension ''{0}'' n''est pas admise lorsque la fonction de traitement sécurisée a la valeur true." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Impossible d'obtenir le préfixe pour un uri d'espace de noms null." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Impossible d'obtenir l'uri d'espace de noms pour le préfixe null." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "Le nom de la fonction ne peut pas avoir une valeur null." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "La parité ne peut pas être négative." }, { "WG_FOUND_CURLYBRACE", "Une accolade ('}') a été trouvée alors qu'aucun modèle d'attribut n'est ouvert !" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Avertissement : L''attribut de count n''a pas d''ascendant dans xsl:number ! Cible = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Syntaxe obsolète : Le nom de l'attribut 'expr' a été remplacé par 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan ne gère pas encore le nom d'environnement local de la fonction format-number." }, { "WG_LOCALE_NOT_FOUND", "Avertissement : Impossible de trouver un environnement local pour xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "Impossible de créer l''URL à partir de : {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Impossible de charger le document demandé : {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Impossible de trouver une fonction de regroupement pour <sort xml:lang= {0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Syntaxe obsolète : L''instruction de fonction doit utiliser une URL {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "encodage non pris en charge : {0}, en utilisant UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "encodage non pris en charge : {0}, en utilisant Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "Conflits de spécificités trouvés : {0} La dernière de la feuille de style sera employée." }, { "WG_PARSING_AND_PREPARING", "========= Analyse et préparation de {0} ==========" }, { "WG_ATTR_TEMPLATE", "Modèle d''attribut, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Conflit de correspondances entre xsl:strip-space et xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan ne gère pas encore l''attribut {0} !" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Pas de déclaration pour le format décimal : {0}" }, { "WG_OLD_XSLT_NS", "Espace de noms XSLT manquant ou incorrect. " }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Une seule déclaration xsl:decimal-format par défaut est admise." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Les noms xsl:decimal-format doivent être uniques. Le nom \"{0}\" a été dupliqué." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} comporte un attribut incorrect : {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Impossible de convertir le préfixe de l''espace de noms : {0}. Le noeud n''est pas traité." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet requiert un attribut 'version' !" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Nom d''attribut incorrect : {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Valeur incorrecte pour l''attribut {0} : {1}" }, { "WG_EMPTY_SECOND_ARG", "L'ensemble de noeuds résultant du second argument de la fonction du document est vide. Un ensemble de noeuds vide est retourné." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "La valeur de l'attribut 'name' de xsl:processing-instruction doit être différente de 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "La valeur de l''attribut ''name'' de xsl:processing-instruction doit être un nom NCName valide : {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Ajout impossible de l''attribut {0} après des noeuds enfants ou avant la production d''un élément. L''attribut est ignoré." }, { "NO_MODIFICATION_ALLOWED_ERR", "Tentative de modification d'un objet qui n'accepte pas les modifications." }, { "ui_language", "en" }, { "help_language", "en" }, { "language", "en" }, { "BAD_CODE", "Le paramètre de createMessage se trouve hors limites" }, { "FORMAT_FAILED", "Exception soulevée lors de l'appel de messageFormat" }, { "version", ">>>>>>> Version de Xalan " }, { "version2", "<<<<<<<" }, { "yes", "oui" }, { "line", "Ligne #" }, { "column", "Colonne #" }, { "xsldone", "XSLProcessor : terminé" }, { "xslProc_option", "Options de classe Process de ligne de commande Xalan-J :" }, { "xslProc_option", "Options de classe Process de ligne de commande Xalan-J:" }, { "xslProc_invalid_xsltc_option", "L''option {0} n''est pas prise en charge en mode XSLTC." }, { "xslProc_invalid_xalan_option", "L''option {0} s''utilise uniquement avec -XSLTC." }, { "xslProc_no_input", "Erreur : Aucun xml de feuille de style ou d'entrée n'est spécifié. Exécutez cette commande sans option pour les instructions d'utilisation." }, { "xslProc_common_options", "-Options courantes-" }, { "xslProc_xalan_options", "-Options pour Xalan-" }, { "xslProc_xsltc_options", "-Options pour XSLTC-" }, { "xslProc_return_to_continue", "(appuyez sur <Retour> pour continuer)" }, { "optionXSLTC", "   [-XSLTC (utilisez XSLTC pour la transformation)]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "   [-XSL URLXSLTransformation]" }, { "optionOUT", "   [-OUT nomFichierSortie]" }, { "optionLXCIN", "   [-LXCIN NomFichierFeuilleDeStylesCompiléEntrée]" }, { "optionLXCOUT", "   [-LXCOUT NomFichierFeuilleDeStylesCompiléSortie]" }, { "optionPARSER", "   [-PARSER nom de classe pleinement qualifié pour la liaison de l'analyseur]" }, { "optionE", "   [-E (Ne pas développer les réf. d'entité)]" }, { "optionV", "   [-E (Ne pas développer les réf. d'entité)]" }, { "optionQC", "   [-QC (Avertissements brefs de conflits de motifs)]" }, { "optionQ", "   [-Q  (Mode bref)]" }, { "optionLF", "   [-LF (Utilise des sauts de ligne uniquement dans la sortie {CR/LF par défaut})]" }, { "optionCR", "   [-LF (Utilise des retours chariot uniquement dans la sortie {CR/LF par défaut})]" }, { "optionESCAPE", "   [-ESCAPE (Caractères d'échappement {<>&\"'\\r\\n par défaut}]" }, { "optionINDENT", "   [-INDENT (Nombre d'espaces pour le retrait {par défaut 0})]" }, { "optionTT", "   [-TT (Contrôle les appels de modèles - fonction de trace.)]" }, { "optionTG", "   [-TG (Contrôle chaque événement de génération - fonction de trace.)]" }, { "optionTS", "   [-TS (Contrôle chaque événement de sélection - fonction de trace.)]" }, { "optionTTC", "   [-TTC (Contrôle les enfants du modèle lors de leur traitement - fonction de trace.)]" }, { "optionTCLASS", "   [-TCLASS (Classe TraceListener pour les extensions de trace.)]" }, { "optionVALIDATE", "   [-VALIDATE (Indique si la validation se produit. La validation est désactivée par défaut.)]" }, { "optionEDUMP", "   [-EDUMP {nom de fichier optionnel} (Crée un vidage de pile en cas d'erreur.)]" }, { "optionXML", "   [-XML (Utilise un formateur XML et ajoute un en-tête XML.)]" }, { "optionTEXT", "   [-TEXT (Utilise un formateur de texte simple.)]" }, { "optionHTML", "   [-HTML (Utilise un formateur HTML.)]" }, { "optionPARAM", "   [-PARAM nom expression (Définit un paramètre de feuille de style)]" }, { "noParsermsg1", "Echec du processus XSL." }, { "noParsermsg2", "** Analyseur introuvable **" }, { "noParsermsg3", "Vérifiez le chemin d'accès des classes." }, { "noParsermsg4", "XML Parser for Java disponible en téléchargement sur le site" }, { "noParsermsg5", "AlphaWorks de IBM : http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER nom de classe complet (Les URI sont résolus par URIResolver)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER nom de classe complet (Les URI sont résolus par EntityResolver)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER nom de classe complet (La sérialisation de la sortie est effectuée par ContentHandler)]" }, { "optionLINENUMBERS", "   [-L utilisation des numéros de ligne pour le document source]" }, { "optionSECUREPROCESSING", "   [-SECURE (attribuez la valeur true à la fonction de traitement sécurisé.)]" }, { "optionMEDIA", "   [-MEDIA type_de_support (Utilise un attribut de support pour trouver la feuille de style associée à un document.)]" }, { "optionFLAVOR", "   [-FLAVOR sax_ou_dom (effectue la transformation à l'aide de SAX (s2s) ou de DOM (d2d).)] " }, { "optionDIAG", "   [-DIAG (affiche la durée globale de la transformation - en millisecondes.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (construction incrémentielle du DTM en définissant http://xml.apache.org/xalan/features/incremental true.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (pas de traitement d'optimisation des feuilles de style en définissant http://xml.apache.org/xalan/features/optimize false.)]" }, { "optionRL", "   [-RL récursivité_maxi (limite de la profondeur de la récursivité pour les feuilles de style.)]" }, { "optionXO", "   [-XO [nom_translet] (assignation du nom au translet généré)]" }, { "optionXD", "   [-XD répertoire_cible (spécification d'un répertoire de destination pour translet)]" }, { "optionXJ", "   [-XJ fichier_jar (réunion des classes translet dans un fichier jar appelé <fichier_jar>)]" }, { "optionXP", "   [-XP module (spécification d'un préfixe de nom de module pour toutes les classes translet générées)]" }, { "optionXN", "   [-XN (activation de la mise en ligne de modèle)]" }, { "optionXX", "   [-XX (activation du débogage supplémentaire de sortie de message)]" }, { "optionXT", "   [-XT (utilisation de translet pour la transformation si possible)]" }, { "diagTiming", " --------- La transformation de {0} via {1} a pris {2} ms" }, { "recursionTooDeep", "Trop grande imbrication de modèle. imbrication = {0}, modèle {1} {2}" }, { "nameIs", "le nom est" }, { "matchPatternIs", "le motif de correspondance est" } };
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_fr
 * JD-Core Version:    0.7.0.1
 */