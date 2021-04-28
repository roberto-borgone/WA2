package it.polito.wa2.lab2.repositories

import it.polito.wa2.lab2.domain.Customer
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CrudRepository<Customer, Long>{

    @Query("select case when count(c) > 0 then true else false end from Customer c where c.userProfile.username = :principal and c.id = :customerId")
    fun principalMatchCustomer(@Param("principal") principal: String, @Param("customerId") customerId: Long): Boolean
    @Query("select case when count(w) > 0 then true else false end from Wallet w where w.owner.userProfile.username = :principal and w.id = :walletId")
    fun isWalletOwnedByPrincipal(@Param("principal") principal: String, @Param("walletId") walletId: Long): Boolean
}