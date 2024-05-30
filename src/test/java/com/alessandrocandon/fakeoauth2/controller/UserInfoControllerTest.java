/* Decathlon Italy - Tacos Team(C) 2024 */
package com.alessandrocandon.fakeoauth2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureMockMvc
class UserInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testPostUserInfo() throws Exception {
    mockMvc
        .perform(
            post("/idp/userinfo.openid")
                .content("")
                .header(
                    HttpHeaders.AUTHORIZATION,
                    "Bearer"
                        + " eyJraWQiOiJNQUlOIiwicGkuYXRtIjoiNSIsImFsZyI6IlJTMjU2IiwidHlwIjoiSldUIn0.eyJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImF1dGhvcml6YXRpb25fZGV0YWlscyI6W10sImNsaWVudF9pZCI6Im15Y2lkIiwiaXNzIjoiZXhhbXBsZS5vcmciLCJqdGkiOiIxMjMiLCJzdWIiOiJhbGVzc2FuZHJvLWNhbmRvbiIsInVpZCI6ImFsZXNzYW5kcm8tY2FuZG9uIiwib3JpZ2luIjoiY29ycG9yYXRlIiwiaWF0IjoxNjkyMDI1MDU5LCJ1dWlkIjoieHh4eHh4eC00N2EzLTM0NGYtYmJiNC14eHh4eHgiLCJ1c2VyX2tleV9pZCI6MH0.rrK6SHgWzlQFAS4WAP9jnSfzmvWveCXYsiiRllyURkxqwC08Dxjf7oSGY8bZvSlCjOqMc7eCZZE9R5atQBy3RYcpKAWS4kCXatJCAvnxjEtYLH0RyzoGFQrHkhtX-_sH-Zu-7FPogjcBl5Z0jiBQ46HOHPJn8TYwNnona68xabnaeDzGwCz2Fpf34UBdzIkAdDLVFMA09pP80FAT8962u2OyyDPYpldLagIHGlfLG9NtX8NGBgpUAkPkC0r6Y8KOBdK4x2C3cyBORg9BrisfdamhCO1qRqzGWfO6nK1N6gAK4jLxEnL72oMyqM2nOUa-EsQ6skASk2G5phCpjEavUw"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetUserInfo() throws Exception {
    mockMvc
            .perform(
                    get("/idp/userinfo.openid")
                            .content("")
                            .header(
                                    HttpHeaders.AUTHORIZATION,
                                    "Bearer"
                                            + " eyJraWQiOiJNQUlOIiwicGkuYXRtIjoiNSIsImFsZyI6IlJTMjU2IiwidHlwIjoiSldUIn0.eyJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImF1dGhvcml6YXRpb25fZGV0YWlscyI6W10sImNsaWVudF9pZCI6Im15Y2lkIiwiaXNzIjoiZXhhbXBsZS5vcmciLCJqdGkiOiIxMjMiLCJzdWIiOiJhbGVzc2FuZHJvLWNhbmRvbiIsInVpZCI6ImFsZXNzYW5kcm8tY2FuZG9uIiwib3JpZ2luIjoiY29ycG9yYXRlIiwiaWF0IjoxNjkyMDI1MDU5LCJ1dWlkIjoieHh4eHh4eC00N2EzLTM0NGYtYmJiNC14eHh4eHgiLCJ1c2VyX2tleV9pZCI6MH0.rrK6SHgWzlQFAS4WAP9jnSfzmvWveCXYsiiRllyURkxqwC08Dxjf7oSGY8bZvSlCjOqMc7eCZZE9R5atQBy3RYcpKAWS4kCXatJCAvnxjEtYLH0RyzoGFQrHkhtX-_sH-Zu-7FPogjcBl5Z0jiBQ46HOHPJn8TYwNnona68xabnaeDzGwCz2Fpf34UBdzIkAdDLVFMA09pP80FAT8962u2OyyDPYpldLagIHGlfLG9NtX8NGBgpUAkPkC0r6Y8KOBdK4x2C3cyBORg9BrisfdamhCO1qRqzGWfO6nK1N6gAK4jLxEnL72oMyqM2nOUa-EsQ6skASk2G5phCpjEavUw"))
            .andExpect(status().isOk());
  }

  @Test
  void testDeleteUserInfoFail() throws Exception {
    mockMvc
            .perform(
                    delete("/idp/userinfo.openid")
                            .content("")
                            .header(
                                    HttpHeaders.AUTHORIZATION,
                                    "Bearer"
                                            + " eyJraWQiOiJNQUlOIiwicGkuYXRtIjoiNSIsImFsZyI6IlJTMjU2IiwidHlwIjoiSldUIn0.eyJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImF1dGhvcml6YXRpb25fZGV0YWlscyI6W10sImNsaWVudF9pZCI6Im15Y2lkIiwiaXNzIjoiZXhhbXBsZS5vcmciLCJqdGkiOiIxMjMiLCJzdWIiOiJhbGVzc2FuZHJvLWNhbmRvbiIsInVpZCI6ImFsZXNzYW5kcm8tY2FuZG9uIiwib3JpZ2luIjoiY29ycG9yYXRlIiwiaWF0IjoxNjkyMDI1MDU5LCJ1dWlkIjoieHh4eHh4eC00N2EzLTM0NGYtYmJiNC14eHh4eHgiLCJ1c2VyX2tleV9pZCI6MH0.rrK6SHgWzlQFAS4WAP9jnSfzmvWveCXYsiiRllyURkxqwC08Dxjf7oSGY8bZvSlCjOqMc7eCZZE9R5atQBy3RYcpKAWS4kCXatJCAvnxjEtYLH0RyzoGFQrHkhtX-_sH-Zu-7FPogjcBl5Z0jiBQ46HOHPJn8TYwNnona68xabnaeDzGwCz2Fpf34UBdzIkAdDLVFMA09pP80FAT8962u2OyyDPYpldLagIHGlfLG9NtX8NGBgpUAkPkC0r6Y8KOBdK4x2C3cyBORg9BrisfdamhCO1qRqzGWfO6nK1N6gAK4jLxEnL72oMyqM2nOUa-EsQ6skASk2G5phCpjEavUw"))
            .andExpect(status().is4xxClientError());
  }
}
