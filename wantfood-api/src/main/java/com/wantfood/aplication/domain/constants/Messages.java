package com.wantfood.aplication.domain.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {

    public static final String GROUP_IN_USE = "Code group %d cannot be removed as it is in use";

    public static final String CITY_IN_USE = "Code city %d cannot be removed as it is in use";

    public static final String PRODUCT_IN_USE = "Product code %d cannot be removed as it is in use";

    public static final String PAYMENT_IN_USE_METHOD = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

    public static final String KITCHEN_IN_USE = "Kitchen code %d cannot be removed as it is in use";

    public static final String STATE_IN_USE = "code state %d cannot be removed as it is in use";
}
