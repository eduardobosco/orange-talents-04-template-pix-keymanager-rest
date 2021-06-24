package br.com.zup.edu.shared.validation

import br.com.zup.edu.NovaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidChavePixValidator::class])
annotation class ValidPixKey(
    val message: String = "chave Pix inválida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ValidChavePixValidator: ConstraintValidator<ValidPixKey, NovaChavePixRequest>{
    override fun isValid(
        value: NovaChavePixRequest?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext?
    ): Boolean {
        if(value?.tipoDeChave == null)
            return false
        return value.tipoDeChave.valida(value.chave)
    }
}