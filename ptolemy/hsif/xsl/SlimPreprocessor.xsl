<?xml version="1.0"?>
<!-- 	This file strips the redundant records generated by the preprocessors.

	Due to the algorithm in global varialbes and local variables preprocessors,
	there are quite a few redundant I/O ports produced.

	The algorithm works this way:
	It iterates the variables to be localized, checks its role in invariants,
	expressions .... Everytime, its role is recognized, one port is generated.
	It is common the variable exists in several places and that is why several
	duplicate I/O ports are generated.

	This file strips redundant port and parameters away.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xalan="http://xml.apache.org/xslt" version="1.0">

    <xsl:output doctype-system="HSIF.dtd"/>
    <xsl:output method="xml" indent="yes"/>

    <!-- features of the XSLT 2.0 language -->
    <xsl:decimal-format name="comma" decimal-separator="," grouping-separator="."/>

    <!-- include necessary attributes here -->

    <!-- time function -->
    <xsl:variable name="now" xmlns:Date="/java.util.Date">
        <xsl:value-of select="Date:toString(Date:new())"/>
    </xsl:variable>

    <!-- configuration -->
    <xsl:param name="author">Ptolemy II</xsl:param>
    <xsl:preserve-space elements="*"/>

    <!-- ========================================================== -->
    <!-- root element -->
    <!-- ========================================================== -->

    <xsl:template match="/">
        <xsl:comment><xsl:value-of select="$author"/> Generated at <xsl:value-of select="$now"/></xsl:comment>
        <xsl:apply-templates/>
    </xsl:template>

    <!-- ========================================================== -->
    <!-- DNHA element -->
    <!-- ========================================================== -->

    <xsl:template match="DNHA">

        <xsl:copy>
            <xsl:for-each select="@*">
                <xsl:attribute name="{name()}"><xsl:value-of select="."/></xsl:attribute>
            </xsl:for-each>

            <xsl:apply-templates select="*"/>

        </xsl:copy>
    </xsl:template>

    <!-- General Copy -->
    <xsl:template match="*">
        <xsl:copy>
            <xsl:for-each select="@*">
                <xsl:attribute name="{name()}"><xsl:value-of select="."/></xsl:attribute>
            </xsl:for-each>
            <xsl:apply-templates select="*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="IntegerVariable|RealVariable|BooleanVariable">
        <xsl:variable name="name" select="@name"/>
        <xsl:if test="not(preceding-sibling::IntegerVariable[@name=$name]|preceding-sibling::RealVariable[@name=$name]|preceding-sibling::BooleanVariable[@name=$name])">
            <xsl:copy>
                <xsl:for-each select="@*">
                    <xsl:attribute name="{name()}"><xsl:value-of select="."/></xsl:attribute>
                </xsl:for-each>
                <xsl:apply-templates select="*"/>
            </xsl:copy>
        </xsl:if>
    </xsl:template>

    <xsl:template match="IntegerParameter|RealParameter|BooleanParameter">
        <xsl:variable name="name" select="@name"/>
        <xsl:if test="not(preceding-sibling::IntegerParameter[@name=$name]|preceding-sibling::RealParameter[@name=$name]|preceding-sibling::BooleanParameter[@name=$name])">
            <xsl:copy>
                <xsl:for-each select="@*">
                    <xsl:attribute name="{name()}"><xsl:value-of select="."/></xsl:attribute>
                </xsl:for-each>
                <xsl:apply-templates select="*"/>
            </xsl:copy>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
