<?xml version="1.0" encoding="UTF-16"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt"
                xmlns:var="http://schemas.microsoft.com/BizTalk/2003/var"
                xmlns:ns0="urn:iso:std:iso:20022:tech:xsd:head.001.001.01"
                xmlns:s0="http://biztalk.abanca.com/ConectorWS/services/SN484101B"
                xmlns:userCSharp="http://schemas.microsoft.com/BizTalk/2003/userCSharp"
                exclude-result-prefixes="msxsl var s0 userCSharp"
                version="1.0">
    <xsl:output omit-xml-declaration="yes" method="xml" version="1.0"/>
    <xsl:template match="/">
        <xsl:apply-templates select="/s0:Root"/>
    </xsl:template>
    <xsl:template match="/s0:Root">
        <ns0:AppHdr>
            <ns0:Fr>
                <ns0:FIId>
                    <xsl:for-each select="s0:SWIFN101">
                        <ns0:FinInstnId>
                            <xsl:if test="s0:SWIFN101010">
                                <ns0:BICFI>
                                    <xsl:value-of select="s0:SWIFN101010/text()"/>
                                </ns0:BICFI>
                            </xsl:if>
                        </ns0:FinInstnId>
                    </xsl:for-each>
                </ns0:FIId>
            </ns0:Fr>
            <ns0:To>
                <ns0:FIId>
                    <xsl:for-each select="s0:SWIFN101">
                        <ns0:FinInstnId>
                            <xsl:if test="s0:SWIFN101020">
                                <ns0:BICFI>
                                    <xsl:value-of select="s0:SWIFN101020/text()"/>
                                </ns0:BICFI>
                            </xsl:if>
                        </ns0:FinInstnId>
                    </xsl:for-each>
                </ns0:FIId>
            </ns0:To>
            <xsl:if test="s0:SWIFN101/s0:SWIFN101030">
                <ns0:BizMsgIdr>
                    <xsl:value-of select="s0:SWIFN101/s0:SWIFN101030/text()"/>
                </ns0:BizMsgIdr>
            </xsl:if>
            <xsl:if test="s0:SWIFN101/s0:SWIFN101040">
                <ns0:MsgDefIdr>
                    <xsl:value-of select="s0:SWIFN101/s0:SWIFN101040/text()"/>
                </ns0:MsgDefIdr>
            </xsl:if>
        </ns0:AppHdr>
    </xsl:template>
</xsl:stylesheet>