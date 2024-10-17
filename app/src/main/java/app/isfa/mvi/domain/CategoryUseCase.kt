package app.isfa.mvi.domain

class CategoryUseCase {

    private val response = mapOf(
        "PLN" to listOf("Jakarta", "Makassar", "Bali"),
        "Pulsa" to listOf("Telkomsel", "XL", "Flexi"),
        "PDAM" to listOf(),
    )

    operator fun invoke(name: String) =
        response[name] ?: error("Aduh, gagal nih.")
}