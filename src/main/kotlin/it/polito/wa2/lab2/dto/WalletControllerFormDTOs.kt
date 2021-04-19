package it.polito.wa2.lab2.dto

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/*
 * Fields are nullable otherwise spring will instantiate them with the default value of 0
 * triggering a misleading error (like a not found). The correct description of the error
 * is that the required field has not been filled, so the @NotNull validator should
 * rise its exception.
 *
 * By doing this wherever i refer to the fields of these DTOs in the WalletController i must use the ?:
 * operator and provide an alternative value of -1 if the field is null
 * (that will always trigger a not found exception or a min value constraint error).
 * The formDTO will never be instantiated anyway because of the validator constraint,
 * so it doesn't matter which alternative value i use in the controller
 */
data class AddWalletToCustomerFormDTO(@field:NotNull val customerId: Long? = null)
data class PerformTransactionFormDTO(@field:NotNull val to: Long? = null, @field:NotNull @field:Min(0) val amount: BigDecimal? = null)
