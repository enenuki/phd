package org.apache.xml.serialize;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import org.apache.xerces.util.EncodingMap;

/**
 * @deprecated
 */
public class EncodingInfo
{
  private Object[] fArgsForMethod = null;
  String ianaName;
  String javaName;
  int lastPrintable;
  Object fCharsetEncoder = null;
  Object fCharToByteConverter = null;
  boolean fHaveTriedCToB = false;
  boolean fHaveTriedCharsetEncoder = false;
  
  public EncodingInfo(String paramString1, String paramString2, int paramInt)
  {
    this.ianaName = paramString1;
    this.javaName = EncodingMap.getIANA2JavaMapping(paramString1);
    this.lastPrintable = paramInt;
  }
  
  public String getIANAName()
  {
    return this.ianaName;
  }
  
  public Writer getWriter(OutputStream paramOutputStream)
    throws UnsupportedEncodingException
  {
    if (this.javaName != null) {
      return new OutputStreamWriter(paramOutputStream, this.javaName);
    }
    this.javaName = EncodingMap.getIANA2JavaMapping(this.ianaName);
    if (this.javaName == null) {
      return new OutputStreamWriter(paramOutputStream, "UTF8");
    }
    return new OutputStreamWriter(paramOutputStream, this.javaName);
  }
  
  public boolean isPrintable(char paramChar)
  {
    if (paramChar <= this.lastPrintable) {
      return true;
    }
    return isPrintable0(paramChar);
  }
  
  private boolean isPrintable0(char paramChar)
  {
    if ((this.fCharsetEncoder == null) && (CharsetMethods.fgNIOCharsetAvailable) && (!this.fHaveTriedCharsetEncoder))
    {
      if (this.fArgsForMethod == null) {
        this.fArgsForMethod = new Object[1];
      }
      try
      {
        this.fArgsForMethod[0] = this.javaName;
        Object localObject = CharsetMethods.fgCharsetForNameMethod.invoke(null, this.fArgsForMethod);
        if (((Boolean)CharsetMethods.fgCharsetCanEncodeMethod.invoke(localObject, (Object[])null)).booleanValue()) {
          this.fCharsetEncoder = CharsetMethods.fgCharsetNewEncoderMethod.invoke(localObject, (Object[])null);
        } else {
          this.fHaveTriedCharsetEncoder = true;
        }
      }
      catch (Exception localException1)
      {
        this.fHaveTriedCharsetEncoder = true;
      }
    }
    if (this.fCharsetEncoder != null) {
      try
      {
        this.fArgsForMethod[0] = new Character(paramChar);
        return ((Boolean)CharsetMethods.fgCharsetEncoderCanEncodeMethod.invoke(this.fCharsetEncoder, this.fArgsForMethod)).booleanValue();
      }
      catch (Exception localException2)
      {
        this.fCharsetEncoder = null;
        this.fHaveTriedCharsetEncoder = false;
      }
    }
    if (this.fCharToByteConverter == null)
    {
      if ((this.fHaveTriedCToB) || (!CharToByteConverterMethods.fgConvertersAvailable)) {
        return false;
      }
      if (this.fArgsForMethod == null) {
        this.fArgsForMethod = new Object[1];
      }
      try
      {
        this.fArgsForMethod[0] = this.javaName;
        this.fCharToByteConverter = CharToByteConverterMethods.fgGetConverterMethod.invoke(null, this.fArgsForMethod);
      }
      catch (Exception localException3)
      {
        this.fHaveTriedCToB = true;
        return false;
      }
    }
    try
    {
      this.fArgsForMethod[0] = new Character(paramChar);
      return ((Boolean)CharToByteConverterMethods.fgCanConvertMethod.invoke(this.fCharToByteConverter, this.fArgsForMethod)).booleanValue();
    }
    catch (Exception localException4)
    {
      this.fCharToByteConverter = null;
      this.fHaveTriedCToB = false;
    }
    return false;
  }
  
  public static void testJavaEncodingName(String paramString)
    throws UnsupportedEncodingException
  {
    byte[] arrayOfByte = { 118, 97, 108, 105, 100 };
    String str = new String(arrayOfByte, paramString);
  }
  
  static class CharToByteConverterMethods
  {
    private static Method fgGetConverterMethod = null;
    private static Method fgCanConvertMethod = null;
    private static boolean fgConvertersAvailable = false;
    
    static
    {
      try
      {
        Class localClass = Class.forName("sun.io.CharToByteConverter");
        fgGetConverterMethod = localClass.getMethod("getConverter", new Class[] { String.class });
        fgCanConvertMethod = localClass.getMethod("canConvert", new Class[] { Character.TYPE });
        fgConvertersAvailable = true;
      }
      catch (Exception localException)
      {
        fgGetConverterMethod = null;
        fgCanConvertMethod = null;
        fgConvertersAvailable = false;
      }
    }
  }
  
  static class CharsetMethods
  {
    private static Method fgCharsetForNameMethod = null;
    private static Method fgCharsetCanEncodeMethod = null;
    private static Method fgCharsetNewEncoderMethod = null;
    private static Method fgCharsetEncoderCanEncodeMethod = null;
    private static boolean fgNIOCharsetAvailable = false;
    
    static
    {
      try
      {
        Class localClass1 = Class.forName("java.nio.charset.Charset");
        Class localClass2 = Class.forName("java.nio.charset.CharsetEncoder");
        fgCharsetForNameMethod = localClass1.getMethod("forName", new Class[] { String.class });
        fgCharsetCanEncodeMethod = localClass1.getMethod("canEncode", new Class[0]);
        fgCharsetNewEncoderMethod = localClass1.getMethod("newEncoder", new Class[0]);
        fgCharsetEncoderCanEncodeMethod = localClass2.getMethod("canEncode", new Class[] { Character.TYPE });
        fgNIOCharsetAvailable = true;
      }
      catch (Exception localException)
      {
        fgCharsetForNameMethod = null;
        fgCharsetCanEncodeMethod = null;
        fgCharsetEncoderCanEncodeMethod = null;
        fgCharsetNewEncoderMethod = null;
        fgNIOCharsetAvailable = false;
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serialize.EncodingInfo
 * JD-Core Version:    0.7.0.1
 */