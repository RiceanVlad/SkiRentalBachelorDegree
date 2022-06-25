package com.example.skirental.utils

import com.google.android.gms.wallet.WalletConstants

object Constants {
    const val USER_HAS_DETAILS = "USER_HAS_DETAILS"
    const val USER_DETAILS = "USER_DETAILS"
    const val USER_CART_ITEMS = "USER_CART_ITEMS"
    const val FIRESTORE_ITEMS_COLLECTION = "Items"
    const val FIRESTORE_ITEMS_DOCUMENT_ID = "VGm6D7t3LCxMxmR8Cbx4"
    const val FIRESTORE_SKI_COLLECTION = "SKI"
    const val FIRESTORE_SKI_BOOTS_COLLECTION = "SKI_BOOTS"
    const val FIRESTORE_CART_ITEMS_COLLECTION = "CartItems"
    const val FIRESTORE_USERS_COLLECTION = "users"
    const val EQUIPMENT_FRAGMENT_LABEL_SKI = "SKIS"
    const val EQUIPMENT_FRAGMENT_LABEL_SKI_BOOTS = "SKI Boots"
    const val FILTER_USER_REQUIREMENTS = "FILTER_USER_REQUIREMENTS"
    const val FILTER_SKI_LENGTH_MARGIN = 10
    const val FILTER_SKI_BEGINNER = 17
    const val FILTER_SKI_INTERMEDIATE = 13
    const val FILTER_SKI_PRO = 9
    const val MONTH = 2629800000L
    const val FIRESTORE_START_DATE = "start_date"
    const val FIRESTORE_END_DATE = "end_date"
    const val FIRESTORE_ADDITIONAL_COMMENT = "additional_comment"
    const val FIRESTORE_HEIGHT = "height"
    const val FIRESTORE_WEIGHT = "weight"
    const val FIRESTORE_SHOE_SIZE = "shoeSize"
    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST
    const val COUNTRY_CODE = "US"
    const val CURRENCY_CODE = "USD"
    const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
}