package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {
    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewDetailsContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter(viewDetailsContract, 0)
    }

    @Test
    fun onIncrement_Test() {
        presenter.onIncrement()
        Mockito.verify(viewDetailsContract, Mockito.atLeastOnce()).setCount(anyInt())
    }

    @Test
    fun onDecrement_Test() {
        presenter.onDecrement()
        Mockito.verify(viewDetailsContract, Mockito.atLeastOnce()).setCount(anyInt())
    }

}