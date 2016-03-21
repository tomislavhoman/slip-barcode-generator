package eu.homan.slip.barcode.generator;

public final class TransactionDataBuilder {

    private static final String BARCODE_HEADER = "HRVHUB30\n";
    private static final String CURRENCY = "HRK\n";

    // Recipient data
    private String recipientName = "";
    private String recipientAddress1 = "";
    private String recipientAddress2 = "";
    private String recipientIban = "";

    // Payer data
    private String payerName = "";
    private String payerAddress1 = "";
    private String payerAddress2 = "";

    // Transaction data
    private String amount = "";
    private String model = "";
    private String referenceNumber = "";
    private String descriptionOfPayment = "";

    public static TransactionDataBuilder create() {
        return new TransactionDataBuilder();
    }

    private TransactionDataBuilder() {
    }

    public TransactionDataBuilder recipientName(final String recipientName) {
        this.recipientName = recipientName;
        return this;
    }

    public TransactionDataBuilder recipientAddress1(final String recipientAddress1) {
        this.recipientAddress1 = recipientAddress1;
        return this;
    }

    public TransactionDataBuilder recipientAddress2(final String recipientAddress2) {
        this.recipientAddress2 = recipientAddress2;
        return this;
    }

    public TransactionDataBuilder recipientIban(final String recipientIban) {
        this.recipientIban = recipientIban;
        return this;
    }

    public TransactionDataBuilder payerName(final String payerName) {
        this.payerName = payerName;
        return this;
    }

    public TransactionDataBuilder payerAddress1(final String payerAddress1) {
        this.payerAddress1 = payerAddress1;
        return this;
    }

    public TransactionDataBuilder payerAddress2(final String payerAddress2) {
        this.payerAddress2 = payerAddress2;
        return this;
    }

    public TransactionDataBuilder amount(final String amount) {
        this.amount = amount;
        return this;
    }

    public TransactionDataBuilder model(final String model) {
        this.model = model;
        return this;
    }

    public TransactionDataBuilder referenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public TransactionDataBuilder descriptionOfPayment(final String descriptionOfPayment) {
        this.descriptionOfPayment = descriptionOfPayment;
        return this;
    }

    public String build() {
        final StringBuilder builder = new StringBuilder();

        builder.append(BARCODE_HEADER);
        builder.append(CURRENCY);
        builder.append(formatAmount(amount)).append("\n");
        builder.append(payerName).append("\n");
        builder.append(payerAddress1).append("\n");
        builder.append(payerAddress2).append("\n");
        builder.append(recipientName).append("\n");
        builder.append(recipientAddress1).append("\n");
        builder.append(recipientAddress2).append("\n");
        builder.append(recipientIban).append("\n");
        builder.append(model).append("\n");
        builder.append(referenceNumber).append("\n");
        builder.append("\n");
        builder.append(descriptionOfPayment).append("\n");

        return builder.toString();
    }

    private String formatAmount(final String amount) {
        final String amountWithoutDot = amount.replace(".", "");
        return ("000000000000000" + amountWithoutDot).substring(amountWithoutDot.length());
    }
}
