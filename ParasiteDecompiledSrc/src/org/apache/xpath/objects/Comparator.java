package org.apache.xpath.objects;

import org.apache.xml.utils.XMLString;

abstract class Comparator
{
  abstract boolean compareStrings(XMLString paramXMLString1, XMLString paramXMLString2);
  
  abstract boolean compareNumbers(double paramDouble1, double paramDouble2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.Comparator
 * JD-Core Version:    0.7.0.1
 */