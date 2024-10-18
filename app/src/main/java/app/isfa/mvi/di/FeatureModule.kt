package app.isfa.mvi.di

import app.isfa.mvi.core.EventHandler
import app.isfa.mvi.core.FeatureEventHandler
import app.isfa.mvi.core.MainUpdateScope
import app.isfa.mvi.core.UpdateScope
import app.isfa.mvi.domain.CategoryUseCase
import app.isfa.mvi.domain.ProductUseCase
import app.isfa.mvi.ui.component.category.CategoryUpdate
import app.isfa.mvi.ui.component.category.CategoryUpdateImpl
import app.isfa.mvi.ui.component.position.PositionUpdate
import app.isfa.mvi.ui.component.position.PositionUpdateImpl
import app.isfa.mvi.ui.component.product.ProductUpdate
import app.isfa.mvi.ui.component.product.ProductUpdateImpl

object FeatureModule {

    fun provideEventHandler(): EventHandler = FeatureEventHandler

    private fun provideMainUpdateScope(): UpdateScope = MainUpdateScope()

    fun provideProductUpdate(): ProductUpdate {
        return ProductUpdateImpl(
            ProductUseCase(),
            provideMainUpdateScope(),
            provideEventHandler()
        )
    }

    fun provideCategoryUpdate(): CategoryUpdate {
        return CategoryUpdateImpl(
            CategoryUseCase(),
            provideMainUpdateScope(),
            provideEventHandler()
        )
    }

    fun providePositionUpdate(): PositionUpdate {
        return PositionUpdateImpl(
            updateScope = provideMainUpdateScope()
        )
    }
}