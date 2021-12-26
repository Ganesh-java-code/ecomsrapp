package in.nareshit.raghu.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import in.nareshit.raghu.constants.OrderStatus;
import in.nareshit.raghu.entity.CartItem;
import in.nareshit.raghu.entity.Order;
import in.nareshit.raghu.entity.OrderItem;

@Component
public class OrderUtil {

	public Order mapCartItemsToOrder(List<CartItem> cartItems) {
		Order order = new Order();
		order.setStatus(OrderStatus.OPEN.name());
		List<OrderItem> orderItemsList = new ArrayList<>();
		for(CartItem item: cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(item.getProduct());
			orderItem.setQty(item.getQty());
			orderItem.setLineAmount(orderItem.getQty() * item.getProduct().getCost());
			orderItemsList.add(orderItem);
		}
		order.setOrderItems(orderItemsList);
		return order;
	}

	public void calculateGrandTotal(List<Order> orders) {
		for(Order order:orders) {
			calculateGrandTotalOrder(order);
		}
	}
	
	public void calculateGrandTotalOrder(Order order) {
		Double amount = order.getOrderItems().stream()
				.mapToDouble(ob->ob.getLineAmount())
				.sum();
		order.setGrandTotal(amount);
		
	}

}
