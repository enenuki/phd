/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_ru
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "Внутренняя ошибка выполнения в ''{0}''" }, { "RUN_TIME_COPY_ERR", "Ошибка времени выполнения при обработке <xsl:copy>." }, { "DATA_CONVERSION_ERR", "Недопустимое преобразование из ''{0}'' в ''{1}''. " }, { "EXTERNAL_FUNC_ERR", "Внешняя функция ''{0}'' не поддерживается XSLTC. " }, { "EQUALITY_EXPR_ERR", "Неизвестный тип аргумента в выражении равенства." }, { "INVALID_ARGUMENT_ERR", "Недопустимый тип аргумента ''{0}'' при вызове ''{1}''" }, { "FORMAT_NUMBER_ERR", "Попытка отформатировать число ''{0}'' с помощью шаблона ''{1}''. " }, { "ITERATOR_CLONE_ERR", "Невозможно создать дубликат счетчика ''{0}''. " }, { "AXIS_SUPPORT_ERR", "Счетчик для оси ''{0}'' не поддерживается. " }, { "TYPED_AXIS_SUPPORT_ERR", "Счетчик для указанной оси ''{0}'' не поддерживается. " }, { "STRAY_ATTRIBUTE_ERR", "Атрибут ''{0}'' вне элемента. " }, { "STRAY_NAMESPACE_ERR", "Объявление пространства имен ''{0}''=''{1}'' вне элемента. " }, { "NAMESPACE_PREFIX_ERR", "Пространство имен для префикса ''{0}'' не объявлено. " }, { "DOM_ADAPTER_INIT_ERR", "DOMAdapter создан с неправильным типом исходного DOM." }, { "PARSER_DTD_SUPPORT_ERR", "Применяемый анализатор SAX не обрабатывает события объявления DTD." }, { "NAMESPACES_SUPPORT_ERR", "Применяемый анализатор SAX не поддерживает пространства имен XML." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "Невозможно обработать ссылку на URI ''{0}''. " }, { "UNSUPPORTED_XSL_ERR", "Неподдерживаемый элемент XSL ''{0}'' " }, { "UNSUPPORTED_EXT_ERR", "Неизвестное расширение XSLTC ''{0}''" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "Указанная процедура преобразования ''{0}'' была создана с помощью более новой версии XSLTC, чем используемая для выполнения версия XSLTC. Для выполнения этого преобразования следует перекомпилировать таблицу стилей или установить более позднюю версию XSLTC. " }, { "INVALID_QNAME_ERR", "В атрибуте, для которого допустимо значение QName, указано значение ''{0}''" }, { "INVALID_NCNAME_ERR", "В атрибуте, для которого допустимо значение NCName, указано значение ''{0}''" }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "Применение функции расширения ''{0}'' недопустимо, если для функции защищенной обработки задано значение true. " }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "Применение элемента расширения ''{0}'' недопустимо, если для функции защищенной обработки задано значение true. " } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_ru
 * JD-Core Version:    0.7.0.1
 */