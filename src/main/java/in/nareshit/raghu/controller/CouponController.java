package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Coupon;
import in.nareshit.raghu.exception.CouponNotFoundException;
import in.nareshit.raghu.service.ICouponService;

/**
 * @author:RAGHU SIR 
 *  Generated F/w:SHWR-Framework 
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {
	@Autowired
	private ICouponService service;

	@GetMapping("/register")
	public String registerCoupon(Model model) {
		model.addAttribute("coupon",new Coupon());
		return "CouponRegister";
	}

	@PostMapping("/save")
	public String saveCoupon(@ModelAttribute Coupon coupon, Model model) {
		java.lang.Long id=service.saveCoupon(coupon);
		model.addAttribute("message","Coupon created with Id:"+id);
		model.addAttribute("coupon",new Coupon()) ;
		return "CouponRegister";
	}

	@GetMapping("/all")
	public String getAllCoupons(Model model,
			@RequestParam(value = "message", required = false) String message) {
		List<Coupon> list=service.getAllCoupons();
		model.addAttribute("list",list);
		model.addAttribute("message",message);
		return "CouponData";
	}

	@GetMapping("/delete")
	public String deleteCoupon(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.deleteCoupon(id);
			attributes.addAttribute("message","Coupon deleted with Id:"+id);
		} catch(CouponNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
		}
		return "redirect:all";
	}

	@GetMapping("/edit")
	public String editCoupon(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page=null;
		try {
			Coupon ob=service.getOneCoupon(id);
			model.addAttribute("coupon",ob);
			page="CouponEdit";
		} catch(CouponNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
			page="redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateCoupon(@ModelAttribute Coupon coupon, RedirectAttributes attributes) {
		service.updateCoupon(coupon);
		attributes.addAttribute("message","Coupon updated");
		return "redirect:all";
	}
}
