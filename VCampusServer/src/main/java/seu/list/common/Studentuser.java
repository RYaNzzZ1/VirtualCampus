package seu.list.common;

import java.util.ArrayList;

public class Studentuser extends User {
    int balance = 0;

    void buy(ArrayList<Goods> Goodlist, Shop shop) {

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
