package com.fiap.tc.infrastructure.presentation.controllers;

import com.fiap.tc.infrastructure.core.security.property.OriginApiProperty;
import com.fiap.tc.infrastructure.presentation.URLMapping;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = URLMapping.ROOT_PRIVATE_API_AUTH)
@Api(tags = "Backoffice OAuth API V1", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class BackofficeTokenController {

    @Autowired
    private OriginApiProperty originApiProperty;

    @ApiOperation(value = "Revoke Refresh Token", notes = "Revoke the refresh token by deleting the corresponding cookie.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully revoked the refresh token")
    })
    @DeleteMapping("/revoke")
    public void revoke(
            @ApiParam(value = "HTTP request", required = true) HttpServletRequest req,
            @ApiParam(value = "HTTP response", required = true) HttpServletResponse resp) {
      try{
          Cookie cookie = new Cookie("refreshToken", null);
          cookie.setHttpOnly(true);
          cookie.setSecure(originApiProperty.getSecurity().isEnableHttps());
          cookie.setPath(req.getContextPath() + "/oauth/token");
          cookie.setMaxAge(0);

          resp.addCookie(cookie);
          resp.setStatus(HttpStatus.NO_CONTENT.value());
      } catch (Exception e) {
          log.error(e.getMessage(), e);
          resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      }
    }
}
