package mx.com.juca.store.util;

import java.math.BigDecimal;

import mx.com.juca.store.business.dto.BrandDTO;
import mx.com.juca.store.business.dto.CategoryDTO;
import mx.com.juca.store.business.dto.CreditCardDTO;
import mx.com.juca.store.business.dto.CustomerDTO;
import mx.com.juca.store.business.dto.OrderDTO;
import mx.com.juca.store.business.dto.ProductDTO;
import mx.com.juca.store.business.dto.ProductOrderDTO;
import mx.com.juca.store.business.dto.payment.PaymentDTO;
import mx.com.juca.store.persistence.domain.Brand;
import mx.com.juca.store.persistence.domain.Category;
import mx.com.juca.store.persistence.domain.CreditCard;
import mx.com.juca.store.persistence.domain.Customer;
import mx.com.juca.store.persistence.domain.Order;
import mx.com.juca.store.persistence.domain.Product;
import mx.com.juca.store.persistence.domain.ProductOrder;
import mx.com.juca.store.persistence.domain.ProductOrderId;
import mx.com.juca.store.persistence.domain.payment.Payment;
import mx.com.juca.store.persistence.domain.payment.PaymentId;

/**
 * Utility class which converts between DTO and Domain classes and viceversa
 * 
 * @author Juan Carlos Cruz
 * @since February 12, 2011
 */
public final class Transfomer {
	
	private Transfomer(){}

	public static BrandDTO convertToDTO(final Brand brand){
		BrandDTO dto = new BrandDTO();
		dto.setIdBrand(brand.getIdBrand());
		dto.setName(brand.getName());
		return dto;
	}
	
	public static CategoryDTO convertToDTO(final Category category){
		CategoryDTO dto = new CategoryDTO();
		dto.setIdCategory(category.getIdCategory());
		dto.setName(category.getName());
		return dto;
	}
	
	public static CreditCardDTO convertToDTO(final CreditCard creditCard){
		CreditCardDTO dto = new CreditCardDTO();
		dto.setCreationTs(creditCard.getCreationTs());
		dto.setModificationTs(creditCard.getModificationTs());
		dto.setExpirationDate(DateUtils.formatDate(creditCard.getExpirationDate(), DateUtils.FORMAT_MMYYYY));
		dto.setIdCreditCard(creditCard.getIdCreditCard());
		dto.setNumber(creditCard.getNumber());		
		dto.setSecurityCode(creditCard.getSecurityCode());		
		dto.setType(creditCard.getType());
		dto.setCustomerDTO(Transfomer.convertToDTO(creditCard.getCustomer()));
		return dto;
	}
	
	public static CustomerDTO convertToDTO(final Customer customer){
		CustomerDTO dto = new CustomerDTO();
		dto.setCreationTs(customer.getCreationTs());
		dto.setModificationTs(customer.getModificationTs());
		dto.setEmail(customer.getEmail());
		dto.setIdCustomer(customer.getIdCustomer());
		dto.setFirstName(customer.getFirstName());		
		dto.setLastName(customer.getLastName());		
		dto.setMiddleName(customer.getMiddleName());
		dto.setPassword(customer.getPassword());
		return dto;
	}
	
	public static OrderDTO convertToDTO(final Order order){
		OrderDTO dto = new OrderDTO();
		dto.setCreationTs(order.getCreationTs());
		dto.setModificationTs(order.getModificationTs());
		dto.setIdOrder(order.getIdOrder());
		dto.setTax(order.getTax().floatValue());
		dto.setTotalSale(order.getTotalSale().floatValue());
		dto.setCustomerDTO(Transfomer.convertToDTO(order.getCustomer()));
		return dto;
	}
	
	public static ProductDTO convertToDTO(final Product product){
		ProductDTO dto = new ProductDTO();
		dto.setBrandDTO(Transfomer.convertToDTO(product.getBrand()));
		dto.setCategoryDTO(Transfomer.convertToDTO(product.getCategory()));
		dto.setCode(product.getCode());
		dto.setDescription(product.getDescription());
		dto.setIdProduct(product.getIdProduct());
		dto.setName(product.getName());
		dto.setPrice(product.getPrice().floatValue());
		dto.setQuantity(product.getQuantity());
		return dto;
	}
	
	public static ProductOrderDTO convertToDTO(final ProductOrder productOrder){
		ProductOrderDTO dto = new ProductOrderDTO();
		dto.setNumberItems(productOrder.getNumberItems());
		dto.setOrderDTO(Transfomer.convertToDTO(productOrder.getOrder()));
		dto.setProductDTO(Transfomer.convertToDTO(productOrder.getProduct()));
		return dto;
	}
	
	public static PaymentDTO convertToDTO(final Payment payment){
		PaymentDTO dto = new PaymentDTO();
		dto.setCreationTs(payment.getCreationTs());
		dto.setIdCustomer(payment.getId().getIdCustomer());
		dto.setIdOrder(payment.getId().getIdOrder());
		dto.setTotalAmountTransaction(new Float(payment.getTotalAmountTransaction()));
		dto.setTransactionNumber(payment.getTransactionNumber());
		return dto;
	}

