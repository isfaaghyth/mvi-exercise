@file:OptIn(ExperimentalCoroutinesApi::class)

package app.isfa.mvi

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun test_should_show_product_list() {
        runTest {
            // Given
            val vm = createViewModel()

            // Then
            vm.state.test {
                assertTrue(awaitItem().productUiState.items.isEmpty())
                assertTrue(awaitItem().productUiState.items.isNotEmpty())
            }
        }
    }

    @Test
    fun test_should_show_category_list_based_on_product_name() {
        runTest {
            // Given
            val expectedValue = listOf("Jakarta", "Makassar")
            val vm = createViewModel(fakeCategoryUseCase(expectedValue))

            // When
            vm.sendEvent(ProductItemClicked(""))

            // Then
            vm.state.test {
                assertTrue(awaitItem().categoryUiState.items.isEmpty())
                assertTrue(awaitItem().categoryUiState.items == expectedValue)
            }
        }
    }

    @Test
    fun test_should_not_show_category_because_empty() {
        runTest {
            // Given
            val expectedValue = listOf("")
            val vm = createViewModel(fakeCategoryUseCase(expectedValue))

            // When
            vm.sendEvent(ProductItemClicked(""))

            // Then
            vm.state.test {
                assertTrue(awaitItem().categoryUiState.items.isEmpty())
                println(awaitItem().categoryUiState.state)
            }
        }
    }

    @Test
    fun test_should_got_network_error_and_dispatch_effect_show_error() {
        runTest {
            // Given
            val vm = createViewModel(
                fakeCategoryUseCase(
                    items = listOf(),
                    isNetworkError = true
                )
            )

            // When
            vm.sendEvent(ProductItemClicked(""))

            // Then
            vm.effects.test {
                assertTrue(awaitItem() is ShowNetworkError)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun fakeCategoryUseCase(
        items: List<String>,
        isNetworkError: Boolean = false
    ) = object : CategoryUseCase {
            override fun invoke(name: String) =
                if (!isNetworkError) items else error("")
        }

    private fun createViewModel(
        categoryUseCase: CategoryUseCase = CategoryUseCaseImpl()
    ) = MainViewModel(
        categoryUseCase = categoryUseCase,
        dispatcher = dispatcher
    )
}