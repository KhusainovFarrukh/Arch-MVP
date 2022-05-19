package kh.farrukh.arch_mvp.utils

import org.mockito.ArgumentCaptor

/**
 *Created by farrukh_kh on 5/19/22 2:19 PM
 *kh.farrukh.arch_mvp.utils
 **/
open class BaseTest {
    open fun <T> captureArg(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}