package app.isfa.mvi.domain

class ProductUseCase {

    operator fun invoke(): List<Pair<Int, String>> {
        return listOf(
            Pair(1, "PLN"),
            Pair(2, "Pulsa"),
            Pair(3, "PDAM"),
            Pair(99, "Foobar - Network Error"),
        )
    }
}