	public static Brand convertToDomain(final BrandDTO brandDTO){
		Brand brand = new Brand();
		brand.setIdBrand(brandDTO.getIdBrand());
		brand.setName(brandDTO.getName());
		return brand;
	}
	
	public static Category convertToDomain(final CategoryDTO categoryDTO){
		Category category = new Category();
		category.setIdCategory(categoryDTO.getIdCategory());
		category.setName(categoryDTO.getName());
		return category;
	}
	
	public static CreditCard convertToDomain(final CreditCardDTO creditCardDTO){
		CreditCard creditCard = new CreditCard();
		creditCard.setCreationTs(creditCardDTO.getCreationTs()==null?new java.util.Date():creditCardDTO.getCreationTs()); //assign defaults
		creditCard.setModificationTs(creditCardDTO.getModificationTs()==null?new java.util.Date():creditCardDTO.getModificationTs()); //assign defaults
		creditCard.setExpirationDate(DateUtils.parseDate(creditCardDTO.getExpirationDate(), DateUtils.FORMAT_MMYYYY));
		creditCard.setIdCreditCard(creditCardDTO.getIdCreditCard());
		creditCard.setNumber(creditCardDTO.getNumber());		
		creditCard.setSecurityCode(creditCardDTO.getSecurityCode());		
		creditCard.setType(creditCardDTO.getType());
		creditCard.setCustomer(Transfomer.convertToDomain(creditCardDTO.getCustomerDTO()));
		return creditCard;
	}
	
	public static Customer convertToDomain(final CustomerDTO customerDTO){
		Customer customer = new Customer();
		customer.setCreationTs(customerDTO.getCreationTs()==null?new java.util.Date():customerDTO.getCreationTs()); //assign defaults
		customer.setModificationTs(customerDTO.getModificationTs()==null?new java.util.Date():customerDTO.getModificationTs()); //assign defaults
		customer.setEmail(customerDTO.getEmail());
		customer.setIdCustomer(customerDTO.getIdCustomer());
		customer.setFirstName(customerDTO.getFirstName());		
		customer.setLastName(customerDTO.getLastName());		
		customer.setMiddleName(customerDTO.getMiddleName());
		customer.setPassword(customerDTO.getPassword());
		return customer;
	}
	
	public static Order convertToDomain(final OrderDTO orderDTO){
		Order order = new Order();
		order.setCreationTs(orderDTO.getCreationTs()==null?new java.util.Date():orderDTO.getCreationTs()); //assign defaults
		order.setModificationTs(orderDTO.getModificationTs()==null?new java.util.Date():orderDTO.getModificationTs()); //assign defaults
		order.setIdOrder(orderDTO.getIdOrder());
		order.setTax(new BigDecimal(orderDTO.getTax()));
		order.setTotalSale(new BigDecimal(orderDTO.getTotalSale()));
		order.setCustomer(Transfomer.convertToDomain(orderDTO.getCustomerDTO()));
		return order;
	}
	
	public static Product convertToDomain(final ProductDTO productDTO){
		Product product = new Product();
		product.setBrand(Transfomer.convertToDomain(productDTO.getBrandDTO()));
		product.setCategory(Transfomer.convertToDomain(productDTO.getCategoryDTO()));
		product.setCode(productDTO.getCode());
		product.setDescription(productDTO.getDescription());
		product.setIdProduct(productDTO.getIdProduct());
		product.setName(productDTO.getName());
		product.setPrice(new BigDecimal(productDTO.getPrice()));
		product.setQuantity(productDTO.getQuantity());
		return product;
	}
	
	public static ProductOrder convertToDomain(final ProductOrderDTO productOrderDTO){
		ProductOrder productOrder = new ProductOrder();
		productOrder.setNumberItems(productOrderDTO.getNumberItems());
		productOrder.setOrder(Transfomer.convertToDomain(productOrderDTO.getOrderDTO()));
		productOrder.setProduct(Transfomer.convertToDomain(productOrderDTO.getProductDTO()));
		//Don't forget the PK for this Table - which I don't have it on my DTO (it's useless to have it there)
		productOrder.setId(new ProductOrderId(productOrder.getProduct().getIdProduct(), productOrder.getOrder().getIdOrder()));
		return productOrder;
	}
	
	public static Payment convertToDomain(final PaymentDTO paymentDTO){
		Payment payment = new Payment();
		payment.setCreationTs(paymentDTO.getCreationTs()==null?new java.util.Date():paymentDTO.getCreationTs()); //assign a default
		payment.setTotalAmountTransaction(paymentDTO.getTotalAmountTransaction());
		payment.setTransactionNumber(paymentDTO.getTransactionNumber());
		//Don't forget the PK for this Table - which I don't have it on my DTO (it's useless to have it there)
		payment.setId(new PaymentId(paymentDTO.getIdCustomer(), paymentDTO.getIdOrder()));
		return payment;
	}	
	
}