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

    public static final String USER_MESSAGE = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato "
            + "com o administrador do sistema.";

    public static final String ERROR_BODY = "The body of this request is invalid. Possible syntax error.";
    public static final String INVALID_FIELDS = "One or more invalid fields. Fill in correctly and try again.";
}
