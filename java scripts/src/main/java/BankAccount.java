/**
 * Simple example of OOP design pattern
 */
public class BankAccount {
    private long number; // account number
    private long balance; // current balance (in cents)
    private Action lastAct; // last action performed
    public class Action {
        private String act;
        private long amount;
        Action(String act, long amount) {
            this.act = act;
            this.amount = amount;
        }
        public String toString() {
            return "account no.: " + number + ": " + act + " " + amount;
        }
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public void deposit(long amount) {
        balance += amount;
        lastAct = new Action("deposit", amount);
    }
    public void withdraw(long amount) {
        balance -= amount;
        lastAct = new Action("withdraw", amount);
    }
    public void transfer(BankAccount other, long amount) {
        other.withdraw(amount);
        deposit(amount);
        lastAct = this.new Action("transfer", amount);
        //other.lastAct = other.new Action("transfer", amount);
        other.lastAct = lastAct;
    }

    public static void main(String[] args) {
        BankAccount acc1 = new BankAccount();
        acc1.setNumber(1L);
        BankAccount acc2 = new BankAccount();
        acc2.setNumber(2L);
        acc1.deposit(200L);
        acc2.deposit(6666L);

        acc1.transfer(acc2,200L);
        System.out.println(acc1.lastAct);
        System.out.println(acc2.lastAct);
    }
}
