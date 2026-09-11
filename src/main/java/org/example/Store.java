package org.example;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Store {

    private List<Product> products = new LinkedList<>();
    private Map<Integer, Product> productById = new LinkedHashMap<>();
    private Map<Integer, Order> orders = new LinkedHashMap<>();
    private Set<String> categores = new LinkedHashSet<>();
    private List<Review> reviews = new LinkedList<>();
    private List<Order> deliveredOrders = new ArrayList<>();
    private Queue<Order> shippedOrders = new ArrayDeque<>();
    private Scanner in = new Scanner(System.in);
    public void addProduct() {
        System.out.println("enter product id");
        int id=in.nextInt();
        in.nextLine();
        if(id<=0){
            System.out.println("unvalid product id");
            return;
        }
       Product p1=productById.get(id);
        if(p1!=null){
            System.out.println("the product is already in stock");
            System.out.println("enter quantity");
            int quantity=in.nextInt();
            in.nextLine();
            int quantity1=p1.getQuantity();
            quantity1+=quantity;
            p1.setQuantity(quantity1);
            return;
        }
        System.out.println("enter product name");
        String name=in.nextLine();
        if(name ==null || name.isEmpty()){
            System.out.println("unvalid product name");
            return;
        }
        System.out.println("enter product price");
        double price=in.nextDouble();
        in.nextLine();
        if(price<=0){
            System.out.println("unvalid product price");
            return;
        }
        System.out.println("enter product category`");
        String category=in.nextLine();
        if(category==null || category.isEmpty()){
            System.out.println("unvalid product category");
            return;
        }
        System.out.println("enter the quantity");
        int quantity=in.nextInt();
        if(quantity<=0){
            System.out.println("unvalid product quantity");
            return;
        }
       Product p=new Product(id,name,price,category,quantity);
        products.add(p);
        productById.put(id,p);
        categores.add(category);

        System.out.println("enter added successfully");
    }
    public void removeProduct(){
        System.out.println("enter product id");
        int id=in.nextInt();
        in.nextLine();

        if(id<=0){
            System.out.println("unvalid product id");
            return;
        }
        Product p=productById.get(id);
        if(p==null){
            System.out.println("product not found");
            return;
        }
        products.remove(p);
        productById.remove(id);


    }
public void displayProducts(){
        if(products.isEmpty()){
            System.out.println("nothing to display");
            return;
        }
        for(Product p:products){
            System.out.println(p);

        }
}
public void searchProductById(){
        System.out.println("enter product id");
        int id=in.nextInt();
        in.nextLine();
        boolean found =false;
        if(id<=0){
            System.out.println("unvalid product id");
            return;
        }
Optional.ofNullable(productById.get(id))
        .ifPresentOrElse(p->{
            System.out.println("the product is found");
            System.out.println(p);
        },
                ()-> System.out.printf("the product is not found")
                );
}
public void showAllCategories(){
        if(categores.isEmpty()){
            System.out.println("nothing to display");
            return;
        }
       for(String s:categores){
           System.out.println(s);
       }

}
public void createOrder(){
        System.out.println("enter order id");
        int id=in.nextInt();
        in.nextLine();
        if(id<=0){
            System.out.println("unvalid order id");
                return;
        }
        if(orders.containsKey(id)){
            System.out.println("order already exists");
            return;
        }
    System.out.println("enter name of customer");
        String name=in.nextLine();
        if(name==null || name.isEmpty()){
            System.out.println("unvalid order name");
            return;
        }
        orders.put(id, new Order(id,name));
    System.out.println("order created successfully");
}
public void displayProductsByPrice(){
        if(products.isEmpty()){
            System.out.println("nothing to display");
            return;
        }
        Collections.sort(products);
        for(Product p:products){
            System.out.println(p.getName()+"products by price"+p.getPrice());
        }
}
public void addItemToOrder(){
        System.out.println("enter order id");
        int id=in.nextInt();
        in.nextLine();
        Order order=orders.get(id);
        if(order==null){
            System.out.println("unvalid order id");
                return;
        }
    System.out.println("enter the id of products");
        int productId=in.nextInt();
    Product product = productById.get(productId);
        if(product==null){
            System.out.println("unvalid product id");
            return;
        }
        if(order.getOrderStatus()!=OrderStatus.PENDING){
            System.out.println("unvalid order status");
            return;
        }
    System.out.println("enter the quantity");
        int quantity=in.nextInt();
        in.nextLine();
        if(quantity<=0){
            System.out.println("unvalid quantity");
            return;
        }
        CartItem cartItem=new CartItem(product,quantity);
        order.addItem(cartItem);
        System.out.println("order added successfully");
}
public void removeItemFromOrder(){
        System.out.println("enter order id");
        int id=in.nextInt();
        in.nextLine();
        Order order=orders.get(id);
        if(order==null){
            System.out.println("unvalid order id");
            return;
        }
        System.out.println("enter the id of products");
        int productId=in.nextInt();
        in.nextLine();
 Optional<CartItem>item=order.getItems().stream().filter(i->i.getProduct().getId()==productId).findFirst();
 if(item.isEmpty()){
     System.out.println("item not found");
     return ;
 }
 CartItem cartItem=item.get();
   order.removeItem(cartItem);
   System.out.println("order removed successfully");
}
public void displayOrders(){
    System.out.println("enter order id");
    int id=in.nextInt();
    in.nextLine();
 Optional.ofNullable(orders.get(id))
         .ifPresentOrElse(order->{
             System.out.println(order);
             System.out.println("order has been added successfully");
         },
                 ()-> System.out.println("the order not found")
                 );
}
public void addOrderToShipping(){
        System.out.println("enter order id");
        int id=in.nextInt();
        in.nextLine();
        Order order=orders.get(id);
        if(order==null){
            System.out.println("unvalid order id");
            return;
        }
        if(order.getOrderStatus()!=OrderStatus.PENDING){
            System.out.println("unvalid order status");
            return;
        }
        if(shippedOrders.contains(order)){
            System.out.println("the order has already been shipped");
            return;
        }
        shippedOrders.add(order);
        order.updateStatus(OrderStatus.SHIPPED);
        System.out.println("order updated successfully");

}
public void ShipNextOrder(){
        if(shippedOrders.isEmpty()){
            System.out.println("nothing to display");
            return;
        }
        Order order=shippedOrders.peek();
        if(order==null){
            System.out.println("nothing to display");
            return;
        }
        if(order.getItems().isEmpty()){
            System.out.println("nothing to display");
            return;
        }
      shippedOrders.poll();
        order.updateStatus(OrderStatus.DELIVERED);
        deliveredOrders.add(order);
    System.out.println("order updated successfully");
}
public void canselOrders(){
        System.out.println("enter order id");
        int id=in.nextInt();
        in.nextLine();
        Order order=orders.get(id);
        if(order==null){
            System.out.println("unvalid order id");
            return;
        }
        if(order.getOrderStatus()==OrderStatus.PENDING){
            order.updateStatus(OrderStatus.CANCELLED);
            System.out.println("order cancelled successfully");
            return;
        }
        if(order.getOrderStatus()==OrderStatus.DELIVERED){
            shippedOrders.remove(order);
            order.updateStatus(OrderStatus.CANCELLED);
            System.out.println("order cancelled successfully");
            return;
        }
        if(order.getOrderStatus()==OrderStatus.DELIVERED){
            System.out.println("the order not can change order status this already delivered");
            return;
        }
        if(order.getOrderStatus()==OrderStatus.CANCELLED){
            System.out.println("the order not can change order status this already delivered");
            return;
        }
    System.out.println("cansel orders cancelled successfully");
}
public void searchOrders(){
        System.out.println("enter order id");
        int id=in.nextInt();
        in.nextLine();
    Optional.ofNullable(orders.get(id))
            .ifPresentOrElse(System.out::println,
                    () -> System.out.println("order not found"));

}
public void addReviewToProduct(){
        System.out.println("enter product id");
        int id=in.nextInt();
        in.nextLine();
      Optional<Review>existReviw=reviews.stream().filter(review -> review.getOrderId()==id).findFirst();
      if(existReviw.isEmpty()){
          System.out.println("the product has been reviwded");
          return;
      }
    System.out.println("neter cutomer name");
        String name=in.nextLine();
        System.out.println("neter cutomer description");
        String description=in.nextLine();
        reviews.add(new Review(id,name,description));
    System.out.println("review added successfully");
}
public void showReviews(){
       if(reviews.isEmpty()){
           System.out.println("nothing to display");
           return;
       }
       for(Review review:reviews){
           System.out.println(review);

       }
}
public void removeOfStock(){
        System.out.println("enter product id");
        int id=in.nextInt();
        in.nextLine();

        Iterator<Product> iterator=products.iterator();
        while(iterator.hasNext()){
            Product product=iterator.next();
            if(product.getQuantity()==0){
                iterator.remove();
                productById.remove(product.getId());
            }
        }
    System.out.println("product removed successfully");

}
public void displayOrderByOrderd(){
      if (orders.isEmpty()){
          System.out.println("nothing to display");
          return;
      }
    List<Order> sortedOrders = new ArrayList<>(orders.values());
      sortedOrders.sort(Comparator.comparingDouble(Order::getTotal));
    for(Order order:sortedOrders){
        order.displayOrder();
    }
}

}

