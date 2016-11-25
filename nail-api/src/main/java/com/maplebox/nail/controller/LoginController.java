package com.maplebox.nail.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.maplebox.nail.domain.DataResult;
import com.maplebox.nail.domain.LoginToken;
import com.maplebox.nail.domain.ResultCode;
import com.maplebox.nail.domain.UserLogin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {
	static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Value("${secret.key}")
	private String secretKey;
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public DataResult<LoginToken> login(@RequestBody UserLogin user, HttpSession session) throws Exception {
		String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (user == null) {
			throw new Exception("驗證失敗");
		} else if (code == null || code.equals(user.getCode()) == false) {
			throw new Exception("驗證碼錯誤");
		} else if ("root".equals(user.getName()) == false || "12345".equals(user.getPassword()) == false) {
			throw new Exception("帳號密碼錯誤");
		}
		DateTime nowDate = new DateTime(new Date());
		String token = Jwts.builder().setSubject(user.getName())
	        .claim("roles", "ADMIN").setIssuedAt(nowDate.toDate())
	        .setExpiration(nowDate.plusDays(7).toDate())
	        .signWith(SignatureAlgorithm.HS256, secretKey).compact();
		return new DataResult<LoginToken>(true, ResultCode.SUCCESS, new LoginToken(user.getName(), token));
	}
}
