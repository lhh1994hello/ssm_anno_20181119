package com.atguigu.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crud.util.Msg;

//作者:lhh 创建时间:2018年11月18日 下午1:42:23
@Controller
@RequestMapping(value = "/test")
public class TestController {
	@ResponseBody
	@RequestMapping(value = "test")
	public Msg test() {
		System.out.println("AAAAAA");
		return Msg.Success();
	}
	@ResponseBody
	@RequestMapping("/testPathVariable/{id}")
	public Msg testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable: " + id);
		return Msg.Success();
	}
}
