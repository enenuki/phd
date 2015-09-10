/*    1:     */ package org.apache.xalan.res;
/*    2:     */ 
/*    3:     */ import java.util.ListResourceBundle;
/*    4:     */ import java.util.Locale;
/*    5:     */ import java.util.MissingResourceException;
/*    6:     */ import java.util.ResourceBundle;
/*    7:     */ 
/*    8:     */ public class XSLTErrorResources_pt_BR
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
/*  264:     */   public static final String ERROR_HEADER = "Erro: ";
/*  265:     */   public static final String WARNING_HEADER = "Aviso: ";
/*  266:     */   public static final String XSL_HEADER = "XSLT ";
/*  267:     */   public static final String XML_HEADER = "XML ";
/*  268:     */   /**
/*  269:     */    * @deprecated
/*  270:     */    */
/*  271:     */   public static final String QUERY_HEADER = "PADRÃO ";
/*  272:     */   
/*  273:     */   public Object[][] getContents()
/*  274:     */   {
/*  275: 491 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_NO_CURLYBRACE", "Erro: Impossível ter '{' na expressão" }, { "ER_ILLEGAL_ATTRIBUTE", "{0} possui um atributo inválido: {1}" }, { "ER_NULL_SOURCENODE_APPLYIMPORTS", "sourceNode é nulo em xsl:apply-imports!" }, { "ER_CANNOT_ADD", "Impossível incluir {0} em {1}" }, { "ER_NULL_SOURCENODE_HANDLEAPPLYTEMPLATES", "sourceNode é nulo em handleApplyTemplatesInstruction!" }, { "ER_NO_NAME_ATTRIB", "{0} deve ter um atributo name." }, { "ER_TEMPLATE_NOT_FOUND", "Não foi possível localizar o template: {0}" }, { "ER_CANT_RESOLVE_NAME_AVT", "Não foi possível resolver nome AVT em xsl:call-template." }, { "ER_REQUIRES_ATTRIB", "{0} requer o atributo: {1}" }, { "ER_MUST_HAVE_TEST_ATTRIB", "{0} deve ter um atributo ''test''." }, { "ER_BAD_VAL_ON_LEVEL_ATTRIB", "Valor inválido no atributo de nível: {0}" }, { "ER_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "O nome de processing-instruction não pode ser 'xml'" }, { "ER_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "O nome de processing-instruction deve ser um NCName válido: {0}" }, { "ER_NEED_MATCH_ATTRIB", "{0} deve ter um atributo de correspondência se tiver um modo." }, { "ER_NEED_NAME_OR_MATCH_ATTRIB", "{0} requer um nome ou um atributo de correspondência." }, { "ER_CANT_RESOLVE_NSPREFIX", "Impossível resolver prefixo do espaço de nomes: {0}" }, { "ER_ILLEGAL_VALUE", "xml:space possui um valor inválido: {0}" }, { "ER_NO_OWNERDOC", "O nó filho não possui um documento do proprietário!" }, { "ER_ELEMTEMPLATEELEM_ERR", "Erro de ElemTemplateElement: {0}" }, { "ER_NULL_CHILD", "Tentando incluir um filho nulo!" }, { "ER_NEED_SELECT_ATTRIB", "{0} requer um atributo select." }, { "ER_NEED_TEST_ATTRIB", "xsl:when deve ter um atributo 'test'." }, { "ER_NEED_NAME_ATTRIB", "xsl:with-param deve ter um atributo 'name'." }, { "ER_NO_CONTEXT_OWNERDOC", "context não possui um documento do proprietário!" }, { "ER_COULD_NOT_CREATE_XML_PROC_LIAISON", "Não foi possível criar XML TransformerFactory Liaison: {0}" }, { "ER_PROCESS_NOT_SUCCESSFUL", "Xalan: O processo não foi bem-sucedido." }, { "ER_NOT_SUCCESSFUL", "Xalan: não foi bem-sucedido." }, { "ER_ENCODING_NOT_SUPPORTED", "Codificação não suportada: {0}" }, { "ER_COULD_NOT_CREATE_TRACELISTENER", "Não foi possível criar TraceListener: {0}" }, { "ER_KEY_REQUIRES_NAME_ATTRIB", "xsl:key requer um atributo 'name'!" }, { "ER_KEY_REQUIRES_MATCH_ATTRIB", "xsl:key requer um atributo 'match'!" }, { "ER_KEY_REQUIRES_USE_ATTRIB", "xsl:key requer um atributo 'use'!" }, { "ER_REQUIRES_ELEMENTS_ATTRIB", "(StylesheetHandler) {0} requer um atributo ''elements''!" }, { "ER_MISSING_PREFIX_ATTRIB", "(StylesheetHandler) O atributo ''prefix'' de {0} está ausente" }, { "ER_BAD_STYLESHEET_URL", "A URL da página de estilo é inválida: {0}" }, { "ER_FILE_NOT_FOUND", "O arquivo da página de estilo não foi encontrado: {0}" }, { "ER_IOEXCEPTION", "Ocorreu uma Exceção de E/S (entrada/saída) no arquivo de página de estilo: {0}" }, { "ER_NO_HREF_ATTRIB", "(StylesheetHandler) Não foi possível encontrar o atributo href para {0}" }, { "ER_STYLESHEET_INCLUDES_ITSELF", "(StylesheetHandler) {0} está incluindo a si mesmo, direta ou indiretamente!" }, { "ER_PROCESSINCLUDE_ERROR", "Erro de StylesheetHandler.processInclude, {0}" }, { "ER_MISSING_LANG_ATTRIB", "(StylesheetHandler) O atributo ''lang'' de {0} está ausente" }, { "ER_MISSING_CONTAINER_ELEMENT_COMPONENT", "(StylesheetHandler) Elemento {0} aplicado incorretamente?? O elemento de contêiner ''component'' está ausente " }, { "ER_CAN_ONLY_OUTPUT_TO_ELEMENT", "A saída pode ser apenas para um Element, DocumentFragment, Document ou PrintWriter." }, { "ER_PROCESS_ERROR", "Erro de StylesheetRoot.process" }, { "ER_UNIMPLNODE_ERROR", "Erro de UnImplNode: {0}" }, { "ER_NO_SELECT_EXPRESSION", "Erro! Não encontrada a expressão xpath select (-select)." }, { "ER_CANNOT_SERIALIZE_XSLPROCESSOR", "Não é possível serializar um XSLProcessor!" }, { "ER_NO_INPUT_STYLESHEET", "A entrada de folha de estilo não foi especificada!" }, { "ER_FAILED_PROCESS_STYLESHEET", "Falha ao processar folha de estilo!" }, { "ER_COULDNT_PARSE_DOC", "Não foi possível analisar o documento {0}!" }, { "ER_COULDNT_FIND_FRAGMENT", "Não foi possível localizar o fragmento: {0}" }, { "ER_NODE_NOT_ELEMENT", "O nó apontado por um identificador de fragmento não era um elemento: {0}" }, { "ER_FOREACH_NEED_MATCH_OR_NAME_ATTRIB", "for-each deve ter um atributo match ou name" }, { "ER_TEMPLATES_NEED_MATCH_OR_NAME_ATTRIB", "templates deve ter um atributo match ou name" }, { "ER_NO_CLONE_OF_DOCUMENT_FRAG", "Nenhum clone de fragmento de documento!" }, { "ER_CANT_CREATE_ITEM", "Impossível criar item na árvore de resultados: {0}" }, { "ER_XMLSPACE_ILLEGAL_VALUE", "xml:space no XML de origem possui um valor inválido: {0}" }, { "ER_NO_XSLKEY_DECLARATION", "Não existe nenhuma declaração xsl:key para {0}!" }, { "ER_CANT_CREATE_URL", "Erro! Impossível criar url para: {0}" }, { "ER_XSLFUNCTIONS_UNSUPPORTED", "xsl:functions não é suportado" }, { "ER_PROCESSOR_ERROR", "Erro de XSLT TransformerFactory" }, { "ER_NOT_ALLOWED_INSIDE_STYLESHEET", "(StylesheetHandler) {0} não permitido dentro de uma folha de estilo!" }, { "ER_RESULTNS_NOT_SUPPORTED", "result-ns não é mais suportado!  Utilize então xsl:output." }, { "ER_DEFAULTSPACE_NOT_SUPPORTED", "default-space não é mais suportado!  Utilize então xsl:strip-space ou xsl:preserve-space." }, { "ER_INDENTRESULT_NOT_SUPPORTED", "indent-result não é mais suportado!  Utilize então xsl:output." }, { "ER_ILLEGAL_ATTRIB", "(StylesheetHandler) {0} possui um atributo inválido: {1}" }, { "ER_UNKNOWN_XSL_ELEM", "Elemento XSL desconhecido: {0}" }, { "ER_BAD_XSLSORT_USE", "(StylesheetHandler) xsl:sort somente pode ser utilizado com xsl:apply-templates ou xsl:for-each." }, { "ER_MISPLACED_XSLWHEN", "(StylesheetHandler) xsl:when aplicado incorretamente!" }, { "ER_XSLWHEN_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:when não está ligado a xsl:choose!" }, { "ER_MISPLACED_XSLOTHERWISE", "(StylesheetHandler) xsl:otherwise aplicado incorretamente!" }, { "ER_XSLOTHERWISE_NOT_PARENTED_BY_XSLCHOOSE", "(StylesheetHandler) xsl:otherwise não está ligado a xsl:choose!" }, { "ER_NOT_ALLOWED_INSIDE_TEMPLATE", "(StylesheetHandler) {0} não é permitido dentro de um template!" }, { "ER_UNKNOWN_EXT_NS_PREFIX", "(StylesheetHandler) o espaço de nomes de extensão {0} possui prefixo {1} desconhecido" }, { "ER_IMPORTS_AS_FIRST_ELEM", "(StylesheetHandler) Importações só podem ocorrer como os primeiros elementos na folha de estilo!" }, { "ER_IMPORTING_ITSELF", "(StylesheetHandler) {0} está importando a si mesmo, direta ou indiretamente!" }, { "ER_XMLSPACE_ILLEGAL_VAL", "(StylesheetHandler) xml:space tem um valor inválido: {0}" }, { "ER_PROCESSSTYLESHEET_NOT_SUCCESSFUL", "processStylesheet não obteve êxito!" }, { "ER_SAX_EXCEPTION", "Exceção de SAX" }, { "ER_FUNCTION_NOT_SUPPORTED", "Função não suportada!" }, { "ER_XSLT_ERROR", "Erro de XSLT" }, { "ER_CURRENCY_SIGN_ILLEGAL", "O sinal monetário não é permitido na cadeia de padrões de formato" }, { "ER_DOCUMENT_FUNCTION_INVALID_IN_STYLESHEET_DOM", "Função Document não suportada no DOM da Folha de Estilo!" }, { "ER_CANT_RESOLVE_PREFIX_OF_NON_PREFIX_RESOLVER", "Impossível resolver prefixo de solucionador sem Prefixo!" }, { "ER_REDIRECT_COULDNT_GET_FILENAME", "Redirecionar extensão: Não foi possível obter o nome do arquivo - o atributo file ou select deve retornar uma cadeia válida." }, { "ER_CANNOT_BUILD_FORMATTERLISTENER_IN_REDIRECT", "Impossível construir FormatterListener em Redirecionar extensão!" }, { "ER_INVALID_PREFIX_IN_EXCLUDERESULTPREFIX", "O prefixo em exclude-result-prefixes não é válido: {0}" }, { "ER_MISSING_NS_URI", "URI do espaço de nomes ausente para o prefixo especificado" }, { "ER_MISSING_ARG_FOR_OPTION", "Argumento ausente para a opção: {0}" }, { "ER_INVALID_OPTION", "Opção inválida: {0}" }, { "ER_MALFORMED_FORMAT_STRING", "Cadeia com problemas de formatação: {0}" }, { "ER_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet requer um atributo 'version'!" }, { "ER_ILLEGAL_ATTRIBUTE_VALUE", "Atributo: {0} possui um valor inválido: {1}" }, { "ER_CHOOSE_REQUIRES_WHEN", "xsl:choose requer um xsl:when" }, { "ER_NO_APPLY_IMPORT_IN_FOR_EACH", "xsl:apply-imports não permitido em um xsl:for-each" }, { "ER_CANT_USE_DTM_FOR_OUTPUT", "Impossível utilizar um DTMLiaison para um nó DOM de saída... transmita um org.apache.xpath.DOM2Helper no lugar!" }, { "ER_CANT_USE_DTM_FOR_INPUT", "Impossível utilizar um DTMLiaison para um nó DOM de entrada... transmita um org.apache.xpath.DOM2Helper no lugar!" }, { "ER_CALL_TO_EXT_FAILED", "Falha na chamada do elemento da extensão: {0}" }, { "ER_PREFIX_MUST_RESOLVE", "O prefixo deve ser resolvido para um espaço de nomes: {0}" }, { "ER_INVALID_UTF16_SURROGATE", "Detectado substituto UTF-16 inválido: {0} ?" }, { "ER_XSLATTRSET_USED_ITSELF", "xsl:attribute-set {0} utilizou a si mesmo, o que causará um loop infinito." }, { "ER_CANNOT_MIX_XERCESDOM", "Impossível misturar entrada não Xerces-DOM com saída Xerces-DOM!" }, { "ER_TOO_MANY_LISTENERS", "addTraceListenersToStylesheet - TooManyListenersException" }, { "ER_IN_ELEMTEMPLATEELEM_READOBJECT", "Em ElemTemplateElement.readObject: {0}" }, { "ER_DUPLICATE_NAMED_TEMPLATE", "Encontrado mais de um template chamado: {0}" }, { "ER_INVALID_KEY_CALL", "Chamada de função inválida: chamadas key() recursivas não são permitidas" }, { "ER_REFERENCING_ITSELF", "A variável {0} está fazendo referência a si mesmo, direta ou indiretamente!" }, { "ER_ILLEGAL_DOMSOURCE_INPUT", "O nó de entrada não pode ser nulo para um DOMSource de newTemplates!" }, { "ER_CLASS_NOT_FOUND_FOR_OPTION", "Arquivo de classe não encontrado para a opção {0}" }, { "ER_REQUIRED_ELEM_NOT_FOUND", "Elemento requerido não encontrado: {0}" }, { "ER_INPUT_CANNOT_BE_NULL", "InputStream não pode ser nulo" }, { "ER_URI_CANNOT_BE_NULL", "URI não pode ser nulo" }, { "ER_FILE_CANNOT_BE_NULL", "File não pode ser nulo" }, { "ER_SOURCE_CANNOT_BE_NULL", "InputSource não pode ser nulo" }, { "ER_CANNOT_INIT_BSFMGR", "Não foi possível inicializar o BSF Manager" }, { "ER_CANNOT_CMPL_EXTENSN", "Não foi possível compilar a extensão" }, { "ER_CANNOT_CREATE_EXTENSN", "Não foi possível criar extensão: {0} devido a: {1}" }, { "ER_INSTANCE_MTHD_CALL_REQUIRES", "A chamada do método da instância para o método {0} requer uma instância Object como primeiro argumento" }, { "ER_INVALID_ELEMENT_NAME", "Especificado nome de elemento inválido {0}" }, { "ER_ELEMENT_NAME_METHOD_STATIC", "O método do nome de elemento deve ser estático {0}" }, { "ER_EXTENSION_FUNC_UNKNOWN", "A função de extensão {0} : {1} é desconhecida" }, { "ER_MORE_MATCH_CONSTRUCTOR", "Mais de uma correspondência principal para o construtor de {0}" }, { "ER_MORE_MATCH_METHOD", "Mais de uma correspondência principal para o método {0}" }, { "ER_MORE_MATCH_ELEMENT", "Mais de uma correspondência principal para o método do elemento {0}" }, { "ER_INVALID_CONTEXT_PASSED", "Contexto inválido transmitido para avaliar {0}" }, { "ER_POOL_EXISTS", "O conjunto já existe" }, { "ER_NO_DRIVER_NAME", "Nenhum Nome de driver foi especificado" }, { "ER_NO_URL", "Nenhuma URL especificada" }, { "ER_POOL_SIZE_LESSTHAN_ONE", "O tamanho do conjunto é menor que um!" }, { "ER_INVALID_DRIVER", "Especificado nome de driver inválido!" }, { "ER_NO_STYLESHEETROOT", "Não encontrada a raiz da folha de estilo!" }, { "ER_ILLEGAL_XMLSPACE_VALUE", "Valor inválido para xml:space" }, { "ER_PROCESSFROMNODE_FAILED", "processFromNode falhou" }, { "ER_RESOURCE_COULD_NOT_LOAD", "O recurso [ {0} ] não pôde carregar: {1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "Tamanho do buffer <=0" }, { "ER_UNKNOWN_ERROR_CALLING_EXTENSION", "Erro desconhecido ao chamar a extensão" }, { "ER_NO_NAMESPACE_DECL", "O prefixo {0} não possui uma declaração do espaço de nomes correspondente" }, { "ER_ELEM_CONTENT_NOT_ALLOWED", "Conteúdo de elemento não permitido para lang=javaclass {0}" }, { "ER_STYLESHEET_DIRECTED_TERMINATION", "Finalização direcionada por folha de estilo" }, { "ER_ONE_OR_TWO", "1 ou 2" }, { "ER_TWO_OR_THREE", "2 ou 3" }, { "ER_COULD_NOT_LOAD_RESOURCE", "Não foi possível carregar {0} (verificar CLASSPATH); utilizando apenas os padrões" }, { "ER_CANNOT_INIT_DEFAULT_TEMPLATES", "Impossível inicializar templates padrão" }, { "ER_RESULT_NULL", "O resultado não deve ser nulo" }, { "ER_RESULT_COULD_NOT_BE_SET", "O resultado não pôde ser definido" }, { "ER_NO_OUTPUT_SPECIFIED", "Nenhuma saída especificada" }, { "ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", "Não é possível transformar em um Resultado do tipo {0} " }, { "ER_CANNOT_TRANSFORM_SOURCE_TYPE", "Não é possível transformar em uma Origem do tipo {0} " }, { "ER_NULL_CONTENT_HANDLER", "Rotina de tratamento de conteúdo nula" }, { "ER_NULL_ERROR_HANDLER", "Rotina de tratamento de erros nula" }, { "ER_CANNOT_CALL_PARSE", "parse não pode ser chamado se ContentHandler não tiver sido definido" }, { "ER_NO_PARENT_FOR_FILTER", "Nenhum pai para o filtro" }, { "ER_NO_STYLESHEET_IN_MEDIA", "Nenhuma página de estilo foi encontrada em: {0}, mídia= {1}" }, { "ER_NO_STYLESHEET_PI", "Nenhum PI xml-stylesheet encontrado em: {0}" }, { "ER_NOT_SUPPORTED", "Não suportado: {0}" }, { "ER_PROPERTY_VALUE_BOOLEAN", "O valor para a propriedade {0} deve ser uma instância Booleana" }, { "ER_COULD_NOT_FIND_EXTERN_SCRIPT", "Não foi possível obter script externo em {0}" }, { "ER_RESOURCE_COULD_NOT_FIND", "O recurso [ {0} ] não pôde ser encontrado.\n{1}" }, { "ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", "Propriedade de saída não reconhecida: {0}" }, { "ER_FAILED_CREATING_ELEMLITRSLT", "Falha ao criar a instância ElemLiteralResult" }, { "ER_VALUE_SHOULD_BE_NUMBER", "O valor para {0} deve conter um número analisável" }, { "ER_VALUE_SHOULD_EQUAL", "O valor de {0} deve ser igual a yes ou no" }, { "ER_FAILED_CALLING_METHOD", "Falha ao chamar o método {0}" }, { "ER_FAILED_CREATING_ELEMTMPL", "Falha ao criar a instância ElemTemplateElement" }, { "ER_CHARS_NOT_ALLOWED", "Não são permitidos caracteres neste ponto do documento" }, { "ER_ATTR_NOT_ALLOWED", "O atributo \"{0}\" não é permitido no elemento {1}!" }, { "ER_BAD_VALUE", "{0} possui valor inválido {1}" }, { "ER_ATTRIB_VALUE_NOT_FOUND", "Valor do atributo {0} não encontrado" }, { "ER_ATTRIB_VALUE_NOT_RECOGNIZED", "Valor do atributo {0} não reconhecido" }, { "ER_NULL_URI_NAMESPACE", "Tentando gerar um prefixo do espaço de nomes com URI nulo" }, { "ER_NUMBER_TOO_BIG", "Tentando formatar um número superior ao maior inteiro Longo" }, { "ER_CANNOT_FIND_SAX1_DRIVER", "Impossível encontrar a classe de driver SAX1 {0}" }, { "ER_SAX1_DRIVER_NOT_LOADED", "Classe de driver SAX1 {0} encontrada, mas não pode ser carregada" }, { "ER_SAX1_DRIVER_NOT_INSTANTIATED", "Classe de driver SAX1 {0} carregada, mas não pode ser instanciada" }, { "ER_SAX1_DRIVER_NOT_IMPLEMENT_PARSER", "A classe de driver SAX1 {0} não implementa org.xml.sax.Parser" }, { "ER_PARSER_PROPERTY_NOT_SPECIFIED", "Propriedade de sistema org.xml.sax.parser não especificada" }, { "ER_PARSER_ARG_CANNOT_BE_NULL", "O argumento Parser não deve ser nulo" }, { "ER_FEATURE", "Recurso: {0}" }, { "ER_PROPERTY", "Propriedade: {0}" }, { "ER_NULL_ENTITY_RESOLVER", "Solucionador de entidade nulo" }, { "ER_NULL_DTD_HANDLER", "Rotina de tratamento DTD nula" }, { "ER_NO_DRIVER_NAME_SPECIFIED", "Nenhum Nome de Driver Especificado!" }, { "ER_NO_URL_SPECIFIED", "Nenhuma URL Especificada!" }, { "ER_POOLSIZE_LESS_THAN_ONE", "O tamanho do conjunto é menor que 1!" }, { "ER_INVALID_DRIVER_NAME", "Especificado Nome de Driver Inválido!" }, { "ER_ERRORLISTENER", "ErrorListener" }, { "ER_ASSERT_NO_TEMPLATE_PARENT", "Erro do programador! A expressão não possui o pai ElemTemplateElement!" }, { "ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", "Declaração do programador em RedundentExprEliminator: {0}" }, { "ER_NOT_ALLOWED_IN_POSITION", "{0} não é permitido nesta posição na página de estilo!" }, { "ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", "O texto sem espaço em branco não é permitido nesta posição na página de estilo!" }, { "INVALID_TCHAR", "Valor inválido: {1} utilizado para o caractere CHAR: {0}. Um atributo de tipo CHAR deve ter apenas 1 caractere!" }, { "INVALID_QNAME", "Valor inválido: {1} utilizado para o atributo QNAME: {0}" }, { "INVALID_ENUM", "Valor inválido: {1} utilizado para o atributo ENUM: {0}. Os valores válidos são: {2}." }, { "INVALID_NMTOKEN", "Valor inválido: {1} utilizado para o atributo NMTOKEN: {0}" }, { "INVALID_NCNAME", "Valor inválido: {1} utilizado para o atributo NCNAME: {0}" }, { "INVALID_BOOLEAN", "Valor inválido: {1} utilizado para o atributo boolean: {0}" }, { "INVALID_NUMBER", "Valor inválido: {1} utilizado para o atributo number: {0}" }, { "ER_ARG_LITERAL", "Argumento para {0} no padrão de correspondência deve ser um literal." }, { "ER_DUPLICATE_GLOBAL_VAR", "Declaração de variável global duplicada." }, { "ER_DUPLICATE_VAR", "Declaração de variável duplicada." }, { "ER_TEMPLATE_NAME_MATCH", "xsl:template deve ter um atributo name ou match (ou ambos)" }, { "ER_INVALID_PREFIX", "O prefixo em exclude-result-prefixes não é válido: {0}" }, { "ER_NO_ATTRIB_SET", "O attribute-set {0} não existe" }, { "ER_FUNCTION_NOT_FOUND", "A função denominada {0} não existe" }, { "ER_CANT_HAVE_CONTENT_AND_SELECT", "O elemento {0} não deve ter um conteúdo e um atributo de seleção ao mesmo tempo." }, { "ER_INVALID_SET_PARAM_VALUE", "O valor do parâmetro {0} deve ser um Objeto Java válido" }, { "ER_INVALID_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX_FOR_DEFAULT", "O atributo result-prefix de um elemento xsl:namespace-alias tem o valor '#default', mas não há nenhuma declaração do espaço de nomes padrão no escopo para o elemento" }, { "ER_INVALID_SET_NAMESPACE_URI_VALUE_FOR_RESULT_PREFIX", "O atributo result-prefix de um elemento xsl:namespace-alias tem o valor ''{0}'', mas não há nenhuma declaração do espaço de nomes para o prefixo ''{0}'' no escopo para o elemento." }, { "ER_SET_FEATURE_NULL_NAME", "O nome do recurso não pode ser nulo em TransformerFactory.setFeature(String name, boolean value)." }, { "ER_GET_FEATURE_NULL_NAME", "O nome do recurso não pode ser nulo em TransformerFactory.getFeature(String name)." }, { "ER_UNSUPPORTED_FEATURE", "Não é possível definir o recurso ''{0}'' neste TransformerFactory." }, { "ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", "O uso do elemento de extensão ''{0}'' não é permitido quando o recurso de processamento seguro é definido como true." }, { "ER_NAMESPACE_CONTEXT_NULL_NAMESPACE", "Não é possível obter o prefixo para um uri de espaço de nomes nulo." }, { "ER_NAMESPACE_CONTEXT_NULL_PREFIX", "Não é possível obter o uri do espaço de nomes para um prefixo nulo." }, { "ER_XPATH_RESOLVER_NULL_QNAME", "O nome da função não pode ser nulo." }, { "ER_XPATH_RESOLVER_NEGATIVE_ARITY", "O arity não pode ser negativo." }, { "WG_FOUND_CURLYBRACE", "Encontrado '}', mas nenhum template de atributo aberto!" }, { "WG_COUNT_ATTRIB_MATCHES_NO_ANCESTOR", "Aviso: o atributo count não corresponde a um predecessor em xsl:number! Destino = {0}" }, { "WG_EXPR_ATTRIB_CHANGED_TO_SELECT", "Sintaxe antiga: O nome do atributo 'expr' foi alterado para 'select'." }, { "WG_NO_LOCALE_IN_FORMATNUMBER", "Xalan ainda não trata do nome de locale na função format-number." }, { "WG_LOCALE_NOT_FOUND", "Aviso: Não foi possível localizar o locale para xml:lang={0}" }, { "WG_CANNOT_MAKE_URL_FROM", "Impossível criar URL a partir de: {0}" }, { "WG_CANNOT_LOAD_REQUESTED_DOC", "Impossível carregar doc solicitado: {0}" }, { "WG_CANNOT_FIND_COLLATOR", "Impossível localizar Intercalador para <sort xml:lang={0}" }, { "WG_FUNCTIONS_SHOULD_USE_URL", "Sintaxe antiga: a instrução functions deve utilizar uma url de {0}" }, { "WG_ENCODING_NOT_SUPPORTED_USING_UTF8", "codificação não suportada: {0}, utilizando UTF-8" }, { "WG_ENCODING_NOT_SUPPORTED_USING_JAVA", "codificação não suportada: {0}, utilizando Java {1}" }, { "WG_SPECIFICITY_CONFLICTS", "Encontrados conflitos de especificação: O último {0} encontrado na página de estilo será utilizado." }, { "WG_PARSING_AND_PREPARING", "========= Análise e preparação {0} ==========" }, { "WG_ATTR_TEMPLATE", "Template de Atr, {0}" }, { "WG_CONFLICT_BETWEEN_XSLSTRIPSPACE_AND_XSLPRESERVESP", "Conflito de correspondência entre xsl:strip-space e xsl:preserve-space" }, { "WG_ATTRIB_NOT_HANDLED", "Xalan ainda não trata do atributo {0}!" }, { "WG_NO_DECIMALFORMAT_DECLARATION", "Nenhuma declaração encontrada para formato decimal: {0}" }, { "WG_OLD_XSLT_NS", "Espaço de nomes XSLT ausente ou incorreto." }, { "WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", "Apenas uma declaração padrão xsl:decimal-format é permitida." }, { "WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", "Os nomes de xsl:decimal-format devem ser exclusivos. O nome \"{0}\" foi duplicado." }, { "WG_ILLEGAL_ATTRIBUTE", "{0} possui um atributo inválido: {1}" }, { "WG_COULD_NOT_RESOLVE_PREFIX", "Não foi possível resolver prefixo do espaço de nomes: {0}. O nó será ignorado." }, { "WG_STYLESHEET_REQUIRES_VERSION_ATTRIB", "xsl:stylesheet requer um atributo 'version'!" }, { "WG_ILLEGAL_ATTRIBUTE_NAME", "Nome de atributo inválido: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_VALUE", "Valor inválido utilizado para o atributo {0}: {1}" }, { "WG_EMPTY_SECOND_ARG", "O nodeset resultante do segundo argumento da função document está vazio. Retornar um node-set vazio." }, { "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", "O valor do atributo 'name' do nome xsl:processing-instruction não deve ser 'xml'" }, { "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", "O valor do atributo ''name'' de xsl:processing-instruction deve ser um NCName válido: {0}" }, { "WG_ILLEGAL_ATTRIBUTE_POSITION", "Impossível incluir atributo {0} depois de nós filhos ou antes da geração de um elemento. O atributo será ignorado." }, { "NO_MODIFICATION_ALLOWED_ERR", "Foi feita uma tentativa de modificar um objeto no qual não são permitidas modificações. " }, { "ui_language", "pt" }, { "help_language", "pt" }, { "language", "pt" }, { "BAD_CODE", "O parâmetro para createMessage estava fora dos limites" }, { "FORMAT_FAILED", "Exceção emitida durante chamada messageFormat" }, { "version", ">>>>>>> Versão Xalan" }, { "version2", "<<<<<<<" }, { "yes", "sim" }, { "line", "Linha n°" }, { "column", "Coluna n°" }, { "xsldone", "XSLProcessor: concluído" }, { "xslProc_option", "Opções da classe Process da linha de comando de Xalan-J:" }, { "xslProc_option", "Opções da classe Process da linha de comandos de Xalan-J:" }, { "xslProc_invalid_xsltc_option", "A opção {0} não é suportada no modo XSLTC." }, { "xslProc_invalid_xalan_option", "A opção {0} somente pode ser utilizada com -XSLTC." }, { "xslProc_no_input", "Erro: Nenhuma página de estilo ou xml de entrada foi especificado. Execute este comando sem nenhuma opção para instruções de uso." }, { "xslProc_common_options", "-Opções Comuns-" }, { "xslProc_xalan_options", "-Opções para Xalan-" }, { "xslProc_xsltc_options", "-Opções para XSLTC-" }, { "xslProc_return_to_continue", "(pressione <return> para continuar)" }, { "optionXSLTC", "   [-XSLTC (utilizar XSLTC para transformação)]" }, { "optionIN", "   [-IN inputXMLURL]" }, { "optionXSL", "   [-XSL XSLTransformationURL]" }, { "optionOUT", "   [-OUT outputFileName]" }, { "optionLXCIN", "   [-LXCIN compiledStylesheetFileNameIn]" }, { "optionLXCOUT", "   [-LXCOUT compiledStylesheetFileNameOutOut]" }, { "optionPARSER", "   [-PARSER nome completo da classe do analisador liaison]" }, { "optionE", "   [-E (Não expandir refs de entidade)]" }, { "optionV", "   [-E (Não expandir refs de entidade)]" }, { "optionQC", "   [-QC (Avisos de Conflitos de Padrão Silencioso)]" }, { "optionQ", "   [-Q  (Modo Silencioso)]" }, { "optionLF", "   [-LF (Utilizar avanços de linha apenas na saída {padrão é CR/LF})]" }, { "optionCR", "   [-CR (Utilizar retornos de carro apenas na saída {padrão é CR/LF})]" }, { "optionESCAPE", "   [-ESCAPE (Quais caracteres de escape {padrão é <>&\"'\\r\\n}]" }, { "optionINDENT", "   [-INDENT (Controlar como os espaços são recuados {padrão é 0})]" }, { "optionTT", "   [-TT (Rastrear os templates enquanto estão sendo chamados.)]" }, { "optionTG", "   [-TG (Rastrear cada evento de geração.)]" }, { "optionTS", "   [-TS (Rastrear cada evento de seleção.)]" }, { "optionTTC", "   [-TTC (Rastrear os filhos do modelo enquanto estão sendo processados.)]" }, { "optionTCLASS", "   [-TCLASS (Classe TraceListener para extensões de rastreio.)]" }, { "optionVALIDATE", "   [-VALIDATE (Definir se ocorrer validação. A validação fica desativada por padrão.)]" }, { "optionEDUMP", "   [-EDUMP {nome de arquivo opcional} (Executar stackdump sob erro.)]" }, { "optionXML", "   [-XML (Utilizar formatador XML e incluir cabeçalho XML.)]" }, { "optionTEXT", "   [-TEXT (Utilizar formatador de Texto simples.)]" }, { "optionHTML", "   [-HTML (Utilizar formatador HTML.)]" }, { "optionPARAM", "   [-PARAM expressão de nome (Definir um parâmetro stylesheet)]" }, { "noParsermsg1", "O Processo XSL não obteve êxito." }, { "noParsermsg2", "** Não foi possível encontrar o analisador **" }, { "noParsermsg3", "Verifique seu classpath." }, { "noParsermsg4", "Se você não tiver o XML Parser para Java da IBM, poderá fazer o download dele a partir de" }, { "noParsermsg5", "IBM's AlphaWorks: http://www.alphaworks.ibm.com/formula/xml" }, { "optionURIRESOLVER", "   [-URIRESOLVER nome completo da classe (URIResolver a ser utilizado para resolver URIs)]" }, { "optionENTITYRESOLVER", "   [-ENTITYRESOLVER nome completo da classe (EntityResolver a ser utilizado para resolver entidades)]" }, { "optionCONTENTHANDLER", "   [-CONTENTHANDLER nome completo da classe (ContentHandler a ser utilizado para serializar saída)]" }, { "optionLINENUMBERS", "   [-L utilizar números de linha para documento de origem]" }, { "optionSECUREPROCESSING", "   [-SECURE (define o recurso de processamento seguro como true.)]" }, { "optionMEDIA", "   [-MEDIA mediaType (utilizar atributo de mídia para encontrar folha de estilo associada a um documento.)]" }, { "optionFLAVOR", "   [-FLAVOR flavorName (Utilizar explicitamente s2s=SAX ou d2d=DOM para executar transformação.)]" }, { "optionDIAG", "   [-DIAG (Imprimir total de milissegundos que a transformação gastou.)]" }, { "optionINCREMENTAL", "   [-INCREMENTAL (pedir construção incremental de DTM definindo http://xml.apache.org/xalan/features/incremental verdadeiro.)]" }, { "optionNOOPTIMIMIZE", "   [-NOOPTIMIMIZE (não solicitar o processamento de otimização de folha de estilo definindo http://xml.apache.org/xalan/features/optimize false.)]" }, { "optionRL", "   [-RL recursionlimit (declarar limite numérico em profundidade de recorrência de folha de estilo.)]" }, { "optionXO", "   [-XO [transletName] (atribuir nome ao translet gerado)]" }, { "optionXD", "   [-XD destinationDirectory (especificar um diretório de destino para translet)]" }, { "optionXJ", "   [-XJ jarfile (empacota classes translet em um arquivo jar denominado <arquivo_jar>)]" }, { "optionXP", "   [-XP package (especifica um prefixo de nome de pacote para todas as classes translet geradas)]" }, { "optionXN", "   [-XN (ativa a seqüência de templates)]" }, { "optionXX", "   [-XX (ativa a saída de mensagem de depuração adicional)]" }, { "optionXT", "   [-XT (utilizar translet para transformação, se possível)]" }, { "diagTiming", " --------- Transformação de {0} via {1} levou {2} ms" }, { "recursionTooDeep", "Aninhamento de templates muito extenso. aninhamento = {0}, template {1} {2}" }, { "nameIs", "o nome é" }, { "matchPatternIs", "o padrão de correspondência é" } };
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
/*  291:1495 */         return (XSLTErrorResources)ResourceBundle.getBundle(className, new Locale("pt", "BR"));
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
 * Qualified Name:     org.apache.xalan.res.XSLTErrorResources_pt_BR
 * JD-Core Version:    0.7.0.1
 */