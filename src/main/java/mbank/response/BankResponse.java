package mbank.response;

public class BankResponse {
    String name;
    Double walletBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }
}
