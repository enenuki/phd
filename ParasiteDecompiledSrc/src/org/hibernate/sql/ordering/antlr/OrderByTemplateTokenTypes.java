package org.hibernate.sql.ordering.antlr;

public abstract interface OrderByTemplateTokenTypes
{
  public static final int EOF = 1;
  public static final int NULL_TREE_LOOKAHEAD = 3;
  public static final int ORDER_BY = 4;
  public static final int SORT_SPEC = 5;
  public static final int ORDER_SPEC = 6;
  public static final int SORT_KEY = 7;
  public static final int EXPR_LIST = 8;
  public static final int DOT = 9;
  public static final int IDENT_LIST = 10;
  public static final int COLUMN_REF = 11;
  public static final int COLLATE = 12;
  public static final int ASCENDING = 13;
  public static final int DESCENDING = 14;
  public static final int COMMA = 15;
  public static final int HARD_QUOTE = 16;
  public static final int IDENT = 17;
  public static final int OPEN_PAREN = 18;
  public static final int CLOSE_PAREN = 19;
  public static final int NUM_DOUBLE = 20;
  public static final int NUM_FLOAT = 21;
  public static final int NUM_INT = 22;
  public static final int NUM_LONG = 23;
  public static final int QUOTED_STRING = 24;
  public static final int LITERAL_ascending = 25;
  public static final int LITERAL_descending = 26;
  public static final int ID_START_LETTER = 27;
  public static final int ID_LETTER = 28;
  public static final int ESCqs = 29;
  public static final int HEX_DIGIT = 30;
  public static final int EXPONENT = 31;
  public static final int FLOAT_SUFFIX = 32;
  public static final int WS = 33;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.OrderByTemplateTokenTypes
 * JD-Core Version:    0.7.0.1
 */