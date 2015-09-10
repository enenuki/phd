/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_ko
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "''{0}''에 런타임 내부 오류가 발생했습니다." }, { "RUN_TIME_COPY_ERR", "<xsl:copy> 실행시 런타임 오류가 발생했습니다." }, { "DATA_CONVERSION_ERR", "''{0}''에서 ''{1}''(으)로의 변환은 유효하지 않습니다." }, { "EXTERNAL_FUNC_ERR", "XSLTC에서 ''{0}'' 외부 함수를 지원하지 않습니다." }, { "EQUALITY_EXPR_ERR", "등식에 알 수 없는 인수 유형이 있습니다." }, { "INVALID_ARGUMENT_ERR", "''{1}''에 대한 호출에서 ''{0}'' 인수 유형이 유효하지 않습니다." }, { "FORMAT_NUMBER_ERR", "''{1}'' 패턴을 사용하여 숫자 ''{0}''을(를) 포맷하려고 시도했습니다." }, { "ITERATOR_CLONE_ERR", "''{0}'' 반복기를 복제할 수 없습니다." }, { "AXIS_SUPPORT_ERR", "''{0}'' 축에 대한 반복기가 지원되지 않습니다." }, { "TYPED_AXIS_SUPPORT_ERR", "유형이 지정된 축 ''{0}''에 대해 반복기가 지원되지 않습니다." }, { "STRAY_ATTRIBUTE_ERR", "''{0}'' 속성이 요소의 외부에 있습니다." }, { "STRAY_NAMESPACE_ERR", "''{0}''=''{1}'' 이름 공간 선언이 요소의 외부에 있습니다." }, { "NAMESPACE_PREFIX_ERR", "''{0}'' 접두부에 대한 이름 공간이 선언되지 않았습니다." }, { "DOM_ADAPTER_INIT_ERR", "소스 DOM의 올바르지 않은 유형을 사용하여 DOMAdapter가 작성되었습니다." }, { "PARSER_DTD_SUPPORT_ERR", "사용 중인 SAX 구문 분석기가 DTD 선언 이벤트를 처리하지 않습니다." }, { "NAMESPACES_SUPPORT_ERR", "사용 중인 SAX 구문 분석기가 XML 이름 공간을 지원하지 않습니다." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "''{0}'' URI 참조를 분석할 수 없습니다." }, { "UNSUPPORTED_XSL_ERR", "''{0}'' XSL 요소가 지원되지 않습니다." }, { "UNSUPPORTED_EXT_ERR", "''{0}'' XSLTC 확장자를 인식할 수 없습니다." }, { "UNKNOWN_TRANSLET_VERSION_ERR", "사용 중인 XSLTC 런타임 버전보다 더 최신의 XSLTC 버전을 사용하여 지정된 ''{0}'' Translet을 작성했습니다. 이 Translet을 실행하려면 스타일시트를 다시 컴파일하거나 더 최신의 XSLTC 버전을 사용해야 합니다. " }, { "INVALID_QNAME_ERR", "값이 QName이어야 하는 속성에 ''{0}'' 값이 있습니다." }, { "INVALID_NCNAME_ERR", "값이 NCName이어야 하는 속성에 ''{0}'' 값이 있습니다." }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "보안 처리 기능이 true로 설정된 경우에는 ''{0}'' 확장 함수를 사용할 수 없습니다." }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "보안 처리 기능이 true로 설정된 경우에는 ''{0}'' 확장 요소를 사용할 수 없습니다." } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_ko
 * JD-Core Version:    0.7.0.1
 */