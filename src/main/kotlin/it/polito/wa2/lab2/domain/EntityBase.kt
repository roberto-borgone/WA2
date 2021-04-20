package it.polito.wa2.lab2.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class EntityBase<T: Serializable> {

    companion object{
        private const val serialVersionUID = -43869754L
    }

    @field:Id
    @field:GeneratedValue
    @field:Column(updatable = false, nullable = false)
    private var id: T? = null

    fun getId(): T? = id

    /*
    * TODO: provide implementation of equals and hashCode
    *
    * It will be required only if i start using entities outside the methods
    * of the service, since until they are used in the context of the same transaction
    * (that starts and ends along with the single method) Hibernate knows how to compare
    * entities.
    *
    * Since it is not required yet i avoid using the implementation of hashCode
    * that returns a constant as showed in the slides, to keep getting benefit
    * of the O(1) research in Sets
    */

}