import java.util.Scanner;

public class StoreroomDAO {
    public static void main(String[] args) {
        try {
            Scanner inputStr = new Scanner(System.in);
            Scanner inputInt = new Scanner(System.in);
            while (true) {

                Storeroom.showStoreroom();
                int limitOfPurchase = 0;
                String customerName;
                System.out.println("welcome , enter your name please:");
                customerName = inputStr.nextLine();
                Cart.createCart(customerName);
                while (true) {
                    System.out.println("(ending purchase= end , show cart= p , find an item in cart= fc , delete an item from cart= d)\n" +
                            "please enter id :");
                    String in = inputStr.nextLine();
                    if (in.equals("p")) {
                        Cart.findAll(customerName);
                        continue;
                    }
                    if (in.equals("end")) {
                        Cart.findAll(customerName);
                        System.out.println("\n============================== good bye! ==============================\n\n");
                        break;
                    }
                    if (in.equals("d") && limitOfPurchase > 0) {
                        System.out.println("enter the id you want to delete:");
                        int deleteId = inputInt.nextInt();
                        Cart.deleteItem(customerName, deleteId);
                        limitOfPurchase--;
                        continue;
                    }
                    if (in.equals("fc")) {
                        System.out.println("enter the id you want to show from your cart : ");
                        int cartId = inputInt.nextInt();
                        Cart.findById(cartId, customerName);
                        continue;
                    }
                    int id = 0;
                    int quantity = 0;
                    if (limitOfPurchase < 5) {
                        id = Integer.parseInt(in);
                        System.out.println("enter quantity :");
                        quantity = inputInt.nextInt();
                        limitOfPurchase = Cart.editCart(id, quantity, customerName, limitOfPurchase);
                        Storeroom.editStoreroom(id, quantity);
                        //limitOfPurchase++;
                    } else if (limitOfPurchase == 5) {
                        //in this case we can update an exisating item only, not purchasing a new item!!!
                        id = Integer.parseInt(in);
                        if (Cart.isContainingTheId(id, customerName)) {
                            System.out.println("enter quantity :");
                            quantity = inputInt.nextInt();
                            Cart.editCart(id, quantity, customerName, limitOfPurchase);
                            Storeroom.editStoreroom(id, quantity);
                        }
                        System.out.println("you have purchase limitation !!! . delete an item or finish your purchase");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
