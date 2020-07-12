package com.example.cn.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @ClassesName LicenseService
 * @Author ShilinMao
 * @DATE 2019/10/28
 * @Desc
 * @Version 1.0
 **/
@Service
@Slf4j
public class LicenseService {

    public InputStream getLicense() {
        InputStream license = null;
        try {
            // 凭证
            String licenseStr =
                    "<License>\n" +
                            "  <Data>\n" +
                            "    <LicensedTo>Hand Enterprise Solutions Co.,Ltd.</LicensedTo>\n" +
                            "    <EmailTo>mingyue.yuan@hand-china.com</EmailTo>\n" +
                            "    <LicenseType>Developer OEM</LicenseType>\n" +
                            "    <LicenseNote>Limited to 1 developer, unlimited physical locations</LicenseNote>\n" +
                            "    <OrderID>200114215634</OrderID>\n" +
                            "    <UserID>135033960</UserID>\n" +
                            "    <OEM>This is a redistributable license</OEM>\n" +
                            "    <Products>\n" +
                            "      <Product>Aspose.Total for Java</Product>\n" +
                            "    </Products>\n" +
                            "    <EditionType>Enterprise</EditionType>\n" +
                            "    <SerialNumber>899048c8-7c82-4c2e-bb03-6f7800f2a3dd</SerialNumber>\n" +
                            "    <SubscriptionExpiry>20210114</SubscriptionExpiry>\n" +
                            "    <LicenseVersion>3.0</LicenseVersion>\n" +
                            "    <LicenseInstructions>https://purchase.aspose.com/policies/use-license</LicenseInstructions>\n" +
                            "  </Data>\n" +
                            "  <Signature>Sog8VKd2vbiGSsPXJPWoUH3ZvxBpcgN/0chEXFhiLZrrhLebE92XodNIPiZKRmfQUqO2b/DpIijya4g1b+zqPPJkCymHLx4kOs98qgK4FnUysztrH9mWEgKNMmbAufkJXOZLYeZHr8iL92tw4SW/zAS5CL0U4a1HVni3oCY9Bvw=</Signature>\n" +
                            "</License>";
            license = new ByteArrayInputStream(licenseStr.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("鉴权发生错误: [{}]",e);
        }
        return license;
    }
}
