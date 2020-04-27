<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <html>
  <body>
  <h2>Catalog</h2>
  <table>
    <xsl:for-each select="/catalog/book">
    <tr>
      <td><xsl:value-of select="title"/></td>
      <td><xsl:if test="available">available</xsl:if></td>
    </tr>
    </xsl:for-each>
  </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>