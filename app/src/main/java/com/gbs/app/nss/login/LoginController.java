package com.gbs.app.nss.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping(value = {"/lgn/form", "/"})
	public String lgnForm(String denied, Model model) throws Exception{
		return "seungsik/lgn/lgnForm";
	}
}
