package com.example.skirental.ui.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.skirental.databinding.PayFragmentBinding
import com.example.skirental.utils.Constants.LOAD_PAYMENT_DATA_REQUEST_CODE
import com.example.skirental.utils.PaymentsUtils
import com.example.skirental.viewmodels.PayViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.squareup.moshi.Json
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class PayFragment : Fragment() {

    private lateinit var viewModel: PayViewModel
    private lateinit var binding: PayFragmentBinding
    private lateinit var paymentsClient: PaymentsClient
    private val SHIPPING_COST_CENTS = 9 * PaymentsUtils.CENTS.toLong()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PayViewModel::class.java]
        binding = PayFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        paymentsClient = PaymentsUtils.createPaymentsClient(requireActivity())
        possiblyShowGooglePayButton()
        binding.googlePayButton.setOnClickListener {
            requestPayment()
        }

        return binding.root
    }

    private fun requestPayment() {

        // Disables the button to prevent multiple clicks.
        binding.googlePayButton.isClickable = false
        lifecycleScope.launch {
            delay(1000)
            binding.googlePayButton.isClickable = true
        }

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
//        val garmentPrice = selectedGarment.getDouble("price")
        val garmentPrice = 1.0
        val priceCents = Math.round(garmentPrice * PaymentsUtils.CENTS.toLong()) + SHIPPING_COST_CENTS

        val paymentDataRequestJson = PaymentsUtils.getPaymentDataRequest(priceCents.toString())
        if (paymentDataRequestJson == null) {
            Timber.tag("RequestPayment").e("Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(
            paymentsClient.loadPaymentData(request), requireActivity(), LOAD_PAYMENT_DATA_REQUEST_CODE)
    }

    private fun possiblyShowGooglePayButton() {

        val isReadyToPayJson = PaymentsUtils.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                // Process error
                Timber.tag("isReadyToPay failed").w(exception)
            }
        }
    }

    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            binding.googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                requireContext(),
                "Unfortunately, Google Pay is not available on this device",
                Toast.LENGTH_LONG).show();
        }
    }
}