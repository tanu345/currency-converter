package package1;
import javax.swing.*;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
public class currencyConverter extends JFrame {
    String apiKey ="dbb9b7419286d4639940ce9b";
    ExchangeRateApi exchangeRateApi;
    public currencyConverter() {
        super("CURRENCY CONVERTER");
        setSize(550, 300);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Input amount:");
        JTextField nameField = new JTextField(15);

        String[] currencies = {"USD", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KID", "KMF", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD", "SSP", "STN", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "UYU", "UZS", "VES", "VND", "VUV", "WST", "XAF", "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"};
        JComboBox<String> currencyDropdown1 = new JComboBox<>(currencies);
        JLabel label2 = new JLabel("Convert to:");
        JComboBox<String> currencyDropdown2 = new JComboBox<>(currencies);

        JLabel resultLabel = new JLabel("");
        exchangeRateApi = new ExchangeRateApi(apiKey);

        currencyDropdown1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency(nameField, currencyDropdown1, currencyDropdown2, resultLabel);
            }
        });

        currencyDropdown2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency(nameField, currencyDropdown1, currencyDropdown2, resultLabel);
            }
        });

        add(label);
        add(nameField);
        add(currencyDropdown1);
        add(label2);
        add(currencyDropdown2);
        add(resultLabel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void convertCurrency(JTextField nameField, JComboBox<String> currencyDropdown1, JComboBox<String> currencyDropdown2, JLabel resultLabel) {
        String inputCurrency = (String) currencyDropdown1.getSelectedItem();
        String outputCurrency = (String) currencyDropdown2.getSelectedItem();
        String amountText = nameField.getText();

        if (amountText.isEmpty()) {
            resultLabel.setText("No Input");
            return;
        }

        double amount = Double.parseDouble(amountText);

        ExchangeRateApi obj = new ExchangeRateApi("dbb9b7419286d4639940ce9b");
        HashMap<String, Double> map = exchangeRateApi.getExchangeRates();
        double result = converter(inputCurrency, outputCurrency, amount, map);

        if (result == -1) {
            resultLabel.setText("INVALID INPUT");
        } else {
            resultLabel.setText(amount + " " + inputCurrency + " = " + result + " " + outputCurrency + "\n Last Updated: " + obj.getdate());
        }
    }

    private double any_to_dollar(String input, double amt, HashMap<String, Double> map) {
        Double val = map.get(input);
        return (1 / val) * amt;
    }

    private double dollar_to_any(String output, double amt, HashMap<String, Double> map) {
        Double val = map.get(output);
        return val * (amt);
    }

    private double converter(String input, String output, double amount, HashMap<String, Double> map) {
        if (input.equals(output) || amount == 0)
            return amount;
        if (amount < 0)
            return -1;
        else {
            double indollar = any_to_dollar(input, amount, map);
            return dollar_to_any(output, indollar, map);
        }
    }

    public static void main(String[] args) {
        currencyConverter gui = new currencyConverter();
        gui.setVisible(true);
    }
}
ss
