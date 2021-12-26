package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nareshit.raghu.constants.OrderStatus;
import in.nareshit.raghu.service.IOrderService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/billPay")
	public String doPayment(
			@RequestParam Long orderId
			) 
	{
		orderService.updateOrderStatus(orderId, OrderStatus.PLACED.name());
		return "redirect:/order/customerOrders";
	}
}
