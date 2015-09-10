/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_ja
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "''{0}'' でランタイム内部エラーがありました。" }, { "RUN_TIME_COPY_ERR", "<xsl:copy> 実行時のランタイム・エラー" }, { "DATA_CONVERSION_ERR", "''{0}'' から ''{1}'' への変換は無効です。" }, { "EXTERNAL_FUNC_ERR", "外部関数 ''{0}'' は XSLTC によってサポートされていません。" }, { "EQUALITY_EXPR_ERR", "等式内の引数が不明です。" }, { "INVALID_ARGUMENT_ERR", "''{1}'' への呼び出しの引数の型 ''{0}'' が無効です。" }, { "FORMAT_NUMBER_ERR", "パターン ''{1}'' を使用して数値 ''{0}'' のフォーマット設定を試みています。" }, { "ITERATOR_CLONE_ERR", "イテレーター ''{0}'' を複製できません。" }, { "AXIS_SUPPORT_ERR", "軸 ''{0}'' のイテレーターはサポートされていません。" }, { "TYPED_AXIS_SUPPORT_ERR", "型付きの軸 ''{0}'' のイテレーターはサポートされていません。" }, { "STRAY_ATTRIBUTE_ERR", "属性 ''{0}'' が要素の外側です。" }, { "STRAY_NAMESPACE_ERR", "名前空間宣言 ''{0}''=''{1}'' が要素の外側です。" }, { "NAMESPACE_PREFIX_ERR", "接頭部 ''{0}'' の名前空間が宣言されていません。" }, { "DOM_ADAPTER_INIT_ERR", "DOMAdapter が間違った型のソース DOM を使用して作成されました。" }, { "PARSER_DTD_SUPPORT_ERR", "使用中の SAX パーサーは DTD 宣言イベントを処理しません。" }, { "NAMESPACES_SUPPORT_ERR", "使用中の SAX パーサーには XML 名前空間のサポートがありません。" }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "URI 参照 ''{0}'' を解決できませんでした。" }, { "UNSUPPORTED_XSL_ERR", "XSL 要素 ''{0}'' はサポートされていません。" }, { "UNSUPPORTED_EXT_ERR", "XSLTC 拡張 ''{0}'' は認識されていません。" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "指定された translet ''{0}'' は、使用中の XSLTC ランタイムより新しいバージョンの XSLTC を使用して作成されました。この translet を実行するには、スタイルシートを再コンパイルするか、または新しいバージョンの XSLTC を使用しなければなりません。" }, { "INVALID_QNAME_ERR", "値が QName でなければならない属性に、値 ''{0}'' がありました。" }, { "INVALID_NCNAME_ERR", "値が NCName でなければならない属性に、値 ''{0}'' がありました。" }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "セキュリティー保護された処理機能が true に設定されているときに、拡張関数 ''{0}'' を使用することはできません。" }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "セキュリティー保護された処理機能が true に設定されているときに、拡張要素 ''{0}'' を使用することはできません。" } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_ja
 * JD-Core Version:    0.7.0.1
 */