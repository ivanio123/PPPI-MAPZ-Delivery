package com.pppi.novaposhta.exception;

import com.pppi.novaposhta.entity.Receipt;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ResourceBundle;

/**
 * Exception class in a case of missing funds.
 * @author group2
 * @version  1.0
 * */
@Getter
@Setter
public class NotEnoughMoneyException extends PayingException{

    public static final String TRANSACTION_FAILED_NOT_ENOUGH_MONEY_KEY_ERROR_MESSAGE_FORMAT = "payment.receipt.failed.not-enough-money.format";
    private Receipt receipt;
    private BigDecimal rejectedFunds;

    /**
     * Constructor for localized error message with rejected insufficient funds.
     * @since 1.0
     **/
    public NotEnoughMoneyException(BigDecimal rejectedFunds, Receipt receipt, ResourceBundle bundle){
        super(buildErrorMessage(rejectedFunds, receipt, bundle));
        this.receipt = receipt;
        this.rejectedFunds = rejectedFunds;
    }

    private static String buildErrorMessage(BigDecimal rejectedFunds, Receipt receipt, ResourceBundle bundle) {
        String format = bundle.getString(TRANSACTION_FAILED_NOT_ENOUGH_MONEY_KEY_ERROR_MESSAGE_FORMAT);
        String uah = bundle.getString(CURRENCY_UAH_KEY);
        return String.format(format, rejectedFunds.toString() + " " + uah, receipt.getTotalPrice().toString() + " " + uah);
    }
}
