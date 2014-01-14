<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited with XML Spy v2007 (http://www.altova.com) -->
<?altova_samplexml file:///E:/xml_workspace/errors.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<html>
			<body>
				<h2 align="center"></h2>

				<table border="1"> 
					<tr align="center">
						<th colspan="4">
							<h2 align="center" style="margin-top:10px">MAT Automation Test Result</h2> 
						</th>
					</tr>
					<xsl:for-each select="com.innover.domain.TableInfoBean">
						<tr>
							<th width="100px" rowspan="7">Summary:</th>
							<th width="200px">Status</th>
							<th>Number</th>
						</tr>
						<tr>
							<td>Total # of Test Cases</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="totalCases"/></td>
						</tr>
						<tr>
							<td># of Passed Test Cases</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="passCases"/></td>
						</tr>
						<tr>
							<td># of Failed Test Cases</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="failedCases"/></td>
						</tr>
						<tr>
							<td># of Abort Test Cases</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="abortedNumber"/></td>
						</tr>
						<tr>
							<td># Passed With Warnings</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="passedWithWarns"/></td>
						</tr>
						
						<tr>
							<td># Passed With Debug</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="passedWithDebug"/></td>
						</tr>
						<!--					
						<tr>
							<td># Passed With Audit</td>
							<td align="center" style="font-weight:bold"><xsl:value-of select="passedWithAudit"/></td>
						</tr>
						-->
						<tr>
							<th rowspan="4">Failure Types:</th>
							<td># of Errors</td>
							<td align="center" style="color:red;font-weight:bold"><xsl:value-of select="errorNumber"/></td>
						</tr>
						<tr> 
							<td># of Warnings</td>
							<td align="center" style="color:orange;font-weight:bold"><xsl:value-of select="wraningNumber"/></td>
						</tr>
						<tr> 
							<td># of Debug</td>
							<td align="center" style="color:green;font-weight:bold"><xsl:value-of select="debugInfNumber"/></td>
						</tr>
						<tr> 
							<td># of Fatals</td>
							<td align="center" style="color:black;font-weight:bold"><xsl:value-of select="fatalNumber"/></td>
						</tr>
						<!--
						<tr> 
							<td># of Audit</td>
							<td align="center" style="color:black;font-weight:bold"><xsl:value-of select="auditNumber"/></td>
						</tr>
						-->
					</xsl:for-each>
					
					    <xsl:variable name="_totalErrorMessage">
						<xsl:value-of select="1 + count(/com.innover.domain.TableInfoBean/details/com.innover.domain.TableDetailData/errorNumber) + count(/com.innover.domain.TableInfoBean/details/com.innover.domain.TableDetailData/errorMessages/com.innover.domain.EventBean)"/>
						</xsl:variable>
						
						<xsl:variable name="numMessage">
							<xsl:value-of select="position()"/>
						</xsl:variable>
						
						<tr>
							<th rowspan="{$_totalErrorMessage}">Error Details:</th>
							<th>Case # And Summary</th>
							<th>Message Count</th>
							<th>Message</th>
						</tr>
						
						<xsl:for-each select="/com.innover.domain.TableInfoBean/details/com.innover.domain.TableDetailData">
						<xsl:variable name="_errorNumber">
						<xsl:value-of select="errorNumber+1"/>
						</xsl:variable> 
						<xsl:variable name="indOfCase">
						<xsl:value-of select="position()"/>
						</xsl:variable> 
						
						<tr> 
							<td rowspan="{$_errorNumber}">No.<xsl:value-of select="testCase"/>: <xsl:value-of select="errorName"/></td>
							<td rowspan="{$_errorNumber}" align="center" style="font-weight:bold"><xsl:value-of select="errorNumber"/></td>
						</tr>
						
							<xsl:for-each select="errorMessages/com.innover.domain.EventBean">

							<xsl:if test="level ='ERROR'">
        					<tr> 
								<td style="color:red">No.<xsl:value-of select="position()"/>_<xsl:value-of select="Timestamp"/>_<xsl:value-of select="message"/></td> 
							</tr>
							</xsl:if>
							<xsl:if test="level ='DEBUG'">
        					<tr> 
								<td style="color:green">No.<xsl:value-of select="position()"/>_<xsl:value-of select="Timestamp"/>_<xsl:value-of select="message"/></td> 
							</tr>
							</xsl:if>
							<xsl:if test="level ='FATAL'">
        					<tr> 
								<td style="color:black">No.<xsl:value-of select="position()"/>_<xsl:value-of select="Timestamp"/>_<xsl:value-of select="message"/></td> 
							</tr>
							</xsl:if>
							<xsl:if test="level ='WARN'">
        					<tr> 
								<td style="color:orange">No.<xsl:value-of select="position()"/>_<xsl:value-of select="Timestamp"/>_<xsl:value-of select="message"/></td> 
							</tr>
							</xsl:if>
							<!--
							<xsl:if test="level ='AUDIT'">
        					<tr> 
								<td style="color:purple">No.<xsl:value-of select="position()"/>_<xsl:value-of select="Timestamp"/>_<xsl:value-of select="message"/></td> 
							</tr>
							</xsl:if>
							-->
							
							</xsl:for-each>  
						</xsl:for-each>
						
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
