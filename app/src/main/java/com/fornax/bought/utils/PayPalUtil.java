package com.fornax.bought.utils;

import android.net.Uri;

import com.paypal.android.sdk.payments.PayPalConfiguration;

/**
 * Created by Hallan on 15/03/2016.
 */
public class PayPalUtil {

    public static final String CONFIG_CLIENT_ID = "AaXyCTpasBmuMqYmQcGeaI7kyPpCPNEP3gJD1XHj5qPDHcRjgkNxNfwcXAksZ1fz-9ZMtEenB6JQJGyO";
    public static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    public static final int REQUEST_CODE_PAYMENT = 1;

    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
}
